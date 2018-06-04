package com.gwghk.ims.monitor.dal.entity;

/**
 * 摘要：活动消息记录
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年8月23日
 */
public class ActMessageRecord extends BaseEntity {
    private Long id;

    /**
     * 消息类型(任务型:rw,物品兑换型:wpdh)
     */
    private String msgType;

    /**
     * 消息编号
     */
    private Long msgCode;

    /**
     * 消息状态(1:未处理  2、处理中   3：已处理)
     */
    private Integer msgStatus;

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

	public Long getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(Long msgCode) {
		this.msgCode = msgCode;
	}

	public Integer getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(Integer msgStatus) {
		this.msgStatus = msgStatus;
	}
}