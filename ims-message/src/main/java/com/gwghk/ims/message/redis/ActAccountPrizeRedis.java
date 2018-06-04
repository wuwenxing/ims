package com.gwghk.ims.message.redis;

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
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: ActAccountPrizeRedis
 * @Description: 新生成的发放记录账号列表临时存储
 * @author lawrence
 * @date 2017年12月20日
 *
 */
@Component
public class ActAccountPrizeRedis {
	private final static Logger logger = LoggerFactory.getLogger(ActAccountPrizeRedis.class);
	private final static String ACT_ACCOUNT_PRIZE_ADD = "ACT_ACCOUNT_PRIZE:A_";
	private final static String ACT_DEMO_ACCOUNT_PRIZE_ADD = "ACT_DEMO_ACCOUNT_PRIZE:A_";

	private final static String ACT_ACCOUNT_TRADE_ADD = "ACT_ACCOUNT_TRADE:A_";

	private Map<String, List<String>> tmp = new HashMap<String, List<String>>();

	private Map<String, List<String>> tmpDemo = new HashMap<String, List<String>>();

	private Map<String, List<String>> tmpTrade = new HashMap<String, List<String>>();

	@Autowired
	private RedisCacheTemplate redisCacheTemplate;

	private String gddSize = "50";

	@PostConstruct
	public void init() {
		for (CompanyEnum actCompany : CompanyEnum.getList()) {
			tmp.put(actCompany.getCode(), new ArrayList<String>());
			tmpDemo.put(actCompany.getCode(), new ArrayList<String>());
			tmpTrade.put(actCompany.getCode(), new ArrayList<String>());

		}
	}

	public void rPushAccount(String accountNo, String companyCode) {
		try {
			if (StringUtil.isEmpty(accountNo)) {
				return;
			}
			logger.info("{}发放记录推送指定账号:{}", companyCode, accountNo);
			redisCacheTemplate.rpush(ACT_ACCOUNT_PRIZE_ADD + companyCode, accountNo);
		} catch (Exception ex) {
			List<String> listGenerateAccount = tmp.get(companyCode);
			if (listGenerateAccount == null) {
				listGenerateAccount = new ArrayList<String>();
				tmp.put(companyCode, listGenerateAccount);
			}
			listGenerateAccount.add(accountNo);
		} finally {
			redisCacheTemplate.close();
		}
	}

	public void rPushDemoAccount(String accountNo, String companyCode) {
		try {
			if (StringUtil.isEmpty(accountNo)) {
				return;
			}
			logger.info("{}发放记录推送指定账号:{}", companyCode, accountNo);
			redisCacheTemplate.rpush(ACT_DEMO_ACCOUNT_PRIZE_ADD + companyCode, accountNo);
		} catch (Exception ex) {
			List<String> listGenerateAccount = tmpDemo.get(companyCode);
			if (listGenerateAccount == null) {
				listGenerateAccount = new ArrayList<String>();
				tmp.put(companyCode, listGenerateAccount);
			}
			listGenerateAccount.add(accountNo);
		} finally {
			redisCacheTemplate.close();
		}
	}

	public void rPushAccount(List<String> accountNoList, String companyCode) {
		if (accountNoList == null || accountNoList.isEmpty()) {
			return;
		}
		Set<String> account = new HashSet<String>();
		for (String accountNoItem : accountNoList) {
			if (StringUtil.isEmpty(accountNoItem)) {
				continue;
			}
			account.add(accountNoItem);
		}
		try {
			int countIndex = 0;
			for (String accountNoItem : account) {
				redisCacheTemplate.rpush(ACT_ACCOUNT_PRIZE_ADD + companyCode, accountNoItem);
				countIndex++;
				if (countIndex % 50 == 0) {
					Thread.sleep(500);
				}
			}
			logger.info("{}发放记录推送指定账号:{}", companyCode, account);
		} catch (Exception ex) {
			List<String> listGenerateAccount = tmp.get(companyCode);
			if (listGenerateAccount == null) {
				listGenerateAccount = new ArrayList<String>();
				tmp.put(companyCode, listGenerateAccount);
			}
			listGenerateAccount.addAll(account);
		} finally {
			redisCacheTemplate.close();
		}
	}

