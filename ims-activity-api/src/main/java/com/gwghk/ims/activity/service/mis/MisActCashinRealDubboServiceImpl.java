package com.gwghk.ims.activity.service.mis;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ActCashinReal;
import com.gwghk.ims.activity.manager.ActCashinRealManager;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.activity.MisActCashinRealDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActCashinRealVO;

/**
 * 摘要：入金记录service
 * @author warren
 * @Date 2017年11月14日
 */
@Service
public class MisActCashinRealDubboServiceImpl implements MisActCashinRealDubboService {
	private static final Logger logger = LoggerFactory.getLogger(MisActCashinRealDubboServiceImpl.class);
	
	@Autowired
	private ActCashinRealManager  actCashinRealManager;

	/**
	 * 功能：分页查询
	 */
	@Override
	public Map<String,Object> findPageList(ActCashinRealVO vo, @Company Long companyId) {
		try {
			return actCashinRealManager.findPageList(vo,companyId);
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return null;
		}
	}

	/**
	 * 功能：查询列表
	 */
	public MisRespResult<List<ActCashinRealVO>> findList(ActCashinRealVO vo,@Company Long companyId) {
		try {
			List<ActCashinReal> list = actCashinRealManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, ActCashinRealVO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}