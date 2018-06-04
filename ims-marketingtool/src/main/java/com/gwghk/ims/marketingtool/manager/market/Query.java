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

import com.gwghk.ims.marketingtool.util.AppConfigUtil;

/**
 * 公共方法处理
 * @author wayne
 *	
 */
@SuppressWarnings("deprecation")
public class Query {
	
	private static final Logger logger = LoggerFactory.getLogger(Query.class);

	private static Query query = null;

	// 开发者账号信息
	private static String userid = "";
	private static String systemActive = "";
	
	private static HttpClient httpClient = null;
	private static final int CONNECTION_POOL_SIZE = 5;
	private static final int TIMEOUT_SECONDS = 10 * 60;// 10分钟
	private static String queryUrl = "";
	
	static {
		// Set connection pool
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
		cm.setMaxTotal(CONNECTION_POOL_SIZE);
		httpClient = new DefaultHttpClient(cm);
		// set timeout
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_SECONDS * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_SECONDS * 1000);
		
		userid = AppConfigUtil.getProperty("of.userid");
		systemActive = AppConfigUtil.getProperty("system.active");
		if("real".equals(systemActive)){
			queryUrl = "http://api2.ofpay.com/api/query.do";
		}else{
			queryUrl = "http://apitest.ofpay.com/api/query.do";
		}
		logger.info("userid={}, queryUrl={}, systemActive={}", userid, queryUrl, systemActive);
	}
	
	private Query(){
		// 设为私有、禁止外部创建
	}
	
	/**
	 * 不需频繁创建，使用单例
	 * @return
	 */
	public static Query getInstance(){
		if(null == query){
			synchronized (Query.class) {
				if(null == query){
					query = new Query(); // 线程安全
				}
			}
		}
		return query;
	}
	
	/**
	 * 话费充值状态主动查询
	 * @desc 1充值成功，0充值中，9充值失败，-1找不到此订单
	 */
	public static String queryByOf(String orderId){
		String result = "";
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("userid", userid);// SP编码如（A00001）
			parameters.put("spbillid", orderId);// Sp商家的订单号（商户传给欧飞的唯一订单编号）
			result = Query.getInstance().doPost(queryUrl, parameters);
			// 返回（1，0，9，-1），其中一项。 1充值成功，0充值中，9充值失败，-1找不到此订单
			logger.debug(result);
		} catch (Exception e) {
			logger.error("获取手机号运营商异常,使用本地获取运营商策略", e);
		}
		return result;
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
		
	}
	
}
