package com.gwghk.ims.monitor.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gwghk.ims.common.vo.monitor.MonitorResultVo;

/**
 * 判定在规定的时间内,重现多少次达到报警水平线的数据POJO
 * @author jackson.tang
 *
 */
public class JudgeAlertStack {
	
	public JudgeAlertStack() {
		stack=new ArrayList<MonitorResultVo>();
	}
	
	/**
	 * 判定的起始时间
	 */
	private Date startDate;
	/**
	 * 报警数据堆
	 */
	private List stack;

	public Date getStartDate() {
		return startDate;
	}
	
	public List getStack() {
		return stack;
	}
	public void setStack(MonitorResultVo monitorResultVo) {
		//当监控数据为增量 单条单条的时候,初始化的第一条为开始计数时间
		if(stack.size()==0 && startDate==null)
			startDate=new Date();
		if(monitorResultVo.getDataType()==2) {
			List list=(List)monitorResultVo.getData();
			stack.addAll(list);
		}else if(monitorResultVo.getDataType()==3) //当监控数据为全量的时候
			stack=(List)monitorResultVo.getData();
		else if(monitorResultVo.getDataType()==1) 
			stack.add(monitorResultVo.getData());
		
	}
	public void clear() {
		stack=null;
		startDate=null;
	}
	
}
