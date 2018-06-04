package com.gwghk.ims.mis.gateway.interceptor;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.gwghk.ims.common.inf.mis.sys.MisSystemDictDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemMenuDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemMenuRoleDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemRoleColumnAuthDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemRoleDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemUserDubboService;
import com.gwghk.ims.mis.gateway.common.SystemCache;

public class SystemListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// 初始化
		MisSystemUserDubboService misSystemUserDubboService = arg0.getApplicationContext().getBean(MisSystemUserDubboService.class);
		MisSystemMenuDubboService misSystemMenuDubboService = arg0.getApplicationContext().getBean(MisSystemMenuDubboService.class);
		MisSystemRoleDubboService misSystemRoleDubboService = arg0.getApplicationContext().getBean(MisSystemRoleDubboService.class);
		MisSystemMenuRoleDubboService misSystemMenuRoleDubboService = arg0.getApplicationContext().getBean(MisSystemMenuRoleDubboService.class);
		MisSystemRoleColumnAuthDubboService misSystemRoleColumnAuthDubboService = arg0.getApplicationContext().getBean(MisSystemRoleColumnAuthDubboService.class);
		
		SystemCache.getInstance().init(misSystemUserDubboService
				, misSystemMenuDubboService
				, misSystemRoleDubboService
				, misSystemMenuRoleDubboService
				, misSystemRoleColumnAuthDubboService);
		
		MisSystemDictDubboService misSystemDictDubboService = arg0.getApplicationContext().getBean(MisSystemDictDubboService.class);
		
	}

}
