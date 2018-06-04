package com.gwghk.ims.activity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.activity.manager.MisMallItemManager;
import com.gwghk.ims.activity.service.mis.MisMallItemsDubboServiceImpl;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.mall.MallItemDTO;
import com.gwghk.ims.common.inf.external.mall.MallItemsDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.mall.ImsMallItemVO;

@Service
public class MallItemsDubboServiceImpl implements MallItemsDubboService {

	private static final Logger logger = LoggerFactory.getLogger(MisMallItemsDubboServiceImpl.class);
	
	@Autowired
	private MisMallItemManager misMallItemsManager;
	
	@Override
	public MisRespResult<PageR<MallItemDTO>> findPageList(MallItemDTO pageSearchDTO) {
		try{
			PageR<ImsMallItemVO> page=misMallItemsManager.findPageList(ImsBeanUtil.copyNotNull(new ImsMallItemVO(),pageSearchDTO));
	        return MisRespResult.success(PageCustomizedUtil.copyPageList(page,new PageR<MallItemDTO>(),MallItemDTO.class));
		}catch(Exception e){
			logger.error("<--系统异常,【findPageList】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<MallItemDTO> findById(Long id, Long companyId) {
		try{
			ImsMallItemVO mallItem=misMallItemsManager.findById(id);
	        return MisRespResult.success(ImsBeanUtil.copyNotNull(new MallItemDTO(), mallItem));
		}catch(Exception e){
			logger.error("<--系统异常,【findById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

}
