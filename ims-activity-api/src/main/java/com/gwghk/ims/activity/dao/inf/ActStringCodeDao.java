package com.gwghk.ims.activity.dao.inf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.activity.dao.entity.ActStringCode;
import com.gwghk.ims.activity.dao.entity.ActStringCodeWrapper;
import com.gwghk.ims.common.common.BaseDao;

/**
 * 摘要：串码管理mapper
 * @author eva.liu
 * @version 1.0
 * @Date 2018年3月29日
 */
public interface ActStringCodeDao extends BaseDao<ActStringCode>{
 
    int checkStringCode(@Param("stringCode") String stringCode,@Param("id") Long id);
 
	int updateByStringCode(ActStringCode actStringCode);
	
	int updateStatusByStringCode(ActStringCode actStringCode);
	
	List<String> findAllStringCodes();

    List<ActStringCodeWrapper> findListBySearch(Map<String,Object> map);
 
}