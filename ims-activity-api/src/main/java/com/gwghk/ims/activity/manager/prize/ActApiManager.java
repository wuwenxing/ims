package com.gwghk.ims.activity.manager.prize;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.dao.entity.ActThirdCallRecord;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.activity.dao.inf.ActThirdCallRecordDao;
import com.gwghk.ims.common.dto.activity.BonusReqDTO;
import com.gwghk.ims.common.enums.ActThirdCallEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

public abstract class ActApiManager {

	private static Logger logger = LoggerFactory.getLogger(ActApiManager.class);
	@Autowired
	private ActThirdCallRecordDao actThirdCallRecordDao;

	/**
	 * 
	 * @MethodName: demoCashAdjust
	 * @Description: 调用第三方的模拟账号金额调整接口
	 * @param actCustomerInfo
	 * @param payAmount
	 * @return void
	 */
	public abstract void demoCashAdjust(ActCustomerInfo actCustomerInfo, BigDecimal payAmount);

	/**
	 * 
	 * @MethodName: getDemoCustomerAvailabelCredit
	 * @Description: 获取第三方模拟账号的账户金额
	 * @param actCustomerInfo
	 * @return void
	 */
	public abstract BigDecimal getDemoCustomerAvailabelCredit(ActCustomerInfo actCustomerInfo);

	/**
	 * 
	 * @MethodName: realAddBonus
	 * @Description: 向第三方请求发放贈金
	 * @param actPrizeRecord
	 * @return
	 * @return String
	 */
	public abstract String realAddBonus(VActPrizeRecord actPrizeRecord);

	/**
	 * 
	 * @MethodName: realReleaseBonus
	 * @Description: 更新贈金可取额度
	 * @param actPrizeRecord
	 * @return
	 * @return String
	 */
	public abstract String realReleaseBonus(VActPrizeRecord actPrizeRecord);

	/**
	 * 
	 * @MethodName: realReleaseBonus
	 * @Description: 更新贈金可取额度
	 * @param actPrizeRecord
	 * @param releasedAmount
	 * @return
	 * @return String
	 */
	public abstract String realReleaseBonus(VActPrizeRecord actPrizeRecord, BigDecimal releasedAmount);

	/**
	 * 
	 * @MethodName: realCancelBonus
	 * @Description: 批量取消已经发放的贈金
	 * @param listActPrizeRecord
	 * @param withdraw
	 * @return
	 * @return Map<String,Map<String,String>>
	 */
	public abstract Map<String, Map<String, String>> realCancelBonus(List<VActPrizeRecord> listActPrizeRecord,
			String cancelReason);

	/**
	 * 
	 * @MethodName: addBonus
	 * @Description: 加贈金
	 * @param bonusReqDTO
	 * @return
	 * @return String
	 */
	public abstract String addBonus(BonusReqDTO bonusReqDto);
	
	/**
	 * 
	 * @MethodName: addBonus
	 * @Description: 加贈金
	 * @param bonusReqDTO
	 * @param saveThird 是否保存第三方调用记录
	 * @return
	 * @return String
	 */
	public abstract String addBonus(BonusReqDTO bonusReqDto,boolean saveThird);

	/**
	 * 
	 * @MethodName: releaseBonus
	 * @Description: 释放贈金
	 * @param bonusReqDTO
	 * @return
	 * @return String
	 */
	public abstract String releaseBonus(BonusReqDTO bonusReqDto);
	
	/**
	 * 
	 * @MethodName: releaseBonus
	 * @Description: 释放贈金
	 * @param bonusReqDTO
	 * @param saveThird 是否保存第三方调用记录
	 * @return
	 * @return String
	 */
	public abstract String releaseBonus(BonusReqDTO bonusReqDto,boolean saveThird);
	/**
	 * 
	 * @MethodName: cancelBonus
	 * @Description: 取消贈金
	 * @param listBonusReqDto
	 * @return
	 * @return Map<String,Map<String,String>>
	 */
	public abstract  Map<String, Map<String, String>> cancelBonus(List<BonusReqDTO> listBonusReqDto);
	
