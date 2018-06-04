package com.gwghk.ims.activity.manager;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.dao.entity.ActPrizeRecordExt;
import com.gwghk.ims.activity.dao.entity.ActRelatedCustomer;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.activity.dao.inf.ImsPrizeRecordDao;
import com.gwghk.ims.activity.dao.inf.ImsPrizeRecordExtDao;
import com.gwghk.ims.activity.dao.inf.VActPrizeRecordDao;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.common.ApiResultCode;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.dto.activity.DemoCashAdjustDto;
import com.gwghk.ims.common.enums.ActAccountTypeEnum;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActPlatformEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.util.BigDecimalUtil;
import com.gwghk.ims.common.util.ImsBeanUtil;

@Component
@Transactional
public class ActDemoCashAdjustManager {

	private static final Logger logger = LoggerFactory.getLogger(ActDemoCashAdjustManager.class);
	
	@Autowired
	private ActRelatedCustomerManager actRelatedCustomer ;
	@Autowired
	private ActCustomerInfoManager actCustomerInfoManager; 
	@Autowired
	public ActGts2DemoApiManager actGts2DemoApiManager;
	@Autowired
	public VActPrizeRecordDao actPrizeRecordDao;
	@Autowired
	private ImsPrizeRecordDao ImsPrizeRecordDao;
	@Autowired
	public ImsPrizeRecordExtDao actPrizeRecordExtDao;
	
