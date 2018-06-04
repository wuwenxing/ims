package com.gwghk.ims.marketingtool.manager.market;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gwghk.ims.common.common.Constants;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.RechargeChannelEnum;
import com.gwghk.ims.common.enums.RechargeStatusEnum;
import com.gwghk.ims.common.vo.marketingtool.ImsRechargeLogDetailVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsRechargeLogDetail;
import com.gwghk.ims.marketingtool.dao.inf.ImsRechargeLogDetailDao;
import com.gwghk.ims.marketingtool.manager.market.online.OnlineContext;
import com.gwghk.ims.marketingtool.manager.market.online.OnlineInfo;
import com.gwghk.ims.marketingtool.manager.market.online.OnlineListener;
import com.gwghk.ims.marketingtool.manager.market.online.OnlineOfServer;
import com.gwghk.ims.marketingtool.util.SmsUtil;

/**
 * 话费接口实现
 * 
 * @author wayne
 */
@Service
public class ImsOnlineManager implements ImsMarkingService{
	private static final Logger logger = LoggerFactory.getLogger(ImsOnlineManager.class);

	@Autowired
	@Qualifier("onlineOfListenerImpl")
	private OnlineListener<ImsRechargeLogDetail> onlineOfListener;

	@Autowired
	private ImsRechargeLogDetailDao imsRechargeLogDetailDao;

	@Override
	public MisRespResult<Map<String, Object>> send(ImsRechargeLogDetailVO vo) {
		MisRespResult<Map<String, Object>> respCode = new MisRespResult<Map<String, Object>>();
		try {
			Map<String, String> extMap = new HashMap<String, String>();
			extMap.put("ext1", vo.getExt1());
			extMap.put("ext2", vo.getExt2());
			extMap.put("ext3", vo.getExt3());
			respCode = doSend(vo.getPhone(), vo.getRechargeSize(), vo.getInterfaceType(), vo.getCreateIp(), extMap, vo.getCompanyId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return respCode;
	}
	
	/**
	 * 具体话费充值 一次只能充值一个号码
	 */
	private MisRespResult<Map<String, Object>> doSend(String mobile, String size, String onlineChannel, String ip,
			Map<String, String> extMap, Long companyId) throws Exception {
		MisRespResult<Map<String, Object>> respCode = new MisRespResult<Map<String, Object>>();
		if (!SmsUtil.checkMobileNum(mobile)) {
			return MisRespResult.error("手机号格式错误");
		}

		OnlineContext<ImsRechargeLogDetail> context = null;
		if (onlineChannel.equals(RechargeChannelEnum.of.getLabelKey())) {
			if (!CompanyEnum.hx.getId().equals(companyId+"")) {
				return MisRespResult.error("欧飞通道目前只有HX使用!");
			}
			OnlineOfServer ofOnlineServer = new OnlineOfServer();
			ofOnlineServer.addOnlineListener(onlineOfListener);
			// 话费实体封装
			String area = MobileQuery.mobileQuery(mobile);// 手机归属地，字符串
			OnlineInfo onlineInfo = new OnlineInfo();
			onlineInfo.setPhones(mobile);
			onlineInfo.setCompanyId(companyId);
			onlineInfo.setSize(size);
			onlineInfo.setIp(ip);
			onlineInfo.setArea(area);
			onlineInfo.setExtMap(extMap);
			context = ofOnlineServer.send(onlineInfo);
		} else {
			return MisRespResult.error("没有设定通道");
		}

		if (null != context && null != context.getObj()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sendStatus", context.getObj().getSendStatus());
			map.put("price", context.getObj().getPrice());
			respCode.setData(map);
		} else {
			return MisRespResult.error("调用第三方充值接口出现异常");
		}
		return respCode;
	}

	/**
	 * 功能：话费回调-欧飞
	 */
	public MisRespResult<Long> callbackFromOF(String retCode, String sporderId, String orderSuccessTime, String errMsg,
			String ip) {
		try {
			if (StringUtils.isBlank(sporderId)) {
				logger.error("-->欧飞话费回调失败,sporderId为空");
				return MisRespResult.error("回调失败,sporderId为空");
			}
			// 返回的sporderId对应主键ID
			Long detailId = null;
			if(sporderId.startsWith(Constants.onlinePrefix)){
				String[] temp = sporderId.split("_");
				if(temp.length == 2){
					detailId = Long.parseLong(temp[2]);
				}else{
					logger.error("-->欧飞话费回调失败,sporderId有误");
					return MisRespResult.error("回调失败,sporderId有误");
				}
			}else{
				// 旧方式无前缀
				detailId = Long.parseLong(sporderId);
			}
			ImsRechargeLogDetail detail = imsRechargeLogDetailDao.get(detailId);
			// 1代表成功，9代表撤消即失败
			// err_msg 失败原因(ret_code为1时，该值为空)
			if ("1".equals(retCode)) {
				logger.error("-->欧飞话费回调成功,充值成功,sporderId={}", sporderId);
				detail.setSendStatus(RechargeStatusEnum.sendSuccess.getLabelKey());
			} else {
				logger.error("-->欧飞话费回调成功,但充值失败,sporderId={}, errMsg={}", sporderId, errMsg);
				detail.setSendStatus(RechargeStatusEnum.sendFail.getLabelKey());
				detail.setRemark(errMsg);
			}
			detail.setUpdateDate(new Date());
			detail.setUpdateIp(ip);
			imsRechargeLogDetailDao.update(detail);
			return MisRespResult.success(detailId);
		} catch (Exception e) {
			logger.error("-->欧飞话费Callback" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}