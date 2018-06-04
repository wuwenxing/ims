package com.gwghk.ims.common.dto.marketingtool;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import com.gwghk.unify.framework.common.util.JsonUtil;

public class Other {

	private List<OtherMsg> otherMsg;

	public List<OtherMsg> getOtherMsg() {
		return otherMsg;
	}

	public void setOtherMsg(List<OtherMsg> otherMsg) {
		this.otherMsg = otherMsg;
	}
	
	public static void main(String[] args) throws Exception{
		String otherMsg = "[{\"imgUrl\":\"http://www.sina.com.cn\",\"otherUrl\":\"http://www.sina.com\"},{\"imgUrl\":\"http://www.sina.com.cn\",\"otherUrl\":\"http://www.sina.com\"}]";
		System.out.println(URLEncoder.encode(otherMsg, "utf-8"));
		String encode = URLEncoder.encode(otherMsg, "utf-8");
		System.out.println(URLDecoder.decode(encode, "utf-8"));
		List<OtherMsg> other = JsonUtil.json2List(otherMsg, OtherMsg.class);
		System.out.println(other.size());
	}
}
