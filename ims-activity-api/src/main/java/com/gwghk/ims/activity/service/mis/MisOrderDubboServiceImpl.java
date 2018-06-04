/**
 * 
 */
package com.gwghk.ims.activity.service.mis;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.activity.manager.ActItemsSettingManager;
import com.gwghk.ims.activity.manager.MisOrderManager;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.AuditStatusEnum;
import com.gwghk.ims.common.enums.DeliveryStatusEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.inf.mis.order.MisOrderDubboService;
import com.gwghk.ims.common.vo.order.ImsOrderVO;

/**
 * @author jackson.tang
 *
 */
@Service
public class MisOrderDubboServiceImpl implements MisOrderDubboService {
	private static final Logger logger = LoggerFactory.getLogger(MisOrderDubboServiceImpl.class);
	
	@Autowired 
	private MisOrderManager misOrderManager;
	@Autowired
	private MisActPrizeRecordService actPrizeRecordService;
	@Autowired
	private ActItemsSettingManager actItemsSettingManager;
	
	
	@Override
	public MisRespResult<PageR<ImsOrderVO>> findPageList(ImsOrderVO pageSearchVo) {
		try{
			PageR<ImsOrderVO> page=misOrderManager.findPageList(pageSearchVo);
			
	        if (page != null) {
	        	List<ImsOrderVO> pageList = page.getRows();
	        	if(CollectionUtils.isNotEmpty(pageList)){
	        		for(ImsOrderVO order: pageList){
	        			order.setAuditStatusTxt(AuditStatusEnum.format(order.getAuditStatus()));
	        			order.setIssuingStatus(IssuingStatusEnum.format(order.getIssuingStatus()));
	        			order.setDeliveryStatusTxt(DeliveryStatusEnum.format(order.getDeliveryStatus().toString()));
	        		}
	        	}
	        }
		        
			return MisRespResult.success(page);
		}catch(Exception e){
			logger.error("<--系统异常,【findPageList】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ImsOrderVO> findById(ImsOrderVO findVo) {
		try{
			ImsOrderVO vo=misOrderManager.findById(findVo.getId());
			
			return MisRespResult.success(vo);
		}catch(Exception e){
			logger.error("<--系统异常,【findById】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> updateExpress(ImsOrderVO imsOrderVo) {
		logger.info("updateExpress-->【expressNo:{},expressCompany:{}】", imsOrderVo.getExpressNo(),imsOrderVo.getExpressCompany());
		
		try{
			if(StringUtils.isEmpty(imsOrderVo.getExpressCompany())){
				return MisRespResult.error(MisResultCode.Order24000) ;
			}
			if(StringUtils.isEmpty(imsOrderVo.getExpressNo())){
				return MisRespResult.error(MisResultCode.Order24001) ;
			}
			imsOrderVo.setDeliveryStatus(DeliveryStatusEnum.out_library.getCode());//录入快递信息，则即改为已出库
			return misOrderManager.batchUpdateDeliveryStatus(imsOrderVo.getId().toString(), imsOrderVo) ;
			
		}catch(Exception e){
			logger.error("<--系统异常,【updateExpress】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> batchUpdateExpress(String ids, ImsOrderVO imsOrderVo) {
		logger.info("batchUpdateExpress-->【ids:{},expressNo:{}】", ids,imsOrderVo.getExpressNo());
		
		try{
			if(StringUtils.isEmpty(imsOrderVo.getExpressCompany())){
				return MisRespResult.error(MisResultCode.Order24000) ;
			}
			if(StringUtils.isEmpty(imsOrderVo.getExpressNo())){
				return MisRespResult.error(MisResultCode.Order24001) ;
			}
			imsOrderVo.setDeliveryStatus(DeliveryStatusEnum.out_library.getCode());//录入快递信息，则即改为已出库
			return misOrderManager.batchUpdateDeliveryStatus(ids, imsOrderVo) ;
		}catch(Exception e){
			logger.error("<--系统异常,【batchUpdateExpress】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> batchUpdateDeliveryStatus(String ids, ImsOrderVO imsOrderVo) {
		logger.info("batchUpdateDeliveryStatus-->【id:{},deliveryStatus:{}】", ids,imsOrderVo.getDeliveryStatus());
		try{
			imsOrderVo.setDeliveryStatus(DeliveryStatusEnum.out_library.getCode());//手动直接改为已出库
			return misOrderManager.batchUpdateDeliveryStatus(ids, imsOrderVo) ;
		}catch(Exception e){
			logger.error("<--系统异常,【batchUpdateDeliveryStatus】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<Void> updateAddress(String[] ids, String address, Long companyId) {
		logger.info("updateAddress-->【ids:{},address:{},companyId:{}】", ids,address);
		
		try{
			for(String id : ids){
				ImsOrderVO imsOrder = misOrderManager.findById(Long.parseLong(id));
				imsOrder.setDetailAddress(address);
				// 待发放与发放中，根据收货地址是否为空判断
				if(imsOrder.getDeliveryStatus() == 0 || imsOrder.getDeliveryStatus() == 1){
					if(StringUtils.isNotBlank(imsOrder.getDetailAddress())){
						imsOrder.setDeliveryStatus(1);//发放中
					}else{
						imsOrder.setDeliveryStatus(0);//待发放
					}
				}
				misOrderManager.updateOrder(imsOrder);
			}
			
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常,【updateAddress】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
