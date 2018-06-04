package com.gwghk.ims.activity.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActTaskSetting;
import com.gwghk.ims.activity.dao.entity.ActTaskSettingParamsValJson;
import com.gwghk.ims.activity.dao.inf.ActTaskSettingDao;
import com.gwghk.ims.common.common.BaseEntity;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：活动任务-业务逻辑处理
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月2日
 */
@Component
@Transactional
public class ActTaskSettingManager {

	@Autowired
	private ActTaskSettingDao actTaskSettingDao;
	
	@Autowired
	private ActTaskCustomSettingManager actTaskCustomSettingManager;
	@Autowired
	private ActConditionSettingManager actConditionSettingManager;
	@Autowired
	private ActTaskItemsManager actTaskItemsManager;
	
	@Autowired
	private ActSettingManager actSettingManager;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActTaskSettingVO> findPageList(ActTaskSettingVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActTaskSetting.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ActTaskSetting>(this.findTaskList(vo)),
				new PageR<ActTaskSettingVO>(), ActTaskSettingVO.class);
	}
	
	/**
	 * 树形结构 
	 * @return
	 */
	public List<ActTaskSettingVO> findTaskTreeList(String activityPeriods) {
		List<ActTaskSettingVO> treeList = null;
		//加载任务,按a.task_group asc,a.task_order asc,a.id asc
		 Map<String,Object> map =  new HashMap<String,Object>();
		 map.put("activityPeriods", activityPeriods);
		 map.put("orderStr", "a.task_group asc,a.task_order asc,a.id asc");
		List<ActTaskSettingVO> actTaskSettings = findTaskListByMap(map);
	    if(CollectionUtils.isNotEmpty(actTaskSettings)){
	    	//加载活动下的所有物品
	    	Map<Long,List<ActTaskItemsVO>> actTaskItemsMap = new HashMap<Long,List<ActTaskItemsVO>>();
			ActTaskItemsVO actTaskItemsReqVo = new ActTaskItemsVO();
			actTaskItemsReqVo.setActivityPeriods(activityPeriods);
			List<ActTaskItemsVO> actTaskItems = actTaskItemsManager.findList(actTaskItemsReqVo);
			if(CollectionUtils.isNotEmpty(actTaskItems)){
				for(ActTaskItemsVO vo : actTaskItems){
					List<ActTaskItemsVO> itemList = actTaskItemsMap.get(vo.getTaskSettingId());
					if(itemList==null){
						itemList = new ArrayList<ActTaskItemsVO>();
					}
					itemList.add(vo);
					actTaskItemsMap.put(vo.getTaskSettingId(),itemList);
				}
			}
			//树型处理 :父子任务(默认已有按task_group asc，故不需要对此排序)
			  treeList = new ArrayList<ActTaskSettingVO>();
			Map<Long,List<ActTaskSettingVO>> parentChildsMap = new HashMap<Long,List<ActTaskSettingVO>>();
			for(ActTaskSettingVO taskVO : actTaskSettings){
				taskVO.setTaskItems(actTaskItemsMap.get(taskVO.getId()));
				if(taskVO.getParentId()==null){
					treeList.add(taskVO);
				}else{
					List<ActTaskSettingVO> childTasks = parentChildsMap.get(taskVO.getParentId());
					if(childTasks==null){
						childTasks = new ArrayList<ActTaskSettingVO>();
					}
					childTasks.add(taskVO);
					parentChildsMap.put(taskVO.getParentId(), childTasks);
				}			
			}
			if(!parentChildsMap.isEmpty()){
				for(ActTaskSettingVO parentTask : treeList){
					parentTask.setSubTaskSettings(parentChildsMap.get(parentTask.getId()));
				}
			} 
	    }
		return treeList;
	}
	
	 

	/**
	 * 功能：根据查询条件->查询
	 */
	private List<ActTaskSettingVO> findTaskListByMap(Map<String,Object> map) {
		 List<ActTaskSetting> list =  actTaskSettingDao.findListByMap(map);
		 List<ActTaskSetting> taskList = wrapActTaskSettings(list);
		 return ImsBeanUtil.cloneList(taskList, ActTaskSettingVO.class);
	}
	
	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ActTaskSettingVO> findList(ActTaskSettingVO reqVo) {
		List<ActTaskSetting> taskList = findTaskList(reqVo);
		return ImsBeanUtil.cloneList(taskList, ActTaskSettingVO.class);
	}
 
	/**
	 * 功能：根据查询条件->查询列表
	 */
	@SuppressWarnings("unchecked")
	public List<ActTaskSetting> findTaskList(ActTaskSettingVO vo) {
		 Map<String,Object> map = (Map<String,Object>)ImsBeanUtil.toMap(vo);
		 List<ActTaskSetting> list =  actTaskSettingDao.findListByMap(map);
		 return wrapActTaskSettings(list);
	}
	
	 

	 
	
	/**
	 * 功能：根据id->获取信息
	 */
	public ActTaskSetting findById(Long id) {
		ActTaskSetting actTaskSetting =  actTaskSettingDao.findObject(id);
		return wrapActTaskSetting(actTaskSetting);
	}
	
	private List<ActTaskSetting> wrapActTaskSettings(List<ActTaskSetting> actTasks){
		 List<ActTaskSetting> wraps = null;
		 if(CollectionUtils.isNotEmpty(actTasks)){
			 wraps = new ArrayList<ActTaskSetting>();
			 for(ActTaskSetting actTaskSetting : actTasks){
				 wraps.add(wrapActTaskSetting(actTaskSetting));
			 }
		 }
		return wraps;
	}
	
	private ActTaskSetting wrapActTaskSetting(ActTaskSetting actTaskSetting){
		if(actTaskSetting!=null){
			//把json中的属性值赋值给condSetting对象
			ActTaskSettingParamsValJson jsonVO = JsonUtil.json2Obj(actTaskSetting.getParamsVal(), ActTaskSettingParamsValJson.class);
			ImsBeanUtil.copyNotNull(actTaskSetting, jsonVO);
		}
		return actTaskSetting;
	}

	 
	/**
	 * 功能：新增或修改时保存信息
	 */
	public Long saveOrUpdate(ActTaskSettingVO vo) {
		//按新的直接覆盖
		ActTaskSetting actTaskSetting = ImsBeanUtil.copyNotNull(new ActTaskSetting(), vo);
		//把需要保存为json格式的参数，复制到Json中
		ActTaskSettingParamsValJson valJson = new ActTaskSettingParamsValJson();
		ImsBeanUtil.copyNotNull(valJson, vo);
		actTaskSetting.setParamsVal(JsonUtil.obj2Str(valJson));
		if (null == vo.getId()) {
			actTaskSetting.beforeSave();
			actTaskSettingDao.save(actTaskSetting);
			return actTaskSetting.getId();
		} else {
			ActTaskSetting oldActTaskSetting = findById(vo.getId());
			//拷贝原始数据中的通用值
			BaseEntity baseEntity = ImsBeanUtil.copyNotNull(new BaseEntity(), oldActTaskSetting);
			ImsBeanUtil.copyNotNull(actTaskSetting, baseEntity);
			actTaskSetting.beforeUpdate();
			actTaskSettingDao.update(actTaskSetting);
			return vo.getId();
		}
	}

	/**
	 * 功能：删除
	 */
	public void deleteById(Long id) {
		actTaskSettingDao.delete(id);
	}
	
	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		actTaskSettingDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}
	
}