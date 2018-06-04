package com.gwghk.ims.activity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.activity.dao.entity.ActAccountActiviStat;
import com.gwghk.ims.activity.manager.ActAccountActiviStatManager;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.dto.job.ActAccountActivityStatReqDTO;
import com.gwghk.ims.common.dto.job.ActAccountActivityStatRespDTO;
import com.gwghk.ims.common.enums.ActSettleStatusEnum;
import com.gwghk.ims.common.inf.external.activity.ActAccountActivityStatDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActAccountActiviStatVO;

/**
 * 
 * 摘要：活动查询业务
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月31日
 */
@Service
public class ActAccountActivityStatDubboServiceImpl implements ActAccountActivityStatDubboService {

	@Autowired
	private ActAccountActiviStatManager actAccountActiviStatManager ;
	
	@Override
	public ApiRespResult<List<ActAccountActivityStatRespDTO>> findListBySettleStatus(int settleStatus) {
		ActAccountActiviStatVO vo = new ActAccountActiviStatVO() ;
		vo.setSettleStatus(ActSettleStatusEnum.UNSETTLE.getCode());
		List<ActAccountActivityStatRespDTO> list = ImsBeanUtil.copyList(actAccountActiviStatManager.findList(vo), ActAccountActivityStatRespDTO.class) ;
		return ApiRespResult.success(list);
	}

	
	
}
