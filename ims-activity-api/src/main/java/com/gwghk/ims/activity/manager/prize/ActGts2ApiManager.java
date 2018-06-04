package com.gwghk.ims.activity.manager.prize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.activity.dto.Gts2BonusProposalReqDto;
import com.gwghk.ims.activity.dto.Gts2ResultDetailDto;
import com.gwghk.ims.activity.dto.Gts2ResultDto;
import com.gwghk.ims.activity.dto.Gts2ResultErrorDto;
import com.gwghk.ims.activity.manager.Gts2ApiCommonManager;
import com.gwghk.ims.activity.util.Gts2EncryptUtil;
import com.gwghk.ims.activity.util.HttpUtil;
import com.gwghk.ims.common.dto.activity.BonusReqDTO;
import com.gwghk.ims.common.dto.activity.Gts2CashAdjustReqDto;
import com.gwghk.ims.common.dto.activity.Gts2PrincipalReqDto;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActPlatformEnum;
import com.gwghk.ims.common.enums.ActThirdCallEnum;
import com.gwghk.unify.framework.common.util.BeanUtils;
import com.gwghk.unify.framework.common.util.IPUtil;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: ActGts2ApiManager
 * @Description: GTS2的API调用
 * @author lawrence
 * @date 2017年5月23日
 *
 */
@Service("actGts2ApiManager")
public class ActGts2ApiManager extends ActApiManager {

	private static Logger logger = LoggerFactory.getLogger(ActGts2ApiManager.class);

	@Autowired
	private Gts2ApiCommonManager gts2ApiCommonManager;

	@Value("${gts2realapi.url}")
	private String realApiUrl;

	@Value("${gts2realapi.loginName}")
	private String realApiloginName;

	@Value("${gts2realapi.invoker}")
	private String realApiInvoker;

	@Value("${gts2realapi.key}")
	private String realapiKey;

	
	/**
	 * GTS2-REAL-查询帐户信息
	 */
    public final static String  GTS2_API_URL_GETCUSTOMERINFOBYACCOUNT_RO = "account_ro/getCustomerAccountView";

	/**
	 * 
	 * @MethodName: demoCashAdjust
	 * @Description: 调用GTS2的模拟账号金额调整接口
	 * @param actCustomerInfo
	 * @param payAmount
	 * @return void
	 */
	public void demoCashAdjust(ActCustomerInfo actCustomerInfo, BigDecimal payAmount) {
		String companyId = actCustomerInfo.getCompanyId().toString();
		String gts2PrincipalReqDtoStr = gts2ApiCommonManager.initDemoGts2Principal(companyId);

		Gts2CashAdjustReqDto gts2CashAdjustReqDto = new Gts2CashAdjustReqDto();
		gts2CashAdjustReqDto.setIsAutoApprove(true);
		gts2CashAdjustReqDto.setPayAmount(payAmount);
		gts2CashAdjustReqDto.setPayCurrency(actCustomerInfo.getAccountCurrency());
		gts2CashAdjustReqDto.setTransAmount(payAmount);
		gts2CashAdjustReqDto.setTransCurrency(actCustomerInfo.getAccountCurrency());
		gts2CashAdjustReqDto.setAccountNo(actCustomerInfo.getAccountNo());
		gts2CashAdjustReqDto.setPlatform(actCustomerInfo.getPlatform());
		gts2CashAdjustReqDto.setAdjustType("302");
		String gts2CashAdjustReqDtoStr = JsonUtil.obj2Str(gts2CashAdjustReqDto);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cashinAdjust", gts2CashAdjustReqDtoStr);
		params.put("args", "[]");
		params.put("_principal_", gts2PrincipalReqDtoStr);

		String signature = Gts2EncryptUtil.sign(gts2ApiCommonManager.getDemoApiKey(companyId.toString()), params);
		try {
			params.put("_signature_", signature);
			HttpUtil.net(gts2ApiCommonManager.demoApiUrl + "cash/addCashAdjust", params, "GET");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(">>>>>>>>>>>>调用GTS2的API出错err={}", e.getMessage());
		}
	}

