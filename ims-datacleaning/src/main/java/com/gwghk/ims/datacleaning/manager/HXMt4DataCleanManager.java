package com.gwghk.ims.datacleaning.manager;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.common.enums.ActAccountLevelEnum;
import com.gwghk.ims.common.enums.ActAccountStatusEnum;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.ActPlatformEnum;
import com.gwghk.ims.common.enums.ActTradeTypeEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.CurrencyEnum;
import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.common.enums.Mt4CountryEnum;
import com.gwghk.ims.common.enums.SyncDataUpdateTypeEnum;
import com.gwghk.ims.datacleaning.dao.entity.Accounts;
import com.gwghk.ims.datacleaning.dao.entity.ActCashin;
import com.gwghk.ims.datacleaning.dao.entity.ActCashout;
import com.gwghk.ims.datacleaning.dao.entity.ActCustomerInfo;
import com.gwghk.ims.datacleaning.dao.entity.ActCustomerInfoDisabled;
import com.gwghk.ims.datacleaning.dao.entity.ActSyncDataUpdate;
import com.gwghk.ims.datacleaning.dao.entity.ActTradeRecord;
import com.gwghk.ims.datacleaning.dao.entity.Demousers;
import com.gwghk.ims.datacleaning.dao.entity.ImsBlackList;
import com.gwghk.ims.datacleaning.dao.entity.Members;
import com.gwghk.ims.datacleaning.dao.entity.Mt4Trades;
import com.gwghk.ims.datacleaning.dao.entity.Withdrawing;
import com.gwghk.ims.datacleaning.dao.inf.base.ActCashinDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ActCashoutDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ActCustomerInfoDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ActCustomerInfoDisabledDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ActSyncDataUpdateDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ActTradeRecordDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ImsBlackListDao;
import com.gwghk.ims.datacleaning.dao.inf.hx.AccountsDao;
import com.gwghk.ims.datacleaning.dao.inf.hx.DemousersDao;
import com.gwghk.ims.datacleaning.dao.inf.hx.MembersDao;
import com.gwghk.ims.datacleaning.dao.inf.hx.WithdrawingDao;
import com.gwghk.ims.datacleaning.dao.inf.hxdemotrade.DemoMt4TradesDao;
import com.gwghk.ims.datacleaning.dao.inf.hxrealtrade.RealMt4TradesDao;
import com.gwghk.ims.datacleaning.redis.ActCustomerRedis;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.unify.framework.common.util.BeanUtils;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：HX-MT4数据清洗业务处理类
 * 
 * @author lawrence
 * @version 1.0
 * @Date 2017年10月9日
 */
@Component
@Transactional(value = "transactionManagerHx")
public class HXMt4DataCleanManager {
	private Logger logger = LoggerFactory.getLogger(HXMt4DataCleanManager.class);

	@Value("${hx.decrypt.key}")
	private String hxDecryptKey;

	@Value("${hx.default.date}")
	private String defaultDate;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private MembersDao membersDao;
	@Autowired
	private DemousersDao demousersDao;
	@Autowired
	private RealMt4TradesDao realMt4TradesDao;
	@Autowired
	private DemoMt4TradesDao demoMt4TradesDao;
	@Autowired
	private WithdrawingDao withdrawingDao;
	@Autowired
	private ActCustomerInfoDao actCustomerInfoDao;
	@Autowired
	private ActTradeRecordDao actTradeRecordDao;
	@Autowired
	private ActCashinDao actCashinDao;
	@Autowired
	private ActCashoutDao actCashoutDao;
	@Autowired
	private AccountsDao accountsDao;

	@Autowired
	private ActSyncDataUpdateDao actSyncDataUpdateDao;

	@Autowired
	private ActCustomerRedis actCustomerRedis;

	@Autowired
	private ActCustomerInfoDisabledDao actCustomerInfoDisabledDao;

	@Autowired
	private ImsBlackListDao imsBlackListDao;

