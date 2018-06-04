package com.gwghk.ims.activity.manager.issue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.util.BeanFactoryUtil;

/**  
* 摘要:   
* @author George.li  
* @date 2018年5月29日  
* @version 1.0  
*/
@Component
public class IssueItemManager {

	Logger logger = LoggerFactory.getLogger(AbstractIssueItems.class) ;
	 
	/**
	 * 
	* 摘要:物品发放统一入口   
	* @author George.li  
	* @date 2018年5月31日  
	* @version 1.0   
	* @param recordNo 物品发放记录流水号
	* @param itemType (模拟币、代币、赠金、流量、话费) 对应的枚举值
	* @param actNo 活动编号
	* @param accountNo 账号
	* @param companyId 公司ID
	* @return
	 */
	public Boolean send(String recordNo,String itemType,String actNo,String accountNo,Long companyId){
		//ImsPrizeRecord porizeRecord =imsPrizeRecordManager.findByRecordNo(recordNo, actNo);
		AbstractIssueItems baseTaskBean = BeanFactoryUtil.getIssueItemsBean(itemType) ;
		if(baseTaskBean == null){
			logger.info("不支持该物品类型：{}",new Object[]{itemType});
			return null ;
		}
		return baseTaskBean.send(recordNo,itemType,actNo, accountNo, companyId) ;
	}
	
	
}
