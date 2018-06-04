package com.gwghk.ims.common.enums;

import com.gwghk.unify.framework.common.util.StringUtil;


/**
 * 摘要：活动提案状态
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月16日
 */
public enum ActProposalStatusEnum {
	 
	/** 待審批 */
	ACTWAITFORAPPROVE("ActWaitForApprove", "待审批"),
	/** 已審批 */
	ACTHASAPPROVED("ActHasApproved", "已审批"),
	/** 已取消 */
	ACtHASCANCELED("ActHasCanceled", "已取消");

	private final String code;
	private final String name;

	ActProposalStatusEnum(String _code, String name) {
		this.code = _code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static String formatByCode(String code){
		 if(StringUtil.isNotEmpty(code)){
           for(ActProposalStatusEnum ae : ActProposalStatusEnum.values()){
               if(code.equals(ae.getCode())){
                   return ae.getName();
               }
           }
       }
       return null;
   }
}
