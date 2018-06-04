package com.gwghk.ims.activity.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.dao.entity.ActCustomerInfoDemo;
import com.gwghk.ims.activity.dao.entity.ActCustomerInfoReal;
import com.gwghk.ims.activity.dao.inf.ActCustomerInfoDemoDao;
import com.gwghk.ims.activity.dao.inf.ActCustomerInfoRealDao;
import com.gwghk.ims.common.enums.ActAccountTypeEnum;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActCustomerInfoVO;
import com.gwghk.unify.framework.common.util.StringUtil;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Sort;

/**
 * 
 * 摘要：账户的信息Manager
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月12日
 */
@Component
@Transactional
public class ActCustomerInfoManager {

	@Autowired
	private ActCustomerInfoDemoDao actCustomerInfoDemoDao ;
	@Autowired
	private ActCustomerInfoRealDao actCustomerInfoRealDao ;

	/**
	 * 功能：根据账号和平台查询用户资料，不限制账号
	 * @param accountNo
	 * @param platform
	 * @return
	 */
	public ActCustomerInfo findByAccountNo(String accountNo,String platform) {
		if(StringUtils.isEmpty(accountNo) || StringUtils.isEmpty(platform)){
			return null ;
		}
		ActCustomerInfo actCustomerInfo = findByAccountNo(accountNo, platform, ActAccountTypeEnum.DEMO.getAliasCode()) ;
		if(null == actCustomerInfo){
			actCustomerInfo = findByAccountNo(accountNo, platform, ActAccountTypeEnum.REAL.getAliasCode()) ;
		}
		return actCustomerInfo ;
	}
	
	/**
	 * 功能：根据账号和平台查询用户资料
	 * @param accountNo 账号
	 * @param platform 平台
	 * @param accountType 账号类型demo或real
	 * @return
	 */
	public ActCustomerInfo findByAccountNo(String accountNo,String platform,String accountType) {
		if(StringUtils.isEmpty(accountNo) || StringUtils.isEmpty(platform)){
			return null ;
		}
		Query query = Query.build() ;
		query.eq("account_no", accountNo).eq("platform", platform) ;
		if(ActAccountTypeEnum.DEMO.getAliasCode().equals(accountType)){
			ActCustomerInfoDemo actCustomerInfoDemo = actCustomerInfoDemoDao.getByExpression(query) ;
			if(null != actCustomerInfoDemo){
				return ImsBeanUtil.copyNotNull(new ActCustomerInfo(), actCustomerInfoDemo) ;
			}
		}else if(ActAccountTypeEnum.REAL.getAliasCode().equals(accountType)){
			ActCustomerInfoReal actCustomerInfoReal = actCustomerInfoRealDao.getByExpression(query) ;
			if(null != actCustomerInfoReal){
				return ImsBeanUtil.copyNotNull(new ActCustomerInfo(), actCustomerInfoReal) ;
			}
		}
		return null ;
	}
	
	/**
	 * 功能：查询账号信息列表
	 * @param vo
	 * @return
	 */
	public List<ActCustomerInfo> findList(ActCustomerInfoVO vo){
		Query query = Query.build() ;
		this.setParams(query, vo) ;
		if(ActAccountTypeEnum.DEMO.getAliasCode().equals(vo.getAccountType())){
			return ImsBeanUtil.copyList(actCustomerInfoDemoDao.findAll(query),ActCustomerInfo.class) ;
       }else if(ActAccountTypeEnum.REAL.getAliasCode().equals(vo.getAccountType())){
    	   return ImsBeanUtil.copyList(actCustomerInfoRealDao.findAll(query),ActCustomerInfo.class) ;
       }
		return null ;
	}
	
	/**
	 * 功能：分页查询
	 */
	public Map<String,Object> findPageList(ActCustomerInfoVO vo) {
		//分页及排序设置
		Query query = new Query().page(vo.getPage(),vo.getRows());
		query.addSort(PageCustomizedUtil.getSortColumn(ActCustomerInfoVO.class,vo.getSort()),"asc".equalsIgnoreCase(vo.getOrder()) ? Sort.ASC  : Sort.DESC);
		//查询条件设置
		this.setParams(query, vo) ;
        Map<String,Object> result = new HashMap<String, Object>();
        if(ActAccountTypeEnum.DEMO.getAliasCode().equals(vo.getAccountType())){
        	 result.put("list", ImsBeanUtil.copyList(actCustomerInfoDemoDao.find(query),ActCustomerInfoVO.class));
             result.put("total", actCustomerInfoDemoDao.countTotal(query));
        }else if(ActAccountTypeEnum.REAL.getAliasCode().equals(vo.getAccountType())){
        	result.put("list", ImsBeanUtil.copyList(actCustomerInfoRealDao.find(query),ActCustomerInfoVO.class));
            result.put("total", actCustomerInfoRealDao.countTotal(query));
        }
        return result;
	}
	
	/**
	 * 功能：设置查询参数
	 */
	private Query setParams(Query query,ActCustomerInfoVO vo){
		if(StringUtil.isNotEmpty(vo.getAccountNo())){
			query.eq("account_no",vo.getAccountNo());
		}
		if(StringUtil.isNotEmpty(vo.getPlatform())){
			query.eq("platform", vo.getPlatform());
		}
		if(StringUtil.isNotEmpty(vo.getMobile())){
			query.eq("mobile",vo.getMobile());
		}
		if(StringUtil.isNotEmpty(vo.getEmail())){
			query.eq("email",vo.getEmail());
		}
		if(StringUtil.isNotEmpty(vo.getAccountStatus())){
			query.eq("account_status",vo.getAccountStatus());
		}
		if(StringUtil.isNotEmpty(vo.getActivatedStatus())){
			query.eq("activated_status",vo.getActivatedStatus());
		}
		if(StringUtil.isNotEmpty(vo.getRegisterStartTime())){
			query.ge("register_time",vo.getRegisterStartTime());
		}
		if(StringUtil.isNotEmpty(vo.getRegisterEndTime())){
			query.le("register_time",vo.getRegisterEndTime());
		}
		if(StringUtil.isNotEmpty(vo.getActivatedStartTime())){
			query.ge("activated_time",vo.getActivatedStartTime());
		}
		if(StringUtil.isNotEmpty(vo.getActivatedEndTime())){
			query.le("activated_time",vo.getActivatedEndTime());
		}
		if(vo.getCompanyId() != null){
			query.eq("company_id", vo.getCompanyId());
		}
		query.eq("delete_flag","N");
		return query;
	}
}
