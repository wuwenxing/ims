package com.gwghk.ims.message.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.common.CacheKeyConstant;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.ims.message.dao.entity.Gts2AccountGroup;
import com.gwghk.ims.message.dao.entity.Gts2Group;
import com.gwghk.ims.message.dao.inf.Gts2AccountGroupDao;
import com.gwghk.ims.message.dao.inf.Gts2GroupDao;

/**
 * 
 * @ClassName: InitDataManager
 * @Description: 初始化数据
 * @author lawrence
 * @date 2017年8月18日
 *
 */
@Component
public class InitDataManager {
	private final static Logger logger = LoggerFactory.getLogger(InitDataManager.class);

	@Autowired
	private Gts2AccountGroupDao gts2AccountGroupDao;

	@Autowired
	private Gts2GroupDao gts2GroupDao;

	private Map<String, Long> gts2RealAccountGroup = new ConcurrentHashMap<String, Long>();
	private Map<String, Long> gts2DemoAccountGroup = new ConcurrentHashMap<String, Long>();

	private Map<String, Long> gts2RealDealGroup = new ConcurrentHashMap<String, Long>();
	private Map<String, Long> gts2DemoDealGroup = new ConcurrentHashMap<String, Long>();

	@PostConstruct
	public void start() {

		try {

			DataSourceContext.setCompany(DataSourceTypeEnum.BASE_DATA,null);
			// 平台组别初始化数据
			List<Gts2AccountGroup> realGts2AccountGroup = gts2AccountGroupDao.select(ActEnvEnum.REAL.getValue());
			List<Gts2AccountGroup> demoGts2AccountGroup = gts2AccountGroupDao.select(ActEnvEnum.DEMO.getValue());

			if (realGts2AccountGroup != null && !realGts2AccountGroup.isEmpty()) {
				for (Gts2AccountGroup gts2AccountGroup : realGts2AccountGroup) {
					gts2RealAccountGroup.put(CacheKeyConstant.GTS2_REAL_ACCOUNT_GROUP_ID + gts2AccountGroup.getId(),
							gts2AccountGroup.getCompanyId());
				}
			}

			if (demoGts2AccountGroup != null && !demoGts2AccountGroup.isEmpty()) {
				for (Gts2AccountGroup gts2AccountGroup : demoGts2AccountGroup) {
					gts2DemoAccountGroup.put(CacheKeyConstant.GTS2_DEMO_ACCOUNT_GROUP_ID + gts2AccountGroup.getId(),
							gts2AccountGroup.getCompanyId());
				}
			}
			logger.info("平台组别信息初始化数据成功");

			// 交易组别初始化数据
			List<Gts2Group> realGts2DealGroup = gts2GroupDao.select(ActEnvEnum.REAL.getValue());
			List<Gts2Group> demoGts2DealGroup = gts2GroupDao.select(ActEnvEnum.DEMO.getValue());

			if (realGts2DealGroup != null && !realGts2DealGroup.isEmpty()) {
				for (Gts2Group gts2Group : realGts2DealGroup) {
					gts2RealDealGroup.put(CacheKeyConstant.GTS2_REAL_DEAL_GROUP_ID + gts2Group.getId(),
							new Long(gts2Group.getCompanyid()));
				}
			}

			if (demoGts2DealGroup != null && !demoGts2DealGroup.isEmpty()) {
				for (Gts2Group gts2Group : demoGts2DealGroup) {
					gts2DemoDealGroup.put(CacheKeyConstant.GTS2_DEMO_DEAL_GROUP_ID + gts2Group.getId(),
							new Long(gts2Group.getCompanyid()));
				}
			}

			logger.info("交易组别信息初始化数据成功");
		} catch (Exception e) {
			logger.error("平台组别信息初始化数据失败:{}" + e.getMessage());
		}
	}

	public Map<String, Long> getGts2RealAccountGroup() {
		return gts2RealAccountGroup;
	}

	public Map<String, Long> getGts2DemoAccountGroup() {
		return gts2DemoAccountGroup;
	}

	public Map<String, Long> getGts2RealDealGroup() {
		return gts2RealDealGroup;
	}

	public Map<String, Long> getGts2DemoDealGroup() {
		return gts2DemoDealGroup;
	}

	/**
	 * 
	 * @MethodName: resetGts2RealAccountGroup
	 * @Description: 重置real客户组公司ID
	 * @param groupId
	 * @param companyId
	 * @return void
	 */
	public void resetGts2RealAccountGroup(Long groupId, Long companyId) {
		gts2RealAccountGroup.put(CacheKeyConstant.GTS2_REAL_ACCOUNT_GROUP_ID + groupId, companyId);
	}

