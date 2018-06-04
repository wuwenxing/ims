package com.gwghk.ims.common.inf.mis.marketingtool;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgAppLogVO;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgBindVO;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgDataVO;

public interface MisImsMsgAppDubboService {
	
	MisRespResult<Void> waitingSendList(@Company Long companyId);

	public MisRespResult<Void> sendAppMsgByTpl(String recipents,String sendTime, ImsMsgBindVO msgBind,ImsMsgDataVO msgData,@Company Long companyId) ;

	public MisRespResult<Void> sendAppMsgByCustom(ImsMsgAppLogVO imsMsgAppLogVO,@Company Long companyId) ;

}
