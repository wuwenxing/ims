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
import com.gwghk.ims.common.inf.mis.marketingtool.MisImsMsgAppLogDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgAppLogVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgAppLog;
import com.gwghk.ims.marketingtool.enums.AppMsgPushContentTypeEnum;
import com.gwghk.ims.marketingtool.enums.AppMsgShowPositionEnum;
import com.gwghk.ims.marketingtool.enums.AppMsgTypeEnum;
import com.gwghk.ims.marketingtool.enums.AppPushMsgStatusEnum;
import com.gwghk.ims.marketingtool.enums.AppPushPlatformEnum;
import com.gwghk.ims.marketingtool.enums.AppPushTypeEnum;
import com.gwghk.ims.marketingtool.manager.ImsMsgAppLogManager;
import com.gwghk.ims.marketingtool.manager.ImsMsgAppManager;

/**
 * 摘要：App消息记录Service
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月15日
 */
@Service
public class MisImsMsgAppLogDubboServiceImpl implements MisImsMsgAppLogDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisImsMsgAppLogDubboServiceImpl.class);

	@Autowired
	private ImsMsgAppLogManager imsMsgAppLogManager;
	
	@Autowired
	private ImsMsgAppManager imsMsgAppManager;
	
	/**
	 * 功能: 分页查询
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> findPageList(ImsMsgAppLogVO vo,@Company Long companyId) {
		try {
			Map<String,Object> map = imsMsgAppLogManager.findPageList(vo);
			Object obj = map.get("list");
			if(obj != null){
				List<ImsMsgAppLogVO> list = (List<ImsMsgAppLogVO>)obj;
				for(ImsMsgAppLogVO ims:list){
					formatRecord(ims) ;
				}
			}
			return map;
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return null;
		}
	}
	
	/**
	 * 功能：获取App消息记录列表
	 */
	public MisRespResult<List<ImsMsgAppLogVO>> findList(ImsMsgAppLogVO vo,@Company Long companyId) {
		try {
			List<ImsMsgAppLog> list = imsMsgAppLogManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, ImsMsgAppLogVO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
 
	/**
	 * 功能：获取消息记录信息
	 */
	@Override
	public MisRespResult<ImsMsgAppLogVO> findById(Integer id,@Company Long companyId) {
		try {
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ImsMsgAppLogVO(), imsMsgAppLogManager.findById(id)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：保存或修改记录
	 */
	@Override
	public MisRespResult<Void> saveOrUpdate(ImsMsgAppLogVO vo,@Company Long companyId) {
		logger.info("saveOrUpdate-->【code:{},recipents:{},companyId:{}】", vo.getCode(),vo.getRecipents(),companyId);
		try {
			return imsMsgAppManager.sendAppMsgByCustom(ImsBeanUtil.copyNotNull(new ImsMsgAppLog(),vo), companyId);
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
			int num = imsMsgAppLogManager.deleteByIds(ids);
			logger.info("<--批量删除App消息记录成功！条数:{}",num);
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【deleteByIds】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 功能：重发消息记录
	 */
	@Override
	public MisRespResult<Void> resend(ImsMsgAppLogVO vo, Long companyId) {
		String idArray = vo.getIds() ;
		String[] ids = idArray.split(",");
		if(ids != null && ids.length > 0){
			for(String id : ids){
				try {
					ImsMsgAppLog msg = imsMsgAppLogManager.findById(Integer.valueOf(id)) ;
					if(null == msg || AppPushMsgStatusEnum.waiting.equals(msg.getStatus())){
						return MisRespResult.error(MisResultCode.Msg40010) ;
					}
					ImsMsgAppLog record = ImsBeanUtil.copyNotNull(new ImsMsgAppLog(),vo);
					record.setId(Integer.valueOf(id));
					record.setStatus(AppPushMsgStatusEnum.waiting.getValue());
					imsMsgAppLogManager.saveOrUpdate(record);
				} catch (Exception e) {
					logger.info("resend app msg exception :",e);
					return MisRespResult.error(MisResultCode.EXCEPTION) ;
				} 
			} 
		} 
		return MisRespResult.success() ; 
	}
	
	private void formatRecord(ImsMsgAppLogVO record){
		record.setContentTypeTxt(AppMsgPushContentTypeEnum.getLabelKeyByValue(record.getContentType()));
		record.setStatus(AppPushMsgStatusEnum.getLabelKeyByValue(record.getStatus()));
		record.setPushType(AppPushTypeEnum.getLabelKeyByValue(record.getPushType()));
		record.setMsgShowPosition(AppMsgShowPositionEnum.getLabelKeyByValues(record.getMsgShowPosition()));
		record.setMsgTypeTxt(AppMsgTypeEnum.getValueByCode(record.getMsgType()));
	}
}