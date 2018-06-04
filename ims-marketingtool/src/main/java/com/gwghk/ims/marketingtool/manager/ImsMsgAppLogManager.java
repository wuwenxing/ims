package com.gwghk.ims.marketingtool.manager;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgAppLogVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgAppLog;
import com.gwghk.ims.marketingtool.dao.inf.ImsMsgAppLogDao;
import com.gwghk.ims.marketingtool.enums.AppPushMsgStatusEnum;
import com.gwghk.unify.framework.common.util.StringUtil;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Sort;
import net.oschina.durcframework.easymybatis.support.CrudService;

/**
 * 摘要：App消息记录Manager
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月31日
 */
@Component
@Transactional
public class ImsMsgAppLogManager extends CrudService<ImsMsgAppLogDao,ImsMsgAppLog> {
	
	/**
	 * 功能：分页查询
	 */
	public Map<String,Object> findPageList(ImsMsgAppLogVO vo) {
		//分页及排序设置
		Query query = new Query().page(vo.getPage(),vo.getRows());
		query.addSort(PageCustomizedUtil.getSortColumn(ImsMsgAppLogVO.class,vo.getSort()),"asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC  : Sort.DESC);
		//查询条件设置
		if(StringUtil.isNotEmpty(vo.getRecipents())){
			query.eq("recipents",vo.getRecipents());
		}
		if(StringUtil.isNotEmpty(vo.getTitle())){
			query.like("title", "%"+vo.getTitle()+"%");
		}
		if(StringUtil.isNotEmpty(vo.getContent())){
			query.like("content","%"+vo.getContent()+"%");
		}
		if(StringUtil.isNotEmpty(vo.getStatus())){
			query.eq("status",vo.getStatus());
		}
		if(StringUtil.isNotEmpty(vo.getStartTimeStr())){
			query.ge("send_time",vo.getStartTimeStr());
		}
		if(StringUtil.isNotEmpty(vo.getEndTimeStr())){
			query.le("send_time",vo.getEndTimeStr());
		}
		if(vo.getCompanyId() != null){
			query.eq("company_id", vo.getCompanyId());
		}
		query.eq("delete_flag","N");
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("list", ImsBeanUtil.copyList(this.getDao().find(query),ImsMsgAppLogVO.class));
        result.put("total", this.getDao().countTotal(query));
        return result;
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ImsMsgAppLog findById(Integer id) {
		return this.getDao().get(id);
	}
	
	/**
	 * 功能：根据信息列表
	 */
	public List<ImsMsgAppLog> findList(ImsMsgAppLogVO vo) {
		Query query = new Query();
		query.addSort(PageCustomizedUtil.getSortColumn(ImsMsgAppLogVO.class,vo.getSort()), "asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC  : Sort.DESC);
		if(StringUtil.isNotEmpty(vo.getContent())){
			query.eq("code",vo.getContent());
		}
		if(StringUtil.isNotEmpty(vo.getRecipents())){
			query.eq("recipents",vo.getRecipents());
		}
		if(StringUtil.isNotEmpty(vo.getExt1())){
			query.eq("ext1",vo.getExt1());
		}
		if(StringUtil.isNotEmpty(vo.getStartTimeStr())){
			query.ge("create_date",vo.getStartTimeStr());
		}
		if(StringUtil.isNotEmpty(vo.getEndTimeStr())){
			query.le("create_date",vo.getEndTimeStr());
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
	public MisRespResult<Void> saveOrUpdate(ImsMsgAppLog vo) throws Exception {
		if (null == vo.getId()) {
			vo.setStatus(AppPushMsgStatusEnum.waiting.getValue());
			this.getDao().saveIgnoreNull(vo);
		} else {
			ImsMsgAppLog old = findById(vo.getId());
			ImsBeanUtil.copyNotNull(old, vo);
			old.setUpdateDate(new Date());
			this.getDao().updateIgnoreNull(old);
		}
		return MisRespResult.success() ;
	}

	/**
	 * 功能：批量删除
	 */
	public int deleteByIds(String ids) {
		return this.getDao().delByExpression(new Query().in("id", Arrays.asList(ids.split(","))));
	}
	
	/**
	 * 功能：查询待发送的消息列表
	 */
	public List<ImsMsgAppLog> findWaitingSendList(Long companyId){
		return this.getDao().findWaitingSendList(companyId);
	}
}