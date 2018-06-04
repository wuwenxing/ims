package com.gwghk.ims.datacleaning.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 摘要：读取application.properties的工具类
 * @author   Gavin.guo
 * @version  1.0
 * @Date	 2016年7月25日
 */
public class ApplicationProperties {
    private static Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);
    
    private static ApplicationProperties instance = new ApplicationProperties();
    // 服务内的自定义application.properties
    private Properties customizeProperties = null;
    // 默认application.properties
    private Properties defaultProperties = null;

    /**
     * 构造函数
     */
    private ApplicationProperties() {
        // step1:加载自定义文件
        loadCustomizeProperties();
        // step2:加载默认文件
        loadDefaultProperties();

    }

    /**
     * @see 加载默认配置文件
     */
    private void loadDefaultProperties() {
        defaultProperties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResource("application.properties").openStream();
            // 加载配置文件
            defaultProperties.load(inputStream);
            logger.info("加载默认application.properties成功." + defaultProperties.toString());
        } catch (Exception e1) {
            logger.error("默认application.properties路径=" + this.getClass().getClassLoader().getResource("application.properties").getPath());
            logger.error("加载默认application.properties失败.", e1.getCause());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 单例获取 Double Check
     * 
     * @return
     */
    public static ApplicationProperties getInstance() {
        return instance;
    }

    /**
     * @see 加载外部配置文件
     * @param resources
     * @return
     */
    private void loadCustomizeProperties() {
        customizeProperties = new Properties();
        String projectPath = System.getProperty("user.dir");
        String filePath = "/" + projectPath + File.separator + "conf" + File.separator + "application.properties";
        File customizeFile = new File(filePath);
        if (customizeFile.exists()) {
            // 使用InputStream得到一个资源文件
            InputStream inputstream = null;
            try {
                inputstream = new FileInputStream(customizeFile);
                // 加载配置文件
                customizeProperties.load(inputstream);
                logger.info("加载自定义application.properties成功." + customizeProperties.toString());
            } catch (Exception e1) {
                logger.error("加载自定义application.properties失败", e1.getCause());
            } finally {
                try {
                    inputstream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            logger.info("未自定义application.properties");
        }
    }

    /**
     * @see 获取配置，自定义配置优先
     * @param key
     * @return
     */
    public String getValue(String key) {
        String value = customizeProperties.getProperty(key);
        if (null != value)
            return value.trim();
        else {
            value = defaultProperties.getProperty(key);
            if (null != value)
                return value.trim();
        }
        return null;
    }

}
