package com.gwghk.ims.marketingtool.manager.market.cami;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.util.DateUtil;
import com.gwghk.ims.marketingtool.dao.entity.ImsCamiLogDetail;
import com.gwghk.ims.marketingtool.util.AppConfigUtil;
import com.gwghk.ims.marketingtool.util.MD5;
import com.gwghk.unify.framework.common.util.HttpClientUtil;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.thoughtworks.xstream.XStream;

/**
 * 
* @ClassName: CamiOfServer
* @Description: 欧飞卡密服务实现类
* @author lawrence
* @date 2018年5月9日
*
 */
public class CamiOfServer {

	private static final Logger logger = LoggerFactory.getLogger(CamiOfServer.class);

	@Value("${of.userid}")
	private  static String userid;
	@Value("${of.password}")
	private  static String password ;
	@Value("${of.keyStr}")
	private static String keyStr;
	@Value("${of.cami.url}")
	private static String url;

	
	static {
		// Set connection pool
		userid = AppConfigUtil.getProperty("of.userid");
		password = MD5.getMD5Str(AppConfigUtil.getProperty("of.password"));
		keyStr = AppConfigUtil.getProperty("of.keyStr");
		url = AppConfigUtil.getProperty("of.cami.url");
		
	 
		
	}
	
	/**
	 * 卡密监听器
	 */
	private List<CamiListener<ImsCamiLogDetail>> camiListeners = new ArrayList<CamiListener<ImsCamiLogDetail>>();

	/**
	 * 充值卡密;
	 */
	@SuppressWarnings("unchecked")
	public CamiContext<ImsCamiLogDetail> send(CamiInfo camiInfo) {
		if (!CompanyEnum.hx.getLongId().equals(camiInfo.getCompanyId() + "")) {
			logger.info("欧飞卡密通道目前只有HX使用!");
			return null;
		}

		CamiContext<ImsCamiLogDetail> camiContext = new CamiContext<ImsCamiLogDetail>();
		camiContext.setCamiInfo(camiInfo);
		doBefore(camiContext);
		try {
			CamiOfReqInfo camiOfReqInfo = new CamiOfReqInfo();
			camiOfReqInfo.setUserid(userid);
			camiOfReqInfo.setUserpws(password);
			camiOfReqInfo.setCardid(camiInfo.getCardid());
			camiOfReqInfo.setCardnum("1");
			camiOfReqInfo.setSporder_id(camiInfo.getOrderNo());
			camiOfReqInfo.setSporder_time(DateUtil.formatDateToString(new Date(), "yyyyMMddHHmmss"));
//			camiOfReqInfo.setPhone(camiInfo.getPhones());
//			camiOfReqInfo.setEmail(camiInfo.getEmail());
			camiOfReqInfo.setVersion("6.0");
			camiOfReqInfo.setMd5_str(getSig(camiOfReqInfo));
			Map<String, String> requestParams = JsonUtil.json2Obj(JsonUtil.obj2Str(camiOfReqInfo), Map.class);
			
			String result = HttpClientUtil.doPostWithMap(url, requestParams, null);
			/**
			 * <?xml version=”1.0” encoding=”GB2312” ?> 
				-	<orderinfo>
				  <err_msg /> 
				<retcode>1</ retcode >
				  < orderid >S0703280004</ orderid >			//CP流水号
				  <cardid>360101</cardid>						//卡编码 
				  <cardnum>1</cardnum>						//购买卡数量
				  <ordercash>1</ordercash>						//订单金额(元)
				  <cardname>欧飞1分钱支付体验卡</cardname>	//卡名称 
				  <sporder_id>123</sporder_id> 				//SP订单号
				-	<cards>
				-	<card>
				  <cardno>you</cardno> 					//卡号
				  <cardpws>success</cardpws>			//密码 
				  <expiretime>4000-12-31</expiretime>	 //有效期
				  </card>
				  </cards>
				  </orderinfo>
			 */
			logger.info("result:{}",result);
			if (StringUtils.isNotBlank(result)) {
				XStream xstream = new XStream();
				// 将别名与xml名字相对应
				xstream.alias("orderinfo", CamiOfResponse.class);
				xstream.alias("card", CamiOfCardItemResponse.class);
				CamiOfResponse camiOfResponse = (CamiOfResponse) xstream.fromXML(result);
				camiContext.setResponse(camiOfResponse);
			}
			doAfter(camiContext);
		} catch (Exception e) {
			logger.error("of-充值卡密出现异常", e);
			camiContext.setThrowable(e);
			doAfterThrowable(camiContext);
		}
		return camiContext;
	}

	/**
	 * 给发送器添加监听器
	 * 
	 * @param onlineListener
	 */
	public void addOnlineListener(CamiListener<ImsCamiLogDetail> camiListener) {
		this.camiListeners.add(camiListener);
	}

	private void doBefore(CamiContext<ImsCamiLogDetail> camiContext) {
		for (CamiListener<ImsCamiLogDetail> camiListener : this.camiListeners) {
			camiListener.updateBefore(camiContext);
		}
	}


	private void doAfter(CamiContext<ImsCamiLogDetail> camiContext) {
		for (CamiListener<ImsCamiLogDetail> camiListener : this.camiListeners) {
			camiListener.updateAfter(camiContext);
		}
	}

	private void doAfterThrowable(CamiContext<ImsCamiLogDetail> camiContext) {
		for (CamiListener<ImsCamiLogDetail> camiListener : this.camiListeners) {
			camiListener.updateAfterThrowable(camiContext);
		}
	}

	/**
	 * 获取签名
	 * 
	 * @return
	 */
	private String getSig(CamiOfReqInfo camiOfReqInfo) {
		String str =camiOfReqInfo.getUserid()
				+ camiOfReqInfo.getUserpws()
				+ camiOfReqInfo.getCardid()
				+ camiOfReqInfo.getCardnum()
				+ camiOfReqInfo.getSporder_id()
				+ camiOfReqInfo.getSporder_time()
				+ this.keyStr;
		return MD5.getMD5Str(str).toUpperCase();
	}

}
