package com.gwghk.ims.monitor.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.monitor.dal.entity.MonitorMachine;
import com.gwghk.ims.monitor.service.imp.MonitorConfigService;
/**
 * 监控配置
 * @author jackson.tang
 *
 */
@Component
public class MonitorConfig {
	/**
	 * 服务列表
	 */
	private List<MonitorMachine> machines;
	
	@Autowired
	private MonitorConfigService configService;

	public List<MonitorMachine> getMachines() {
		return machines;
	}

	/**
	 * 读取最新额配置数据
	 */
	public void load(String env) {
		machines=configService.findConfigInfo(env);
	}

}
