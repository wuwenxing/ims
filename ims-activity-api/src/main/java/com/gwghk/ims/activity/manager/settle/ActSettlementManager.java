package com.gwghk.ims.activity.manager.settle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ActAccountActiviStat;
import com.gwghk.ims.activity.manager.ActAccountActiviStatManager;
import com.gwghk.ims.activity.util.BeanFactoryUtil;
import com.gwghk.ims.common.enums.ActSettleStatusEnum;
import com.gwghk.ims.common.enums.ActSettleTypeEnum;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.BaseVO;
import com.gwghk.ims.common.vo.activity.ActAccountActiviStatVO;

/**
 * 
 * 摘要：用户清算业务处理
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月29日
 */
@Component
public class ActSettlementManager {
	private static Logger logger = LoggerFactory.getLogger(ActSettlementManager.class);
	@Autowired
	private ActAccountActiviStatManager actAccountActiviStatManager ;
	
	List<ActAccountActiviStat> settlementList = new ArrayList<>() ;
	
	/**
	 * 开始对用户进行清算
	 * @param actNo
	 * @param accNo
	 * @param platform
	 * @param companyId
	 */
	public void beginUserSettlement(ActSettleTypeEnum mode,String actNo,String accNo,String platform,BaseVO base) {
		logger.info("开始对用户进行清算：actNo:{},accNo:{},platform:{},companyId:{}",new Object[]{actNo,accNo,platform,base.getCompanyId()});
		ActAccountActiviStat stat = actAccountActiviStatManager.findByAccountNo(accNo, actNo, platform) ;
		if(null == stat) return ;
		ImsBeanUtil.copyNotNull(stat, base);
		stat.setSettleMode(mode.getCode());
		stat.setSettleStatus(ActSettleStatusEnum.WAITTING.getCode());
		stat.setAboutSettleTime(new Date());
		stat.setUpdateDate(new Date());
		actAccountActiviStatManager.saveOrUpdate(stat) ;
		settlementList.add(stat) ;
	}
	
	/**
	 * 用户清算处理逻辑
	 * @param actNo
	 * @param accNo
	 * @param platform
	 * @param companyId
	 */
	private void userSettlement(ActAccountActiviStat stat){
		logger.info("用户进行清算：actNo:{},accNo:{},platform:{},companyId:{}",new Object[]{stat.getActNo(),stat.getAccountNo(),stat.getPlatform(),stat.getCompanyId()});
		AbstractSettlementType baseTaskBean = BeanFactoryUtil.getActSettleTypeBean(stat.getSettleMode()) ;
		stat.setSettleMode(ActSettleStatusEnum.SETTLING.getCode());
		stat.setUpdateDate(new Date());
		if(baseTaskBean.doSettlement(stat)){
			stat.setSettleStatus(ActSettleStatusEnum.SETTLESUCC.getCode());
			stat.setRealSettleTime(new Date());
		}
		actAccountActiviStatManager.saveOrUpdate(stat) ;
	}
	
	/**
	 * 把待清算的列表，执行清算操作
	 */
	@PostConstruct
	private void sysnUserSettlement(){
		//启动的时候先初始化任务
		if(settlementList.size() == 0){
			initSettlementList(); 
			//needSettlementAct();
		}
		Thread th = new Thread(){
			@Override
			public void run() {
				while(true){
					try{
						for(int i = 0 ;i< settlementList.size() ;i++) {
							ActAccountActiviStat act = settlementList.get(i) ;
							logger.debug("开始清算，活动：{}，用户：{}：",new Object[]{act.getActNo(),act.getAccountNo()});
							userSettlement(act) ;
							settlementList.remove(act); 
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};th.start();
	}
	
	/**
	 * 初始化需要等待完成的任务
	 */
	private void initSettlementList(){
		ActAccountActiviStatVO vo = new ActAccountActiviStatVO() ;
		vo.setSettleStatus(ActSettleStatusEnum.WAITTING.getCode());
		List<ActAccountActiviStat> list = actAccountActiviStatManager.findList(vo) ;
		for(ActAccountActiviStat act : list) {
			try{
				settlementList.add(act) ;
			}catch(Exception e){
				logger.error("初始化任务记录异常：{}",e);
			}
		}
	}
	
	/**
	 * 监听到时间需要监控的活动
	 */
	private void needSettlementAct(){
		Thread th = new Thread(){
			@Override
			public void run() {
				while(true){
					try{
						ActAccountActiviStatVO vo = new ActAccountActiviStatVO() ;
						vo.setSettleStatus(ActSettleStatusEnum.UNSETTLE.getCode());
						List<ActAccountActiviStat> list = actAccountActiviStatManager.findList(vo) ;
						for(ActAccountActiviStat act : list) {
							try{
								//判断活动是否到清算时间，如果到了则进入待清算状态
								if(act.getAboutSettleTime().compareTo(new Date()) > 0){
									settlementList.add(act) ;
								}
							}catch(Exception e){
								logger.error("初始化任务记录异常：{}",e);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};th.start();
	}
}
