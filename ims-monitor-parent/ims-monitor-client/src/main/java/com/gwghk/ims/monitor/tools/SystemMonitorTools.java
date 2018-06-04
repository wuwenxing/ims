package com.gwghk.ims.monitor.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 系统监控工具,这些方法仅仅适用于linux
 * @author jackson.tang
 *
 */
public class SystemMonitorTools {
	private static Logger logger=LoggerFactory.getLogger(SystemMonitorTools.class);
	/**
	 * 获取cpu memory状况
	 * 这条命令输出如下内容
	 * Cpu(s):  3.6%us,  0.7%sy,  0.0%ni, 95.6%id,  0.0%wa,  0.0%hi,  0.0%si,  0.0%st
	 * Mem:  12194112k total,  9729240k used,  2464872k free,    85496k buffers
	 */
	private static final String[] GET_CPUMEM_USE_RATE_COMMAND={"/bin/sh","-c","top -bn1 | head -n4 | tail -n2"};
	/**
	 * 获取硬盘状况
	 * 这条命令输出如下内容:
	 * total                 500G  149G  327G  32%
	 */
	private static final String[] GET_HDISK_USE_RATE_COMMAND={"/bin/sh","-c","df -h --total | tail -n1"};
	
	public static final String CPU_USE_RATE="cpu";
	public static final String MEM_USE_RATE="memory";
	public static final String HDISK_USE_RATE="hardDisk";
	public static final String NET_USE_RATE="network";
	public static final String NET_UPL_SPEED="uploadSpeed";
	public static final String NET_DOWN_SPEED="downloadSpeed";
	
	/**
	 * 获取系统CPU 内存使用状况
	 * @return
	 */
	public static Map<String,Object> getSystemUseRate(){
		Map<String,Object> data=new HashMap<String,Object>();
		
		Runtime runtime=Runtime.getRuntime();
		try {
			Process process=runtime.exec(GET_CPUMEM_USE_RATE_COMMAND);
			BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line=null;
			
			Matcher matcher=null;
			//匹配cpu使用率正则表达式
			Pattern pCpuRate=Pattern.compile("cpu(?:\\(s\\))?:(?:\\s+[0-9.]+%\\w+,)+\\s+([0-9.]+)%id");
			//匹配mem使用率正则表达式
			Pattern pMemRate=Pattern.compile("mem(?:\\(s\\))?:\\s*(\\d+)k\\s*total,\\s*(\\d+)k\\s*used");
			
			while((line=reader.readLine())!=null) {
				line=line.trim().toLowerCase();
				
				//找出cpu使用率
				matcher=pCpuRate.matcher(line);
				if(matcher.find()) {//cpu(s):  6.9%us,  0.8%sy,  0.0%ni, 92.3%id,  0.0%wa,  0.0%hi,  0.0%si,  0.0%st
					Float value=Float.parseFloat(matcher.group(1));
					data.put(CPU_USE_RATE, (100f-value)+"%");// 100-cpu空闲状况下的使用率
				}
				
				//找出mem使用率
				matcher=pMemRate.matcher(line);
				if(matcher.find()) {//Mem:  12194112k total,  9729240k used,  2464872k free,    85496k buffers
					Float total=Float.parseFloat(matcher.group(1));
					Float used=Float.parseFloat(matcher.group(2));
					if(total!=null && used!=null) {
						DecimalFormat decimalFormat=new DecimalFormat(".00");
						data.put(MEM_USE_RATE,decimalFormat.format(used*100/total)+"%");//
					}
					continue;
				}
				
			}
			
			process.destroy();
			reader.close();
			return data;
			
		}catch(Exception e) {
			logger.error(">>>获取本机 系统监控数据异常",e);
			return null;
		}
	}
	
	/**
	 * 获取硬盘空间状态
	 * @return
	 */
	public static Map<String,Object> getHardDiskUseRate(){
		Map<String,Object> data=new HashMap<String,Object>();
		
		Runtime runtime=Runtime.getRuntime();
		try {
			Process process=runtime.exec(GET_HDISK_USE_RATE_COMMAND);
			BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line=reader.readLine().trim().toLowerCase();
			
			//匹配磁盘使用率正则表达式
			//total                 500G  149G  327G  32%
			Pattern pHdRate=Pattern.compile("total\\s+(?:\\d+g\\s+)+(\\d+%)");
			Matcher matcher=pHdRate.matcher(line);
			if(matcher.find())
				data.put(HDISK_USE_RATE, matcher.group(1));
			
			process.destroy();
			reader.close();
			return data;
		}catch(Exception e) {
			logger.error(">>>获取本机 硬盘监控数据异常",e);
			return null;
		}
	}
	
	/**
	 * 获取本机的网络速度
	 * @return
	 */
	public static Map<String,Object> getNetWorkSpeed() {
		Map<String,Object> data=new HashMap<String,Object>();
		
        Process pro1,pro2;    
        Runtime r = Runtime.getRuntime();    
        try {    
            String command = "cat /proc/net/dev";    
            //第一次采集流量数据    
            long startTime = System.currentTimeMillis();    
            pro1 = r.exec(command);    
            BufferedReader in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));    
            String line = null;    
            long inSize1 = 0, outSize1 = 0;    
            while((line=in1.readLine()) != null){       
                line = line.trim();    
                if(line.startsWith("eth0")){    
                    String[] temp = line.split("\\s+");     
                    inSize1 = Long.parseLong(temp[1]); //Receive bytes,单位为Byte    
                    outSize1 = Long.parseLong(temp[9]);//Transmit bytes,单位为Byte    
                    break;    
                }  
            }       
            in1.close();    
            pro1.destroy();    
            try {  
                Thread.sleep(1000);    
            } catch (InterruptedException e) {    
                StringWriter sw = new StringWriter();    
                e.printStackTrace(new PrintWriter(sw));    
                System.out.println("NetUsage休眠时发生InterruptedException. " + e.getMessage());    
                System.out.println(sw.toString());    
            }    
            //第二次采集流量数据    
            long endTime = System.currentTimeMillis();    
            pro2 = r.exec(command);    
            BufferedReader in2 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));    
            long inSize2 = 0 ,outSize2 = 0;    
            while((line=in2.readLine()) != null){       
                line = line.trim();    
                if(line.startsWith("eth0")){    
                    System.out.println(line);    
                    String[] temp = line.split("\\s+");     
                    inSize2 = Long.parseLong(temp[1]);    
                    outSize2 = Long.parseLong(temp[9]);    
                    break;    
                }  
            }  
              
            //cal dl speed  
            float interval = (float)(endTime - startTime)/1000;  
            
            float dlSpeed = (float) ((float)(inSize2 - inSize1)/1024/interval);  
            float ulSpeed = (float) ((float)(outSize2 - outSize1)/1024/interval);  
            
            in2.close();    
            pro2.destroy();  
            DecimalFormat decimalFormat=new DecimalFormat(".00");
            
            data.put(NET_UPL_SPEED, decimalFormat.format(ulSpeed)+"KB");
            data.put(NET_DOWN_SPEED, decimalFormat.format(dlSpeed)+"KB");
            
            return data;
        } catch (Exception e) {  
        	logger.error(">>>获取本机 网络状况异常",e);
			return null;
        }
    }  
}
