package com.gwghk.ims.common.common;

/**
 * 摘要：Mis后台-Api response code
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年9月12日
 */
public enum MisResultCode{
	/**成功*/
	OK("OK", "成功"),
	/**失败*/
	FAIL("FAIL", "失败"),
	/**系统异常*/
	EXCEPTION("-1", "系统异常"),
	/**重做*/
	REDO("REDO", "重做"),
	/**连接失败*/
	RpcError("0","连接失败"),
	/**连接超时*/
	RpcTimeoutError("1","连接超时"),
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
	/**对不起，请先登录！*/
	Err10009("10009","对不起，请先登录！"),
	/**对不起，无权限访问！*/
	Err10010("10010","对不起，无权限访问！"),
    
     /**获取序列号失败*/
    Error10011("10011","获取序列号失败!"),
    /**模板文件格式有误!*/
    Error10012("10012","模板文件格式有误!"),

    /**对不起,请先登录后再操作！*/
    Err20018("20018", "对不起,请先登录后再操作！"),
    /**该浏览器不支持,请换浏览器再试！*/
    Err20019("20019", "该浏览器不支持,请换浏览器再试！"),
    /**参数不合法!*/
    Error40001("40001","参数不合法!"),
    /**获取下一个序列失败!*/
    Error40007("40007","获取下一个序列失败!"),
    /**上传失败，导入数据为空*/
    Error40008("4008","上传失败，导入数据为空！"),
    
    /**上传失败，上传文件最大为5M*/
    Error40009("4009","上传失败，上传文件最大为5M!"),
    
    
    //--------------活动任务  用20000开头-------------------
    /**活动不存在！*/
    Act20000("20000","活动不存在！"),

     /**保存失败，系统已存在此活动编号!**/
    Act20001("20001","保存失败，系统已存在此活动编号!"),
    /**保存失败，该活动下已存在此参与条件信息!**/
    Act20002("20002","保存失败，该活动下已存在此参与条件信息!"),
    /**活动参与信息不存在**/
    Act20003("20003","活动参与信息不存在！"),
    /**未查询到关联的模拟账户！*/
    Act20004("20004","未查询到关联的模拟账户！"),
    /**未查询到模拟账户信息！*/
    Act20005("20005","未查询到模拟账户信息！"),
    /**真实账户下MT4平台账户关联不了对应的模拟账户！*/
    Act20006("20006","真实账户下MT4平台账户关联不了对应的模拟账户！"),
    /**超过模拟账号保留金最低限制！*/
    Act20007("20007","超过模拟账号保留金最低限制！"),
    /**模拟账户余额不足！*/
    Act20008("20008","模拟账户余额不足！"),
    /**很抱歉，活动暂不支持延期*/
    Act20009("20009","很抱歉，活动暂不支持延期！"),
    /**已取消的活动提案不允许修改！*/
    Act20010("20010","操作失败，已取消的活动提案不允许修改！"),
    /**该活动已经审批过，不能再次审批！*/
    Act20011("20011","该活动已经审批过，不能再次审批"),
    
    /**操作失败，状态不匹配！**/
    Act20012("20012","操作失败，状态不匹配！"),
	
    /**活动结束时间小于当前时间!**/
    Act20013("20013","活动结束时间小于当前时间!"),
    
    /**操作失败，已失效的活动不允许修改!**/
    Act20014("20014","操作失败，已失效的活动不允许修改！"),
    /**数据没有变化，无需修改**/
    Act20015("20015","数据没有变化，无需修改"),
    /**已审批且有效的活动才允许上传黑白名单！**/
    Act20016("20016","已审批且有效的活动才允许上传黑白名单！"),
    
 
    //--------------活动黑白名单  用21000开头-------------------
    /**文件不存在!*/
    Act21001("21001","文件不存在！"),
    /**文件读取有误!*/
    Act21002("21002","文件读取有误!"),
    /**活动名单已经存在*/
    Act21003("21003","名单已存在!"),
    
