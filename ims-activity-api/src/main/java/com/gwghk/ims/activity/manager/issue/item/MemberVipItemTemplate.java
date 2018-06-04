package com.gwghk.ims.activity.manager.issue.item;

import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.manager.issue.AbstractIssueItems;
import com.gwghk.ims.common.annotation.IssueItemCategory;
import com.gwghk.ims.common.enums.IssueItemCategoryEnum;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;

/**  
* 摘要:会员 
* @author George.li  
* @date 2018年5月30日  
* @version 1.0  
*/
@Component
@IssueItemCategory({IssueItemCategoryEnum.MEMBERVIP})
public class MemberVipItemTemplate extends AbstractIssueItems{

 
	@Override
	public Boolean send(String recordNo,String itemType,String actNo,String accountNo,Long companyId) {
		
		//ImsPrizeRecord pr=imsPrizeRecordManager.findByRecordNo(recordNo, actNo);
		// TODO Auto-generated method stub
		return null;
	}
 
	@Override
	public Boolean isFinishDeduct(ImsPrizeRecordVO porizeRecord, String accountNo, Long companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	 
 

}
