package com.gwghk.ims.message.consumer.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.ims.message.dao.entity.Gts2Group;
import com.gwghk.ims.message.dao.inf.Gts2GroupDao;
import com.gwghk.ims.message.dto.KafkaGTS2CommonData;
import com.gwghk.ims.message.manager.InitDataManager;
import com.gwghk.ims.message.utils.GwConstants;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: KafkaGts2OriGroupDataHandle
 * @Description: GTS2交易组数据同步
 * @author lawrence
 * @date 2017年8月18日
 *
 */
@Component
public class KafkaGts2OriGroupDataHandle extends KafkaGts2CommonDataHandle {
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2OriGroupDataHandle.class);

	//final Lock lock = new ReentrantLock();

	@Autowired
	private Gts2GroupDao gts2GroupDao;

	@Autowired
	private InitDataManager initDataManager;

	/**
	 * 功能：处理消息
	 */
	public void handleMessage(String message, String cmp, String env) {
		try {
			logger.info("开始-GTS2({})-{}交易组数据->活动中心同步！", cmp, env);
			//lock.lock();
			KafkaGTS2CommonData commonData = JsonUtil.json2Obj(message, KafkaGTS2CommonData.class);
			if (commonData != null) {
				if ((GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())
						|| GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA()))
						&& StringUtil.isNotEmpty(commonData.getN())) {
					Gts2Group gts2Group = JsonUtil.json2Obj(commonData.getN(), Gts2Group.class);
					gts2Group.setEnv(env);
					DataSourceContext.setCompany(DataSourceTypeEnum.BASE_DATA, null);
					saveOrUpdate(message,env,commonData, gts2Group);
					DataSourceContext.setCompany(DataSourceTypeEnum.BUSINESS_DATA, gts2Group.getCompanyid());
					saveOrUpdate(message,env,commonData, gts2Group);
					if (gts2Group.getId() != null && gts2Group.getCompanyid() != null) {
						if (ActEnvEnum.DEMO.getValue().equals(env)) {
							initDataManager.resetGts2DemoDealGroup(new Long(gts2Group.getId()),
									new Long(gts2Group.getCompanyid()));
						} else if (ActEnvEnum.REAL.getValue().equals(env)) {
							initDataManager.resetGts2RealDealGroup(new Long(gts2Group.getId()),
									new Long(gts2Group.getCompanyid()));
						}
					}
					logger.info("完成-GTS2({})-{}交易组数据->活动中心同步", cmp, env);
				}
			} else {
				logger.warn("GTS2({})-{}交易组数据同步没有产生记录！时间:{}", cmp, env, new Date());
			}
		} catch (Exception e) {
			logger.info("GTS2({})-{}交易组数据kafka数据格式错误{},消息内容{}", cmp, env, e,message);
		} finally {
			//lock.unlock();
		}
	}

	private void saveOrUpdate(String message, String env,KafkaGTS2CommonData commonData, Gts2Group gts2Group) {
		try {
			if (GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())) {
				gts2GroupDao.save(gts2Group);
			} else if (GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA())) {
				if (message.indexOf("\"O\":{}")>-1  || gts2GroupDao.update(gts2Group)<=0) //当不存在旧数据 或者 更新失败(以前漏插入一条)
					gts2GroupDao.save(gts2Group);
			}
		}catch(DuplicateKeyException ex) {
			gts2GroupDao.update(gts2Group);//处理重复插入数据的情况
			logger.warn("GTS2-{}交易组数据->活动中心同步,插入消息重复,改为更新,消息内容{}",env,message);
		}
	}

}
