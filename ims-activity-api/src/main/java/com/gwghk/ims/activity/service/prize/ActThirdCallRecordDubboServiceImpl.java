package com.gwghk.ims.activity.service.prize;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.common.ApiResultCode;
import com.gwghk.ims.common.inf.external.prize.ActThirdCallRecordDubboService;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: ActThirdCallDubboServiceImpl
 * @Description: 触发第三方平台调用
 * @author lawrence
 * @date 2017年11月20日
 *
 */
@Service
public class ActThirdCallRecordDubboServiceImpl implements ActThirdCallRecordDubboService {

	//@Autowired
	//private ActThirdCallRecordManager actThirdCallRecordManager;

	/**
	 * 触发掉单数据
	 */
	@Override
	public ApiRespResult<List<String>> dealSingleOutRecord(String companyCode, List<Long> itemIds) {
		ApiRespResult<List<String>> result = new ApiRespResult<List<String>>();
		if (StringUtil.isEmpty(companyCode) || itemIds.isEmpty()) {
			return result.setRespMsg(ApiResultCode.Err10002);
		}
		List<String> deals = new ArrayList<String>();
		/*for (Long idItem : itemIds) {
			ActThirdCallRecord actThirdCallRecord = actThirdCallRecordManager.getActThirdCallRecord(idItem,companyCode);
			if (actThirdCallRecord != null
					&& ActThirdCallEnum.BONUS_ADD_BONUS.getType().equals(actThirdCallRecord.getType())) {
				String dealResult = actThirdCallRecordManager.dealWithFailureBonus(actThirdCallRecord);
				if (StringUtil.isNotEmpty(dealResult)) {
					deals.add(dealResult);
				}
			}
		}*/
		result.setData(deals);
		result.setCode(ApiResultCode.OK.getCode());
		return result.setRespMsg(ApiResultCode.OK);
	}
	
	/**
	 * 
	 * @MethodName: dealWithFailureBonus
	 * @Description: 处理贈金掉单记录
	 * @return void
	 */
	public void dealWithFailureBonus(Long companyId){
		//actThirdCallRecordManager.dealWithFailureBonus(companyId);
	}

	@Override
	public void dealWithFailureBonusRecordNumber(String companyCode, String recordNumber) {
		//actThirdCallRecordManager.dealWithFailureBonusRecordNumber(companyCode, recordNumber);	
	}
}
