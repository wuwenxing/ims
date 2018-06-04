package com.gwghk.ims.activity.service.mis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.manager.ActTaskSettingManager;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.activity.MisActTaskSettingDubboService;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;

/**
 * 摘要：活动任务设置实现
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月13日
 */
@Service
public class MisActTaskSettingDubboServiceImpl implements MisActTaskSettingDubboService {
	private static final Logger logger = LoggerFactory.getLogger(MisActTaskSettingDubboServiceImpl.class);
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