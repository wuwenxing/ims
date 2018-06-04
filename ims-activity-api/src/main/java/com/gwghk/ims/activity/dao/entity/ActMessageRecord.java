package com.gwghk.ims.activity.dao.entity;

import com.gwghk.ims.common.common.BaseEntity;

public class ActMessageRecord extends BaseEntity {
	private Long id;

	/**
	 * 消息类型(任务型:rw,物品兑换型:wpdh,入金赠金型:rjzj,层级型:cj)
	 */
	private String msgType;

	/**
	 * 消息编号
	 */
	private Integer msgCode;

	/**
	 * 消息状态(1:未处理 2、处理中 3：已处理)
	 */
	private Boolean msgStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Integer getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(Integer msgCode) {
		this.msgCode = msgCode;
	}

	public Boolean getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(Boolean msgStatus) {
		this.msgStatus = msgStatus;
	}

}