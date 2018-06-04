package com.gwghk.ims.datacleaning.dao.inf.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.datacleaning.dao.entity.ActCashout;

/**
 * 
 * @ClassName: ActCashoutMapper
 * @Description: 用户出金记录
 * @author lawrence
 * @date 2017年6月6日
 *
 */
public interface ActCashoutDao extends BaseDao<ActCashout>{
	@SuppressWarnings("rawtypes")
	int insertGts2(Map data);

	ActCashout selectLastActCashoutRecord(@Param("companyCode") String companyCode);
	
	/**
	 * 查询所有没有推送的数据
	 * @return
	 */
	List<ActCashout> findUnpushCashout();
	/**
	 * 批量更新推送状态
	 */
	int batchUpdatePushStatus(@Param("id") Long id);
}
