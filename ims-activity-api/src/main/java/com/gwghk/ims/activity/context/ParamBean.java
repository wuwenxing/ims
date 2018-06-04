package com.gwghk.ims.activity.context;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.common.Kuak;
import com.gwghk.unify.framework.common.util.StringUtil;


public class ParamBean {

	/**
	 * 参数id
	 */
	private String id;
	 

	/**
	 * 参数属性名
	 */
	private String proName;

	/**
	 * 是否必须输入
	 */
	private Boolean required;

	/**
	 * 是否只读的参数，不需要保存，默认false
	 */
	private Boolean readOnly=false;
	/**
	 * 输入类型
	 */
	private String inputType;

	/**
	 * 校验类型
	 */
	private String validType;

	/**
	 * 默认值
	 */
	private String defaultVal;

	/**
	 * 是否需要动态加载集合（eg:当前是下拉或复选框，需要动态加载数据字典或枚举或其它表)
	 */
	private Boolean isDynamict;
	
	/**
	 * dynamicOptionName:下拉选项动态名，默认动态名为name+options eg: name="accountType"  dynamicName:accountType_options
	 */
	private String dynamicOptionName;

	/**
	 * 单位是否为动态显示(true:为是，为空或false:表示固定显示，默认为null，目前主要是接品物品需要)
	 */
	private Boolean dynamicUnit =false;;

	/**
	 * 此参数前置提示语
	 */
	private String label;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 是否显示，默认为true
	 */
	private Boolean isShow = true;

	/**
	 * 下拉选项集合
	 */
	private List<Kuak> options;
	
	/**
	 * 参数单位
	 */
	private ParamUnitBean paramUnit;
 
	/**
	 * 参数单位集合
	 */
	private Map<String,ParamUnitBean> paramUnitMap;
	
	/**
	 * 参数单位集合--属性
	 */
	private String paramUnitProName;
	
	
	/**
	 * 支持的活动类型
	 */
	private String actTypes;

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

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getInputType() {
		if(StringUtil.isBlank(inputType)){
			return "input";//默认为input
		}
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getValidType() {
		return validType;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public Boolean getIsDynamict() {
		return isDynamict;
	}

	public void setIsDynamict(Boolean isDynamict) {
		this.isDynamict = isDynamict;
	}

	 

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
 
 

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	 
	public String getDynamicOptionName() {
		return dynamicOptionName;
	}

	public void setDynamicOptionName(String dynamicOptionName) {
		this.dynamicOptionName = dynamicOptionName;
	}

	public List<Kuak> getOptions() {
		return options;
	}

	public void setOptions(List<Kuak> options) {
		this.options = options;
	}

	public ParamUnitBean getParamUnit() {
		return paramUnit;
	}

	public void setParamUnit(ParamUnitBean paramUnit) {
		this.paramUnit = paramUnit;
	}

	public Map<String, ParamUnitBean> getParamUnitMap() {
		return paramUnitMap;
	}

	public void setParamUnitMap(Map<String, ParamUnitBean> paramUnitMap) {
		this.paramUnitMap = paramUnitMap;
	}

	
	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	
	public Boolean getDynamicUnit() {
		return dynamicUnit;
	}

	public void setDynamicUnit(Boolean dynamicUnit) {
		this.dynamicUnit = dynamicUnit;
	}

	public String getActTypes() {
		return actTypes;
	}

	public void setActTypes(String actTypes) {
		this.actTypes = actTypes;
	}

	public String getParamUnitProName() {
		return paramUnitProName;
	}

	public void setParamUnitProName(String paramUnitProName) {
		this.paramUnitProName = paramUnitProName;
	}

	@Override
	public String toString() {
		return "ParamBean [id=" + id + ", proName=" + proName + ", required="
				+ required + ", readOnly=" + readOnly + ", inputType="
				+ inputType + ", validType=" + validType + ", defaultVal="
				+ defaultVal + ", isDynamict=" + isDynamict
				+ ", dynamicOptionName=" + dynamicOptionName + ", dynamicUnit="
				+ dynamicUnit + ", label=" + label + ", desc=" + desc
				+ ", isShow=" + isShow + ", options=" + options
				+ ", paramUnit=" + paramUnit + ", paramUnitMap=" + paramUnitMap
				+ ", paramUnitProName=" + paramUnitProName + ", actTypes="
				+ actTypes + "]";
	}
 

	 

}
