package com.gwghk.ims.message.consumer.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.ims.message.dao.entity.Gts2BoDict;
import com.gwghk.ims.message.dao.inf.Gts2BoDictDao;
import com.gwghk.ims.message.dto.KafkaGTS2CommonData;
import com.gwghk.ims.message.utils.GwConstants;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: KafkaGts2BoDictDataHandle
 * @Description: GTS2数据字典数据同步
 * @author lawrence
 * @date 2017年8月18日
 *
 */
@Component
public class KafkaGts2OriBoDictDataHandle extends KafkaGts2CommonDataHandle{
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2OriBoDictDataHandle.class);
	
	//final Lock lock = new ReentrantLock();

	@Autowired
	private Gts2BoDictDao gts2BoDictDao;
	
	/**
	 * 功能：处理消息
	 */
	public void handleMessage(String message,String cmp,String env){
		try{
			logger.info("开始-GTS2({})-{}数据字典->活动中心同步！",cmp,env);
			//lock.lock();
			KafkaGTS2CommonData commonData = JsonUtil.json2Obj(message, KafkaGTS2CommonData.class);
			if(commonData!=null){
				if((GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA()) || 
						GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA())) 
						&& StringUtil.isNotEmpty(commonData.getN())){
//					String value = commonData.getN().replace("+00", "");
					Gts2BoDict gts2BoDict = JsonUtil.json2Obj(commonData.getN(),Gts2BoDict.class);
					gts2BoDict.setEnv(env);
					DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, gts2BoDict.getCompanyId());
					
					try {
						if(GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA()) || 
							(GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA()) && gts2BoDictDao.update(gts2BoDict)<=0) //更新受影响的条目为零说明之前插入的数据漏掉了
						  ){
							gts2BoDictDao.insert(gts2BoDict);
						}
					}catch(DuplicateKeyException ex) {
						gts2BoDictDao.update(gts2BoDict);//处理重复插入数据的情况
						logger.warn("GTS2-{}数据字典->活动中心同步,插入消息重复,改为更新,消息内容{}",env,message);
					}
					
					logger.info("完成-GTS2({})-{}数据字典->活动中心同步",cmp,env);
				}
			}else{
				logger.warn("GTS2({})-{}数据字典同步没有产生记录！时间:{}",cmp,env,new Date());
			}
		}catch(Exception e){
			logger.info("GTS2({})-{}数据字典kafka数据格式错误{},消息内容{}",cmp,env,e,message);
		}finally{
    		//lock.unlock();
    	}
	}

	
	public static void main(String[] args) {
		String value1="{\"U\":\"590d78c3-3fa6-4cdf-8dc4-70ac34552309\",\"Q\":1041,\"C\":\"2017-08-30 09:51:22.054+00\",\"A\":\"I\",\"S\":\"office\",\"T\":\"t_bo_dict\",\"N\":{\"id\":48815,\"code\":\"A7\",\"parent_code\":\"PaySwitchSeqTagCode\",\"valid\":1,\"name_cn\":\"A7\",\"name_tw\":\"A7\",\"name_en\":\"A7\",\"sort\":17,\"create_user\":\"system\",\"create_ip\":\"127.0.0.1\",\"create_date\":\"2017-08-30 09:51:22.054529+00\",\"update_user\":\"system\",\"update_ip\":\"127.0.0.1\",\"update_date\":\"2017-08-30 09:51:22.054529+00\",\"version_no\":0,\"company_id\":9}}";
		KafkaGTS2CommonData commonData = JsonUtil.json2Obj(value1, KafkaGTS2CommonData.class);
//		String value = commonData.getN().replace("+00", "");
		Gts2BoDict gts2BoDict = JsonUtil.json2Obj(commonData.getN(),Gts2BoDict.class);
		System.out.println(gts2BoDict.getCreateDate());
	
	}
}
