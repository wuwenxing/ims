package com.gwghk.ims.activity.manager.issue.item;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.activity.manager.ImsPrizeRecordManager;
import com.gwghk.ims.activity.manager.issue.AbstractIssueItems;
import com.gwghk.ims.common.annotation.IssueItemCategory;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.IssueItemCategoryEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.enums.RechargeChannelEnum;
import com.gwghk.ims.common.enums.RechargeStatusEnum;
import com.gwghk.ims.common.enums.TableEnum;
import com.gwghk.ims.common.inf.external.marketingtool.CamiDubboService;
import com.gwghk.ims.common.util.BigDecimalUtil;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;
import com.gwghk.unify.framework.common.util.StringUtil;

/**  
* 摘要: 串码物品
* @author George.li  
* @date 2018年5月30日  
* @version 1.0  
*/
@Component
@IssueItemCategory({IssueItemCategoryEnum.STRINGCODE})
public class CamiManager extends AbstractIssueItems{
 
Logger logger = LoggerFactory.getLogger(MobileDataItemManger.class);
	
	@Autowired
	private CamiDubboService camiDubboService;
	
	@Autowired
	private ImsPrizeRecordManager imsPrizeRecordManager;
	
	@Override
	public Boolean send(String recordNo,String itemType,String actNo,String accountNo,Long companyId) {
		
		logger.info("send-->卡密充值 【RechargeType:{},actNo:{},companyId:{}】", itemType, actNo, companyId);
		
		ImsPrizeRecord pr=imsPrizeRecordManager.findByRecordNo(recordNo, actNo);
		try {
		String channel = "";
		
		if(CompanyEnum.fx.getLongId() == companyId){
			// TODO
		}else if(CompanyEnum.hx.getLongId() == companyId){
			channel = RechargeChannelEnum.of.getLabelKey();
		}else{
			// TODO
			return true;
		}
		
		if(StringUtil.isNullOrEmpty(pr.getCustMobile())){ //手机号为空，直接返回发放失败，并备注对应的信息
			logger.info("send-->手机号为空，话费发放失败!【RechargeType:{},actNo:{},companyId:{}】", itemType, actNo, companyId);
			pr.setGivedStatus(IssuingStatusEnum.issue_fail.getValue());
			ImsPrizeRecord ar = new ImsPrizeRecord();
			logger.info("send-->卡密  发放记录不存在！请查看物品是否有效或数量是否足够！【RechargeType:{},actNo:{},companyId:{}】", itemType, actNo, companyId);
			ImsBeanUtil.copyNotNull(ar, pr);
			ar.setRemark("充值失败,手机号为空");
			ar.setUpdateDate(new Date());
			imsPrizeRecordManager.saveOrUpdate(ar); //更新发放失败信息
			return false;
		}
		
		Map<String, String> extMap = new HashMap<String, String>();
		extMap.put("ext2", TableEnum.act_prize_record_.getCode(companyId));
		extMap.put("ext3", pr.getId()+"");
		String size = null;
	
		if(pr.getItemAmount()!=null){
			size = String.valueOf(pr.getItemAmount());
		}
		
		String remark = null;
					
		ApiRespResult<Map<String, Object>> result = camiDubboService.send(pr.getCustMobile(),  pr.getRecordNo(),  pr.getItemName(), channel, "127.0.0.1", extMap, companyId);
		String resBatchNo = null;
		//if(null != result && result.isOk()){
		if(null != result && result.getCode().equals("OK")){
			Map<String, Object> resultMap = result.getData(); 
			if(null != resultMap){
				if(null != resultMap.get("price")){
					if(StringUtil.isNotBlank(resultMap.get("price")+"")){
						BigDecimal price = BigDecimalUtil.convertDownFromDouble2(resultMap.get("price")+"");
						pr.setItemPrice(price);
						pr.setItemPriceUnit("元");
					}
				}
				if(RechargeStatusEnum.sendSuccess.getLabelKey().equals(resultMap.get("sendStatus"))){
					pr.setGivedStatus(IssuingStatusEnum.issue_success.getValue());
				}else if(RechargeStatusEnum.sendIn.getLabelKey().equals(resultMap.get("sendStatus"))){
					pr.setGivedStatus(IssuingStatusEnum.in_distributed.getValue());
				}else if(RechargeStatusEnum.sendFail.getLabelKey().equals(resultMap.get("sendStatus"))){
					pr.setGivedStatus(IssuingStatusEnum.issue_fail.getValue());
				}
				if(resultMap.get("resBatchNo")!=null){
					resBatchNo = resultMap.get("resBatchNo").toString();
				}
			}else{
				pr.setGivedStatus(IssuingStatusEnum.issue_fail.getValue());
				remark = null == result ? "":result.getMsg();
			}
		}else{
			logger.info("send-->调用第三方接口，话费发放失败!【RechargeType:{},actNo:{},companyId:{},msg:{}】", itemType, actNo, companyId,result.getMsg());
			pr.setGivedStatus(IssuingStatusEnum.issue_fail.getValue());
			remark = null == result ? "":result.getMsg();
		}
		ImsPrizeRecord ar = new ImsPrizeRecord();
		ImsBeanUtil.copyNotNull(ar, pr);
		ar.setRemark(remark);
		ar.setUpdateDate(new Date());
		imsPrizeRecordManager.saveOrUpdate(ar);//更新发放记录
	 
		if(resBatchNo!=null){
			//更新拓展表信息
//			ImsPrizeRecordExt actPrizeRecordExt = new ImsPrizeRecordExt();
//			actPrizeRecordExt.setCompanyCode(ActCompany.getCodeByKey(companyId));
//			actPrizeRecordExt.setRecordNumber(pr.getRecordNumber());
//			actPrizeRecordExt.setDealNumber(resBatchNo);
//			imsPrizeRecordExtManager.updateByRecordNumber(actPrizeRecordExt);
		}
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			return false;
		}
		return true;
	}

	 
	@Override
	public Boolean isFinishDeduct(ImsPrizeRecordVO porizeRecord, String accountNo, Long companyId) {
		// TODO Auto-generated method stub
		return null;
	}
	 
}
