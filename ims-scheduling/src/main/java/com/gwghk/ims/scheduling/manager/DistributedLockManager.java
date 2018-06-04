package com.gwghk.ims.scheduling.manager;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.unify.framework.cache.redis.cluster.RedisCacheTemplate;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：分布式同步锁处理
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年7月19日
 */
@Component
public class DistributedLockManager {
	private static final Logger logger = LoggerFactory.getLogger(DistributedLockManager.class);
	
	@Autowired
    private RedisCacheTemplate redisCacheTemplate;
	
	private String currentLock = null;
	
    /**
     * 功能: 获取锁
     * @param lockKey  分布式锁key
     * @return true 获取锁  false 未获取锁
     */
    public boolean getLock(String lockKey){
    	try{
    		String key = StringUtil.isNullOrEmpty(lockKey) ? "schedule_lock" : lockKey;
            //判断currentLock是否为空，否则初始化，并且设置为抢占锁,同时设置过期时间,访问事务中断 
            if(this.currentLock==null || "".equals(this.currentLock)){
                this.currentLock = System.currentTimeMillis() + "-" + new Random().nextInt(1000);
            }       
            String scheduleLock = this.redisCacheTemplate.get(key);
            if (scheduleLock == null || "".equals(scheduleLock)) {
                this.redisCacheTemplate.set(key, this.currentLock, 3);
                scheduleLock = this.currentLock;
            }
            if(this.currentLock.equals(scheduleLock)){
                this.redisCacheTemplate.set(key, this.currentLock, 3);
                logger.info("#获取同步缓存锁.currentLock: "+currentLock);  
                return true;
            }else{
                return false;
            } 
    	}catch(Exception e){
    		logger.error(e.getMessage(),e);
    		return true;
    	}
    }
    
    /**
     * 功能: 释放锁
     * @param lockKey 分布式锁key
     */
    public void releaseLock(String lockKey){
    	try{
    		this.redisCacheTemplate.del(lockKey);
    	}catch(Exception e){
    		logger.error(e.getMessage(),e);
    	}
    }
}
