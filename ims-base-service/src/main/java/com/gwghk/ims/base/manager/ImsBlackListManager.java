package com.gwghk.ims.base.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.base.dao.entity.ImsBlackList;
import com.gwghk.ims.base.dao.inf.ImsBlackListDao;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.base.ImsBlackListVO;
import com.gwghk.unify.framework.common.util.StringUtil;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Sort;
import net.oschina.durcframework.easymybatis.support.CrudService;

/**
 * 摘要：全局黑名单业务逻辑处理
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月4日
 */
@Component
@Transactional
public class ImsBlackListManager extends CrudService<ImsBlackListDao,ImsBlackList> {
	/**
	 * 功能：分页查询
	 */
	public Map<String,Object> findPageList(ImsBlackListVO vo, Long companyId) {
		//分页及排序设置
		Query query = new Query().page(vo.getPage(),vo.getRows());
		this.setParams(query, vo);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("list", ImsBeanUtil.copyList(this.getDao().find(query),ImsBlackListVO.class));
        result.put("total", this.getDao().countTotal(query));
        return result;
	}

	/**
	 * 功能：根据id->查询信息
	 */
	public ImsBlackList findById(Long id) {
		return this.getDao().get(id);
	}
	
	/**
	 * 功能：根据id->查询信息
	 */
	public List<ImsBlackList> findListByMap(ImsBlackListVO vo) {
		Query query = Query.buildQueryAll();
		this.setParams(query, vo);
		return this.getDao().find(query);
	}
	
	private Query setParams(Query query, ImsBlackListVO vo){
		query.addSort(PageCustomizedUtil.getSortColumn(ImsBlackListVO.class, vo.getSort()), 
				"asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC  : Sort.DESC);
		//查询条件设置
		if(StringUtil.isNotBlank(vo.getAccountNo())){
			query.eq("account_no", vo.getAccountNo());
		}
		if(null != vo.getCompanyId()){
			query.eq("company_id", vo.getCompanyId());
		}
		return query;
	}
	
	/**
	 * 功能：保存全局黑名单
	 */
	public int saveBatch(Set<String> accounts,String ip,Long companyId) {
		Integer sum = 0;
		for(String accountNo : accounts){
			if(CollectionUtils.isEmpty(this.findListByMap(new ImsBlackListVO(accountNo, companyId)))){
				ImsBlackList  imsBlackList = new ImsBlackList();
				imsBlackList.setAccountNo(accountNo);
				imsBlackList.setCompanyId(companyId);
				imsBlackList.beforeSave();
				imsBlackList.setCreateIp(ip);
				imsBlackList.setUpdateIp(ip);
				imsBlackList.setEnableFlag("Y");
				imsBlackList.setDeleteFlag("N");
				imsBlackList.setUpdateDate(new Date());
				imsBlackList.setCreateDate(new Date());
				imsBlackList.setVersionNo(0);
				this.getDao().saveIgnoreNull(imsBlackList);
				sum ++;
			}
		}
		return sum;
	}
}