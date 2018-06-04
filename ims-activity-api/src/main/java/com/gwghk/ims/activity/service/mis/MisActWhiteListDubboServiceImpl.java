package com.gwghk.ims.activity.service.mis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ActBlackListWrapper;
import com.gwghk.ims.activity.dao.entity.ActWhiteList;
import com.gwghk.ims.activity.dao.entity.ActWhiteListWrapper;
import com.gwghk.ims.activity.manager.ActWhiteListManager;
import com.gwghk.ims.activity.manager.ImsPrizeRecordManager;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.ActProposalStatusEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActWhiteListDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActBlackListVO;
import com.gwghk.ims.common.vo.activity.ActWhiteListVO;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：活动白名单实现
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月7日
 */
@Service
public class MisActWhiteListDubboServiceImpl implements MisActWhiteListDubboService {
	private static final Logger logger = LoggerFactory.getLogger(MisActWhiteListDubboServiceImpl.class);

	@Autowired
	private ActWhiteListManager actWhiteListManager;
	
	@Autowired
	private ImsPrizeRecordManager imsPrizeRecordManager;

	public MisRespResult<PageR<ActWhiteListVO>> findPageList(ActWhiteListVO dto,@Company Long companyId) {
		logger.info("findPageList-->【ActivityPeriods:{}】", dto.getActivityPeriods());
		try {
			PageR<ActWhiteListVO> pageR = actWhiteListManager.findPageList(dto);
			
			String isHasPrizeRecord = dto.getIsHasPrizeRecord();
			if (StringUtil.isEmpty(isHasPrizeRecord)){
				for(ActWhiteListVO vo:pageR.getList()) {
					wrapActWhiteList(vo);
				}
			}else {
				Iterator<ActWhiteListVO> it = pageR.getList().iterator();
				while(it.hasNext()){
					ActWhiteListVO item = it.next();
					String flag = this.isHasPrizeRecord(item) ? "Y" : "N";
					if(!flag.equals(isHasPrizeRecord)){
						it.remove();
					}else{
						wrapActWhiteList(item);
					}
				}
			}
			return MisRespResult.success(pageR);
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<List<ActWhiteListVO>> findList(ActWhiteListVO vo,@Company Long companyId) {
		logger.info("findList-->【ActivityPeriods:{}】", vo.getActivityPeriods());
		try {
			List<ActWhiteListWrapper> list = actWhiteListManager.findList(vo);
			List<ActWhiteListVO> whiteList=new ArrayList<ActWhiteListVO>();
			whiteList=ImsBeanUtil.copyList(list, ActWhiteListVO.class);
			String isHasPrizeRecord = vo.getIsHasPrizeRecord();
			if (StringUtil.isEmpty(isHasPrizeRecord)){
				for(ActWhiteListVO wrapper:whiteList) {
					wrapActWhiteList(wrapper);
				}
			}else {
				Iterator<ActWhiteListVO> it = whiteList.iterator();
				while(it.hasNext()){
					ActWhiteListVO item = it.next();
					String flag = this.isHasPrizeRecord(item) ? "Y" : "N";
					if(!flag.equals(isHasPrizeRecord)){
						it.remove();
					}else{
						wrapActWhiteList(item);
					}
				}
			}
			return MisRespResult.success(whiteList);
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}

	}
	
	@Override
	public MisRespResult<Void> checkUploadData(List<List<Object>> result, String compareFilePath,String actNo,@Company Long companyId) {
		logger.info("checkUploadData-->【compareFilePath:{},actNo:{}】", compareFilePath,actNo);
		try {
			return actWhiteListManager.checkUploadData(result, compareFilePath, actNo) ;
		} catch (Exception e) {
			logger.error("<--系统异常，【deleteByIdArray】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ActWhiteListVO> findById(Integer id,@Company Long companyId) {
		logger.info("findById-->【id:{}】", id);
		try {
			return MisRespResult.success(
					ImsBeanUtil.copyNotNull(new ActWhiteListVO(), actWhiteListManager.findById(id)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	

	@Override
	public MisRespResult<Void> proposal(String idArray,ActWhiteListVO actWhiteListVO,@Company Long companyId) {
		logger.info("proposal-->【idArray:{}】", idArray);
		try {
			return actWhiteListManager.proposal(idArray,actWhiteListVO);
		}catch(Exception e) {
			logger.error("<--系统异常，【proposal】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	@Override
	public MisRespResult<Void> cancel(String idArray,ActWhiteListVO actWhiteListVO,@Company Long companyId) {
		logger.info("cancel-->【idArray:{}】", idArray);
		try {
			return actWhiteListManager.cancel(idArray,actWhiteListVO);
		}catch(Exception e) {
			logger.error("<--系统异常，【cancel】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> saveOrUpdate(ActWhiteListVO dto,@Company Long companyId) {
		logger.info("saveOrUpdate-->【ActivityPeriods:{}】", dto.getActivityPeriods());
		try {
			actWhiteListManager.saveOrUpdate(dto);
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【saveOrUpdate】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> deleteByIdArray(String idArray,@Company Long companyId) {
		logger.info("deleteByIdArray-->【idArray:{}】", idArray);
		try {
			List<String> ids=Arrays.asList(idArray.split(","));
			for(String id:ids) {
				ActWhiteList white=actWhiteListManager.findById(Integer.parseInt(id));
				ActWhiteListVO whiteVO=ImsBeanUtil.copyNotNull(new ActWhiteListVO(),white);
				if(isHasPrizeRecord(whiteVO)) {
					String msg = String.format("已产生物品发放记录不能删除,ID=", whiteVO.getId());
					return MisRespResult.error(msg);
				}
			}
			actWhiteListManager.deleteByIdArray(idArray);
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【deleteByIdArray】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> batchSave(ActWhiteListVO vo,@Company Long companyId) {
		logger.info("batchSave-->【actBlackListVO:{}】", vo);
		try {
			return actWhiteListManager.batchSave(vo) ;
		} catch (Exception e) {
			logger.error("<--系统异常，【batchSave】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
     }
	
	/**
	 * 白名单信息封装格式化显示
	 * @param actBaseInfo
	 * @return
	 */
	private void wrapActWhiteList(ActWhiteListVO actWhiteListVO){
		actWhiteListVO.setIsHasPrizeRecord(this.isHasPrizeRecord(actWhiteListVO) ? "是" : "否");
		actWhiteListVO.setEnv(ActEnvEnum.getLabelKeyByValue(actWhiteListVO.getEnv()));
		actWhiteListVO.setProposalStatusTxt(ActProposalStatusEnum.formatByCode(actWhiteListVO.getProposalStatus()));
		 
	}
	
	/**
	 * 功能：是否产生发放记录
	 */
	private boolean isHasPrizeRecord(ActWhiteListVO actWhiteList){
		if(actWhiteList != null){
			//先判断是否有发放记录
			ImsPrizeRecordVO prizeRecordVO=new ImsPrizeRecordVO();
			prizeRecordVO.setActNo(actWhiteList.getActivityPeriods());
			prizeRecordVO.setAccountNo(actWhiteList.getAccountNo());
			return CollectionUtils.isNotEmpty(imsPrizeRecordManager.findList(prizeRecordVO)) ? true :false;
		}
		return false;
	}
}
