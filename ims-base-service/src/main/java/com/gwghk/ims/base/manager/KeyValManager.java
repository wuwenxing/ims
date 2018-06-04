package com.gwghk.ims.base.manager;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.base.dao.entity.KeyVal;
import com.gwghk.ims.base.dao.inf.KeyValDao;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.base.KeyValVO;
import com.gwghk.unify.framework.common.util.StringUtil;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Sort;
import net.oschina.durcframework.easymybatis.support.CrudService;

/**
 * 摘要：键值对业务逻辑处理
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月09日
 */
@Component
@Transactional
public class KeyValManager extends CrudService<KeyValDao,KeyVal> {
	private static final Logger logger = LoggerFactory.getLogger(KeyValManager.class);
	
	/**
	 * 功能：分页查询
	 */
	public Map<String,Object> findPageList(KeyValVO keyValVO,Long companyId) {
		//分页及排序设置
		Query query = new Query().page(keyValVO.getPage(),keyValVO.getRows());
		query.addSort(PageCustomizedUtil.getSortColumn(KeyValVO.class,keyValVO.getSort()),"asc".equalsIgnoreCase(keyValVO.getOrder()) ? Sort.ASC  : Sort.DESC);
		//查询条件设置
		if(StringUtil.isNotBlank(keyValVO.getDataKey())){
			query.like("data_key", "%"+keyValVO.getDataKey()+"%");
		}
		if(StringUtil.isNotBlank(keyValVO.getDataVal())){
			query.like("data_val",  "%"+keyValVO.getDataVal()+"%");
		}
		if(StringUtil.isNotBlank(keyValVO.getTag())){
			query.like("tag",  "%"+keyValVO.getTag()+"%");
		}
		if(StringUtil.isNotBlank(keyValVO.getEnableFlag())){
			query.eq("enable_flag", keyValVO.getEnableFlag());
		}
		if(StringUtil.isNotBlank(keyValVO.getRemark())){
			query.like("remark", "%"+keyValVO.getRemark()+"%");
		}
		if(companyId != null){
			query.eq("company_id", companyId);
		}
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("list", ImsBeanUtil.copyList(this.getDao().find(query),KeyValVO.class));
        result.put("total", this.getDao().countTotal(query));
        return result;
	}

	/**
	 * 功能：根据键值对id->查询键值对信息
	 */
	public KeyVal findById(Long id) {
		return this.getDao().get(id);
	}

	/**
	 * 功能：根据键->查询键值对信息
	 */
	@Cacheable(value="keyValCache", key="'keyAndCompanyId:' + #key + '_' + #companyId")
	public KeyVal findByKey(String key, Long companyId) {
		Query query = new Query();
		if(StringUtil.isNotBlank(key)){
			query.eq("data_key",key);
		}
		if(companyId != null){
			query.eq("company_id",companyId);
		}
		return this.getDao().getByExpression(query);
	}
	
	/**
	 * 功能：新增或修改
	 */
	public MisRespResult<Void> saveOrUpdate(KeyValVO keyValVO,Long companyId) {
		try{
			if(this.isExsit(keyValVO.getDataKey(),keyValVO.getId(),companyId)){
				logger.warn(MisResultCode.Base60001.getMessage()+":"+keyValVO.getDataKey());
				return MisRespResult.error(MisResultCode.Base60001);
			}
			if (null == keyValVO.getId()) {
				this.getDao().saveIgnoreNull(ImsBeanUtil.copyNotNull(new KeyVal(),keyValVO));
			} else {
				KeyVal oldKeyVal = findById(keyValVO.getId());
				ImsBeanUtil.copyNotNull(oldKeyVal,keyValVO);
				oldKeyVal.setUpdateDate(new Date());
				this.getDao().updateIgnoreNull(oldKeyVal);
			}
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常,【saveOrUpdate】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：批量删除
	 */
	public int deleteByIds(String ids) {
		return this.getDao().delByExpression(new Query().in("id", Arrays.asList(ids.split(","))));
	}

	/**
	 * 功能：检查key是否存在
	 */
	private boolean isExsit(String dateKey,Long id,Long companyId) {
		Query query = new Query();
		if(StringUtil.isNotBlank(dateKey)){
			query.eq("data_key",dateKey);
		}
		if(companyId != null){
			query.eq("company_id",companyId);
		}
		if(id != null){
			query.notEq("id",id);
		}
		return CollectionUtils.isNotEmpty(this.getDao().find(query)) ? true : false;
	}
}