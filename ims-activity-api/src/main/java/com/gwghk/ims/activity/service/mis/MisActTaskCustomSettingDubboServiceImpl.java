package com.gwghk.ims.activity.service.mis;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.activity.dao.entity.ActTaskCustomSetting;
import com.gwghk.ims.activity.dao.entity.ActTaskSetting;
import com.gwghk.ims.activity.manager.ActTaskCustomSettingManager;
import com.gwghk.ims.activity.manager.ActTaskSettingManager;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.activity.MisActTaskCustomSettingDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActTaskCustomSettingVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;

/**
 * 
 * 摘要：任务管理，业务实现
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月28日
 */
@Service
public class MisActTaskCustomSettingDubboServiceImpl implements MisActTaskCustomSettingDubboService {
	private static final Logger logger = LoggerFactory.getLogger(MisActTaskCustomSettingDubboServiceImpl.class);
		
	@Autowired
	private ActTaskCustomSettingManager actTaskCustomSettingManager ;
	
	@Autowired
	private ActTaskSettingManager actTaskSettingManager ;

	/**
	 * 功能：分页查询
	 */
	public  Map<String,Object> findPageList(ActTaskCustomSettingVO vo,@Company Long companyId) {
		try {
			return actTaskCustomSettingManager.findPageList(vo,companyId);
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return null;
		}
	}

	/**
	 * 功能：根据列表
	 */
	public MisRespResult<List<ActTaskCustomSettingVO>> findList(ActTaskCustomSettingVO vo,@Company Long companyId) {
		try {
			List<ActTaskCustomSetting> list = actTaskCustomSettingManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, ActTaskCustomSettingVO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 根据id->查询任务信息
	 */
	@Override
	public MisRespResult<ActTaskCustomSettingVO> findById(Long id,@Company Long companyId) {
		try {
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ActTaskCustomSettingVO(), actTaskCustomSettingManager.findById(id)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：保存任务信息
	 */
	@Override
	public MisRespResult<Void> saveOrUpdate(ActTaskCustomSettingVO vo,@Company Long companyId) {
		logger.info("saveOrUpdate-->【id:{},taskCode:{},ruleCode:{}】", new Object[]{vo.getId(),vo.getTaskCode(),vo.getRuleCode()});
		try {
			vo.setCompanyId(companyId);
			return actTaskCustomSettingManager.saveOrUpdate(vo);
		} catch (Exception e) {
			logger.error("<--系统异常，【saveOrUpdate】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：批量删除
	 */
	@Override
	public MisRespResult<Void> deleteByIds(String taskCodes,@Company Long companyId) {
		logger.info("deleteByIds-->【taskCodes:{}】",taskCodes);
		try {
			
			MisRespResult<Void> mis = new MisRespResult<Void>() ;
			String[] taskItemCodeArr = taskCodes.split(",");
			if (taskItemCodeArr != null && taskItemCodeArr.length > 0) {
				for (String taskItemCode : taskItemCodeArr) {
					if (!StringUtils.isEmpty(taskItemCode)) {
						ActTaskSettingVO vo = new ActTaskSettingVO() ;
						vo.setTaskItemCode(taskItemCode); 
						List<ActTaskSetting> taskSettingList = actTaskSettingManager.findTaskList(vo) ;
						if(!CollectionUtils.isEmpty(taskSettingList)){    //任务已经被引用
							mis.setRespMsg(MisResultCode.Task22008); 
							return mis;
						}
					}
				}
			}
			return actTaskCustomSettingManager.deleteByTaskCodes(taskCodes);
		} catch (Exception e) {
			logger.error("<--系统异常，【deleteByIds】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：批量启用或禁用任务
	 */
	@Override
	public MisRespResult<Void> updateEnable(String taskCodes,String enable,@Company Long companyId) {
		MisRespResult<Void> mis = new MisRespResult<Void>() ;
		String[] taskItemCodeArr = taskCodes.split(",");
		if (taskItemCodeArr != null && taskItemCodeArr.length > 0) {
			for (String taskItemCode : taskItemCodeArr) {
				if (!StringUtils.isEmpty(taskItemCode)) {
					ActTaskSettingVO vo = new ActTaskSettingVO() ;
					vo.setTaskItemCode(taskItemCode); 
					List<ActTaskSetting> taskSettingList = actTaskSettingManager.findTaskList(vo) ;
					if(!CollectionUtils.isEmpty(taskSettingList)){    //任务已经被引用
						mis.setRespMsg(MisResultCode.Task22008); 
						return mis;
					}
					actTaskCustomSettingManager.updateByTaskCode(taskItemCode, enable);
				}
			}
		}
		return mis;
	}
}