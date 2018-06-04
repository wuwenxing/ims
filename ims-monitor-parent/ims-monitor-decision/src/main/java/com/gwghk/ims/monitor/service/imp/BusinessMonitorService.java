package com.gwghk.ims.monitor.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.vo.monitor.MonitorDataVo;
import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.dal.entity.MonitorItem;
import com.gwghk.ims.monitor.dal.entity.MonitorMachine;
import com.gwghk.ims.monitor.service.inf.IBusinessMonitor;
import com.gwghk.unify.framework.common.util.HttpClientUtil;

/**
 * 业务监控
 * @author jackson.tang
 *
 */
@Service
public class BusinessMonitorService implements IBusinessMonitor{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	@Override
	public MonitorResultVo getMonitorInfo(MonitorMachine machine,MonitorItem item) {
		String url=machine.getBaseURL()+"/monitor/business/"+item.getInterfaceName();
		MonitorResultVo result=new MonitorResultVo();
		result.setRequestDate(new Date());
		result.setHostname(machine.getIpAddress()+":"+machine.getIpPort());
		result.setItemId(item.getItemId());
		result.setItemName(item.getName());
		
		try {
			//返回的数据类型只能这三种： 1.简单单行记录 \d[%|s] 2.简单的列表 List&lt;String&gt; 3.复杂的map列表List&lt;Map&gt
			String json = HttpClientUtil.doGet(url,null);
			if(json==null)
				throw new Exception("HTTP请求失败");
			MonitorDataVo data=JSON.parseObject(json, MonitorDataVo.class);
			result.setMonitorDate(data.getOcurDate());
			
			//监控接口错误直接返回
			if(data.getCode()==0){
				result.setCode(0);
				result.setAlertMsg(data.getMsg());
				return result;
			}
			
			
			result.setCode(1);
			result.setData(data.getData());
			//判断结果数据类型： 1.简单单行记录 \d[%|s] 2.简单的列表 List&lt;String&gt; 3.复杂的map列表List&lt;Map&gt
			int dataType=1;
			
			if(data.getData() instanceof List) {
				@SuppressWarnings("rawtypes")
				List list=(List)data;
				dataType= list.get(0) instanceof Map ? 3 : 2; //这里仅仅假设客户端回传这三种数据类型
			}
			result.setDataType(dataType);
			
			return result;
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			result.setCode(-1);
			result.setAlertMsg("监控系统异常:"+e.getMessage());
		}
		
		return result;
	}

}
