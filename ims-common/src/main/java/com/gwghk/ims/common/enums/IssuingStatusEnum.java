package com.gwghk.ims.common.enums;

/**
 * 摘要：发放状态
 * @author eva
 * @version 1.0
 * @Date 2017年7月17日
 */
public enum IssuingStatusEnum {
	/**在库*/
	in_library("in_library","在库"),
    /**出库*/
    out_library("out_library","出库"),
    /**待发放*/
    issuing("issuing","待发放"),
    /**发放中*/
    in_distributed("in_distributed","发放中"),
    /**等待中*/
    waiting("waiting","等待中"),
    /**发放成功*/
    issue_success("issue_success","发放成功"),
    /**发放失败*/
    issue_fail("issue_fail","发放失败"),
    /**取消发放*/
    issue_cancel("issue_cancel","取消发放");
	 
	private String value;
	private  String labelKey;
	
	IssuingStatusEnum(String value,String labelKey){
		this.value = value;
		this.labelKey = labelKey;
	}

	public static String format(String value){
		for(IssuingStatusEnum ae : IssuingStatusEnum.values()){
			if(ae.getValue().equals(value)){
				return ae.getLabelKey();
			}
		}
		return value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }
}