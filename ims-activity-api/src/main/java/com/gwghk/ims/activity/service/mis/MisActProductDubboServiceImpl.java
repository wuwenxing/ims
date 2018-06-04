package com.gwghk.ims.activity.service.mis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.activity.dao.entity.ActProduct;
import com.gwghk.ims.activity.manager.ActProductManager;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.activity.MisActProductDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActProductVO;

/**
 * 摘要：产品维护
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月7日
 */
@Service
public class MisActProductDubboServiceImpl implements MisActProductDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisActProductDubboServiceImpl.class);
	
	@Autowired
	private ActProductManager actProductManager;
	
	public MisRespResult<ActProductVO> findByProductCode(String productCode, Long companyId) {
		logger.info("findByProductCode-->【ProductCode:{}, companyId:{}】", productCode, companyId);
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ActProductVO(), actProductManager.findByProductCode(productCode, companyId)));
		}catch(Exception e){
			logger.error("<--系统异常,【findByProductCode】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<PageR<ActProductVO>> findPageList(ActProductVO vo) {
		logger.info("findPageList-->【ProductCode:{}, companyId:{}】", vo.getProductCode(), vo.getCompanyId());
		try{
			PageR<ActProductVO> pageR = actProductManager.findPageList(vo);
			return MisRespResult.success(pageR);
		}catch(Exception e){
			logger.error("<--系统异常,【findPageList【】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<List<ActProductVO>> findList(ActProductVO vo) {
		logger.info("findList-->【ProductCode:{}, companyId:{}】", vo.getProductCode(), vo.getCompanyId());
		try{
			List<ActProduct> systemUserList = actProductManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(systemUserList, ActProductVO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findList【】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ActProductVO> findById(Long id) {
		logger.info("findById-->【id:{}】", id);
		try{
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ActProductVO(), actProductManager.findById(id)));
		}catch(Exception e){
			logger.error("<--系统异常,【findById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> saveOrUpdate(ActProductVO vo) {
		logger.info("saveOrUpdate-->【productCode:{}, companyId:{}】", vo.getProductCode(), vo.getCompanyId());
		try{
			actProductManager.saveOrUpdate(vo);
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常，【saveOrUpdate】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> deleteByIdArray(String ids) {
		logger.info("deleteById-->【ids:{}】", ids);
		try{
			actProductManager.deleteByIdArray(ids);
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常，【deleteById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}