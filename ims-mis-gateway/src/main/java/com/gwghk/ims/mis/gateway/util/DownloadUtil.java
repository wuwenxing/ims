package com.gwghk.ims.mis.gateway.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwghk.ims.common.util.ExcelUtil;
import com.gwghk.ims.mis.gateway.enums.DownTemplateEnum;

/**
 * 
 * 摘要：提供文件下载工具类
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月12日
 */
public class DownloadUtil {
	
	Logger logger = LoggerFactory.getLogger(DownloadUtil.class) ;

	public void downloadExcelTemplate(DownTemplateEnum exportTemplate,HttpServletRequest request,HttpServletResponse response){
		BufferedInputStream in = new BufferedInputStream(this.getClass().getResourceAsStream(exportTemplate.getPath())) ;    
 	   try {
 		   BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream()) ;    
 		   ExcelUtil.wrapExcelExportResponse(exportTemplate.getValue(), request, response);
 	       byte[] data = new byte[1024];    
 	       int len = 0;    
 	       while (-1 != (len=in.read(data, 0, data.length))) {    
 	    	   out.write(data, 0, len);    
 	       }
 	       out.flush(); 
        } catch (Exception e) {
            logger.error("exportExcel->导出发放记录失败!", e);
        }
	}
}
