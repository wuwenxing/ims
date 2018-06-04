package com.gwghk.ims.marketingtool.manager.market;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.enums.RechargeTypeEnum;
import com.gwghk.ims.common.vo.marketingtool.ImsRechargeLogDetailVO;

/**
 * 充值代理
 * @author wayne
 */
@Service
public class ImsMarkingProxyManager implements ImsMarkingService{
	
	@Autowired
	@Qualifier("imsFlowManager")
	private ImsMarkingService imsFlowManager;
	
	@Autowired
	@Qualifier("imsOnlineManager")
	private ImsMarkingService imsOnlineManager;

	/**
	 * 流量及话费充值接口
	 * @param detail
	 * @return
	 */
	@Override
	public MisRespResult<Map<String, Object>> send(ImsRechargeLogDetailVO vo){
		if(RechargeTypeEnum.flow.getCode().equals(vo.getRechargeType())){
			return imsFlowManager.send(vo);
		}else if(RechargeTypeEnum.online.getCode().equals(vo.getRechargeType())){
			return imsOnlineManager.send(vo);
		}else{
			return MisRespResult.error("充值类型错误");
		}
	}
	
}
