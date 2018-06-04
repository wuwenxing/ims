package com.gwghk.ims.activity.dao.inf;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.activity.dao.entity.ActItemsSetting;
import com.gwghk.ims.common.common.BaseDao;

public interface ActItemsSettingDao extends BaseDao<ActItemsSetting> {
	 
	 /**
	  * 查找物品引用的活动为待审批或者正在进行中的条数
	  * @param map
	  * @return
	  */
	int countItemActivityInUse(Map<String,Object> map);
 
	/**
	 * 指定追加物品数量步长值
	 * @param giftNumber
	 * @param itemStockAmountStep 正负数,正数相加,负数相减
	 * @param itemUsableAmountStep 正负数,正数相加,负数相减
	 * @return
	 */
    int updateActItemsAmount(@Param("itemNumber") String itemNumber,@Param("itemStockAmountStep") Integer  itemStockAmountStep,@Param("itemUsableAmountStep") Integer  itemUsableAmountStep);
}