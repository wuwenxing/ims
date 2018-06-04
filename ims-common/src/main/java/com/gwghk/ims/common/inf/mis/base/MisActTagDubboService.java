package com.gwghk.ims.common.inf.mis.base;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.base.ActTagVO;

/**
 * 摘要：Mis用户标签服务
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月30日
 */
public interface MisActTagDubboService {
	MisRespResult<PageR<ActTagVO>> findPageList(ActTagVO userTagVO,Long companyId);
	
	MisRespResult<ActTagVO> findById(Long id,Long companyId);
	
	MisRespResult<ActTagVO> findByCode(String code,Long companyId);
	
	MisRespResult<Void> save(ActTagVO userTagVO,Long companyId);
	
	MisRespResult<Void> deleteByIds(String ids,Long companyId);
}
