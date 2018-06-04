package com.gwghk.ims.message.consumer.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.common.enums.DataSourceTypeEnum;
import com.gwghk.ims.datasource.data.DataSourceContext;
import com.gwghk.ims.message.dao.entity.Gts2CountryDict;
import com.gwghk.ims.message.dao.inf.Gts2CountryDictDao;
import com.gwghk.ims.message.dto.KafkaGTS2CommonData;
import com.gwghk.ims.message.utils.GwConstants;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: KafkaGts2CountryDictDataHandle
 * @Description: GTS2国籍字典同步
 * @author lawrence
 * @date 2018年3月27日
 *
 */
@Component
public class KafkaGts2OriCountryDictDataHandle extends KafkaGts2CommonDataHandle {
	private final static Logger logger = LoggerFactory.getLogger(KafkaGts2OriCountryDictDataHandle.class);

	//final Lock lock = new ReentrantLock();

	@Autowired
	private Gts2CountryDictDao gts2CountryDictDao;

	/**
	 * 功能：处理消息
	 */
	public void handleMessage(String message) {
		try {
			logger.info("开始-GTS2-国籍数据字典->活动中心同步！");
			//lock.lock();
			KafkaGTS2CommonData commonData = JsonUtil.json2Obj(message, KafkaGTS2CommonData.class);
			if (commonData != null) {
				if ((GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())
						|| GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA()))
						&& StringUtil.isNotEmpty(commonData.getN())) {
					Gts2CountryDict gts2CountryDict = JsonUtil.json2Obj(commonData.getN(), Gts2CountryDict.class);
					//国家字段数据目前没有必要拿取，默认是放入base数据库，貌似base数据库并没有没有加入这张表
					/*CREATE TABLE `t_country_dict` (
							`id` BIGINT(20) NOT NULL COMMENT 'table id, 這ID在這TABLE沒有任何用處',
							`code` VARCHAR(20) NULL DEFAULT NULL COMMENT '國家ID，ISO開頭的就是國家，省份關聯國家是用到的',
							`parent_code` VARCHAR(20) NULL DEFAULT NULL COMMENT '如果是-1，即是國家，如是正數，即是省份/城市，而當中的parent_country_id就是所屬的國家ID',
							`national_code` VARCHAR(20) NULL DEFAULT NULL COMMENT '國家的2個英文字母簡稱',
							`country_code` VARCHAR(20) NULL DEFAULT NULL COMMENT '國家區號/電話區號, 86/852等等',
							`valid` INT(11) NULL DEFAULT NULL COMMENT '是否有效，默认有效',
							`name_cn` VARCHAR(100) NULL DEFAULT NULL COMMENT '简体名称',
							`name_tw` VARCHAR(100) NULL DEFAULT NULL COMMENT '繁体名称',
							`name_en` VARCHAR(100) NULL DEFAULT NULL COMMENT '英文名称',
							`sort` INT(11) NULL DEFAULT NULL COMMENT '排序',
							`create_user` VARCHAR(50) NULL DEFAULT NULL,
							`create_ip` VARCHAR(50) NULL DEFAULT NULL,
							`create_date` DATETIME(6) NULL DEFAULT NULL,
							`update_user` VARCHAR(50) NULL DEFAULT NULL,
							`update_ip` VARCHAR(50) NULL DEFAULT NULL,
							`update_date` DATETIME(6) NULL DEFAULT NULL,
							`version_no` INT(11) NOT NULL,
							PRIMARY KEY (`id`),
							INDEX `idx_country_dict_parent_code` (`parent_code`, `name_tw`, `valid`) USING BTREE,
							INDEX `idx_t_country_dict_name_cn` (`parent_code`, `name_cn`, `valid`)
						)
						COLLATE='utf8_general_ci'
						ENGINE=InnoDB
						;*/
					DataSourceContext.setCompany(DataSourceTypeEnum.BASE_DATA, null);
					if (GwConstants.KAFKA_GTS2_OP_INSERT.equals(commonData.getA())) {
						gts2CountryDictDao.insert(gts2CountryDict);
					} else if (GwConstants.KAFKA_GTS2_OP_UPDATE.equals(commonData.getA())) {
						if (message.indexOf("\"O\":{}") > 0) {
							try {
								gts2CountryDictDao.insert(gts2CountryDict);
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
								gts2CountryDictDao.update(gts2CountryDict);
							}
						} else {
							gts2CountryDictDao.update(gts2CountryDict);
						}
					}
					logger.info("完成-GTS2-国籍数据字典->活动中心同步");
				}
			} else {
				logger.warn("GTS2-国籍数据字典同步没有产生记录！时间:{}", new Date());
			}
		} catch (Exception e) {
			logger.info("GTS2-国籍数据kafka数据格式错误{},消息内容{}", e,message);
		} finally {
			//lock.unlock();
		}
	}

}
