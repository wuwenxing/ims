package com.gwghk.ims.activity.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ImsTaskRecord;
import com.gwghk.ims.activity.dao.inf.ImsTaskRecordDao;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ImsTaskRecordVO;


/**
 * 
 * 摘要：用户任务记录manager
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月7日
 */
@Component
public class ImsTaskRecordManager {
	
	Logger logger = LoggerFactory.getLogger(ImsTaskRecordManager.class) ;

	@Autowired
	private ImsTaskRecordDao imsTaskRecordDao ;
	
	/**
	 * 根据ID查询
	 */
	public ImsTaskRecord findById(int id) {
		return imsTaskRecordDao.findObject(id) ;
	}
	
	public ImsTaskRecord findById(int id,String actNo) {
		return imsTaskRecordDao.findById(id,actNo) ;
	}
	/**
	 * 功能：查询列表
	 */
	public List<ImsTaskRecord> findListByAccount(String actNo,String accNo,String platform,Integer actTaskSettingId,String itemNo,Integer taskGroup) {
		ImsTaskRecordVO record = new ImsTaskRecordVO() ;
		record.setActNo(actNo);
		record.setAccountNo(accNo);
		record.setPlatform(platform);
		record.setActTaskSettingId(actTaskSettingId);
		record.setItemNo(itemNo);
		record.setTaskGroup(taskGroup);
		return findList(actNo, record) ;
	}
	
	/**
	 * 功能：查询列表
	 */
	@SuppressWarnings("unchecked")
	public List<ImsTaskRecord> findList(String actNo,ImsTaskRecordVO record) {
		Map<String, Object> map = ImsBeanUtil.toMap(record) ;
		map.put("actNo", actNo) ;
		return imsTaskRecordDao.findListByMap(map) ;
	}
	
	/**
	 * 功能：查询需要进行发放记录发放的列表
	 */
	public List<ImsTaskRecord> findNeedRecordList(String actNo) {
		return imsTaskRecordDao.findNeedRecordList(actNo) ;
	}
	
	/**
	 * 查询最后一个任务的完成时间
	 * @param actNo
	 * @param accNo
	 * @param platform
	 * @param actTaskSettingId
	 * @return
	 */
	public Date findMaxTaskFinishTime(String actNo,String accNo,String platform,Integer actTaskSettingId,Integer taskGroup){
		return imsTaskRecordDao.findMaxTaskFinishTime(actNo, accNo, platform, actTaskSettingId,null,taskGroup) ;
	}
	
	/**
	 * 功能：新增或修改
	 */
	public MisRespResult<Void> saveOrUpdate(ImsTaskRecord record) {
		try{
			if (null == record.getId()) {
				imsTaskRecordDao.save(record) ;
			} else {
				imsTaskRecordDao.update(record) ;
			}
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常,【saveOrUpdate】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
}