	public void rPushDemoAccount(List<String> accountNoList, String companyCode) {
		if (accountNoList == null || accountNoList.isEmpty()) {
			return;
		}
		Set<String> account = new HashSet<String>();
		for (String accountNoItem : accountNoList) {
			if (StringUtil.isEmpty(accountNoItem)) {
				continue;
			}
			account.add(accountNoItem);
		}
		try {
			int countIndex = 0;
			for (String accountNoItem : account) {
				redisCacheTemplate.rpush(ACT_DEMO_ACCOUNT_PRIZE_ADD + companyCode, accountNoItem);
				countIndex++;
				if (countIndex % 50 == 0) {
					Thread.sleep(500);
				}
			}
			logger.info("{}发放记录推送指定账号:{}", companyCode, account);
		} catch (Exception ex) {
			List<String> listGenerateAccount = tmpDemo.get(companyCode);
			if (listGenerateAccount == null) {
				listGenerateAccount = new ArrayList<String>();
				tmp.put(companyCode, listGenerateAccount);
			}
			listGenerateAccount.addAll(account);
		} finally {
			redisCacheTemplate.close();
		}
	}

	public void rPushAccount(Set<String> accountNoList, String companyCode) {
		if (accountNoList == null || accountNoList.isEmpty()) {
			return;
		}
		try {
			int countIndex = 0;
			for (String accountNoItem : accountNoList) {
				if (StringUtil.isEmpty(accountNoItem)) {
					continue;
				}
				redisCacheTemplate.rpush(ACT_ACCOUNT_PRIZE_ADD + companyCode, accountNoItem);
				if (countIndex % 50 == 0) {
					Thread.sleep(500);
				}
				countIndex++;
			}
			logger.info("{}发放记录推送指定账号:{}", companyCode, accountNoList);
		} catch (Exception ex) {
			List<String> listGenerateAccount = tmp.get(companyCode);
			if (listGenerateAccount == null) {
				listGenerateAccount = new ArrayList<String>();
				tmp.put(companyCode, listGenerateAccount);
			}
			listGenerateAccount.addAll(accountNoList);
		} finally {
			redisCacheTemplate.close();
		}
	}

	public void rPushTradeAccount(String accountNo, String companyCode) {
		if (StringUtil.isEmpty(accountNo)) {
			return;
		}
		try {
			redisCacheTemplate.rpush(ACT_ACCOUNT_TRADE_ADD + companyCode, accountNo);
		} catch (Exception ex) {
			List<String> listGenerateAccount = tmpTrade.get(companyCode);
			if (listGenerateAccount == null) {
				listGenerateAccount = new ArrayList<String>();
				tmpTrade.put(companyCode, listGenerateAccount);
			}
			listGenerateAccount.add(accountNo);
		} finally {
			redisCacheTemplate.close();
		}
	}

	public void rPushTradeAccount(Set<String> accountNoList, String companyCode) {
		if (accountNoList == null || accountNoList.isEmpty()) {
			return;
		}
		try {
			int countIndex = 0;
			for (String accountNoItem : accountNoList) {
				if (StringUtil.isEmpty(accountNoItem)) {
					continue;
				}
				redisCacheTemplate.rpush(ACT_ACCOUNT_TRADE_ADD + companyCode, accountNoItem);
				if (countIndex % 50 == 0) {
					Thread.sleep(500);
				}
				countIndex++;
			}
		} catch (Exception ex) {
			List<String> listGenerateAccount = tmpTrade.get(companyCode);
			if (listGenerateAccount == null) {
				listGenerateAccount = new ArrayList<String>();
				tmpTrade.put(companyCode, listGenerateAccount);
			}
			listGenerateAccount.addAll(accountNoList);
		} finally {
			redisCacheTemplate.close();
		}
	}

