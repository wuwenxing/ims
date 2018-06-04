package com.gwghk.ims.marketingtool.manager.market.online;

/**
 * 欧飞话费充值-返回实体
 * <?xml version="1.0" encoding="GB2312" ?> 
	<orderinfo>
	  	<err_msg /> 
		<retcode>1</ retcode > //支付状态，结果参考交易结果码备注
	  	<orderid>S0703300003</orderid> //欧飞订单号
	  	<cardid>142303</cardid> //商品编号
	  	<cardnum>1</cardnum> //数量
		<ordercash>99.8</ordercash>	//订单金额
	  	<cardname>江苏移动100元充值</cardname> 
	  	<sporder_id>200912180001</sporder_id> 
	  	<game_userid>13813834333 </game_userid> 
	  	<game_state>0</game_state> //如果成功将为1，澈消(充值失败)为9，充值中为0,只能当状态为9时，商户才可以退款给用户。
	</orderinfo>
 * @author wayne
 *
 */
public class OnlineOfResponse {
	
	private String err_msg;
	private String retcode;
	private String orderid;
	private String cardid;
	private String cardnum;
	private String ordercash;
	private String cardname;
	private String sporder_id;
	private String game_userid;
	private String game_state;//如果成功将为1，澈消(充值失败)为9，充值中为0,只能当状态为9时，商户才可以退款给用户。
	
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getCardnum() {
		return cardnum;
	}
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}
	public String getOrdercash() {
		return ordercash;
	}
	public void setOrdercash(String ordercash) {
		this.ordercash = ordercash;
	}
	public String getCardname() {
		return cardname;
	}
	public void setCardname(String cardname) {
		this.cardname = cardname;
	}
	public String getSporder_id() {
		return sporder_id;
	}
	public void setSporder_id(String sporder_id) {
		this.sporder_id = sporder_id;
	}
	public String getGame_userid() {
		return game_userid;
	}
	public void setGame_userid(String game_userid) {
		this.game_userid = game_userid;
	}
	public String getGame_state() {
		return game_state;
	}
	public void setGame_state(String game_state) {
		this.game_state = game_state;
	}
}
