package com.gwghk.ims.activity.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.manager.ActTaskSettingManager;
import com.gwghk.ims.activity.service.mis.CommonDubboService;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.external.activity.ActTaskSettingDubboService;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;

/***
 * 
* 摘要:活动任务设置实现   
* @author George.li  
* @date 2018年5月4日  
* @version 1.0
 */
@Service
public class ActTaskSettingDubboServiceImpl implements ActTaskSettingDubboService {
	private static final Logger logger = LoggerFactory.getLogger(ActTaskSettingDubboServiceImpl.class);
	@Autowired
	private ActTaskSettingManager actTaskSettingManager;
	
	@Autowired
	private CommonDubboService commonDubboService;
 
	@Override
	public MisRespResult<List<ActTaskSettingVO>> findList(ActTaskSettingVO reqVo,Long companyId) {
		try {
			reqVo.setCompanyId(companyId);
			List<ActTaskSettingVO> list= actTaskSettingManager.findList(reqVo);
			commonDubboService.wrapTaskFullName(list);
			return MisRespResult.success(list);
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
 
}