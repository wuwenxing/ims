package com.gwghk.ims.activity.service.mis;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ActItemsSetting;
import com.gwghk.ims.activity.dao.entity.ActStringCode;
import com.gwghk.ims.activity.manager.ActItemsSettingManager;
import com.gwghk.ims.activity.manager.ActStringCodeManager;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActItemCategoryEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActItemsSettingDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.ImsDateUtil;
import com.gwghk.ims.common.vo.activity.ActItemsSettingVO;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：物品设置实现
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年3月28日
 */
@Service
public class MisActItemsSettingDubboServiceImpl implements MisActItemsSettingDubboService {
	private static final Logger logger = LoggerFactory.getLogger(MisActItemsSettingDubboServiceImpl.class);
 
	@Autowired
	private ActItemsSettingManager actItemsSettingManager;
	@Autowired
	private ActStringCodeManager actStringCodeManager;
	
	
 
	@Override
	public MisRespResult<PageR<ActItemsSettingVO>> findPageList(ActItemsSettingVO vo,Long companyId) {
		logger.info("findPageList-->【params:{}】", vo.toString());
		try {
			PageR<ActItemsSettingVO> pageR = actItemsSettingManager.findPageList(vo);
			return MisRespResult.success(pageR);
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	
	@Override
	public MisRespResult<List<ActItemsSettingVO>> findList(ActItemsSettingVO vo,Long companyId) {
		logger.info("findList-->【params:{}】", vo.toString());
		try {
			List<ActItemsSettingVO> list = actItemsSettingManager.findList(vo);
			return MisRespResult.success(list);
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	public MisRespResult<ActItemsSettingVO> findByItemNumber(String itemNumber,Boolean isloadActUseFlag, Long companyId) {
		logger.info("findByItemNumber-->【itemNumber:{}】", itemNumber);
		try {
			ActItemsSetting actItemsSetting = actItemsSettingManager.findByItemNumber(itemNumber);
			ActItemsSettingVO vo = ImsBeanUtil.copyNotNull(new ActItemsSettingVO(), actItemsSetting);
			if(isloadActUseFlag!=null && isloadActUseFlag){
				vo.setActUseFlag(actItemsSettingManager.isActivityInUse(actItemsSetting.getItemNumber()));
			}
			return MisRespResult.success(vo);
		} catch (Exception e) {
			logger.error("<--系统异常,【findByItemNumber】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<List<ActItemsSettingVO>> findByItemName(String itemName,Long companyId) {
		logger.info("findByItemName-->【itemName:{}】", itemName);
		try {
			ActItemsSettingVO dto = new ActItemsSettingVO();
			dto.setItemName(itemName);
			List<ActItemsSettingVO> list = actItemsSettingManager.findList(dto);
			return MisRespResult.success(list);
		} catch (Exception e) {
			logger.error("<--系统异常,【findByItemName】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	
	
	 
	@Override
	public MisRespResult<ActItemsSettingVO> findById(Long id,Long companyId) {
		logger.info("findById-->【id:{}】", id);
		try {
			return MisRespResult
					.success(ImsBeanUtil.copyNotNull(new ActItemsSettingVO(), actItemsSettingManager.findById(id)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 获得系统默认的物品编号
	 * @param idArray
	 * @param companyId
	 * @return
	 */
	@Override
	public MisRespResult<String> getNewItemNumber(Long companyId){
		String companyCode = CompanyEnum.getCodeById(companyId) ;
		String newItemNumber = (StringUtil.isNotEmpty(companyCode)?companyCode:"") + "_" + ImsDateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss");
		return MisRespResult.success(newItemNumber);
	}
	
	@Override
	public MisRespResult<Void> saveOrUpdate(ActItemsSettingVO vo,Long companyId) {
		logger.info("saveOrUpdate-->【params:{}】",vo.toString());
		try {
			//参数默认值设置
			if (ActItemTypeEnum.VIRTUAL.getValue().equals(vo.getItemType())) {
	            if ("mobile_charge".equals(vo.getItemCategory())) {
	            	vo.setItemUnit("RMB");
	            } else if ("member_vip".equals(vo.getItemCategory())) {
	            	vo.setItemUnit("Month");
	            }
	        }else if(ActItemTypeEnum.ANALOGCOIN.getValue().equals(vo.getItemType())
	            ||ActItemTypeEnum.TOKENCOIN.getValue().equals(vo.getItemType())
	            ||ActItemTypeEnum.WITHGOLD.getValue().equals(vo.getItemType())){
	        	vo.setItemUnit("USD");
	        } else if (ActItemTypeEnum.REAL.getValue().equals(vo.getItemType())) {
	        	vo.setItemUnit("RMB");
	        }
			vo.setItemUsableAmount(vo.getItemStockAmount());
			return actItemsSettingManager.saveOrUpdate(vo);
		} catch (Exception e) {
			logger.error("<--系统异常，【saveOrUpdate】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> deleteByIdArray(String idArray,Long companyId) {
		logger.info("deleteByIdArray-->【idArray:{}】", idArray);
		try {
			 MisRespResult<Void> result  = new  MisRespResult<Void>();
			String[] idsArray = idArray.split(",");
			for (String idStr : idsArray) {
				// 物品对象
				ActItemsSetting actItem = actItemsSettingManager.findById(Long.parseLong(idStr));
				if (actItem != null) {
					//step1:判断虚拟物品其下存在串码不能删除
	                if (ActItemTypeEnum.VIRTUAL.getValue().equals(actItem.getItemType())) {
	                    if (ActItemCategoryEnum.STRINGCODE.getValue().equals(actItem.getItemCategory())){
	                    	ActStringCode actStringCode = new ActStringCode();
	                    	actStringCode.setEnableFlag(null);
	                    	actStringCode.setDeleteFlag(null);
	                    	actStringCode.setItemNumber(actItem.getItemNumber());
	                    	actStringCode.setCompanyId(actItem.getCompanyId());
	                        int stringCodeNums = actStringCodeManager.getStringCodeCount(actStringCode);
	                        if(stringCodeNums>0){
	                        	result.setRespMsg(MisResultCode.Item31002);
	                            return result;
	                        }
	                    } 
	                }
	                //step:判断是否被活动未结算的活动引用
	               boolean useFlag = actItemsSettingManager.isProcessingActivityInUse(actItem.getItemNumber());
	               if(useFlag){
	            	   result.setRespMsg(MisResultCode.Item31007);
                       return result;
	               }
				}
			}
			actItemsSettingManager.deleteByIdArray(idArray);
			
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【deleteByIdArray】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
 
	 
	 
}