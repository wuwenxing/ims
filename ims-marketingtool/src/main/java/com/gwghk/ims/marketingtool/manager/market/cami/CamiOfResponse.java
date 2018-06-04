package com.gwghk.ims.marketingtool.manager.market.cami;

import java.util.List;

import com.thoughtworks.xstream.XStream;

/**
 * 欧飞卡密充值-返回实体
 * <?xml version=”1.0” encoding=”GB2312” ?> 
	<orderinfo>
  		<err_msg /> 
		<retcode>1</ retcode >
  		<orderid >S0703280004</orderid >			//CP流水号
  		<cardid>360101</cardid>						//卡编码 
  		<cardnum>1</cardnum>						//购买卡数量
  		<ordercash>1</ordercash>					//订单金额(元)
  		<cardname>欧飞1分钱支付体验卡</cardname>			//卡名称 
  		<sporder_id>123</sporder_id> 				//SP订单号
		<cards>
			<card>
	  			<cardno>you</cardno> 				//卡号
	  			<cardpws>success</cardpws>			//密码 
	  			<expiretime>4000-12-31</expiretime>	 //有效期
	  		</card>
	  	</cards>
  </orderinfo>
 * 
 *
 */
public class CamiOfResponse {
	
	private String err_msg;
	private String retcode;
	private String orderid;
	private String cardid;
	private String cardnum;
	private String ordercash;
	private String cardname;
	private String sporder_id;
	
	private List<CamiOfCardItemResponse> cards;
	
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
	public List<CamiOfCardItemResponse> getCards() {
		return cards;
	}
	public void setCards(List<CamiOfCardItemResponse> cards) {
		this.cards = cards;
	}
	
	public static void main(String[] args) {
		String xml = "<orderinfo>\n" +
                "  <err_msg /> \n" +
                "  <retcode>1</retcode >\n" +
                "  <cards>\n" +
                "    <card>\n" +
                "      <cardno>you</cardno> \n" +
                "    <cardpws>success</cardpws>\n" +
                "    <expiretime>4000-12-31</expiretime>\n" +
                "     </card>\n" +
                "    </cards>\n" +
                "  </orderinfo>\n" ;
		//创建xStream对象
        XStream xstream = new XStream();
        //将别名与xml名字相对应
        xstream.alias("orderinfo", CamiOfResponse.class);
        xstream.alias("card", CamiOfCardItemResponse.class);
        CamiOfResponse camiOfResponse = (CamiOfResponse) xstream.fromXML(xml);
        System.out.println(camiOfResponse.getRetcode());
        System.out.println(camiOfResponse.getCards().get(0).getCardno());
	}
}
