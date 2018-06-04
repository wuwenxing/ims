package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.ActBlackListVO;

public interface MisActBlackListDubboService {

	MisRespResult<PageR<ActBlackListVO>> findPageList(ActBlackListVO vo,@Company Long companyId);

	MisRespResult<List<ActBlackListVO>> findList(ActBlackListVO vo,@Company Long companyId);

	MisRespResult<List<ActBlackListVO>> findListByActivityPeriods(String actNo,@Company Long companyId);

	MisRespResult<ActBlackListVO> findById(Integer id,@Company Long companyId);

	MisRespResult<Void> proposal(String idArray,ActBlackListVO actBlackListVO,@Company Long companyId);
	
	MisRespResult<Void> cancel(String idArray,ActBlackListVO actBlackListVO,@Company Long companyId);
	
	MisRespResult<Void> saveOrUpdate(ActBlackListVO vo,@Company Long companyId);
	/**
	 * 批量保存黑名单
	 * @param activityPeriods 活动编号
	 * @param whiteListFilePath 黑名单文件完整路径
	 * @return
	 */
	MisRespResult<Void> batchSave(ActBlackListVO actBlackListVO,@Company Long companyId);
	
	MisRespResult<Void> checkUploadData(List<List<Object>> result, String compareFilePath,String actNo,@Company Long companyId) ;

	MisRespResult<Void> deleteByIdArray(String idArray,@Company Long companyId);

}
