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
import com.gwghk.ims.common.vo.marketingtool.ImsMsgSmsTplVO;
import com.gwghk.ims.common.vo.marketingtool.ImsRechargeLogDetailVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsRechargeLogDetail;
import com.gwghk.ims.marketingtool.dao.inf.ImsRechargeLogDetailDao;
import com.gwghk.unify.framework.common.util.StringUtil;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Sort;
import net.oschina.durcframework.easymybatis.support.CrudService;

/**
 * 流量、话费充值记录
 * @author wayne
 */
@Component
@Transactional
public class ImsRechargeLogDetailManager extends CrudService<ImsRechargeLogDetailDao,ImsRechargeLogDetail> {

	/**
	 * 功能：分页查询
	 */
	public Map<String,Object> findPageList(ImsRechargeLogDetailVO vo) {
		//分页及排序设置
		Query query = new Query().page(vo.getPage(),vo.getRows());
		this.setParams(query, vo);
		Map<String,Object> result = new HashMap<String, Object>();
        result.put("list", ImsBeanUtil.copyList(this.getDao().find(query),ImsRechargeLogDetailVO.class));
        result.put("total", this.getDao().countTotal(query));
        return result;
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ImsRechargeLogDetail> findList(ImsRechargeLogDetailVO vo) {
		Query query = Query.buildQueryAll();
		this.setParams(query, vo);
		return this.getDao().find(query);
	}

	private Query setParams(Query query, ImsRechargeLogDetailVO vo){
		query.addSort(PageCustomizedUtil.getSortColumn(ImsMsgSmsTplVO.class,vo.getSort()), "asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC  : Sort.DESC);
		if(StringUtil.isNotEmpty(vo.getRechargeType())){
			query.eq("recharge_type",vo.getRechargeType());
		}
		if(StringUtil.isNotEmpty(vo.getInterfaceType())){
			query.eq("interface_type",vo.getInterfaceType());
		}
		if(StringUtil.isNotEmpty(vo.getResCode())){
			query.like("res_code","%"+vo.getResCode()+"%");
		}
		if(StringUtil.isNotEmpty(vo.getResBatchNo())){
			query.like("res_batch_no","%"+vo.getResBatchNo()+"%");
		}
		if(StringUtil.isNotEmpty(vo.getCommitStatus())){
			query.eq("commit_status",vo.getCommitStatus());
		}
		if(StringUtil.isNotEmpty(vo.getSendStatus())){
			query.eq("send_status",vo.getSendStatus());
		}
		if(StringUtil.isNotEmpty(vo.getPhone())){
			query.eq("phone",vo.getPhone());
		}
		if(StringUtil.isNotEmpty(vo.getPhoneArea())){
			query.like("phone_area","%"+vo.getPhoneArea()+"%");
		}
		if(StringUtil.isNotEmpty(vo.getStartTimeStr())){
			query.ge("create_date",vo.getStartTimeStr());
		}
		if(StringUtil.isNotEmpty(vo.getEndTimeStr())){
			query.le("create_date",vo.getEndTimeStr());
		}
		if(StringUtil.isNotEmpty(vo.getExt1())){
			query.eq("ext1",vo.getExt1());
		}
		if(StringUtil.isNotEmpty(vo.getExt2())){
			query.eq("ext2",vo.getExt2());
		}
		if(StringUtil.isNotEmpty(vo.getExt2())){
			query.eq("ext3",vo.getExt3());
		}
		if(vo.getCompanyId() != null){
			query.eq("company_id",vo.getCompanyId());
		}
		query.eq("delete_flag","N");
		return query;
	}
	
	
	/**
	 * 功能：根据id->获取信息
	 */
	public ImsRechargeLogDetail findById(Long id) {
		return this.getDao().get(id);
	}
	
	/**
	 * 功能：新增或修改时保存信息
	 */
	public MisRespResult<Void> saveOrUpdate(ImsRechargeLogDetailVO vo){
		if (null == vo.getDetailId()) {
			this.getDao().saveIgnoreNull(ImsBeanUtil.copyNotNull(new ImsRechargeLogDetail(), vo));
		} else {
			ImsRechargeLogDetail old = findById(vo.getDetailId());
			ImsBeanUtil.copyNotNull(old, vo);
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
}