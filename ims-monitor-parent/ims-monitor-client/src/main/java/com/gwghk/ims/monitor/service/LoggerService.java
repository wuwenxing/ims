package com.gwghk.ims.monitor.service;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * logger服务 主要用来获取logger文件的位置
 * @author jackson.tang
 *
 */
@Component
public class LoggerService {
	
	private String logPath;
	
	
	/**
	 * 初始化logger文件位置
	 * @return
	 */
	
	public void initLogFilePath() {
		String path=getBasePath()+"/conf/log4j2.xml";
		LoggerFactory.getLogger(this.getClass()).info("-------------path "+path);
		
		try {   
		    File f = new File(path);   
		    SAXReader reader = new SAXReader();
		    Document doc = reader.read(f);   
		    Element root = doc.getRootElement(); 
		    
		    //获取logger文件路径 :${LOG_HOME}/${SERVICE_NAME}.log
		    String logPath=root.element("appenders").element("RollingRandomAccessFile").attribute("fileName").getValue();
		    //填充这些变量
		    for(Element elm: (List<Element>)root.element("properties").elements("property")) {
		    	String name=elm.attribute("name").getValue();
		    	String value=elm.getText().trim();
		    	logPath=logPath.replaceAll("\\$\\{"+name+"\\}", value);
		    }
		    
		    this.logPath=getBasePath()+"/"+logPath;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getBasePath() {
		//file:/root/tmp123/ims-activity-api-dev-20180417/lib/ims-activity-api-2.0.0-SNAPSHOT.jar!/
		String path=LoggerService.class.getResource("/").getPath();
		if(path.startsWith("file:"))
			path=path.replaceFirst("file:", "");
		if(path.endsWith(".jar!/")) {
			path=path.replaceFirst("(?i)[a-z0-9.-]+.jar!/$", "");
		}
		
		if(path.matches("^/\\w:.+")) //win: /Y:/DFD/DD
			path=path.replaceFirst("/", "");
		
		return path+"..";
	}

	public String getLogPath() {
		if(logPath==null)
			initLogFilePath();
		
		return logPath;
	}
	
	
}
