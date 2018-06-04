package com.gwghk.ims.marketingtool.service.mis;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.enums.SmsSourceEnum;
import com.gwghk.ims.common.inf.mis.marketingtool.MisImsMsgSmsLogDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgSmsLogVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgSmsLog;
import com.gwghk.ims.marketingtool.enums.AppPushMsgStatusEnum;
import com.gwghk.ims.marketingtool.manager.ImsMsgSmsLogManager;
import com.gwghk.ims.marketingtool.manager.ImsMsgSmsManager;

/**
 * 摘要：短信记录服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月15日
 */
@Service
public class MisImsMsgSmsLogDubboServiceImpl implements MisImsMsgSmsLogDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisImsMsgSmsLogDubboServiceImpl.class);

	@Autowired
	private ImsMsgSmsLogManager imsMsgSmsLogManager;
	
	@Autowired
	private ImsMsgSmsManager imsMsgSmsManager;
	
	/**
	 * 功能: 分页查询
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> findPageList(ImsMsgSmsLogVO vo,@Company Long companyId) {
		try {
			Map<String,Object> map = imsMsgSmsLogManager.findPageList(vo);
			Object obj = map.get("list");
			if(obj != null){
				List<ImsMsgSmsLogVO> list = (List<ImsMsgSmsLogVO>)obj;
				for(ImsMsgSmsLogVO ims:list){
					ims.setSource(SmsSourceEnum.getLabelByValue(ims.getSource()));
					ims.setStatus(AppPushMsgStatusEnum.getLabelKeyByValue(ims.getStatus()));
				}
			}
			return map;
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return null;
		}
	}

	/**
	 * 功能：获取短信记录列表
	 */
	public MisRespResult<List<ImsMsgSmsLogVO>> findList(ImsMsgSmsLogVO vo,@Company Long companyId) {
		try {
			List<ImsMsgSmsLog> list = imsMsgSmsLogManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, ImsMsgSmsLogVO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ImsMsgSmsLogVO> findById(Long id,@Company Long companyId) {
		try {
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ImsMsgSmsLogVO(), imsMsgSmsLogManager.findById(id)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：保存或修改记录
	 */
	@Override
	public MisRespResult<Void> saveOrUpdate(ImsMsgSmsLogVO vo,@Company Long companyId) {
		logger.info("saveOrUpdate-->【code:{},mobile:{},companyId:{}】",vo.getCode(),vo.getMobile(),companyId);
		try {
			return imsMsgSmsManager.sendSmsMsgByCustom(vo.getMobile(),vo.getContent(),vo.getBusinessNo(),vo.getRemark(),companyId);
		} catch (Exception e) {
			logger.error("<--系统异常，【saveOrUpdate】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：批量删除记录
	 */
	@Override
	public MisRespResult<Void> deleteByIds(String ids,@Company Long companyId) {
		logger.info("deleteByIds-->【ids:{},companyId:{}】",ids,companyId);
		try {
			int num  = imsMsgSmsLogManager.deleteByIds(ids);
			logger.info("批量删除短信记录成功！条数:{}",num);
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【deleteByIds】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