	/*@Autowired
	public ActGts2ApiManager actGts2ApiManager;
	@Autowired
	public ActGts2DemoApiManager actGts2DemoApiManager;
	@Autowired
	public RelatedCustomerMapper relatedCustomerMapper;
	@Autowired
	public ActCustomerRedis actCustomerRedis;
	@Autowired
	private ActMallItemManager actMallItemManager;*/
	/**
	 * 后台手工新增发放记录，扣模拟金额逻辑
	 * 
	 * @param record
	 */
	public MisRespResult<Void> demoCashAdjust(DemoCashAdjustDto dto) {
		MisRespResult<Void> result = new MisRespResult<Void>();
		if(BigDecimal.ZERO == dto.getAmount()){
			// 价格为0，直接返回
			return result;
		}
		String demoAccount = dto.getAccountNo() ;
		if(ActEnvEnum.REAL.getValue().equalsIgnoreCase(dto.getEnv())){
			if(ActPlatformEnum.GTS2.getCode().equalsIgnoreCase(dto.getPlatform())){
				// 关联信息
				ActRelatedCustomer relatedCustomer = actRelatedCustomer.findByCustomerNumber(dto.getAccountNo()) ;
				// 获取关联个模拟账户
				if(null != relatedCustomer && StringUtils.isNotBlank(relatedCustomer.getDemoCustomerNumber()+"")){
					demoAccount = relatedCustomer.getDemoCustomerNumber(); 
				}else{
					logger.error("未查询到关联的模拟账户！");
					result.setRespMsg(MisResultCode.Act20004) ;
					return result;
				}
			}else{
				logger.error("真实账户下MT4平台账户关联不了对应的模拟账户");
				result.setRespMsg(MisResultCode.Act20006) ;
				return result;
			}
		}
		ActCustomerInfo actCustomerInfoDemo = actCustomerInfoManager.findByAccountNo(demoAccount, dto.getPlatform(), ActAccountTypeEnum.DEMO.getAliasCode()) ;
		if(null == actCustomerInfoDemo){
			result.setRespMsg(MisResultCode.Act20005) ;
			return result;
		}
		String accountNo = dto.getAccountNo();
		synchronized(accountNo){// 确保同一个账户,每次只有一个线程在执行此方法
			try {
				BigDecimal tempValue = dto.getAmount();
				if("1".equals(dto.getOperateType())){
					// 增加
					ApiRespResult<String> apiRespResult = actGts2DemoApiManager.demoCashAdjust(actCustomerInfoDemo, tempValue);
					if(apiRespResult.isNotOk()){
						if(ApiResultCode.InterfaceError.getCode().equals(apiRespResult.getCode())){
							logger.info("调用接口异常！");
							result.setCode(ApiResultCode.InterfaceError.getCode());
							result.setMsg(ApiResultCode.InterfaceError.getMessage()) ;
							return result;
						}else if(ApiResultCode.DEMOAMOUNT_REDO.getCode().equals(apiRespResult.getCode())){
							logger.info("调用成功,但增加模拟金额失败,需在GTS2重做redo");
							result.setCode(ApiResultCode.DEMOAMOUNT_REDO.getCode());
							result.setMsg(ApiResultCode.DEMOAMOUNT_REDO.getMessage()) ;
							return result;
						}
					}
				}else{
					// 余额
					BigDecimal blance = actGts2DemoApiManager.getDemoCustomerAvailabelCredit(actCustomerInfoDemo);
					if(null == blance){
						logger.error("GTS2接口异常！");
						result.setCode(ApiResultCode.InterfaceError.getCode());
						result.setMsg(ApiResultCode.InterfaceError.getMessage()) ;
						return result;
					}
					if (blance.compareTo(tempValue) >= 0) {
						// 账户最低保留金额
						if(null != dto.getDemoKeepAmount() && dto.getDemoKeepAmount().compareTo(BigDecimal.ZERO) > 0){
							BigDecimal blance2 = BigDecimalUtil.subtract(tempValue, blance);
							if(blance2.compareTo(dto.getDemoKeepAmount()) < 0){
								// 余额不足
								logger.info("超过模拟账号保留金最低限制！");
								result.setRespMsg(MisResultCode.Act20007);
								return result;
							}
						}
						// 扣模拟金额
						// 所需模拟金额 * -1
						tempValue = BigDecimalUtil.multiply(new BigDecimal(-1), tempValue);
						ApiRespResult<String> apiRespResult = actGts2DemoApiManager.demoCashAdjust(actCustomerInfoDemo, tempValue);
						if(apiRespResult.isNotOk()){
							if(ApiResultCode.InterfaceError.getCode().equals(apiRespResult.getCode())){
								logger.info("调用接口异常！");
								result.setCode(ApiResultCode.InterfaceError.getCode());
								result.setMsg(ApiResultCode.InterfaceError.getMessage()) ;
								return result;
							}else if(ApiResultCode.DEMOAMOUNT_REDO.getCode().equals(apiRespResult.getCode())){
								logger.info("调用成功,但扣除模拟金额失败,需在GTS2重做redo");
								result.setCode(ApiResultCode.DEMOAMOUNT_REDO.getCode());
								result.setMsg(ApiResultCode.DEMOAMOUNT_REDO.getMessage()) ;
								return result;
							}
						}
					} else {
						// 余额不足
						logger.info("对不起，模拟金额不足！");
						result.setRespMsg(MisResultCode.Act20008);
						return result;
					}
				}
			} catch (Exception e) {
				logger.error("-->系统异常" + e.getMessage(), e);
				result.setRespMsg(MisResultCode.EXCEPTION);
				return result;
			}
			return result;
		}
	}
	
