package com.gwghk.ims.monitor.dal.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gwghk.ims.monitor.enums.MONITOR_TYPE;

/**
 * 监控条目配置
 * @author jackson.tang
 *
 */
@Table(name="monitor_item")
public class MonitorItem{
	@Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;
	
	private String name;
	/**
	 * 应用模块ID, 这个id用来确定向谁发送告警
	 */
	private Long appId;
	
	/**
	 * 条目类型枚举
	 * @see MONITOR_TYPE
	 */
	@Transient
	private MONITOR_TYPE itemTypeEnum;
	
	/**
	 * 条目类型
	 */
	private int itemType;
	/**
	 * 请求接口,条目类型为1、2时候为方法名称,3为接口api
	 */
	private String interfaceName;
	/**
	 * 请求间隔
	 */
	private Integer requestInterval;
 
	/**
	 * 应用的监控策略
	 */
	@Transient
	private List<AlertStrategy> strategies;

	public MONITOR_TYPE getItemTypeEnum() {
		return itemTypeEnum;
	}

	public void setItemTypeEnum(MONITOR_TYPE itemTypeEnum) {
		this.itemTypeEnum = itemTypeEnum;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public Integer getRequestInterval() {
		return requestInterval;
	}

	public void setRequestInterval(Integer requestInterval) {
		this.requestInterval = requestInterval;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public List<AlertStrategy> getStrategies() {
		return strategies;
	}

	public void setStrategies(List<AlertStrategy> strategies) {
		this.strategies = strategies;
	}
	
}
