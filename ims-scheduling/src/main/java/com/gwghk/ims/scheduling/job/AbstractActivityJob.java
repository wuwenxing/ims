package com.gwghk.ims.scheduling.job;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.gwghk.ims.common.enums.CompanyEnum;

/**
 * 这个仅仅适用于每个公司的业务逻辑都一致的定时任务,读取公司列表是从 CompanyEnum 里面读取
 * @author jackson.tang
 *
 */
public abstract class AbstractActivityJob implements SimpleJob{
	protected final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	private List<String> excludeList=new ArrayList<String>();
	
	@Override
	public void execute(ShardingContext shardingContext) {
		try {
			//利用定时任务集群的互斥性，说白了就是分布式锁，只要在这个范围内开启多线程执行都不会出问题
			CompanyEnum[] list=CompanyEnum.values();
			
			final CountDownLatch latch=new CountDownLatch(list.length);
			for(CompanyEnum company:CompanyEnum.values()) {
				//剔除排除列表公司
				if(excludeList.indexOf(company.getCode())!=-1)
					continue;
				
				//开始使用多线程来处理job
				new Thread() {
					@Override
					public void run() {
						try {
							doJob(company);
							latch.countDown();
						}catch(Exception e) {
							logger.error(">>>运行定时任务,在处理{}时出错:{}",company.getCode(),e.getMessage(),e);
						}
					}
				}.start();
			}
			//等待公司线程全部运行完毕
			latch.await();
		}catch(Exception e) {
			logger.error(">>>运行定时任务主体出错：{}",e.getMessage(),e);
		}
	}
	/**
	 * 处理某个公司的业务
	 * @param companyCode
	 */
	protected abstract void doJob(CompanyEnum company); 
	
	/**
	 * 设置待剔除的公司
	 * @param companyCode
	 */
	public void setExclude(String companyCode) {
		this.excludeList.add(companyCode);
	}
}
