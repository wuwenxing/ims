package com.gwghk.ims.activity.manager.issue;

import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;

/**  
* 摘要:   
* @author George.li  
* @date 2018年5月29日  
* @version 1.0  
*/
public abstract class AbstractIssueItems {
	
	 
	/***
	* 摘要: 物品发放   
	* @author George.li  
	* @date 2018年5月31日  
	* @version 1.0   
	* @param porizeRecord
	* @param accountNo
	* @param companyId
	* @return
	 */
	public abstract Boolean send(String record,String itemType,String actNo,String accountNo,Long companyId);
	
	/**
	 * 
	* 摘要:扣回操作(赠金、代币)   
	* @author George.li  
	* @date 2018年5月31日  
	* @version 1.0   
	* @param porizeRecord
	* @param accountNo
	* @param companyId
	* @return
	 */
	public abstract Boolean isFinishDeduct(ImsPrizeRecordVO porizeRecord,String accountNo,Long companyId);
		 
}
