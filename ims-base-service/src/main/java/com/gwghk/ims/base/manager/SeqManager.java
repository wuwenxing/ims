package com.gwghk.ims.base.manager;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.base.dao.entity.Seq;
import com.gwghk.ims.base.dao.inf.SeqDao;
import com.gwghk.ims.base.util.SeqUtil;
import com.gwghk.unify.framework.cache.redis.cluster.RedisCacheTemplate;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.support.CrudService;

/**
 * 摘要：统一序列号业务处理(采用Redis存取)
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年3月2日
 */
@Component
@Transactional
public class SeqManager extends CrudService<SeqDao,Seq> {
    private static Logger logger = LoggerFactory.getLogger(SeqManager.class);
    
    public final static String  REDIS_KEY_SEQ = "Ims:Seq";
    
    final Lock lock = new ReentrantLock();
    
    @Autowired
	private RedisCacheTemplate redisCacheTemplate;
    
    /**
     * 功能：获取最新的序列号
     */
    public String updateSeq(String seqCode,Long companyId) {
        if(StringUtils.isBlank(seqCode)){
            return null;
        }
        Seq seq = this.getDao().getByExpression(new Query().eq("seq_code",seqCode).eq("company_id",companyId));
        if(seq != null){
            Long initVal = seq.getInitVal(),newVal = 0L,
                 incrVal = seq.getIncrVal(),
                 curVal = (seq.getCurVal() == null ? initVal:seq.getCurVal());
            String redisfield = seqCode;
            lock.lock();
            try{
                Long redisVal = 0L,dbVal = 0L;
                //step1: 先判断redis中是否有对应业务序列的值与缓存的值比较
                Object redisOldValObj = redisCacheTemplate.hget(REDIS_KEY_SEQ, redisfield);
                if(redisOldValObj != null){
                	AtomicLong redisOldAtomic = new AtomicLong(Long.valueOf(redisOldValObj.toString()));
                	redisVal = redisOldAtomic.addAndGet(incrVal);
                }
                AtomicLong zeroAtomic = new AtomicLong(curVal);
                dbVal = zeroAtomic.addAndGet(incrVal);
                newVal = redisVal > dbVal ? redisVal : dbVal;//redis和db的值，谁大取谁值
                //step2: 将序列最新值设置到redis
                redisCacheTemplate.hset(REDIS_KEY_SEQ, redisfield, newVal.toString());
                //step3: 将序列最新值异步更新到序列表(方便后台查询列表展示),同时更新本地化缓存,提升并发速度
                this.getDao().updateByCode(seqCode,newVal);
                //step4: 按照某种规则重新组装序列
                String result = SeqUtil.buildSeqByRule(seq,newVal);
                logger.info("【 new seq no :{}】",result);
                return result;
            }catch(Exception e){
                //step5: 如果redis挂了,则保底，直接从数据库中求最新值返回
                logger.error("updateSeq->redis is down,please check!",e);
                AtomicLong curAtomic = new AtomicLong(curVal);
                final Long dbVal = curAtomic.addAndGet(incrVal);
                this.getDao().updateByCode(seqCode,dbVal);
                String result = SeqUtil.buildSeqByRule(seq, dbVal);
                logger.info("【new seq no :{}】",result);
                return result;
            }finally{
                lock.unlock();
            }
        }else{
            logger.error("updateSeq->no seq config info,please config!");
            return null;
        }
    }
}