	/**
	 * 
	 * @MethodName: mergeData
	 * @Description: HX-MT4清洗逻辑共用方法
	 * @param mergeBusinese
	 * @param syncDataUpdateType
	 * @return void
	 * @throws Exception
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	public void mergeData(HXMergeBusiness mergeBusinese, String syncDataUpdateType) throws Exception {
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, CompanyEnum.hx.getId());
		boolean exitSynTime = false;
		Calendar paramLastUpdateTime = null;
		ActSyncDataUpdate itemActSyncDataUpdate = null;
		List<ActSyncDataUpdate> listActSyncDataUpdate = actSyncDataUpdateDao.findBySyncType(syncDataUpdateType);
		// 判断是否已经存在同步时间的记录
		if (listActSyncDataUpdate != null && !listActSyncDataUpdate.isEmpty()) {
			exitSynTime = true;
			itemActSyncDataUpdate = listActSyncDataUpdate.get(0);
			paramLastUpdateTime = Calendar.getInstance();
			paramLastUpdateTime.setTime(itemActSyncDataUpdate.getLastUpdateTime());
		} else {
			// 当不存在同步记录时，对于交易数据，是取当前默认时间，当天零晨开始的数据
			if (SyncDataUpdateTypeEnum.MT4_HX_REAL_TRADE.getValue().equals(syncDataUpdateType)
					|| SyncDataUpdateTypeEnum.MT4_HX_DEMO_TRADE.getValue().equals(syncDataUpdateType)) {
				paramLastUpdateTime = Calendar.getInstance();
				paramLastUpdateTime.set(Calendar.HOUR, 0);
				paramLastUpdateTime.set(Calendar.SECOND, 0);
				paramLastUpdateTime.set(Calendar.MINUTE, 1);
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		if (paramLastUpdateTime != null) {
			data.put("lastUpdateDate", paramLastUpdateTime.getTime());
		}
		data.put("hxDecryptKey", hxDecryptKey);
		data.put("companyCode", CompanyEnum.hx.getCode());
		Date lastUpdateTime = null;

		if (exitSynTime || paramLastUpdateTime != null) {
			lastUpdateTime = mergeBusinese.doMerge(data);
		} else {
			// 当同步记录不存在，并且是非交易数据同步，需要将旧数据全部提取,循环每30天取一次时间范围内数据进行清洗
			try {
				Date now = new Date();
				Date lastUpdateDateBegin = sdf.parse(defaultDate);
				Calendar calLastUpdateDateBegin = Calendar.getInstance();
				Calendar calLastUpdateDateEnd = Calendar.getInstance();
				calLastUpdateDateBegin.setTime(lastUpdateDateBegin);
				calLastUpdateDateEnd.setTime(lastUpdateDateBegin);
				calLastUpdateDateEnd.add(Calendar.DATE, 30);
				Date lastUpdateDateEnd = calLastUpdateDateEnd.getTime();
				data.put("lastUpdateDateBegin", lastUpdateDateBegin);
				data.put("lastUpdateDateEnd", lastUpdateDateEnd);
				while (calLastUpdateDateBegin.getTime().compareTo(now) < 0) {
					logger.info("类型{},开始清洗时间范围{}-{}数据", syncDataUpdateType,
							sdf.format(calLastUpdateDateBegin.getTime()), sdf.format(calLastUpdateDateEnd.getTime()));
					lastUpdateTime = mergeBusinese.doMerge(data);
					calLastUpdateDateBegin.add(Calendar.DATE, 30);
					calLastUpdateDateEnd.add(Calendar.DATE, 30);
					data.put("lastUpdateDateBegin", calLastUpdateDateBegin.getTime());
					data.put("lastUpdateDateEnd", calLastUpdateDateEnd.getTime());
				}
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
		}

		if (lastUpdateTime == null) {
			return;
		}
		paramLastUpdateTime = Calendar.getInstance();
		paramLastUpdateTime.setTime(lastUpdateTime);
		// // 推后多1分钟，防止数据同步出现遗漏
		// paramLastUpdateTime.add(Calendar.MINUTE, -1);

		if (!exitSynTime) {
			itemActSyncDataUpdate = new ActSyncDataUpdate();
			itemActSyncDataUpdate.setSyncType(syncDataUpdateType);
			itemActSyncDataUpdate.setEnableFlag("Y");
			itemActSyncDataUpdate.setDeleteFlag("N");
			itemActSyncDataUpdate.setCreateDate(new Date());
		}
		itemActSyncDataUpdate.setLastUpdateTime(paramLastUpdateTime.getTime());
		itemActSyncDataUpdate.setUpdateDate(new Date());
		itemActSyncDataUpdate.setCompanyId(2l);
		if (exitSynTime) {
			actSyncDataUpdateDao.update(itemActSyncDataUpdate);
		} else {
			actSyncDataUpdateDao.save(itemActSyncDataUpdate);
		}
	}

	/**
	 * 
	 * @MethodName: mergeRealActCustomerInfo
	 * @Description: HX-MT4真实用户资料清洗逻辑
	 * @return void
	 * @throws Exception
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	public void mergeRealActCustomerInfo() throws Exception {
		logger.info(">>>>>>>>>>>>开始{}", SyncDataUpdateTypeEnum.MT4_HX_REAL_CUSTOMER.getLabelKey());
		mergeData(new HXMergeBusiness() {
			@SuppressWarnings("rawtypes")
			@Override
			public Date doMerge(Map data) {
				return doMergeRealActCustomerInfo(data);
			}

		}, SyncDataUpdateTypeEnum.MT4_HX_REAL_CUSTOMER.getValue());
		logger.info("<<<<<<<<<<<<结束{}", SyncDataUpdateTypeEnum.MT4_HX_REAL_CUSTOMER.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: mergeMissRealActCustomerInfo
	 * @Description: HX-MT4真实用户资料清洗逻辑(防止数据丢失)
	 * @param paramData
	 * @return void
	 * @throws Exception 
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	public void mergeMissRealActCustomerInfo(Map<String, Object> paramData) throws Exception {
		logger.info(">>>>>>>>>>>>开始{}(数据丢失部分,备用)", SyncDataUpdateTypeEnum.MT4_HX_REAL_CUSTOMER.getLabelKey());
		if (paramData == null) {
			Map<String, Object> data = new HashMap<String, Object>();
			setMissDataParam(data);
			paramData = data;
		}
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, CompanyEnum.findByName("hx").getLongId());
		doMergeRealActCustomerInfo(paramData);
		logger.info("<<<<<<<<<<<<结束{}(数据丢失部分,备用)", SyncDataUpdateTypeEnum.MT4_HX_REAL_CUSTOMER.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: doMergeRealActCustomerInfo
	 * @Description: HX-MT4真实用户资料与活动中心真实用户资料数据转换
	 * @param data
	 * @return
	 * @return Date
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	private Date doMergeRealActCustomerInfo(Map data) {
		Date syncDate = new Date();
		List<Members> listMembers = membersDao.select(data);
		logger.info("HX-MT4真实用户资料数据量:" + listMembers.size());
		if (listMembers == null || listMembers.size() == 0) {
			return null;
		}
		ActCustomerInfo actCustomerInfo = null;
		Calendar lastUpdateTime = Calendar.getInstance();
		lastUpdateTime.set(Calendar.DATE, -10);
		for (Members memberItem : listMembers) {
			if (StringUtil.isEmpty(memberItem.getUsername())) {
				continue;
			}
			actCustomerInfo = new ActCustomerInfo();
			actCustomerInfo.setSyncUpdateDate(syncDate);
			actCustomerInfo.setCompanyId(2L);
			actCustomerInfo.setAccountNo(memberItem.getUsername());
			actCustomerInfo.setPlatform(ActPlatformEnum.MT4.getCode());
			actCustomerInfo.setChineseName(memberItem.getChineseName());
			actCustomerInfo.setAccountCurrency(CurrencyEnum.USD.getValue());
			actCustomerInfo.setMobile(memberItem.getMobile());
			actCustomerInfo.setIdNumber(memberItem.getIdNumber());
			actCustomerInfo.setEmail(memberItem.getEmail());
			actCustomerInfo.setAccountEnv(ActEnvEnum.REAL.getValue());
			actCustomerInfo.setEnv(ActEnvEnum.REAL.getValue());
			if (memberItem.getCountry() != null) {
				actCustomerInfo.setNationality(
						Mt4CountryEnum.getIsoValue(memberItem.getCountry().replace("（", "(").replace("）", ")")));
			}
			if (memberItem.getDepositTime() != null) {
				actCustomerInfo.setActivatedStatus("Y");
				actCustomerInfo.setActivatedTime(memberItem.getDepositTime());
			} else {
				actCustomerInfo.setActivatedStatus("N");
			}
			if ("0".equals(memberItem.getUserStatus())) {
				// 启用
				actCustomerInfo.setAccountStatus(ActAccountStatusEnum.A.getCode());
			} else if ("1".equals(memberItem.getUserStatus())) {
				// 销户
				actCustomerInfo.setAccountStatus(ActAccountStatusEnum.D.getCode());
			} else {
				// 黑名单,冻结
				actCustomerInfo.setAccountStatus(ActAccountStatusEnum.S.getCode());
			}
			if ("20美元".equals(memberItem.getGroup()) || "标准客户".equals(memberItem.getGroup())) {
				actCustomerInfo.setAccountLevel(ActAccountLevelEnum.MINI.getCode());
			} else if ("26美元".equals(memberItem.getGroup()) || "专业客户".equals(memberItem.getGroup())) {
				actCustomerInfo.setAccountLevel(ActAccountLevelEnum.STANDARD.getCode());
			} else if ("28美元".equals(memberItem.getGroup()) || "尊贵客户".equals(memberItem.getGroup())) {
				actCustomerInfo.setAccountLevel(ActAccountLevelEnum.VIP.getCode());
			}
			actCustomerInfo.setSyncCreateDate(new Date());
			actCustomerInfo.setRegisterTime(memberItem.getCtime());
			actCustomerInfo.setEnableFlag("Y");
			actCustomerInfo.setDeleteFlag("N");
			actCustomerInfo.setSource("1");
			actCustomerInfo.setCreateDate(memberItem.getCtime());
			if(memberItem.getMtime() != null || memberItem.getPlatformMtime()!=null){
				if(memberItem.getMtime() == null && memberItem.getPlatformMtime()!=null){
					actCustomerInfo.setUpdateDate(memberItem.getPlatformMtime());
				}else if(memberItem.getMtime() != null && memberItem.getPlatformMtime() ==null){
					actCustomerInfo.setUpdateDate(memberItem.getMtime());
				}else if(memberItem.getMtime().compareTo(memberItem.getPlatformMtime())>0){
					actCustomerInfo.setUpdateDate(memberItem.getMtime());
				}else{
					actCustomerInfo.setUpdateDate(memberItem.getPlatformMtime());
				}
			}else{
				actCustomerInfo.setUpdateDate(memberItem.getCtime());
			}

			actCustomerInfo.setUpdateUser(memberItem.getModifier());
			actCustomerInfo.setPlatformCurrency(actCustomerInfo.getPlatform() + "#" + actCustomerInfo.getAccountCurrency());
			actCustomerInfo.setCompanyCode(CompanyEnum.hx.getCode());
			String companyCode = CompanyEnum.hx.getCode();
			ActCustomerInfo params = new ActCustomerInfo();
			params.setAccountNo(actCustomerInfo.getAccountNo());
			params.setPlatform(actCustomerInfo.getPlatform());
			params.setCompanyCode(companyCode);
			params.setEnv(ActEnvEnum.REAL.getValue());
			ActCustomerInfo dbActCustomerInfo = actCustomerInfoDao.selectByParams(params);
			boolean changeExit = false;
			if (dbActCustomerInfo != null) {
				String dbValue = dbActCustomerInfo.getAccountLevel() + dbActCustomerInfo.getAccountStatus()
						+ (dbActCustomerInfo.getActivatedTime() != null ? dbActCustomerInfo.getActivatedTime().getTime()
								: "")
						+ (dbActCustomerInfo.getIdNumber() != null ? dbActCustomerInfo.getIdNumber() : "")
						+ (dbActCustomerInfo.getEmail() != null ? dbActCustomerInfo.getEmail() : "")
						+ (dbActCustomerInfo.getMobile() != null ? dbActCustomerInfo.getMobile() : "");
				String newValue = actCustomerInfo.getAccountLevel() + actCustomerInfo.getAccountStatus()
						+ (actCustomerInfo.getActivatedTime() != null ? actCustomerInfo.getActivatedTime().getTime()
								: "")
						+ (actCustomerInfo.getIdNumber() != null ? actCustomerInfo.getIdNumber() : "")
						+ (actCustomerInfo.getEmail() != null ? actCustomerInfo.getEmail() : "")
						+ (actCustomerInfo.getMobile() != null ? actCustomerInfo.getMobile() : "");
				if (!dbValue.equals(newValue)) {
					changeExit = true;
				}

			}
			if (changeExit) {
				actCustomerRedis.cleanCustomerInfo(ActEnvEnum.REAL.getValue(), actCustomerInfo.getPlatform(),
						actCustomerInfo.getAccountNo(), companyCode);
			}
			ImsBlackList actGroupBlackAccount = imsBlackListDao.select(actCustomerInfo.getAccountNo());
			if (actGroupBlackAccount != null) {
				actCustomerInfo.setAccountBlack("Y");
			} else {
				actCustomerInfo.setAccountBlack("N");
			}

			if (dbActCustomerInfo != null) {
				actCustomerInfo.setId(dbActCustomerInfo.getId());
				actCustomerInfoDao.update(actCustomerInfo);
			} else {
				actCustomerInfoDao.save(actCustomerInfo);
			}

			if (ActAccountStatusEnum.D.getCode().equals(actCustomerInfo.getAccountStatus())) {
				if (StringUtil.isNotEmpty(actCustomerInfo.getIdNumber())) {
					saveActCustomerInfoDisalbed(companyCode, "id_number", actCustomerInfo.getIdNumber());
				}
				if (StringUtil.isNotEmpty(actCustomerInfo.getMobile())) {
					saveActCustomerInfoDisalbed(companyCode, "mobile", actCustomerInfo.getMobile());
				}
			}

			// actCustomerInfoMapper.saveOrUpdateRealManual(actCustomerInfo);

			if (lastUpdateTime.getTime().compareTo(actCustomerInfo.getUpdateDate()) < 0) {
				lastUpdateTime.setTime(actCustomerInfo.getUpdateDate());
			}
		}
		return lastUpdateTime.getTime();
	}

	private void saveActCustomerInfoDisalbed(String companyCode, String type, String dataValue) {
		ActCustomerInfoDisabled actCustomerInfoDisabled = new ActCustomerInfoDisabled();
		actCustomerInfoDisabled = new ActCustomerInfoDisabled();
		actCustomerInfoDisabled.setCompanyCode(companyCode);
		actCustomerInfoDisabled.setType(type);
		actCustomerInfoDisabled.setDataValue(dataValue);
		actCustomerInfoDisabled.setCreateDate(new Date());
		actCustomerInfoDisabled.setUpdateDate(new Date());
		;
		actCustomerInfoDisabledDao.save(actCustomerInfoDisabled);
	}

	/**
	 * 
	 * @MethodName: mergeDemoActCustomerInfo
	 * @Description: HX-MT4模拟用户资料清洗逻辑
	 * @return void
	 * @throws Exception
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	public void mergeDemoActCustomerInfo() throws Exception {
		logger.info(">>>>>>>>>>>>开始{}", SyncDataUpdateTypeEnum.MT4_HX_DEMO_CUSTOMER.getLabelKey());
		mergeData(new HXMergeBusiness() {
			@SuppressWarnings("rawtypes")
			@Override
			public Date doMerge(Map data) throws Exception {
				return doMergeDemoActCustomerInfo(data);
			}

		}, SyncDataUpdateTypeEnum.MT4_HX_DEMO_CUSTOMER.getValue());
		logger.info("<<<<<<<<<<<<结束{}", SyncDataUpdateTypeEnum.MT4_HX_DEMO_CUSTOMER.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: mergeMissDemoActCustomerInfo
	 * @Description: HX-MT4模拟用户资料清洗逻辑(防止数据丢失)
	 * @param paramData
	 * @return void
	 * @throws Exception
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	public void mergeMissDemoActCustomerInfo(Map<String, Object> paramData) throws Exception {
		logger.info(">>>>>>>>>>>>开始{}(数据丢失部分,备用)", SyncDataUpdateTypeEnum.MT4_HX_DEMO_CUSTOMER.getLabelKey());
		if (paramData == null) {
			Map<String, Object> data = new HashMap<String, Object>();
			setMissDataParam(data);
			paramData = data;
		}
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, CompanyEnum.findByName("hx").getLongId());
		doMergeDemoActCustomerInfo(paramData);
		logger.info(">>>>>>>>>>>>结束{}(数据丢失部分,备用)", SyncDataUpdateTypeEnum.MT4_HX_DEMO_CUSTOMER.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: doMergeDemoActCustomerInfo
	 * @Description: HX-MT4模拟用户资料与活动中心模拟用户资料数据转换
	 * @param data
	 * @return
	 * @return Date
	 * @throws Exception
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("rawtypes")
	private Date doMergeDemoActCustomerInfo(Map data) throws Exception {
		Date syncDate = new Date();
		List<Demousers> listDemousers = demousersDao.select(data);
		logger.info("HX-MT4模拟用户资料数据量:" + listDemousers.size());
		if (listDemousers == null || listDemousers.size() == 0) {
			return null;
		}
		ActCustomerInfo actCustomerInfo = null;
		Calendar lastUpdateTime = Calendar.getInstance();
		lastUpdateTime.set(Calendar.DATE, -10);
		for (Demousers demoUserItem : listDemousers) {
			if (StringUtil.isEmpty(demoUserItem.getUsername())) {
				continue;
			}
			actCustomerInfo = new ActCustomerInfo();
			actCustomerInfo.setSyncUpdateDate(syncDate);
			actCustomerInfo.setCompanyId(2L);
			actCustomerInfo.setAccountNo(demoUserItem.getUsername());
			actCustomerInfo.setPlatform(ActPlatformEnum.MT4.getCode());
			actCustomerInfo.setChineseName(demoUserItem.getName());
			actCustomerInfo.setAccountCurrency(CurrencyEnum.USD.getValue());
			if (demoUserItem.getNationality() != null) {
				actCustomerInfo.setNationality(
						Mt4CountryEnum.getIsoValue(demoUserItem.getNationality().replace("（", "(").replace("）", ")")));
			}
			actCustomerInfo.setMobile(demoUserItem.getMobile());
			actCustomerInfo.setEmail(demoUserItem.getEmail());
			actCustomerInfo.setAccountEnv(ActEnvEnum.DEMO.getValue());
			actCustomerInfo.setEnv(ActEnvEnum.DEMO.getValue());
			actCustomerInfo.setActivatedStatus("Y");
			actCustomerInfo.setActivatedTime(demoUserItem.getCtime());
			// 启用
			actCustomerInfo.setAccountStatus(ActAccountStatusEnum.A.getCode());
			actCustomerInfo.setAccountLevel(ActAccountLevelEnum.STANDARD.getCode());

			actCustomerInfo.setRegisterTime(demoUserItem.getCtime());
			actCustomerInfo.setCancelBefore("N");
			actCustomerInfo.setEnableFlag("Y");
			actCustomerInfo.setDeleteFlag("N");
			actCustomerInfo.setSource("1");
			actCustomerInfo.setCreateDate(demoUserItem.getCtime());
			actCustomerInfo
					.setUpdateDate(demoUserItem.getMtime() != null ? demoUserItem.getMtime() : demoUserItem.getCtime());
			actCustomerInfo
					.setPlatformCurrency(actCustomerInfo.getPlatform() + "#" + actCustomerInfo.getAccountCurrency());
			actCustomerInfo.setCompanyCode(CompanyEnum.hx.getCode());
			actCustomerInfo.setSyncCreateDate(new Date());

			ActCustomerInfo params = new ActCustomerInfo();
			params.setAccountNo(actCustomerInfo.getAccountNo());
			params.setPlatform(actCustomerInfo.getPlatform());
			params.setCompanyCode(CompanyEnum.hx.getCode());
			params.setEnv(ActEnvEnum.DEMO.getValue());
			ActCustomerInfo dbActCustomerInfo = actCustomerInfoDao.selectByParams(params);
			boolean changeExit = false;
			if (dbActCustomerInfo != null) {
				String dbValue = dbActCustomerInfo.getAccountLevel() + dbActCustomerInfo.getAccountStatus()
						+ (dbActCustomerInfo.getActivatedTime() != null ? dbActCustomerInfo.getActivatedTime().getTime()
								: "")
						+ (dbActCustomerInfo.getIdNumber() != null ? dbActCustomerInfo.getIdNumber() : "")
						+ (dbActCustomerInfo.getEmail() != null ? dbActCustomerInfo.getEmail() : "")
						+ (dbActCustomerInfo.getMobile() != null ? dbActCustomerInfo.getMobile() : "");
				String newValue = actCustomerInfo.getAccountLevel() + actCustomerInfo.getAccountStatus()
						+ (actCustomerInfo.getActivatedTime() != null ? actCustomerInfo.getActivatedTime().getTime()
								: "")
						+ (actCustomerInfo.getIdNumber() != null ? actCustomerInfo.getIdNumber() : "")
						+ (actCustomerInfo.getEmail() != null ? actCustomerInfo.getEmail() : "")
						+ (actCustomerInfo.getMobile() != null ? actCustomerInfo.getMobile() : "");
				if (!dbValue.equals(newValue)) {
					changeExit = true;
				}

			}
			if (changeExit) {
				actCustomerRedis.cleanCustomerInfo(ActEnvEnum.DEMO.getValue(), actCustomerInfo.getPlatform(),
						actCustomerInfo.getAccountNo(), CompanyEnum.hx.getCode());
			}
			ImsBlackList imsBlackList = imsBlackListDao.select(actCustomerInfo.getAccountNo());
			if (imsBlackList != null) {
				actCustomerInfo.setAccountBlack("Y");
			} else {
				actCustomerInfo.setAccountBlack("N");
			}

			if (dbActCustomerInfo != null) {
				actCustomerInfo.setId(dbActCustomerInfo.getId());
				actCustomerInfoDao.update(actCustomerInfo);
			} else {
				actCustomerInfoDao.save(actCustomerInfo);
			}

			// actCustomerInfoMapper.saveOrUpdateDemoManual(actCustomerInfo);

			if (lastUpdateTime.getTime().compareTo(actCustomerInfo.getUpdateDate()) < 0) {
				lastUpdateTime.setTime(actCustomerInfo.getUpdateDate());
			}
		}
		return lastUpdateTime.getTime();
	}

	/**
	 * 
	 * @MethodName: mergeRealActTradeRecord
	 * @Description: HX-MT4真实用户交易数据清洗逻辑
	 * @return void
	 * @throws Exception
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	public void mergeRealActTradeRecord() throws Exception {
		logger.info(">>>>>>>>>>>>开始{}", SyncDataUpdateTypeEnum.MT4_HX_REAL_TRADE.getLabelKey());
		mergeData(new HXMergeBusiness() {
			@SuppressWarnings("rawtypes")
			@Override
			public Date doMerge(Map data) {
				return doMergeRealActTradeRecord(data);
			}

		}, SyncDataUpdateTypeEnum.MT4_HX_REAL_TRADE.getValue());
		logger.info("<<<<<<<<<<<<结束{}", SyncDataUpdateTypeEnum.MT4_HX_REAL_TRADE.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: mergeMissRealActTradeRecord
	 * @Description: HX-MT4真实用户交易数据清洗逻辑(防止数据丢失)
	 * @param paramData
	 * @return void
	 * @throws Exception 
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	public void mergeMissRealActTradeRecord(Map<String, Object> paramData) throws Exception {
		logger.info(">>>>>>>>>>>>开始{}(数据丢失部分,备用)", SyncDataUpdateTypeEnum.MT4_HX_REAL_TRADE.getLabelKey());
		if (paramData == null) {
			Map<String, Object> data = new HashMap<String, Object>();
			setMissDataParam(data);
			paramData = data;
		}
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, CompanyEnum.findByName("hx").getLongId());
		doMergeRealActTradeRecord(paramData);
		logger.info("<<<<<<<<<<<<结束{}(数据丢失部分,备用)", SyncDataUpdateTypeEnum.MT4_HX_REAL_TRADE.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: doMergeRealActTradeRecord
	 * @Description: HX-MT4真实用户交易数据转换活动中心的真实用户交易数据
	 * @param data
	 * @return
	 * @return Date
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	private Date doMergeRealActTradeRecord(Map data) {
		List<Mt4Trades> listMt4Trades = realMt4TradesDao.selectTrade(data);
		if (listMt4Trades == null || listMt4Trades.size() == 0) {
			return null;
		}
		ActTradeRecord actTradeRecord = null;
		ActTradeRecord partialActTradeRecord = null;
		Calendar lastUpdateTime = Calendar.getInstance();
		lastUpdateTime.set(Calendar.DATE, -10);
		for (Mt4Trades mt4Tradeitem : listMt4Trades) {
			actTradeRecord = new ActTradeRecord();
			actTradeRecord.setCompanyCode(CompanyEnum.hx.getCode());
			actTradeRecord.setCompanyId(2L);
			actTradeRecord.setSource("1");
			actTradeRecord.setAccountNo("" + mt4Tradeitem.getLogin());
			actTradeRecord.setPlatform(ActPlatformEnum.MT4.getCode());
			actTradeRecord.setOrderNo(new Long(mt4Tradeitem.getTicket()));
			actTradeRecord.setPositionNo(actTradeRecord.getOrderNo());
			actTradeRecord.setProductType("1");
			actTradeRecord.setCreateDate(new Date());
			actTradeRecord.setUpdateDate(new Date());
			if ("GOLD".equals(mt4Tradeitem.getSymbol())) {
				actTradeRecord.setProduct("LLG");
			} else if ("SILVER".equals(mt4Tradeitem.getSymbol())) {
				actTradeRecord.setProduct("LLS");
			} else {
				actTradeRecord.setProduct(mt4Tradeitem.getSymbol());
			}
			actTradeRecord.setTradeType(mt4Tradeitem.getTradeType());
			actTradeRecord.setTradeLot(mt4Tradeitem.getVolume().doubleValue() / 100f);
			actTradeRecord.setProfit(new BigDecimal(mt4Tradeitem.getProfit()));
			actTradeRecord.setCommission(new BigDecimal(mt4Tradeitem.getCommission()));
			actTradeRecord.setSwap(new BigDecimal(mt4Tradeitem.getSwaps()));
			if (actTradeRecord.getTradeType().equals(ActTradeTypeEnum.IN.getValue())) {
				actTradeRecord.setTradeTime(mt4Tradeitem.getOpenTime());
			} else {
				actTradeRecord.setTradeTime(mt4Tradeitem.getCloseTime());
			}
			actTradeRecord.setDeleteFlag("N");
			// 部分平仓处理，增加多一条开仓记录,或者出现平仓单，再生成一条平仓单(防止遗漏)
			if (StringUtil.isNotEmpty(mt4Tradeitem.getComment())
					&& (mt4Tradeitem.getComment().startsWith("to #") || mt4Tradeitem.getComment().startsWith("from #"))
					|| actTradeRecord.getTradeType().equals(ActTradeTypeEnum.OUT.getValue())) {
				partialActTradeRecord = new ActTradeRecord();
				BeanUtils.copyExceptNull(partialActTradeRecord, actTradeRecord);
				partialActTradeRecord.setTradeType(ActTradeTypeEnum.IN.getValue());
				partialActTradeRecord.setTradeTime(mt4Tradeitem.getOpenTime());
				actTradeRecordDao.saveOrUpdateRealManual(partialActTradeRecord);
			}
			actTradeRecordDao.saveOrUpdateRealManual(actTradeRecord);

			if (lastUpdateTime.getTime().compareTo(actTradeRecord.getTradeTime()) < 0) {
				lastUpdateTime.setTime(actTradeRecord.getTradeTime());
			}
		}
		lastUpdateTime.add(Calendar.SECOND, -30);
		return lastUpdateTime.getTime();
	}

	/**
	 * 
	 * @MethodName: mergeDemoActTradeRecord
	 * @Description: HX-MT4模拟用户交易数据清洗逻辑
	 * @return void
	 * @throws Exception
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	public void mergeDemoActTradeRecord() throws Exception {
		logger.info(">>>>>>>>>>>>开始{}", SyncDataUpdateTypeEnum.MT4_HX_DEMO_TRADE.getLabelKey());
		mergeData(new HXMergeBusiness() {
			@SuppressWarnings("rawtypes")
			@Override
			public Date doMerge(Map data) {
				return doMergeDemoActTradeRecord(data);
			}
		}, SyncDataUpdateTypeEnum.MT4_HX_DEMO_TRADE.getValue());
		logger.info("<<<<<<<<<<<<结束{}", SyncDataUpdateTypeEnum.MT4_HX_DEMO_TRADE.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: mergeMissDemoActTradeRecord
	 * @Description: HX-MT4模拟用户交易数据清洗逻辑(防止数据丢失)
	 * @param paramData
	 * @return void
	 * @throws Exception 
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	public void mergeMissDemoActTradeRecord(Map<String, Object> paramData) throws Exception {
		logger.info(">>>>>>>>>>>>开始{}(数据丢失部分,备用)", SyncDataUpdateTypeEnum.MT4_HX_DEMO_TRADE.getLabelKey());
		if (paramData == null) {
			Map<String, Object> data = new HashMap<String, Object>();
			setMissDataParam(data);
			paramData = data;
		}
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, CompanyEnum.findByName("hx").getLongId());
		doMergeDemoActTradeRecord(paramData);
		logger.info("<<<<<<<<<<<<结束{}(数据丢失部分,备用)", SyncDataUpdateTypeEnum.MT4_HX_DEMO_TRADE.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: doMergeDemoActTradeRecord
	 * @Description: HX-MT4模拟用户交易数据转换活动中心模拟用户交易数据
	 * @param data
	 * @return
	 * @return Date
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("rawtypes")
	private Date doMergeDemoActTradeRecord(Map data) {
		List<Mt4Trades> listMt4Trades = demoMt4TradesDao.selectTrade(data);
		if (listMt4Trades == null || listMt4Trades.size() == 0) {
			return null;
		}
		ActTradeRecord actTradeRecord = null;
		ActTradeRecord partialActTradeRecord = null;
		Calendar lastUpdateTime = Calendar.getInstance();
		lastUpdateTime.set(Calendar.DATE, -10);
		for (Mt4Trades mt4Tradeitem : listMt4Trades) {
			actTradeRecord = new ActTradeRecord();
			actTradeRecord.setCompanyCode(CompanyEnum.hx.getCode());
			actTradeRecord.setCompanyId(2L);
			actTradeRecord.setSource("1");
			actTradeRecord.setAccountNo("" + mt4Tradeitem.getLogin());
			actTradeRecord.setPlatform(ActPlatformEnum.MT4.getCode());
			actTradeRecord.setOrderNo(new Long(mt4Tradeitem.getTicket()));
			actTradeRecord.setPositionNo(actTradeRecord.getOrderNo());
			actTradeRecord.setProductType("1");
			actTradeRecord.setCreateDate(new Date());
			actTradeRecord.setUpdateDate(new Date());
			if ("GOLD".equals(mt4Tradeitem.getSymbol())) {
				actTradeRecord.setProduct("LLG");
			} else if ("SILVER".equals(mt4Tradeitem.getSymbol())) {
				actTradeRecord.setProduct("LLS");
			} else {
				actTradeRecord.setProduct(mt4Tradeitem.getSymbol());
			}
			actTradeRecord.setTradeType(mt4Tradeitem.getTradeType());
			actTradeRecord.setTradeLot(mt4Tradeitem.getVolume().doubleValue() / 100f);
			actTradeRecord.setProfit(new BigDecimal(mt4Tradeitem.getProfit()));
			actTradeRecord.setCommission(new BigDecimal(mt4Tradeitem.getCommission()));
			actTradeRecord.setSwap(new BigDecimal(mt4Tradeitem.getSwaps()));
			if (actTradeRecord.getTradeType().equals(ActTradeTypeEnum.IN.getValue())) {
				actTradeRecord.setTradeTime(mt4Tradeitem.getOpenTime());
			} else {
				actTradeRecord.setTradeTime(mt4Tradeitem.getCloseTime());
			}
			actTradeRecord.setDeleteFlag("N");
			// 部分平仓处理，增加多一条开仓记录,或者出现平仓单，再生成一条平仓单(防止遗漏)
			if (StringUtil.isNotEmpty(mt4Tradeitem.getComment())
					&& (mt4Tradeitem.getComment().startsWith("to #") || mt4Tradeitem.getComment().startsWith("from #"))
					|| actTradeRecord.getTradeType().equals(ActTradeTypeEnum.OUT.getValue())) {
				partialActTradeRecord = new ActTradeRecord();
				BeanUtils.copyExceptNull(partialActTradeRecord, actTradeRecord);
				partialActTradeRecord.setTradeType(ActTradeTypeEnum.IN.getValue());
				partialActTradeRecord.setTradeTime(mt4Tradeitem.getOpenTime());
				actTradeRecordDao.saveOrUpdateDemoManual(partialActTradeRecord);
			}
			actTradeRecordDao.saveOrUpdateDemoManual(actTradeRecord);

			if (lastUpdateTime.getTime().compareTo(actTradeRecord.getTradeTime()) < 0) {
				lastUpdateTime.setTime(actTradeRecord.getTradeTime());
			}
		}
		return lastUpdateTime.getTime();
	}

	/**
	 * 
	 * @MethodName: mergeRealActCashin
	 * @Description: HX-MT4真实用户入金数据清洗逻辑
	 * @return void
	 * @throws Exception
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	public void mergeRealActCashin() throws Exception {
		logger.info(">>>>>>>>>>>>开始{}", SyncDataUpdateTypeEnum.MT4_HX_REAL_CASHIN.getLabelKey());
		mergeData(new HXMergeBusiness() {
			@SuppressWarnings("rawtypes")
			@Override
			public Date doMerge(Map data) {
				return doMergeRealActCashin(data);
			}
		}, SyncDataUpdateTypeEnum.MT4_HX_REAL_CASHIN.getValue());
		logger.info("<<<<<<<<<<<<结束{}", SyncDataUpdateTypeEnum.MT4_HX_REAL_CASHIN.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: mergeMissRealActCashin
	 * @Description: HX-MT4真实用户入金数据清洗逻辑(防止数据丢失)
	 * @param paramData
	 * @return void
	 * @throws Exception 
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	public void mergeMissRealActCashin(Map<String, Object> paramData) throws Exception {
		logger.info(">>>>>>>>>>>>开始{}(数据丢失部分,备用)", SyncDataUpdateTypeEnum.MT4_HX_REAL_CASHIN.getLabelKey());
		if (paramData == null) {
			Map<String, Object> data = new HashMap<String, Object>();
			setMissDataParam(data);
			paramData = data;
		}
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, CompanyEnum.findByName("hx").getLongId());
		doMergeRealActCashin(paramData);
		logger.info("<<<<<<<<<<<<结束{}(数据丢失部分,备用)", SyncDataUpdateTypeEnum.MT4_HX_REAL_CASHIN.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: doMergeRealActCashin
	 * @Description: HX-MT4模真实用户入金数据转换活动中心真实用户入金数据
	 * @param data
	 * @return
	 * @return Date
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	private Date doMergeRealActCashin(Map data) {
		List<Accounts> listAccounts = accountsDao.select(data);
		if (listAccounts == null || listAccounts.size() == 0) {
			return null;
		}
		ActCashin actCashin = null;
		Calendar lastUpdateTime = Calendar.getInstance();
		lastUpdateTime.set(Calendar.DATE, -10);
		for (Accounts accounts : listAccounts) {
			actCashin = new ActCashin();
			actCashin.setCompanyId(2L);
			actCashin.setSource("1");
			actCashin.setAccountNo(accounts.getLogin());
			actCashin.setPlatform(ActPlatformEnum.MT4.getCode());
			actCashin.setPno(accounts.getOrder());
			actCashin.setTransAmount(new BigDecimal(accounts.getUsd()));
			actCashin.setApproveDate(accounts.getDepositTime());
			// actCashin.setFirstDeposit(accounts.getFirstDeposit());
			actCashin.setCompanyCode(CompanyEnum.hx.getCode());
			actCashin.setCreateDate(new Date());
			actCashin.setUpdateDate(new Date());
			actCashinDao.save(actCashin);

			if (lastUpdateTime.getTime().compareTo(actCashin.getApproveDate()) < 0) {
				lastUpdateTime.setTime(actCashin.getApproveDate());
			}
		}
		return lastUpdateTime.getTime();
	}

	/**
	 * 
	 * @MethodName: mergeRealActCashout
	 * @Description: HX-MT4真实用户出金数据清洗逻辑
	 * @return void
	 * @throws Exception
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	public void mergeRealActCashout() throws Exception {
		logger.info(">>>>>>>>>>>>开始{}", SyncDataUpdateTypeEnum.MT4_HX_REAL_CASHOUT.getLabelKey());
		mergeData(new HXMergeBusiness() {
			@SuppressWarnings("rawtypes")
			@Override
			public Date doMerge(Map data) {
				return doMergeRealActCashout(data);
			}
		}, SyncDataUpdateTypeEnum.MT4_HX_REAL_CASHOUT.getValue());
		logger.info("<<<<<<<<<<<<结束{}", SyncDataUpdateTypeEnum.MT4_HX_REAL_CASHOUT.getLabelKey());
	}

	/**
	 * 
	 * @MethodName: mergeMissRealActCashout
	 * @Description: HX-MT4真实用户出金数据清洗逻辑(防止数据丢失)
	 * @param paramData
	 * @return void
	 * @throws Exception 
	 */
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	public void mergeMissRealActCashout(Map<String, Object> paramData) throws Exception {
		logger.info(">>>>>>>>>>>>开始{}(数据丢失部分,备用)", SyncDataUpdateTypeEnum.MT4_HX_REAL_CASHOUT.getLabelKey());
		if (paramData == null) {
			Map<String, Object> data = new HashMap<String, Object>();
			setMissDataParam(data);
			paramData = data;
		}
		DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, CompanyEnum.findByName("hx").getLongId());
		doMergeRealActCashout(paramData);
		logger.info(">>>>>>>>>>>>结束{}(数据丢失部分,备用)", SyncDataUpdateTypeEnum.MT4_HX_REAL_CASHOUT.getLabelKey());
	}

	@SuppressWarnings("rawtypes")
	@Transactional(value = "transactionManagerHx", propagation = Propagation.NOT_SUPPORTED)
	private Date doMergeRealActCashout(Map data) {
		List<Withdrawing> listWithdrawing = withdrawingDao.select(data);
		if (listWithdrawing == null || listWithdrawing.size() == 0) {
			return null;
		}
		ActCashout actCashout = null;
		Calendar lastUpdateTime = Calendar.getInstance();
		lastUpdateTime.set(Calendar.DATE, -10);
		for (Withdrawing withdrawingItem : listWithdrawing) {
			actCashout = new ActCashout();
			actCashout.setCompanyId(2L);
			actCashout.setSource("1");
			actCashout.setAccountNo(withdrawingItem.getLogin());
			actCashout.setPlatform(ActPlatformEnum.MT4.getCode());
			actCashout.setPno(withdrawingItem.getOrder());
			actCashout.setTransAmount(new BigDecimal(withdrawingItem.getMoney()).abs());
			actCashout.setApproveDate(withdrawingItem.getApproveTime());
			actCashout.setCompanyCode(CompanyEnum.hx.getCode());
			actCashout.setCreateDate(new Date());
			actCashout.setUpdateDate(new Date());
			if ("Hidden".equals(withdrawingItem.getStatus()) || withdrawingItem.getMoney() > 0) {
				actCashout.setEnableFlag("N");
				actCashout.setDeleteFlag("Y");
			} else {
				actCashout.setEnableFlag("Y");
				actCashout.setDeleteFlag("N");
			}
			actCashoutDao.save(actCashout);

			if (lastUpdateTime.getTime().compareTo(actCashout.getApproveDate()) < 0) {
				lastUpdateTime.setTime(actCashout.getApproveDate());
			}
		}
		return lastUpdateTime.getTime();
	}

	public void setMissDataParam(Map<String, Object> data) {
		Calendar lastUpdateDateBegin = Calendar.getInstance();
		Calendar lastUpdateDateEnd = Calendar.getInstance();
		lastUpdateDateEnd.add(Calendar.HOUR, -1);
		lastUpdateDateBegin.add(Calendar.HOUR, -25);
		data.put("lastUpdateDateBegin", lastUpdateDateBegin.getTime());
		data.put("lastUpdateDateEnd", lastUpdateDateEnd.getTime());
	}
}
