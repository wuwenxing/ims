package com.gwghk.ims.activity.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gwghk.ims.common.dto.activity.Gts2PrincipalReqDto;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.unify.framework.common.util.IPUtil;
import com.gwghk.unify.framework.common.util.JsonUtil;

@Service("gts2ApiCommonManager")
public class Gts2ApiCommonManager {

	// 改为public，给外部直接引用
	
	@Value("${gts2demoapi.url}")
	public String demoApiUrl;

	@Value("${gts2demoapi.loginName}")
	public String demoApiloginName;

	@Value("${gts2demoapi.invoker}")
	public String demoApiInvoker;

	@Value("${gts2demoapi.key}")
	public String demoapiKey;

	@Value("${gts2demoapi.hx.loginName}")
	public String demoHxApiloginName;

	@Value("${gts2demoapi.hx.invoker}")
	public String demoHxApiInvoker;

	@Value("${gts2demoapi.hx.key}")
	public String demoHxapiKey;

	@Value("${gts2demoapi.hxfx.loginName}")
	public String demoHxFxApiloginName;

	@Value("${gts2demoapi.hxfx.invoker}")
	public String demoHxFxApiInvoker;

	@Value("${gts2demoapi.hxfx.key}")
	public String demoHxFxapiKey;

	@Value("${gts2demoapi.cf.loginName}")
	public String demoCfApiloginName;

	@Value("${gts2demoapi.cf.invoker}")
	public String demoCfApiInvoker;

	@Value("${gts2demoapi.cf.key}")
	public String demoCfapiKey;
	
	/**
	 * @MethodName: initDemoGts2Principal
	 * @Description: 初始化GTS2-demo调用前的principal信息
	 * @param companyId
	 * @return
	 * @return String
	 */
	public String initDemoGts2Principal(String companyId) {
		Gts2PrincipalReqDto gts2PrincipalReqDto = new Gts2PrincipalReqDto();
		if(CompanyEnum.fx.getId().equals(companyId)){
			gts2PrincipalReqDto.setLoginName(demoApiloginName);
			gts2PrincipalReqDto.setInvoker(demoApiInvoker);
		}else if(CompanyEnum.hx.getId().equals(companyId)){
			gts2PrincipalReqDto.setLoginName(demoHxApiloginName);
			gts2PrincipalReqDto.setInvoker(demoHxApiInvoker);
		}else if(CompanyEnum.hxfx.getId().equals(companyId)){
			gts2PrincipalReqDto.setLoginName(demoHxFxApiloginName);
			gts2PrincipalReqDto.setInvoker(demoHxFxApiInvoker);
		}else if(CompanyEnum.cf.getId().equals(companyId)){
			gts2PrincipalReqDto.setLoginName(demoCfApiloginName);
			gts2PrincipalReqDto.setInvoker(demoCfApiInvoker);
		}
		gts2PrincipalReqDto.setRemoteIpAddress(IPUtil.getRealIp());
		gts2PrincipalReqDto.setCompanyId(companyId);
		String gts2PrincipalReqDtoStr = JsonUtil.obj2Str(gts2PrincipalReqDto);
		return gts2PrincipalReqDtoStr;
	}
	
	public String getDemoApiKey(String companyId){
		if(CompanyEnum.fx.getId().equals(companyId)){
			return demoapiKey;
		}else if(CompanyEnum.hx.getId().equals(companyId)){
			return demoHxapiKey;
		}else if(CompanyEnum.hxfx.getId().equals(companyId)){
			return demoHxFxapiKey;
		}else if(CompanyEnum.cf.getId().equals(companyId)){
			return demoCfapiKey;
		}
		return "";
	}
	
}
