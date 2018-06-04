package com.gwghk.ims.common.inf.external.prize;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.ApiRespResult;

/**
 * 
* @ClassName: ActThirdCallDubboService
* @Description: 第三方调用
* @author lawrence
* @date 2017年11月20日
*
 */
public interface ActThirdCallRecordDubboService {

	/**
	 * 
	 * @MethodName: dealSingleOutRecord
	 * @Description:  触发调单数据
	 * @param companyCode
	 * @param itemIds
	 * @return
	 * @return ApiRespResult<List<String>>
	 */
	public ApiRespResult<List<String>> dealSingleOutRecord(String companyCode,List<Long> itemIds);
	
	/**
	 * 
	 * @MethodName: dealWithFailureBonus
	 * @Description: 处理掉单记录
	 * @param companyId
	 * @return void
	 */
	public void dealWithFailureBonus(@Company Long companyId);
	
	/**
	 * 
	 * @MethodName: dealWithFailureBonusRecordNumber
	 * @Description: 指定订单号处理掉单记录
	 * @param companyCode
	 * @param recordNumber
	 * @return void
	 */
	public void dealWithFailureBonusRecordNumber(String companyCode,String recordNumber);
}
