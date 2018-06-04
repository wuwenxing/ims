package com.gwghk.ims.marketingtool.manager.market.cami;

/**
 * 
* @ClassName: CamiInfo
* @Description: 卡密所需要的参数
* @author lawrence
* @date 2018年5月7日
*
 */
public class CamiInfo {
	/**
	 * 11位手机号码
	 */
	private String phones;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 商品编号
	 */
	private String cardid;
	/**
	 * 业务平台
	 */
	private Long companyId;
	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * ip
	 */
	private String ip;
	/**
	 * 卡密名称
	 */
	private String camiName;

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCamiName() {
		return camiName;
	}

	public void setCamiName(String camiName) {
		this.camiName = camiName;
	}

}