    //--------------活动任务  用22000开头-------------------
    /**任务不存在*/
    Task22000("22000","任务不存在！"),
    /**任务识别码不能为空*/
    Task22001("22001","任务识别码不能为空！"),
    /**规则识别码不能为空！*/
    Task22002("22002","规则识别码不能为空！"),
    /**任务名称不能为空！*/
    Task22003("22003","任务名称不能为空"),
    /**任务识别码已存在！*/
    Task22004("22004","任务识别码已存在！"),
    /**规则识别码已存在！*/
    Task22005("22005","规则识别码已存在！"),
    /**任务名称重复！*/
    Task22006("22006","任务名称重复！"),
    /**获取任务序列号失败！*/
    Task22007("22007","获取任务序列号失败！"),
    /**任务已经被引用！*/
    Task22008("22008","任务已经被引用！"),
    /**系统任务不允许修改！*/
    Task22009("22009","系统任务不允许修改！"),
    /**系统任务不允许删除！*/
    Task22010("22010","系统任务不允许删除！"),
    
    //--------------物品发放记录  用23000开头-------------------   
    /**请选择要操作的发放记录！*/
    Prize23001("23001","请选择要操作的发放记录！"),
    /**该平台账户不存在！*/
    Prize23002("23002","该平台账户不存在！"),
    /**该平台账户不存在！*/
    Prize23003("23003","需要输入发放额度！"),
    
    Prize23004("23004","上传名单失败，导入数据为空！"),
    
    //--------------订单  用24000开头-------------------   
    /**快递公司不能为空！*/
    Order24000("24000","快递公司不能为空！"),
    /**物流单号不能为空！*/
    Order24001("24001","物流单号不能为空！"),
    
    //--------------活动物品  用31000开头-------------------
    /**保存失败，该串码编号在系统下已存在*/
    Item31001("31001","保存失败，该串码编号在系统下已存在"),
    /**
     * 删除失败，该物品下存在串码，不能删除!
     */
    Item31002("31002","删除失败，物品下存在串码，不能删除!"),
    /**
     *  物品正在被活动使用中！
     */
    Item31003("31003","物品正在被活动使用中！"),
    
    /**保存失败，该物品编号系统已存在*/
    Item31004("31004","保存失败，该物品编号系统已存在！"),
    
    /**保存失败，该物品系统已不存在*/
    Item31005("31005","保存失败，该物品系统已不存在！"),
    
    /**保存失败，已存在相同的物品名称*/
    Item31006("31006","保存失败，已存在相同的物品名称！"),
    /**删除失败，该物品有活动正在使用，不能删除!*/
    Item31007("31007","删除失败，该物品有活动正在使用，不能删除!"),
    
    //--------------营销工具  消息推送 用40000开头-------------------
    /**该公司不支持APP消息推送！*/
    Msg40001("40001","该公司不支持APP消息推送！"),
    /**绑定的物品或活动不生效！*/
    Msg40002("40002","绑定的物品或活动不生效！"),
    /**app消息模板编号不存在！*/
    Msg40003("40003","app消息模板编号不存在！"),
    /**app消息模板不存在！*/
    Msg40004("40004","app消息模板不存在！"),
    /**绑定编号或模板编号不能为空*/
    Msg40005("40005","绑定编号或模板编号不能为空！"),
    /**短信消息模板编号不存在！*/
    Msg40006("40006","短信消息模板编号不存在！"),
    /**短信消息模板不存在！*/
    Msg40007("40007","短信消息模板不存在！"),
    /**该公司不支持短信发送*/
    Msg40008("40008","该公司不支持短信发送！"),
    /**模板编号已存在*/
    Msg40009("40009","模板编号已存在！"),
    /**该消息不允许重发！*/
    Msg40010("40010","该消息不允许重发！"),
    /**绑定的编号已存在！*/
    Msg40011("40011","绑定的编号已存在！"),
    
    //---------------基础服务 用60000开头------------------
    /**key不能重复*/
    Base60001("60001","key不能重复！"),
    
    Goods50001("50005","保存失败，该商品已不存在！"),

   
	;
	
	private String code;
	private String message;
	
	public static MisResultCode getByCode(String code){
		for(MisResultCode ae : MisResultCode.values()){
			if(ae.getCode().equals(code)){
				return ae;
			}
		}
		return null;
	}
	
	
	private MisResultCode(String code, String message) {
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