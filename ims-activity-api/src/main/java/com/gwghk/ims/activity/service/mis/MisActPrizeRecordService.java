package com.gwghk.ims.activity.service.mis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ActPrizeRecord;
import com.gwghk.ims.activity.dao.inf.ActItemsSettingDao;
import com.gwghk.ims.activity.dao.inf.ActPrizeRecordDao;
import com.gwghk.ims.common.enums.CompanyEnum;

/**
 * 摘要：活动奖品记录业务处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月12日
 */
@Service
public class MisActPrizeRecordService {
	private static final Logger logger = LoggerFactory.getLogger(MisActPrizeRecordService.class);

	@Autowired
	private ActPrizeRecordDao actPrizeRecordDao;
	
	@Autowired
	private ActItemsSettingDao actItemsSettingDao;
	
	/**
	 * 功能：根据recordNumber查询活动奖品设置
	 */
	public ActPrizeRecord findByRecordNumber(String recordNumber,Long companyId) {
		String companyCode=CompanyEnum.getCodeById(companyId);
		ActPrizeRecord record=actPrizeRecordDao.selectByRecordNumber(recordNumber,companyId,companyCode);
		//record.setCompanyCode(companyCode);
		return record;
	}
	
	/**
	 * 更新状态
	 * @param actPrizeRecord
	 */
	public int updateIssuingStatus(ActPrizeRecord actPrizeRecord) {
		return actPrizeRecordDao.updateByPrimaryKey(actPrizeRecord);
	}
	
	 
}