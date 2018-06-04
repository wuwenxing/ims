package com.gwghk.ims.datacleaning.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.common.enums.ActAccountStatusEnum;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.common.enums.SyncDataUpdateTypeEnum;
import com.gwghk.ims.datacleaning.dao.entity.ActCashin;
import com.gwghk.ims.datacleaning.dao.entity.ActCashout;
import com.gwghk.ims.datacleaning.dao.entity.ActCustomerInfo;
import com.gwghk.ims.datacleaning.dao.entity.ActCustomerInfoDisabled;
import com.gwghk.ims.datacleaning.dao.entity.ActSyncDataUpdate;
import com.gwghk.ims.datacleaning.dao.entity.ActTradeRecord;
import com.gwghk.ims.datacleaning.dao.entity.ImsBlackList;
import com.gwghk.ims.datacleaning.dao.inf.base.ActCashinDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ActCashoutDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ActCustomerInfoDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ActCustomerInfoDisabledDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ActSyncDataUpdateDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ActTradeRecordDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ImsBlackListDao;
import com.gwghk.ims.datacleaning.redis.ActCustomerRedis;
import com.gwghk.ims.datacleaning.utils.CacheKeyConstant;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.unify.framework.cache.redis.cluster.RedisCacheTemplate;
import com.gwghk.unify.framework.common.util.DateUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：HX-MT4数据清洗业务处理类
 * 
 * @author lawrence
 * @version 1.0
 * @Date 2017年5月22日
 */
@Component
@Transactional(value = "transactionManager")
public class GTS2DataCleanManager {
	private Logger logger = LoggerFactory.getLogger(GTS2DataCleanManager.class);
	
	@Autowired
	private ActCustomerInfoDao actCustomerInfoMapper;

	@Autowired
	private ActTradeRecordDao actTradeRecordMapper;

	@Autowired
	private ActCashinDao actCashinMapper;

	@Autowired
	private ActCashoutDao actCashoutMapper;

	@Autowired
	private ActSyncDataUpdateDao actSyncDataUpdateMapper;

	// @Autowired
	// private ActDuplicateAccountInfoMapper actDuplicateAccountInfoMapper;

	@Autowired
	private RedisCacheTemplate redisCacheTemplate;

	@Autowired
	private ActCustomerRedis actCustomerRedis;

	@Autowired
	private ActCustomerInfoDisabledDao actCustomerInfoDisabledMapper;

