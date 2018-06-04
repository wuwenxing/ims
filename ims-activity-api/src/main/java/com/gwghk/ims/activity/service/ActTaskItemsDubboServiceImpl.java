package com.gwghk.ims.activity.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.manager.ActTaskItemsManager;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.activity.MisActTaskItemsDubboService;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;

/***
 * 
* 摘要:活动任务奖励设置实现
* @author George.li  
* @date 2018年5月4日  
* @version 1.0
 */
@Service
public class ActTaskItemsDubboServiceImpl implements MisActTaskItemsDubboService {
	private static final Logger logger = LoggerFactory.getLogger(ActTaskItemsDubboServiceImpl.class);
	@Autowired
	private ActTaskItemsManager actTaskItemsManager;
 
	@Override
	public MisRespResult<List<ActTaskItemsVO>> findList(ActTaskItemsVO reqVo,Long companyId) {
		try {
			reqVo.setCompanyId(companyId);
			List<ActTaskItemsVO> list= actTaskItemsManager.findList(reqVo);
			return MisRespResult.success(list);
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
 
}