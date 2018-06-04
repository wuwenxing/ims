package com.gwghk.ims.activity.manager.settle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ActAccountActiviStat;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.common.annotation.ActSettleType;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActSettleTypeEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;

@Component
@ActSettleType({ActSettleTypeEnum.ACTEND})
public class ActEndSettlementType extends AbstractSettlementType{
	
	private String SETTLE_REMARK = "活动结束清算" ;

	@Override
	public boolean doSettlement(ActAccountActiviStat stat) {
		List<ImsPrizeRecord> recordList = findPrizeRecordByAccountNo(stat.getActNo(), stat.getAccountNo()) ;		
		//等待中的发放记录取消发放 待发放的代币及赠金取消发放
		cancelPrizeRecord(getCancelRecord(recordList), stat.getActNo(), SETTLE_REMARK);
		//没有完成要求的手数赠金回扣
		if(!cancelUnreleaseWithGold(stat.getActNo(), stat.getAccountNo(), stat.getAboutSettleTime(), ActSettleTypeEnum.ACTEND.getCode(), stat.getPlatform(), stat.getCompanyId())){
			return false ;
		}
		return true;
	}
	
	private List<ImsPrizeRecord> getCancelRecord(List<ImsPrizeRecord> record) {
		List<ImsPrizeRecord> cancels = new ArrayList<ImsPrizeRecord>() ;
		for(ImsPrizeRecord ims : record) {
			if(ims.getGivedStatus().equals(IssuingStatusEnum.issuing.getValue()) && (ims.getItemType().equals(ActItemTypeEnum.TOKENCOIN) || ims.getItemType().equals(ActItemTypeEnum.WITHGOLD))){
				cancels.add(ims) ;
			}else if(ims.getGivedStatus().equals(IssuingStatusEnum.waiting.getValue())){
				cancels.add(ims) ;
			}
		}
		return cancels; 
	}

}
