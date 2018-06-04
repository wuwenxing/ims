package com.gwghk.ims.activity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.gwghk.ims.activity.manager.MisOrderManager;
import com.gwghk.ims.activity.service.mis.MisOrderDubboServiceImpl;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.mall.MallOrderDTO;
import com.gwghk.ims.common.inf.external.mall.MallOrderDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.order.ImsOrderVO;

@Service
public class MallOrderDubboServiceImpl implements MallOrderDubboService {

	private static final Logger logger = LoggerFactory.getLogger(MisOrderDubboServiceImpl.class);
	
	@Autowired 
	private MisOrderManager misOrderManager;
	
	@Override
	public MisRespResult<PageR<MallOrderDTO>> findPageList(MallOrderDTO pageSearchDTO) {
		try{
			PageR<ImsOrderVO> page=misOrderManager.findPageList(ImsBeanUtil.copyNotNull(new ImsOrderVO(),pageSearchDTO));
		        
			return MisRespResult.success(PageCustomizedUtil.copyPageList(page,new PageR<MallOrderDTO>() ,MallOrderDTO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findPageList】{}", JSON.toJSONString(pageSearchDTO ),e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<MallOrderDTO> findById(MallOrderDTO findDTO) {
		try{
			ImsOrderVO vo=misOrderManager.findById(findDTO.getId());
			
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new MallOrderDTO(),vo));
		}catch(Exception e){
			logger.error("<--系统异常,【findById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
