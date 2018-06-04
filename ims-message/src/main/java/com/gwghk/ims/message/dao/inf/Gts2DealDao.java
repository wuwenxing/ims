package com.gwghk.ims.message.dao.inf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.message.dao.entity.Gts2Deal;

/**
 * 
 * @ClassName: Gts2DealMapper
 * @Description: GTS2交易数据
 * @author lawrence
 * @date 2017年6月15日
 *
 */
public interface Gts2DealDao extends BaseDao<Gts2Deal>{
	
    int insertBatchReal(@Param("gts2DealRealList") List<Gts2Deal> gts2DealRealList);
		
	int updateBatchReal(@Param("gts2DealRealList") List<Gts2Deal> gts2DealRealList);
}
