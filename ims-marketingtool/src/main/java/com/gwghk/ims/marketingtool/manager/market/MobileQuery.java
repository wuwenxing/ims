package com.gwghk.ims.marketingtool.manager.market;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwghk.ims.common.enums.RechargeChannelEnum;
import com.gwghk.ims.marketingtool.enums.MobileOperatorEnum;
import com.gwghk.ims.marketingtool.util.AppConfigUtil;

/**
 * 公共方法处理
 * @author wayne
 *
 *
 */
@SuppressWarnings("deprecation")
public class MobileQuery {
	
	private static final Logger logger = LoggerFactory.getLogger(MobileQuery.class);

	private static MobileQuery mobileQuery = null;
	
	private static HttpClient httpClient = null;
	private static final int CONNECTION_POOL_SIZE = 5;
	private static final int TIMEOUT_SECONDS = 10 * 60;// 10分钟
	private static String mobileQueryUrl = "";
	
	static {
		// Set connection pool
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
		cm.setMaxTotal(CONNECTION_POOL_SIZE);
		httpClient = new DefaultHttpClient(cm);
		// set timeout
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_SECONDS * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_SECONDS * 1000);

		mobileQueryUrl = AppConfigUtil.getProperty("of.mobile.query.url");
		logger.info("mobileQueryUrl={}", mobileQueryUrl);
	}
	
	private MobileQuery(){
		// 设为私有、禁止外部创建
	}
	
	/**
	 * MobileQuery不需频繁创建，使用单例
	 * @return
	 */
	public static MobileQuery getInstance(){
		if(null == mobileQuery){
			synchronized (MobileQuery.class) {
				if(null == mobileQuery){
					mobileQuery = new MobileQuery(); // 线程安全
				}
			}
		}
		return mobileQuery;
	}
	
	/**
	 * 获取手机号运营商
	 */
	public static MobileOperatorEnum getMobileOperatorEnum(String str){
		MobileOperatorEnum returnEnum = MobileOperatorEnum.UNKNOWN;
		if(StringUtils.isNotBlank(str)){
			if(str.contains("移动")){
				returnEnum = MobileOperatorEnum.CMCC;
			}else if(str.contains("联通")){
				returnEnum = MobileOperatorEnum.CUCC;
			}else if(str.contains("电信")){
				returnEnum = MobileOperatorEnum.CTCC;
			}
		}
		return returnEnum;
	}
	
	/**
	 * 获取手机号运营商
	 */
	public static String mobileQuery(String phone){
		String returnStr = null;
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("mobilenum", phone);
			returnStr = MobileQuery.getInstance().doPost(mobileQueryUrl, parameters);
			/**13813831111|江苏南京|移动*/
			if(StringUtils.isNotBlank(returnStr)){
				int i = returnStr.indexOf("|");
				if(i != -1){
					returnStr = returnStr.substring(i+1, returnStr.length());
				}
			}
			logger.debug(returnStr);
		} catch (IOException e) {
			logger.error("获取手机号运营商异常,使用本地获取运营商策略", e);
			MobileOperatorEnum operator = MobileOperatorEnum.checkOpeator(phone);
			returnStr = operator.getValue();
		}
		return returnStr;
	}
	
	/**
	 * 根据手机号\流量通道\流量大小,不拆分流量
	 * 
	 * @param phone
	 * @param flowChannel
	 * @param flowSize
	 * @param area 手机号归属地
	 * @return
	 */
	public static List<String> getSNOrPacket(String phone, String flowChannel, String flowSize, String area) {
		Integer size = Integer.parseInt(flowSize);
		List<String> returnList = new ArrayList<String>();
		List<String> tempList = new ArrayList<String>();
		MobileOperatorEnum operator = MobileQuery.getMobileOperatorEnum(area);
		if (RechargeChannelEnum.rl.getLabelKey().equals(flowChannel)) {
			if (MobileOperatorEnum.CMCC.getLabelKey().equals(operator.getLabelKey())) {
				tempList = RechargeChannelEnum.rlSNOrPacketList_cmcc;
			} else if (MobileOperatorEnum.CUCC.getLabelKey().equals(operator.getLabelKey())) {
				tempList = RechargeChannelEnum.rlSNOrPacketList_cucc;
			} else if (MobileOperatorEnum.CTCC.getLabelKey().equals(operator.getLabelKey())) {
				tempList = RechargeChannelEnum.rlSNOrPacketList_ctcc;
			}
			// 从大到小比较相等
			for (String str:tempList) {
				Integer value = Integer.parseInt(str.split(",")[1]);
				if(size == value){
					returnList.add(str);
				}
			}
		}else if(RechargeChannelEnum.of.getLabelKey().equals(flowChannel)){
			if (MobileOperatorEnum.CMCC.getLabelKey().equals(operator.getLabelKey())) {
				tempList = RechargeChannelEnum.ofSNOrPacketList_cmcc;
			} else if (MobileOperatorEnum.CUCC.getLabelKey().equals(operator.getLabelKey())) {
				tempList = RechargeChannelEnum.ofSNOrPacketList_cucc;
			} else if (MobileOperatorEnum.CTCC.getLabelKey().equals(operator.getLabelKey())) {
				tempList = RechargeChannelEnum.ofSNOrPacketList_ctcc;
			}
			// 从大到小比较相等
			for (String str:tempList) {
				String temp = str.split(",")[1];
				String sizeTemp = temp.substring(0, temp.length()-1);
				String unit = temp.substring(temp.length()-1, temp.length());
				Integer value = null;
				if("G".equals(unit)){//单位为G，乘以1024
					value = Integer.parseInt(sizeTemp)*1024;
				}else{
					value = Integer.parseInt(sizeTemp);
				}
				if(size == value){
					returnList.add(str);
				}
			}
		}
		return returnList;
	}
	
	/**
	 * 根据手机号\流量通道\流量大小,组合拆分流量
	 * 
	 * @param phone
	 * @param flowChannel
	 * @param flowSize
	 * @param area 手机号归属地
	 * @return
	 */
	public static List<String> getSNOrPacketMap(String phone, String flowChannel, String flowSize, String area) {
		Integer size = Integer.parseInt(flowSize);
		List<String> returnList = new ArrayList<String>();
		List<String> tempList = new ArrayList<String>();
		MobileOperatorEnum operator = MobileQuery.getMobileOperatorEnum(area);
		if (RechargeChannelEnum.rl.getLabelKey().equals(flowChannel)) {
			if (MobileOperatorEnum.CMCC.getLabelKey().equals(operator.getLabelKey())) {
				tempList = RechargeChannelEnum.rlSNOrPacketList_cmcc;
			} else if (MobileOperatorEnum.CUCC.getLabelKey().equals(operator.getLabelKey())) {
				tempList = RechargeChannelEnum.rlSNOrPacketList_cucc;
			} else if (MobileOperatorEnum.CTCC.getLabelKey().equals(operator.getLabelKey())) {
				tempList = RechargeChannelEnum.rlSNOrPacketList_ctcc;
			}
			// 依次求余,从大到小
			for (String str:tempList) {
				Integer value = Integer.parseInt(str.split(",")[1]);
				// 整除
				int num = size / value;
				for (int i = 0; i < num; i++) {
					returnList.add(str);
				}
				// 求余
				size = size % value;
			}
		}else if(RechargeChannelEnum.of.getLabelKey().equals(flowChannel)){
			if (MobileOperatorEnum.CMCC.getLabelKey().equals(operator.getLabelKey())) {
				tempList = RechargeChannelEnum.ofSNOrPacketList_cmcc;
			} else if (MobileOperatorEnum.CUCC.getLabelKey().equals(operator.getLabelKey())) {
				tempList = RechargeChannelEnum.ofSNOrPacketList_cucc;
			} else if (MobileOperatorEnum.CTCC.getLabelKey().equals(operator.getLabelKey())) {
				tempList = RechargeChannelEnum.ofSNOrPacketList_ctcc;
			}
			// 依次求余,从大到小
			for (String str:tempList) {
				String temp = str.split(",")[1];
				String sizeTemp = temp.substring(0, temp.length()-1);
				String unit = temp.substring(temp.length()-1, temp.length());
				Integer value = null;
				if("G".equals(unit)){//单位为G，乘以1024
					value = Integer.parseInt(sizeTemp)*1024;
				}else{
					value = Integer.parseInt(sizeTemp);
				}
				// 整除
				int num = size / value;
				for (int i = 0; i < num; i++) {
					returnList.add(str);
				}
				// 求余
				size = size % value;
			}
		}
		return returnList;
	}
	
	/**
	 * post请求，参数为键值对
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public String doPost(String url, Map<String, String> parameters) throws IOException {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (parameters != null && !parameters.isEmpty()) {
			Iterator<Entry<String, String>> itr = parameters.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) itr.next();
				NameValuePair nvp = new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue());
				nvps.add(nvp);
			}
		}
		HttpPost httpPost = new HttpPost(url);
		StringEntity params = new UrlEncodedFormEntity(nvps, "GB2312");	
		httpPost.setEntity(params);
		logger.info("post: " + httpPost.getURI());
		logger.info(nvps.toString());
		HttpEntity entity = null;
		try {
			HttpContext context = new BasicHttpContext();
			HttpResponse remoteResponse = httpClient.execute(httpPost, context);
			logger.info(remoteResponse.getStatusLine().toString());
			entity = remoteResponse.getEntity();
		} catch (Exception e) {
			logger.error("fetch remote content" + url + "  error", e);
			httpPost.abort();
			return null;
		}

		// 404错误
		if (entity == null) {
			throw new RuntimeException(url + " is not found");
		}

		InputStream input = entity.getContent();
		try {
			return IOUtils.toString(input, "GB2312");
		} finally {
			// 保证InputStream的关闭.
			IOUtils.closeQuietly(input);
		}
	}

	public static void main(String[] args) {
		String returnStr = "13813831111|江苏南京|移动";
		if(StringUtils.isNotBlank(returnStr) && returnStr.length() >= 11){
			returnStr = returnStr.substring(11, returnStr.length());
		}
		System.out.println(returnStr);
		List<String> list = getSNOrPacketMap("13760291376", "rl", "8000", "移动");
		for (String str:list) {
			System.out.println(str);
		}
	}
	
}
