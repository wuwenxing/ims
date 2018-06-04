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

import com.gwghk.ims.common.dto.job.ActSettleReqDTO;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.unify.framework.cache.redis.cluster.RedisCacheTemplate;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 
 * @ClassName: ActWithdrawAccountRedis
 * @Description: 取款临时队列
 * @author lawrence
 * @date 2017年12月26日
 *
 */
@Component
public class ActWithdrawAccountRedis {
	private final static Logger logger = LoggerFactory.getLogger(ActWithdrawAccountRedis.class);
	private final static String ACT_WITHDRAW_ACCOUNT_ADD = "ActWithdrawAccount:A_";
	
	private final static String ACT_WITHDRAW_SETTLE_ACCOUNT_ADD = "ActWithdrawSettleAccount:A_";


	private Map<String, List<ActSettleReqDTO>> tmpWithdraw = new HashMap<String, List<ActSettleReqDTO>>();
	private Map<String, List<ActSettleReqDTO>> tmpWithdrawSettle = new HashMap<String, List<ActSettleReqDTO>>();

	@Autowired
	private RedisCacheTemplate redisCacheTemplate;

	private String gddSize = "50";

	@PostConstruct
	public void init() {
		for (CompanyEnum actCompany : CompanyEnum.getList()) {
			tmpWithdraw.put(actCompany.getCode(), new ArrayList<ActSettleReqDTO>());
			tmpWithdrawSettle.put(actCompany.getCode(), new ArrayList<ActSettleReqDTO>());
		}
	}

	public void rPushAccount(ActSettleReqDTO actSettleReqDto, String companyCode) {
		String actSettleReqDtoStr = JsonUtil.obj2Str(actSettleReqDto);
		try {
			redisCacheTemplate.rpush(ACT_WITHDRAW_ACCOUNT_ADD + companyCode, actSettleReqDtoStr);
		} catch (Exception ex) {
			List<ActSettleReqDTO> accountNoList = tmpWithdraw.get(companyCode);
			if (accountNoList == null) {
				accountNoList = new ArrayList<ActSettleReqDTO>();
				tmpWithdraw.put(companyCode, accountNoList);
			}
			accountNoList.add(actSettleReqDto);
		} finally {
			redisCacheTemplate.close();
		}
	}

	
	public void rPushSettleAccount(ActSettleReqDTO actSettleReqDto, String companyCode) {
		String actSettleReqDtoStr = JsonUtil.obj2Str(actSettleReqDto);
		try {
			redisCacheTemplate.rpush(ACT_WITHDRAW_SETTLE_ACCOUNT_ADD + companyCode, actSettleReqDtoStr);
		} catch (Exception ex) {
			List<ActSettleReqDTO> accountNoList = tmpWithdrawSettle.get(companyCode);
			if (accountNoList == null) {
				accountNoList = new ArrayList<ActSettleReqDTO>();
				tmpWithdrawSettle.put(companyCode, accountNoList);
			}
			accountNoList.add(actSettleReqDto);
		} finally {
			redisCacheTemplate.close();
		}
	}
	
	public List<ActSettleReqDTO> popAccount(String companyCode) {
		return popAccount(null, companyCode);
	}
	


	public List<ActSettleReqDTO> popAccount(Integer max, String companyCode) {
		List<ActSettleReqDTO> data = new ArrayList<ActSettleReqDTO>();
		max = (max == null ? Integer.valueOf(gddSize) : max);
		try {
			// 判断redis中是否有数据，没数据直接返回
			long total = redisCacheTemplate.llen(ACT_WITHDRAW_ACCOUNT_ADD + companyCode);
			if (0 != total) {
				long curSysnSize = total > max ? max : total;
				// CountDownLatch latch = new CountDownLatch((int) curSysnSize);
				for (int i = 0; i < curSysnSize; i++) {
					String msg = redisCacheTemplate.lpop(ACT_WITHDRAW_ACCOUNT_ADD + companyCode);
					ActSettleReqDTO dto = JsonUtil.json2Obj(msg, ActSettleReqDTO.class);
					data.add(dto);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			redisCacheTemplate.close();
		}
		if (tmpWithdraw.get(companyCode) != null && !tmpWithdraw.get(companyCode).isEmpty()) {
			data.addAll(tmpWithdraw.get(companyCode));
			tmpWithdraw.get(companyCode).clear();
		}
		return data;
	}

	
	public List<ActSettleReqDTO> popSettleAccount(String companyCode) {
		return popSettleAccount(null, companyCode);
	}

	public List<ActSettleReqDTO> popSettleAccount(Integer max, String companyCode) {
		List<ActSettleReqDTO> data = new ArrayList<ActSettleReqDTO>();
		Set<ActSettleReqDTO> account = new HashSet<ActSettleReqDTO>();
		max = (max == null ? Integer.valueOf(gddSize) : max);
		try {
			// 判断redis中是否有数据，没数据直接返回
			long total = redisCacheTemplate.llen(ACT_WITHDRAW_SETTLE_ACCOUNT_ADD + companyCode);
			if (0 != total) {
				long curSysnSize = total > max ? max : total;
				// CountDownLatch latch = new CountDownLatch((int) curSysnSize);
				for (int i = 0; i < curSysnSize; i++) {
					String msg = redisCacheTemplate.lpop(ACT_WITHDRAW_SETTLE_ACCOUNT_ADD + companyCode);
					ActSettleReqDTO dto = JsonUtil.json2Obj(msg, ActSettleReqDTO.class);
					account.add(dto);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			redisCacheTemplate.close();
		}
		if (tmpWithdraw.get(companyCode) != null && !tmpWithdraw.get(companyCode).isEmpty()) {
			data.addAll(tmpWithdraw.get(companyCode));
			tmpWithdraw.get(companyCode).clear();
		}
		data.addAll(account);
		return data;
	}

}
