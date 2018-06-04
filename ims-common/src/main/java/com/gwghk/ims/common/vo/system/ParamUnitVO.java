package com.gwghk.ims.common.vo.system;

import java.io.Serializable;
import java.util.List;

import com.gwghk.ims.common.common.Kuak;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class ParamUnitVO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5602299444192901218L;

	/**
	 * 单位id,
	 */
	private String id;

	/**
	 * 单位属性名,
	 */
	private String proName;

	/**
	 * unitType:单位类型(MONEY:表示金额单位，RMB或USD;cycleTime：表示周期(HOUR[小时]、Day[天]、WEEK[周]
	 * 、YEAR[年]);size:(G,M,KB),other:其它(LOT[手],NUM[个],THIMES[次],%[%])),
	 */
	private String unitType;

	/**
	 * inputType:单位输入类型(select:下拉框，checkbox:复选框,textarea:复文本框)，默认是文本显示label
	 */
	private String inputType;

 

	/**
	 * 是否需要动态加载集合（eg:当前是下拉或复选框，需要动态加载数据字典或枚举或其它表)
	 */
     private Boolean isDynamict;
	 
	 
	 /**
	  * 动态匹配值 主要是接口物品单位与物品类型有关，不同的类型显示不同的单位
	  */
	private String dynamicVal;
	
	

	/**
	 * 是否显示，默认为true
	 */
	private Boolean isShow = true;

	/**
	 * 单位下拉选项集合
	 */
	private List<Kuak> options;
	
	/**
	 * 默认值
	 */
	private String defaultVal;
	/**
	 * 描述
	 */
	@XStreamAsAttribute
	private String desc;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	 
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getDynamicVal() {
		return dynamicVal;
	}
	public void setDynamicVal(String dynamicVal) {
		this.dynamicVal = dynamicVal;
	}
	public Boolean getIsShow() {
		return isShow;
	}
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	 
	public Boolean getIsDynamict() {
		return isDynamict;
	}
	public void setIsDynamict(Boolean isDynamict) {
		this.isDynamict = isDynamict;
	}
 
	public List<Kuak> getOptions() {
		return options;
	}
	public void setOptions(List<Kuak> options) {
		this.options = options;
	}
	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "ParamUnitVO [id=" + id + ", proName=" + proName + ", unitType="
				+ unitType + ", inputType=" + inputType + ", isDynamict="
				+ isDynamict + ", dynamicVal=" + dynamicVal + ", isShow="
				+ isShow + ", options=" + options + ", defaultVal="
				+ defaultVal + ", desc=" + desc + "]";
	}
	 
}
