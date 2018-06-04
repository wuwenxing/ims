package com.gwghk.ims.marketingtool.manager.market.cami;

/**
 * 
* @ClassName: CamiOfCardItemResponse
* @Description: 欧飞卡密返回的卡信息
* @author lawrence
* @date 2018年5月9日
*
 */
public class CamiOfCardItemResponse {

	private String cardno;
	private String cardpws;
	private String expiretime;

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCardpws() {
		return cardpws;
	}

	public void setCardpws(String cardpws) {
		this.cardpws = cardpws;
	}

	public String getExpiretime() {
		return expiretime;
	}

	public void setExpiretime(String expiretime) {
		this.expiretime = expiretime;
	}

}