	/**
	 * 
	 * @MethodName: resetGts2DemoAccountGroup
	 * @Description: 重置demo客户组公司ID
	 * @param groupId
	 * @param companyId
	 * @return void
	 */
	public void resetGts2DemoAccountGroup(Long groupId, Long companyId) {
		gts2RealAccountGroup.put(CacheKeyConstant.GTS2_REAL_ACCOUNT_GROUP_ID + groupId, companyId);
	}

	/**
	 * 
	 * @MethodName: resetGts2RealDealGroup
	 * @Description: 重置real交易组公司ID
	 * @param groupId
	 * @param companyId
	 * @return void
	 */
	public void resetGts2RealDealGroup(Long groupId, Long companyId) {
		gts2RealDealGroup.put(CacheKeyConstant.GTS2_REAL_DEAL_GROUP_ID + groupId, companyId);
	}

	/**
	 * 
	 * @MethodName: resetGts2DemoDealGroup
	 * @Description: 重置demo交易组公司ID
	 * @param groupId
	 * @param companyId
	 * @return void
	 */
	public void resetGts2DemoDealGroup(Long groupId, Long companyId) {
		gts2DemoDealGroup.put(CacheKeyConstant.GTS2_REAL_DEAL_GROUP_ID + groupId, companyId);
	}
	
	/**
	 * 
	 * @MethodName: getGts2DealGroupCompanyId
	 * @Description: 获取交易组的公司ID
	 * @param groupId
	 * @param env
	 * @return
	 * @return Long
	 * @throws Exception 
	 */
	public Long getGts2DealGroupCompanyId(Long groupId, String env) throws Exception {
		Long companyId = null;
		if (ActEnvEnum.REAL.getValue().equals(env)) {
			companyId = getGts2RealDealGroup().get(CacheKeyConstant.GTS2_REAL_DEAL_GROUP_ID + groupId);
		} else if (ActEnvEnum.DEMO.getValue().equals(env)) {
			companyId = getGts2DemoDealGroup().get(CacheKeyConstant.GTS2_DEMO_DEAL_GROUP_ID + groupId);
		}
		if (companyId == null) {
			DataSourceContext.setCompany(DataSourceTypeEnum.BASE_DATA,null);
			Gts2Group params = new Gts2Group();
			params.setEnv(env);
			params.setId(groupId);
			Gts2Group gts2Group = gts2GroupDao.findObject(params);
			if (gts2Group != null) {
				companyId = gts2Group.getCompanyid();
				if (ActEnvEnum.REAL.getValue().equals(env)) {
					resetGts2RealDealGroup(groupId, companyId);
				} else if (ActEnvEnum.DEMO.getValue().equals(env)) {
					resetGts2DemoDealGroup(groupId, companyId);
				}
			}
		}
		return companyId;
	}
	
	/**
	 * 
	 * @MethodName: getGts2AccountGroupCompanyId
	 * @Description: 获取客户组的公司ID
	 * @param accountGroupId
	 * @param env
	 * @return
	 * @throws Exception
	 * @return Long
	 */
	public Long getGts2AccountGroupCompanyId(Long accountGroupId, String env) throws Exception {
		Long companyId = null;
		if (ActEnvEnum.REAL.getValue().equals(env)) {
			companyId = getGts2RealAccountGroup().get(CacheKeyConstant.GTS2_REAL_ACCOUNT_GROUP_ID + accountGroupId);
		} else if (ActEnvEnum.DEMO.getValue().equals(env)) {
			companyId = getGts2RealAccountGroup().get(CacheKeyConstant.GTS2_DEMO_ACCOUNT_GROUP_ID + accountGroupId);
		}
		if (companyId == null) {
			DataSourceContext.setCompany(DataSourceTypeEnum.BASE_DATA,null);
			Gts2AccountGroup params = new Gts2AccountGroup();
			params.setEnv(env);
			params.setId(accountGroupId);
			Gts2Group gts2Group = gts2GroupDao.findObject(params);
			if (gts2Group != null) {
				companyId = gts2Group.getCompanyid();
				if (ActEnvEnum.REAL.getValue().equals(env)) {
					resetGts2RealAccountGroup(accountGroupId, companyId);
				} else if (ActEnvEnum.DEMO.getValue().equals(env)) {
					resetGts2DemoAccountGroup(accountGroupId, companyId);
				}
			}
		}
		return companyId;
	}
	
}
