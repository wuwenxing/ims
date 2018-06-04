package com.gwghk.ims.common.inf.mis.activity;

import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.ActProposalModifyVO;
import com.gwghk.ims.common.vo.system.SystemUserVO;

public interface MisActProposalModifyDubboService {

	MisRespResult<PageR<ActProposalModifyVO>> findPageList(ActProposalModifyVO vo,@Company Long companyId);
 
	
	 /**
     * 根据提案号，查询该提案的活动的修改详情
     * 
     * @param actSetting
     * @param actProposalModify
     * @return
     */
	MisRespResult<Map<String,Object>> getActProposalModifyByPno(String pno,@Company Long companyId);
    
    /**
     * 功能：批核活动修改提案
     */
    MisRespResult<Void> updateApproveModifyProposal(String pno, boolean isAutoApprove,SystemUserVO approver,@Company Long companyId);
    
    /**
     * 功能：取消活动修改提案
     */
    MisRespResult<Void> updateCancelModifyProposal(String pno,String cancelReason,SystemUserVO approver,Long companyId);

}
