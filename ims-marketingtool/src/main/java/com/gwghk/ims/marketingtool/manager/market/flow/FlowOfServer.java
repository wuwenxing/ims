package com.gwghk.ims.marketingtool.manager.market.flow;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwghk.ims.marketingtool.dao.entity.ImsRechargeLogDetail;
import com.gwghk.ims.marketingtool.util.AppConfigUtil;
import com.gwghk.ims.marketingtool.util.MD5;

/**
 * 欧飞流量服务实现类
 * 
 * @author wayne
 */
@SuppressWarnings("deprecation")
public class FlowOfServer {

	private static final Logger logger = LoggerFactory.getLogger(FlowOfServer.class);

	// 开发者账号信息
	private static String userid = "";
	private static String password = "";
	private static String keyStr = "";
	private static String url = "";
	private static String systemActive = "";
	private static String callbackUrl = "";

	private static HttpClient httpClient = null;
	private static final int CONNECTION_POOL_SIZE = 3;
	private static final int TIMEOUT_SECONDS = 10 * 60;// 10分钟

	/**
	 * 流量监听器
	 */
	private List<FlowListener<ImsRechargeLogDetail>> flowListeners = new ArrayList<FlowListener<ImsRechargeLogDetail>>();

	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
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
		password = MD5.getMD5Str(AppConfigUtil.getProperty("of.password"));
		keyStr = AppConfigUtil.getProperty("of.keyStr");
		url = AppConfigUtil.getProperty("of.flow.url");
		callbackUrl = AppConfigUtil.getProperty("of.flow.callback.url");
		systemActive = AppConfigUtil.getProperty("system.active");
		logger.info("userid={},password={},keyStr={},url={},systemActive={},callbackUrl={}", userid, password, keyStr,
				url, systemActive, callbackUrl);
	}

	/**
	 * 充值多条流量
	 */
	public void send(List<FlowInfo> flowInfos) {
		for (FlowInfo flowInfo : flowInfos) {
			send(flowInfo);
		}
	}

	/**
	 * 充值单条流量;
	 */
	@SuppressWarnings("unchecked")
	public FlowContext<ImsRechargeLogDetail> send(final FlowInfo flowInfo) {
		FlowContext<ImsRechargeLogDetail> flowContext = new FlowContext<ImsRechargeLogDetail>();
		flowContext.setFlowInfo(flowInfo);
		doBefore(flowContext);
		try {
			// 电话号码
			String phones = flowInfo.getPhones();
			// 第三方交易id
			String customId = flowInfo.getCustomIdRl();
			// 面值
			String sn = flowInfo.getFlowNoRl();
			// 流量包大小
			String packet = flowInfo.getFlowSizeRl();

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("userid", userid);// SP编码如（A00001）
			parameters.put("userpws", password);// SP接入密码
			parameters.put("phoneno", phones);// 待充值手机号码
			parameters.put("perValue", sn);// 面值
			parameters.put("flowValue", packet);// 流量值
			parameters.put("range", "2");// 使用范围 1（省内）、 2（全国）
			parameters.put("effectStartTime", "1");// 生效时间 1（当日）、2（次日）、3（次月）、6(国庆)
			parameters.put("effectTime", "1");// 1-当月有效
			parameters.put("sporderId", customId);// Sp商家的订单号（商户传给欧飞的唯一订单编号）
			parameters.put("md5Str", getSig(parameters));// 签名串，具体加密规则参考接口说明
			parameters.put("retUrl", callbackUrl); // 回调第三方的地址,不必填
			parameters.put("version", "6.0"); // 固定值-6.0
			String result = doPost(url, parameters);
			/**
			 * result: <?xml version="1.0" encoding="GB2312" ?>
			 * <orderinfo> <err_msg></err_msg> <retcode>1</retcode>
			 * <orderid>S1610252298212</orderid> <cardid>8494</cardid>
			 * <cardnum>1</cardnum> <ordercash>5</ordercash>
			 * <cardname>江苏移动5元30M全国当日生效当月有效4G流量包</cardname>
			 * <sporder_id>testLL1234567</sporder_id>
			 * <game_userid>15996271050</game_userid>
			 * <game_state>0</game_state> </orderinfo>
			 */
			logger.info("result:" + result);
			if (StringUtils.isNotBlank(result)) {
				// 转换成Document
				Document document = DocumentHelper.parseText(result);
				if (null != document) {
					// 获取根节点元素对象
					Element root = document.getRootElement();
					if (null != root) {
						// 获取根节点下的所有子元素
						List<Element> elements = root.elements();
						// 循环所有子元素
						if (elements != null && elements.size() > 0) {
							List<Object> list=new ArrayList<Object>();
							for (Element element : elements) {
								list.add(element.getTextTrim());
							}
							if(list.size() < 10){
								for(int i = list.size(); i<10; i++){
									list.add("");
								}
							}
							FlowOfResponse response = new FlowOfResponse();
							response.setErr_msg(null == list.get(0) ? "" : (list.get(0)) + "");
							response.setRetcode(null == list.get(1) ? "" : (list.get(1)) + "");
							response.setOrderid(null == list.get(2) ? "" : (list.get(2)) + "");
							response.setCardid(null == list.get(3) ? "" : (list.get(3)) + "");
							response.setCardnum(null == list.get(4) ? "" : (list.get(4)) + "");
							response.setOrdercash(null == list.get(5) ? "" : (list.get(5)) + "");
							response.setCardname(null == list.get(6) ? "" : (list.get(6)) + "");
							response.setSporder_id(null == list.get(7) ? "" : (list.get(7)) + "");
							response.setGame_userid(null == list.get(8) ? "" : (list.get(8)) + "");
							response.setGame_state(null == list.get(9) ? "" : (list.get(9)) + "");
							flowContext.setResponse(response);
						}
					}
				}
			}
			doAfter(flowContext);
		} catch (Exception e) {
			logger.error("of-充值流量出现异常", e);
			flowContext.setThrowable(e);
			doAfterThrowable(flowContext);
		}
		return flowContext;
	}

	/**
	 * 给发送器添加监听器
	 * 
	 * @param flowListener
	 */
	public void addFlowListener(FlowListener<ImsRechargeLogDetail> flowListener) {
		this.flowListeners.add(flowListener);
	}

	private void doBefore(FlowContext<ImsRechargeLogDetail> flowContext) {
		for (FlowListener<ImsRechargeLogDetail> flowListener : this.flowListeners) {
			flowListener.updateBefore(flowContext);
		}
	}

	private void doAfter(FlowContext<ImsRechargeLogDetail> flowContext) {
		for (FlowListener<ImsRechargeLogDetail> flowListener : this.flowListeners) {
			flowListener.updateAfter(flowContext);
		}
	}

	private void doAfterThrowable(FlowContext<ImsRechargeLogDetail> flowContext) {
		for (FlowListener<ImsRechargeLogDetail> flowListener : this.flowListeners) {
			flowListener.updateAfterThrowable(flowContext);
		}
	}

	/**
	 * 获取签名
	 * 
	 * @return
	 */
	private String getSig(Map<String, String> parameters) {
		String str = parameters.get("userid")
				+ parameters.get("userpws")
				+ parameters.get("phoneno")
				+ parameters.get("perValue") 
				+ parameters.get("flowValue")
				+ parameters.get("range")
				+ parameters.get("effectStartTime")
				+ parameters.get("effectTime")
				+ parameters.get("sporderId")
				+ keyStr;
		return MD5.getMD5Str(str).toUpperCase();
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
		logger.info(MD5.getMD5Str("of111111"));
		String str = "A08566"
				+ "4c625b7861a92c7971cd2029c2fd3c4a"
				+ "15996271050"
				+ "5"
				+ "30M"
				+ "2"
				+ "1"
				+ "1"
				+ "testLL1234567"
				+ keyStr;
		logger.info(MD5.getMD5Str(str).toUpperCase());
	}
}
