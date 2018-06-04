package com.gwghk.ims.common.vo.system;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.common.Kuak;
import com.gwghk.unify.framework.common.util.StringUtil;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

 
public class ParamVO  implements Serializable{
 

	/**
	 * 
	 */
	private static final long serialVersionUID = -4497660229632150334L;

	/**
	 * 参数id
	 */
 
	private String id;

	/**
	 * 参数属性名
	 */
	@XStreamAsAttribute
	private String proName;

	/**
	 * 是否必须输入
	 */
	@XStreamAsAttribute
	private Boolean required;

	/**
	 * 输入类型
	 */
	@XStreamAsAttribute
	private String inputType;

	/**
	 * 校验类型
	 */
	@XStreamAsAttribute
	private String validType;

	/**
	 * 默认值
	 */
	@XStreamAsAttribute
	private String defaultVal;

	/**
	 * 是否需要动态加载集合（eg:当前是下拉或复选框，需要动态加载数据字典或枚举或其它表)
	 */
	@XStreamAsAttribute
	private Boolean isDynamict;

	/**
	 * 单位是否为动态显示(true:为是，为空或false:表示固定显示，默认为null，目前主要是接品物品需要)
	 */
	private Boolean dynamicUnit = false;

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
	private ParamUnitVO paramUnit;
 
	/**
	 * 参数单位集合
	 */
	private Map<String,ParamUnitVO> paramUnitMap;
	
	/**
	 * 参数单位集合--对应属性
	 */
	private String paramUnitProName;
	
	/**
	 * 本身参数dynamicOptionName:下拉选项动态名，默认动态名为name+options eg: name="accountType"  dynamicName:accountType_options
	 */
	private String dynamicOptionName;
	
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
		if(isDynamict==null){
			isDynamict =false;
		}
		return isDynamict;
	}

	public void setIsDynamict(Boolean isDynamict) {
		this.isDynamict = isDynamict;
	}

	 

	public Boolean getDynamicUnit() {
		return dynamicUnit;
	}

	public void setDynamicUnit(Boolean dynamicUnit) {
		this.dynamicUnit = dynamicUnit;
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
 
	public List<Kuak> getOptions() {
		return options;
	}

	public void setOptions(List<Kuak> options) {
		this.options = options;
	}

	public ParamUnitVO getParamUnit() {
		return paramUnit;
	}

	public void setParamUnit(ParamUnitVO paramUnit) {
		this.paramUnit = paramUnit;
	}

	public Map<String, ParamUnitVO> getParamUnitMap() {
		return paramUnitMap;
	}

	public void setParamUnitMap(Map<String, ParamUnitVO> paramUnitMap) {
		this.paramUnitMap = paramUnitMap;
	}

	public String getDynamicOptionName() {
		if(this.getIsDynamict()){
			if(StringUtil.isEmpty(dynamicOptionName)){
				dynamicOptionName=this.getProName()+"_options";
			}
		}
		return dynamicOptionName;
	}

	public void setDynamicOptionName(String dynamicOptionName) {
		this.dynamicOptionName = dynamicOptionName;
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
		return "ParamVO [id=" + id + ", proName=" + proName + ", required="
				+ required + ", inputType=" + inputType + ", validType="
				+ validType + ", defaultVal=" + defaultVal + ", isDynamict="
				+ isDynamict + ", dynamicUnit=" + dynamicUnit + ", label="
				+ label + ", desc=" + desc + ", isShow=" + isShow
				+ ", options=" + options + ", paramUnit=" + paramUnit
				+ ", paramUnitMap=" + paramUnitMap + ", paramUnitProName="
				+ paramUnitProName + ", dynamicOptionName=" + dynamicOptionName
				+ ", actTypes=" + actTypes + "]";
	}
 

	 
 
 

}
