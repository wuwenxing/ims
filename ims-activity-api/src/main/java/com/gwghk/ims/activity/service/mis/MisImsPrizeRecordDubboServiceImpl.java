package com.gwghk.ims.activity.service.mis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ActItemsSetting;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ActTaskItems;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.activity.manager.ActItemsSettingManager;
import com.gwghk.ims.activity.manager.ActSettingManager;
import com.gwghk.ims.activity.manager.ActTaskItemsManager;
import com.gwghk.ims.activity.manager.ImsPrizeRecordManager;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActItemCategoryEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActTypeEnum;
import com.gwghk.ims.common.enums.ActUnitEnum;
import com.gwghk.ims.common.enums.AuditStatusEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.inf.mis.activity.MisImsPrizeRecordDubboService;
import com.gwghk.ims.common.util.BigDecimalUtil;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.BaseVO;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;
import com.gwghk.ims.common.vo.activity.PrizeRecordRedoVO;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * 摘要：物品发放记录Service
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月10日
 */
@Service
public class MisImsPrizeRecordDubboServiceImpl implements MisImsPrizeRecordDubboService {
	private static final Logger logger = LoggerFactory.getLogger(MisImsPrizeRecordDubboServiceImpl.class);

	@Autowired
	private ImsPrizeRecordManager imsPrizeRecordManager;
	
	@Autowired
	private ActSettingManager actSettingManager;
	
	@Autowired
	private ActItemsSettingManager actItemsSettingManager;
	
	@Autowired
	private ActTaskItemsManager actTaskItemsManager;

