package com.gwghk.ims.activity.service.mis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ActStringCode;
import com.gwghk.ims.activity.manager.ActStringCodeManager;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.activity.MisActStringCodeDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActStringCodeVO;

/**
 * 摘要：物品设置实现
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年3月28日
 */
@Service
public class MisActStringCodeDubboServiceImpl implements MisActStringCodeDubboService {
	private static final Logger logger = LoggerFactory.getLogger(MisActStringCodeDubboServiceImpl.class);
	@Autowired
	private ActStringCodeManager actStringCodeManager;

	@Override
	public MisRespResult<PageR<ActStringCodeVO>> findPageList(ActStringCodeVO reqVo,Long companyId) {
		try {
			PageR<ActStringCodeVO> pageR = actStringCodeManager.findPageList(reqVo);
			return MisRespResult.success(pageR);
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<List<ActStringCodeVO>> findList(ActStringCodeVO reqVo,Long companyId) {
		try {
			List<ActStringCodeVO> list= actStringCodeManager.findList(reqVo);
			return MisRespResult.success(list);
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	@Override
	public MisRespResult<ActStringCodeVO> findById(Long id,Long companyId) {
		try {
			return MisRespResult.success(ImsBeanUtil.copyNotNull(
					new ActStringCodeVO(), actStringCodeManager.findById(id)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ActStringCodeVO> findByStringCode(String stringCode,Long companyId) {
		try {
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ActStringCodeVO(),
					actStringCodeManager.findByStringCode(stringCode)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findByStringCode】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> saveOrUpdate(ActStringCodeVO entityVO,Long companyId) {
		logger.info("saveOrUpdate-->【params:{}】", entityVO);
		try {
			return actStringCodeManager.saveOrUpdate(entityVO);
		} catch (Exception e) {
			logger.error("<--系统异常，【saveOrUpdate】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> updateByStringCode(ActStringCodeVO reqVo,Long companyId) {
		logger.info("updateByStringCode-->【params:{}】", reqVo);
		try {
			actStringCodeManager.updateByStringCode(ImsBeanUtil.copyNotNull(new ActStringCode(), reqVo));
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【updateByStringCode】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Integer> getStringCodeCount(ActStringCodeVO reqVo,Long companyId) {
		logger.info("getStringCodeCount-->【params:{}】", reqVo);
		try {
			int count = actStringCodeManager.getStringCodeCount(ImsBeanUtil.copyNotNull(new ActStringCode(), reqVo));
			return MisRespResult.success(count);
		} catch (Exception e) {
			logger.error("<--系统异常，【getStringCodeCount】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}

	}

	@Override
	public MisRespResult<Void> deleteByIds(String idArray,Long companyId) {
		logger.info("deleteByIdArray-->【idArray:{},{}】", idArray,companyId);
		try {
			actStringCodeManager.deleteByIds(idArray);
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【deleteByIdArray】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
 
	@Override
	public MisRespResult<List<String>> findAllStringCodes(Long companyId) {
		try {
			List<String> stringCodes = actStringCodeManager.findAllStringCodes();
			return MisRespResult.success(stringCodes);
		} catch (Exception e) {
			logger.error("<--系统异常，【findAllStringCodes】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
 
	
	@Override
	public MisRespResult<Integer> batchAdd(List<String> stringCodeList,String itemNumber,Long companyId){
		logger.info("batchAdd-->【companyId:{},itemNumber:{}】", companyId,itemNumber);
		try {
			 int count = actStringCodeManager.batchAdd(stringCodeList,itemNumber,companyId);
			return MisRespResult.success(count);
		} catch (Exception e) {
			logger.error("<--系统异常，【batchAdd】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
 
}