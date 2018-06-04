package com.gwghk.ims.datacleaning.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.gwghk.ims.common.dto.activity.ActPrizeBaseReqDTO;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.common.inf.external.activity.ActPrizeRecordApiDubboService;
import com.gwghk.ims.common.inf.external.activity.ActivityApiDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.datacleaning.dao.entity.ActCashin;
import com.gwghk.ims.datacleaning.dao.entity.ActCashout;
import com.gwghk.ims.datacleaning.dao.entity.ActCustomerInfo;
import com.gwghk.ims.datacleaning.dao.entity.ActTradeRecord;
import com.gwghk.ims.datacleaning.dao.inf.base.ActCashinDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ActCashoutDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ActCustomerInfoDao;
import com.gwghk.ims.datacleaning.dao.inf.base.ActTradeRecordDao;
import com.gwghk.ims.datasource.data.DataSourceContext;

/**
 * 推送变动的账号数据，实例化即开启了推送线程
 * @author jackson.tang
 *
 */
@Component
@Order(1)
public class AccountPushTask implements CommandLineRunner{
	/**
	 * 最小推送时间,默认15秒
	 */
	@Value("${message.push.minInterval:15}")
	private int minInterval;
	/**
	 * 默认最大线程数量50
	 */
	@Value("${message.push.maxThread:50}")
	private int maxThread;
	/**
	 * 线程池
	 */
	private ExecutorService threadPool;
	/**
	 * 线程上次运行时间
	 */
	private Date lastRunTime;
	
	@Autowired
	private ActCashinDao actCashinDao;
	@Autowired
	private ActCashoutDao actCashoutDao;
	@Autowired
	private ActCustomerInfoDao actCustomerInfoDao;
	@Autowired
	private ActTradeRecordDao actTradeRecordDao;
	/**
	 * 推送目标接口
	 */
	@Autowired
	private ActivityApiDubboService activityApiService;
	
	@Autowired
	private ActPrizeRecordApiDubboService actPrizeRecordApiDubboService;
	/**
	 * 待推送的数据
	 */
	private Map<String,AccountPushInfo> readyPushData;
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void run(String... args) throws Exception {
		if(minInterval<1)//时间间隔不能小于1秒
			minInterval=1;
		
		//初始化线程池
		threadPool=Executors.newFixedThreadPool(maxThread);
		
		//初始化待推送的数据
		readyPushData=new HashMap<String,AccountPushInfo>();
		
		//启动推送任务
		logger.info(">>>开始启动推送服务");
		startPushTask();
	}
	
	public Date getLastRunTime() {return lastRunTime;}
	
