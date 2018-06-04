package com.gwghk.ims.monitor.client;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwghk.ims.common.vo.monitor.MonitorDataVo;
import com.gwghk.ims.monitor.service.LoggerService;

@RestController
@RequestMapping("/monitor/business")
public class ImsActMonitorDemoController {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LoggerService loggerService;
	
	@RequestMapping("/demo")
	public MonitorDataVo getSystemInfo() {
		MonitorDataVo resultVo=new MonitorDataVo();
		Map<String,Object> data=new HashMap<String,Object>();
		
		Map<String,Object> item=null;
		try {
			String monitorResult="80%";
			return resultVo.success(monitorResult);
			
		}catch(Exception e) {
			logger.error(">>>{}",e.getMessage());
			return resultVo.fail(e.getMessage());
		}
	}
}
