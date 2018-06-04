package com.gwghk.ims.activity.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ActCashinReal;
import com.gwghk.ims.activity.dao.inf.ActCashinRealDao;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActCashinRealVO;
import com.gwghk.unify.framework.common.util.StringUtil;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Sort;
import net.oschina.durcframework.easymybatis.support.CrudService;

/**
 * 摘要：入金业务处理
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月24日
 */
@Component
public class ActCashinRealManager extends CrudService<ActCashinRealDao, ActCashinReal> {

	/**
	 * 功能：查询分页列表
	 */
	public Map<String, Object> findPageList(ActCashinRealVO vo, Long companyId) {
		Query query = new Query().page(vo.getPage(), vo.getRows());
		query.addSort(PageCustomizedUtil.getSortColumn(ActCashinRealVO.class, vo.getSort()),
				"asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC : Sort.DESC);
		setParams(query, vo);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", ImsBeanUtil.copyList(this.getDao().find(query),ActCashinRealVO.class));
		result.put("total", this.getDao().countTotal(query));
		return result;
	}

	/**
	 * 功能：查询列表
	 */
	public List<ActCashinReal> findList(ActCashinRealVO vo) {
		Query query = Query.buildQueryAll();
		query.addSort(PageCustomizedUtil.getSortColumn(ActCashinRealVO.class, vo.getSort()),"asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC : Sort.DESC);
		this.setParams(query, vo);
		return this.getDao().find(query);
	}
	
	
	
	/**
	 * 功能：设置查询参数
	 */
	private Query setParams(Query query,ActCashinRealVO vo){
		if(StringUtil.isNotBlank(vo.getAccountNo())){
			query.eq("account_no",vo.getAccountNo());
		}
		if(StringUtil.isNotBlank(vo.getPlatform())){
			query.eq("platform",vo.getPlatform());
		}
		if(StringUtil.isNotBlank(vo.getFirstDeposit())){
			query.eq("first_deposit",vo.getFirstDeposit());
		}
		if(StringUtil.isNotBlank(vo.getPno())){
			query.eq("pno",vo.getPno());
		}
		if(StringUtils.isNotBlank(vo.getStartApproveDate())){
			query.ge("approve_date",vo.getStartApproveDate());
		}
		if(StringUtils.isNotBlank(vo.getEndApproveDate())){
			query.le("approve_date",vo.getEndApproveDate());
		}
		if(vo.getCompanyId() != null){
			query.eq("company_id",vo.getCompanyId());
		}
		return query;
	}
}