package com.gwghk.ims.message.consumer.handler;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.ActAccountLevelEnum;
import com.gwghk.ims.common.enums.ActAccountStatusEnum;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.ims.message.dao.entity.ActCustomerInfo;
import com.gwghk.ims.message.dao.entity.ActCustomerInfoCancel;
import com.gwghk.ims.message.dao.entity.ImsBlackList;
import com.gwghk.ims.message.dao.inf.ActCustomerInfoCancelDao;
import com.gwghk.ims.message.dao.inf.ActCustomerInfoDao;
import com.gwghk.ims.message.dao.inf.ImsBlackListDao;
import com.gwghk.ims.message.dto.Gts2CloneCustomerAccountInfo;
import com.gwghk.ims.message.dto.KafkaGTS2CommonData;
import com.gwghk.ims.message.redis.ActCustomerRedis;
import com.gwghk.ims.message.utils.GwConstants;
import com.gwghk.unify.framework.common.util.BeanUtils;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: KafkaGts2CustomerAccountInfoDataHandle
 * @Description: GTS2平台客户资料数据推送
 * @author lawrence
 * @date 2018年3月29日
 *
 */
@Component
public class KafkaGts2CloneCustomerAccountInfoDataHandle {
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2CloneCustomerAccountInfoDataHandle.class);

	//final Lock lock = new ReentrantLock();

	@Autowired
	private ActCustomerInfoDao actCustomerInfoDao;

	@Autowired
	private ActCustomerInfoCancelDao actCustomerInfoCancelDao;

	@Autowired
	private ImsBlackListDao imsBlackListDao;

	@Autowired
	private ActCustomerRedis actCustomerRedis;

	/**
	 * 功能：处理消息
	 */
	public void handleMessage(String message, String env) {
		try {
			logger.info("开始-GTS2-{}账号平台数据->活动中心同步！", env);
			//lock.lock();
			KafkaGTS2CommonData commonData = JsonUtil.json2Obj(message, KafkaGTS2CommonData.class);
			if (commonData != null) {
				if ((GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())
						|| GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA()))
						&& StringUtil.isNotEmpty(commonData.getN())) {
					Gts2CloneCustomerAccountInfo gts2CloneCustomerAccountInfo = JsonUtil.json2Obj(commonData.getN(),
							Gts2CloneCustomerAccountInfo.class);
					if (StringUtil.isNotEmpty(CompanyEnum.getCodeById(gts2CloneCustomerAccountInfo.getCompanyId()))) {
						DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA,
								gts2CloneCustomerAccountInfo.getCompanyId());
						saveUpdateCustomerInfo(gts2CloneCustomerAccountInfo, env,message);
						logger.info("完成-GTS2-{}账号平台数据->活动中心同步", env);
					}
				}
			} else {
				logger.warn("GTS2-{}账号平台数据同步没有产生记录！时间:{}", env, new Date());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("GTS2({})-账号平台数据kafka数据格式错误{},消息内容{}", env, e, message);
		} finally {
			//lock.unlock();
		}
	}

	/**
	 * 
	 * @MethodName: saveUpdateCustomerInfo
	 * @Description: 修改或者保存客户资料信息
	 * @param gts2CloneCustomerAccountInfo
	 * @param env
	 * @return void
	 * @throws Exception
	 */
	public void saveUpdateCustomerInfo(Gts2CloneCustomerAccountInfo gts2CloneCustomerAccountInfo, String env,String message)
			throws Exception {

		if (StringUtil.isEmpty(gts2CloneCustomerAccountInfo.getAccountNo())) {
			logger.error("Gts2CloneCustomerAccountInfo账号信息为空,{}", gts2CloneCustomerAccountInfo);
			return;
		}

		String companyCode = CompanyEnum.getCodeById(gts2CloneCustomerAccountInfo.getCompanyId());
		ActCustomerInfo params = new ActCustomerInfo();
		params.setEnv(env);
		params.setAccountNo(gts2CloneCustomerAccountInfo.getAccountNo());
		params.setPlatform(gts2CloneCustomerAccountInfo.getPlatform());
		params.setCompanyCode(companyCode);

		List<ActCustomerInfo> dbListCustomer = actCustomerInfoDao.selectByExample(params);

		if (dbListCustomer != null && !dbListCustomer.isEmpty()) {
			for (ActCustomerInfo itemActCustomerInfo : dbListCustomer) {
				ActCustomerInfo actCustomerInfo = initParams(gts2CloneCustomerAccountInfo, env);
				BeanUtils.copyExceptNull(itemActCustomerInfo, actCustomerInfo);
				actCustomerInfo.setCompanyCode(companyCode);
				actCustomerInfoDao.update(actCustomerInfo);
				actCustomerRedis.resetCustomerInfo(actCustomerInfo);
			}
		} else {
			ActCustomerInfo actCustomerInfo = initParams(gts2CloneCustomerAccountInfo, env);
			actCustomerInfo.setCompanyCode(companyCode);
			try {
				actCustomerInfoDao.save(actCustomerInfo);
			}catch(DuplicateKeyException ex) {
				actCustomerInfoDao.update(actCustomerInfo);//处理重复插入数据的情况
				logger.warn("GTS2-{}账号平台数据->活动中心同步,插入消息重复,改为更新,消息内容{}",env,message);
			}
		}
		
	}

	/**
	 * 
	 * @MethodName: initParams
	 * @Description: 初始化kafka参数，封装对象
	 * @param gts2CloneCustomerAccountInfo
	 * @param env
	 * @return
	 * @return ActCustomerInfo
	 * @throws Exception
	 */
	private ActCustomerInfo initParams(Gts2CloneCustomerAccountInfo gts2CloneCustomerAccountInfo, String env)
			throws Exception {
		ActCustomerInfo actCustomerInfo = new ActCustomerInfo();
		ImsBlackList actGroupBlackAccount = imsBlackListDao.select(gts2CloneCustomerAccountInfo.getAccountNo());
		if (actGroupBlackAccount != null) {
			actCustomerInfo.setAccountBlack("Y");
		} else {
			actCustomerInfo.setAccountBlack("N");
		}
		actCustomerInfo.setEnv(env);
		actCustomerInfo.setCompanyId(gts2CloneCustomerAccountInfo.getCompanyId());
		actCustomerInfo.setAccountNo(gts2CloneCustomerAccountInfo.getAccountNo());
		actCustomerInfo.setPlatform(gts2CloneCustomerAccountInfo.getPlatform());
		actCustomerInfo.setChineseName(gts2CloneCustomerAccountInfo.getChineseName());
		actCustomerInfo.setAccountCurrency(gts2CloneCustomerAccountInfo.getAccountCurrency());
		if (ActEnvEnum.DEMO.getValue().equals(env)) {
			actCustomerInfo.setAccountLevel(ActAccountLevelEnum.STANDARD.getCode());
			actCustomerInfo.setAccountEnv("demo");
			actCustomerInfo.setActivatedTime(gts2CloneCustomerAccountInfo.getOpenAccountDate());
			actCustomerInfo.setActivatedStatus("Y");
		} else {
			if (gts2CloneCustomerAccountInfo.getAccountLevel() != null) {
//				if (gts2CloneCustomerAccountInfo.getAccountLevel().indexOf("MIN") >= 0) {
//					actCustomerInfo.setAccountLevel(ActAccountLevelEnum.MINI.getCode());
//				} else if (gts2CloneCustomerAccountInfo.getAccountLevel().indexOf("STD") >= 0) {
//					actCustomerInfo.setAccountLevel(ActAccountLevelEnum.STANDARD.getCode());
//				} else if (gts2CloneCustomerAccountInfo.getAccountLevel().indexOf("VIP") >= 0) {
//					actCustomerInfo.setAccountLevel(ActAccountLevelEnum.VIP.getCode());
//				} else if (gts2CloneCustomerAccountInfo.getAccountLevel().indexOf("PRO") >= 0) {
//					actCustomerInfo.setAccountLevel(ActAccountLevelEnum.STANDARD.getCode());
//				} else {
					actCustomerInfo.setAccountLevel(gts2CloneCustomerAccountInfo.getAccountLevel());
//				}
			}
			if(ActEnvEnum.REAL.getValue().equals(env)){
				
			}
			if (gts2CloneCustomerAccountInfo.getIsReal() != null) {
				if (gts2CloneCustomerAccountInfo.getIsReal()) {
					actCustomerInfo.setAccountEnv("real");
				} else {
					actCustomerInfo.setAccountEnv("test");
				}
			} else {
				actCustomerInfo.setAccountEnv("demo");
			}
			actCustomerInfo.setActivatedTime(gts2CloneCustomerAccountInfo.getActivateTime());
			if (actCustomerInfo.getActivatedTime() != null) {
				actCustomerInfo.setActivatedStatus("Y");
			} else {
				actCustomerInfo.setActivatedStatus("N");
			}
		}

		actCustomerInfo.setMobile(gts2CloneCustomerAccountInfo.getMobilePhone());
		actCustomerInfo.setIdNumber(gts2CloneCustomerAccountInfo.getIdDocumentNumber());
		actCustomerInfo.setEmail(gts2CloneCustomerAccountInfo.getEmail());

		actCustomerInfo.setRegisterTime(gts2CloneCustomerAccountInfo.getOpenAccountDate());
		actCustomerInfo.setPlatformCurrency(actCustomerInfo.getPlatform() + "#" + actCustomerInfo.getAccountCurrency());
		actCustomerInfo.setCreateDate(new Date());
		actCustomerInfo.setUpdateDate(new Date());
		actCustomerInfo.setNationality(gts2CloneCustomerAccountInfo.getNationality());

		if ("S".equals(gts2CloneCustomerAccountInfo.getAccountStatus())
				|| "C".equals(gts2CloneCustomerAccountInfo.getAccountStatus())) {
			actCustomerInfo.setAccountStatus(ActAccountStatusEnum.S.getCode());
		} else if ("D".equals(gts2CloneCustomerAccountInfo.getAccountStatus())) {
			actCustomerInfo.setAccountStatus(ActAccountStatusEnum.D.getCode());
		} else {
			actCustomerInfo.setAccountStatus(ActAccountStatusEnum.A.getCode());
		}

		if (ActAccountStatusEnum.D.getCode().equals(actCustomerInfo.getAccountStatus())
				&& actCustomerInfo.getActivatedTime() != null) {
			if (StringUtil.isNotEmpty(actCustomerInfo.getIdNumber())) {
				saveActCustomerInfoCancel("id_number", actCustomerInfo.getIdNumber());
			}
			if (StringUtil.isNotEmpty(actCustomerInfo.getMobile())) {
				saveActCustomerInfoCancel("mobile", actCustomerInfo.getMobile());
			}
		}

		actCustomerInfo.setAccountUpdateDate(gts2CloneCustomerAccountInfo.getUpdateDate());
		actCustomerInfo.setUpdateUser(gts2CloneCustomerAccountInfo.getUpdateUser());
		actCustomerInfo.setAccountCreateDate(gts2CloneCustomerAccountInfo.getCreateDate());
		actCustomerInfo.setCreateUser(gts2CloneCustomerAccountInfo.getCreateUser());
		return actCustomerInfo;
	}

	/**
	 * 
	 * @MethodName: saveActCustomerInfoDisalbed
	 * @Description: 保存身份证，手机号的账号注销信息
	 * @param companyCode
	 * @param type
	 * @param dataValue
	 * @return void
	 */
	private void saveActCustomerInfoCancel(String type, String dataValue) {
		ActCustomerInfoCancel actCustomerInfoCancel = new ActCustomerInfoCancel();
		actCustomerInfoCancel = new ActCustomerInfoCancel();
		actCustomerInfoCancel.setType(type);
		actCustomerInfoCancel.setDataValue(dataValue);
		actCustomerInfoCancel.setCreateDate(new Date());
		actCustomerInfoCancel.setUpdateDate(new Date());
		actCustomerInfoCancelDao.save(actCustomerInfoCancel);
	}

}
