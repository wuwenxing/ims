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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.ActTradeTypeEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.ims.message.dao.entity.ActTradeRecord;
import com.gwghk.ims.message.dao.inf.ActTradeRecordDao;
import com.gwghk.ims.message.dto.Gts2CloneTrade;
import com.gwghk.ims.message.dto.KafkaGTS2CommonData;
import com.gwghk.ims.message.utils.GwConstants;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: KafkaGts2TradeDataHandle
 * @Description: GTS2交易数据推送
 * @author lawrence
 * @date 2018年3月30日
 *
 */
@Component
public class KafkaGts2CloneTradeDataHandle {
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2CloneTradeDataHandle.class);

	//final Lock lock = new ReentrantLock();

	@Autowired
	private ActTradeRecordDao actTradeRecordDao;

	private List<Integer> dealResonData = Arrays.asList(1, 2, 4, 81, 82, 84, 8, 16, 32, 64, 128, 160, 161, 162);

	/**
	 * 功能：处理消息
	 */
	public void handleMessage(String message, String env) {
		try {
			logger.info("开始-GTS2-{}交易数据->活动中心同步！", env);
			//lock.lock();
			KafkaGTS2CommonData commonData = JsonUtil.json2Obj(message, KafkaGTS2CommonData.class);
			if (commonData != null) {
				if ((GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())
						|| GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA()))
						&& StringUtil.isNotEmpty(commonData.getN())) {

					Gts2CloneTrade gts2CloneTrade = JsonUtil.json2Obj(commonData.getN(), Gts2CloneTrade.class);
					if (StringUtil.isNotEmpty(CompanyEnum.getCodeById(gts2CloneTrade.getCompanyId()))) {
						if (dealResonData.contains(gts2CloneTrade.getDealReason())) {
							DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA,
									gts2CloneTrade.getCompanyId());
							ActTradeRecord actTradeRecord = initParams(gts2CloneTrade, env);
							try {
								if(actTradeRecord.getId()==null  || actTradeRecordDao.update(actTradeRecord)<=0)// //更新受影响的条目为零说明之前插入的数据漏掉了
									actTradeRecordDao.save(actTradeRecord);
							}catch(DuplicateKeyException ex) {
								actTradeRecordDao.update(actTradeRecord);//处理重复插入数据的情况
								logger.warn("GTS2-{}交易数据->活动中心同步,插入消息重复,改为更新,消息内容{}",env,message);
							}
							
							logger.info("完成-GTS2-{}交易数据->活动中心同步", env);
						}
					}
				}
			} else {
				logger.warn("GTS2-{}交易数据同步没有产生记录！时间:{}", env, new Date());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("GTS2({})-交易数据kafka数据格式错误{},消息内容{}", env, e, message);
		} finally {
			//lock.unlock();
		}
	}

	/**
	 * 
	 * @MethodName: initParams
	 * @Description: 初始化kafka参数，封装对象
	 * @param gts2CloneTrade
	 * @param env
	 * @return
	 * @return ActTradeRecord
	 */
	private ActTradeRecord initParams(Gts2CloneTrade gts2CloneTrade, String env) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", gts2CloneTrade.getAccountNo());
		params.put("platform", gts2CloneTrade.getPlatform());
		params.put("orderNo", gts2CloneTrade.getDealPIdInt());
		params.put("positionNo", gts2CloneTrade.getPositionIdInt());
		if ("OPEN".equals(gts2CloneTrade.getReportType())) {
			params.put("tradeType", ActTradeTypeEnum.IN.getValue());
		} else {
			params.put("tradeType", ActTradeTypeEnum.OUT.getValue());
		}
		params.put("env", env);
		ActTradeRecord actTradeRecord = actTradeRecordDao.findObjectByMap(params);
		if (actTradeRecord == null) {
			actTradeRecord = new ActTradeRecord();
			actTradeRecord.setCompanyId(gts2CloneTrade.getCompanyId());
			actTradeRecord.setAccountNo(gts2CloneTrade.getAccountNo());
			actTradeRecord.setPlatform(gts2CloneTrade.getPlatform());
		}
		actTradeRecord.setEnv(env);
		actTradeRecord.setPositionNo(gts2CloneTrade.getPositionIdInt());
		actTradeRecord.setOrderNo(gts2CloneTrade.getDealPIdInt());
		actTradeRecord.setProduct(gts2CloneTrade.getDisplayName());
		if ("OPEN".equals(gts2CloneTrade.getReportType())) {
			actTradeRecord.setTradeType(ActTradeTypeEnum.IN.getValue());
		} else {
			actTradeRecord.setTradeType(ActTradeTypeEnum.OUT.getValue());
			params.put("positionNo", gts2CloneTrade.getPositionIdInt());
			params.put("orderNo", null);
			params.put("tradeType", ActTradeTypeEnum.IN.getValue());
			ActTradeRecord openTradeRecord = actTradeRecordDao.findObjectByMap(params);
			if(openTradeRecord!=null){
				actTradeRecord.setOpenTime(openTradeRecord.getTradeTime());
			}
		}
		actTradeRecord.setTradeLot(gts2CloneTrade.getTradeVolume());
		actTradeRecord.setProfit(gts2CloneTrade.getProfit());
		actTradeRecord.setCommission(gts2CloneTrade.getCommission());
		actTradeRecord.setSwap(gts2CloneTrade.getSwap());
		Calendar tradeTime = Calendar.getInstance();
		tradeTime.setTime(gts2CloneTrade.getTradeTime());
		tradeTime.add(Calendar.HOUR, 8);
		actTradeRecord.setTradeTime(tradeTime.getTime());
		actTradeRecord.setCreateDate(new Date());
		actTradeRecord.setUpdateDate(new Date());
		return actTradeRecord;
	}

}