	/**
	 * 
	 * @MethodName: sendDemoAmount
	 * @Description: 给模拟账户添加模拟金额
	 * @param companyCode
	 * @param syncDataUpdateType
	 * @return void
	 */
	public void sendDemoAmount(VActPrizeRecord record){
		if(!ActItemTypeEnum.ANALOGCOIN.getValue().equals(record.getItemType()))
			return;
		
		ActCustomerInfo dto = new ActCustomerInfo();
		dto.setAccountNo(record.getAccountNo());

		dto.setAccountEnv(ActEnvEnum.DEMO.getValue());
		dto.setPlatform(ActPlatformEnum.GTS2.getCode());
		dto.setCompanyId(record.getCompanyId());
		ApiRespResult<String> result = this.addDemoCashAdjust(dto, record.getItemAmount());
		
		JSONObject otherMsgJson = null;
		if(StringUtils.isNotBlank(record.getOtherMsg())){
			otherMsgJson = JSONObject.parseObject(record.getOtherMsg());
		}else{
			otherMsgJson = new JSONObject();
		}
		
		if (result.isOk() || ApiResultCode.DEMOAMOUNT_REDO.getCode().equals(result.getCode())) {
			if(ApiResultCode.DEMOAMOUNT_REDO.getCode().equals(result.getCode())){
				otherMsgJson.put("sendDemoAmountRemark", "增加"+result.getMsg());
				record.setOtherMsg(otherMsgJson.toString());
			}
			// 添加模拟金额成功-更新发放状态
			record.setGivedStatus(IssuingStatusEnum.issue_success.getValue());
			
			// 更新返回流水号
			ActPrizeRecordExt recordExt = new ActPrizeRecordExt();
			recordExt.setRecordNumber(record.getRecordNo());
			recordExt.setDealNumber(result.getData());
			recordExt.setCompanyId(record.getCompanyId());
			actPrizeRecordExtDao.updateByRecordNumber(recordExt);
		}else{
			// 添加模拟金额失败
			otherMsgJson.put("sendDemoAmountRemark", "增加模拟金额失败："+result.getMsg());
			record.setOtherMsg(otherMsgJson.toString());
			record.setGivedStatus(IssuingStatusEnum.issue_fail.getValue());
		}
		
		//更新发放记录状态
		ImsPrizeRecordDao.update(ImsBeanUtil.copyNotNull(new ImsPrizeRecord(),record));
	}
	
	/**
	 * 根据条件查询发放记录
	 * @return
	 */
	/*private List<ActPrizeRecord> findActPrizeRecordList(String companyCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("giftType", ActItemTypeEnum.ANALOGCOIN.getCode());// 物品类型=模拟币
		map.put("issuingStatus", IssuingStatusEnum.issuing.getValue());// 发放状态=待发放
		map.put("auditStatus", AuditStatusEnum.auditPass.getValue());// 审核状态=审批通过
		map.put("sendStatus", 0);// 只查可发情况
		map.put("companyCode", companyCode);
		map.put("companyId", CompanyEnum.getKeyByCode(companyCode));
		map.put("paltform", Platform.GTS2.getCode());// 只查GTS2
		return actPrizeRecordMapper.findActPrizeRecordList(map);
	}*/
	
	
	
	/**
	 * 增加模拟金额
	 * @param actCustomerInfo
	 * @param tempValue
	 * @return
	 */
	public ApiRespResult<String> addDemoCashAdjust(ActCustomerInfo actCustomerInfo, BigDecimal tempValue) {
		ApiRespResult<String> result = null;
		try {
			// 增加
			result = actGts2DemoApiManager.demoCashAdjust(actCustomerInfo, tempValue);
			if(result.isNotOk()){
				if(ApiResultCode.InterfaceError.getCode().equals(result.getCode())){
					logger.info("调用接口异常！");
					result.setRespMsg(ApiResultCode.InterfaceError);
					return result;
				}else if(ApiResultCode.DEMOAMOUNT_REDO.getCode().equals(result.getCode())){
					logger.info("调用成功,但增加模拟金额失败,需在GTS2重做redo");
					result.setRespMsg(ApiResultCode.DEMOAMOUNT_REDO);
					return result;
				}
			}
		} catch (Exception e) {
			logger.info("系统异常！", e);
			result = new ApiRespResult<String>();
			result.setRespMsg(ApiResultCode.EXCEPTION);
			return result;
		}
		return result;
	}
	
	/**
	 * 查询账号模拟金额
	 * @param actCustomerInfo
	 * @return
	 */
	public BigDecimal getCustomerAvailabelCredit(ActCustomerInfo actCustomerInfo) {
		try {
			return actGts2DemoApiManager.getDemoCustomerAvailabelCredit(actCustomerInfo);
		} catch (Exception e) {
			logger.info("系统异常！", e);
		}
		return null;
	}
}
