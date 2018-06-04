package com.gwghk.ims.datacleaning.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.common.ApiResultCode;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.datacleaning.manager.GTS2DataCleanManager;
import com.gwghk.ims.datacleaning.manager.HXMt4DataCleanManager;
import com.gwghk.unify.framework.common.util.DateUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：提供手动清洗GTS2或HX-MT4数据
 * 
 * @author lawrence
 * @version 1.0
 * @Date 2017年11月9日
 */
@RestController
@RequestMapping(value = "/act/clean")
public class GTS2AndHXMT4DataCleanController {
	private static final Logger logger = LoggerFactory.getLogger(GTS2AndHXMT4DataCleanController.class);

	@Autowired
	private GTS2DataCleanManager actGts2DataMergeManager;

	@Autowired
	private HXMt4DataCleanManager actHxDataMergeManager;

	@Value("${hx.decrypt.key}")
	private String hxDecryptKey;

	/**
	 * 
	 * @MethodName: databaseMerge
	 * @Description: 执行清洗数据功能
	 * @param req
	 * @return
	 * @return ApiRespResult<String>
	 */
	@RequestMapping(value = "/gw/data", method = { RequestMethod.POST, RequestMethod.GET })
	public ApiRespResult<String> cleanGwData(HttpServletRequest req) {
		DateUtil dateUtil = DateUtil.getInstance(8);
		String baseName = req.getParameter("baseName");
		String type = req.getParameter("type");
		String startDate = req.getParameter("startDate")+" 00:00:00";
		String endDate = req.getParameter("endDate")+" 23:59:59";
		String companyCode = req.getParameter("companyCode");
		String time = req.getParameter("time");
		String companyId = CompanyEnum.getIdByCode(companyCode);
		logger.info("参数,baseName:{},type:{},startDate:{},endDate:{},companyCode:{},time:{}",
				baseName,type,startDate,endDate,companyCode,time);
		if (StringUtil.isEmpty(endDate)) {
			endDate = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		}
		ApiRespResult<String> result = new ApiRespResult<String>();
		Calendar pStart = dateUtil.parseCalendarFormat(startDate);
		Calendar pTmpEnd = dateUtil.parseCalendarFormat(startDate);
		Calendar pEnd = dateUtil.parseCalendarFormat(endDate);
		Integer timeSpace = 5;
		if(StringUtil.isNotEmpty(time)){
			timeSpace = Integer.parseInt(time);
		}
		 
		while (pStart.compareTo(pEnd) < 0) {
			
			pTmpEnd.add(Calendar.DATE, timeSpace);
			String strStartDate = DateUtil.formatDateToString(pStart.getTime(),"yyyy-MM-dd HH:mm:ss");
			String strEndDate = DateUtil.formatDateToString(pTmpEnd.getTime(),"yyyy-MM-dd HH:mm:ss");
			logger.info("databaseMerge->数据源：{},处理类型：{},开始时间：{},结束时间:{},公司代码:{}",
					new Object[] { baseName, type,  strStartDate
							, strEndDate, companyCode });
			Map<String, Object> paramData = new HashMap<String, Object>();
			paramData.put("lastUpdateDateBegin", strStartDate);
			paramData.put("lastUpdateDateEnd", strEndDate);
			paramData.put("companyCode", companyCode);
			paramData.put("companyId", companyId);
			if (StringUtil.isNullOrEmpty(baseName) || StringUtil.isNullOrEmpty(type)
					|| StringUtil.isNullOrEmpty(startDate) || StringUtil.isNullOrEmpty(companyCode)
					|| companyId == null) {
				result.setRespMsg(ApiResultCode.Err10002);
				return result;
			}
			try {
				if ("GTS2".equals(baseName)) {
					if ("realCustomer".equals(type)) {
						actGts2DataMergeManager.mergeMissRealActCustomerInfo(paramData, companyCode);
					} else if ("demoCustomer".equals(type)) {
						actGts2DataMergeManager.mergeMissDemoActCustomerInfo(paramData, companyCode);
					} else if ("realTrade".equals(type)) {
						actGts2DataMergeManager.mergeMissRealActTradeRecord(paramData, companyCode);
					} else if ("demoTrade".equals(type)) {
						actGts2DataMergeManager.mergeMissDemoActTradeRecord(paramData, companyCode);
					} else if ("cashin".equals(type)) {
						actGts2DataMergeManager.mergeMissRealActCashin(paramData, companyCode);
					} else if ("cashout".equals(type)) {
						actGts2DataMergeManager.mergeMissRealActCashout(paramData, companyCode);
					} else {
						result.setRespMsg(ApiResultCode.Err10002);
						return result;
					}
				} else if ("HxMt4".equals(baseName)) {
					paramData.put("hxDecryptKey", hxDecryptKey);
					if ("realCustomer".equals(type)) {
						actHxDataMergeManager.mergeMissRealActCustomerInfo(paramData);
					} else if ("demoCustomer".equals(type)) {
						actHxDataMergeManager.mergeMissDemoActCustomerInfo(paramData);
					} else if ("realTrade".equals(type)) {
						actHxDataMergeManager.mergeMissRealActTradeRecord(paramData);
					} else if ("demoTrade".equals(type)) {
						actHxDataMergeManager.mergeMissDemoActTradeRecord(paramData);
					} else if ("cashin".equals(type)) {
						actHxDataMergeManager.mergeMissRealActCashin(paramData);
					} else if ("cashout".equals(type)) {
						actHxDataMergeManager.mergeMissRealActCashout(paramData);
					} else {
						result.setRespMsg(ApiResultCode.Err10002);
						return result;
					}
				} else {
					result.setRespMsg(ApiResultCode.Err10002);
					return result;
				}				
				pStart.add(Calendar.DATE, timeSpace);
				result.setData("执行成功");
				result.setRespMsg(ApiResultCode.OK);
			} catch (Exception ex) {
				logger.error("调用异常!", ex);
				result.setRespMsg(ApiResultCode.EXCEPTION);
			}
		}

		return result;
	}

	public static void main(String[] args) {
		Calendar cal = DateUtil.getInstance(8).parseCalendarFormat("2017-10-21 00:00:00");
		System.out.println(cal.getTime());
	}
}
