package com.gwghk.ims.activity.service.mis;

import java.util.List;

import org.aspectj.weaver.AjAttribute.EffectiveSignatureAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ActMaintenanceSetting;
import com.gwghk.ims.activity.dao.entity.ActMaintenanceSettingWrapper;
import com.gwghk.ims.activity.manager.ActMaintenanceSettingManager;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActCommonStatusEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActMaintenanceSettingDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.BaseVO;
import com.gwghk.ims.common.vo.activity.ActMaintenanceSettingVO;

/**
 * 
 * 摘要：任务维护设置，业务实现
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月28日
 */
@Service
public class MisActMaintenanceSettingDubboServiceImpl implements MisActMaintenanceSettingDubboService {
	private static final Logger logger = LoggerFactory.getLogger(MisActMaintenanceSettingDubboServiceImpl.class);
		
	@Autowired
	private ActMaintenanceSettingManager actMaintenanceManager ;

	public MisRespResult<PageR<ActMaintenanceSettingVO>> findPageList(ActMaintenanceSettingVO vo,@Company Long companyId) {
		logger.info("findPageList-->【activityName:{},activityPeriods:{},companyId:{}】", new Object[]{vo.getActivityName(),vo.getActivityPeriods(),vo.getCompanyId()});
		try {
			PageR<ActMaintenanceSettingVO> pageR = actMaintenanceManager.findPageList(vo);
			return MisRespResult.success(pageR);
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<List<ActMaintenanceSettingVO>> findList(ActMaintenanceSettingVO vo,@Company Long companyId) {
		logger.info("findList-->【activityName:{},activityPeriods:{},companyId:{}】", new Object[]{vo.getActivityName(),vo.getActivityPeriods(),vo.getCompanyId()});
		try {
			List<ActMaintenanceSettingWrapper> list = actMaintenanceManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, ActMaintenanceSettingVO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ActMaintenanceSettingVO> findById(Integer id,@Company Long companyId) {
		logger.info("findById-->【id:{}】", id);
		try {
			return MisRespResult.success(
					ImsBeanUtil.copyNotNull(new ActMaintenanceSettingVO(), actMaintenanceManager.findById(id)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> saveOrUpdate(ActMaintenanceSettingVO vo,@Company Long companyId) {
		logger.info("saveOrUpdate-->【id:{}】", vo.getId());
		try {
			return actMaintenanceManager.saveOrUpdate(vo);
		} catch (Exception e) {
			logger.error("<--系统异常，【saveOrUpdate】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> deleteByIdArray(String idArray,@Company Long companyId) {
		logger.info("deleteByIdArray-->【idArray:{}】", idArray);
		try {
			actMaintenanceManager.deleteByIdArray(idArray);
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【deleteByIdArray】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> updateEnable(int id,String enable,@Company Long companyId) {
		ActMaintenanceSettingVO dto = new ActMaintenanceSettingVO() ;
		dto.setId(id);
		dto.setEnableFlag(enable);
		try {
			actMaintenanceManager.saveOrUpdate(dto);
			return MisRespResult.success() ;
		} catch (Exception e) {
			logger.error("<--系统异常，【updateEnable】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		} 
	}

	@Override
	public MisRespResult<Void> batchEffective(String ids,BaseVO vo, Long companyId) {
		String[] s = ids.split(",") ;
		for(String id : s){
			ActMaintenanceSetting dto = actMaintenanceManager.findById(Integer.valueOf(id)) ;
			if(dto.getEffectiveFlag().equals(ActCommonStatusEnum.DISABLE.getCode())){
				ImsBeanUtil.copyNotNull(dto, vo);
				ActMaintenanceSettingVO actMaintenanceSettingVO = ImsBeanUtil.copyNotNull(new ActMaintenanceSettingVO(), dto);
				actMaintenanceSettingVO.setEffectiveFlag(ActCommonStatusEnum.ENABLE.getCode());
				actMaintenanceManager.saveOrUpdate(actMaintenanceSettingVO) ;
			}
		}
		return MisRespResult.success();
	}
}