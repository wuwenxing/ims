package com.gwghk.ims.marketingtool.manager.market.online;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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

import com.gwghk.ims.common.util.DateUtil;
import com.gwghk.ims.marketingtool.dao.entity.ImsRechargeLogDetail;
import com.gwghk.ims.marketingtool.util.AppConfigUtil;
import com.gwghk.ims.marketingtool.util.MD5;

/**
 * 欧飞话费服务实现类
 * 
 * @author wayne
 */
@SuppressWarnings("deprecation")
public class OnlineOfServer {

	private static final Logger logger = LoggerFactory.getLogger(OnlineOfServer.class);

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
	 * 话费监听器
	 */
	private List<OnlineListener<ImsRechargeLogDetail>> onlineListeners = new ArrayList<OnlineListener<ImsRechargeLogDetail>>();

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
		url = AppConfigUtil.getProperty("of.online.url");
		callbackUrl = AppConfigUtil.getProperty("of.online.callback.url");
		systemActive = AppConfigUtil.getProperty("system.active");
		logger.info("userid={},password={},keyStr={},url={},systemActive={},callbackUrl={}", userid, password, keyStr,
				url, systemActive, callbackUrl);
	}

	/**
	 * 充值多条话费
	 */
	public void send(List<OnlineInfo> onlineInfos) {
		for (OnlineInfo onlineInfo : onlineInfos) {
			send(onlineInfo);
		}
	}

	/**
	 * 充值单条话费;
	 */
	@SuppressWarnings("unchecked")
	public OnlineContext<ImsRechargeLogDetail> send(final OnlineInfo onlineInfo) {
		OnlineContext<ImsRechargeLogDetail> onlineContext = new OnlineContext<ImsRechargeLogDetail>();
		onlineContext.setOnlineInfo(onlineInfo);
		doBefore(onlineContext);
		try {
			// 电话号码
			String phones = onlineInfo.getPhones();
			// 第三方交易id
			String customId = onlineInfo.getCustomId();
			// 面值
			String size = onlineInfo.getSize();

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("userid", userid);// SP编码如（A00001）
			parameters.put("userpws", password);// SP接入密码
			parameters.put("cardid", "140101");// 快充140101，慢充170101
			parameters.put("cardnum", size);// 面值，快充可选面值（0.01、1、2、5、10、20、30、50、100、200、300、500） 慢充可选面值（30、50、100）
			parameters.put("sporder_id", customId);// Sp商家的订单号（商户传给欧飞的唯一订单编号）
			parameters.put("sporder_time", DateUtil.formatDateToString(new Date(), "yyyyMMddHHmmss"));// 订单时间 （yyyyMMddHHmmss 如：20070323140214）
			parameters.put("game_userid", phones);// 待充值手机号码
			parameters.put("md5_str", getSig(parameters));// 签名串，具体加密规则参考接口说明
			parameters.put("ret_url", callbackUrl); // 回调第三方的地址,不必填
			parameters.put("version", "6.0"); // 固定值-6.0
			String result = doPost(url, parameters);
			/**
			 * <?xml version="1.0" encoding="GB2312" ?> 
				<orderinfo>
				  	<err_msg /> 
					<retcode>1</ retcode > //支付状态，结果参考交易结果码备注
				  	<orderid>S0703300003</orderid> //欧飞订单号
				  	<cardid>142303</cardid> //商品编号
				  	<cardnum>1</cardnum> //数量
					<ordercash>99.8</ordercash>	//订单金额
				  	<cardname>江苏移动100元充值</cardname> 
				  	<sporder_id>200912180001</sporder_id> 
				  	<game_userid>13813834333 </game_userid> 
				  	<game_state>0</game_state> //如果成功将为1，澈消(充值失败)为9，充值中为0,只能当状态为9时，商户才可以退款给用户。
				</orderinfo>
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
							OnlineOfResponse response = new OnlineOfResponse();
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
							onlineContext.setResponse(response);
						}
					}
				}
			}
			doAfter(onlineContext);
		} catch (Exception e) {
			logger.error("of-充值话费出现异常", e);
			onlineContext.setThrowable(e);
			doAfterThrowable(onlineContext);
		}
		return onlineContext;
	}

	/**
	 * 给发送器添加监听器
	 * 
	 * @param onlineListener
	 */
	public void addOnlineListener(OnlineListener<ImsRechargeLogDetail> onlineListener) {
		this.onlineListeners.add(onlineListener);
	}

	private void doBefore(OnlineContext<ImsRechargeLogDetail> onlineContext) {
		for (OnlineListener<ImsRechargeLogDetail> onlineListener : this.onlineListeners) {
			onlineListener.updateBefore(onlineContext);
		}
	}

	private void doAfter(OnlineContext<ImsRechargeLogDetail> onlineContext) {
		for (OnlineListener<ImsRechargeLogDetail> onlineListener : this.onlineListeners) {
			onlineListener.updateAfter(onlineContext);
		}
	}

	private void doAfterThrowable(OnlineContext<ImsRechargeLogDetail> onlineContext) {
		for (OnlineListener<ImsRechargeLogDetail> onlineListener : this.onlineListeners) {
			onlineListener.updateAfterThrowable(onlineContext);
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
				+ parameters.get("cardid")
				+ parameters.get("cardnum") 
				+ parameters.get("sporder_id")
				+ parameters.get("sporder_time")
				+ parameters.get("game_userid")
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
