package com.gwghk.ims.message.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.unify.framework.cache.redis.cluster.RedisCacheTemplate;

/**
 * 摘要：分布式锁管理类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年7月5日
 */
//@Component
public class DistributedLockManager {
	private static final Logger logger = LoggerFactory.getLogger(DistributedLockManager.class);
	
    @Autowired
    private RedisCacheTemplate redisCacheTemplate;
	
	private String currentLock = null;
	
    /**
     * 判断是否获取同步缓存锁
     * @param key
     * @return
     */
    /*
	public boolean checkSyncLock(){
    	String key = "schedule_lock";
        //判断currentLock是否为空，否则初始化，并且设置为抢占锁。设置过期时间为1分钟。     
        if(this.currentLock==null || "".equals(this.currentLock)){
            this.currentLock = System.currentTimeMillis() + "-" + new Random().nextInt(1000);
        }       
        String scheduleLock = (String) this.redisCacheTemplate.get(key);
        if (scheduleLock == null || "".equals(scheduleLock)) {
            this.redisCacheTemplate.set(key, this.currentLock, 30);
            scheduleLock = this.currentLock;
        }        
        if(this.currentLock.equals(scheduleLock)){
            this.redisCacheTemplate.set(key, this.currentLock, 30);
            logger.info("#获取同步缓存锁.currentLock: "+currentLock);     
            return true;
        }else{
            return false;
        }   
    }
    */
}
