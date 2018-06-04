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
import com.gwghk.ims.common.inf.mis.marketingtool.MisImsMsgBindDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgBindVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgBind;
import com.gwghk.ims.marketingtool.enums.AppMsgBindTypeEnum;
import com.gwghk.ims.marketingtool.manager.ImsMsgBindManager;

/**
 * 摘要：消息绑定服务实现类
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月15日
 */
@Service
public class MisImsMsgBindDubboServiceImpl implements MisImsMsgBindDubboService{

	private static final Logger logger = LoggerFactory.getLogger(MisImsMsgBindDubboServiceImpl.class);

	@Autowired
	private ImsMsgBindManager imsMsgBindManager ;

	/**
	 * 功能: 分页查询
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> findPageList(ImsMsgBindVO vo,@Company Long companyId) {
		try {
			Map<String,Object> map = imsMsgBindManager.findPageList(vo);
			Object obj = map.get("list");
			if(obj != null){
				List<ImsMsgBindVO> list = (List<ImsMsgBindVO>)obj;
				for(ImsMsgBindVO ims : list){
					ims.setBindTypeStr(AppMsgBindTypeEnum.getLabelKeyByValue(ims.getBindType()));
				}
			}
			return map;
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return null;
		}
	}

	public MisRespResult<List<ImsMsgBindVO>> findList(ImsMsgBindVO vo,@Company Long companyId) {
		try {
			List<ImsMsgBind> list = imsMsgBindManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, ImsMsgBindVO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ImsMsgBindVO> findById(Long id,@Company Long companyId) {
		logger.info("findById-->【id:{}】", id);
		try {
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ImsMsgBindVO(), imsMsgBindManager.findById(id)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：保存或修改记录
	 */
	@Override
	public MisRespResult<Void> saveOrUpdate(ImsMsgBindVO vo,@Company Long companyId) {
		logger.info("saveOrUpdate-->【bindCode:{},appCode:{},smsCode:{},companyId:{}】",vo.getBindCode(),vo.getAppCode(),
				vo.getSmsCode(),companyId);
		try {
			logger.info("保存或修改消息绑定信息成功！");
			return imsMsgBindManager.saveOrUpdate(vo);
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
		logger.info("deleteByIds-->【ids:{},companyId:{}】", ids,companyId);
		try {
			int num = imsMsgBindManager.deleteByIds(ids);
			logger.info("批量删除消息绑定信息成功！条数:{}",num);
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【deleteByIds】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
