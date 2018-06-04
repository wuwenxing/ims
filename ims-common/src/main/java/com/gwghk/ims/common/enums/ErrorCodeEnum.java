package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * ErrorCode类型定义
 * @author gavin.guo
 */
public enum ErrorCodeEnum {
	// 初始数据校验相关
	initDataTip("该数据为系统初始数据，不可修改与删除", "initDataTip"),
	superAdminResetPassword("该数据只可由superAdmin自己重置", "superAdminResetPassword"),

	// 编号校验相关
	codeExists("code已经存在,请重新输入", "codeExists"),
	userNoExists("账号已经存在,请重新输入", "userNoExists"),
	idNumExists("身份证号已经存在,请重新输入", "idNumExists"),
	recordExists("记录已经重复,请重新输入", "recordExists"),
	childNodeExists("删除的节点存在子节点，请先删除子节点", "childNodeExists"),
	SeqCodeExists("此使用下已存在该编号,请重新输入", "seqCodeExists"),
	
	// 登录校验相关
	userNameNotEmpty("用户名不能为空", "userNameNotEmpty"),
	passwordNotEmpty("密码不能为空", "passwordNotEmpty"),
	companyNotEmpty("公司为空", "companyNotEmpty"),
	validateCodeNotEmpty("验证码不能为空", "validateCodeNotEmpty"),
	validateCodeError("验证码错误", "validateCodeError"),
	userNameError("用户名错误", "userNameError"),
	passwordError("密码错误", "passwordError"),
	userDisable("此用户被禁用", "userDisable"),
	roleEmptyOrDisable("此用户未分配角色或角色被禁用", "roleEmptyOrDisable"),
	error("用户名或密码错误", "error"),
	sessionTimeOut("session超时", "sessionTimeOut"),
	noPermission("此用户无权限", "noPermission"),

	// 修改密码相关
	oldPasswordError("原密码错误", "oldPasswordError"),
	passwordTheSame("新密码与原密码相同", "passwordTheSame"),
	/**
	 * 此賬號已曾經激活過
	 */
	ErrorAG06("此賬號已曾經激活過", "ErrorAG06"),
	/**
	 * 激活條件並未設定所需的累積存款金額及所有平台
	 */
	ErrorAG03("激活條件並未設定所需的累積存款金額及所有平台", "ErrorAG03"),
	
	noRecordExists("excel没有记录,请重新导入", "noRecordExists"),
    excelFormatError("导入excel内容格式有错误,请重新编辑后再导入", "excelFormatError"),
	
	//标记名
	tagNameExists("标记名已经存在", "tagNameExists"),
	tagNameNoExists("标记名不存在", "tagNameNoExists"),
	tagCustomerExists("已经标记过该用户", "tagCustomerExists"),
	paramError("参数有错误", "paramError"),
	Error1008("提案狀態不符!","1008"),
	Error1067("確認人及審批人不能為同一人", "1067"),
	
	/**账户币种不能重复*/
	Error1068("账户币种不能重复", "1068"),
	/**来源货币和兑换货币不能重复*/
	Error1069("该业务、来源货币和兑换货币的汇率设置已存在", "1069"),
	
	/**新增統一帳號提案必須包含交易賬號*/
    ErrorCP01("ErrorCP01", "新增統一帳號提案必須包含交易賬號"),
    /**客户提案不存在*/
    ErrorCP02("ErrorCP02", "客户提案不存在"),
    /**提案号不能為空*/
    ErrorCP03("ErrorCP03", "提案号不能為空"),
    /**客户提案狀態不符!*/
    ErrorCP04("ErrorCP04","客户提案狀態不符!"),
    /**账户提案不存在!*/
    ErrorCP05("ErrorCP05","账户提案不存在!"),
    /**账户提案狀態不符!*/
    ErrorCP06("ErrorCP06","账户提案狀態不符!"),
    /**批核交易账号失败!*/
    ErrorCP07("ErrorCP07","批核交易账号失败!"),
    
    Error1101("用户记录不能删除","Error1101")
	;
	
	private final String value;
	private final String labelKey;
	ErrorCodeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<ErrorCodeEnum> getList(){
		List<ErrorCodeEnum> result = new ArrayList<ErrorCodeEnum>();
		for(ErrorCodeEnum ae : ErrorCodeEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
