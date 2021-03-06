package com.gwghk.sys.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.sys.SystemDictDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.system.SystemDictVO;
import com.gwghk.sys.api.manager.SystemDictManager;

/**
 * 摘要：系统-数据字典服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月7日
 */
@Service
public class SystemDictDubboServiceImpl implements SystemDictDubboService{
	private static final Logger logger = LoggerFactory.getLogger(SystemDictDubboServiceImpl.class);
	
	@Autowired
	private SystemDictManager systemDictManager;

	public MisRespResult<SystemDictVO> findByDictCode(String dictCode, Long companyId) {
		logger.info("findByDictCode-->【字典code:{dictCode},公司Id:{companyId}】",dictCode,companyId);
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new SystemDictVO(),systemDictManager.findByDictCode(dictCode, companyId)));
		}catch(Exception e){
			logger.error("<--findByDictCode 系统异常!!!",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<List<SystemDictVO>> findListByParentDictCode(String parentDictCode, Long companyId){
		logger.info("findListByParentDictCode-->【父字典code:{parentDictCode}, companyId:{companyId}】",parentDictCode,companyId);
		try{
			return MisRespResult.success(ImsBeanUtil.copyList(systemDictManager.findListByParentDictCode(parentDictCode, companyId),SystemDictVO.class));
		}catch(Exception e){
			logger.error("<--findListByParentDictCode 系统异常!!!",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
}
