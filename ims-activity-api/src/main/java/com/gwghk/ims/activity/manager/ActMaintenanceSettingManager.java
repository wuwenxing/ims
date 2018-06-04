package com.gwghk.ims.activity.manager;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActMaintenanceSetting;
import com.gwghk.ims.activity.dao.entity.ActMaintenanceSettingWrapper;
import com.gwghk.ims.activity.dao.inf.ActMaintenanceSettingDao;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActMaintenanceSettingVO;

/**
 * 
 * 摘要：任务维护设置，业务处理
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月28日
 */
@Component
@Transactional
public class ActMaintenanceSettingManager {
	
	Logger logger = LoggerFactory.getLogger(ActMaintenanceSettingManager.class); 

	@Autowired
	private ActMaintenanceSettingDao actMaintenanceDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActMaintenanceSettingVO> findPageList(ActMaintenanceSettingVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActMaintenanceSetting.class, vo.getSort(), vo.getOrder()));
		//return new PageR<ActMaintenanceSettingVO>(this.findList(vo)); 
		return PageCustomizedUtil.copyPageList(new PageR<ActMaintenanceSettingWrapper>(this.findList(vo)),new PageR<ActMaintenanceSettingVO>(), ActMaintenanceSettingVO.class);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ActMaintenanceSettingWrapper> findList(ActMaintenanceSettingVO vo) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("activityPeriods", vo.getActivityPeriods());
		map.put("activityName", vo.getActivityName());
		map.put("enableFlag", vo.getEnableFlag());
		map.put("companyId", vo.getCompanyId());
		map.put("effectiveFlag", vo.getEffectiveFlag());
		List<ActMaintenanceSettingWrapper> list = actMaintenanceDao.findListBySearch(map) ;
		try {
			return list ;
		} catch (Exception e) {
			logger.info("ActMaintenanceSettingVO 对象转换异常e:{}",new Object[]{e});
			e.printStackTrace();
		}
		return null ;
	}
	
	/**
     * 根据活动的修改状态，自动创建活动升级维护对象
     */
	public void createActMaintainByActEnableFlag(String activityPeriods,String oldEnableFlag,String newEnableFlag,Long companyId){
        if(!StringUtils.isEmpty(activityPeriods) && !StringUtils.isEmpty(newEnableFlag) && !newEnableFlag.equals(oldEnableFlag)){
        	ActMaintenanceSetting entity = null;
            if(newEnableFlag.equals("Y")){//修改为启动
                entity = actMaintenanceDao.findDisabledActMaintainByActivityPeriods(activityPeriods);
                if(entity != null ){
                    entity.setEndTime(new Date());//设置结束时间
                    entity.setUpdateDate(new Date());
                    entity.setCompanyId(companyId);
                   // entity.beforeUpdateSave();
                    actMaintenanceDao.update(entity);
                }
            }else if(newEnableFlag.equals("N")){//修改为禁用
                entity = new ActMaintenanceSetting();
                entity.setActivityPeriods(activityPeriods);
                entity.setStartTime(new Date());//设置开始时间
                entity.setEffectiveFlag("Y");//生效
                entity.setCreateDate(new Date());
                entity.setCompanyId(companyId);
              //  entity.beforeCreateSave();
                actMaintenanceDao.save(entity);
            }
        }
    }

	/**
	 * 功能：根据id->获取信息
	 */
	public ActMaintenanceSetting findById(Integer id) {
		return actMaintenanceDao.findObject(id);
	}
	
	/**
	 * 功能：新增或修改时保存信息
	 */
	public MisRespResult<Void> saveOrUpdate(ActMaintenanceSettingVO vo) {
		if (null == vo.getId()) {
			actMaintenanceDao.save(ImsBeanUtil.copyNotNull(new ActMaintenanceSetting(), vo));
		} else {
			ActMaintenanceSetting old = findById(vo.getId());
			ImsBeanUtil.copyNotNull(old, vo);
			actMaintenanceDao.update(old);
		}
		return MisRespResult.success() ;
	}

	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		actMaintenanceDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}
	
	
	
}