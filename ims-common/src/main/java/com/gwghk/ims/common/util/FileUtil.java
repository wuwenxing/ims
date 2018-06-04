package com.gwghk.ims.common.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class FileUtil {
    /**
     * 功能：根据文件全路径获取对应的文件流数据
     * @param filePath  文件全路径
     */
    public static  InputStream readInputStream(String filePath){
        try{
            URL url =new URL(filePath); // 创建URL   
            URLConnection urlconn = url.openConnection(); // 试图连接并取得返回状态码    
            urlconn.connect();           
            HttpURLConnection httpconn =(HttpURLConnection)urlconn;  
            int httpResult = httpconn.getResponseCode();        
            if(httpResult == HttpURLConnection.HTTP_OK){
                return urlconn.getInputStream();
            }
        }catch(Exception e){
            return null;
        }
        return null;
    }
}
