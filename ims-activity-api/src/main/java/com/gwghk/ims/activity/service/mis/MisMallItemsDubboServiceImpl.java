package com.gwghk.ims.activity.service.mis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.activity.manager.MisMallItemManager;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.mall.MisMallItemsDubboService;
import com.gwghk.ims.common.vo.mall.ImsMallItemVO;

public class MisMallItemsDubboServiceImpl implements MisMallItemsDubboService {
	private static final Logger logger = LoggerFactory.getLogger(MisMallItemsDubboServiceImpl.class);
	
	@Autowired
	private MisMallItemManager misMallItemsManager;
	

	@Override
	public MisRespResult<PageR<ImsMallItemVO>> findPageList(ImsMallItemVO pageSearchVo) {
		try{
			PageR<ImsMallItemVO> page=misMallItemsManager.findPageList(pageSearchVo);
	        return MisRespResult.success(page);
		}catch(Exception e){
			logger.error("<--系统异常,【findPageList】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ImsMallItemVO> findById(Long id, Long companyId) {
		try{
			ImsMallItemVO mallItem=misMallItemsManager.findById(id);
			
	        return MisRespResult.success(mallItem);
		}catch(Exception e){
			logger.error("<--系统异常,【findById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> save(ImsMallItemVO imsMallItemsVo) {
		logger.info("save-->【"+imsMallItemsVo.toStr()+"】");
		try{
			
			if(imsMallItemsVo.getId()!=null && imsMallItemsVo.getId()>0) {
				ImsMallItemVO oldMallItems=misMallItemsManager.findById(imsMallItemsVo.getId());
				
				if (oldMallItems == null) 
					return MisRespResult.error(MisResultCode.Goods50001.getMessage());
				
				//更新 ,后台同时控制只允许更新以下字段
				oldMallItems.setImgUrl(imsMallItemsVo.getImgUrl());
				oldMallItems.setMallItemsDesc(imsMallItemsVo.getMallItemsDesc());
				oldMallItems.setEnableFlag(imsMallItemsVo.getEnableFlag());
				oldMallItems.setPublishDate(imsMallItemsVo.getPublishDate());
				
				oldMallItems.setUpdateIp(imsMallItemsVo.getUpdateIp());
				oldMallItems.setUpdateUser(imsMallItemsVo.getUpdateUser());
				oldMallItems.setUpdateDate(imsMallItemsVo.getUpdateDate());
				misMallItemsManager.update(oldMallItems);
			}else {
				//新增
				misMallItemsManager.insert(imsMallItemsVo);
			}
			
			return MisRespResult.success();
			
		}catch(Exception e){
			logger.error("<--系统异常,【save】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
		
	}

	@Override
	public MisRespResult<Void> batchDel(String[] idArr, Long companyId) {
		try{
			//mallItems.setDeleteFlag("Y");
			misMallItemsManager.deleteByIds(idArr);
	        return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常,【findById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> batchSoftDel(String[] idArr, Long companyId) {
		try{
			//mallItems.setDeleteFlag("Y");
			misMallItemsManager.batchSoftDel(idArr);
	        return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常,【findById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
