package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActProposalModifyDetailDTO;

public interface ActProposalModifyDetailDubboService {

	MisRespResult<PageR<ActProposalModifyDetailDTO>> findPageList(ActProposalModifyDetailDTO dto);

	MisRespResult<List<ActProposalModifyDetailDTO>> findList(ActProposalModifyDetailDTO dto);

	MisRespResult<ActProposalModifyDetailDTO> findById(Long id);

	MisRespResult<Void> saveOrUpdate(ActProposalModifyDetailDTO dto);

	MisRespResult<Void> deleteByIdArray(String idArray);

}
