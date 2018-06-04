package com.gwghk.ims.common.vo.monitor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 监控数据VO 
 * 默认实例化的时间就是接收的时间
 * @author jackson.tang
 *
 */
public class MonitorDataVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7652065497618262335L;
	/**
	 * 状态：0.失败 1.成功
	 */
	private int code;
	/**
	 * 接收时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
	private Date ocurDate;
	private Object data;
	private String msg;
	
	public MonitorDataVo() {
		//实例化的时间就是接收的时间
		ocurDate=new Date();
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public Date getOcurDate() {
		return ocurDate;
	}

	public void setOcurDate(Date ocurDate) {
		this.ocurDate = ocurDate;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public MonitorDataVo success(Object data) {
		code=1;
		this.data=data;
		return this;
	}
	
	public MonitorDataVo fail(String msg) {
		code=0;
		this.msg=msg;
		return this;
	}
}