	/**
	 * 寻找所有未推送的信息
	 * @throws Exception
	 */
	public void refreshUnpushAccount() throws Exception {
		
		final String[] envs= {"real","demo"}; 
		long timePos1=System.currentTimeMillis();
		
		//遍历所有的公司
		for(CompanyEnum company:CompanyEnum.values()) {
			logger.info(">>>开始刷新【{}】被标记为待推送的数据",company.getCode());
			DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, company.getLongId());
			
			//入金变化账号
			List<ActCashin> cashinlist=actCashinDao.findUnpushCashin();
			if(cashinlist!=null && cashinlist.size()>0) {
				actCashinDao.batchUpdatePushStatus(cashinlist.get(cashinlist.size()-1).getId());//更新推送状态
				for(int i=0;i<cashinlist.size();i++) {
					if(readyPushData.containsKey(cashinlist.get(i).getAccountNo()))
						continue;
					
					AccountPushInfo data=ImsBeanUtil.copyNotNull(new AccountPushInfo(), cashinlist.get(i));
					data.setCompanyId(company.getLongId());
					data.setEnv("real");
					readyPushData.put(cashinlist.get(i).getAccountNo(), data);
				}
			}
			
			//出金变化账号
			List<ActCashout> cashoutList=actCashoutDao.findUnpushCashout();
			if(cashoutList!=null && cashoutList.size()>0) {
				actCashoutDao.batchUpdatePushStatus(cashoutList.get(cashoutList.size()-1).getId());//更新推送状态
				for(int i=0;i<cashoutList.size();i++) {
					if(readyPushData.containsKey(cashoutList.get(i).getAccountNo()))
						continue;
					
					AccountPushInfo data=ImsBeanUtil.copyNotNull(new AccountPushInfo(), cashoutList.get(i));
					data.setCompanyId(company.getLongId());
					data.setEnv("real");
					readyPushData.put(cashoutList.get(i).getAccountNo(),data);
				}
			}
			
			for(String env:envs) {
				//客户新增账号(包含real 与 demo 两种类型)
				List<ActCustomerInfo> customerList=actCustomerInfoDao.findUnpushCustomerInfo(env);
				if(customerList!=null && customerList.size()>0) {
					actCustomerInfoDao.batchUpdatePushStatus(env,customerList.get(customerList.size()-1).getId());//更新推送状态
					for(int i=0;i<customerList.size();i++) {
						if(readyPushData.containsKey(customerList.get(i).getAccountNo()))
							continue;
						
						AccountPushInfo data=ImsBeanUtil.copyNotNull(new AccountPushInfo(), customerList.get(i));
						data.setCompanyId(company.getLongId());
						data.setEnv(env);
						readyPushData.put(customerList.get(i).getAccountNo(),data);
					}
				}
			
			
				//交易记录变动的账号
				List<ActTradeRecord> tradeRecordList=actTradeRecordDao.findUnpushTrashRecord(env);
				if(tradeRecordList!=null && tradeRecordList.size()>0) {
					actTradeRecordDao.batchUpdatePushStatus(env,tradeRecordList.get(tradeRecordList.size()-1).getId());//更新推送状态
					for(int i=0;i<tradeRecordList.size();i++) {
						if(readyPushData.containsKey(tradeRecordList.get(i).getAccountNo()))
							continue;
						
						AccountPushInfo data=ImsBeanUtil.copyNotNull(new AccountPushInfo(), tradeRecordList.get(i));
						data.setCompanyId(company.getLongId());
						data.setEnv(env);
						readyPushData.put(tradeRecordList.get(i).getAccountNo(),data);
					}
				}
			}
			
			logger.info(">>>刷新待推送的数据完毕，耗时{}毫秒-------",System.currentTimeMillis()-timePos1);
		}
	}
	
	/**
	 * 启动推送任务
	 */
	private void startPushTask() {
		new Thread() {
			@Override
			public void run() {
				while(true) {
					
					try {
						sleep(minInterval*1000);
						
						lastRunTime=new Date();//记录任务时间
						
						//刷新未推送的数据
						refreshUnpushAccount();
						
						for(Map.Entry<String, AccountPushInfo> entry: readyPushData.entrySet()) {
							
							threadPool.execute(new Runnable() {
								@Override
								public void run() {
									//推送产生发放记录任务
									try {
										logger.info(">>>开始向目标服务推送产生发放记录的数据: {}",JSON.toJSONString(entry.getValue()));
										activityApiService.autoPrizeRecord(entry.getValue().getAccountNo(),entry.getValue().getPlatform(),entry.getValue().getEnv(),entry.getValue().getCompanyId());
									}catch(Exception e) {
										logger.error(">>>推送至目标服务异常,请求参数:{},详细错误:{}",JSON.toJSONString(entry.getValue()),e.getMessage(),e);
									}
								}
							});
							
							threadPool.execute(new Runnable() {
								@Override
								public void run() {
									//推送赠金释放任务
									try {
										logger.info(">>>开始向目标服务推送需要释放赠金的账号: {}",JSON.toJSONString(entry.getValue()));
										actPrizeRecordApiDubboService.batchRelease(entry.getValue().getAccountNo(), entry.getValue().getEnv(),entry.getValue().getCompanyId());
									}catch(Exception e) {
										logger.error(">>>推送至目标服务异常,请求参数:{},详细错误:{}",JSON.toJSONString(entry.getValue()),e.getMessage(),e);
									}
								}
							});
							
						}
						
						readyPushData.clear();//清空所有的数据
						logger.info(">>>本轮推送数据到目标耗时{}毫秒",System.currentTimeMillis()-lastRunTime.getTime());
						
					}catch(Exception e) {
						logger.error(">>>本轮推送数据异常,详细错误:{}",e.getMessage(),e);
					}
				}
				
			}
		}.start();
	}
}
