package com.gwghk.ims.activity.manager.prize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.ActPlatformEnum;
import com.gwghk.ims.common.enums.CompanyEnum;

/**
 * 
 * @ClassName: ApiDistributeManager
 * @Description: API管理类，由此类决定用GTS2的API,还是第三方的API
 * @author lawrence
 * @date 2017年10月23日
 *
 */
@Component
public class ApiDistributeManager {
	@Autowired
	private ActGts2ApiManager actGts2ApiManager;
	
	@Autowired
	private ActHxMt4ApiManager actHxMt4ApiManager;

	public ActApiManager getApi(String companyCode, String platform) {
		if (CompanyEnum.hx.getCode().equals(companyCode) && ActPlatformEnum.MT4.getCode().equals(platform)) {
			return actHxMt4ApiManager;
		} else {
			return actGts2ApiManager;
		}
	}
	
	public void resetHxSid(){
		actHxMt4ApiManager.getSid();
	}
}
