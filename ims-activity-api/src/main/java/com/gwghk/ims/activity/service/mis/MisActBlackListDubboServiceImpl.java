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

import com.gwghk.ims.activity.dao.entity.ActBlackList;
import com.gwghk.ims.activity.dao.entity.ActBlackListWrapper;
import com.gwghk.ims.activity.dao.entity.ActWhiteListWrapper;
import com.gwghk.ims.activity.manager.ActBlackListManager;
import com.gwghk.ims.activity.manager.ImsPrizeRecordManager;
import com.gwghk.ims.activity.manager.settle.ActSettlementManager;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.ActProposalStatusEnum;
import com.gwghk.ims.common.enums.ActSettleTypeEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActBlackListDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActBlackListVO;
import com.gwghk.ims.common.vo.activity.ActWhiteListVO;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：活动黑名单实现
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月7日
 */
@Service
public class MisActBlackListDubboServiceImpl implements MisActBlackListDubboService {
	private static final Logger logger = LoggerFactory.getLogger(MisActBlackListDubboServiceImpl.class);

	@Autowired
	private ActBlackListManager actBlackListManager;
	
	@Autowired
	private ImsPrizeRecordManager imsPrizeRecordManager;
	
	@Autowired
	private ActSettlementManager actSettlementManager; 

	public MisRespResult<PageR<ActBlackListVO>> findPageList(ActBlackListVO vo,@Company Long companyId) {
		logger.info("findPageList-->【ActivityPeriods:{},activityName:{}】", vo.getActivityPeriods(),vo.getActivityName());
		try {
			PageR<ActBlackListVO> pageR = actBlackListManager.findPageList(vo);
			for(ActBlackListVO blackListVO:pageR.getList()) {
				wrapActBlackList(blackListVO);
			}
			return MisRespResult.success(pageR);
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
																
	public MisRespResult<List<ActBlackListVO>> findListByActivityPeriods(String actNo,@Company Long companyId) {
		logger.info("findList-->【ActivityPeriods:{}】", actNo);
		try {
			List<ActBlackList> list = actBlackListManager.findListByActivityPeriods(actNo);
			List<ActBlackListVO> blackList=new ArrayList<ActBlackListVO>();
			ImsBeanUtil.copyList(list, ActBlackListVO.class);
			for(ActBlackListVO wrapper:blackList) {
				wrapActBlackList(wrapper);
			}
			return MisRespResult.success(blackList);	 
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	public MisRespResult<List<ActBlackListVO>> findList(ActBlackListVO dto,@Company Long companyId) {
		logger.info("findList-->【ActivityPeriods:{}】", dto.getActivityPeriods());
		/*try {
			List<ActBlackListWrapper> list = actBlackListManager.findList(dto);
			return MisRespResult.success(ImsBeanUtil.copyList(list, ActBlackListVO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}*/
		try {
			List<ActBlackListWrapper> list = actBlackListManager.findList(dto);
			List<ActBlackListVO> blackList=new ArrayList<ActBlackListVO>();
			blackList=ImsBeanUtil.copyList(list, ActBlackListVO.class);
			String isHasPrizeRecord = dto.getIsHasPrizeRecord();
			if (StringUtil.isEmpty(isHasPrizeRecord)){
				for(ActBlackListVO wrapper:blackList) {
					wrapActBlackList(wrapper);
				}
			}else {
				Iterator<ActBlackListVO> it = blackList.iterator();
				while(it.hasNext()){
					ActBlackListVO item = it.next();
					String flag = this.isHasPrizeRecord(item) ? "Y" : "N";
					if(!flag.equals(isHasPrizeRecord)){
						it.remove();
					}else{
						wrapActBlackList(item);
					}
				}
			}
			return MisRespResult.success(blackList);
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ActBlackListVO> findById(Integer id,@Company Long companyId) {
		logger.info("findById-->【id:{}】", id);
		try {
			return MisRespResult.success(
					ImsBeanUtil.copyNotNull(new ActBlackListVO(), actBlackListManager.findById(id)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	
	@Override
	public MisRespResult<Void> proposal(String idArray,ActBlackListVO actBlackListVO,@Company Long companyId) {
		logger.info("proposal-->【idArray:{}】", idArray);
		try {
			return actBlackListManager.proposal(idArray,actBlackListVO);
		}catch(Exception e) {
			logger.error("<--系统异常，【proposal】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	@Override
	public MisRespResult<Void> cancel(String idArray,ActBlackListVO actBlackListVO,@Company Long companyId) {
		logger.info("cancel-->【idArray:{}】", idArray);
		try {
			return actBlackListManager.cancel(idArray,actBlackListVO);
		}catch(Exception e) {
			logger.error("<--系统异常，【cancel】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	@Override
	public MisRespResult<Void> saveOrUpdate(ActBlackListVO dto,@Company Long companyId) {
		logger.info("saveOrUpdate-->【ActivityPeriods:{}】", dto.getActivityPeriods());
		try {
			actBlackListManager.saveOrUpdate(dto);
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
				ActBlackList black=actBlackListManager.findById(Integer.parseInt(id));
				ActBlackListVO blackVO=ImsBeanUtil.copyNotNull(new ActBlackListVO(),black);
				if(isHasPrizeRecord(blackVO)) {
					String msg = String.format("已产生物品发放记录不能删除,ID=", black.getId());
					return MisRespResult.error(msg);
				}
			}
			
			actBlackListManager.deleteByIdArray(idArray);
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【deleteByIdArray】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	@Override
	public MisRespResult<Void> checkUploadData(List<List<Object>> result, String compareFilePath,String actNo,@Company Long companyId) {
		logger.info("checkUploadData-->【compareFilePath:{},actNo:{}】", compareFilePath,actNo);
		try {
			return actBlackListManager.checkUploadData(result, compareFilePath, actNo) ;
		} catch (Exception e) {
			logger.error("<--系统异常，【checkUploadData】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> batchSave(ActBlackListVO actBlackListVO,@Company Long companyId) {
		logger.info("batchSave-->【actBlackListVO:{}】", actBlackListVO);
		try {
			return actBlackListManager.batchSave(actBlackListVO) ;
		} catch (Exception e) {
			logger.error("<--系统异常，【batchSave】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
     }
	
	/**
	 * 黑名单信息封装格式化显示
	 * @param actBaseInfo
	 * @return
	 */
	private void wrapActBlackList(ActBlackListVO actBlackListVO){
		actBlackListVO.setIsHasPrizeRecord(this.isHasPrizeRecord(actBlackListVO) ? "是" : "否");
		actBlackListVO.setEnv(ActEnvEnum.getLabelKeyByValue(actBlackListVO.getEnv()));
		actBlackListVO.setProposalStatusTxt(ActProposalStatusEnum.formatByCode(actBlackListVO.getProposalStatus()));
		 
	}
	
	/**
	 * 功能：是否产生发放记录
	 */
	private boolean isHasPrizeRecord(ActBlackListVO actBlackList){
		if(actBlackList != null){
			//先判断是否有发放记录
			ImsPrizeRecordVO prizeRecordVO=new ImsPrizeRecordVO();
			prizeRecordVO.setActNo(actBlackList.getActivityPeriods());
			prizeRecordVO.setAccountNo(actBlackList.getAccountNo());
			return CollectionUtils.isNotEmpty(imsPrizeRecordManager.findList(prizeRecordVO)) ? true :false;
		}
		return false;
	}
}
