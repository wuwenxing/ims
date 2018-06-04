package com.gwghk.ims.message.consumer.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.ims.message.dao.entity.Gts2AccountGroup;
import com.gwghk.ims.message.dao.inf.Gts2AccountGroupDao;
import com.gwghk.ims.message.dto.KafkaGTS2CommonData;
import com.gwghk.ims.message.manager.InitDataManager;
import com.gwghk.ims.message.utils.GwConstants;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: KafkaGts2OriAccountGroupDataHandle
 * @Description: GTS2账号平台组数据同步
 * @author lawrence
 * @date 2017年8月14日
 *
 */
@Component
public class KafkaGts2OriAccountGroupDataHandle extends KafkaGts2CommonDataHandle{
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2OriAccountGroupDataHandle.class);
	
	//final Lock lock = new ReentrantLock();

	@Autowired
	private Gts2AccountGroupDao gts2AccountGroupMapper;
		
	@Autowired
	private InitDataManager initDataManager;
	/**
	 * 功能：处理消息
	 */
	public void handleMessage(String message,String cmp,String env){
		try{
			logger.info("开始-GTS2({})-{}账号平台组数据->活动中心同步！",cmp,env);
			//lock.lock();
			KafkaGTS2CommonData commonData = JsonUtil.json2Obj(message, KafkaGTS2CommonData.class);
			if(commonData!=null){
				if((GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA()) || 
						GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA())) 
						&& StringUtil.isNotEmpty(commonData.getN())){
					Gts2AccountGroup gts2AccountGroup = JsonUtil.json2Obj(commonData.getN(),Gts2AccountGroup.class);
					gts2AccountGroup.setEnv(env);
					DataSourceContext.setCompany(DataSourceTypeEnum.BASE_DATA,null);
					saveOrUpdate(message, env,commonData, gts2AccountGroup);
					DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA,gts2AccountGroup.getCompanyId());
					saveOrUpdate(message, env,commonData, gts2AccountGroup);
					if(gts2AccountGroup.getId()!=null && gts2AccountGroup.getCompanyId()!=null){
						if(ActEnvEnum.DEMO.getValue().equals(env)){
							initDataManager.resetGts2DemoAccountGroup(gts2AccountGroup.getId(), 
									gts2AccountGroup.getCompanyId());
						}else if(ActEnvEnum.REAL.getValue().equals(env)){
							initDataManager.resetGts2RealAccountGroup(gts2AccountGroup.getId(), 
									gts2AccountGroup.getCompanyId());
						}
					}
					logger.info("完成-GTS2({})-{}账号平台组数据->活动中心同步",cmp,env);
				}
			}else{
				logger.warn("GTS2({})-{}账号平台组数据同步没有产生记录！时间:{}",cmp,env,new Date());
			}
		}catch(Exception e){
			logger.info("GTS2({})-{}账号平台组数据kafka数据格式错误{},消息内容{}",cmp,env,e,message);
		}finally{
    		//lock.unlock();
    	}
	}
	private void saveOrUpdate(String message,String env,KafkaGTS2CommonData commonData, Gts2AccountGroup gts2AccountGroup) {
		try {
			if(GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())){
				gts2AccountGroupMapper.save(gts2AccountGroup);
			}else if(GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA())){
				if(message.indexOf("\"O\":{}")>-1 || gts2AccountGroupMapper.update(gts2AccountGroup)<=0) //当不存在旧数据 或者 更新失败(以前漏插入一条)
					gts2AccountGroupMapper.save(gts2AccountGroup);
			}
		}catch(DuplicateKeyException ex) {
			gts2AccountGroupMapper.update(gts2AccountGroup);//处理重复插入数据的情况
			logger.warn("GTS2-{}账号平台组数据->活动中心同步,插入消息重复,改为更新,消息内容{}",env,message);
		}
	}

}
