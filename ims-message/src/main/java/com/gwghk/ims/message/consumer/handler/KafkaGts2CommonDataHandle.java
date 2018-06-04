package com.gwghk.ims.message.consumer.handler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.common.CacheKeyConstant;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.message.dao.entity.Gts2CashinProposal;
import com.gwghk.ims.message.dao.entity.Gts2CashoutProposal;
import com.gwghk.ims.message.dao.entity.Gts2Deal;
import com.gwghk.ims.message.manager.InitDataManager;
import com.gwghk.unify.framework.cache.redis.cluster.RedisCacheTemplate;



public class KafkaGts2CommonDataHandle {
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2CommonDataHandle.class);

	@Autowired
    private RedisCacheTemplate redisCacheTemplate;
	
	@Autowired
	protected InitDataManager initDataManager;

	private Short [] dealReason = {1,2,4,8, 16, 32, 64,81,82,84, 128,160, 161,162};
	/**
	 * 
	 * @MethodName: setCustomerInfoCache
	 * @Description: 缓存设置账号资料更新时间
	 * @param env 环境 real/demo
	 * @param cmp 公司代码 
	 * @param updateDate 最后更新时间
	 * @return void
	 */
	protected void setCustomerInfoCache(String env, String cmp, Date updateDate) {
		try {
			boolean continuedo = compareTwoWeek(updateDate);
			if(continuedo){
				String key = CacheKeyConstant.getCustomerInfoKey(env, cmp);
				if(key!=null){
					Calendar redisDate = (Calendar) redisCacheTemplate.getex(key);
					if (redisDate != null) {
						Calendar kafkaUpdate = Calendar.getInstance();
						kafkaUpdate.setTime(updateDate);
						// 如果redis存储的时间大于或者等于目前kafka接收数据的时间，则重置redis数据
						if (!kafkaUpdate.after(redisDate)) {
							kafkaUpdate.add(Calendar.MILLISECOND, -10);
							redisCacheTemplate.setex(key, kafkaUpdate);
							logger.info("kafka同步的数据出现延迟，重置缓存({})成功，值:({})", key,
									kafkaUpdate.getTime());
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error("重置缓存出现失败:",ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @MethodName: setCashinCache
	 * @Description: 缓存入金最后更新时间
	 * @param env 环境 real/demo
	 * @param gts2CashinProposal 入金对象
	 * @return void
	 */
	protected void setCashinCache(String env, Gts2CashinProposal gts2CashinProposal) {
		try {
			if(gts2CashinProposal.getAccountGroupId()!=null){
				Long companyId = initDataManager.getGts2AccountGroupCompanyId(gts2CashinProposal.getAccountGroupId(), env);
				if(companyId!=null){
					String cmp = CompanyEnum.getCodeById(companyId);
					if(cmp!=null){
						String key = CacheKeyConstant.getCashinKey(cmp);
						if(key!=null){
							Calendar redisDate = (Calendar) redisCacheTemplate.getex(key);
							if (redisDate != null) {
								Calendar kafkaUpdate = Calendar.getInstance();
								kafkaUpdate.setTime(gts2CashinProposal.getApproveDate());
								// 如果redis存储的时间大于或者等于目前kafka接收数据的时间，则重置redis数据
								if (!kafkaUpdate.after(redisDate)) {
									kafkaUpdate.add(Calendar.MILLISECOND, -10);
									redisCacheTemplate.setex(key, kafkaUpdate);
									logger.info("kafka同步的数据出现延迟，重置缓存({})成功，值:({})", key,
											kafkaUpdate.getTime());
								}
							}
						}
					}
				}
				
			}
		} catch (Exception ex) {
			logger.error("重置缓存出现失败:{}",ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @MethodName: setCashoutCache
	 * @Description: 缓存入金最后更新时间
	 * @param env
	 * @param gts2CashoutProposal 出金
	 * @return void
	 */
	protected void setCashoutCache(String env,Gts2CashoutProposal gts2CashoutProposal) {
		try {
			if(gts2CashoutProposal.getAccountGroupId()!=null){
				Long companyId = initDataManager.getGts2RealAccountGroup().get(
						CacheKeyConstant.GTS2_REAL_ACCOUNT_GROUP_ID+gts2CashoutProposal.getAccountGroupId());
				if(companyId!=null){
					String cmp = CompanyEnum.getCodeById(companyId);
					if(cmp!=null){
						String key = CacheKeyConstant.getCashoutKey(cmp);
						if(key!=null){
							Calendar redisDate = (Calendar) redisCacheTemplate.getex(key);
							if (redisDate != null) {
								Calendar kafkaUpdate = Calendar.getInstance();
								kafkaUpdate.setTime(gts2CashoutProposal.getApproveDate());
								// 如果redis存储的时间大于或者等于目前kafka接收数据的时间，则重置redis数据
								if (!kafkaUpdate.after(redisDate)) {
									kafkaUpdate.add(Calendar.MILLISECOND, -10);
									redisCacheTemplate.setex(key, kafkaUpdate);
									logger.info("kafka同步的数据出现延迟，重置缓存({})成功，值:({})", key,
											kafkaUpdate.getTime());
								}
							}
						}
					}
				}
				
			}
		} catch (Exception ex) {
			logger.error("重置缓存出现失败:{}",ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @MethodName: dealReasonExit
	 * @Description: 判断交易数据有效reason值
	 * @param gts2Deal
	 * @return
	 * @return boolean
	 */
	private boolean dealReasonExit(Gts2Deal gts2Deal){
		for(Short reasonItem:dealReason){
			if(reasonItem.equals(gts2Deal.getReason())){
				return true;
			}
		}
		return false;
	}
	
	public void setDealCache(String env,Gts2Deal gts2Deal){
		try {
			if(gts2Deal.getGroupid()!=null){
				if(!dealReasonExit(gts2Deal)){
					return ;
				}
				Long companyId = initDataManager.getGts2DealGroupCompanyId(gts2Deal.getGroupid(), env);				
				if(companyId!=null){
					String cmp = CompanyEnum.getCodeById(companyId);
					if(cmp!=null){
						String key = CacheKeyConstant.getDealKey(env,cmp);
						if(key!=null){
							Calendar redisDate = (Calendar) redisCacheTemplate.getex(key);
							if (redisDate != null) {
								Calendar kafkaUpdate = Calendar.getInstance();
								kafkaUpdate.setTime(gts2Deal.getExectime());
								// 如果redis存储的时间大于或者等于目前kafka接收数据的时间，则重置redis数据
								if (!kafkaUpdate.after(redisDate)) {
									kafkaUpdate.add(Calendar.MILLISECOND, -10);
									redisCacheTemplate.setex(key, kafkaUpdate);
									logger.info("kafka同步的数据出现延迟，重置缓存({})成功，值:({})", key,
											kafkaUpdate.getTime());
								}
							}
						}
					}
				}
				
			}
		} catch (Exception ex) {
			logger.error("重置缓存出现失败:{}",ex.getMessage());
		}
	}
	
	
	
	
	/**
	 * 
	 * @MethodName: saveKafkaData
	 * @Description: 将kafka接收处理失败的数据放置redis
	 * @param key
	 * @param data
	 * @return void
	 */
	protected void saveKafkaData(String key,String data){
		try{
			List<String> kafakData = (List) redisCacheTemplate.getex(key);
			if(kafakData == null){
				kafakData = new ArrayList<String>();
				redisCacheTemplate.setex(key, kafakData);
			}
			kafakData.add(data);
		}catch(Exception ex){
			logger.error("kafaka存储{}:{} 数据失败:{}",key,data,ex.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @MethodName: compareTwoWeek
	 * @Description: 比较kafka传来的数据，最后更新时间距离当前时间是否超过两个星期，如果是，则不再更新缓存
	 * @param lastUpdate
	 * @return
	 * @return boolean
	 */
	public static boolean compareTwoWeek(Date lastUpdate){
		Calendar twoWeekCal = Calendar.getInstance();
		Calendar lastUpdateCal = Calendar.getInstance();
		twoWeekCal.add(Calendar.DATE, -14);
		lastUpdateCal.setTime(lastUpdate);
		if(lastUpdateCal.compareTo(twoWeekCal)>=0){
			return true;
		}
		return false;
	}

}
