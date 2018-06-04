package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActMessageRecordDTO;

public interface ActMessageRecordDubboService {

	MisRespResult<PageR<ActMessageRecordDTO>> findPageList(ActMessageRecordDTO dto);

	MisRespResult<List<ActMessageRecordDTO>> findList(ActMessageRecordDTO dto);

	MisRespResult<ActMessageRecordDTO> findById(Long id);

	MisRespResult<Void> saveOrUpdate(ActMessageRecordDTO dto);

	MisRespResult<Void> deleteByIdArray(String idArray);

}
