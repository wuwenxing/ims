package com.gwghk.ims.marketingtool.manager;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgAppTplVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgAppTpl;
import com.gwghk.ims.marketingtool.dao.inf.ImsMsgAppTplDao;
import com.gwghk.unify.framework.common.util.StringUtil;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Sort;
import net.oschina.durcframework.easymybatis.support.CrudService;

/**
 * 摘要：App消息模板Manager
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月31日
 */
@Component
@Transactional
public class ImsMsgAppTplManager  extends CrudService<ImsMsgAppTplDao,ImsMsgAppTpl> {
	private static final Logger logger = LoggerFactory.getLogger(ImsMsgAppTplManager.class);
	
	/**
	 * 功能：分页查询
	 */
	public  Map<String,Object>  findPageList(ImsMsgAppTplVO vo) {
		//分页及排序设置
		Query query = new Query().page(vo.getPage(),vo.getRows());
		query.addSort(PageCustomizedUtil.getSortColumn(ImsMsgAppTplVO.class,vo.getSort()), "asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC  : Sort.DESC);
		//查询条件设置
		if(StringUtil.isNotEmpty(vo.getCode())){
			query.eq("code",vo.getCode());
		}
		if(StringUtil.isNotEmpty(vo.getLang())){
			query.eq("lang", vo.getLang());
		}
		if(StringUtil.isNotEmpty(vo.getTplName())){
			query.like("tpl_name","%"+vo.getTplName()+"%");
		}
		if(StringUtil.isNotEmpty(vo.getTplContent())){
			query.like("tpl_content","%"+vo.getTplContent()+"%");
		}
		if(StringUtil.isNotEmpty(vo.getEnableFlag())){
			query.eq("enable_flag",vo.getEnableFlag());
		}
		if(vo.getCompanyId() != null){
			query.eq("company_id", vo.getCompanyId());
		}
		query.eq("delete_flag","N");
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("list", ImsBeanUtil.copyList(this.getDao().find(query),ImsMsgAppTplVO.class));
        result.put("total", this.getDao().countTotal(query));
        return result;
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ImsMsgAppTpl findById(Long id) {
		return this.getDao().get(id);
	}
	
	/**
	 * 功能：根据code->获取信息
	 */
	public ImsMsgAppTpl findByCode(String code){
		return this.getDao().getByExpression(new Query().eq("code", code));
	}
	
	/**
	 * 功能：根据code->获取信息
	 */
	public List<ImsMsgAppTpl> findList(ImsMsgAppTplVO vo){
		Query query = new Query();
		query.addSort(PageCustomizedUtil.getSortColumn(ImsMsgAppTplVO.class,vo.getSort()), "asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC  : Sort.DESC);
		if(StringUtil.isNotEmpty(vo.getCode())){
			query.eq("code",vo.getCode());
		}
		if(StringUtil.isNotEmpty(vo.getLang())){
			query.eq("lang", vo.getLang());
		}
		if(StringUtil.isNotEmpty(vo.getTplName())){
			query.like("tpl_name",vo.getTplName());
		}
		if(StringUtil.isNotEmpty(vo.getTplContent())){
			query.like("tpl_content",vo.getTplContent());
		}
		if(StringUtil.isNotEmpty(vo.getEnableFlag())){
			query.eq("enable_flag",vo.getEnableFlag());
		}
		if(vo.getCompanyId() != null){
			query.eq("company_id", vo.getCompanyId());
		}
		query.eq("delete_flag","N");
		return this.getDao().find(query);
	}
	
	/**
	 * 功能：新增或修改时保存信息
	 */
	public MisRespResult<Void> saveOrUpdate(ImsMsgAppTpl tpl) throws Exception {
		if(this.isExsit(tpl.getCode(),tpl.getId())){
			logger.warn(MisResultCode.Msg40009.getMessage()+":"+tpl.getCode());
			return MisRespResult.error(MisResultCode.Msg40009);
		}
		if (null == tpl.getId()) {
			this.getDao().saveIgnoreNull(tpl);
		} else {
			ImsMsgAppTpl old = findById(tpl.getId());
			ImsBeanUtil.copyNotNull(old,tpl);
			old.setUpdateDate(new Date());
			this.getDao().updateIgnoreNull(old);
		}
		return MisRespResult.success() ;
	}

	/**
	 * 功能：批量删除信息
	 */
	public int deleteByIds(String ids) {
		return this.getDao().delByExpression(new Query().in("id", Arrays.asList(ids.split(","))));
	}
	
	/**
	 * 功能：检查key是否存在
	 */
	private boolean isExsit(String code,Long id) {
		Query query = new Query();
		if(StringUtil.isNotEmpty(code)){
			query.eq("code",code);
		}
		if(id != null){
			query.notEq("id",id);
		}
		return CollectionUtils.isNotEmpty(this.getDao().find(query)) ? true : false;
	}
}