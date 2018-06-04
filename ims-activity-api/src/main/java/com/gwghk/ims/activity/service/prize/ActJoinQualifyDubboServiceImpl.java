package com.gwghk.ims.activity.service.prize;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.common.enums.ActTypeEnum;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.external.prize.ActJoinQualifyDubboService;

/**
 * 
 * @ClassName: ActJoinQualifyDubboServiceImpl
 * @Description: 实现将符合活动资格的用户放置到活动资格表
 * @author lawrence
 * @date 2017年5月24日
 *
 */
@Service
public class ActJoinQualifyDubboServiceImpl implements ActJoinQualifyDubboService {

	private static Logger logger = LoggerFactory.getLogger(ActJoinQualifyDubboServiceImpl.class);

	//@Autowired
	//private ActJoinQualityManager actJoinQualityManager;

	/**
	 * 处理用户活动资格
	 */
	@Override
	public void processActCustomerQuality(ActTypeEnum actSettingType,boolean isSending,Long companyId) {
		try {
			//actJoinQualityManager.processActCustomerQuality(actSettingType,isSending,actCompany);
		} catch (Exception e) {
			logger.error(">>>>>>>>>>>>符合活动条件的用户程序执行失败,活动类型{},公司{}>>>err={}",actSettingType.getName(),CompanyEnum.getCodeById(companyId), e);
		}
	}

	@Override
	public void processActCustomerQuality(ActTypeEnum actSettingType,Map<String, Object> actParams,Long companyId) {
		try {
			//actJoinQualityManager.processActCustomerQuality(actSettingType,actParams,actCompany);
		} catch (Exception e) {
			logger.error(">>>>>>>>>>>>符合活动条件的用户程序执行失败,活动类型{},公司{}>>>err={}",actSettingType.getName(),CompanyEnum.getCodeById(companyId), e);
		}
	}
}
