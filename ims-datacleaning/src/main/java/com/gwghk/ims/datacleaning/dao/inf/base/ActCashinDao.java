package com.gwghk.ims.datacleaning.dao.inf.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.datacleaning.dao.entity.ActCashin;

/**
 * 
 * @ClassName: ActCashinMapper
 * @Description: 用户入金记录
 * @author lawrence
 * @date 2017年6月6日
 *
 */
public interface ActCashinDao extends BaseDao<ActCashin>{ 
	@SuppressWarnings("rawtypes")
	int insertGts2(Map data);

	ActCashin selectLastActCashinRecord(@Param("source") String source,@Param("companyCode") String companyCode);
	/**
	 * 查询所有没有推送的数据
	 * @return
	 */
	List<ActCashin> findUnpushCashin();
	/**
	 * 批量更新推送状态
	 */
	int batchUpdatePushStatus(@Param("id") Long id);
}
