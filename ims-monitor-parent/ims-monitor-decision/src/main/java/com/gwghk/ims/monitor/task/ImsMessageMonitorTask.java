package com.gwghk.ims.monitor.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.util.ActDateUtil;
import com.gwghk.ims.monitor.notice.NoticeService;
import com.gwghk.ims.monitor.util.ResourcesUtil;
import com.gwghk.unify.framework.common.util.HttpClientUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：监控-营销平台message系统
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月31日
 */
//@Component
public class ImsMessageMonitorTask {
	private static final Logger logger = LoggerFactory.getLogger(ImsMessageMonitorTask.class);
	
	@Autowired
	private NoticeService noticeService;
	
	/**
     * 功能：message监控
     */
	@Scheduled(cron = "${ims.kafka.monitor.cron}")
    public void monitorKafka() {
		String enable = ResourcesUtil.application.getString("enable.monitor");
		if("true".equals(enable)){
			String url = "http://"+ResourcesUtil.application.getString("ims.kafka.monitor.url")+"/act/kafka/test";
			String apiResult = HttpClientUtil.doGet(url,null);
		    if(StringUtil.isNotEmpty(apiResult)){
		    	logger.info(">>>message项目正常运行....");
		    }else{  //预警
		    	logger.error(">>>message项目挂啦....");
		    	String content = "告警时间："+ActDateUtil.getDateW3CFormat(new Date())+"<br>";
		    	content += "告警节点："+ResourcesUtil.application.getString("ims.kafka.monitor.url")+"<br>";
		    	content += "告警内容：营销平台-message项目挂啦,请尽快处理！";
		    	noticeService.notice("【严重告警】营销平台-message项目挂啦", content);
		    }
		}
    }
}