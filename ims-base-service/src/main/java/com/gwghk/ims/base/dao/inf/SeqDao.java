package com.gwghk.ims.base.dao.inf;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.gwghk.ims.base.dao.entity.Seq;

import net.oschina.durcframework.easymybatis.dao.CrudDao;

/**
 * 摘要：统一序列号DAO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月15日
 */
public interface SeqDao extends CrudDao<Seq> {
    
	@Select("UPDATE  ims_seq SET  cur_val = #{curVal} WHERE seq_code = #{seqCode}")
	void updateByCode(@Param("seqCode") String seqCode,@Param("curVal") Long curVal);
}