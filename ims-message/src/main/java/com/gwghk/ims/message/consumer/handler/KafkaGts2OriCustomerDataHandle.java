package com.gwghk.ims.message.consumer.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.ims.message.dao.entity.Gts2AccountInfo;
import com.gwghk.ims.message.dao.entity.Gts2Customer;
import com.gwghk.ims.message.dao.inf.Gts2CustomerDao;
import com.gwghk.ims.message.dto.KafkaGTS2CommonData;
import com.gwghk.ims.message.utils.GwConstants;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: KafkaGts2OriCustomerDataHandle
 * @Description: GTS2账号基础资料数据同步
 * @author lawrence
 * @date 2017年8月14日
 *
 */
@Component
public class KafkaGts2OriCustomerDataHandle extends KafkaGts2CommonDataHandle{
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2OriCustomerDataHandle.class);
	
	//final Lock lock = new ReentrantLock();

	@Autowired
	private Gts2CustomerDao gts2CustomerDao;
	
	/**
	 * 功能：处理消息
	 */
	public void handleMessage(String message,String cmp,String env){
		try{
			logger.info("开始-GTS2({})-{}账号基础资料数据->活动中心同步！",cmp,env);
			//lock.lock();
			KafkaGTS2CommonData commonData = JsonUtil.json2Obj(message, KafkaGTS2CommonData.class);
			if(commonData!=null){
				if((GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA()) || 
						GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA())) 
						&& StringUtil.isNotEmpty(commonData.getN())){
//					String value = commonData.getN().replace("+00", "");
					Gts2Customer gts2Customer = JsonUtil.json2Obj(commonData.getN(),Gts2Customer.class);
					gts2Customer.setEnv(env);
					
					DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, gts2Customer.getCompanyId());
					
					try {
						if(GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())){
							gts2CustomerDao.save(gts2Customer);
						}else if(GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA())){
							if(message.indexOf("\"O\":{}")>-1 || gts2CustomerDao.update(gts2Customer)<=0) //当不存在旧数据 或者 更新失败(以前漏插入一条)
								gts2CustomerDao.save(gts2Customer);
						}
					}catch(DuplicateKeyException ex) {
						gts2CustomerDao.update(gts2Customer);//处理重复插入数据的情况
						logger.warn("GTS2-{}账号基础资料数据->活动中心同步,插入消息重复,改为更新,消息内容{}",env,message);
					}
					
					setCustomerInfoCache(env, cmp,gts2Customer.getUpdateDate());
					logger.info("完成-GTS2({})-{}账号基础资料数据->活动中心同步",cmp,env);
				}
			}else{
				logger.warn("GTS2({})-{}账号基础资料数据同步没有产生记录！时间:{}",cmp,env,new Date());
			}
		}catch(Exception e){
			logger.info("GTS2({})-{}账号基础资料数据kafka数据格式错误{},消息内容{}",cmp,env,e,message);
		}finally{
    		//lock.unlock();
    	}
	}
	
	
	public static void main(String[] args) {
		String value = "{\"id\":47169,\"company_id\":8,\"gts2_customer_id\":250326311,\"gts2_account_id\":null}";
		Gts2AccountInfo gts2AccountInfo = JsonUtil.json2Obj(value, Gts2AccountInfo.class);
		System.out.println(gts2AccountInfo.getGts2CustomerId());
	}

}
