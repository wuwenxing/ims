package com.gwghk.ims.marketingtool.dao.inf;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.gwghk.ims.marketingtool.dao.entity.ImsMsgAppLog;

import net.oschina.durcframework.easymybatis.dao.CrudDao;

public interface ImsMsgAppLogDao extends CrudDao<ImsMsgAppLog> {

	@Select("select * from ims_msg_app_log where company_id = #{companyId} and enable_flag = 'Y' and (status = 'waiting' or status = 'fail') and delete_flag != 'Y' and (fail_times  is null or fail_times <5)")
    List<ImsMsgAppLog> findWaitingSendList(@Param("companyId") Long companyId);
}