package com.gwghk.ims.activity.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ActTradeRecord;
import com.gwghk.ims.activity.dao.inf.ActTradeRecordDemoDao;
import com.gwghk.ims.activity.dao.inf.ActTradeRecordRealDao;
import com.gwghk.ims.common.enums.ActAccountTypeEnum;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActCustomerInfoVO;
import com.gwghk.ims.common.vo.activity.ActTradeRecordVO;
import com.gwghk.unify.framework.common.util.StringUtil;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Sort;

/**
 * 摘要：交易记录数据处理
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月24日
 */
@Component
public class ActTradeRecordManager {

	@Autowired
	private ActTradeRecordRealDao actTradeRecordRealDao ;
	@Autowired
	private ActTradeRecordDemoDao actTradeRecordDemoDao ;
	
	/**
	 * 功能：查询分页列表
	 */
	public Map<String, Object> findPageList(ActTradeRecordVO vo, Long companyId) {
		//分页及排序设置
		Query query = new Query().page(vo.getPage(),vo.getRows());
		query.addSort(PageCustomizedUtil.getSortColumn(ActCustomerInfoVO.class,vo.getSort()),"asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC  : Sort.DESC);
		//查询条件设置
		this.setParams(query, vo) ;
        Map<String,Object> result = new HashMap<String, Object>();
        if(ActAccountTypeEnum.DEMO.getAliasCode().equals(vo.getEnv())){
        	 result.put("list", ImsBeanUtil.copyList(actTradeRecordDemoDao.find(query),ActCustomerInfoVO.class));
             result.put("total", actTradeRecordDemoDao.countTotal(query));
        }else if(ActAccountTypeEnum.REAL.getAliasCode().equals(vo.getEnv())){
        	result.put("list", ImsBeanUtil.copyList(actTradeRecordRealDao.find(query),ActCustomerInfoVO.class));
            result.put("total", actTradeRecordRealDao.countTotal(query));
        }
        return result;
	}

	/**
	 * 功能：查询列表
	 */
	public List<ActTradeRecord> findList(ActTradeRecordVO vo) {
		Query query = Query.buildQueryAll();
		query.addSort(PageCustomizedUtil.getSortColumn(ActTradeRecordVO.class,vo.getSort()),"asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC  : Sort.DESC);
		this.setParams(query, vo);
		if(ActAccountTypeEnum.DEMO.getAliasCode().equals(vo.getEnv())){
			return ImsBeanUtil.copyList(actTradeRecordDemoDao.findAll(query),ActTradeRecord.class) ;
       }else if(ActAccountTypeEnum.REAL.getAliasCode().equals(vo.getEnv())){
    	   return ImsBeanUtil.copyList(actTradeRecordRealDao.findAll(query),ActTradeRecord.class);
       }
		return null ;
	}
	
	/**
	 * 功能：设置查询参数
	 */
	private Query setParams(Query query,ActTradeRecordVO vo){
		if(StringUtil.isNotBlank(vo.getAccountNo())){
			query.eq("account_no",vo.getAccountNo());
		}
		if(StringUtil.isNotBlank(vo.getProduct())){
			query.in("product", vo.getProduct().split(","));
		}
		if(StringUtil.isNotBlank(vo.getPlatform())){
			query.eq("platform",vo.getPlatform());
		}
		if(null != vo.getPositionId()){
			query.eq("position_id",vo.getPositionId());
		}
		if(StringUtil.isNotBlank(vo.getTradeType())){
			query.eq("trade_type",vo.getTradeType());
		}
		if(vo.getTradeStartTime() != null){
			query.ge("trade_time",vo.getTradeStartTime ());
		}
		if(vo.getTradeEndTime() != null){
			query.le("trade_time",vo.getTradeEndTime());
		}
		if(vo.getCompanyId() != null){
			query.eq("company_id",vo.getCompanyId());
		}
		if(vo.getProfit() != null){
			if(vo.getProfit().doubleValue() > 0){
				query.ge("profit",vo.getProfit());
			}else{
				query.le("profit",vo.getProfit());
			}
		}
		return query;
	}
}