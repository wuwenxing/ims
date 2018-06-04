package com.gwghk.ims.message.consumer.handler;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.common.CacheKeyConstant;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.ims.message.dao.entity.ActTradeRecord;
import com.gwghk.ims.message.dao.entity.Gts2Deal;
import com.gwghk.ims.message.dao.inf.ActTradeRecordDao;
import com.gwghk.ims.message.dao.inf.Gts2DealDao;
import com.gwghk.ims.message.dto.KafkaGTS2CommonData;
import com.gwghk.ims.message.utils.GwConstants;
import com.gwghk.unify.framework.common.util.BeanUtils;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: KafkaGts2OriDealDataHandle
 * @Description: GTS2账号的交易数据同步
 * @author lawrence
 * @date 2017年7月6日
 *
 */
@Component
public class KafkaGts2OriDealDataHandle extends KafkaGts2CommonDataHandle{
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2OriDealDataHandle.class);
	
	//final Lock lock = new ReentrantLock();
	
	@Autowired
	private Gts2DealDao gts2DealDao;
	@Autowired
	private ActTradeRecordDao actTradeRecordDao;
	//数据清洗类型，默认是时间区间
	@Value("${gts2.dataclean.type:1}")
	private int dataCleanType;
	
	/**
	 * 交易原因码
	 */
	private List<Integer> dealResonData = Arrays.asList(1, 2, 4, 81, 82, 84, 8, 16, 32, 64, 128, 160, 161, 162);
	/**
	 * 功能：处理消息
	 */
	public void handleMessage(String message,String env){
		try{
			long start = System.currentTimeMillis();
			logger.info("开始-GTS2-{}交易数据->活动中心同步！",env);
			//lock.lock();
			KafkaGTS2CommonData commonData = JsonUtil.json2Obj(message, KafkaGTS2CommonData.class);
			if(commonData!=null){
				if((GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA()) || 
						GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA())) 
						&& StringUtil.isNotEmpty(commonData.getN())){
					Gts2Deal gts2DealData = JsonUtil.json2Obj(commonData.getN(),Gts2Deal.class);
					gts2DealData.setOp(commonData.getA());
					gts2DealData.setEnv(env);
					Long companyId = super.initDataManager.getGts2DealGroupCompanyId(gts2DealData.getGroupid(), env);
					
					if(StringUtil.isNotEmpty(CompanyEnum.getCodeById(companyId))){
						DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, companyId);
						
						try {
							if(GwConstants.KAFKA_GTS2_OP_INSERT.equals(gts2DealData.getOp()) 
								|| (GwConstants.KAFKA_GTS2_OP_UPDATE.equals(gts2DealData.getOp()) && gts2DealDao.update(gts2DealData)<=0) //更新受影响的条目为零说明之前插入的数据漏掉了	
							  ){
								gts2DealDao.save(gts2DealData);
							}
						}catch(DuplicateKeyException ex) {
							gts2DealDao.update(gts2DealData);//处理重复插入数据的情况
							logger.warn("GTS2-{}交易数据->活动中心同步,插入消息重复,改为更新,消息内容{}",env,message);
						}
						
						if(dataCleanType==2)//实时清洗
							saveTrade(env,gts2DealData);
						else {
							if(gts2DealData.getExectime()!=null)
								setDealCache(env, gts2DealData);
						}
						
						logger.info("完成-GTS2-{}交易数据->活动中心同步,耗时:{}ms",env,System.currentTimeMillis()-start);
					}
				}
			}else{
				logger.warn("GTS2-{}交易数据同步没有产生记录！时间:{}",env,new Date());
			}
		}catch(Exception e){
			logger.info("GTS2-{}交易数据kafka数据格式错误{},消息内容{}",env,message,e,message);
			saveKafkaData("gts2deal_demo", message);
		}finally{
			//lock.unlock();
    	}
	}
	
	/**
	 * 实时保存清洗数据
	 * @param env
	 * @param gts2Deal
	 */
	public boolean saveTrade(String env, Gts2Deal gts2Deal) {
	if (gts2Deal.getGroupid() == null) 
		return false;
	
		Long companyId = null;
		if (ActEnvEnum.REAL.getValue().equals(env)) {
			companyId = initDataManager.getGts2RealDealGroup()
					.get(CacheKeyConstant.GTS2_REAL_DEAL_GROUP_ID + gts2Deal.getGroupid());
		} else if (ActEnvEnum.DEMO.getValue().equals(env)) {
			companyId = initDataManager.getGts2DemoDealGroup()
					.get(CacheKeyConstant.GTS2_DEMO_DEAL_GROUP_ID + gts2Deal.getGroupid());
		}
		if (companyId == null) 
			return false;
		
		String cmp = CompanyEnum.getCodeById(companyId);
		if (cmp == null) 
			return false;
		
		if (!dealResonData.contains(gts2Deal.getReason().intValue())) 
			return false;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", gts2Deal.getId());
		params.put("env", ActEnvEnum.REAL.getValue());
		params.put("companyId", companyId);
		params.put("companyCode", cmp);
		ActTradeRecord actTradeRecord = actTradeRecordDao.getCleanTrade(params);
		if (actTradeRecord == null) 
			return false;
		
		int affectRow=0;
		actTradeRecord.setCompanyCode(cmp);
		actTradeRecord.setEnv(env);
		ActTradeRecord dbTrade = actTradeRecordDao.getTrade(actTradeRecord);
		if(dbTrade!=null){
			//GMT+0转8
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, 8);
			BeanUtils.copyExceptNull(dbTrade, actTradeRecord);
			dbTrade.setUpdateDate(cal.getTime());
			affectRow=actTradeRecordDao.update(dbTrade);
		}else{
			actTradeRecord.setSource("0");
			actTradeRecord.setCompanyCode(cmp);
			affectRow=actTradeRecordDao.save(actTradeRecord);
		}
		
		return affectRow>0;
	}
}
