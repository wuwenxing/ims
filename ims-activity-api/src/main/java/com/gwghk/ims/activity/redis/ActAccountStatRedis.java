package com.gwghk.ims.activity.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.unify.framework.cache.redis.cluster.RedisCacheTemplate;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 
* @ClassName: ActAccountStatRedis
* @Description: 处理用户活动统计通知
* @author lawrence
* @date 2018年4月16日
*
 */
@Component
public class ActAccountStatRedis {
	private final static Logger logger = LoggerFactory.getLogger(ActAccountStatRedis.class);
	private final static String ACT_ACCOUNT_STAT_ADD = "ACT_ACCOUNT_STAT:A_";
	

	private Map<String, List<String>> tmp = new HashMap<String, List<String>>();

	@Autowired
	private RedisCacheTemplate redisCacheTemplate;

	private String gddSize = "50";

	@PostConstruct
	public void init() {
		for (CompanyEnum actCompany : CompanyEnum.getList()) {
			tmp.put(actCompany.getCode(), new ArrayList<String>());
		}
	}

	public void rPushAccount(Map<String,Object> dataParams, String companyCode) {
		try {
			if (dataParams == null) {
				return;
			}
			redisCacheTemplate.rpush(ACT_ACCOUNT_STAT_ADD + companyCode, JsonUtil.obj2Str(dataParams));
		} catch (Exception ex) {
			List<String> listGenerateAccount = tmp.get(companyCode);
			if (listGenerateAccount == null) {
				listGenerateAccount = new ArrayList<String>();
				tmp.put(companyCode, listGenerateAccount);
			}
			listGenerateAccount.add(JsonUtil.obj2Str(dataParams));
		} finally {
			redisCacheTemplate.close();
		}
	}


	public List<String> popAccount(String companyCode) {
		return popAccount(null, companyCode);
	}



	public List<String> popAccount(Integer max, String companyCode) {
		List<String> data = new ArrayList<String>();
		Set<String> account = new HashSet<String>();
		max = (max == null ? Integer.valueOf(gddSize) : max);
		try {
			// 判断redis中是否有数据，没数据直接返回
			long total = redisCacheTemplate.llen(ACT_ACCOUNT_STAT_ADD + companyCode);
			if (0 == total) {
				return data;
			}
			long curSysnSize = total > max ? max : total;
			// CountDownLatch latch = new CountDownLatch((int) curSysnSize);
			for (int i = 0; i < curSysnSize; i++) {
				String msg = redisCacheTemplate.lpop(ACT_ACCOUNT_STAT_ADD + companyCode);
				if(msg!=null){
					account.add(msg);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			redisCacheTemplate.close();
		}
		if (tmp.get(companyCode) != null && !tmp.get(companyCode).isEmpty()) {
			account.addAll(tmp.get(companyCode));
			tmp.get(companyCode).clear();
		}
		data.addAll(account);
		return data;
	}

}
