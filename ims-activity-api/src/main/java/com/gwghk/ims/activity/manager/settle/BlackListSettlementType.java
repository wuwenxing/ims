package com.gwghk.ims.activity.manager.settle;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.dao.entity.ActAccountActiviStat;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.common.annotation.ActSettleType;
import com.gwghk.ims.common.enums.ActSettleTypeEnum;

@Component
@ActSettleType({ActSettleTypeEnum.BLACKLIST})
public class BlackListSettlementType extends AbstractSettlementType{

	private String SETTLE_REMARK = "黑名单清算" ;
	
	@Override
	public boolean doSettlement(ActAccountActiviStat stat) {
		List<ImsPrizeRecord> recordList = findPrizeRecordByAccountNo(stat.getActNo(), stat.getAccountNo()) ;
		//step1 发放记录处理  黑名单清算，未发放成功的发放记录都取消发放
		cancelPrizeRecord(recordList, stat.getActNo(), SETTLE_REMARK);
		//step2 取消应发记录   删除应发记录添加发放记录为取消发放
		cancelPrizeRecordWaiting(stat.getActNo(), stat.getAccountNo(), SETTLE_REMARK) ;
		//step3 代币扣回
		if(!cancelTokenCoin(stat.getActNo(), stat.getAccountNo())){
			return false ;
		}
		//step4 未释放赠金扣回
		if(!cancelUnreleaseWithGold(stat.getActNo(), stat.getAccountNo(), stat.getAboutSettleTime(), ActSettleTypeEnum.BLACKLIST.getCode(), stat.getPlatform(), stat.getCompanyId())){
			return false ;
		}
		return true;
	}

}
