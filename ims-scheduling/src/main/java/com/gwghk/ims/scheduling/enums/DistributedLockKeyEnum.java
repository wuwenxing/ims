package com.gwghk.ims.scheduling.enums;

/**
 * 摘要：分布式锁键值
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年7月5日
 */
public enum DistributedLockKeyEnum {
	JOIN_QUALIFY_LOCK_KEY("join_qualify_lock_key_", "参与资格锁key"),
	PRIZE_RECORD_LOCK_KEY("prize_record_lock_key_", "发放记录锁key"),
	SETTLEMENT_LOCK_KEY("settlement_lock_key_", "结算锁key");

	
	private String value;
	private String name;

	DistributedLockKeyEnum(String value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}