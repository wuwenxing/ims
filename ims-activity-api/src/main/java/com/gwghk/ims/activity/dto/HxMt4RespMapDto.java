package com.gwghk.ims.activity.dto;

import java.util.Map;

/**
 * 
 * @ClassName: HxMt4LoginDto
 * @Description: HX-MT4登录返回的sid数据
 * @author lawrence
 * @date 2017年12月14日
 *
 */
public class HxMt4RespMapDto {

	private String status;
	private Map<String, String> data;
	private String msg;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
