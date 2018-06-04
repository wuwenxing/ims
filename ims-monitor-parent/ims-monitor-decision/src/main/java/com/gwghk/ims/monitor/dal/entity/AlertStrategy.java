package com.gwghk.ims.monitor.dal.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 监控策略
 * @author jackson.tang
 *
 */
@Table(name="alert_strategy")
public class AlertStrategy{
	/**
	 * 无穷大表示方法
	 */
	@Transient
	public static final String INFINITE="infinite";
	@Id
    @Column(name="strategy_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long strategyId;
	
	private String name;
	/**
	 * 报警级别：40.黄色警告 50.红色警告
	 */
	private int alertLevel;
	/**
	 * 时间容量
	 */
	private Integer timeRange;
	/**
	 * 判定符号:1.包含或者确定值 2.区间范围
	 */
	private int judgeSymbol;
	/**
	 * 判定值,例如:"value"|"min:infinite"
	 */
	private String judgeValue;
	/**
	 * 重现次数
	 */
	private int recurrenceCount;
	
	public Long getStrategyId() {
		return strategyId;
	}
	public void setStrategyId(Long strategyId) {
		this.strategyId = strategyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAlertLevel() {
		return alertLevel;
	}
	public void setAlertLevel(int alertLevel) {
		this.alertLevel = alertLevel;
	}
	public Integer getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(Integer timeRange) {
		this.timeRange = timeRange;
	}
	public int getJudgeSymbol() {
		return judgeSymbol;
	}
	public void setJudgeSymbol(int judgeSymbol) {
		this.judgeSymbol = judgeSymbol;
	}
	public String getJudgeValue() {
		return judgeValue;
	}
	public void setJudgeValue(String judgeValue) {
		this.judgeValue = judgeValue;
	}
	public int getRecurrenceCount() {
		return recurrenceCount;
	}
	public void setRecurrenceCount(int recurrenceCount) {
		this.recurrenceCount = recurrenceCount;
	}
	public static String getInfinite() {
		return INFINITE;
	}
	
	
}