	public void rPushDemoAccount(Set<String> accountNoList, String companyCode) {
		if (accountNoList == null || accountNoList.isEmpty()) {
			return;
		}
		try {
			int countIndex = 0;
			for (String accountNoItem : accountNoList) {
				if (StringUtil.isEmpty(accountNoItem)) {
					continue;
				}
				redisCacheTemplate.rpush(ACT_DEMO_ACCOUNT_PRIZE_ADD + companyCode, accountNoItem);
				if (countIndex % 50 == 0) {
					Thread.sleep(500);
				}
				countIndex++;
			}
			logger.info("{}发放记录推送指定账号:{}", companyCode, accountNoList);
		} catch (Exception ex) {
			List<String> listGenerateAccount = tmpDemo.get(companyCode);
			if (listGenerateAccount == null) {
				listGenerateAccount = new ArrayList<String>();
				tmp.put(companyCode, listGenerateAccount);
			}
			listGenerateAccount.addAll(accountNoList);
		} finally {
			redisCacheTemplate.close();
		}
	}

	public List<String> popAccount(String companyCode) {
		return popAccount(null, companyCode);
	}

	public List<String> popDemoAccount(String companyCode) {
		return popDemoAccount(null, companyCode);
	}

	public void cleanAccount(String companyCode) {
		long total = redisCacheTemplate.llen(ACT_ACCOUNT_PRIZE_ADD + companyCode);
		for (int i = 0; i < total; i++) {
			redisCacheTemplate.lpop(ACT_ACCOUNT_PRIZE_ADD + companyCode);
		}
	}

	public List<String> popAccount(Integer max, String companyCode) {
		List<String> data = new ArrayList<String>();
		Set<String> account = new HashSet<String>();
		max = (max == null ? Integer.valueOf(gddSize) : max);
		try {
			// 判断redis中是否有数据，没数据直接返回
			long total = redisCacheTemplate.llen(ACT_ACCOUNT_PRIZE_ADD + companyCode);
			if (0 == total) {
				return data;
			}
			long curSysnSize = total > max ? max : total;
			// CountDownLatch latch = new CountDownLatch((int) curSysnSize);
			for (int i = 0; i < curSysnSize; i++) {
				String msg = redisCacheTemplate.lpop(ACT_ACCOUNT_PRIZE_ADD + companyCode);
				account.add(msg);
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

	public List<String> popDemoAccount(Integer max, String companyCode) {
		List<String> data = new ArrayList<String>();
		Set<String> account = new HashSet<String>();
		max = (max == null ? Integer.valueOf(gddSize) : max);
		try {
			// 判断redis中是否有数据，没数据直接返回
			long total = redisCacheTemplate.llen(ACT_DEMO_ACCOUNT_PRIZE_ADD + companyCode);
			if (0 == total) {
				return data;
			}
			long curSysnSize = total > max ? max : total;
			// CountDownLatch latch = new CountDownLatch((int) curSysnSize);
			for (int i = 0; i < curSysnSize; i++) {
				String msg = redisCacheTemplate.lpop(ACT_DEMO_ACCOUNT_PRIZE_ADD + companyCode);
				account.add(msg);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			redisCacheTemplate.close();
		}
		if (tmpDemo.get(companyCode) != null && !tmpDemo.get(companyCode).isEmpty()) {
			account.addAll(tmpDemo.get(companyCode));
			tmpDemo.get(companyCode).clear();
		}
		data.addAll(account);
		return data;
	}

	public List<String> popTradeAccount(Integer max, String companyCode) {
		List<String> data = new ArrayList<String>();
		Set<String> account = new HashSet<String>();
		max = (max == null ? Integer.valueOf(gddSize) : max);
		try {
			// 判断redis中是否有数据，没数据直接返回
			long total = redisCacheTemplate.llen(ACT_ACCOUNT_TRADE_ADD + companyCode);
			if (0 == total) {
				return data;
			}
			long curSysnSize = total > max ? max : total;
			// CountDownLatch latch = new CountDownLatch((int) curSysnSize);
			for (int i = 0; i < curSysnSize; i++) {
				String msg = redisCacheTemplate.lpop(ACT_ACCOUNT_TRADE_ADD + companyCode);
				account.add(msg);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			redisCacheTemplate.close();
		}
		if (tmpTrade.get(companyCode) != null && !tmpTrade.get(companyCode).isEmpty()) {
			account.addAll(tmpTrade.get(companyCode));
			tmpTrade.get(companyCode).clear();
		}
		data.addAll(account);
		return data;
	}
}
