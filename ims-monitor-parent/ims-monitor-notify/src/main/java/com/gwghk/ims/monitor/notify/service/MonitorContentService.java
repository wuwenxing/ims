package com.gwghk.ims.monitor.notify.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.notify.dao.entity.MonitorContent;
import com.gwghk.ims.monitor.notify.dao.inf.MonitorContentDao;

import net.oschina.durcframework.easymybatis.query.Query;

@Service
public class MonitorContentService {
	@Autowired
	private MonitorContentDao monitorContentDao;
	
	public boolean save(MonitorResultVo resultVo) {
		MonitorContent data=new MonitorContent();
		data.setItemId(resultVo.getItemId());
		data.setDataJson(JSON.toJSONString(resultVo));
		data.setRequestDate(resultVo.getRequestDate());
		data.setResponceDate(resultVo.getResponceDate());
		
		return monitorContentDao.save(data)>0;
	}

	public boolean delExpiredData(int dataExpiredDay) {
		//不能删除0天前的数据
		if(dataExpiredDay<=0)
			return false;
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time=new Date().getTime()-3600L*24*dataExpiredDay*1000;
		Date expiredDate=new Date(time);
		
		Query query=Query.build().gt("responce_date", sdf.format(expiredDate));
		return monitorContentDao.delByExpression(query)>0;
	}

}