	/**
	 * 
	 * @MethodName: getDemoCustomerAvailabelCredit
	 * @Description: 获取Gts2模拟账号的账户金额
	 * @param actCustomerInfo
	 * @return void
	 */
	public BigDecimal getDemoCustomerAvailabelCredit(ActCustomerInfo actCustomerInfo) {
		BigDecimal availableCredit = BigDecimal.ZERO;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", actCustomerInfo.getAccountNo());
		params.put("platform", actCustomerInfo.getPlatform());
		params.put("isGetCredit", true);
		params.put("freezeAmount", 0);
		params.put("args", "[]");
		String companyId = actCustomerInfo.getCompanyId().toString();
		String gts2PrincipalReqDtoStr = gts2ApiCommonManager.initDemoGts2Principal(companyId);
		params.put("_principal_", gts2PrincipalReqDtoStr);
		String signature = Gts2EncryptUtil.sign(gts2ApiCommonManager.getDemoApiKey(companyId), params);
		params.put("_signature_", signature);
		String httpReturnValue = null;
		try {
			httpReturnValue = HttpUtil.net(gts2ApiCommonManager.demoApiUrl + "account/getCustomerAccountView", params, "GET");
			logger.info("gts2 api return value:" + httpReturnValue);
			if (StringUtil.isNotEmpty(httpReturnValue)) {
				Map<String, Object> parseValue = JsonUtil.json2Map(httpReturnValue);
				if ("SUCCESS".equals(parseValue.get("code"))) {
					availableCredit = new BigDecimal(
							JsonUtil.json2Map(parseValue.get("result").toString()).get("availableCredit") + "");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(">>>>>>>>>>>>调用GTS2的API出错err={}", e.getMessage());
			availableCredit = null;
		}
		return availableCredit;
	}
	
	/**
	 * 
	 * @MethodName: realReleaseBonus
	 * @Description: 更新贈金可取额度
	 * @param actPrizeRecord
	 * @return
	 * @return String
	 */
	public String realReleaseBonus(VActPrizeRecord actPrizeRecord) {
		return realReleaseBonus(actPrizeRecord, null);
	}

	@Override
	public String realReleaseBonus(VActPrizeRecord actPrizeRecord, BigDecimal releasedAmount) {
		if(StringUtil.isEmpty(actPrizeRecord.getDealNumber())){
			return null;
		}
		boolean paramReleaseExit = false;
		if(releasedAmount!=null){
			paramReleaseExit = true;
		}
		String dealNumber = null;
		String companyId = actPrizeRecord.getCompanyId().toString();
		String gts2PrincipalReqDtoStr = initRealGts2Principal(companyId);

		Gts2BonusProposalReqDto gts2BonusProposalReqDto = new Gts2BonusProposalReqDto();
		gts2BonusProposalReqDto.setSource("IMS");
		//gts2BonusProposalReqDto.setRefId(actPrizeRecord.getOtherRecordNumber());
		gts2BonusProposalReqDto.setRefId(actPrizeRecord.getRecordNo());
		gts2BonusProposalReqDto.setBonusPno(actPrizeRecord.getDealNumber());
		
		BigDecimal oriBonusAmount = actPrizeRecord.getItemAmount();
		BigDecimal bonusAmount = actPrizeRecord.getItemAmount();
		BigDecimal finishedTradeLots = actPrizeRecord.getFinishedTradeLots();
		BigDecimal needTradeLots = actPrizeRecord.getNeedTradeLots();
		if (releasedAmount == null) {
			releasedAmount = BigDecimal.ZERO;
			if (finishedTradeLots.compareTo(needTradeLots) == 0) {
				releasedAmount = oriBonusAmount;
			} else {
				releasedAmount = oriBonusAmount.multiply(finishedTradeLots
									.divide(needTradeLots, 4, BigDecimal.ROUND_DOWN));
			}
		} else {
			if (oriBonusAmount.compareTo(releasedAmount) < 0) {
				logger.error("释放金额大于贈金金额，参数错误,订单号{}", actPrizeRecord.getRecordNo());
				return null;
			}
		}
		actPrizeRecord.setReleasedBonus(releasedAmount.setScale(2, BigDecimal.ROUND_DOWN));
		
		bonusAmount = oriBonusAmount.multiply(actPrizeRecord.getExchangeRate() != null ? actPrizeRecord.getExchangeRate() : BigDecimal.ONE)
				.setScale(2, BigDecimal.ROUND_DOWN);
		if(!paramReleaseExit){
			releasedAmount = releasedAmount.multiply(actPrizeRecord.getExchangeRate() != null ? actPrizeRecord.getExchangeRate() : BigDecimal.ONE)
					.setScale(2, BigDecimal.ROUND_DOWN);
		}
		gts2BonusProposalReqDto.setBonusAmount(bonusAmount);
		gts2BonusProposalReqDto.setReleasedAmount(releasedAmount);
		gts2BonusProposalReqDto.setLockedAmount(
				oriBonusAmount.subtract(releasedAmount).setScale(2, BigDecimal.ROUND_DOWN));

		gts2BonusProposalReqDto.setIsAutoApprove(true);
		gts2BonusProposalReqDto.setRemark(actPrizeRecord.getActName() + "(" + actPrizeRecord.getActNo() + ")");
		String gts2BonusProposalReqDtoStr = JsonUtil.obj2Str(gts2BonusProposalReqDto);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bonusProposal", gts2BonusProposalReqDtoStr);
		params.put("args", "[]");
		params.put("_principal_", gts2PrincipalReqDtoStr);

		String signature = Gts2EncryptUtil.sign(realapiKey, params);
		String httpReturnValue = null;

		try {
			params.put("_signature_", signature);
			logger.info(">>>>>>>>>>>>调用gts2释放可取金额接口,参数:{}", gts2BonusProposalReqDtoStr);
			httpReturnValue = HttpUtil.net(realApiUrl + "cash/releaseBonus", params, "GET");
			logger.debug(">>>>>>>>>>>>gts2 api return value:" + httpReturnValue);
		} catch (Exception e) {
			logger.error(">>>>>>>>>>>>调用GTS2的API出错err={}", e.getMessage());
		}

		if (StringUtil.isNotEmpty(httpReturnValue)) {
			try {
				Gts2ResultDto gts2ResultDto = JsonUtil.json2Obj(httpReturnValue, Gts2ResultDto.class);
				if ("SUCCESS".equals(gts2ResultDto.getCode())) {
					Gts2ResultDetailDto gts2ResultDetailDto = gts2ResultDto.getResult();
					if ("OK".equals(gts2ResultDetailDto.getCode())) {
						dealNumber = gts2ResultDetailDto.getResult();
					} else if ("FAIL".equals(gts2ResultDetailDto.getCode())) {
						Gts2ResultErrorDto gts2ResultErrorDto = gts2ResultDetailDto.getError();
						// 出现重复记录
						if (gts2ResultErrorDto != null && "102".equals(gts2ResultErrorDto.getCode())) {
							dealNumber = gts2ResultDetailDto.getResult();
						}
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error(">>>>>>>>>>>>解析Gts2的API返回的数据出错err={}", e.getMessage());
			}
		}
		return dealNumber;
	}
	
	/**
	 * 
	 * @MethodName: realCancelBonus
	 * @Description: 批量取消已经发放的贈金
	 * @param listActPrizeRecord
	 * @return
	 * @return Map<String,Map<String,String>>
	 */
	public Map<String, Map<String, String>> realCancelBonus(List<VActPrizeRecord> listActPrizeRecord,
			String cancelReason) {
		Map<String, Map<String, String>> execResult = new HashMap<String, Map<String, String>>();
		String companyId = listActPrizeRecord.get(0).getCompanyId().toString();
		String gts2PrincipalReqDtoStr = initRealGts2Principal(companyId);
		List<String> dealNumbers = new ArrayList<String>();
		List<Gts2BonusProposalReqDto> listGts2BonusProposalReqDto = new ArrayList<Gts2BonusProposalReqDto>();
		Gts2BonusProposalReqDto gts2BonusProposalReqDto = null;
		for (VActPrizeRecord actPrizeRecordItem : listActPrizeRecord) {
			gts2BonusProposalReqDto = new Gts2BonusProposalReqDto();
			gts2BonusProposalReqDto.setSource("IMS");
			gts2BonusProposalReqDto.setBonusPno(actPrizeRecordItem.getDealNumber());
			gts2BonusProposalReqDto.setIsAutoApprove(true);
			gts2BonusProposalReqDto.setRefId(actPrizeRecordItem.getRecordNo());
			gts2BonusProposalReqDto.setRemark(
					actPrizeRecordItem.getActName() + "(" + actPrizeRecordItem.getActNo() + ")");
			gts2BonusProposalReqDto.setCancelReason(cancelReason+"(" + actPrizeRecordItem.getActNo() + ")");
			listGts2BonusProposalReqDto.add(gts2BonusProposalReqDto);
			dealNumbers.add(actPrizeRecordItem.getDealNumber());
		}

		String gts2BonusProposalReqDtoStr = JsonUtil.obj2Str(listGts2BonusProposalReqDto);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bonusProposals", gts2BonusProposalReqDtoStr);
		params.put("args", "[]");
		params.put("_principal_", gts2PrincipalReqDtoStr);

		String signature = Gts2EncryptUtil.sign(realapiKey, params);
		String httpReturnValue = null;

		try {
			params.put("_signature_", signature);
			logger.info(">>>>>>>>>>>>调用gts2取消贈金接口,参数:{}>>>>>>>>>>>>", JsonUtil.obj2Str(dealNumbers));
			httpReturnValue = HttpUtil.net(realApiUrl + "cash/cancelBonus", params, "POST");
			logger.debug(">>>>>>>>>>>>gts2 api return value:" + httpReturnValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(">>>>>>>>>>>>调用GTS2的API出错err={}", e.getMessage());
		}

		if (StringUtil.isNotEmpty(httpReturnValue)) {
			try {
				Gts2ResultDto gts2ResultDto = JsonUtil.json2Obj(httpReturnValue, Gts2ResultDto.class);
				if ("SUCCESS".equals(gts2ResultDto.getCode())) {
					Gts2ResultDetailDto gts2ResultDetailDto = gts2ResultDto.getResult();
					execResult.put("success", gts2ResultDetailDto.getContext());
					Map<String, Gts2ResultErrorDto> fieldErrors = gts2ResultDetailDto.getFieldErrors();
					if (fieldErrors != null && !fieldErrors.isEmpty()) {
						Map<String, String> errorResults = new HashMap<String, String>();
						execResult.put("errors", errorResults);
						for (Map.Entry<String, Gts2ResultErrorDto> entryItem : fieldErrors.entrySet()) {
							// 出现该记录已完結提示,重复调用出现(1094:该记录已完結)
							if ("102".equals(entryItem.getValue().getCode())
									|| "1094".equals(entryItem.getValue().getCode())) {
								errorResults.put(entryItem.getKey(), null);
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error(">>>>>>>>>>>>解析Gts2的API返回的数据出错err={}", e.getMessage());
			}
		}
		return execResult;
	}

	/**
	 * 
	 * @MethodName: realAddBonus
	 * @Description: 向GTS2请求发放贈金,或者释放未釋放金額 (贈金可取金额发生变化)
	 * @param actPrizeRecord
	 * @return
	 * @return String
	 */
	public String realAddBonus(VActPrizeRecord actPrizeRecord) {

		String dealNumber = null;
		String companyId = actPrizeRecord.getCompanyId().toString();
		String gts2PrincipalReqDtoStr = initRealGts2Principal(companyId);

		Gts2BonusProposalReqDto gts2BonusProposalReqDto = new Gts2BonusProposalReqDto();
		gts2BonusProposalReqDto.setSource("IMS");
		gts2BonusProposalReqDto.setRefId(actPrizeRecord.getRecordNo());
		gts2BonusProposalReqDto.setPlatform(actPrizeRecord.getPlatform());
		if(ActEnvEnum.DEMO.getValue().equals(actPrizeRecord.getEnv())){
			gts2BonusProposalReqDto.setAccountNo(actPrizeRecord.getAccountNo());
			gts2BonusProposalReqDto.setPlatform(ActPlatformEnum.GTS2.getCode());
		}else{
			gts2BonusProposalReqDto.setAccountNo(actPrizeRecord.getAccountNo());
		}

		BigDecimal oriBonusAmount = actPrizeRecord.getItemAmount();
		BigDecimal bonusAmount =  BigDecimal.ZERO;
		BigDecimal releasedAmount = BigDecimal.ZERO;
		BigDecimal finishedTradeLots = actPrizeRecord.getFinishedTradeLots();
		BigDecimal needTradeLots = actPrizeRecord.getNeedTradeLots();
		//代币不允许释放
		if(ActItemTypeEnum.TOKENCOIN.getCode().equals(actPrizeRecord.getItemType())){
			releasedAmount = BigDecimal.ZERO;
			gts2BonusProposalReqDto.setExpiryDate(actPrizeRecord.getUseEndTime());
		}else{
			// 非法数据，正常情况已经完成手数是一定小于或者等于要求完成的手数
			if (finishedTradeLots.compareTo(needTradeLots) > 0) {
				return null;
			}
			// releaseType=1时，直接做释放;=2时，要求手数=完成手数，才做释放;=3时，不管要求手数与完成手数是否相等，都不做释放,=4时，要求手数与完成手数相等时，才做释放
			// 2表示要达到手数后才能可取
			if (actPrizeRecord.getReleaseType() == null || actPrizeRecord.getReleaseType() != 3) {
				boolean limitAdd = (actPrizeRecord.getReleaseType() != null
						&& (actPrizeRecord.getReleaseType() == 2 || actPrizeRecord.getReleaseType() == 4));
				if (finishedTradeLots.compareTo(needTradeLots) == 0) {
					releasedAmount = oriBonusAmount;
				} else {
					if (!limitAdd) {
						releasedAmount = oriBonusAmount
								.multiply(finishedTradeLots
										.divide(needTradeLots, 4, BigDecimal.ROUND_DOWN));
					}
				}
			}
		}
		
		actPrizeRecord.setReleasedBonus(releasedAmount.setScale(2, BigDecimal.ROUND_DOWN));
		gts2BonusProposalReqDto.setBonusDesc(actPrizeRecord.getActName() + "(" + actPrizeRecord.getActNo() + ")");
		
		bonusAmount = oriBonusAmount.multiply(actPrizeRecord.getExchangeRate() != null ? actPrizeRecord.getExchangeRate() : BigDecimal.ONE)
				.setScale(2, BigDecimal.ROUND_DOWN);
		releasedAmount = releasedAmount.multiply(actPrizeRecord.getExchangeRate() != null ? actPrizeRecord.getExchangeRate() : BigDecimal.ONE)
				.setScale(2, BigDecimal.ROUND_DOWN);
		gts2BonusProposalReqDto.setBonusAmount(bonusAmount);
		gts2BonusProposalReqDto.setReleasedAmount(releasedAmount);
		gts2BonusProposalReqDto.setLockedAmount(
				oriBonusAmount.subtract(releasedAmount).setScale(2, BigDecimal.ROUND_DOWN));
		gts2BonusProposalReqDto.setAllowWithdraw(true);
		if(actPrizeRecord.getTransfer()!=null && actPrizeRecord.getTransfer().equals(0)){
			gts2BonusProposalReqDto.setAllowTransfer(false);
		}else{
			gts2BonusProposalReqDto.setAllowTransfer(true);
		}
		if(actPrizeRecord.getTurnGroup()!=null && actPrizeRecord.getTurnGroup().equals(0)){
			gts2BonusProposalReqDto.setAllowChangeAccountGroup(false);
		}else{
			gts2BonusProposalReqDto.setAllowChangeAccountGroup(true);
		}
		gts2BonusProposalReqDto.setIsAutoApprove(true);
		String gts2BonusProposalReqDtoStr = JsonUtil.obj2Str(gts2BonusProposalReqDto);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bonusProposal", gts2BonusProposalReqDtoStr);
		params.put("args", "[]");
		params.put("_principal_", gts2PrincipalReqDtoStr);

		String signature = Gts2EncryptUtil.sign(realapiKey, params);
		String httpReturnValue = null;

		try {
			params.put("_signature_", signature);
			logger.info(">>>>>>>>>>>>调用gts2增加贈金接口,参数:{}", gts2BonusProposalReqDtoStr);
			httpReturnValue = HttpUtil.net(realApiUrl + "cash/addBonus", params, "GET");
			logger.debug(">>>>>>>>>>>>gts2 api return value:" + httpReturnValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(">>>>>>>>>>>>调用GTS2的API出错err={}", e.getMessage());
		}

		if (StringUtil.isNotEmpty(httpReturnValue)) {
			try {
				Gts2ResultDto gts2ResultDto = JsonUtil.json2Obj(httpReturnValue, Gts2ResultDto.class);
				if ("SUCCESS".equals(gts2ResultDto.getCode())) {
					Gts2ResultDetailDto gts2ResultDetailDto = gts2ResultDto.getResult();
					if ("OK".equals(gts2ResultDetailDto.getCode())) {
						dealNumber = gts2ResultDetailDto.getResult();
					} else if ("FAIL".equals(gts2ResultDetailDto.getCode())) {
						Gts2ResultErrorDto gts2ResultErrorDto = gts2ResultDetailDto.getError();
						// 出现重复记录,GTS2会返回已生成的订单号 ,102(该记录已存在)
						if (gts2ResultErrorDto != null && "102".equals(gts2ResultErrorDto.getCode())) {
							dealNumber = gts2ResultDetailDto.getResult();
						}
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error(">>>>>>>>>>>>解析Gts2的API返回的数据出错err={}", e.getMessage());
			}
		}
		return dealNumber;
	}

	/**
	 * 
	 * @MethodName: initRealGts2Principal
	 * @Description: 初始化GTS2-real调用前的principal信息
	 * @param companyId
	 * @return
	 * @return String
	 */
	private String initRealGts2Principal(String companyId) {
		Gts2PrincipalReqDto gts2PrincipalReqDto = new Gts2PrincipalReqDto();
		gts2PrincipalReqDto.setLoginName(realApiloginName);
		gts2PrincipalReqDto.setRemoteIpAddress(IPUtil.getRealIp());
		gts2PrincipalReqDto.setInvoker(realApiInvoker);
		gts2PrincipalReqDto.setCompanyId(companyId);
		String gts2PrincipalReqDtoStr = JsonUtil.obj2Str(gts2PrincipalReqDto);
		return gts2PrincipalReqDtoStr;
	}
	
	@Override
	public String addBonus(BonusReqDTO bonusReqDto) {
		return addBonus(bonusReqDto,true);
	}
	
	@Override
	public String addBonus(BonusReqDTO bonusReqDto,boolean saveThird) {
		String dealNumber = null;
		String gts2PrincipalReqDtoStr = initRealGts2Principal(bonusReqDto.getCompanyId().toString());

		Gts2BonusProposalReqDto gts2BonusProposalReqDto = new Gts2BonusProposalReqDto();
		BeanUtils.copyExceptNull(gts2BonusProposalReqDto, bonusReqDto);

		gts2BonusProposalReqDto.setSource("IMS");
		gts2BonusProposalReqDto.setAllowWithdraw(true);
		if(bonusReqDto.getTransfer()!=null && bonusReqDto.getTransfer().equals(0)){
			gts2BonusProposalReqDto.setAllowTransfer(false);
		}else{
			gts2BonusProposalReqDto.setAllowTransfer(true);
		}
		if(bonusReqDto.getTurnGroup()!=null && bonusReqDto.getTurnGroup().equals(0)){
			gts2BonusProposalReqDto.setAllowChangeAccountGroup(false);
		}else{
			gts2BonusProposalReqDto.setAllowChangeAccountGroup(true);
		}
		gts2BonusProposalReqDto.setIsAutoApprove(true);
		String gts2BonusProposalReqDtoStr = JsonUtil.obj2Str(gts2BonusProposalReqDto);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bonusProposal", gts2BonusProposalReqDtoStr);
		params.put("args", "[]");
		params.put("_principal_", gts2PrincipalReqDtoStr);

		String signature = Gts2EncryptUtil.sign(realapiKey, params);
		String httpReturnValue = null;

		try {
			params.put("_signature_", signature);
			logger.info(">>>>>>>>>>>>调用gts2增加贈金接口,参数:{}", gts2BonusProposalReqDtoStr);
			httpReturnValue = HttpUtil.net(realApiUrl + "cash/addBonus", params, "GET");
			logger.debug(">>>>>>>>>>>>gts2 api return value:" + httpReturnValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(">>>>>>>>>>>>调用GTS2的API出错err={}", e.getMessage());
		}

		if (StringUtil.isNotEmpty(httpReturnValue)) {
			try {
				Gts2ResultDto gts2ResultDto = JsonUtil.json2Obj(httpReturnValue, Gts2ResultDto.class);
				if ("SUCCESS".equals(gts2ResultDto.getCode())) {
					Gts2ResultDetailDto gts2ResultDetailDto = gts2ResultDto.getResult();
					if ("OK".equals(gts2ResultDetailDto.getCode())) {
						dealNumber = gts2ResultDetailDto.getResult();
					} else if ("FAIL".equals(gts2ResultDetailDto.getCode())) {
						Gts2ResultErrorDto gts2ResultErrorDto = gts2ResultDetailDto.getError();
						// 出现重复记录,GTS2会返回已生成的订单号 ,102(该记录已存在)
						if (gts2ResultErrorDto != null && "102".equals(gts2ResultErrorDto.getCode())) {
							dealNumber = gts2ResultDetailDto.getResult();
						}
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error(">>>>>>>>>>>>解析Gts2的API返回的数据出错err={}", e.getMessage());
			}
		}
		if(saveThird){
			saveThirdCallRecord(bonusReqDto, dealNumber, ActThirdCallEnum.BONUS_ADD_BONUS);
		}
		return dealNumber;
	}

	@Override
	public String releaseBonus(BonusReqDTO bonusReqDto) {
		return releaseBonus(bonusReqDto,true);
	}
	
	@Override
	public String releaseBonus(BonusReqDTO bonusReqDto,boolean saveThird) {
		String dealNumber = null;
		String gts2PrincipalReqDtoStr = initRealGts2Principal(bonusReqDto.getCompanyId().toString());

		Gts2BonusProposalReqDto gts2BonusProposalReqDto = new Gts2BonusProposalReqDto();
		BeanUtils.copyExceptNull(gts2BonusProposalReqDto, bonusReqDto);
		gts2BonusProposalReqDto.setSource("IMS");
		gts2BonusProposalReqDto.setRefId(bonusReqDto.getRefId());
		gts2BonusProposalReqDto.setIsAutoApprove(true);

		String gts2BonusProposalReqDtoStr = JsonUtil.obj2Str(gts2BonusProposalReqDto);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bonusProposal", gts2BonusProposalReqDtoStr);
		params.put("args", "[]");
		params.put("_principal_", gts2PrincipalReqDtoStr);

		String signature = Gts2EncryptUtil.sign(realapiKey, params);
		String httpReturnValue = null;

		try {
			params.put("_signature_", signature);
			logger.info(">>>>>>>>>>>>调用gts2释放可取金额接口,参数:{}", gts2BonusProposalReqDtoStr);
			httpReturnValue = HttpUtil.net(realApiUrl + "cash/releaseBonus", params, "GET");
			logger.debug(">>>>>>>>>>>>gts2 api return value:" + httpReturnValue);
		} catch (Exception e) {
			logger.error(">>>>>>>>>>>>调用GTS2的API出错err={}", e.getMessage());
		}

		if (StringUtil.isNotEmpty(httpReturnValue)) {
			try {
				Gts2ResultDto gts2ResultDto = JsonUtil.json2Obj(httpReturnValue, Gts2ResultDto.class);
				if ("SUCCESS".equals(gts2ResultDto.getCode())) {
					Gts2ResultDetailDto gts2ResultDetailDto = gts2ResultDto.getResult();
					if ("OK".equals(gts2ResultDetailDto.getCode())) {
						dealNumber = gts2ResultDetailDto.getResult();
					} else if ("FAIL".equals(gts2ResultDetailDto.getCode())) {
						Gts2ResultErrorDto gts2ResultErrorDto = gts2ResultDetailDto.getError();
						// 出现重复记录
						if (gts2ResultErrorDto != null && "102".equals(gts2ResultErrorDto.getCode())) {
							dealNumber = gts2ResultDetailDto.getResult();
						}
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error(">>>>>>>>>>>>解析Gts2的API返回的数据出错err={}", e.getMessage());
			}
		}
		if(saveThird){
			saveThirdCallRecord(bonusReqDto, dealNumber, ActThirdCallEnum.BONUS_RELEASE_BONUS);
		}
		return dealNumber;
	}

	@Override
	public Map<String, Map<String, String>> cancelBonus(List<BonusReqDTO> listBonusReqDto) {
		return cancelBonus(listBonusReqDto,true);
	}
	
	@Override
	public Map<String, Map<String, String>> cancelBonus(List<BonusReqDTO> listBonusReqDto,boolean saveThird) {
		Map<String, Map<String, String>> execResult = new HashMap<String, Map<String, String>>();
		String gts2PrincipalReqDtoStr = initRealGts2Principal(listBonusReqDto.get(0).getCompanyId().toString());
		List<String> dealNumbers = new ArrayList<String>();
		List<Gts2BonusProposalReqDto> listGts2BonusProposalReqDto = new ArrayList<Gts2BonusProposalReqDto>();
		Gts2BonusProposalReqDto gts2BonusProposalReqDto = null;
		for (BonusReqDTO bonusReqDto : listBonusReqDto) {
			gts2BonusProposalReqDto = new Gts2BonusProposalReqDto();
			gts2BonusProposalReqDto.setSource("IMS");
			gts2BonusProposalReqDto.setBonusPno(bonusReqDto.getBonusPno());
			gts2BonusProposalReqDto.setIsAutoApprove(true);
			gts2BonusProposalReqDto.setRefId(bonusReqDto.getRefId());
			gts2BonusProposalReqDto.setRemark(bonusReqDto.getRemark());
			gts2BonusProposalReqDto.setCancelReason(bonusReqDto.getCancelReason());
		
			listGts2BonusProposalReqDto.add(gts2BonusProposalReqDto);
			dealNumbers.add(gts2BonusProposalReqDto.getBonusPno());
		}

		String gts2BonusProposalReqDtoStr = JsonUtil.obj2Str(listGts2BonusProposalReqDto);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bonusProposals", gts2BonusProposalReqDtoStr);
		params.put("args", "[]");
		params.put("_principal_", gts2PrincipalReqDtoStr);

		String signature = Gts2EncryptUtil.sign(realapiKey, params);
		String httpReturnValue = null;

		try {
			params.put("_signature_", signature);
			logger.info(">>>>>>>>>>>>调用gts2取消贈金接口,参数:{}>>>>>>>>>>>>", JsonUtil.obj2Str(dealNumbers));
			httpReturnValue = HttpUtil.net(realApiUrl + "cash/cancelBonus", params, "POST");
			logger.debug(">>>>>>>>>>>>gts2 api return value:" + httpReturnValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(">>>>>>>>>>>>调用GTS2的API出错err={}", e.getMessage());
		}

		if (StringUtil.isNotEmpty(httpReturnValue)) {
			try {
				Gts2ResultDto gts2ResultDto = JsonUtil.json2Obj(httpReturnValue, Gts2ResultDto.class);
				if ("SUCCESS".equals(gts2ResultDto.getCode())) {
					Gts2ResultDetailDto gts2ResultDetailDto = gts2ResultDto.getResult();
					execResult.put("success", gts2ResultDetailDto.getContext());
					Map<String, Gts2ResultErrorDto> fieldErrors = gts2ResultDetailDto.getFieldErrors();
					if (fieldErrors != null && !fieldErrors.isEmpty()) {
						Map<String, String> errorResults = new HashMap<String, String>();
						execResult.put("errors", errorResults);
						for (Map.Entry<String, Gts2ResultErrorDto> entryItem : fieldErrors.entrySet()) {
							// 出现该记录已完結提示,重复调用出现(1094:该记录已完結)
							if ("102".equals(entryItem.getValue().getCode())
									|| "1094".equals(entryItem.getValue().getCode())) {
								errorResults.put(entryItem.getKey(), null);
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error(">>>>>>>>>>>>解析Gts2的API返回的数据出错err={}", e.getMessage());
			}
		}
		
		if(saveThird){
			Map<String, String> result = execResult.get("success");
			saveThirdCallRecord(listBonusReqDto, result, ActThirdCallEnum.BONUS_CANCEL_BONUS);
		}
		return execResult;
	}
	
    /**
     * 根据账号获取当前账号信息
     * @param accountNo
     */
   /* public ApiRespResult<Gts2AccountInfoDTO> getCustomerAccountView(String accountNo, String platform, Long companyId) {
        String url = realApiUrl + GTS2_API_URL_GETCUSTOMERINFOBYACCOUNT_RO;
        String gts2PrincipalReqDtoStr = initRealGts2Principal(companyId.toString());
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountNo", accountNo);
		map.put("platform", platform);
		map.put("args", "[]");
		map.put("isGetCredit", "");
		map.put("freezeAmount", "");
		map.put("_principal_", gts2PrincipalReqDtoStr);
		String sign = Gts2EncryptUtil.sign(realapiKey, map);
		map.put("_signature_", sign);
		ApiRespResult<Gts2AccountInfoDTO> apiRespResult = new ApiRespResult<Gts2AccountInfoDTO>();
        try{
        	String apiResult = HttpUtil.net(url, map, "GET");
            if (StringUtils.isNotBlank(apiResult)) {
                JSONObject respJson = (JSONObject) JSONObject.parse(apiResult);
                if ("SUCCESS".equalsIgnoreCase(respJson.getString("code")) && StringUtil.isNotEmpty(respJson.getString("result"))) {
                	JSONObject resultJson = respJson.getJSONObject("result");
                	Gts2AccountInfoDTO resultDTO = new Gts2AccountInfoDTO();
                	resultDTO.setAccountNo(resultJson.getString("accountNo"));
                	resultDTO.setCurrency(resultJson.getString("currency"));
                    String mobile = resultJson.getString("mobilePhone");
                    resultDTO.setGuestPhone(MobileUtil.getNoPreMobilePhoneNum(mobile));
                    resultDTO.setGuestEmail(resultJson.getString("email"));
                    resultDTO.setGuestName(resultJson.getString("chineseName"));
                    resultDTO.setNationality(resultJson.getString("nationality"));
                    resultDTO.setAccountGroupId(resultJson.getInteger("accountGroupId"));
                    resultDTO.setGts2AccountId(resultJson.getLong("gts2AccountId"));
                    resultDTO.setAgentCode(resultJson.getString("agentCode"));
                    JSONObject activateTimeJson = resultJson.getJSONObject("activateTime");// 激活时间
                    resultDTO.setPlatform(resultJson.getString("platform"));
                    resultDTO.setCompanyId(resultJson.getLong("companyId"));
                    resultDTO.setCustomerNumber(resultJson.getLong("customerNumber"));
                    if (activateTimeJson != null) {
                        Object timeObj = (Object) activateTimeJson.get("time");
                        if (timeObj != null) {
                            Calendar c = java.util.Calendar.getInstance();
                            c.setTimeInMillis(Long.parseLong(timeObj.toString()));
                            resultDTO.setActivateTime(c.getTime());
                        }
                    }
                    JSONObject createTimeJson = resultJson.getJSONObject("createDate");// 开户时间
                    if (createTimeJson != null) {
                        Object timeObj = (Object) createTimeJson.get("time");
                        if (timeObj != null) {
                            Calendar c = java.util.Calendar.getInstance();
                            c.setTimeInMillis(Long.parseLong(timeObj.toString()));
                            resultDTO.setRegTime(c.getTime());
                        }
                    }
                    apiRespResult.setData(resultDTO);
                    return apiRespResult;
                }
            }
        }catch(Exception e){
            logger.info("调用第三方接口:根据账号获取当前账号信息异常{}",e);
    		apiRespResult.setRespMsg(ApiResultCode.InterfaceError);
    		return apiRespResult;
        }
        logger.info("账号不存在！");
		apiRespResult.setRespMsg(ApiResultCode.Err70021, "真实");
		return apiRespResult;
    }*/
    
    
    public static void main(String[] args) {
//		Map<String, Map<String, String>> execResult = new HashMap<String, Map<String, String>>();
//		String value="{\"code\":\"SUCCESS\",\"result\":{\"code\":\"FAIL\",\"context\":{\"BA180123E46425\":\"BC180211F08144\",\"BA180131E50999\":\"BC180211F08145\",\"BA180131E51000\":\"BC180211F08146\"},\"error\":{\"code\":\"FAIL\",\"message\":\"has errors,please check it!\"},\"errors\":[],\"fieldErrors\":{\"BA180123E46425\":{\"code\":\"102\",\"message\":\"该记录已存在\"},\"BA180131E50999\":{\"code\":\"102\",\"message\":\"该记录102\",\"message\":\"该记录已存在\"}},\"multiResult\":true,\"ok\":false,\"result\":null,\"results\":[]}}";
//		if (StringUtil.isNotEmpty(value)) {
//			try {
//				Gts2ResultDto gts2ResultDto = JsonUtil.json2Obj(value, Gts2ResultDto.class);
//				if ("SUCCESS".equals(gts2ResultDto.getCode())) {
//					Gts2ResultDetailDto gts2ResultDetailDto = gts2ResultDto.getResult();
//					execResult.put("success", gts2ResultDetailDto.getContext());
//					Map<String, Gts2ResultErrorDto> fieldErrors = gts2ResultDetailDto.getFieldErrors();
//					if (fieldErrors != null && !fieldErrors.isEmpty()) {
//						Map<String, String> errorResults = new HashMap<String, String>();
//						execResult.put("errors", errorResults);
//						for (Map.Entry<String, Gts2ResultErrorDto> entryItem : fieldErrors.entrySet()) {
//							// 出现该记录已完結提示,重复调用出现(1094:该记录已完結)
//							if ("102".equals(entryItem.getValue().getCode())
//									|| "1094".equals(entryItem.getValue().getCode())) {
//								errorResults.put(entryItem.getKey(), null);
//							}
//						}
//					}
//				}
//			} catch (Exception e) {
//				logger.error(e.getMessage(), e);
//				logger.error(">>>>>>>>>>>>解析Gts2的API返回的数据出错err={}", e.getMessage());
//			}
//		}
//		System.out.println(execResult);
    	
    	
	}
   
	
}
