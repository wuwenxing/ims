package com.gwghk.ims.monitor.task;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.util.ActDateUtil;
import com.gwghk.ims.monitor.dal.entity.ActMessageRecord;
import com.gwghk.ims.monitor.dal.mapper.ActMessageRecordMapper;
import com.gwghk.ims.monitor.notice.NoticeService;

/**
 * 摘要：监控-营销平台消息表(发现问题报警，自动修复)
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月31日
 */
//@Component
public class ImsMessageRecordTask {
	private static final Logger logger = LoggerFactory.getLogger(ImsMessageRecordTask.class);
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private ActMessageRecordMapper actMessageRecordMapper;
	
	/**
     * 功能：消息表监控
     */
	@Scheduled(cron ="${ims.messagerecord.monitor.cron}")
    public void monitorMessageRecord() {
		List<ActMessageRecord> messageRecordList = actMessageRecordMapper.queryList();
		if(CollectionUtils.isNotEmpty(messageRecordList)){   //预警
			logger.error(">>>消息表卡出了啦....");
			actMessageRecordMapper.update();  //启动自动修复
	    	String content = "告警时间："+ActDateUtil.getDateW3CFormat(new Date())+"<br>";
	    	content += "告警内容：消息表卡出啦，没有发放记录产生，请密切注意！";
	    	noticeService.notice("【严重告警】消息表卡出啦，没有发放记录产生，系统启动自动修复！", content);
		}else {
			logger.info(">>>消息表正常运行....");
	    }
    }
}