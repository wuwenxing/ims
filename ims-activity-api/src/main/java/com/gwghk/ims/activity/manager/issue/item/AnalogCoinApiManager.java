package com.gwghk.ims.activity.manager.issue.item;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.dao.entity.ActPrizeRecordExt;
import com.gwghk.ims.activity.dao.entity.ActRelatedCustomer;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.activity.dao.inf.ImsPrizeRecordExtDao;
import com.gwghk.ims.activity.manager.ActDemoCashAdjustManager;
import com.gwghk.ims.activity.manager.ActRelatedCustomerManager;
import com.gwghk.ims.activity.manager.ImsPrizeRecordManager;
import com.gwghk.ims.activity.manager.issue.AbstractIssueItems;
import com.gwghk.ims.common.annotation.IssueItemCategory;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.common.ApiResultCode;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActPlatformEnum;
import com.gwghk.ims.common.enums.IssueItemCategoryEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;

/**
 * 模拟币接口
 * @author jackson.tang
 *
 */
@Component
@IssueItemCategory({IssueItemCategoryEnum.ANALOGCOIN})
public class AnalogCoinApiManager extends AbstractIssueItems{
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ActDemoCashAdjustManager actDemoCashAdjustManager;
	@Autowired
	private ActRelatedCustomerManager actRelatedCustomerManager;
	@Autowired
	private ImsPrizeRecordManager imsPrizeRecordManager;
	@Autowired
	private ImsPrizeRecordExtDao imsPrizeRecordExtDao;
	
	/**
	 * 根据发放记录调整模拟币
	 * @param vActPrizeRecord
	 * @param env
	 * @return
	 */
	public boolean addDemoCashAdjust(VActPrizeRecord vActPrizeRecord,String env) {
		if(!ActItemTypeEnum.ANALOGCOIN.getValue().equals(vActPrizeRecord.getItemType()))
			return false;
		
		//找出用户关联账号
		ActRelatedCustomer relatedCustomerInfo=actRelatedCustomerManager.findByCustomerNumber(vActPrizeRecord.getAccountNo(),env);
		
		String demoAccount=null;
		if(ActEnvEnum.DEMO.getValue().equals(env))
			demoAccount=vActPrizeRecord.getAccountNo();
		//可能真实账号有绑定过模拟账号
		else if(relatedCustomerInfo!=null && relatedCustomerInfo.getDemoCustomerNumber()!=null )
			demoAccount=relatedCustomerInfo.getDemoCustomerNumber();
		
		//必须拥有模拟账号才能发放
		if(demoAccount==null)
			return false;
		
		boolean isSuccess=false;
		ActCustomerInfo dto = new ActCustomerInfo();
		dto.setAccountNo(vActPrizeRecord.getAccountNo());

		dto.setAccountEnv(ActEnvEnum.DEMO.getValue());
		dto.setPlatform(ActPlatformEnum.GTS2.getCode());
		dto.setCompanyId(vActPrizeRecord.getCompanyId());
		ApiRespResult<String> result = actDemoCashAdjustManager.addDemoCashAdjust(dto, vActPrizeRecord.getItemAmount());
		
		JSONObject otherMsgJson = null;
		if(StringUtils.isNotBlank(vActPrizeRecord.getOtherMsg())){
			otherMsgJson = JSONObject.parseObject(vActPrizeRecord.getOtherMsg());
		}else{
			otherMsgJson = new JSONObject();
		}
		
		if (result.isOk() || ApiResultCode.DEMOAMOUNT_REDO.getCode().equals(result.getCode())) {
			if(ApiResultCode.DEMOAMOUNT_REDO.getCode().equals(result.getCode())){
				otherMsgJson.put("sendDemoAmountRemark", "增加"+result.getMsg());
				vActPrizeRecord.setOtherMsg(otherMsgJson.toString());
			}
			// 添加模拟金额成功-更新发放状态
			vActPrizeRecord.setGivedStatus(IssuingStatusEnum.issue_success.getValue());
			
			// 更新返回流水号
			ActPrizeRecordExt recordExt = new ActPrizeRecordExt();
			recordExt.setRecordNumber(vActPrizeRecord.getRecordNo());
			recordExt.setDealNumber(result.getData());
			recordExt.setCompanyId(vActPrizeRecord.getCompanyId());
			imsPrizeRecordExtDao.updateByRecordNumber(recordExt);
			
			isSuccess=true;
		}else{
			// 添加模拟金额失败
			otherMsgJson.put("sendDemoAmountRemark", "增加模拟金额失败："+result.getMsg());
			vActPrizeRecord.setOtherMsg(otherMsgJson.toString());
			vActPrizeRecord.setGivedStatus(IssuingStatusEnum.issue_fail.getValue());
		}
		
		//更新发放记录状态
		imsPrizeRecordManager.saveOrUpdate(ImsBeanUtil.copyNotNull(new ImsPrizeRecord(),vActPrizeRecord));
		
		return isSuccess;
	}
	
	/**
	 * 调整金额（支持负数,表示扣除数额）
	 * @param accountNo
	 * @param amount
	 * @param companyId
	 * @return
	 */
	public boolean addDemoCashAdjust(String accountNo,BigDecimal amount, Long companyId) {
		ActCustomerInfo dto = new ActCustomerInfo();
		dto.setAccountNo(accountNo);
		dto.setAccountEnv(ActEnvEnum.DEMO.getValue());
		dto.setPlatform(ActPlatformEnum.GTS2.getCode());
		dto.setCompanyId(companyId);
		ApiRespResult<String> result = actDemoCashAdjustManager.addDemoCashAdjust(dto, amount);
	
		return result.isOk();
	}
	
	/**
	 * 查询指定账号可用模拟币
	 * @param accountNo
	 * @param companyId
	 * @return
	 */
	public BigDecimal findAvailabelCredit(String accountNo,Long companyId) {
		ActCustomerInfo dto = new ActCustomerInfo();
		dto.setAccountNo(accountNo);
		dto.setAccountEnv(ActEnvEnum.DEMO.getValue());
		dto.setPlatform(ActPlatformEnum.GTS2.getCode());
		dto.setCompanyId(companyId);
		return actDemoCashAdjustManager.getCustomerAvailabelCredit(dto);
	}

	@Override
	public Boolean send(String record, String itemType, String actNo, String accountNo, Long companyId) {
		VActPrizeRecord vActPrizeRecord=imsPrizeRecordManager.findVByRecordNo(record, actNo);
		if(vActPrizeRecord==null) {
			logger.error("无法找到发放记录【{}】",record);
			return false;
		}
		
		return addDemoCashAdjust(vActPrizeRecord, vActPrizeRecord.getEnv());
	}

	@Override
	public Boolean isFinishDeduct(ImsPrizeRecordVO porizeRecord, String accountNo, Long companyId) {
		return false;
	}

}
