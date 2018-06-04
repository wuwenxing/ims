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
import com.gwghk.ims.common.inf.mis.marketingtool.MisImsMsgSmsTplDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgSmsTplVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgSmsTpl;
import com.gwghk.ims.marketingtool.manager.ImsMsgSmsTplManager;

/**
 * 摘要：短信模板服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年4月15日
 */
@Service
public class MisImsMsgSmsTplDubboServiceImpl implements MisImsMsgSmsTplDubboService{

	private static final Logger logger = LoggerFactory.getLogger(MisImsMsgSmsTplDubboServiceImpl.class);

	@Autowired
	private ImsMsgSmsTplManager imsMsgSmsTplManager ;

	/**
	 * 功能: 分页查询
	 */
	public  Map<String,Object> findPageList(ImsMsgSmsTplVO vo,@Company Long companyId) {
		try {
			return imsMsgSmsTplManager.findPageList(vo);
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return null;
		}
	}

	public MisRespResult<List<ImsMsgSmsTplVO>> findList(ImsMsgSmsTplVO vo,@Company Long companyId) {
		try {
			List<ImsMsgSmsTpl> list = imsMsgSmsTplManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, ImsMsgSmsTplVO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ImsMsgSmsTplVO> findById(Long id,@Company Long companyId) {
		try {
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ImsMsgSmsTplVO(), imsMsgSmsTplManager.findById(id)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：保存或修改记录
	 */
	@Override
	public MisRespResult<Void> saveOrUpdate(ImsMsgSmsTplVO vo,@Company Long companyId) {
		logger.info("saveOrUpdate-->【code:{},tplName:{},companyId:{}】", vo.getCode(),vo.getTplName(),companyId);
		try {
			return imsMsgSmsTplManager.saveOrUpdate(vo);
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
			int sum = imsMsgSmsTplManager.deleteByIds(ids);
			logger.info("批量删除短信模板成功!共{}条",sum);
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【deleteByIds】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}