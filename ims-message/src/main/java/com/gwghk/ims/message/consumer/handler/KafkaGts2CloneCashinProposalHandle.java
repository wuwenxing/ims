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
import com.gwghk.ims.message.dao.entity.ActCashin;
import com.gwghk.ims.message.dao.inf.ActCashinDao;
import com.gwghk.ims.message.dto.Gts2CloneCashinProposal;
import com.gwghk.ims.message.dto.KafkaGTS2CommonData;
import com.gwghk.ims.message.utils.GwConstants;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: KafkaGts2CashinProposalHandle
 * @Description: GTS2入金数据处理
 * @author lawrence
 * @date 2018年4月2日
 *
 */
@Component
public class KafkaGts2CloneCashinProposalHandle {
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2CloneCashinProposalHandle.class);

	//final Lock lock = new ReentrantLock();

	@Autowired
	private ActCashinDao actCashinDao;

	private List<Integer> cusTranTypeData = Arrays.asList(100, 102);

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
					Gts2CloneCashinProposal gts2CloneCashinProposal = JsonUtil.json2Obj(commonData.getN(),
							Gts2CloneCashinProposal.class);
					if (StringUtil.isNotEmpty(CompanyEnum.getCodeById(gts2CloneCashinProposal.getCompanyId()))) {
						if (cusTranTypeData.contains(gts2CloneCashinProposal.getCusTranType())
								&& "2".equals(gts2CloneCashinProposal.getProposalStatus())) {
							DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, gts2CloneCashinProposal.getCompanyId());
						
							ActCashin actCashin = initParams(gts2CloneCashinProposal);
							
							try {
								if(actCashin.getId()==null || actCashinDao.update(actCashin)<=0)//更新受影响的条目为零说明之前插入的数据漏掉了
									actCashinDao.save(actCashin);
							}catch(DuplicateKeyException ex) {
								actCashinDao.update(actCashin);//处理重复插入数据的情况
								logger.warn("GTS2-{}入金数据->活动中心同步,插入消息重复,改为更新,消息内容{}",env,message);
							}
							logger.info("完成-GTS2-{}入金数据->活动中心同步", env);
						}
					}
				}
			} else {
				logger.warn("GTS2-{}入金数据同步没有产生记录！时间:{}", env, new Date());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("GTS2({})-入金数据kafka数据格式错误{},消息内容{}", env, e, message);
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
	 * @return ActCashin
	 */
	private ActCashin initParams(Gts2CloneCashinProposal gts2CloneCashinProposal) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("accountNo", gts2CloneCashinProposal.getAccountNo());
		params.put("platform", gts2CloneCashinProposal.getPlatform());
		params.put("pno", gts2CloneCashinProposal.getPno());
		
		ActCashin actCashin = actCashinDao.findObjectByMap(params);
		if(actCashin == null){
			actCashin = new ActCashin();
			actCashin.setCompanyId(gts2CloneCashinProposal.getCompanyId());
			actCashin.setAccountNo(gts2CloneCashinProposal.getAccountNo());
			actCashin.setPlatform(gts2CloneCashinProposal.getPlatform());
			actCashin.setPno(gts2CloneCashinProposal.getPno());
			actCashin.setCreateDate(new Date());
		}
		actCashin.setTransAmount(gts2CloneCashinProposal.getTransAmount());
		actCashin.setApproveDate(gts2CloneCashinProposal.getApproveDate());
		actCashin.setUpdateDate(new Date());
		actCashin.setEnableFlag("Y");
		actCashin.setDeleteFlag("N");
		return actCashin;
	}

}
