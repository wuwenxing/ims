package com.gwghk.ims.activity.manager.prize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.activity.dto.HxMt4BonusReqDto;
import com.gwghk.ims.activity.dto.HxMt4RespDto;
import com.gwghk.ims.activity.dto.HxMt4RespMapDto;
import com.gwghk.ims.activity.util.HttpUtil;
import com.gwghk.ims.common.dto.activity.BonusReqDTO;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActThirdCallEnum;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

@Service("actHxMt4ApiManager")
public class ActHxMt4ApiManager extends ActApiManager {

	private static Logger logger = LoggerFactory.getLogger(ActHxMt4ApiManager.class);

	@Value("${hxmt4api.url}")
	private String hxmt4apiUrl;

	@Value("${hxmt4api.apiLogin}")
	private String hxmt4ApiLogin;

	@Value("${hxmt4api.apiPassword}")
	private String hxmt4apiPassword;

	private String sid;

	private Date pGetSidDate;

	/**
	 * 获取HXMT4登录会话id
	 * @return
	 */
	public String getSid() {
		Calendar nowCal = Calendar.getInstance();
		nowCal.add(Calendar.MINUTE, -15);
		// 每隔15分钟重新获取sid
		if (pGetSidDate == null || nowCal.getTime().compareTo(pGetSidDate) > 0) {
			String httpReturnValue = null;
			try {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("apiLogin", hxmt4ApiLogin);
				params.put("apiPassword", hxmt4apiPassword);
				httpReturnValue = HttpUtil.net(hxmt4apiUrl + "members/login/", params, "GET");
				logger.debug(">>>>>>>>>>>>gts2 api return value:" + httpReturnValue);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error(">>>>>>>>>>>>调用HX-MT4的API出错err={}", e.getMessage());
			}

			if (StringUtil.isNotEmpty(httpReturnValue)) {
				try {
					HxMt4RespDto hxMt4RespDto = JsonUtil.json2Obj(httpReturnValue, HxMt4RespDto.class);
					if (hxMt4RespDto != null && StringUtil.isNotEmpty(hxMt4RespDto.getData())
							&& hxMt4RespDto.getStatus().equals("0")) {
						this.sid = hxMt4RespDto.getData();
						pGetSidDate = new Date();
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.error(">>>>>>>>>>>>解析HX-MT4的API返回的数据出错err={}", e.getMessage());
				}
			}
		}
		return this.sid;
	}
	
	@Override
	public void demoCashAdjust(ActCustomerInfo actCustomerInfo, BigDecimal payAmount) {
	}

	@Override
	public BigDecimal getDemoCustomerAvailabelCredit(ActCustomerInfo actCustomerInfo) {
		return null;
	}

	@Override
	public String realAddBonus(VActPrizeRecord actPrizeRecord) {
		if (StringUtil.isEmpty(this.sid)) {
			this.sid = getSid();
		}
		String dealNumber = null;
		HxMt4BonusReqDto hxMt4AddBonusDto = new HxMt4BonusReqDto();
		hxMt4AddBonusDto.setSid(this.sid);
		hxMt4AddBonusDto.setSource("IMS");
		hxMt4AddBonusDto.setRefOrder(actPrizeRecord.getRecordNo());
		hxMt4AddBonusDto
				.setBonusDesc(actPrizeRecord.getActName() + "(" + actPrizeRecord.getActNo() + ")");
		hxMt4AddBonusDto.setLogin(actPrizeRecord.getAccountNo());

		BigDecimal oriBonusAmount = actPrizeRecord.getItemAmount();
		BigDecimal bonusAmount = BigDecimal.ZERO;
		BigDecimal releasedAmount = BigDecimal.ZERO;
		BigDecimal finishedTradeLots = actPrizeRecord.getFinishedTradeLots();
		BigDecimal needTradeLots = actPrizeRecord.getNeedTradeLots();

		//代币不允许释放
		if(ActItemTypeEnum.TOKENCOIN.getCode().equals(actPrizeRecord.getItemType())){
				releasedAmount = BigDecimal.ZERO;
				hxMt4AddBonusDto.setExpiryDate(actPrizeRecord.getUseEndTime());
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
								.multiply(finishedTradeLots.divide(needTradeLots, 4, BigDecimal.ROUND_DOWN));
					}
				}
			}
		}
		actPrizeRecord.setReleasedBonus(releasedAmount.setScale(2, BigDecimal.ROUND_DOWN));
		bonusAmount = oriBonusAmount.setScale(2, BigDecimal.ROUND_DOWN);
		releasedAmount = releasedAmount.setScale(2, BigDecimal.ROUND_DOWN);
		BigDecimal lockAmount = oriBonusAmount.subtract(releasedAmount).setScale(2, BigDecimal.ROUND_DOWN);

		if (releasedAmount.compareTo(BigDecimal.ZERO) == 0) {
			releasedAmount = BigDecimal.ZERO;
		}

		if (lockAmount.compareTo(BigDecimal.ZERO) == 0) {
			lockAmount = BigDecimal.ZERO;
		}
		actPrizeRecord.setReleasedBonus(releasedAmount);
		hxMt4AddBonusDto.setBonusAmount(bonusAmount);
		hxMt4AddBonusDto.setReleasedAmount(releasedAmount);
		hxMt4AddBonusDto.setLockedAmount(lockAmount);
		hxMt4AddBonusDto.setIsAutoApprove("N");

		String hxMt4ReqDtoStr = JsonUtil.obj2Str(hxMt4AddBonusDto);
		Map<String, Object> params = JsonUtil.json2Map(hxMt4ReqDtoStr);
		String httpReturnValue = null;
		try {
			logger.info(">>>>>>>>>>>>调用HX-MT4增加贈金接口,参数:{}", hxMt4ReqDtoStr);
			httpReturnValue = HttpUtil.net(hxmt4apiUrl + "cash/addBonus", params, "POST");
			logger.debug(">>>>>>>>>>>>HX-MT4 api return value:" + httpReturnValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(">>>>>>>>>>>>调用HX-MT4的API出错err={}", e.getMessage());
		}

		if (StringUtil.isNotEmpty(httpReturnValue)) {
			try {
				HxMt4RespDto hxMt4RespDto = JsonUtil.json2Obj(httpReturnValue, HxMt4RespDto.class);
				if (hxMt4RespDto != null && hxMt4RespDto.getStatus().equals("0")) {
					dealNumber = hxMt4RespDto.getData();
				} else {
					if (hxMt4RespDto != null && "API007".equals(hxMt4RespDto.getStatus())) {
						pGetSidDate = null;
						getSid();
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error(">>>>>>>>>>>>解析HX-MT4的API返回的数据出错err={}", e.getMessage());
			}
		}
		return dealNumber;
	}

	@Override
	public String realReleaseBonus(VActPrizeRecord actPrizeRecord) {
		return realReleaseBonus(actPrizeRecord, null);
	}

	@Override
	public String realReleaseBonus(VActPrizeRecord actPrizeRecord, BigDecimal releasedAmount) {
		if (StringUtil.isEmpty(this.sid)) {
			this.sid = getSid();
		}
		if (StringUtil.isEmpty(actPrizeRecord.getDealNumber())) {
			return null;
		}
		boolean paramReleaseExit = false;
		if (releasedAmount != null) {
			paramReleaseExit = true;
		}
		String dealNumber = null;
		HxMt4BonusReqDto hxMt4AddBonusDto = new HxMt4BonusReqDto();
		hxMt4AddBonusDto.setSid(this.sid);
		hxMt4AddBonusDto.setSource("IMS");
		hxMt4AddBonusDto.setRefOrder(actPrizeRecord.getOtherRecordNumber());
		hxMt4AddBonusDto.setBountyOrder(actPrizeRecord.getDealNumber());
		hxMt4AddBonusDto
				.setBonusDesc(actPrizeRecord.getActName() + "(" + actPrizeRecord.getActNo() + ")");

		BigDecimal oriBonusAmount = actPrizeRecord.getItemAmount();
		BigDecimal bonusAmount = actPrizeRecord.getItemAmount();
		BigDecimal finishedTradeLots = actPrizeRecord.getFinishedTradeLots();
		BigDecimal needTradeLots = actPrizeRecord.getNeedTradeLots();
		if (releasedAmount == null) {
			releasedAmount = BigDecimal.ZERO;
			if (finishedTradeLots.compareTo(needTradeLots) == 0) {
				releasedAmount = oriBonusAmount;
			} else {
				releasedAmount = oriBonusAmount
						.multiply(finishedTradeLots.divide(needTradeLots, 4, BigDecimal.ROUND_DOWN));
			}
		} else {
			if (oriBonusAmount.compareTo(releasedAmount) < 0) {
				logger.error("释放金额大于贈金金额，参数错误,订单号{}", actPrizeRecord.getRecordNo());
				return null;
			}
		}
		actPrizeRecord.setReleasedBonus(releasedAmount.setScale(2, BigDecimal.ROUND_DOWN));

		bonusAmount = oriBonusAmount.setScale(2, BigDecimal.ROUND_DOWN);
		if (!paramReleaseExit) {
			releasedAmount = releasedAmount.setScale(2, BigDecimal.ROUND_DOWN);
		}

		if (releasedAmount.compareTo(BigDecimal.ZERO) == 0) {
			releasedAmount = BigDecimal.ZERO;
		}
		BigDecimal lockAmount = oriBonusAmount.subtract(releasedAmount).setScale(2, BigDecimal.ROUND_DOWN);
		if (lockAmount.compareTo(BigDecimal.ZERO) == 0) {
			lockAmount = BigDecimal.ZERO;
		}

		actPrizeRecord.setReleasedBonus(releasedAmount);
		hxMt4AddBonusDto.setBonusAmount(bonusAmount);
		hxMt4AddBonusDto.setReleasedAmount(releasedAmount);
		hxMt4AddBonusDto.setLockedAmount(lockAmount);
		hxMt4AddBonusDto.setRemark(actPrizeRecord.getActName() + "(" + actPrizeRecord.getActNo() + ")");
		hxMt4AddBonusDto.setIsAutoApprove("N");

		String hxMt4ReqDtoStr = JsonUtil.obj2Str(hxMt4AddBonusDto);
		Map<String, Object> params = JsonUtil.json2Map(hxMt4ReqDtoStr);

		String httpReturnValue = null;
		try {
			logger.info(">>>>>>>>>>>>调用HX-MT4释放可取金额接口,参数:{}", hxMt4ReqDtoStr);
			httpReturnValue = HttpUtil.net(hxmt4apiUrl + "cash/releaseBonus", params, "POST");
			logger.debug(">>>>>>>>>>>>HX-MT4 api return value:" + httpReturnValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(">>>>>>>>>>>>调用HX-MT4的API出错err={}", e.getMessage());
		}

		if (StringUtil.isNotEmpty(httpReturnValue)) {
			try {
				HxMt4RespDto hxMt4RespDto = JsonUtil.json2Obj(httpReturnValue, HxMt4RespDto.class);
				if (hxMt4RespDto != null && hxMt4RespDto.getStatus().equals("0")) {
					dealNumber = hxMt4RespDto.getData();
				} else {
					if (hxMt4RespDto != null && "API007".equals(hxMt4RespDto.getStatus())) {
						pGetSidDate = null;
						getSid();
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error(">>>>>>>>>>>>解析HX-MT4的API返回的数据出错err={}", e.getMessage());
			}
		}
		return dealNumber;
	}
	
	@Override
	public Map<String, Map<String, String>> realCancelBonus(List<VActPrizeRecord> listActPrizeRecord,
			String cancelReason) {
		if (StringUtil.isEmpty(this.sid)) {
			this.sid = getSid();
		}
		Map<String, Map<String, String>> execResult = new HashMap<String, Map<String, String>>();
		Map<String, String> successMap = new HashMap<String, String>();
		HxMt4BonusReqDto hxMt4AddBonusDto = new HxMt4BonusReqDto();
		List<HxMt4BonusReqDto> listHxMt4AddBonusDto = new ArrayList<HxMt4BonusReqDto>();
		List<String> dealNumbers = new ArrayList<String>();
		for (VActPrizeRecord actPrizeRecordItem : listActPrizeRecord) {
			hxMt4AddBonusDto = new HxMt4BonusReqDto();
			hxMt4AddBonusDto.setSource("IMS");
			hxMt4AddBonusDto.setRefOrder(actPrizeRecordItem.getRecordNo());
			hxMt4AddBonusDto.setBountyOrder(actPrizeRecordItem.getDealNumber());
			hxMt4AddBonusDto.setDeductedReason(cancelReason + "(" + actPrizeRecordItem.getActNo() + ")");
			hxMt4AddBonusDto.setIsAutoApprove("N");
			listHxMt4AddBonusDto.add(hxMt4AddBonusDto);
			dealNumbers.add(actPrizeRecordItem.getDealNumber());
		}

		String hxMt4ReqDtoStr = JsonUtil.obj2Str(listHxMt4AddBonusDto);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bonus_proposal", hxMt4ReqDtoStr);
		params.put("sid", this.sid);
		String httpReturnValue = null;
		try {
			logger.info(">>>>>>>>>>>>调用HX-MT4取消贈金接口,参数:{}>>>>>>>>>>>>", JsonUtil.obj2Str(params));
			httpReturnValue = HttpUtil.net(hxmt4apiUrl + "cash/batchCancelBonus", params, "POST");
			logger.debug(">>>>>>>>>>>>HX-MT4 api return value:" + httpReturnValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(">>>>>>>>>>>>调用HX-MT4的API出错err={}", e.getMessage());
		}

		if (StringUtil.isNotEmpty(httpReturnValue)) {
			try {
				List<HxMt4RespMapDto> hxMt4RespDto = JsonUtil.json2List(httpReturnValue, HxMt4RespMapDto.class);
				if (hxMt4RespDto != null && !hxMt4RespDto.isEmpty()) {
					for (HxMt4RespMapDto itemDto : hxMt4RespDto) {
						if (itemDto.getStatus().equals("0")) {
							successMap.putAll(itemDto.getData());
						} else {
							if (hxMt4RespDto != null && "API007".equals(itemDto.getStatus())) {
								pGetSidDate = null;
								getSid();
							}
						}
					}
				}
				execResult.put("success", successMap);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error(">>>>>>>>>>>>解析HX-MT4的API返回的数据出错err={}", e.getMessage());
			}
		}
		return execResult;
	}

	public static void main(String[] args) {
		HxMt4BonusReqDto dto = new HxMt4BonusReqDto();
		dto.setBonusAmount(new BigDecimal(10));
		dto.setExpiryDate(new Date());
		System.out.println(JsonUtil.obj2Str(dto));
	}

	@Override
	public String addBonus(BonusReqDTO bonusReqDto) {
		return addBonus(bonusReqDto, true);
	}

	@Override
	public String addBonus(BonusReqDTO bonusReqDto, boolean saveThird) {
		if (StringUtil.isEmpty(this.sid)) {
			this.sid = getSid();
		}
		String dealNumber = null;
		HxMt4BonusReqDto hxMt4AddBonusDto = new HxMt4BonusReqDto();
		hxMt4AddBonusDto.setSid(this.sid);
		hxMt4AddBonusDto.setSource("IMS");
		hxMt4AddBonusDto.setRefOrder(bonusReqDto.getRecordNumber());
		hxMt4AddBonusDto.setBonusDesc(bonusReqDto.getBonusDesc());
		hxMt4AddBonusDto.setLogin(bonusReqDto.getAccountNo());
		hxMt4AddBonusDto.setBonusAmount(bonusReqDto.getBonusAmount());
		hxMt4AddBonusDto.setReleasedAmount(bonusReqDto.getReleasedAmount());
		hxMt4AddBonusDto.setLockedAmount(bonusReqDto.getLockedAmount());
		hxMt4AddBonusDto.setIsAutoApprove("N");

		String hxMt4ReqDtoStr = JsonUtil.obj2Str(hxMt4AddBonusDto);
		Map<String, Object> params = JsonUtil.json2Map(hxMt4ReqDtoStr);
		String httpReturnValue = null;
		try {
			logger.info(">>>>>>>>>>>>调用HX-MT4增加贈金接口,参数:{}", hxMt4ReqDtoStr);
			httpReturnValue = HttpUtil.net(hxmt4apiUrl + "cash/addBonus", params, "POST");
			logger.debug(">>>>>>>>>>>>HX-MT4 api return value:" + httpReturnValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(">>>>>>>>>>>>调用HX-MT4的API出错err={}", e.getMessage());
		}

		if (StringUtil.isNotEmpty(httpReturnValue)) {
			try {
				HxMt4RespDto hxMt4RespDto = JsonUtil.json2Obj(httpReturnValue, HxMt4RespDto.class);
				if (hxMt4RespDto != null && hxMt4RespDto.getStatus().equals("0")) {
					dealNumber = hxMt4RespDto.getData();
				} else {
					if (hxMt4RespDto != null && "API007".equals(hxMt4RespDto.getStatus())) {
						pGetSidDate = null;
						getSid();
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error(">>>>>>>>>>>>解析HX-MT4的API返回的数据出错err={}", e.getMessage());
			}
		}
		if (saveThird) {
			saveThirdCallRecord(bonusReqDto, dealNumber, ActThirdCallEnum.BONUS_ADD_BONUS);
		}
		return dealNumber;
	}

	@Override
	public String releaseBonus(BonusReqDTO bonusReqDto) {
		return releaseBonus(bonusReqDto, true);
	}

	@Override
	public String releaseBonus(BonusReqDTO bonusReqDto, boolean saveThird) {
		if (StringUtil.isEmpty(this.sid)) {
			this.sid = getSid();
		}
		String dealNumber = null;
		HxMt4BonusReqDto hxMt4AddBonusDto = new HxMt4BonusReqDto();
		hxMt4AddBonusDto.setSid(this.sid);
		hxMt4AddBonusDto.setSource("IMS");
		hxMt4AddBonusDto.setRefOrder(bonusReqDto.getRefId());
		hxMt4AddBonusDto.setBountyOrder(bonusReqDto.getBonusPno());
		hxMt4AddBonusDto.setBonusDesc(bonusReqDto.getBonusDesc());
		hxMt4AddBonusDto.setReleasedAmount(bonusReqDto.getReleasedAmount());
		hxMt4AddBonusDto.setLockedAmount(bonusReqDto.getLockedAmount());
		hxMt4AddBonusDto.setRemark(bonusReqDto.getRemark());
		hxMt4AddBonusDto.setIsAutoApprove("N");

		String hxMt4ReqDtoStr = JsonUtil.obj2Str(hxMt4AddBonusDto);
		Map<String, Object> params = JsonUtil.json2Map(hxMt4ReqDtoStr);

		String httpReturnValue = null;
		try {
			logger.info(">>>>>>>>>>>>调用HX-MT4释放可取金额接口,参数:{}", hxMt4ReqDtoStr);
			httpReturnValue = HttpUtil.net(hxmt4apiUrl + "cash/releaseBonus", params, "POST");
			logger.debug(">>>>>>>>>>>>HX-MT4 api return value:" + httpReturnValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(">>>>>>>>>>>>调用HX-MT4的API出错err={}", e.getMessage());
		}

		if (StringUtil.isNotEmpty(httpReturnValue)) {
			try {
				HxMt4RespDto hxMt4RespDto = JsonUtil.json2Obj(httpReturnValue, HxMt4RespDto.class);
				if (hxMt4RespDto != null && hxMt4RespDto.getStatus().equals("0")) {
					dealNumber = hxMt4RespDto.getData();
				} else {
					if (hxMt4RespDto != null && "API007".equals(hxMt4RespDto.getStatus())) {
						pGetSidDate = null;
						getSid();
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error(">>>>>>>>>>>>解析HX-MT4的API返回的数据出错err={}", e.getMessage());
			}
		}
		if (saveThird) {
			saveThirdCallRecord(bonusReqDto, dealNumber, ActThirdCallEnum.BONUS_RELEASE_BONUS);
		}
		return dealNumber;
	}

	@Override
	public Map<String, Map<String, String>> cancelBonus(List<BonusReqDTO> listBonusReqDto) {
		return cancelBonus(listBonusReqDto, true);
	}

	public Map<String, Map<String, String>> cancelBonus(List<BonusReqDTO> listBonusReqDto, boolean saveThird) {
		if (StringUtil.isEmpty(this.sid)) {
			this.sid = getSid();
		}
		Map<String, Map<String, String>> execResult = new HashMap<String, Map<String, String>>();
		Map<String, String> successMap = new HashMap<String, String>();
		HxMt4BonusReqDto hxMt4AddBonusDto = new HxMt4BonusReqDto();
		List<HxMt4BonusReqDto> listHxMt4AddBonusDto = new ArrayList<HxMt4BonusReqDto>();
		List<String> dealNumbers = new ArrayList<String>();
		for (BonusReqDTO bonusReqDto : listBonusReqDto) {
			hxMt4AddBonusDto = new HxMt4BonusReqDto();
			hxMt4AddBonusDto.setSource("IMS");
			hxMt4AddBonusDto.setRefOrder(bonusReqDto.getRefId());
			hxMt4AddBonusDto.setBountyOrder(bonusReqDto.getBonusPno());
			hxMt4AddBonusDto.setDeductedReason(bonusReqDto.getCancelReason());

			hxMt4AddBonusDto.setIsAutoApprove("N");
			listHxMt4AddBonusDto.add(hxMt4AddBonusDto);
			dealNumbers.add(bonusReqDto.getBonusPno());
		}

		String hxMt4ReqDtoStr = JsonUtil.obj2Str(listHxMt4AddBonusDto);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bonus_proposal", hxMt4ReqDtoStr);
		params.put("sid", this.sid);
		String httpReturnValue = null;
		try {
			logger.info(">>>>>>>>>>>>调用HX-MT4取消贈金接口,参数:{}>>>>>>>>>>>>", JsonUtil.obj2Str(params));
			httpReturnValue = HttpUtil.net(hxmt4apiUrl + "cash/batchCancelBonus", params, "POST");
			logger.debug(">>>>>>>>>>>>HX-MT4 api return value:" + httpReturnValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error(">>>>>>>>>>>>调用HX-MT4的API出错err={}", e.getMessage());
		}

		if (StringUtil.isNotEmpty(httpReturnValue)) {
			try {
				List<HxMt4RespMapDto> hxMt4RespDto = JsonUtil.json2List(httpReturnValue, HxMt4RespMapDto.class);
				if (hxMt4RespDto != null && !hxMt4RespDto.isEmpty()) {
					for (HxMt4RespMapDto itemDto : hxMt4RespDto) {
						if (itemDto.getStatus().equals("0")) {
							successMap.putAll(itemDto.getData());
						} else {
							if (hxMt4RespDto != null && "API007".equals(itemDto.getStatus())) {
								pGetSidDate = null;
								getSid();
							}
						}
					}
				}
				execResult.put("success", successMap);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.error(">>>>>>>>>>>>解析HX-MT4的API返回的数据出错err={}", e.getMessage());
			}
		}
		if (saveThird) {
			Map<String, String> result = execResult.get("success");
			saveThirdCallRecord(listBonusReqDto, result, ActThirdCallEnum.BONUS_CANCEL_BONUS);
		}
		return execResult;
	}
}
