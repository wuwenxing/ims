package com.gwghk.ims.monitor.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.util.ActDateUtil;
import com.gwghk.ims.monitor.notice.NoticeService;
import com.gwghk.ims.monitor.util.ResourcesUtil;
import com.gwghk.unify.framework.common.util.HttpClientUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：监控-营销平台后台系统
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月31日
 */
//@Component
public class ImsBosMonitorTask {
	private static final Logger logger = LoggerFactory.getLogger(ImsBosMonitorTask.class);
	
	@Autowired
	private NoticeService noticeService;
	
   /**
    * 功能：Bos监控
    */
	//@Scheduled(cron = "${ims.bos.monitor.cron}")
    public void monitorBos() {
		String enable = ResourcesUtil.application.getString("enable.monitor");
		if("true".equals(enable)){
			String url = "http://"+ResourcesUtil.application.getString("ims.bos.monitor.url")+"/unify-activity-bos-web/test";
			String apiResult = HttpClientUtil.doGet(url,null);
		    if(StringUtil.isNotEmpty(apiResult)){
		    	logger.info(">>>bos项目正常运行....");
		    }else{  //预警
		    	logger.error(">>>bos项目挂啦....");
		    	String content = "告警时间："+ActDateUtil.getDateW3CFormat(new Date())+"<br>";
		    	content += "告警节点："+ResourcesUtil.application.getString("ims.bos.monitor.url")+"<br>";
		    	content += "告警内容：营销平台-后台挂啦,请尽快处理！";
		    	noticeService.notice("【严重告警】营销平台-后台挂啦", content);
		    }
		}
   }
}