package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.ActWhiteListVO;

public interface MisActWhiteListDubboService {

	MisRespResult<PageR<ActWhiteListVO>> findPageList(ActWhiteListVO vo,@Company Long companyId);

	MisRespResult<List<ActWhiteListVO>> findList(ActWhiteListVO vo,@Company Long companyId);

	MisRespResult<ActWhiteListVO> findById(Integer id,@Company Long companyId);

	MisRespResult<Void> proposal(String idArray,ActWhiteListVO actWhiteListVO,@Company Long companyId);
	
	MisRespResult<Void> cancel(String idArray,ActWhiteListVO actWhiteListVO,@Company Long companyId);
	
	MisRespResult<Void> saveOrUpdate(ActWhiteListVO vo,@Company Long companyId);
	/**
	 * 批量保存白名单
	 * @param activityPeriods 活动编号
	 * @param whiteListFilePath 白名单文件完整路径
	 * @return
	 */
	MisRespResult<Void> batchSave(ActWhiteListVO vo,@Company Long companyId);

	MisRespResult<Void> checkUploadData(List<List<Object>> result, String compareFilePath,String actNo,@Company Long companyId) ;
	
	MisRespResult<Void> deleteByIdArray(String idArray,@Company Long companyId);

}
