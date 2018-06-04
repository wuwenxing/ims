package com.gwghk.ims.common.enums;

/**
 * 摘要：审核状态
 * @author eva
 * @version 1.0
 * @Date 2017年7月17日
 */
public enum AuditStatusEnum {
	/**待审核*/
    waitForApprove("waitForApprove","待审核"),
    /**审批通过*/
    auditPass("auditPass","审批通过"),
    /**审核拒绝*/
    auditRefuse("auditRefuse","审核拒绝");
	
	private String value;
	private  String labelKey;
	
	AuditStatusEnum(String value,String labelKey){
		this.value = value;
		this.labelKey = labelKey;
	}

	public static String format(String value){
		for(AuditStatusEnum ae : AuditStatusEnum.values()){
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