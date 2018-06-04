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
import com.gwghk.ims.message.dao.entity.ActRelatedCustomer;
import com.gwghk.ims.message.dao.entity.VActPrizeRecord;
import com.gwghk.ims.message.dao.inf.ActRelatedCustomerDao;
import com.gwghk.ims.message.dao.inf.VActPrizeRecordDao;
import com.gwghk.ims.message.dto.KafkaGTS2CommonData;
import com.gwghk.ims.message.redis.ActAccountPrizeRedis;
import com.gwghk.ims.message.utils.GwConstants;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: KafkaactRelatedCustomerDataHandle
 * @Description: GTS2-真实跟模拟关系数据同步
 * @author lawrence
 * @date 2018年1月9日
 *
 */
@Component
public class KafkaGts2RelatedCustomerDataHandle {
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2RelatedCustomerDataHandle.class);

	//final Lock lock = new ReentrantLock();

	@Autowired
	private ActRelatedCustomerDao actRelatedCustomerDao;

	@Autowired
	private VActPrizeRecordDao vActPrizeRecordDao;

	@Autowired
	private ActAccountPrizeRedis actAccountPrizeRedis;

	/**
	 * 功能：处理消息
	 */
	public void handleMessage(String message) {
		try {
			logger.info("开始-GTS2-真实模拟账号数据关系->活动中心同步！");
			//lock.lock();
			KafkaGTS2CommonData commonData = JsonUtil.json2Obj(message, KafkaGTS2CommonData.class);
			if (commonData != null) {
				if ((GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())
						|| GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA()))
						&& StringUtil.isNotEmpty(commonData.getN())) {
					ActRelatedCustomer actRelatedCustomer = JsonUtil.json2Obj(commonData.getN(),
							ActRelatedCustomer.class);
					if (StringUtil.isNotEmpty(CompanyEnum.getCodeById(actRelatedCustomer.getCompanyId()))) {
						DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA,
								actRelatedCustomer.getCompanyId());
						String companyCode = CompanyEnum.getCodeById(actRelatedCustomer.getCompanyId());
						actRelatedCustomer.setCompanyCode(companyCode);
						
						try {
							if (GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())) {
								insertRelatedCustomer(actRelatedCustomer);
							} else if (GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA())) {
								if (message.indexOf("\"O\":{}") > -1 || updateRelatedCustomer(actRelatedCustomer)<=0 ) //当不存在旧数据 或者 更新失败(以前漏插入一条)
									insertRelatedCustomer(actRelatedCustomer);
							}
						}catch(DuplicateKeyException ex) {
							updateRelatedCustomer(actRelatedCustomer);//处理重复插入数据的情况
							logger.warn("GTS2-真实模拟账号数据关系->活动中心同步,插入消息重复,改为更新,消息内容{}",message);
						}
						
						
						logger.info("完成-GTS2-真实模拟账号数据关系->活动中心同步");
					}
				}
			} else {
				logger.warn("GTS2真实模拟账号数据关系同步没有产生记录！时间:{}", new Date());
			}
		} catch (Exception e) {
			logger.info("GTS2真实模拟账号数据关系数据kafka数据格式错误{},消息内容{}", e,message);
		} finally {
			//lock.unlock();
		}
	}

	private int insertRelatedCustomer(ActRelatedCustomer actRelatedCustomer) {
		if (actRelatedCustomer.getCustomerNumber() != null && actRelatedCustomer.getDemoCustomerNumber() != null) {
			actRelatedCustomer.setBindDate(new Date());
			logger.info("绑定关系:{},{}", actRelatedCustomer.getCustomerNumber(),
					actRelatedCustomer.getDemoCustomerNumber());
			// pushAccountToPrizeRedis(actRelatedCustomer);
		}
		
		return actRelatedCustomerDao.save(actRelatedCustomer);
	}

	private int updateRelatedCustomer(ActRelatedCustomer actRelatedCustomer) {
		ActRelatedCustomer dbactRelatedCustomer = actRelatedCustomerDao.getById(actRelatedCustomer);
		if (dbactRelatedCustomer == null) 
			return 0;
			
		if (dbactRelatedCustomer.getCustomerNumber() == null
				|| dbactRelatedCustomer.getDemoCustomerNumber() == null) {
			if (actRelatedCustomer.getCustomerNumber() != null
					&& actRelatedCustomer.getDemoCustomerNumber() != null) {
				logger.info("绑定关系:{},{}", actRelatedCustomer.getCustomerNumber(),
						actRelatedCustomer.getDemoCustomerNumber());
				actRelatedCustomer.setBindDate(new Date());
				pushAccountToPrizeRedis(actRelatedCustomer);
			}
		}
		
		return actRelatedCustomerDao.update(actRelatedCustomer);
	}

	/**
	 * 
	 * @MethodName: pushAccountToPrizeRedis
	 * @Description: 判断绑定demo账号后的发放记录是否存在发放中状态，存在就推送数据到redis
	 * @param actRelatedCustomer
	 * @return void
	 */
	private void pushAccountToPrizeRedis(ActRelatedCustomer actRelatedCustomer) {
		String demoAccount = "" + actRelatedCustomer.getDemoCustomerNumber();
		String companyCode = actRelatedCustomer.getCompanyCode();
		//@todo  用户参与过的活动
		String actNo="";
		VActPrizeRecord vActPrizeRecord = null;//vActPrizeRecordDao.getBonusDemoPrizeIssuing(demoAccount,actNo,ActPlatformEnum.GTS2.getCode(), companyCode);
		if (vActPrizeRecord != null) {
			actAccountPrizeRedis.rPushDemoAccount(demoAccount, companyCode);
		}
	}

}
