package com.gwghk.ims.common.common;

/**
 * 摘要：Api response code
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年9月12日
 */
public enum ApiResultCode{
	/**成功*/
	OK("0", "成功"),
	/**系统异常*/
	EXCEPTION("-1", "系统异常"),
	/**失败*/
	FAIL("-2", "失败"),
	/**连接失败*/
	RpcError("-3","连接失败"),
	/**连接超时*/
	RpcTimeoutError("-4","连接超时"),
	/**调用接口异常*/
	InterfaceError("2","调用接口异常"),
	 /**模拟金额掉单重做*/
    DEMOAMOUNT_REDO("DEMOAMOUNT_REDO", "模拟金额掉单重做"),
	/**重做*/
	REDO("-5", "重做"),
	/**包含非法字符*/
	Err10001("10001","包含非法字符"),
	/**参数有误，请检查*/
	Err10002("10002","参数有误，请检查！"),
	/**账号不能为空*/
	Err10003("10003","账号不能为空！"),
	/**密码不能为空*/
	Err10004("10004","密码不能为空！"),
	/**验证码不能为空*/
	Err10005("10005","验证码不能为空！"),
	/**验证码不正确*/
	Err10006("10006","验证码不正确！"),
	/**账号不正确*/
	Err10007("10007","账号不正确！"),
	/**密码不正确*/
	Err10008("10008","密码不正确！"),
	 /**账号不存在*/
    Err10009("10009", "账号不存在！"),
    /**对不起,请先登录后再操作！*/
    Err20018("20018", "对不起,请先登录后再操作！"),
    /**该浏览器不支持,请换浏览器再试！*/
    Err20019("20019", "该浏览器不支持,请换浏览器再试！"),
    /**参数不合法!*/
    Error40001("40001","参数不合法!"),
    /**获取下一个序列失败!*/
    Error40007("40007","获取下一个序列失败!"),
    
    /**任务不符合参与条件*/
    Task30001("30001","任务不符合参与条件"),
    /**物品不存在！*/
    Task30002("30002","物品不存在！"),
    /**物品兑换已到达上限！*/
    Task30003("30003","物品兑换已到达上限！"),
    /**此活动已经结束，不能继续参加！*/
    Task30004("30004","物品兑换已到达上限！"),
	;
	
	private String code;
	private String message;

	private ApiResultCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}