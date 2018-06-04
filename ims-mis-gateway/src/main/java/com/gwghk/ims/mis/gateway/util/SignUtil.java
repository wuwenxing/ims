package com.gwghk.ims.mis.gateway.util;

import java.util.Date;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.util.DateUtil;

/**
 * 签名生成与验证工具类
 * 
 * @author wayne
 *
 */
public class SignUtil {

	private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);
	
    public static final String DATE_PATTERN_MILL_S = "yyyyMMddHHmmssSSS";

	/**
	 * 固定token值
	 */
	public static final String token_fx = "685c99706c81a33f54235194a5dab827";
	public static final String token_hx = "5d43712ac420b7364eece1487ada241b";
	public static final String token_pm = "9ac9016bc952335aa9e20c5ee1ea3280";
	
	// 缓存已校验的签名,签名只能用一次
	public static Map<String, String> signMap = new TheSizeLinkedHashMap<String, String>();
	
	/**
	 * 获取时间戳
	 */
	public static String getTimestamp() {
		String timestamp = DateUtil.formatDateToString(new Date(), DATE_PATTERN_MILL_S);
		return timestamp;
	}
	
	/**
	 * 获取时间戳
	 */
	public static String getTimestamp(Date date) {
		String timestamp = DateUtil.formatDateToString(date, DATE_PATTERN_MILL_S);
		return timestamp;
	}

	/**
	 * 生成签名加密串
	 */
	public static String getSign(String timestamp, Long companyId) {
		if (CompanyEnum.fx.getId().equals(companyId+"")) {
			return DigestUtils.md5Hex(SignUtil.token_fx + timestamp);
		} else if (CompanyEnum.hx.getId().equals(companyId+"")) {
			return DigestUtils.md5Hex(SignUtil.token_hx + timestamp);
		} else if (CompanyEnum.pm.getId().equals(companyId+"")) {
			return DigestUtils.md5Hex(SignUtil.token_pm + timestamp);
		} else if (CompanyEnum.hxfx.getId().equals(companyId+"")) {
			// TODO
		} else if (CompanyEnum.cf.getId().equals(companyId+"")) {
			// TODO
		}
		return "";
	}

	/**
	 * 验证签名
	 */
	public static <E> boolean validate(String timestamp, String sign, E companyId) {
		logger.info("签权验证开始[timestamp={}, sign={}, companyId={}]", new Object[]{timestamp, sign, companyId});
		if (StringUtils.isNotBlank(timestamp) && StringUtils.isNotBlank(sign) && StringUtils.isNotBlank(companyId+"")) {
			// 有效期验证
			Date sendDate = DateUtil.stringToDate(timestamp, DATE_PATTERN_MILL_S);
			if(null == sendDate){
				logger.info("签权格式有误");
				return false;
			}
			Date nowDate = new Date();
			String nowTimestamp = DateUtil.formatDateToString(nowDate, DATE_PATTERN_MILL_S);
			if(DateUtil.addHours(nowDate, -1).compareTo(sendDate)>0){
				logger.info("签权已过有效期,1小时内有效");
				return false;
			}
			String serverSign = SignUtil.getSign(timestamp, Long.parseLong(companyId+""));
			// 重复验证-签权已使用，系统毫秒存在时间差，但几乎时间很短、设定在10秒内都有效
			int times = 10;
			String value = signMap.get(sign);
			if(null != value){
				Date date = DateUtil.stringToDate(value, DATE_PATTERN_MILL_S);
				if(DateUtil.addSeconds(date, times).compareTo(nowDate) < 0){
					logger.info("签权已被使用");
					return false;
				}
			}
			if (sign.equals(serverSign)) {
				logger.info("签权验证成功");
				signMap.put(sign, nowTimestamp);//value值，存系统时间
				return true;
			}
		}
		logger.info("签权验证失败");
		return false;
	}
	
	public static void main(String[] args) {
		Long companyId = 1L;
		String timestamp = getTimestamp();
		logger.info(timestamp);
		String sign = SignUtil.getSign(timestamp, companyId);
		logger.info(sign);
		SignUtil.validate(timestamp, sign, companyId);
	}
	
}
