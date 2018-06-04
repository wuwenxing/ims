package com.gwghk.ims.common.inf.mis.sys;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.system.SystemLogVO;

public interface MisSystemLogDubboService {
	
	MisRespResult<Void> addLog(SystemLogVO logReqDto);

	MisRespResult<PageR<SystemLogVO>> findPageList(SystemLogVO vo);
}