package com.gwghk.ims.message.consumer.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.ims.message.dao.entity.Gts2CashinProposal;
import com.gwghk.ims.message.dao.inf.Gts2CashinProposalDao;
import com.gwghk.ims.message.dto.KafkaGTS2CommonData;
import com.gwghk.ims.message.utils.GwConstants;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: KafkaGts2CustomerDataHandle
 * @Description: GTS2入金数据同步
 * @author lawrence
 * @date 2017年8月14日
 *
 */
@Component
public class KafkaGts2OriCashinProposalDataHandle extends KafkaGts2CommonDataHandle {
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2OriCashinProposalDataHandle.class);

	//final Lock lock = new ReentrantLock();

	@Autowired
	private Gts2CashinProposalDao gts2CashinProposalDao;

	/**
	 * 功能：处理消息
	 */
	public void handleMessage(String message, String env) {
		try {
			logger.info("开始-GTS2-{}入金数据->活动中心同步！", env);
			//lock.lock();
			KafkaGTS2CommonData commonData = JsonUtil.json2Obj(message, KafkaGTS2CommonData.class);
			if (commonData != null) {
				if ((GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())
						|| GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA()))
						&& StringUtil.isNotEmpty(commonData.getN())) {

					Gts2CashinProposal gts2CashinProposal = JsonUtil.json2Obj(commonData.getN(), Gts2CashinProposal.class);
					gts2CashinProposal.setEnv(env);
					Long companyId = super.initDataManager
							.getGts2AccountGroupCompanyId(gts2CashinProposal.getAccountGroupId(), env);

					if (StringUtil.isNotEmpty(CompanyEnum.getCodeById(companyId))) {
						DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, companyId);
						
						try {
							if (GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())
									|| (GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA()) && gts2CashinProposalDao.update(gts2CashinProposal)<=0) //更新受影响的条目为零说明之前插入的数据漏掉了
								) {
								gts2CashinProposalDao.save(gts2CashinProposal);
							}
						}catch(DuplicateKeyException ex) {
							gts2CashinProposalDao.update(gts2CashinProposal);//处理重复插入数据的情况
							logger.warn("GTS2-{}入金数据->活动中心同步,插入消息重复,改为更新,消息内容{}",env,message);
						}
						if (gts2CashinProposal.getApproveDate() != null) {
							setCashinCache(env, gts2CashinProposal);
						}
						logger.info("完成-GTS2-{}入金数据->活动中心同步", env);
					}
				}
			} else {
				logger.warn("GTS2-{}入金数据同步没有产生记录！时间:{}", env, new Date());
			}
		} catch (Exception e) {
			logger.info("GTS2-{}入金数据kafka数据格式错误{},消息内容{}", env, e,message);
		} finally {
			//lock.unlock();
		}
	}

}
