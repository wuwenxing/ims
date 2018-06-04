package com.gwghk.ims.monitor.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.vo.monitor.MonitorResultVo;
import com.gwghk.ims.monitor.dal.entity.AlertStrategy;
import com.gwghk.ims.monitor.dal.entity.MonitorItem;

/**
 * 监控指定条目，主要是用来实现 数据是否预警条件
 * @author jackson.tang
 *
 */
public class MonitorItemThread extends MonitorThread{

	public MonitorItemThread(MonitorItem item) {
		super(item);
	}

	//这里预期数据格式:
	//	1.getCPU、getMemory、getHardDisk、getNetwork、getJvmMem 返回的值都格式: \d%
	//	2.getRequestTime 返回的数据格式: \ds
	//	3.getSlowSQL、getSlowBean 返回数据格式:[{"name":"sdff","value":\ds}] 并且是全量的
	//	4.getErrorLog 返回的数据格式：["error log1","error log2"] 是单独的
	@Override
	protected boolean dataDecision(String interfaceName, AlertStrategy strategy, MonitorResultVo resultVo) {
		boolean alert=false;
		
		switch(resultVo.getDataType()) {
		case 1:
			alert=judgeSimpleData(strategy, resultVo);
			break;
		case 2:
			alert=judgeListData(strategy, resultVo);
			break;
		case 3:
			alert=judgeMapData(strategy, resultVo);
			break;
		default:
			break;
		}
		
		return alert;
	}
	
	/**
	 * 处理数据格式: \d% 或者 \ds \dKB
	 * @param strategies
	 * @param result
	 * @return
	 */
	private boolean judgeSimpleData(AlertStrategy strategy, MonitorResultVo result) {
		
		try {
			String data=result.getData().toString().replaceAll("([0-9.]+)(?:[s%]|KB)", "$1");//去掉数字的百分比或者单位秒
			int symbol=strategy.getJudgeSymbol();
			String judgeValue=strategy.getJudgeValue().replaceAll("([0-9.]+)(?:[s%]|KB)", "$1");//去掉数字的百分比或者单位秒
			
			return _compareNumberData(data, symbol, judgeValue);
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return false;
	}
	
	/**
	 * 处理类似 数据格式:LIST&lt;Map&gt; [{"name":"sdff","value":\ds}]
	 * @param strategy
	 * @param result
	 * @return
	 */
	private boolean judgeMapData(AlertStrategy strategy, MonitorResultVo result) {
		try {
			int symbol=strategy.getJudgeSymbol();
			String judgeValue=strategy.getJudgeValue().replaceAll("([0-9.]+)[s%]", "$1");//去掉数字的百分比或者单位秒
			List<Map<String,String>> data=(List<Map<String,String>>)result.getData();
			if(data==null)
				return false;
			
			List<Map<String,String>> alertData=new ArrayList<Map<String,String>>();
			for(int i=0;i<data.size();i++) {
				String value=data.get(i).get("value").replaceAll("([0-9.]+)[s%]", "$1");
				if(_compareNumberData(value, symbol, judgeValue))
					alertData.add(data.get(i));
			}
			
			if(alertData.size()>0)//只要存在报警数据就断定为true
				return true;
			
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return false;
	}
	
	/**
	 * 处理类似 数据格式:LIST&lt;String&gt; ["sdfsdf"],目前暂时处理类似于包含这种关系
	 * @param strategy
	 * @param result
	 * @return
	 */
	private boolean judgeListData(AlertStrategy strategy, MonitorResultVo result) {
		try {
			int symbol=strategy.getJudgeSymbol();
			String judgeValue=strategy.getJudgeValue();//去掉数字的百分比或者单位秒
			
			if(symbol!=1) {
				//非包含关系暂时还不支持
				return false;
			}
			
			List<String> data=(List<String>)result.getData();
			if(data==null)
				return false;
			
			List<String> alertData=new ArrayList<String>();
			for(int i=0;i<data.size();i++) {
				String value=data.get(i).toLowerCase();
				if(value.indexOf(judgeValue)>-1) //判断是否包含此类的字符串
					alertData.add(data.get(i));
			}
			
			if(alertData.size()>0)//只要存在报警数据就断定为true
				return true;
			
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return false;
	}
	
	/**
	 * 主要用来判断数字是否 等于某个值  或者在某个区间
	 * @param data
	 * @param symbol
	 * @param judgeValue
	 * @return
	 */
	private boolean _compareNumberData(String data,int symbol,String judgeStr) {
		Double value=Double.parseDouble(data);
		//处理数据等于的情况
		if(symbol==1){
			Double judgeValue=Double.parseDouble(judgeStr);
			return value==judgeValue;
		}
		if(symbol==2) {
			String[] valArr=judgeStr.split(",");
			
			if(AlertStrategy.INFINITE.equals(valArr[1]))  //表示大于某个值 data >=valArr[0]
				return Double.parseDouble(data)>=Double.parseDouble(valArr[0]) ? true : false;
			
			else //表示在某个区间 [ valArr[0]<= data<=valArr[1] ]
				return (value>=Double.parseDouble(valArr[0]) && value<=Double.parseDouble(valArr[1])) ? true : false;
		}
		
		//@todo 其他不支持的地方返回false
		return false;
	}
}
