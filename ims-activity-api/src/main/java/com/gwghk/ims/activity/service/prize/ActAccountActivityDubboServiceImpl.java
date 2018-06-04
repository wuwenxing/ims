package com.gwghk.ims.activity.service.prize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.common.dto.job.ActAccountActivityReqDTO;
import com.gwghk.ims.common.dto.job.ActAccountActivityRespDTO;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.external.prize.ActAccountActivityDubboService;

/**
 * @ClassName: ActAccountActivityDubboServiceImpl
 * @Description: 用户-活动服务实现
 * @author lawrence
 * @date 2017年6月19日
 */
@Service
public class ActAccountActivityDubboServiceImpl implements ActAccountActivityDubboService {

	//@Autowired
	//private ActAccountActivityManager actAccountActivityManager;
	
	/**
	 * 功能：查询用户-活动列表
	 */
	@Override
	public List<ActAccountActivityRespDTO> getAccountActivityList(ActAccountActivityReqDTO actAccountActivityReqDto){
		Map<String, Object> params = new HashMap<>();
		params.put("accountNo", actAccountActivityReqDto.getAccountNo());
		params.put("platform", actAccountActivityReqDto.getPlatform());
		params.put("companyId", actAccountActivityReqDto.getCompanyId());
		params.put("companyCode", CompanyEnum.getCodeById(actAccountActivityReqDto.getCompanyId()));
		/*List<VActAccountActivity> accountActivityList = actAccountActivityManager.getAccountActivityList(params);
		if(!CollectionUtils.isEmpty(accountActivityList)){
			List<ActAccountActivityRespDTO>  actAccountActivityRespDtoList = new ArrayList<>();
			for(VActAccountActivity  accountActivity : accountActivityList){
				ActAccountActivityRespDTO  actAccountActivityRespDto = new ActAccountActivityRespDTO();
				BeanUtils.copyProperties(accountActivity,actAccountActivityRespDto);
				actAccountActivityRespDtoList.add(actAccountActivityRespDto);
			}
			return actAccountActivityRespDtoList;
		}*/
		return null;
	}
	
	/**
	 * 功能：同步用户-活动关系数据
	 */
	@Override
	public void syncAccountActivity(Long companyId) {
		//actAccountActivityManager.processAccountActivityRecord(companyCode);
	}

	/**
	 * 功能：根据查询条件->同步用户-活动关系数据
	 */
	@Override
	public void syncAccountActivityRecordWithParams(Map<String, Object> actParams,Long companyId) {
		//actAccountActivityManager.processAccountActivityRecord(actParams, companyCode);
	}
}
