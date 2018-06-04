package com.gwghk.ims.activity.dao.inf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.activity.dao.entity.ImsMallItem;
import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.common.vo.mall.ImsMallItemVO;

public interface MisMallItemDao extends BaseDao<ImsMallItem>{
	
	/**
	 * 分页查询
	 * @param pageSearchVo
	 * @return
	 */
	List<ImsMallItem> listByPage(ImsMallItemVO pageSearchVo);

	/**
	 * 
	 * @param idArr
	 * @return
	 */
	int batchSoftDel(@Param("ids") String[] idArr);

}
