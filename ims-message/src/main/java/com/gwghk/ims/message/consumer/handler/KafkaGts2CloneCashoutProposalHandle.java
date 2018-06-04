package com.gwghk.ims.message.consumer.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.ims.message.dao.entity.ActCashout;
import com.gwghk.ims.message.dao.inf.ActCashoutDao;
import com.gwghk.ims.message.dto.Gts2CloneCashoutProposal;
import com.gwghk.ims.message.dto.KafkaGTS2CommonData;
import com.gwghk.ims.message.utils.GwConstants;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: KafkaGts2CashinProposalHandle
 * @Description: GTS2出金数据处理
 * @author lawrence
 * @date 2018年4月2日
 *
 */
@Component
public class KafkaGts2CloneCashoutProposalHandle {
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2CloneCashoutProposalHandle.class);

	//final Lock lock = new ReentrantLock();

	@Autowired
	private ActCashoutDao actCashoutDao;

	private List<Integer> cusTranTypeData = Arrays.asList(200);

	/**
	 * 功能：处理消息
	 */
	public void handleMessage(String message, String env) {
		try {
			logger.info("开始-GTS2-{}出金数据->活动中心同步！", env);
			//lock.lock();
			KafkaGTS2CommonData commonData = JsonUtil.json2Obj(message, KafkaGTS2CommonData.class);
			if (commonData != null) {
				if ((GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())
						|| GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA()))
						&& StringUtil.isNotEmpty(commonData.getN())) {
					Gts2CloneCashoutProposal gts2CloneCashoutProposal = JsonUtil.json2Obj(commonData.getN(),
							Gts2CloneCashoutProposal.class);
					if (StringUtil.isNotEmpty(CompanyEnum.getCodeById(gts2CloneCashoutProposal.getCompanyId()))) {
						if (cusTranTypeData.contains(gts2CloneCashoutProposal.getCusTranType())
								&& "2".equals(gts2CloneCashoutProposal.getProposalStatus())) {
							DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA,
									gts2CloneCashoutProposal.getCompanyId());
							ActCashout actCashout = initParams(gts2CloneCashoutProposal);
							try {
								if (actCashout.getId() == null || actCashoutDao.update(actCashout)<=0) //更新受影响的条目为零说明之前插入的数据漏掉了
									actCashoutDao.save(actCashout);
							}catch(DuplicateKeyException ex) {
								actCashoutDao.update(actCashout);//处理重复插入数据的情况
								logger.warn("GTS2-{}出金数据->活动中心同步,插入消息重复,改为更新,消息内容{}",env,message);
							}
							
							logger.info("完成-GTS2-{}出金数据->活动中心同步", env);
						}
					}
				}
			} else {
				logger.warn("GTS2-{}出金数据同步没有产生记录！时间:{}", env, new Date());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("GTS2({})-出金数据kafka数据格式错误{},消息内容{}", env, e, message);
		} finally {
			//lock.unlock();
		}
	}

	/**
	 * 
	 * @MethodName: initParams
	 * @Description: 初始化kafka参数，封装对象
	 * @param gts2CloneCashinProposal
	 * @return
	 * @return ActCashout
	 */
	private ActCashout initParams(Gts2CloneCashoutProposal gts2CloneCashoutProposal) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", gts2CloneCashoutProposal.getAccountNo());
		params.put("platform", gts2CloneCashoutProposal.getPlatform());
		params.put("pno", gts2CloneCashoutProposal.getPno());

		ActCashout actCashout = actCashoutDao.findObjectByMap(params);
		if (actCashout == null) {
			actCashout = new ActCashout();
			actCashout.setCompanyId(gts2CloneCashoutProposal.getCompanyId());
			actCashout.setAccountNo(gts2CloneCashoutProposal.getAccountNo());
			actCashout.setPlatform(gts2CloneCashoutProposal.getPlatform());
			actCashout.setPno(gts2CloneCashoutProposal.getPno());
			actCashout.setCreateDate(new Date());
		}
		actCashout.setTransAmount(gts2CloneCashoutProposal.getTransAmount());
		actCashout.setUpdateDate(new Date());
		actCashout.setEnableFlag("Y");
		actCashout.setDeleteFlag("N");
		return actCashout;
	}

}
