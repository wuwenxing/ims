package com.gwghk.ims.activity.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * 摘要：http请求根据类
 * @author Warren.wu
 * @version 1.0
 * @Date 2016年11月10日
 */
public class HttpUtil {
	
	/**
	 * log
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
	/**
	 * charset
	 */
	public static final String DEF_CHATSET = "UTF-8";
	/**
	 * 连接超时时间（毫秒）
	 */
    public static final int DEF_CONN_TIMEOUT = 30000;
    /**
	 * 超时时间（毫秒）
	 */
    public static final int DEF_READ_TIMEOUT = 30000;
	
    /*public static void main(String[] args) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	
    	try {
    		params.put("key", "3b6ae3c17adb99ce58f67bcaf7fbad9a");
    		params.put("bb", "bb");
			HttpUtil.net("http://op.juhe.cn/idcard/query", params, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}*/
    
	public static String net(String strUrl, Map params,String method) throws Exception {
		HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            LOGGER.info(strUrl);
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    LOGGER.error("请求接口异常：{}，请求参数：{}", e.getMessage(), params);
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
            LOGGER.info("interface result:{}", rs);
//            JSONObject parse = (JSONObject)JSONObject.parse(rs);
//            JSONObject resultJson = parse.getJSONObject("result");
        } catch (IOException e) {
        	LOGGER.error("请求接口异常：{}，请求参数：{}", e.getMessage(), params);
        	throw new Exception(e);	
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
	}
	
	/**
	 * 将map型转为请求参数型
	 * @param data 请求数据的map
	 * @return String
	 */
    public static String urlencode(Map<String,Object> data) {
        StringBuilder sb = new StringBuilder();
        if (data == null || data.size() == 0) {
        	LOGGER.info("interface params is null!");
        	return null;
        }
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
            	LOGGER.error("请求接口参数组装异常：{}", e.getMessage());
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}
