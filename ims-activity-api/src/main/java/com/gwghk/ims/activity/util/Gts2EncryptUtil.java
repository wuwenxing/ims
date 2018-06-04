package com.gwghk.ims.activity.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwghk.ims.common.util.MD5;

public class Gts2EncryptUtil {
  private static String SIGNATURE = "_signature_";
  private static Logger logger = LoggerFactory.getLogger(Gts2EncryptUtil.class);

  public static String sign(String userKey, Map<String, Object> args) {
    TreeMap<String, Object> tm = new TreeMap<String, Object>();
    for (String k : args.keySet()) {
      if (k.equals(SIGNATURE)) { // _signature_
        continue;
      }
      Object vs = args.get(k);
      tm.put(k, vs);
    }

    StringBuilder sb = new StringBuilder();
    for (String key : tm.keySet()) {
      Object arg = tm.get(key);
      sb.append(arg);
      sb.append("&");
    }
    sb.append(userKey);

    logger.debug("post content " + sb);
    return MD5.getMd5(sb.toString()) ;
  }

  // Example
  public static void main(String[] args) throws Exception {
    HashMap m = new HashMap();
    m.put("updateType", "FO_UPDATE_CUSTOMER");
    m.put("id", "990024115");
    m.put("orgPassword", "123456");
    m.put("newPassword", "123456");
    m.put("arg", "[]");
    m.put("_principal_",
        "{\"loginName\":\"hx9999\", \"remoteIpAddress\":\"127.0.0.1\", \"invoker\":\"hx_website\", \"companyId\":\"2\"}");
    System.out.println(sign("afd28a45d93ab166093d85be22f23834", m));
  }
}
