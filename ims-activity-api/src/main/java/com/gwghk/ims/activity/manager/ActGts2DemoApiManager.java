package com.gwghk.ims.activity.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.util.Gts2EncryptUtil;
import com.gwghk.ims.activity.util.HttpUtil;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.common.ApiResultCode;
import com.gwghk.ims.common.dto.activity.Gts2CashAdjustReqDto;
import com.gwghk.ims.common.dto.activity.Gts2DemoAccountInfoDTO;
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
@Service("actGts2DemoApiManager")
public class ActGts2DemoApiManager extends Gts2ApiCommonManager {

	private static Logger logger = LoggerFactory.getLogger(Gts2ApiCommonManager.class) ;

	/**
	 * GTS2-DEMO-查询模拟帐户信息
	 */
    public final static String GTS2_DEMO_API_URL_GETDEMOCUSTOMER_RO = "demo_ro/getDemoCustomerPage";
	
	/**
	 * 根据模拟交易帐号查询模拟帐户信息
	 * @param demoAccountNO
	 * @return
	 */
	public ApiRespResult<Gts2DemoAccountInfoDTO> getDemoAccountInfoByAccountNO(String demoAccountNO, String platform, Long companyId) {
		String url = demoApiUrl + GTS2_DEMO_API_URL_GETDEMOCUSTOMER_RO;
		StringBuffer paramBuffer = new StringBuffer();
		paramBuffer.append("{");
		paramBuffer.append("\"customerNumber\":\"").append(demoAccountNO).append("\"");
		if(StringUtils.isNotBlank(platform)){
			paramBuffer.append(",\"platform\":\"").append(platform).append("\"");
		}
		paramBuffer.append("}");
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pageNo", "1");
		paramMap.put("pageSize", "10");
		paramMap.put("request", paramBuffer.toString());
		paramMap.put("args", "[]");
		String gts2PrincipalReqDtoStr = initDemoGts2Principal(companyId.toString());
		paramMap.put("_principal_", gts2PrincipalReqDtoStr);
		String signature = Gts2EncryptUtil.sign(getDemoApiKey(companyId.toString()), paramMap);
		paramMap.put("_signature_", signature);
		ApiRespResult<Gts2DemoAccountInfoDTO> apiRespResult = new ApiRespResult<Gts2DemoAccountInfoDTO>();
        try{
        	String result = HttpUtil.net(url, paramMap, "POST");
        	if(StringUtil.isNotBlank(result)) {
             	logger.info("调用第三方接口，根据根据模拟帐户查询客户信息返回数据：{}", result);
             	JSONObject respJson = (JSONObject) JSONObject.parse(result);
             	if("SUCCESS".equalsIgnoreCase(respJson.getString("code"))){
             		if (StringUtil.isNotEmpty(respJson.getString("result"))) {
             			JSONObject jsonObject = respJson.getJSONObject("result");
             			JSONArray jsonArray = jsonObject.getJSONArray("collection");
             			if(CollectionUtils.isNotEmpty(jsonArray)){
             				JSONObject data = jsonArray.getJSONObject(0);
             				if(null != data){
             					String chineseName = data.getString("chineseName");
             					String email = data.getString("email");
             					String mobilePhone = data.getString("mobilePhone");
             					JSONArray accounts = data.getJSONArray("accountInfoParams");
             					if(CollectionUtils.isNotEmpty(accounts)){
             						JSONObject account = accounts.getJSONObject(0);
             						JSONObject dateObj = account.getJSONObject("openAccountDate");
             						Gts2DemoAccountInfoDTO demoAccount = new Gts2DemoAccountInfoDTO();
             						demoAccount.setAccountNo(account.getString("accountNo"));
             						demoAccount.setRegisterTime(new Date(dateObj.getLong("time")));
             						demoAccount.setPlatform(account.getString("platform"));
             						demoAccount.setCurrency(account.getString("currency"));
             						demoAccount.setCompanyId(account.getLong("companyId"));
             						demoAccount.setCustomerNumber(account.getLong("customerNumber"));
             						demoAccount.setMobilePhone(mobilePhone);
             						demoAccount.setEmail(email);
             						demoAccount.setChineseName(chineseName);
             	    				logger.info("demoAccountNO：{}", demoAccount.getAccountNo());
             						apiRespResult.setData(demoAccount);
             	    				return apiRespResult;
             					}
             				}
             			}
             		}
             	}
            }
        }catch(Exception e){
            logger.info("调用第三方接口:根据账号获取当前账号信息异常{}",e);
    		apiRespResult.setRespMsg(ApiResultCode.InterfaceError);
    		return apiRespResult;
        }
        logger.info("账号不存在！");
		apiRespResult.setRespMsg(ApiResultCode.Err10009);
		return apiRespResult;
	}
	
	/**
	 * 
	 * @MethodName: demoCashAdjust
	 * @Description: 调用GTS2的模拟账号金额调整接口
	 * @param actCustomerInfo
	 * @param payAmount
	 * @return void
	 * @throws Exception 
	 */
	public ApiRespResult<String> demoCashAdjust(ActCustomerInfo actCustomerInfo, BigDecimal payAmount) throws Exception {
		ApiRespResult<String> apiRespResult = new ApiRespResult<String>();
		try{
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
			params.put("cashinAdjustParam", gts2CashAdjustReqDtoStr);
			params.put("args", "[]");
			String gts2PrincipalReqDtoStr = initDemoGts2Principal(actCustomerInfo.getCompanyId().toString());
			params.put("_principal_", gts2PrincipalReqDtoStr);
			String signature = Gts2EncryptUtil.sign(getDemoApiKey(actCustomerInfo.getCompanyId().toString()), params);
			params.put("_signature_", signature);
			String result = HttpUtil.net(demoApiUrl + "cash/addCashAdjust", params, "GET");
			logger.info(result);
			if(StringUtil.isNotBlank(result)){
				Map<String, Object> parseValue = JsonUtil.json2Map(result);
				if ("SUCCESS".equals(parseValue.get("code"))) {
					Map<String, Object> resultMap = JsonUtil.json2Map(parseValue.get("result").toString());
					String code = resultMap.get("code") + "";
					String resultNo = resultMap.get("result") + "";
					if("OK".equals(code)){
						// 接口请求成功,但调整成功
						apiRespResult.setData(resultNo);
						return apiRespResult;
					}else if("FAIL".equals(code)){
						// 失败
						return apiRespResult.setRespMsg(ApiResultCode.Err10009);
					}else if("REDO".equals(code)){
						// 接口请求成功,但调整失败
						apiRespResult.setData(resultNo);
						return apiRespResult.setRespMsg(ApiResultCode.DEMOAMOUNT_REDO);
					}
				}
			}
			return apiRespResult.setRespMsg(ApiResultCode.InterfaceError);
		}catch(Exception e){
			logger.error("接口请求失败", e);
			apiRespResult.setRespMsg(ApiResultCode.InterfaceError);
			return apiRespResult;
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
		String gts2PrincipalReqDtoStr = initDemoGts2Principal(companyId);
		params.put("_principal_", gts2PrincipalReqDtoStr);
		String signature = Gts2EncryptUtil.sign(getDemoApiKey(companyId), params);
		params.put("_signature_", signature);
		String httpReturnValue = null;
		try {
			httpReturnValue = HttpUtil.net(demoApiUrl + "account/getCustomerAccountView", params, "GET");
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
	
}
