package com.gwghk.ims.activity.manager.settle;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.activity.dao.entity.ActAccountActiviStat;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecordWaiting;
import com.gwghk.ims.activity.manager.ImsPrizeRecordManager;
import com.gwghk.ims.activity.manager.ImsPrizeRecordWaitingManager;
import com.gwghk.ims.activity.manager.api.AbstractActivityType;
import com.gwghk.ims.activity.manager.issue.item.WithGoldApiManager;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.BaseVO;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordWaitingVO;

public abstract class AbstractSettlementType {
	Logger logger = LoggerFactory.getLogger(AbstractActivityType.class) ;
	
	@Autowired
	private ImsPrizeRecordManager imsPrizeRecordManager ;
	@Autowired
	private ImsPrizeRecordWaitingManager imsPrizeRecordWaitingManager ;
	@Autowired
	private WithGoldApiManager withGoldApiManager ;
	
	public abstract boolean doSettlement(ActAccountActiviStat stat) ;
	
	/**
	 * 查询用户的所有发放记录
	 * @param actNo
	 * @param accountNo
	 * @return
	 */
	protected List<ImsPrizeRecord> findPrizeRecordByAccountNo(String actNo,String accountNo) {
		ImsPrizeRecordVO vo = new ImsPrizeRecordVO() ;
		vo.setAccountNo(accountNo);
		vo.setActNo(actNo);
		return imsPrizeRecordManager.findList(vo) ;
	}
	
	/**
	 * 取消发放记录
	 * @param actNo
	 * @param accountNo
	 * @param givedState
	 */
	protected void cancelPrizeRecord(List<ImsPrizeRecord> recordList,String actNo,String remark){
		BaseVO base = new BaseVO() ;
		base.setUpdateDate(new Date());
		for(ImsPrizeRecord ims : recordList) {
			imsPrizeRecordManager.batchOperate(ims.getId().toString(), "cancel", remark, actNo, base) ;
		}
	}
	
	/**
	 * 取消应发记录
	 * @param actNo
	 * @param accountNo
	 * @param givedState
	 * @param remark
	 */
	protected void cancelPrizeRecordWaiting(String actNo,String accountNo,String remark){
		ImsPrizeRecordWaitingVO vo = new ImsPrizeRecordWaitingVO() ;
		vo.setAccountNo(accountNo);
		vo.setActNo(actNo);
		List<ImsPrizeRecordWaiting> recordList = imsPrizeRecordWaitingManager.findList(vo) ;
		BaseVO base = new BaseVO() ;
		base.setUpdateDate(new Date());
		for(ImsPrizeRecordWaiting ims : recordList) {
			ims.setGivedStatus(IssuingStatusEnum.issue_cancel.getValue());
			ims.setUpdateDate(new Date());
			ims.setWaitingStatus(0);
			imsPrizeRecordWaitingManager.saveOrUpdate(ImsBeanUtil.copyNotNull(new ImsPrizeRecordWaitingVO(), ims));
		}
	}
	
	/**
	 * 未释放的赠金扣回
	 * @param actNo
	 * @param accountNo
	 */
	protected boolean cancelUnreleaseWithGold(String actNo,String accountNo,Date settleTime,Integer settleType,String platform,Long companyId){
		//调用接口扣回未释放的赠金,如果返回大于0，则说明还有未释放的赠金，需要等待释放完后在进行扣回
		Integer c = withGoldApiManager.cancelBonus(accountNo,actNo, settleTime,settleType, platform, companyId) ;
		if(c > 0) return false ;
		return true ;
	}
	
	/**
	 * 已发放的代币，立即扣回
	 * @param actNo
	 * @param accountNo
	 */
	protected boolean cancelTokenCoin(String actNo,String accountNo){
		//调用接口扣回对应的代币
		return true ;
	}
	
	
}