	public MisRespResult<PageR<ImsPrizeRecordVO>> findPageList(ImsPrizeRecordVO vo,@Company Long companyId) {
		logger.info("findPageList-->【ActivityPeriods:{},accountNo:{}】", vo.getActNo(),vo.getAccountNo());
		try {
			PageR<ImsPrizeRecordVO> pageR = imsPrizeRecordManager.findPageList(vo);
			List<ImsPrizeRecordVO> list = pageR.getList() ;
			formateReturnData(list,companyId);
			return MisRespResult.success(pageR);
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<List<ImsPrizeRecordVO>> findList(ImsPrizeRecordVO vo,@Company Long companyId) {
		logger.info("findList-->【ActivityPeriods:{}】", vo.getActNo());
		try {
			List<ImsPrizeRecord> list = imsPrizeRecordManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, ImsPrizeRecordVO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ImsPrizeRecordVO> findById(Integer id,String actNo,@Company Long companyId) {
		logger.info("findById-->【id:{}】", id);
		try {
			return MisRespResult.success(
					ImsBeanUtil.copyNotNull(new ImsPrizeRecordVO(), imsPrizeRecordManager.findById(id,actNo)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> saveOrUpdate(ImsPrizeRecordVO vo,@Company Long companyId) {
		logger.info("saveOrUpdate-->【ActivityPeriods:{}】", vo.getActNo());
		try {
			vo.setIsAuto(0);
			vo.setAddFromBos(1);
			return imsPrizeRecordManager.save(vo);
		} catch (Exception e) {
			logger.error("<--系统异常，【saveOrUpdate】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> batchOperate(String ids, String action,String remark,String actNo,BaseVO base, Long companyId) {
		logger.info("batchOperate-->【ids:{},actNo:{},action:{}】", new Object[]{ids,actNo,action});
		try {
			return imsPrizeRecordManager.batchOperate(ids, action,remark,actNo,base);
		} catch (Exception e) {
			logger.error("<--系统异常，【batchOperate】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> batchSave(ImsPrizeRecordVO vo, List<List<Object>> accountList, Long companyId) {
		return imsPrizeRecordManager.batchSave(vo, accountList);
	}
	
	/**
	 * 批量添加同一活动下同一物品
	 * @param reqDto
	 * @return
	 */
	@Override
	public MisRespResult<Void> saveToBatch(ImsPrizeRecordVO reqVO,List<ImsPrizeRecordVO> accountVOs,Long companyId) {
		int successCount=0;
        int failCount=0;
        List<ImsPrizeRecordVO> failAccountVOs = null;
	    if(CollectionUtils.isNotEmpty(accountVOs)){
	    	
	    	 // TODO 任务类型不同类型操作暂无 george.li  2018-05-08
	        // 查询所属活动
	        ActSetting actSetting = actSettingManager.findByactivityPeriods(reqVO.getActNo());
	       
	        // 获取发放的物品
	        ActItemsSetting actItemsSetting =  actItemsSettingManager.findByItemNumber(reqVO.getItemNo());
	        
	        ActTaskItems actTaskItems = actTaskItemsManager.findById(reqVO.getTaskItemId());
	       /* if (ActTypeEnum.RW.getCode().equals(actSetting.getActivityType())) {
	        	actTaskItems = vActRwTaskItemMapper.getActItemById(imsPrizeRecordVO.getTaskItemsId());
	        } else if (ActTypeEnum.RJZJ.getCode().equals(actSetting.getActivityType())) {
	        	actTaskItems = vActRjzjTaskItemMapper.getActItemById(imsPrizeRecordVO.getTaskItemsId());
	        } else if (ActTypeEnum.CJ.getCode().equals(actSetting.getActivityType())) {
	        	actTaskItems = vActCjTaskItemMapper.getActItemById(imsPrizeRecordVO.getTaskItemsId());
	        } else if (ActTypeEnum.WPDH_DEMO.getCode().equals(actSetting.getActivityType())) {
	        	actTaskItems = vActWpdhDemoTaskItemMapper.getActItemById(imsPrizeRecordVO.getTaskItemsId());
	        } else if (ActTypeEnum.WPDH.getCode().equals(actSetting.getActivityType())) {
	        	actTaskItems = vActWpdhTaskItemMapper.getActItemById(imsPrizeRecordVO.getTaskItemsId());
	        }*/
	        
	        for(ImsPrizeRecordVO accountVO : accountVOs){
	            accountVO.setAccountNo(reqVO.getAccountNo());
	            accountVO.setPlatform(reqVO.getPlatform());
	            accountVO.setEnv(reqVO.getEnv());
	            accountVO.setCustMobile(reqVO.getCustMobile());
	            accountVO.setCustName(reqVO.getCustName());
	            accountVO.setCustEmail(reqVO.getCustEmail());
	            accountVO.setItemAmount(reqVO.getItemAmount());//奖励金额
	            
	            if(accountVO.getItemAmount()!=null && accountVO.getAccountCurrency()!=null){
	            	accountVO.setItemAmountUnit("USD".equals(accountVO.getAccountCurrency().toUpperCase())?"USD":"RMB");
	            }
	            
	            //查找用户信息
	            MisRespResult<Void> saveResult = null;
	            try{
	            	// TODO  保存需要 做以下 数据操作 根据旧的代码理解的业务。新的系统中新增发放记录没有对应的逻辑  george.li 2018-05-08   
	            	//1 、活动、物品、奖励物品是否存在 
	            	//2、 验证账号在平台的参与资格
	            	//3、获取发放物品，如是 物品兑换活动,需要扣除模拟币对等值
	            	//4、对已参与的账号保存 发放奖品的统计
	            	//5、保存账户统计信息
	                //saveResult = saveRecord(reqVO,actSetting,vActTaskItem,actItemsSetting);
	            	saveResult=saveOrUpdate(accountVO,reqVO.getCompanyId());
	            }catch(Exception e){
	            	return MisRespResult.error(MisResultCode.EXCEPTION);
	            }
	            
	            if(saveResult.isNotOk()){
	                failCount++;
	                if(failAccountVOs==null){
	                    failAccountVOs = new ArrayList<ImsPrizeRecordVO>();
	                }
	                accountVO.setRemark(saveResult.getMsg());
	                failAccountVOs.add(accountVO);
	            }else{
	                successCount++;
	            }
	 
	        }
	    }
	    MisRespResult<Void> result = new MisRespResult<Void>(MisResultCode.OK);
	    Map<String,Object> resultObj = new HashMap<String,Object>();
	    resultObj.put("failAccountVOs", failAccountVOs);
	    resultObj.put("successCount", successCount);
	    resultObj.put("failCount", failCount);
	    result.setExtendData(result);
	  
	    return result;
        
		 
	}
	
	/**
	 * 格式化输出的内容
	 * @param list
	 */
	public void formateReturnData(List<ImsPrizeRecordVO> list,Long companyId){
		for(ImsPrizeRecordVO r : list){
			r.setGivedStatusTxt(IssuingStatusEnum.format(r.getGivedStatus()));
            if(IssuingStatusEnum.waiting.getValue().equals(r.getGivedStatus())){
                r.setAuditStatusTxt("-");
            }else{
                r.setAuditStatusTxt(AuditStatusEnum.format(r.getAuditStatus()));
            }
            if (ActItemTypeEnum.WITHGOLD.getCode().equals(r.getItemType())
                || ActItemTypeEnum.TOKENCOIN.getCode().equals(r.getItemType())
                || ActItemTypeEnum.ANALOGCOIN.getCode().equals(r.getItemType())) {
                r.setItemCategoryTxt("-");
                r.setItemPriceTxt("-");
            } else {
                r.setItemCategoryTxt(ActItemCategoryEnum.format(r.getItemCategory()));
                if (r.getItemPrice() != null) {
                    r.setItemPriceTxt(BigDecimalUtil.formatComma2BigDecimal(r.getItemPrice()).toString());
                    if (StringUtil.isNotEmpty(r.getItemPriceUnit())) {
                       // r.setItemPriceTxt(r.getItemPriceTxt() + DictCache.getDictNameByDictCode(r.getItemPriceUnit()));
                    	r.setItemPriceTxt(r.getItemPriceTxt() + ActUnitEnum.getFormatCode(r.getItemPriceUnit()));
                    }
                }
            }
            if (r.getItemAmount() != null) {
                //String ItemAmountUnit = DictCache.getDictNameByDictCode(r.getItemAmountUnit());
                String ItemAmountUnit = ActUnitEnum.getFormatCode(r.getItemAmountUnit()) ;
                r.setItemAmountTxt(BigDecimalUtil.toFixedTwoDights(r.getItemAmount()));
                if (StringUtil.isNotEmpty(r.getItemAmountUnit())) {
                    r.setItemAmountTxt(r.getItemAmountTxt() + ItemAmountUnit);
                }
            }
            r.setItemTypeTxt(ActItemTypeEnum.formatValue(r.getItemType()));
            //r.setItemTypeTxt(DictCache.getDictNameByDictCode(r.getItemType()));
		}
		
	}

}
