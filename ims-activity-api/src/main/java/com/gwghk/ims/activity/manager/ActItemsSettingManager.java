package com.gwghk.ims.activity.manager;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActItemsSetting;
import com.gwghk.ims.activity.dao.inf.ActItemsSettingDao;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActProposalStatusEnum;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActItemsSettingVO;

/**
 * 摘要：物品设置-业务逻辑处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
@Component
@Transactional
public class ActItemsSettingManager {

	@Autowired
	private ActItemsSettingDao actItemsSettingDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActItemsSettingVO> findPageList(ActItemsSettingVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActItemsSetting.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ActItemsSetting>(this.findItemList(vo)),new PageR<ActItemsSettingVO>(), ActItemsSettingVO.class);
	}
 
	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ActItemsSettingVO> findList(ActItemsSettingVO vo) {
		List<ActItemsSetting> list =  findItemList(vo);
		List<ActItemsSettingVO>  voList =  ImsBeanUtil.cloneList(list, ActItemsSettingVO.class);
		return voList;
	}
	
	/**
	 * 功能：根据查询条件->查询列表
	 */
	private List<ActItemsSetting> findItemList(ActItemsSettingVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("itemNumber", vo.getItemNumber());
		map.put("itemName", vo.getItemName());
		map.put("itemType", vo.getItemType());
		map.put("itemCategory", vo.getItemCategory());
		map.put("enableFlag", vo.getEnableFlag());
		map.put("startDate", vo.getStartDate());
		map.put("endDate", vo.getEndDate());
		map.put("companyId", vo.getCompanyId());
		List<ActItemsSetting> list =  actItemsSettingDao.findListByMap(map);
		return list;
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ActItemsSetting findById(Long id) {
		return actItemsSettingDao.findObject(id);
	}

	/**
	 * 功能：根据物品编号->获取信息(包括已删除的)
	 */
	public ActItemsSetting findByItemNumber(String itemNumber) {
		Map<String, Object> map = new HashMap<>();
		map.put("itemNumber",itemNumber);
		return actItemsSettingDao.findObjectByMap(map);
	}
	
	/**
	 * 功能：根据物品名称->获取信息(不包括已删除的)
	 */
	public ActItemsSetting findByItemName(String itemName) {
		Map<String, Object> map = new HashMap<>();
		map.put("itemName",itemName);
		map.put("deleteFlag","N");
		return actItemsSettingDao.findObjectByMap(map);
	}

	/**
	 * 功能：新增或修改时保存信息
	 */
	public MisRespResult<Void> saveOrUpdate(ActItemsSettingVO vo) throws Exception {
		ActItemsSetting  obj = ImsBeanUtil.copyNotNull(new ActItemsSetting(), vo);
		ActItemsSetting actItemsSetting = findByItemNumber(vo.getItemNumber());
        if (actItemsSetting!=null) {
        	//校验物品编号唯 一(包括已删除的)
        	if(vo.getId()==null || !vo.getId().equals(actItemsSetting.getId())){
        		return new MisRespResult<Void>(MisResultCode.Item31004);
        	}
        }
        //校验物品名称唯 一(不包括已删除的)
        ActItemsSetting actItemsSetting2 = findByItemName(vo.getItemName());
        if(actItemsSetting2!=null){
        	if(vo.getId()==null || !vo.getId().equals(actItemsSetting2.getId())){
        		return new MisRespResult<Void>(MisResultCode.Item31006);
        	}
        }
		if (null == vo.getId()) {
			obj.beforeSave();
			actItemsSettingDao.save(obj);
		} else {
			if(actItemsSetting==null){
				return new MisRespResult<Void>(MisResultCode.Item31005);
			}
			ImsBeanUtil.copyNotNull(actItemsSetting, obj);
			actItemsSetting.setUpdateDate(new Date());
			actItemsSettingDao.update(actItemsSetting);
		}
		return MisRespResult.success();
	}

	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		List<String> ids = Arrays.asList(idArray.split(","));
		for(String id :ids){
			if(StringUtils.isNotBlank(id)){
				ActItemsSetting item =findById(Long.parseLong(id));
				if(item!=null){
					 //是否有被活动使用 
			        boolean useFlag = isActivityInUse(item.getItemNumber());
			        if(useFlag){
			        	item.setDeleteFlag("Y");//假删除
			        	actItemsSettingDao.update(item);
			        }else{
						//真删除
						actItemsSettingDao.delete(Long.parseLong(id));
					}
				}
			}
		}
	}
	
	
	/**
	 * 是否有被正在进行中的活动使用 
	 * @param itemNumber
	 * @return
	 */
	public boolean isProcessingActivityInUse(String itemNumber){
		 Map<String,Object> inUseMap = new HashMap<String,Object>();
	        inUseMap.put("itemNumber", itemNumber);
	        inUseMap.put("filterProposalStatus", ActProposalStatusEnum.ACtHASCANCELED.getCode());//过滤已取消的
	        inUseMap.put("is_settlement", 0);//查询未结算的
	        int count  = countItemActivityInUse(inUseMap);
	        return count>0?true:false;
	}
	
	/**
	 * 是否有被活动使用 
	 * @param itemNumber
	 * @return
	 */
	public boolean isActivityInUse(String itemNumber){
		 Map<String,Object> inUseMap = new HashMap<String,Object>();
         inUseMap.put("itemNumber", itemNumber);
         int count  = countItemActivityInUse(inUseMap);
         return count>0?true:false;
	}
	 
	
	/**
	  * 查找物品引用的活动为待审批或者正在进行中的条数
	  * @param map
	  * @return
	  */
	public int countItemActivityInUse(Map<String,Object> map){
		return actItemsSettingDao.countItemActivityInUse(map);
	}
	
 

	/**
	 * 更新物品数量
	 * @param giftNumber
	 * @param giftTmpAmountStep
	 * @param giftAmountStep
	 */
	public boolean updateActItemsAmount(String itemNumber,Integer  itemStockAmountStep,Integer  itemUsableAmountStep) {
		return actItemsSettingDao.updateActItemsAmount(itemNumber,itemStockAmountStep,itemUsableAmountStep)>0;
	}
}