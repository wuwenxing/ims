package com.gwghk.ims.activity.service.mis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ActTaskCustomSetting;
import com.gwghk.ims.activity.manager.ActTaskCustomSettingManager;
import com.gwghk.ims.common.common.Kuak;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.inf.mis.base.MisActTagDubboService;
import com.gwghk.ims.common.inf.mis.sys.SystemDictDubboService;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
import com.gwghk.ims.common.vo.base.ActTagVO;
import com.gwghk.ims.common.vo.system.SystemDictVO;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：CommonDubboService实现类
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月3日
 */
@Service
public class CommonDubboService {
	 
	@Autowired
	private SystemDictDubboService systemDictDubboService;
	
	@Autowired
	private MisActTagDubboService misUserTagDubboService;
	
	@Autowired
	private ActTaskCustomSettingManager actTaskCustomSettingManager;
	 
	/**
	 * 根据数据字典编号获得名称
	 * @param dictCode
	 * @param companyId
	 * @return
	 */
	public String getDictName(String dictCode, Long companyId){
		if(StringUtil.isNotEmpty(dictCode)){
			MisRespResult<SystemDictVO> result = systemDictDubboService.findByDictCode(dictCode, companyId);
			if(result==null || result.isNotOk() ||result.getData()==null){
			   return 	dictCode;
			}else{
				return result.getData().getDictNameCn();
			}
		}
		return null;
	}
	
	/**
	 * 根据父code加载其下所有的子节点list集合对象
	 * @param parentDictCode
	 * @param companyId
	 * @return
	 */
	public List<SystemDictVO> getSubDictListByParentCode(String parentDictCode, Long companyId){
		if(StringUtil.isNotEmpty(parentDictCode)){
			MisRespResult<List<SystemDictVO>> result = systemDictDubboService.findListByParentDictCode(parentDictCode, companyId);
			if(result==null || result.isNotOk() ||result.getData()==null){
			   return 	null;
			}else{
				return result.getData();
			}
		}
		return null;
	}
	
	/**
	 * 根据父code加载其下所有的子节点map集合
	 * @param parentDictCode
	 * @param companyId
	 * @return
	 */
	public Map<String,String> getSubDictMapByParentCode(String parentDictCode, Long companyId){
		if(StringUtil.isNotEmpty(parentDictCode)){
			MisRespResult<List<SystemDictVO>> result = systemDictDubboService.findListByParentDictCode(parentDictCode, companyId);
			if(result==null || result.isNotOk() ||result.getData()==null){
			   return 	null;
			}else{
				List<SystemDictVO> list = result.getData();
				if(CollectionUtils.isNotEmpty(list)){
					 Map<String,String> dictMap = new HashMap<String,String>();
					 for(SystemDictVO dict : list){
						 dictMap.put(dict.getDictCode(), dict.getDictNameCn());
					 }
					 return dictMap;
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据父code加载其下所有的子节点list集合
	 * @param parentDictCode
	 * @param companyId
	 * @return
	 */
	public  List<Kuak> getOptionsByParentCode(String parentDictCode, Long companyId){
		if(StringUtil.isNotEmpty(parentDictCode)){
			MisRespResult<List<SystemDictVO>> result = systemDictDubboService.findListByParentDictCode(parentDictCode, companyId);
			if(result==null || result.isNotOk() ||result.getData()==null){
			   return 	null;
			}else{
				List<SystemDictVO> list = result.getData();
				if(CollectionUtils.isNotEmpty(list)){
					List<Kuak> options = new ArrayList<Kuak>();
					 for(SystemDictVO dict : list){
						 
						 options.add(new Kuak(dict.getDictCode(), dict.getDictNameCn()));
					 }
					 return options;
				}
			}
		}
		return null;
	}
 
 
	/**
	 * 根据数据字典编号获得名称
	 * @param dictCode
	 * @param companyId
	 * @return
	 */
	public String getTagName(String tagCode, Long companyId){
		if(StringUtil.isNotEmpty(tagCode)){
			MisRespResult<ActTagVO> result = misUserTagDubboService.findByCode(tagCode, companyId);
			if(result==null || result.isNotOk() ||result.getData()==null){
			   return null;
			}else{
				return result.getData().getTagName();
			}
		}
		return null;
	}
	
	/**
	 * 任务全名
	 * @param actTaskSettings
	 */
	public void wrapTaskFullName(List<ActTaskSettingVO> actTaskSettings){
		if(CollectionUtils.isNotEmpty(actTaskSettings)){
			for(ActTaskSettingVO  vo : actTaskSettings){ 
				wrapTaskFullName(vo,null);
			}	
		}
	}
	
	/**
	 * 封装任务名称及任务显示全名
	 * @param actTaskSettings
	 */
	public void wrapTaskFullName(ActTaskSettingVO vo,String taskName){
		if(vo!=null){
			//任务名称
			ActTaskCustomSetting actTaskCustomSetting = null;
			if(StringUtils.isBlank(taskName)){
				if(StringUtils.isBlank(vo.getTaskName())){
					actTaskCustomSetting = actTaskCustomSettingManager.findObjectByTaskCode(vo.getTaskItemCode());
					vo.setTaskName(actTaskCustomSetting!=null?actTaskCustomSetting.getTaskName():null);
				}
			}else{
				vo.setTaskName(taskName);
			}		
			//处理任务标题全名
			if(StringUtil.isNotEmpty(vo.getTaskDesc())){
				vo.setTaskFullName(vo.getTaskDesc());
			}else{
				StringBuffer taskFullName = new StringBuffer();
				taskFullName.append(vo.getTaskName());
				if(StringUtil.isNotBlank(taskFullName)){
					if(StringUtil.isNotEmpty(vo.getTaskItemVal())){
						taskFullName.append(vo.getTaskItemVal());
						if(StringUtil.isNotEmpty(vo.getTaskItemValUnit())){
							String dicName = getDictName(vo.getTaskItemValUnit(), vo.getCompanyId());
							taskFullName.append(dicName!=null?dicName:"");
						}
					}
					if(StringUtil.isNotEmpty(vo.getTaskItemVal2())){
						taskFullName.append(vo.getTaskItemVal2());
						if(StringUtil.isNotEmpty(vo.getTaskItemVal2Unit())){
							String dicName =getDictName(vo.getTaskItemVal2Unit(), vo.getCompanyId());
							taskFullName.append(dicName!=null?dicName:"");
						}
					}
					if(vo.getTaskItemTime()!=null){
						taskFullName.append(vo.getTaskItemTime());
						if(StringUtil.isNotEmpty(vo.getTaskItemTimeUnit())){
							String dicName =getDictName(vo.getTaskItemTimeUnit(), vo.getCompanyId());
							taskFullName.append(dicName!=null?dicName:"");
						}
					}						 
				}
				vo.setTaskFullName(taskFullName.toString());
			}
			if(CollectionUtils.isNotEmpty(vo.getSubTaskSettings())){
				wrapTaskFullName(vo.getSubTaskSettings());
			}
		}	 
	}
	
}