	@Autowired
	private ImsBlackListDao imsBlackListDao;
	/**
	 * 
	 * @MethodName: mergeData
	 * @Description: GTS2数据映射共用方法
	 * @param mergeBusinese
	 *            外部处理方法
	 * @param syncDataUpdateType
	 *            同步数据类型
	 * @return void
	 */
	@Transactional(value = "transactionManager", propagation = Propagation.NOT_SUPPORTED)
	public void mergeData(GTS2MergeBusiness mergeBusinese, SyncDataUpdateTypeEnum syncDataUpdateType, Long companyId,
			String companyCode,String key) {
		boolean exitSynTime = false;
		Calendar paramLastUpdateTime = null;
		Calendar syncUpdateTime = null;

		ActSyncDataUpdate itemActSyncDataUpdate = null;
		List<ActSyncDataUpdate> listActSyncDataUpdate = actSyncDataUpdateMapper.findBySyncType(syncDataUpdateType.getValue());
		
		Calendar cacheCal = getCache(key);
		if (listActSyncDataUpdate != null && !listActSyncDataUpdate.isEmpty()) {
			exitSynTime = true;
			itemActSyncDataUpdate = listActSyncDataUpdate.get(0);
			paramLastUpdateTime = Calendar.getInstance();
			paramLastUpdateTime.setTime(itemActSyncDataUpdate.getLastUpdateTime());
			if (cacheCal != null) {
				// 如果redis缓存数据的时间相对于db存储数据时间更早
				if (!cacheCal.after(paramLastUpdateTime)) {
					paramLastUpdateTime = cacheCal;
				}
			}
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("companyCode", companyCode);
		data.put("companyId", companyId);
		if (paramLastUpdateTime != null) {
			data.put("lastUpdateDate", paramLastUpdateTime.getTime());
			Calendar calendarEnd = Calendar.getInstance();
			calendarEnd.add(Calendar.SECOND, -30);
			calendarEnd.add(Calendar.HOUR_OF_DAY, -8);
			data.put("lastUpdateDateEnd", calendarEnd.getTime());
			logger.info("{}时间范围数据:{} ~ {}", syncDataUpdateType.getLabelKey(), paramLastUpdateTime.getTime(),
					calendarEnd.getTime());
		} else {
			// 非客户资料数据清先
			if (syncDataUpdateType.getValue().indexOf("Customer") < 0) {
				setCache(key,null);
				DateUtil dateUtil = DateUtil.getInstance(8);
				data.put("lastUpdateDate", dateUtil.getBeginTimeOfDay(new Date()));
				data.put("lastUpdateDateEnd", dateUtil.getEndTimeOfDay(new Date()));
				logger.info("{}时间范围数据:{} ~ {}", syncDataUpdateType.getLabelKey(), data.get("lastUpdateDate"),
						data.get("lastUpdateDateEnd"));
			}
		}
		// 执行合并数据
		int mergeCount = mergeBusinese.doMerge(data);
		// 如果本次执行，清洗的数据没有任何变化时，则不再执行之后的逻辑
		if (mergeCount == 0) {
			return;
		}

		// 获取清洗后数据最后一条更新记录
		Date lastUpdate = mergeBusinese.selectLastUpdateTime();

		if (lastUpdate != null) {
			syncUpdateTime = Calendar.getInstance();
			syncUpdateTime.setTime(lastUpdate);

			// GTS2的DB为GMT+0，推后多10，防止数据同步出现遗漏
			syncUpdateTime.add(Calendar.HOUR_OF_DAY, -8);
			// paramLastUpdateTime.add(Calendar.MILLISECOND, -10);

			if (!exitSynTime) {
				itemActSyncDataUpdate = new ActSyncDataUpdate();
				itemActSyncDataUpdate.setSyncType(syncDataUpdateType.getValue());
				itemActSyncDataUpdate.setEnableFlag("Y");
				itemActSyncDataUpdate.setDeleteFlag("N");
				itemActSyncDataUpdate.setCreateDate(new Date());
			}

			// 当缓存为空时或者这次同步时所使用的是缓存的同步时间,则将已经同步的数据最新更新时间设置至缓存中
			Calendar retrieveCache = getCache(key);
			if (retrieveCache == null || (paramLastUpdateTime!=null && paramLastUpdateTime.equals(retrieveCache))) {
				setCache(key,syncUpdateTime);
			}
			itemActSyncDataUpdate.setLastUpdateTime(syncUpdateTime.getTime());
			itemActSyncDataUpdate.setUpdateDate(new Date());
			itemActSyncDataUpdate.setCompanyId(syncDataUpdateType.getCompanyId());
			if (exitSynTime) {
				actSyncDataUpdateMapper.update(itemActSyncDataUpdate);
			} else {
				actSyncDataUpdateMapper.save(itemActSyncDataUpdate);
			}
		}
	}

	// public void setMissDataParam(Map<String,Object> data){
	// Calendar lastUpdateDateBegin = DateUtil.getGMT0Calendar();
	// Calendar lastUpdateDateEnd = DateUtil.getGMT0Calendar();
	// lastUpdateDateEnd.add(Calendar.HOUR, -1);
	// lastUpdateDateBegin.add(Calendar.HOUR, -25);
	// data.put("lastUpdateDateBegin", lastUpdateDateBegin.getTime());
	// data.put("lastUpdateDateEnd", lastUpdateDateEnd.getTime());
	// }

	/**
	 * 
	 * @MethodName: mergeRealActCustomerInfo
	 * @Description: 将GTS2真实客户资料数据清洗至活动中心真实客户资料表
	 * @param companyCode
	 *            公司名
	 * @param companyId
	 *            公司ID
	 * @return void
	 * @throws Exception 
	 */
	@Transactional(value = "transactionManager", propagation = Propagation.NOT_SUPPORTED)
	public void mergeRealActCustomerInfo(Long companyId, String companyCode, SyncDataUpdateTypeEnum syncDataUpdateType) throws Exception {
		//设置公司所对应的数据源
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, companyId);
		
		logger.info(">>>>>>>>>>>>开始{}", syncDataUpdateType.getLabelKey());
		String key = CacheKeyConstant.getCustomerInfoKey(ActEnvEnum.REAL.getValue(), companyCode);
		mergeData(new GTS2MergeBusiness() {
			@Override
			public Date selectLastUpdateTime() {
				ActCustomerInfo actCustomerInfo = actCustomerInfoMapper
						.selectLastActCustomerInfo(ActEnvEnum.REAL.getValue(), "0", companyCode);
				return actCustomerInfo != null ? actCustomerInfo.getUpdateDate() : null;
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public int doMerge(Map data) {
				data.put("synDate", DateUtil.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				int updateCount = actCustomerInfoMapper.insertGts2Real(data);
				logger.info("执行{},更新数据量:{}", syncDataUpdateType.getLabelKey(), updateCount);
				if(updateCount>500){
					logger.info("{}-{}批量更新集团黑名单标识",companyCode,ActEnvEnum.REAL.getValue());
					actCustomerInfoDisabledMapper.insertDisabledIdNumber(companyCode);
					actCustomerInfoDisabledMapper.insertDisabledMobile(companyCode);
					ActCustomerInfo actCustomerInfo = new ActCustomerInfo();
					actCustomerInfo.setCompanyCode(companyCode);
					actCustomerInfo.setEnv(ActEnvEnum.REAL.getValue());
					actCustomerInfoMapper.updateGroupBlack(actCustomerInfo);
				}else{
					List<ActCustomerInfo> actCustomerInfos = actCustomerInfoMapper.selectRealData(data);
					try {
						for (ActCustomerInfo itemCustomerInfo : actCustomerInfos) {
							itemCustomerInfo.setEnv(ActEnvEnum.REAL.getValue());
							actCustomerRedis.resetCustomerInfo(itemCustomerInfo);
							if (ActAccountStatusEnum.D.getCode().equals(itemCustomerInfo.getAccountStatus())) {
								if (StringUtil.isNotEmpty(itemCustomerInfo.getIdNumber())) {
									saveActCustomerInfoDisalbed(companyCode, "id_number", itemCustomerInfo.getIdNumber());
								}
								if (StringUtil.isNotEmpty(itemCustomerInfo.getMobile())) {
									saveActCustomerInfoDisalbed(companyCode, "mobile", itemCustomerInfo.getMobile());
								}
							}
							ImsBlackList imsBlackList = imsBlackListDao
									.select(itemCustomerInfo.getAccountNo());
							if (imsBlackList != null) {
								itemCustomerInfo.setCompanyCode(companyCode);
								itemCustomerInfo.setAccountBlack("Y");
								itemCustomerInfo.setEnv(ActEnvEnum.REAL.getValue());
								actCustomerInfoMapper.update(itemCustomerInfo);
							}
						}

					} catch (Exception ex) {
						logger.error(ex.getMessage(), ex);
					}
					logger.info("结束{}重置真实客户资料缓存", companyCode);
				}
				
				return updateCount;
			}

			private void saveActCustomerInfoDisalbed(String companyCode, String type, String dataValue) {
				ActCustomerInfoDisabled actCustomerInfoDisabled = new ActCustomerInfoDisabled();
				actCustomerInfoDisabled = new ActCustomerInfoDisabled();
				actCustomerInfoDisabled.setCompanyCode(companyCode);
				actCustomerInfoDisabled.setType(type);
				actCustomerInfoDisabled.setDataValue(dataValue);
				actCustomerInfoDisabled.setCreateDate(new Date());
				actCustomerInfoDisabled.setUpdateDate(new Date());
				actCustomerInfoDisabledMapper.save(actCustomerInfoDisabled);
			}
		}, syncDataUpdateType, companyId, companyCode,key);
		logger.info("<<<<<<<<<<<<结束{}", syncDataUpdateType.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: mergeMissRealActCustomerInfo
	 * @Description: 清洗GTS2真实用户到活动平台真实客户资料表(数据丢失部分,备用)
	 * @param paramData
	 * @param companyCode
	 * @return void
	 * @throws Exception 
	 */
	public void mergeMissRealActCustomerInfo(Map<String, Object> paramData, String companyCode) throws Exception {
		logger.info(">>>>>>>>>>>>开始清洗客户资料数据【{}(Gts2)-真实->活动中心】(数据丢失部分,备用)", companyCode);
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, CompanyEnum.findByName(companyCode).getLongId());
		actCustomerInfoMapper.insertGts2Real(paramData);
		logger.info("{}-{}批量更新集团黑名单标识",companyCode,ActEnvEnum.REAL.getValue());
		actCustomerInfoDisabledMapper.insertDisabledIdNumber(companyCode);
		actCustomerInfoDisabledMapper.insertDisabledMobile(companyCode);
		ActCustomerInfo actCustomerInfo = new ActCustomerInfo();
		actCustomerInfo.setCompanyCode(companyCode);
		actCustomerInfo.setEnv(ActEnvEnum.REAL.getValue());
		actCustomerInfoMapper.updateGroupBlack(actCustomerInfo);
		logger.info("<<<<<<<<<<<<结束清洗客户资料数据【{}(Gts2)-真实->活动中心】(数据丢失部分,备用)", companyCode);
	}

	/**
	 * 
	 * @MethodName: mergeDemoActCustomerInfo
	 * @Description: 将GTS2模拟客户资料数据清洗至活动中心模拟客户资料表
	 * @param companyId
	 * @param companyCode
	 * @param syncDataUpdateType
	 * @return void
	 * @throws Exception 
	 */
	@Transactional(value = "transactionManager", propagation = Propagation.NOT_SUPPORTED)
	public void mergeDemoActCustomerInfo(Long companyId, String companyCode, SyncDataUpdateTypeEnum syncDataUpdateType) throws Exception {
		logger.info(">>>>>>>>>>>>开始{}", syncDataUpdateType.getLabelKey());
		
		//设置公司所对应的数据源
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, companyId);
		
		String key = CacheKeyConstant.getCustomerInfoKey(ActEnvEnum.DEMO.getValue(), companyCode);

		mergeData(new GTS2MergeBusiness() {
			@Override
			public Date selectLastUpdateTime() {
				ActCustomerInfo actCustomerInfo = actCustomerInfoMapper
						.selectLastActCustomerInfo(ActEnvEnum.DEMO.getValue(), "0", companyCode);
				return actCustomerInfo != null ? actCustomerInfo.getUpdateDate() : null;
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public int doMerge(Map data) {
				data.put("synDate", DateUtil.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				int updateCount = actCustomerInfoMapper.insertGts2Demo(data);
				logger.info("执行{},更新数据量:{}", syncDataUpdateType.getLabelKey(), updateCount);
				if(updateCount>500){
					logger.info("{}-{}批量更新集团黑名单标识",companyCode,ActEnvEnum.DEMO.getValue());
					ActCustomerInfo actCustomerInfo = new ActCustomerInfo();
					actCustomerInfo.setCompanyCode(companyCode);
					actCustomerInfo.setEnv(ActEnvEnum.DEMO.getValue());
					actCustomerInfoMapper.updateGroupBlack(actCustomerInfo);
				}else{
					List<ActCustomerInfo> actCustomerInfos = actCustomerInfoMapper.selectDemoData(data);
					try {
						for (ActCustomerInfo itemCustomerInfo : actCustomerInfos) {
							itemCustomerInfo.setEnv(ActEnvEnum.DEMO.getValue());
							actCustomerRedis.resetCustomerInfo(itemCustomerInfo);
						}
					} catch (Exception ex) {
						logger.error(ex.getMessage(), ex);
					} 
				}

				logger.info("结束{}重置模拟客户资料缓存", companyCode);
			
				return updateCount;
			}

		}, syncDataUpdateType, companyId, companyCode,key);
		logger.info("<<<<<<<<<<<<结束{}", syncDataUpdateType.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: mergeMissDemoActCustomerInfo
	 * @Description: 将GTS2真实用户映射到活动平台真实客户资料表(防止数据丢失)
	 * @param paramData
	 * @param companyCode
	 * @return void
	 * @throws Exception 
	 */
	public void mergeMissDemoActCustomerInfo(Map<String, Object> paramData, String companyCode) throws Exception {
		logger.info(">>>>>>>>>>>>开始清洗客户资料数据【{}(Gts2)-模拟->活动中心】(数据丢失部分,备用)", companyCode);
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, CompanyEnum.findByName(companyCode).getLongId());
		actCustomerInfoMapper.insertGts2Demo(paramData);
		logger.info("<<<<<<<<<<<<结束清洗客户资料数据【{}(Gts2)-模拟->活动中心】(数据丢失部分,备用)", companyCode);
	}

	/**
	 * 
	 * @MethodName: mergeRealActTradeRecord
	 * @Description: 将GTS2真实客户交易数据清洗至活动中心真实客户交易表
	 * @param companyId
	 * @param companyCode
	 * @param syncDataUpdateType
	 * @return void
	 * @throws Exception 
	 */
	@Transactional(value = "transactionManager", propagation = Propagation.NOT_SUPPORTED)
	public void mergeRealActTradeRecord(Long companyId, String companyCode, SyncDataUpdateTypeEnum syncDataUpdateType) throws Exception {
		logger.info(">>>>>>>>>>>>开始{}", syncDataUpdateType.getLabelKey());
		//设置公司所对应的数据源
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, companyId);
		String key = CacheKeyConstant.getDealKey(ActEnvEnum.REAL.getValue(), companyCode);
		mergeData(new GTS2MergeBusiness() {
			@Override
			public Date selectLastUpdateTime() {
				ActTradeRecord actTradeRecord = actTradeRecordMapper.selectLastActTradeRecord(ActEnvEnum.REAL.getValue(),
						"0", companyCode);
				return actTradeRecord != null ? actTradeRecord.getTradeTime() : null;
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public int doMerge(Map data) {
				data.put("env", ActEnvEnum.REAL.getValue());
				int updateCount = actTradeRecordMapper.insertGts2Trade(data);
				logger.info("执行{},更新数据量:{}", syncDataUpdateType.getLabelKey(), updateCount);
				return updateCount;
			}

			
		}, syncDataUpdateType, companyId, companyCode,key);
		logger.info("<<<<<<<<<<<<结束{}", syncDataUpdateType.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: mergeMissRealActTradeRecord
	 * @Description: 将GTS2真实用户交易数据映射到活动平台真实客户交易表(防止数据丢失)
	 * @param paramData
	 * @param companyCode
	 * @return void
	 * @throws Exception 
	 */
	public void mergeMissRealActTradeRecord(Map<String, Object> paramData, String companyCode) throws Exception {
		logger.info(">>>>>>>>>>>>开始清洗客户交易数据【{}(Gts2)-真实->活动中心】(数据丢失部分,备用)", companyCode);
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, CompanyEnum.findByName(companyCode).getLongId());
		paramData.put("env", ActEnvEnum.REAL.getValue());
		actTradeRecordMapper.insertGts2Trade(paramData);
		logger.info("<<<<<<<<<<<<结束清洗客户交易数据【{}(Gts2)-真实->活动中心】(数据丢失部分,备用)", companyCode);
	}

	/**
	 * 
	 * @MethodName: mergeDemoActTradeRecord
	 * @Description: 将GTS2模拟客户交易数据清洗至活动中心模拟客户交易表
	 * @param companyId
	 * @param companyCode
	 * @param syncDataUpdateType
	 * @return void
	 * @throws Exception 
	 */
	@Transactional(value = "transactionManager", propagation = Propagation.NOT_SUPPORTED)
	public void mergeDemoActTradeRecord(Long companyId, String companyCode, SyncDataUpdateTypeEnum syncDataUpdateType) throws Exception {
		logger.info(">>>>>>>>>>>>开始{}", syncDataUpdateType.getLabelKey());
		//设置公司所对应的数据源
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, companyId);
		String key = CacheKeyConstant.getDealKey(ActEnvEnum.DEMO.getValue(), companyCode);
		mergeData(new GTS2MergeBusiness() {
			@Override
			public Date selectLastUpdateTime() {
				ActTradeRecord actTradeRecord = actTradeRecordMapper.selectLastActTradeRecord(ActEnvEnum.DEMO.getValue(),
						"0", companyCode);
				return actTradeRecord != null ? actTradeRecord.getTradeTime() : null;
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public int doMerge(Map data) {
				data.put("env", ActEnvEnum.DEMO.getValue());
				int updateCount = actTradeRecordMapper.insertGts2Trade(data);
				logger.info("执行{},更新数据量:{}", syncDataUpdateType.getLabelKey(), updateCount);
				return updateCount;
			}

		}, syncDataUpdateType, companyId, companyCode,key);
		logger.info("<<<<<<<<<<<<结束{}", syncDataUpdateType.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: mergeMissDemoActTradeRecord
	 * @Description: 将GTS2模拟用户交易数据映射到活动平台模拟客户交易表(防止数据丢失)
	 * @param paramData
	 * @param companyCode
	 * @return void
	 * @throws Exception 
	 */
	public void mergeMissDemoActTradeRecord(Map<String, Object> paramData, String companyCode) throws Exception {
		logger.info(">>>>>>>>>>>>开始清洗客户交易数据【{}(Gts2)-模拟->活动中心】(数据丢失部分,备用)", companyCode);
		paramData.put("env", ActEnvEnum.DEMO.getValue());
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, CompanyEnum.findByName(companyCode).getLongId());
		actTradeRecordMapper.insertGts2Trade(paramData);
		logger.info("<<<<<<<<<<<<结束清洗客户交易数据【{}(Gts2)-模拟->活动中心】(数据丢失部分,备用)", companyCode);
	}

	/**
	 * 
	 * @MethodName: mergeRealActCashin
	 * @Description: 将GTS2真实客户入金数据清洗至活动中心真实客户入金表
	 * @param companyId
	 * @param companyCode
	 * @param syncDataUpdateType
	 * @return void
	 * @throws Exception 
	 */
	@Transactional(value = "transactionManager", propagation = Propagation.NOT_SUPPORTED)
	public void mergeRealActCashin(Long companyId, String companyCode, SyncDataUpdateTypeEnum syncDataUpdateType) throws Exception {
		logger.info(">>>>>>>>>>>>开始{}", syncDataUpdateType.getLabelKey());
		//设置公司所对应的数据源
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, companyId);
		String key = CacheKeyConstant.getCashinKey(companyCode);
		mergeData(new GTS2MergeBusiness() {
			@Override
			public Date selectLastUpdateTime() {
				ActCashin actCashin = actCashinMapper.selectLastActCashinRecord("0", companyCode);
				return actCashin != null ? actCashin.getApproveDate() : null;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public int doMerge(Map data) {
				int updateCount = actCashinMapper.insertGts2(data);
				logger.info("执行{},更新数据量:{}", syncDataUpdateType.getLabelKey(), updateCount);
				return updateCount;
			}

		}, syncDataUpdateType, companyId, companyCode,key);
		logger.info("<<<<<<<<<<<<结束{}", syncDataUpdateType.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: mergeMissRealActCashin
	 * @Description: 将GTS2真实用户入金数据映射到活动平台真实用户入金表(防止数据丢失)
	 * @param paramData
	 * @param companyCode
	 * @return void
	 * @throws Exception 
	 */
	public void mergeMissRealActCashin(Map<String, Object> paramData, String companyCode) throws Exception {
		logger.info(">>>>>>>>>>>>开始清洗客户入金数据【{}(Gts2)->活动中心】(数据丢失部分,备用)", companyCode);
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, CompanyEnum.findByName(companyCode).getLongId());
		actCashinMapper.insertGts2(paramData);
		logger.info("<<<<<<<<<<<<结束清洗客户入金数据【{}(Gts2)->活动中心】(数据丢失部分,备用)", companyCode);
	}

	/**
	 * 
	 * @MethodName: mergeRealActCashout
	 * @Description: 将GTS2真实用户出金数据映射到活动平台真实用户出金表
	 * @return void
	 * @throws Exception 
	 */
	@Transactional(value = "transactionManager", propagation = Propagation.NOT_SUPPORTED)
	public void mergeRealActCashout(Long companyId, String companyCode, SyncDataUpdateTypeEnum syncDataUpdateType) throws Exception {
		logger.info(">>>>>>>>>>>>开始{}", syncDataUpdateType.getLabelKey());
		//设置公司所对应的数据源
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, companyId);
		String key = CacheKeyConstant.getCashoutKey(companyCode);
		mergeData(new GTS2MergeBusiness() {
			@Override
			public Date selectLastUpdateTime() {
				ActCashout actCashout = actCashoutMapper.selectLastActCashoutRecord(companyCode);
				return actCashout != null ? actCashout.getApproveDate() : null;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public int doMerge(Map data) {
				int updateCount = actCashoutMapper.insertGts2(data);
				logger.info("执行{},更新数据量:{}", syncDataUpdateType.getLabelKey(), updateCount);
				return updateCount;
			}

		}, syncDataUpdateType, companyId, companyCode,key);
		logger.info("<<<<<<<<<<<<结束{}", syncDataUpdateType.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: mergeMissRealActCashout
	 * @Description: 将GTS2真实用户出金数据映射到活动平台真实用户出金表(防止数据丢失)
	 * @param paramData
	 * @param companyCode
	 * @return void
	 * @throws Exception 
	 */
	public void mergeMissRealActCashout(Map<String, Object> paramData, String companyCode) throws Exception {
		logger.info(">>>>>>>>>>>>开始清洗客户出金数据【{}(Gts2)->活动中心】(数据丢失部分,备用)", companyCode);
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, CompanyEnum.findByName(companyCode).getLongId());
		actCashoutMapper.insertGts2(paramData);
		logger.info("<<<<<<<<<<<<结束清洗客户出金数据【{}(Gts2)->活动中心】(数据丢失部分,备用)", companyCode);
	}
	
	public Calendar getCache(String key) {
		Calendar cacheValue = null;
		if (key != null) {
			try {
				cacheValue = (Calendar) redisCacheTemplate.getex(key);
			} catch (Exception e) {
				logger.error("缓存({})获取失败{}:{}", key, e.getMessage());
			}
			return cacheValue;
		}
		return null;
	}

	public void setCache(String key,Calendar cal) {
		try {
			if (key != null) {
				redisCacheTemplate.setex(key, cal);
				logger.info("重置缓存({})成功，值:({})", key, cal.getTime());
			}
		} catch (Exception e) {
			logger.error("重置缓存({})失败:{}", key, e.getMessage());
		}

	}

	public static void main(String[] args) {
		System.out.println(DateUtil.getInstance(8).getBeginTimeOfDay(new Date()));
		System.out.println(DateUtil.getInstance(8).getEndTimeOfDay(new Date()));
	}
}
