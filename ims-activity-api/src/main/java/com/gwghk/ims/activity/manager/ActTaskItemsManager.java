package com.gwghk.ims.activity.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.activity.dao.entity.ActTaskItems;
import com.gwghk.ims.activity.dao.entity.ActTaskItemsParamsValJson;
import com.gwghk.ims.activity.dao.entity.ActTaskItemsWrapper;
import com.gwghk.ims.activity.dao.inf.ActTaskItemsDao;
import com.gwghk.ims.common.common.BaseEntity;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：活动任务-物品关联业务逻辑处理
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月3日
 */
@Component
@Transactional
public class ActTaskItemsManager {

	@Autowired
	private ActTaskItemsDao actTaskItemsDao;
	
	 

	/**
	 * 功能：根据查询条件->查询列表
	 */
	@SuppressWarnings("unchecked")
	public List<ActTaskItemsVO> findList(ActTaskItemsVO reqVo) {
		Map<String,Object> map = (Map<String,Object>)ImsBeanUtil.toMap(reqVo);
		List<ActTaskItemsWrapper> itemList = actTaskItemsDao.findListWrapperByMap(map);
		return ImsBeanUtil.cloneList(wrapActTaskItemsWrapperList(itemList), ActTaskItemsVO.class);
	}
	
	/**
	 * 功能：根据查询条件->查询列表
	 */
	@SuppressWarnings("unchecked")
	public ActTaskItems findObject(ActTaskItemsVO reqVo) {
		Map<String,Object> map = (Map<String,Object>)ImsBeanUtil.toMap(reqVo);
		return actTaskItemsDao.findObjectByMap(map) ;
	}
	 
 
	/**
	 * 功能：根据id->获取信息
	 */
	public ActTaskItems findById(Long id) {
		return wrapActTaskItems(actTaskItemsDao.findObject(id));
	}

	private List<ActTaskItemsWrapper> wrapActTaskItemsWrapperList(List<ActTaskItemsWrapper> actTaskItemsList){
		 List<ActTaskItemsWrapper> wraps = null;
		 if(CollectionUtils.isNotEmpty(actTaskItemsList)){
			 wraps = new ArrayList<ActTaskItemsWrapper>();
			 for(ActTaskItemsWrapper actTaskItems : actTaskItemsList){
				//把json中的属性值赋值给condSetting对象
				 ActTaskItemsParamsValJson jsonVO = JsonUtil.json2Obj(actTaskItems.getParamsVal(), ActTaskItemsParamsValJson.class);
				 ImsBeanUtil.copyNotNull(actTaskItems, jsonVO);
				 wraps.add(actTaskItems);
			 }
		 }
		return wraps;
	}
	
	
	private ActTaskItems wrapActTaskItems(ActTaskItems actTaskItems){
		//把json中的属性值赋值给condSetting对象
		ActTaskItemsParamsValJson jsonVO = JsonUtil.json2Obj(actTaskItems.getParamsVal(), ActTaskItemsParamsValJson.class);
		ImsBeanUtil.copyNotNull(actTaskItems, jsonVO);
		return actTaskItems;
	}
	 
	/**
	 * 功能：新增或修改时保存信息
	 */
	public void saveOrUpdate(ActTaskItemsVO vo)  {
		ActTaskItems actItem = ImsBeanUtil.copyNotNull(new ActTaskItems(), vo);
		//把需要保存为json格式的参数，复制到Json中
		ActTaskItemsParamsValJson valJson = new ActTaskItemsParamsValJson();
		ImsBeanUtil.copyNotNull(valJson, vo);
		actItem.setParamsVal(JsonUtil.obj2Str(valJson));
		if (null == vo.getId()) {
			actItem.beforeSave();
			actTaskItemsDao.save(actItem);
		} else {
			ActTaskItems oldActItem = findById(vo.getId());
			//拷贝原始数据中的通用值
			BaseEntity baseEntity = ImsBeanUtil.copyNotNull(new BaseEntity(), oldActItem);
			ImsBeanUtil.copyNotNull(actItem, baseEntity);
			actItem.setParamsVal(JsonUtil.obj2Str(valJson));
			actItem.beforeUpdate();
			actTaskItemsDao.update(actItem);
		}
	}

	/**
	 * 功能：删除
	 */
	public void deleteById(Long id) {
		actTaskItemsDao.delete(id);
	}
	
	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		actTaskItemsDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}
	
	 
	
}