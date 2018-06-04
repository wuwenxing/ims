package com.gwghk.ims.datasource.data;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * 动态数据源
 * @author jackson.tang
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	private DataSource defaultDataSource;
	/**
	 * 数据源集合
	 */
	private Map<Object,Object> dataSources=new HashMap<Object,Object>();
	/**
	 * 当前线程指定的数据源
	 */
	private static ThreadLocal<String> dataSourceHolder=new ThreadLocal<String>();
	
	public DataSource getDefaultDataSource() {
		return defaultDataSource;
	}

	public void setDefaultDataSource(DataSource defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}

	public static String getDataSourceHolder() {
		return dataSourceHolder.get();
	}


	public static void setDataSourceHolder(String flag) {
		LoggerFactory.getLogger(DynamicDataSource.class).info(">>>切换数据源为:{}",flag);
		dataSourceHolder.set(flag);
	}

	public Map<Object, Object> getDataSources() {
		return dataSources;
	}

	public void setDataSources(Map<Object, Object> dataSources) {
		this.dataSources = dataSources;
	}

	/**
	 * 仅仅初始化调用
	 */
	@Override
	public void afterPropertiesSet() {
		this.setDefaultTargetDataSource(defaultDataSource);  
        this.setTargetDataSources(dataSources);
		super.afterPropertiesSet();
	}
	
	/**
	 * 这个返回值决定了使用那个数据源，
	 * 目前已知每次获取SqlSession都会调用
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return dataSourceHolder.get();
	}

}
