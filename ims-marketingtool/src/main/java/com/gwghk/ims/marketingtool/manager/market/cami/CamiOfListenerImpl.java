package com.gwghk.ims.marketingtool.manager.market.cami;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.common.enums.CommitStatusEnum;
import com.gwghk.ims.common.enums.RechargeChannelEnum;
import com.gwghk.ims.common.enums.RechargeStatusEnum;
import com.gwghk.ims.marketingtool.dao.entity.ImsCamiLogDetail;
import com.gwghk.ims.marketingtool.manager.market.ImsCamiLogDetailMapper;

/**
 * 
* @ClassName: CamiOfListenerImpl
* @Description: 卡密-欧飞处理发放请求前后处理逻辑
* @author lawrence
* @date 2018年5月7日
*
 */
@Service
public class CamiOfListenerImpl implements CamiListener<ImsCamiLogDetail> {

	private static final Logger logger = LoggerFactory.getLogger(CamiOfListenerImpl.class);

	@Autowired
	private ImsCamiLogDetailMapper camiLogDetailMapper;

	@Override
	public void updateBefore(CamiContext<ImsCamiLogDetail> onlineContext) {
		try {
			CamiInfo camiInfo = onlineContext.getCamiInfo();
			String phones = camiInfo.getPhones();

			ImsCamiLogDetail detailVo = new ImsCamiLogDetail();
			detailVo.setPhone(phones);
			detailVo.setCommitStatus(CommitStatusEnum.commitReady.getLabelKey());// 提交状态
			detailVo.setSendStatus(RechargeStatusEnum.sendFail.getLabelKey());// 充值失败
			detailVo.setInterfaceType(RechargeChannelEnum.of.getLabelKey());
			detailVo.setCamiName(camiInfo.getCamiName());
			detailVo.setResCode("");
			detailVo.setCreateIp(camiInfo.getIp());
			detailVo.setCreateDate(new Date());
			detailVo.setUpdateIp(camiInfo.getIp());
			detailVo.setUpdateDate(new Date());
			detailVo.setVersionNo(1);
			detailVo.setCompanyId(camiInfo.getCompanyId());
			detailVo.setOrderNo(camiInfo.getOrderNo());
			camiLogDetailMapper.saveOrUpdate(detailVo);
			onlineContext.setObj(detailVo);
		} catch (Exception e) {
			logger.error("卡密信息记录保存出现异常:" + e.getMessage(), e);
		}
	}

	@Override
	public void updateAfter(CamiContext<ImsCamiLogDetail> camiContext) {
		try {
			CamiInfo camiInfo = camiContext.getCamiInfo();

			ImsCamiLogDetail detailVo = camiContext.getObj();
			CamiOfResponse response = (CamiOfResponse) camiContext.getResponse();
			if (null != response) {
				logger.info("of卡密信息-返回retcode={}, err_msg={}", response.getRetcode(), response.getErr_msg());
				// 1代表请求成功
				if ("1".equals(response.getRetcode())) {
					// 请求成功
					// 更新
					//如果成功将为1，澈消(充值失败)为9，充值中为0,只能当状态为9时，商户才可以退款给用户。
					detailVo.setSendStatus(RechargeStatusEnum.sendSuccess.getLabelKey());// 充值成功
					if(response.getOrdercash()!=null){
						detailVo.setPrice(new BigDecimal(response.getOrdercash()));
					}
					detailVo.setResCode(response.getRetcode() + "-" + response.getOrderid() + "-" + response.getCardid() + "-" + response.getOrdercash());
					detailVo.setResBatchNo(response.getOrderid());
					detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 提交状态
					List<CamiOfCardItemResponse> cardList = response.getCards();
					CamiOfCardItemResponse cardItem =  null;
					if(cardList!=null && !cardList.isEmpty()){
						cardItem = cardList.get(0);
						detailVo.setRemark("卡号:"+cardItem.getCardno()+";密码:"+cardItem.getCardpws()+";过期时间:"+cardItem.getExpiretime());
					}
					camiLogDetailMapper.saveOrUpdate(detailVo);
//					if(cardItem!=null){
//						Map<String,Object> tplArgs = new HashMap<String,Object>();
//						tplArgs.put("itemName", camiInfo.getCamiName());
//						tplArgs.put("cardNo", cardItem.getCardno());
//						tplArgs.put("pwd", cardItem.getCardpws());
//						tplArgs.put("exp", cardItem.getExpiretime());
//						SmsDTO smsDTO = new SmsDTO();
//						smsDTO.setCompanyId(camiInfo.getCompanyId().toString());
//						smsDTO.setTplCode("jdecard001");
//						smsDTO.setTplArgs(tplArgs);
//						smsDTO.setMobile(camiInfo.getPhones());
//						smsDubboService.sendSms(smsDTO);
//					}
					
				} else {
					// 请求失败
					detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 提交状态
					detailVo.setSendStatus(RechargeStatusEnum.sendFail.getLabelKey());// 充值失败
					detailVo.setResBatchNo(response.getSporder_id());
					detailVo.setResCode(response.getRetcode() + "-" + response.getErr_msg());
					camiLogDetailMapper.saveOrUpdate(detailVo);
				}
			} else {
				logger.error("of-卡密请求异常,订单号{},response=null",camiInfo.getOrderNo());
				detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 提交状态
				detailVo.setSendStatus(RechargeStatusEnum.sendFail.getLabelKey());// 充值失败
				camiLogDetailMapper.saveOrUpdate(detailVo);
			}
		} catch (Exception e) {
			logger.error("卡密信息发送出现异常:" + e.getMessage(), e);
		}
	}

	@Override
	public void updateAfterThrowable(CamiContext<ImsCamiLogDetail> camiContext) {
		try {
			ImsCamiLogDetail detailVo = camiContext.getObj();
			detailVo.setCommitStatus(CommitStatusEnum.commitFail.getLabelKey());// 提交状态
			detailVo.setSendStatus(RechargeStatusEnum.sendFail.getLabelKey());// 充值失败
			camiLogDetailMapper.saveOrUpdate(detailVo);
		} catch (Exception e) {
			logger.error("卡密信息保存出现异常:" + e.getMessage(), e);
		}
	}

}
