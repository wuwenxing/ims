package com.gwghk.ims.marketingtool.manager.market.online;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.common.common.Constants;
import com.gwghk.ims.common.enums.CommitStatusEnum;
import com.gwghk.ims.common.enums.RechargeChannelEnum;
import com.gwghk.ims.common.enums.RechargeStatusEnum;
import com.gwghk.ims.common.enums.RechargeTypeEnum;
import com.gwghk.ims.marketingtool.dao.entity.ImsRechargeLogDetail;
import com.gwghk.ims.marketingtool.dao.inf.ImsRechargeLogDetailDao;

/**
 * service层公共方法
 * 
 * @author wayne
 */
@Service
public class OnlineOfListenerImpl implements OnlineListener<ImsRechargeLogDetail> {

	private static final Logger logger = LoggerFactory.getLogger(OnlineOfListenerImpl.class);
	
	@Autowired
	private ImsRechargeLogDetailDao imsRechargeLogDetailDao;

	@Override
	public void updateBefore(OnlineContext<ImsRechargeLogDetail> onlineContext) {
		try {
			OnlineInfo onlineInfo = onlineContext.getOnlineInfo();
			String phones = onlineInfo.getPhones();
			logger.info("of-updateBefore[phones=" + phones + "]");

			ImsRechargeLogDetail detailVo = new ImsRechargeLogDetail();
			detailVo.setRechargeType(RechargeTypeEnum.online.getCode());
			detailVo.setPhone(phones);
			detailVo.setCommitStatus(CommitStatusEnum.commitReady.getLabelKey());// 提交状态
			detailVo.setSendStatus(RechargeStatusEnum.sendFail.getLabelKey());// 充值失败
			detailVo.setInterfaceType(RechargeChannelEnum.of.getLabelKey());
			detailVo.setRechargeSize(onlineInfo.getSize());
			detailVo.setPhoneArea(onlineInfo.getArea());
			detailVo.setPrice(null);
			detailVo.setExt1(onlineInfo.getSize());
			detailVo.setExt2(null == onlineInfo.getExtMap()?null:onlineInfo.getExtMap().get("ext2"));
			detailVo.setExt3(null == onlineInfo.getExtMap()?null:onlineInfo.getExtMap().get("ext3"));
			detailVo.setResBatchNo("");
			detailVo.setResCode("");
			detailVo.setCreateIp(onlineContext.getOnlineInfo().getIp());
			detailVo.setCreateDate(new Date());
			detailVo.setUpdateIp(onlineContext.getOnlineInfo().getIp());
			detailVo.setUpdateDate(new Date());
			detailVo.setDeleteFlag("N");
			detailVo.setEnableFlag("Y");
			detailVo.setVersionNo(1);
			detailVo.setCompanyId(onlineContext.getOnlineInfo().getCompanyId());
			imsRechargeLogDetailDao.save(detailVo);
			onlineContext.setObj(detailVo);
			onlineInfo.setCustomId(Constants.onlinePrefix  + onlineContext.getOnlineInfo().getCompanyId() + "_" + detailVo.getDetailId());//保证唯一即可
		} catch (Exception e) {
			logger.error("发送出现异常:" + e.getMessage(), e);
		}
	}

	@Override
	public void updateAfter(OnlineContext<ImsRechargeLogDetail> onlineContext) {
		try {
			OnlineInfo onlineInfo = onlineContext.getOnlineInfo();
			String phones = onlineInfo.getPhones();
			logger.info("of-updateAfter[phones=" + phones + "]");

			ImsRechargeLogDetail detailVo = onlineContext.getObj();
			OnlineOfResponse response = (OnlineOfResponse) onlineContext.getResponse();
			if (null != response) {
				logger.info("of-返回retcode={}, gameState={}", response.getRetcode(), response.getGame_state());
				// 1代表请求成功
				if ("1".equals(response.getRetcode())) {
					// 请求成功
					// 更新
					//如果成功将为1，澈消(充值失败)为9，充值中为0,只能当状态为9时，商户才可以退款给用户。
					if ("0".equals(response.getGame_state())) {
						detailVo.setSendStatus(RechargeStatusEnum.sendIn.getLabelKey());// 充值中
					} else if ("1".equals(response.getGame_state())) {
						detailVo.setSendStatus(RechargeStatusEnum.sendSuccess.getLabelKey());// 充值成功
					} else if ("9".equals(response.getGame_state())) {
						detailVo.setSendStatus(RechargeStatusEnum.sendFail.getLabelKey());// 充值失败
					} else {
						detailVo.setSendStatus(RechargeStatusEnum.sendFail.getLabelKey());// 充值失败
					}
					if(null != response.getOrdercash()){
						detailVo.setPrice(Double.parseDouble(response.getOrdercash()));
					}
					detailVo.setResCode(response.getRetcode() + "-" + response.getOrderid() + "-" + response.getCardid() + "-" + response.getOrdercash());
					detailVo.setResBatchNo(response.getSporder_id());
					detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 提交状态
					imsRechargeLogDetailDao.update(detailVo);
				} else {
					// 请求失败
					detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 提交状态
					detailVo.setSendStatus(RechargeStatusEnum.sendFail.getLabelKey());// 充值失败
					detailVo.setResBatchNo(response.getSporder_id());
					detailVo.setResCode(response.getRetcode() + "-" + response.getErr_msg());
					imsRechargeLogDetailDao.update(detailVo);
				}
			} else {
				logger.error("of-请求异常response=null");
				detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 提交状态
				detailVo.setSendStatus(RechargeStatusEnum.sendFail.getLabelKey());// 充值失败
				imsRechargeLogDetailDao.update(detailVo);
			}
		} catch (Exception e) {
			logger.error("发送出现异常:" + e.getMessage(), e);
		}
	}

	@Override
	public void updateAfterThrowable(OnlineContext<ImsRechargeLogDetail> onlineContext) {
		try {
			OnlineInfo onlineInfo = onlineContext.getOnlineInfo();
			String phones = onlineInfo.getPhones();
			logger.info("of-updateAfterThrowable[phones=" + phones + "]");
			ImsRechargeLogDetail detailVo = onlineContext.getObj();
			detailVo.setCommitStatus(CommitStatusEnum.commitFail.getLabelKey());// 提交状态
			detailVo.setSendStatus(RechargeStatusEnum.sendFail.getLabelKey());// 充值失败
			imsRechargeLogDetailDao.update(detailVo);
		} catch (Exception e) {
			logger.error("发送出现异常:" + e.getMessage(), e);
		}
	}

}
