package com.gwghk.ims.marketingtool.service.mis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.inf.mis.marketingtool.MisImsMsgAppDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgAppLogVO;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgBindVO;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgDataVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgAppLog;
import com.gwghk.ims.marketingtool.manager.ImsMsgAppManager;

@Service
public class MisImsMsgAppDubboServiceImpl implements MisImsMsgAppDubboService{

	private static final Logger logger = LoggerFactory.getLogger(MisImsMsgAppDubboServiceImpl.class);

	@Autowired
	private ImsMsgAppManager imsMsgAppManager ;

	@Override
	public MisRespResult<Void> waitingSendList(Long companyId) {
		imsMsgAppManager.waitingSendList(companyId);
		return MisRespResult.success();
	}

	@Override
	public MisRespResult<Void> sendAppMsgByTpl(String recipents, String sendTime, ImsMsgBindVO msgBind,
			ImsMsgDataVO msgData, Long companyId) {
		logger.info("sendAppMsgByTpl-->recipents:{},sendTime:{},msgBind:{},msgData:{},companyId:{}",new Object[]{recipents,sendTime,msgBind,msgData,companyId});
		return imsMsgAppManager.sendAppMsgByTpl(recipents, sendTime, msgBind, msgData, companyId);
	}

	@Override
	public MisRespResult<Void> sendAppMsgByCustom(ImsMsgAppLogVO vo, Long companyId) {
		logger.info("sendAppMsgByCustom-->ImsMsgAppLogVO:{},companyId:{}",new Object[]{vo,companyId});
		return imsMsgAppManager.sendAppMsgByCustom(ImsBeanUtil.copyNotNull(new ImsMsgAppLog(), vo), companyId) ;
	}
}
