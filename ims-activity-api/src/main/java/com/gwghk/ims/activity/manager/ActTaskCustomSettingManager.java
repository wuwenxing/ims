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

import com.gwghk.ims.activity.dao.entity.ActTaskCustomSetting;
import com.gwghk.ims.activity.dao.inf.ActTaskCustomSettingDao;
import com.gwghk.ims.activity.enums.ActTaskEnum;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.enums.SeqEnum;
import com.gwghk.ims.common.inf.SeqDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActTaskCustomSettingVO;
import com.gwghk.unify.framework.common.util.StringUtil;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Queryable;
import net.oschina.durcframework.easymybatis.query.Sort;
import net.oschina.durcframework.easymybatis.query.expression.Expression;
import net.oschina.durcframework.easymybatis.query.expression.ExpressionJoinable;
import net.oschina.durcframework.easymybatis.query.expression.ExpressionListable;
import net.oschina.durcframework.easymybatis.query.expression.ExpressionSqlable;
import net.oschina.durcframework.easymybatis.query.expression.ExpressionValueable;
import net.oschina.durcframework.easymybatis.query.expression.Expressional;
import net.oschina.durcframework.easymybatis.support.CrudService;

/**
 * 摘要：任务管理，业务处理
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月28日
 */
@Component
@Transactional
public class ActTaskCustomSettingManager  extends CrudService<ActTaskCustomSettingDao,ActTaskCustomSetting> {

	Logger logger = LoggerFactory.getLogger(ActTaskCustomSettingManager.class) ;
	
	@Autowired
	private SeqDubboService seqDubboService ;
	
