package com.gwghk.ims.marketingtool.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.enums.RechargeChannelEnum;
import com.gwghk.ims.common.inf.external.marketingtool.CamiDubboService;
import com.gwghk.ims.marketingtool.dao.entity.ImsCamiLogDetail;
import com.gwghk.ims.marketingtool.manager.market.cami.CamiContext;
import com.gwghk.ims.marketingtool.manager.market.cami.CamiInfo;
import com.gwghk.ims.marketingtool.manager.market.cami.CamiListener;
import com.gwghk.ims.marketingtool.manager.market.cami.CamiOfServer;

/**  
* 摘要:   
* @author George.li  
* @date 2018年5月31日  
* @version 1.0  
*/
public class CamiDubboServiceImpl implements CamiDubboService {
	private static final Logger logger = LoggerFactory.getLogger(OnlineDubboServiceImpl.class);
	
	@Autowired
	@Qualifier("camiOfListenerImpl")
	private CamiListener<ImsCamiLogDetail> camiOfListener;
	
	/**
	 * 卡密充值
	 */
	@Override
	public ApiRespResult<Map<String, Object>> send(String mobile, String orderNo, String giftName, String camiChannel,
			String ip, Map<String, String> extMap, Long companyId) {
		 
		ApiRespResult<Map<String, Object>> respCode = new ApiRespResult<Map<String, Object>>();
		try {
			return doSend(mobile, orderNo, giftName, camiChannel, ip, extMap, companyId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			respCode.setCode("exception");
			respCode.setMsg("系统出现异常");
		}
		return respCode;
	}
	
	private ApiRespResult<Map<String, Object>> doSend(String mobile, String orderNo, String giftName,
			String camiChannel, String ip, Map<String, String> extMap, Long companyId) throws Exception {
		ApiRespResult<Map<String, Object>> respCode = new ApiRespResult<Map<String, Object>>();
		// if(!SmsUtil.checkMobileNum(mobile)){
		// respCode.setCode("error");
		// respCode.setMsg("手机号格式错误");
		// return respCode;
		// }
		
		CamiContext<ImsCamiLogDetail> context = null;
		if (camiChannel.equals(RechargeChannelEnum.of.getLabelKey())) {
			CamiOfServer camiOfServer = new CamiOfServer();
			camiOfServer.addOnlineListener(camiOfListener);
			// 卡密实体封装
			CamiInfo camiInfo = new CamiInfo();
			camiInfo.setPhones(mobile);
			camiInfo.setCompanyId(companyId);
			camiInfo.setOrderNo(orderNo);
			// camiInfo.setEmail(email);
			String cardid = null;
			if (giftName.indexOf("100元京东E卡") >= 0) {
				cardid = "1227402";
			}else if(giftName.indexOf("50元京东E卡") >= 0){
				cardid = "1227408";
			}else if(giftName.indexOf("5元京东E卡") >= 0){
				cardid = "1227400";
			}else if(giftName.indexOf("10元京东E卡") >= 0){
				cardid = "1227401";
			}else if(giftName.indexOf("200元京东E卡") >= 0){
				cardid = "1227403";
			}else if(giftName.indexOf("300元京东E卡") >= 0){
				cardid = "1227404";
			}else if(giftName.indexOf("500元京东E卡") >= 0){
				cardid = "1227405";
			}else if(giftName.indexOf("800元京东E卡") >= 0){
				cardid = "1227406";
			}else if(giftName.indexOf("1000元京东E卡") >= 0){
				cardid = "1227407";
			}
			camiInfo.setCardid(cardid);
			camiInfo.setIp(ip);
			camiInfo.setCamiName(giftName);
			context = camiOfServer.send(camiInfo);
		} else {
			respCode.setCode("error");
			respCode.setMsg("没有设定通道");
			return respCode;
		}

		if (null != context && null != context.getObj()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sendStatus", context.getObj().getSendStatus());
			map.put("price", context.getObj().getPrice());
			map.put("resBatchNo", context.getObj().getResBatchNo());
			respCode.setData(map);
		} else {
			respCode.setCode("error");
			respCode.setMsg("调用第三方卡密充值接口出现异常");
			return respCode;
		}
		return respCode;
	}

	 
	 

}
