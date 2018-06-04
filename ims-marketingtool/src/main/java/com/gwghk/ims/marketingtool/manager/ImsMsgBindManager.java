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
import com.gwghk.ims.common.vo.marketingtool.ImsMsgBindVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgBind;
import com.gwghk.ims.marketingtool.dao.inf.ImsMsgBindDao;
import com.gwghk.unify.framework.common.util.StringUtil;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Sort;
import net.oschina.durcframework.easymybatis.support.CrudService;

/**
 * 
 * 摘要：消息模板绑定Manager
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月31日
 */
@Component
@Transactional
public class ImsMsgBindManager extends CrudService<ImsMsgBindDao,ImsMsgBind>  {

	Logger logger = LoggerFactory.getLogger(ImsMsgBindManager.class) ;
	
	/**
	 * 功能：分页查询
	 */
	public Map<String,Object> findPageList(ImsMsgBindVO vo) {
		//分页及排序设置
		Query query = new Query().page(vo.getPage(),vo.getRows());
		query.addSort(PageCustomizedUtil.getSortColumn(ImsMsgBindVO.class,vo.getSort()), "asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC  : Sort.DESC);
		//查询条件设置
		if(StringUtil.isNotEmpty(vo.getBindCode())){
			query.eq("bind_code", vo.getBindCode());
		}
		if(StringUtil.isNotEmpty(vo.getBindType())){
			query.eq("bind_type",vo.getBindType());
		}
		if(StringUtil.isNotEmpty(vo.getSmsCode())){
			query.eq("sms_code",vo.getSmsCode());
		}
		if(StringUtil.isNotEmpty(vo.getAppCode())){
			query.eq("app_code",vo.getAppCode());
		}
		if(StringUtil.isNotEmpty(vo.getEnableFlag())){
			query.eq("enable_flag",vo.getEnableFlag());
		}
		if(vo.getCompanyId() != null){
			query.eq("company_id", vo.getCompanyId());
		}
		query.eq("delete_flag","N");
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("list", ImsBeanUtil.copyList(this.getDao().find(query),ImsMsgBindVO.class));
        result.put("total", this.getDao().countTotal(query));
        return result;
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ImsMsgBind> findList(ImsMsgBindVO vo) {
		Query query = new Query();
		if(StringUtil.isNotEmpty(vo.getBindCode())){
			query.eq("bind_code", vo.getBindCode());
		}
		if(StringUtil.isNotEmpty(vo.getBindType())){
			query.eq("bind_type",vo.getBindType());
		}
		if(StringUtil.isNotEmpty(vo.getSmsCode())){
			query.eq("sms_code",vo.getSmsCode());
		}
		if(StringUtil.isNotEmpty(vo.getAppCode())){
			query.eq("app_code",vo.getAppCode());
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
	 * 功能：根据code->获取信息
	 */
	public ImsMsgBind findByBindCode(String bindCode){
		return this.getDao().getByProperty("bind_code",bindCode);
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ImsMsgBind findById(Long id) {
		return this.getDao().get(id);
	}
	
	/**
	 * 功能：新增或修改时保存信息
	 */
	public MisRespResult<Void> saveOrUpdate(ImsMsgBindVO vo) throws Exception {
		if(this.isExsit(vo.getBindCode(),vo.getId())){
			logger.warn(MisResultCode.Msg40009.getMessage()+":"+vo.getBindCode());
			return MisRespResult.error(MisResultCode.Msg40011);
		}
		if (null == vo.getId()) {
			this.getDao().save(ImsBeanUtil.copyNotNull(new ImsMsgBind(), vo));
		} else {
			ImsMsgBind old = findById(vo.getId());
			ImsBeanUtil.copyNotNull(old, vo);
			old.setUpdateDate(new Date());
			this.getDao().update(old);
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
			query.eq("bind_code",code);
		}
		if(id != null){
			query.notEq("id",id);
		}
		return CollectionUtils.isNotEmpty(this.getDao().find(query)) ? true : false;
	}
}