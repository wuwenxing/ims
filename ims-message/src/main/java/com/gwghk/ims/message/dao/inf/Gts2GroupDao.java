package com.gwghk.ims.message.dao.inf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.message.dao.entity.Gts2Group;

/**
 * 
 * @ClassName: Gts2GroupMapper
 * @Description: GTS2交易组
 * @author lawrence
 * @date 2017年8月18日
 *
 */
public interface Gts2GroupDao  extends BaseDao<Gts2Group> {
	
	List<Gts2Group> select(@Param("env")String env);
}