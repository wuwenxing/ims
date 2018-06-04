package com.gwghk.ims.marketingtool.manager.market.cami;

/**
 * 
* @ClassName: CamiOfReqInfo
* @Description: 卡密欧飞请求参数
* @author lawrence
* @date 2018年5月7日
*
 */
public class CamiOfReqInfo {

	/**
	 * SP编码
	 */
	private String userid;
	/**
	 * SP接口密码
	 */
	private String userpws;
	/**
	 * 商品编号由欧飞产品部门提供
	 */
	private String cardid;
	/**
	 * 提卡数量
	 */
	private String cardnum;
	/**
	 * 商家传给欧飞的唯一单号
	 */
	private String sporder_id;
	/**
	 * 订单时间 （yyyyMMddHHmmss)
	 */
	private String sporder_time;
	/**
	 * 签名串
	 */
	private String md5_str;
	/**
	 * 接收卡密信息的手机号码
	 */
	private String phone;
	/**
	 * 接收卡密信息的email地址
	 */
	private String email;
	/**
	 * 固定值6.0
	 */
	private String version;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpws() {
		return userpws;
	}

	public void setUserpws(String userpws) {
		this.userpws = userpws;
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

	public String getSporder_id() {
		return sporder_id;
	}

	public void setSporder_id(String sporder_id) {
		this.sporder_id = sporder_id;
	}

	public String getSporder_time() {
		return sporder_time;
	}

	public void setSporder_time(String sporder_time) {
		this.sporder_time = sporder_time;
	}

	public String getMd5_str() {
		return md5_str;
	}

	public void setMd5_str(String md5_str) {
		this.md5_str = md5_str;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
