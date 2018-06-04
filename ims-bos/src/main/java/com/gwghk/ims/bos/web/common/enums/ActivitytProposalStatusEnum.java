package com.gwghk.ims.bos.web.common.enums;

import java.util.ArrayList;
import java.util.List;

import com.gwghk.ims.common.enums.EnumIntf;

/**
 * 摘要：物品类型
 * 
 * @author 
 * @version 1.0
 * @Date 2017年7月24日
 */
public enum ActivitytProposalStatusEnum implements EnumIntf {

	/** 待審批 */
	ActWaitForApprove("待审批", "ActWaitForApprove"),
	/** 已審批 */
	ActHasApproved("已审批", "ActHasApproved"),
	/** 已取消 */
	ActHasCanceled("已取消", "ActHasCanceled");
	
	private final String value;
	private final String labelKey;
	ActivitytProposalStatusEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<ActivitytProposalStatusEnum> getList() {
		List<ActivitytProposalStatusEnum> result = new ArrayList<ActivitytProposalStatusEnum>();
		for (ActivitytProposalStatusEnum ae : ActivitytProposalStatusEnum.values()) {
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(ActivitytProposalStatusEnum ae : ActivitytProposalStatusEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

}