	/**
	 * 功能：查询分页列表
	 */
	public Map<String,Object> findPageList(ActTaskCustomSettingVO vo,Long companyId) {
		//分页及排序设置
		Query query = new Query().page(vo.getPage(),vo.getRows());
		query.addSort(PageCustomizedUtil.getSortColumn(ActTaskCustomSettingVO.class,vo.getSort()),"asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC  : Sort.DESC);
		setParams(query,vo);
		Map<String,Object> result = new HashMap<String, Object>();
        result.put("list", ImsBeanUtil.copyList(this.getDao().find(query),ActTaskCustomSettingVO.class));
        result.put("total", this.getDao().countTotal(query));
        return result;
	}
	
	/**
	 * 功能：查询列表
	 */
	public List<ActTaskCustomSetting> findList(ActTaskCustomSettingVO vo){
		Query query = Query.buildQueryAll();
		this.setParams(query, vo);
		return this.getDao().find(query);
	}
	
	/**
	 * 功能：根据id->获取信息
	 */
	public ActTaskCustomSetting findById(Long id) {
		return this.getDao().get(id);
	}
	
	/**
	 * 功能：根据taskCode->查询实体
	 */
	public ActTaskCustomSetting findObjectByTaskCode(String taskCode) {
		if(StringUtils.isEmpty(taskCode)){
			return null ;
		}
		return this.getDao().getByExpression(new Query().eq("task_code",taskCode));
	}
	
	/**
	 * 功能：根据ruleCode->查询实体
	 */
	public ActTaskCustomSetting findObjectByRuleCode(String ruleCode) {
		if(StringUtils.isEmpty(ruleCode)){
			return null ;
		}
		return this.getDao().getByExpression(new Query().eq("rule_code",ruleCode));
	}
	
	/**
	 * 功能：新增或修改时保存信息
	 */
	public MisRespResult<Void> saveOrUpdate(ActTaskCustomSettingVO vo) throws Exception {
		MisRespResult<Void> mis = checkTaskExist(vo); 
		if(mis.isNotOk()){
			return mis ;
		}
		if (null == vo.getId()) {
			String pno = seqDubboService.getSeq(SeqEnum.ActivityTaskNumber.getSeqCode(), vo.getCompanyId()); 
	        if (pno == null) {
	            mis.setRespMsg(MisResultCode.Task22007) ;
	            return mis;
	        }
	        vo.setTaskNumber(pno);
			vo.setTaskType(ActTaskEnum.taskCustom.getCode());
			this.getDao().saveIgnoreNull(ImsBeanUtil.copyNotNull(new ActTaskCustomSetting(), vo));
		} else {
			ActTaskCustomSetting old = findById(vo.getId());
			if(ActTaskEnum.taskSystem.getCode()==old.getTaskType()){
				return MisRespResult.error(MisResultCode.Task22009) ;
			}
			ImsBeanUtil.copyNotNull(old, vo);
			old.setUpdateDate(new Date());
			this.getDao().updateIgnoreNull(old);
		}
		return MisRespResult.success() ;
	}
	
	/**
	 * 功能：启用或禁用
	 */
	public void updateByTaskCode(String taskCode,String enableFlag){
		ActTaskCustomSetting  actTaskCustomSetting = findObjectByTaskCode(taskCode);
		actTaskCustomSetting.setEnableFlag(enableFlag);
		this.getDao().updateIgnoreNull(actTaskCustomSetting);
	}
	
	/**
	 * 功能：删除
	 */
	public MisRespResult<Void> deleteById(String taskCode,String enableFlag){
		ActTaskCustomSetting  actTaskCustomSetting = findObjectByTaskCode(taskCode);
		if(ActTaskEnum.taskSystem.getCode().equals(actTaskCustomSetting.getTaskType())){
			return MisRespResult.error(MisResultCode.Task22010) ;
		}
		 
		this.getDao().delByExpression(new Query().eq("task_code", taskCode));
		return MisRespResult.success();
	}
	
	 
	/**
	 * 功能：批量删除信息
	 */
	public MisRespResult<Void> deleteByTaskCodes(String taskCodes) {
		String[] s = taskCodes.split(",") ;
		for(String taskCode:s){
			ActTaskCustomSetting  old = findObjectByTaskCode(taskCode);
			if(ActTaskEnum.taskSystem.getCode().equals(old.getTaskType())){
				return MisRespResult.error(MisResultCode.Task22010) ;
			}
		}
		this.getDao().delByExpression(new Query().in("task_code", Arrays.asList(taskCodes.split(","))));
		return MisRespResult.success();
	}

	 
	/**
	 * 功能：批量删除信息
	 */
	public MisRespResult<Void> deleteByIds(String ids) {
		String[] s = ids.split(",") ;
		for(String id:s){
			ActTaskCustomSetting old = findById(Long.valueOf(id)); 
			if(ActTaskEnum.taskSystem.getCode().equals(old.getTaskType())){
				return MisRespResult.error(MisResultCode.Task22010) ;
			}
		}
		this.getDao().delByExpression(new Query().in("id", Arrays.asList(ids.split(","))));
		return MisRespResult.success();
	}
	
	/**
	 * 功能：查询任务是否存在
	 */
	public MisRespResult<Void> checkTaskExist(ActTaskCustomSettingVO vo){
		MisRespResult<Void> mis = new MisRespResult<>() ;
		if(StringUtils.isEmpty(vo.getTaskCode())){
			return MisRespResult.error(MisResultCode.Task22001);
		}
		if(StringUtils.isEmpty(vo.getRuleCode())){
			return MisRespResult.error(MisResultCode.Task22002);
		}
		if(StringUtils.isEmpty(vo.getTaskName())){
			return MisRespResult.error(MisResultCode.Task22003);
		}
		Map<String,Object> map = new HashMap<>() ;
		map.put("taskCode", vo.getTaskCode()) ;
		map.put("ruleCode", vo.getRuleCode()) ;
		map.put("taskName", vo.getTaskName()) ;
		List<ActTaskCustomSetting> list = this.getDao().isExsits(map);
		if(list.size() > 0){
			for(int i = 0 ;i< list.size() ;i++){
				ActTaskCustomSetting actTaskCustomSetting = list.get(i) ;
				if(vo.getId() == null || !actTaskCustomSetting.getId().equals(vo.getId())){
					if(actTaskCustomSetting.getTaskCode().equals(vo.getTaskCode())){
						return MisRespResult.error(MisResultCode.Task22004);
					}else if(actTaskCustomSetting.getRuleCode().equals(vo.getRuleCode())){
						return MisRespResult.error(MisResultCode.Task22005);
					}else if(actTaskCustomSetting.getTaskName().equals(vo.getTaskName())){
						return MisRespResult.error(MisResultCode.Task22006);
					}
				}
				
			}
		}
		return mis;
	}
	
	/**
	 * 功能：设置查询参数
	 */
	private Query setParams(Query query,ActTaskCustomSettingVO vo){
		if(vo.getTaskType() != null){
			query.eq("task_type",vo.getTaskType());
		}
		if(StringUtil.isNotBlank(vo.getTaskCode())){
			query.like("task_code","%"+vo.getTaskCode()+"%");
		}
		if(StringUtil.isNotBlank(vo.getTaskName())){
			query.like("task_name","%"+vo.getTaskName()+"%");
		}
		if(StringUtil.isNotBlank(vo.getRuleCode())){
			query.like("rule_code","%"+vo.getRuleCode()+"%");
		}
		if(StringUtil.isNotBlank(vo.getEnableFlag())){
			query.eq("enable_flag",vo.getEnableFlag());
		}
		if(StringUtil.isNotBlank(vo.getDeleteFlag())){
			query.eq("delete_flag",vo.getDeleteFlag());
		}
		if(StringUtil.isNotEmpty(vo.getStartDate())){
			query.ge("create_date",vo.getStartDate());
		}
		if(StringUtil.isNotEmpty(vo.getEndDate())){
			query.le("create_date",vo.getEndDate());
		}
		if(vo.getCompanyId() != null){
			query.eq("company_id",vo.getCompanyId());
		}
		return query;
	}
}