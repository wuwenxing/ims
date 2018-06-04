package com.gwghk.ims.activity.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.cache.ActSettingLocalCache;
import com.gwghk.ims.activity.dao.entity.ActConditionSetting;
import com.gwghk.ims.activity.dao.entity.ActConditionSettingWrapper;
import com.gwghk.ims.activity.dao.entity.ActConditionValJson;
import com.gwghk.ims.activity.dao.inf.ActConditionSettingDao;
import com.gwghk.ims.activity.util.ActCommonDealUtil;
import com.gwghk.ims.common.common.BaseEntity;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActRuleTypeEnum;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActConditionSettingVO;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：活动条件-业务逻辑处理
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月2日
 */
@Component
@Transactional
public class ActConditionSettingManager {

	@Autowired
	private ActConditionSettingDao actConditionSettingDao;
	@Autowired
	private ActSettingLocalCache actSettingLocalCache;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActConditionSettingVO> findPageList(ActConditionSettingVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActConditionSetting.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ActConditionSetting>(this.findList(vo)),
				new PageR<ActConditionSettingVO>(), ActConditionSettingVO.class);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ActConditionSetting> findList(ActConditionSettingVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("activityPeriods", vo.getActivityPeriods());
		map.put("conditionType", vo.getConditionType());
		map.put("conditionVal", vo.getConditionVal());
		map.put("companyId", vo.getCompanyId());
		return actConditionSettingDao.findListByMap(map);
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ActConditionSetting findById(Long id) {
		return actConditionSettingDao.findObject(id);
	}

	/**
	 * 获得指定活动的客户参与条件
	 * @param activityPeriods
	 * @return
	 */
	public ActConditionSetting findCustCondtionSetting(String activityPeriods){
		return actSettingLocalCache.getActCondCustomInfoByActivityPeriods(activityPeriods);
	}
	
	/**
	 * 获得指定活动的客户参与条件VO对象
	 * @param activityPeriods
	 * @return
	 */
	public ActConditionSettingVO findCustCondtionSettingVO(String activityPeriods){
		ActConditionSetting actConditionSetting =  actSettingLocalCache.getActCondCustomInfoByActivityPeriods(activityPeriods);
		if(actConditionSetting!=null){
			ActConditionSettingVO actCondSettingVO = new ActConditionSettingVO();
			ImsBeanUtil.copyNotNull(actCondSettingVO, actConditionSetting);
			return actCondSettingVO;
		}
		return null;
	}
	
	/**
	 * 获得指定活动的客户参与条件(包含其它业务处理属性)
	 * @param activityPeriods
	 * @return
	 */
	public ActConditionSettingWrapper findCustCondtionSettingWrap(String activityPeriods){
		ActConditionSettingWrapper actCondSetting = actSettingLocalCache.getActCondCustomInfoWrapByActivityPeriods(activityPeriods);
		return actCondSetting;
	}
	
	/**
	 * 功能：根据活动->获得客户的参与条件信息(原生数据)
	 */
	public ActConditionSetting getActCustConditionSetting(String activityPeriods) {
		return findByActAndType(activityPeriods,ActRuleTypeEnum.CUSTINFO.getCode());
	}
	
	
	/**
	 * 功能：根据活动->获取信息
	 */
	public ActConditionSetting findByActAndType(String activityPeriods,String conditionType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("activityPeriods", activityPeriods);
		map.put("conditionType", conditionType);
		ActConditionSetting actConditionSetting =  actConditionSettingDao.findObjectByMap(map);
		return actConditionSetting;
	}
	
	/**
	 * 功能：新增或修改时保存信息
	 */
	public MisRespResult<Void> saveOrUpdate(ActConditionSettingVO vo)  {
		//保存客户参与条件
		ActConditionSetting oldActCond = getActCustConditionSetting(vo.getActivityPeriods());
		if(oldActCond!=null){
        	if(vo.getId()==null || !vo.getId().equals(vo.getId())){
        		return new MisRespResult<Void>(MisResultCode.Act20002);
        	}
		}else if(vo.getId()!=null && oldActCond==null){
			return new MisRespResult<Void>(MisResultCode.Act20003);
		}
		if(StringUtil.isNotBlank(vo.getConditionType())){
			vo.setConditionType(ActRuleTypeEnum.CUSTINFO.getCode());
		}
		vo.setPlatforms(ActCommonDealUtil.getPlatforms(vo.getPlatformsCcy()));
		vo.setCcy(ActCommonDealUtil.getCcy(vo.getPlatformsCcy()));
		//按新的设置保存
		ActConditionSetting actCond = ImsBeanUtil.copyNotNull(new ActConditionSetting(), vo);
		//把需要保存为json格式的参数，复制到Json中
		ActConditionValJson actConditionValJson = new ActConditionValJson();
		ImsBeanUtil.copyNotNull(actConditionValJson, vo);
		actCond.setConditionVal(JsonUtil.obj2Str(actConditionValJson));
		if (null == vo.getId()) {
			actCond.beforeSave();
			actConditionSettingDao.save(actCond);
		} else {
			//拷贝原始数据中的通用值
			BaseEntity baseEntity = ImsBeanUtil.copyNotNull(new BaseEntity(), oldActCond);
			ImsBeanUtil.copyNotNull(actCond, baseEntity);
			actCond.beforeUpdate();
			actConditionSettingDao.update(actCond);
		}
		return MisRespResult.success();
	}
	
	

	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		actConditionSettingDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}
	
}