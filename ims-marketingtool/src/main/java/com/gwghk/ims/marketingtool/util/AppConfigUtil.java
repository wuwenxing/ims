package com.gwghk.ims.marketingtool.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 摘要：属性配置读取工具类(spring PropertyPlaceholderConfigurer 配置的文件)
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年2月1日
 */
public class AppConfigUtil extends PropertyPlaceholderConfigurer {
	private static Map<String,String> ctxPropertiesMap;

	/**
	 * 重写processProperties方法
	 */
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String,String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	public static String getProperty(String name) {
		return ctxPropertiesMap.get(name);
	}

	public static Object setProperty(String name, Object value) {
		return ctxPropertiesMap.put(name, value.toString());
	}

	public static Map<String,String> getContextPropertyMap() {
		return ctxPropertiesMap;
	}
}
