package com.gwghk.ims.mis.gateway.util;

import java.util.ResourceBundle;

import org.springframework.context.annotation.PropertySource;

/**
 * 摘要：配置文件读取工具类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年2月9日
 */
@PropertySource(value = {"classpath:application.properties"})
public final class ResourcesUtil {
	public static final ResourceBundle application = ResourceBundle.getBundle("application");
}
