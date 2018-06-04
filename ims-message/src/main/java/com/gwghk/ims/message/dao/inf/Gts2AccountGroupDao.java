package com.gwghk.ims.message.dao.inf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.message.dao.entity.Gts2AccountGroup;

/**
 * 
 * @ClassName: Gts2AccountGroupMapper
 * @Description: GTS2账号平台组
 * @author lawrence
 * @date 2017年8月17日
 *
 */
public interface Gts2AccountGroupDao extends BaseDao<Gts2AccountGroup>{
		
	List<Gts2AccountGroup> select(@Param("env")String env);
}