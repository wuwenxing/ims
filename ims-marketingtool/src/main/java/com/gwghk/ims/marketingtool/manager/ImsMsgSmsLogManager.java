package com.gwghk.ims.marketingtool.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgSmsLogVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgSmsLog;
import com.gwghk.ims.marketingtool.dao.inf.ImsMsgSmsLogDao;
import com.gwghk.unify.framework.common.util.StringUtil;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Sort;
import net.oschina.durcframework.easymybatis.support.CrudService;

/**
 * 摘要：短信日志Manager
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月31日
 */
@Component
@Transactional
public class ImsMsgSmsLogManager extends CrudService<ImsMsgSmsLogDao,ImsMsgSmsLog> {
	
	/**
	 * 功能：分页查询
	 */
	public Map<String,Object> findPageList(ImsMsgSmsLogVO vo) {
		//分页及排序设置
		Query query = new Query().page(vo.getPage(),vo.getRows());
		query.addSort(PageCustomizedUtil.getSortColumn(ImsMsgSmsLogVO.class,vo.getSort()), "asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC  : Sort.DESC);
		if(StringUtil.isNotEmpty(vo.getMobile())){
			query.eq("mobile",vo.getMobile());
		}
		if(StringUtil.isNotEmpty(vo.getContent())){
			query.like("content","%"+vo.getContent()+"%");
		}
		if(StringUtil.isNotEmpty(vo.getStatus())){
			query.eq("status",vo.getStatus());
		}
		if(StringUtil.isNotEmpty(vo.getChannel())){
			query.eq("channel",vo.getChannel());
		}
		if(StringUtil.isNotEmpty(vo.getSource())){
			query.eq("source",vo.getSource());
		}
		if(StringUtil.isNotEmpty(vo.getBusinessNo())){
			query.eq("business_no",vo.getBusinessNo());
		}
		if(StringUtil.isNotEmpty(vo.getStartTimeStr())){
			query.ge("send_time",vo.getStartTimeStr());
		}
		if(StringUtil.isNotEmpty(vo.getEndTimeStr())){
			query.le("send_time",vo.getEndTimeStr());
		}
		if(vo.getCompanyId() != null){
			query.eq("company_id",vo.getCompanyId());
		}
		query.eq("delete_flag","N");
		Map<String,Object> result = new HashMap<String, Object>();
        result.put("list", ImsBeanUtil.copyList(this.getDao().find(query),ImsMsgSmsLogVO.class));
        result.put("total", this.getDao().countTotal(query));
        return result;
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ImsMsgSmsLog> findList(ImsMsgSmsLogVO vo) {
		Query query = new Query();
		query.addSort(PageCustomizedUtil.getSortColumn(ImsMsgSmsLogVO.class,vo.getSort()), "asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC  : Sort.DESC);
		if(StringUtil.isNotEmpty(vo.getMobile())){
			query.eq("mobile",vo.getCode());
		}
		if(StringUtil.isNotEmpty(vo.getContent())){
			query.like("content",vo.getContent());
		}
		if(StringUtil.isNotEmpty(vo.getStatus())){
			query.eq("status",vo.getStatus());
		}
		if(StringUtil.isNotEmpty(vo.getChannel())){
			query.eq("channel",vo.getChannel());
		}
		if(StringUtil.isNotEmpty(vo.getSource())){
			query.eq("source",vo.getSource());
		}
		if(StringUtil.isNotEmpty(vo.getBusinessNo())){
			query.eq("business_no",vo.getBusinessNo());
		}
		if(StringUtil.isNotEmpty(vo.getStartTimeStr())){
			query.ge("send_time",vo.getStartTimeStr());
		}
		if(StringUtil.isNotEmpty(vo.getEndTimeStr())){
			query.le("send_time",vo.getEndTimeStr());
		}
		if(vo.getCompanyId() != null){
			query.eq("company_id",vo.getCompanyId());
		}
		query.eq("delete_flag","N");
		return this.getDao().findAll(query);
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ImsMsgSmsLog findById(Long id) {
		return this.getDao().get(id);
	}
	
	/**
	 * 功能：新增或修改时保存信息
	 */
	public MisRespResult<Void> saveOrUpdate(ImsMsgSmsLog vo){
		this.getDao().save(vo) ;
		return MisRespResult.success() ;
	}
	
	public List<ImsMsgSmsLog> findWaitingSendList(Long companyId){
		return this.getDao().findWaitingSendList(companyId);
	}

	/**
	 * 功能：批量删除信息
	 */
	public int deleteByIds(String ids) {
		return this.getDao().delByExpression(new Query().in("id", Arrays.asList(ids.split(","))));
	}
}