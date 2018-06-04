package com.gwghk.ims.message.consumer.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.ims.message.dao.entity.Gts2Symbol;
import com.gwghk.ims.message.dao.inf.Gts2SymbolDao;
import com.gwghk.ims.message.dto.KafkaGTS2CommonData;
import com.gwghk.ims.message.utils.GwConstants;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: KafkaGts2OriSymbolDataHandle
 * @Description: GTS2交易产品数据同步
 * @author lawrence
 * @date 2017年8月17日
 *
 */
@Component
public class KafkaGts2OriSymbolDataHandle{
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2OriSymbolDataHandle.class);
	
	//final Lock lock = new ReentrantLock();

	@Autowired
	private Gts2SymbolDao gts2SymbolDao;
	
	/**
	 * 功能：处理消息
	 */
	public void handleMessage(String message,String cmp,String env){
		try{
			logger.info("开始-GTS2({})-{}交易产品数据->活动中心同步！",cmp,env);
			//lock.lock();
			KafkaGTS2CommonData commonData = JsonUtil.json2Obj(message, KafkaGTS2CommonData.class);
			if(commonData!=null){
				if((GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA()) || 
						GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA())) 
						&& StringUtil.isNotEmpty(commonData.getN())){
//					String value = commonData.getN().replace("+00", "");
					Gts2Symbol gts2symbol = JsonUtil.json2Obj(commonData.getN(),Gts2Symbol.class);
					gts2symbol.setEnv(env);
					DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, gts2symbol.getCompanyid());
					
					try {
						if(GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())){
							gts2SymbolDao.save(gts2symbol);
						}else if(GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA())){
							if (message.indexOf("\"O\":{}") > -1 || gts2SymbolDao.update(gts2symbol)<=0) //当不存在旧数据 或者 更新失败(以前漏插入一条)
								gts2SymbolDao.save(gts2symbol);
						}
					}catch(DuplicateKeyException ex) {
						gts2SymbolDao.update(gts2symbol);//处理重复插入数据的情况
						logger.warn("GTS2-{}交易产品数据->活动中心同步,插入消息重复,改为更新,消息内容{}",env,message);
					}
					
					logger.info("完成-GTS2({})-{}交易产品数据->活动中心同步",cmp,env);
				}
			}else{
				logger.warn("GTS2({})-{}交易产品数据同步没有产生记录！时间:{}",cmp,env,new Date());
			}
		}catch(Exception e){
			logger.info("GTS2({})-{}交易产品数据kafka数据格式错误{},消息内容{}",cmp,env,e,message);
		}finally{
    		//lock.unlock();
    	}
	}

}
