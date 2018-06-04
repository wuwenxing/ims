package com.gwghk.ims.datacleaning.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.ActPlatformEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.datacleaning.dao.entity.ActCustomerInfo;
import com.gwghk.unify.framework.cache.redis.cluster.RedisCacheTemplate;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: ActCustomerRedis
 * @Description: 账号信息缓存
 * @author lawrence
 * @date 2018年1月24日
 *
 */
@Component
public class ActCustomerRedis {
	private final static Logger logger = LoggerFactory.getLogger(ActCustomerRedis.class);
	private final static String CUSTOMER_CACHE = "CUSTOMER_CACHE:A_";

	@Autowired
	private RedisCacheTemplate redisCacheTemplate;

	private int expireTime = 60 * 60 * 24;

	public void cleanCustomerInfo(String env, String platform, String accountNo, String companyCode) {
		if (StringUtil.isEmpty(accountNo)) {
			return;
		}
		try {
			if (platform == null) {
				for (ActPlatformEnum actPlatform : ActPlatformEnum.values()) {
					redisCacheTemplate.del(env + CUSTOMER_CACHE + companyCode + actPlatform.getCode() + accountNo);
				}
			} else {
				redisCacheTemplate.del(env + CUSTOMER_CACHE + companyCode + platform + accountNo);
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			redisCacheTemplate.close();
		}
	}

	public void resetCustomerInfo(ActCustomerInfo actCustomerInfo) {
		if (actCustomerInfo == null) {
			return;
		}
		try {
			String customerValue = JsonUtil.obj2Str(actCustomerInfo);
			redisCacheTemplate.setex(
					actCustomerInfo.getEnv() + CUSTOMER_CACHE + CompanyEnum.getCodeById(actCustomerInfo.getCompanyId())
							+ actCustomerInfo.getPlatform() + actCustomerInfo.getAccountNo(),
							customerValue, expireTime);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			redisCacheTemplate.close();
		}
	}
}