	/**
	 * 
	 * @MethodName: cancelBonus
	 * @Description: 取消贈金
	 * @param listBonusReqDto
	 * @param saveThird 是否保存第三方调用记录
	 * @return
	 * @return Map<String,Map<String,String>>
	 */
	public abstract  Map<String, Map<String, String>> cancelBonus(List<BonusReqDTO> listBonusReqDto,boolean saveThird);
	
	protected void saveThirdCallRecord(BonusReqDTO bonusReqDto, String dealNumber, ActThirdCallEnum actThirdCallEnum) {
		try {
			String companyCode = CompanyEnum.getCodeById(bonusReqDto.getCompanyId());
			ActThirdCallRecord actThirdCallRecord = new ActThirdCallRecord();
			actThirdCallRecord.setType(actThirdCallEnum.getType());
			actThirdCallRecord.setCode(actThirdCallEnum.getCode());
			actThirdCallRecord.setActivityPeriods(bonusReqDto.getActivityPeriods());
			actThirdCallRecord.setCompanyCode(companyCode);
			actThirdCallRecord.setCompanyId(bonusReqDto.getCompanyId());
			actThirdCallRecord.setPlatform(bonusReqDto.getPlatform());
			actThirdCallRecord.setAccountNo(bonusReqDto.getAccountNo());
			actThirdCallRecord.setParentRecordNo(bonusReqDto.getRecordNumber());
			actThirdCallRecord.setRecordNo(bonusReqDto.getRefId());
			actThirdCallRecord.setThirdRecordNo(dealNumber);
			actThirdCallRecord.setThirdDealResult(StringUtil.isNotEmpty(dealNumber) ? "success" : "failure");
			actThirdCallRecord.setCreateDate(new Date());
			actThirdCallRecord.setUpdateDate(new Date());
			actThirdCallRecord.setTryCount(1);
			actThirdCallRecord.setSource(bonusReqDto.getSource());
			actThirdCallRecord.setData(JsonUtil.obj2Str(bonusReqDto));
			actThirdCallRecordDao.save(actThirdCallRecord);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	protected void saveThirdCallRecord(List<BonusReqDTO> listBonusReqDto, Map<String, String> execResult,
			ActThirdCallEnum actThirdCallEnum) {
		try {
			for (BonusReqDTO bonusReqDto : listBonusReqDto) {
				String companyCode = CompanyEnum.getCodeById(bonusReqDto.getCompanyId());
				ActThirdCallRecord actThirdCallRecord = new ActThirdCallRecord();
				actThirdCallRecord.setType(actThirdCallEnum.getType());
				actThirdCallRecord.setCode(actThirdCallEnum.getCode());
				actThirdCallRecord.setActivityPeriods(bonusReqDto.getActivityPeriods());
				actThirdCallRecord.setCompanyCode(companyCode);
				actThirdCallRecord.setCompanyId(bonusReqDto.getCompanyId());
				actThirdCallRecord.setPlatform(bonusReqDto.getPlatform());
				actThirdCallRecord.setAccountNo(bonusReqDto.getAccountNo());
				actThirdCallRecord.setParentRecordNo(bonusReqDto.getRecordNumber());
				actThirdCallRecord.setRecordNo(bonusReqDto.getRefId());
				if(execResult!=null && execResult.get(bonusReqDto.getBonusPno()) != null){
					actThirdCallRecord.setThirdRecordNo(execResult.get(bonusReqDto.getBonusPno()));
					actThirdCallRecord.setThirdDealResult("success");
				}else{
					actThirdCallRecord.setThirdDealResult("failure");
				}
				actThirdCallRecord.setCreateDate(new Date());
				actThirdCallRecord.setUpdateDate(new Date());
				actThirdCallRecord.setTryCount(1);
				actThirdCallRecord.setSource(bonusReqDto.getSource());
				actThirdCallRecord.setData(JsonUtil.obj2Str(bonusReqDto));
				actThirdCallRecordDao.save(actThirdCallRecord);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
