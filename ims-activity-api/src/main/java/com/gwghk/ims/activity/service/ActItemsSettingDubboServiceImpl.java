package com.gwghk.ims.activity.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.gwghk.ims.activity.dao.entity.ActItemsSetting;
import com.gwghk.ims.activity.manager.ActItemsSettingManager;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActItemsSettingDTO;
import com.gwghk.ims.common.inf.external.activity.ActItemsSettingDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActItemsSettingVO;

@Service
public class ActItemsSettingDubboServiceImpl implements ActItemsSettingDubboService{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ActItemsSettingManager actItemsSettingManager;
	
	@Override
	public MisRespResult<PageR<ActItemsSettingDTO>> findPageList(ActItemsSettingDTO dto) {
		try {
			
			PageR<ActItemsSettingVO> pageR = actItemsSettingManager.findPageList(ImsBeanUtil.copyNotNull(new ActItemsSettingVO(),dto));
			return MisRespResult.success(PageCustomizedUtil.copyPageList(pageR,new PageR<ActItemsSettingDTO>(),ActItemsSettingDTO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList】{}",JSON.toJSONString(dto), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<List<ActItemsSettingDTO>> findList(ActItemsSettingDTO dto) {
		try {
			List<ActItemsSettingVO> list = actItemsSettingManager.findList(ImsBeanUtil.copyNotNull(new ActItemsSettingVO(),dto));
			return MisRespResult.success(ImsBeanUtil.cloneList(list,ActItemsSettingDTO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList】 {}",JSON.toJSONString(dto) , e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ActItemsSettingDTO> findByItemNumber(String itemNumber, boolean isloadActUseFlag, Long companyId) {
		try {
			ActItemsSetting actItemsSetting = actItemsSettingManager.findByItemNumber(itemNumber);
			ActItemsSettingDTO dto = ImsBeanUtil.copyNotNull(new ActItemsSettingDTO(), actItemsSetting);
			if(isloadActUseFlag){
				dto.setActUseFlag(actItemsSettingManager.isActivityInUse(actItemsSetting.getItemNumber()));
			}
			return MisRespResult.success(dto);
		} catch (Exception e) {
			logger.error("<--系统异常,【findByItemNumber】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
