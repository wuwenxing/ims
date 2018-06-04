package com.gwghk.ims.activity.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ImsOrder;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.activity.dao.inf.MisOrderDao;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.AuditStatusEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.order.ImsOrderVO;

@Component
@Transactional
public class MisOrderManager {
	
	@Autowired
	private ImsPrizeRecordManager imsPrizeRecordManager ;
	@Autowired
	private ActItemsSettingManager actItemsSettingManager ;

	@Autowired
	private MisOrderDao misOrderDao;
	
	public PageR<ImsOrderVO> findPageList(ImsOrderVO pageSearchVo) {
		
		PageHelper.startPage(pageSearchVo.getPage(), pageSearchVo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ImsOrderVO.class, pageSearchVo.getSort(), pageSearchVo.getOrder()));
		
		return PageCustomizedUtil.copyPageList(new PageR<ImsOrder>(misOrderDao.listByPage(pageSearchVo)), new PageR<ImsOrderVO>(),ImsOrderVO.class);
	}

	public ImsOrderVO findById(Long id) {
		return ImsBeanUtil.copyNotNull(new ImsOrderVO(), misOrderDao.findObject(id));
	}

	/**
	 * 更新订单
	 * @param order
	 * @return
	 */
	public int updateOrder(ImsOrderVO orderVo) {
		ImsOrder order=ImsBeanUtil.copyNotNull(new ImsOrder(),orderVo);
		return misOrderDao.update(order);
	}
	
	/**
	 * 更新订单
	 * @param order
	 * @return
	 */
	public void saveOrder(ImsOrder orderVo) {
		if(null != orderVo){
			misOrderDao.save(orderVo) ;
		}
	}
	
	/**
	 * 查找订单通过订单记录号码
	 * @param recordNumber
	 * @return
	 */
	public ImsOrderVO findByRecordNumber(String recordNumber) {
		if(recordNumber==null)
			return null;
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("recordNumber", recordNumber);
		
		return ImsBeanUtil.copyNotNull(new ImsOrderVO(), misOrderDao.findObjectByMap(map));
	}
	
	
	
	/**
	 * 批量处理出库
	 * @param ids
	 * @param imsOrderVo
	 * @return
	 */
	public MisRespResult<Void> batchUpdateDeliveryStatus(String batchId, ImsOrderVO imsOrderVo){
		List<ImsOrderVO> orderList = new ArrayList<ImsOrderVO>();
		String[] ids = batchId.split(",") ;
		for(String id : ids){
			ImsOrderVO order = findById(Long.parseLong(id));
			if(null != order){
				if(order.getDeliveryStatus() == 2){
					return MisRespResult.error("已取消的订单，不可出库，请检查");
				}
				if(order.getDeliveryStatus() == 3){
					return MisRespResult.error("已出库的订单，不可出库，请检查");
				}
				if(!AuditStatusEnum.auditPass.getValue().equals(order.getAuditStatus())){
					return MisRespResult.error("对应发放记录只有审批通过后才可出库，请检查");
				}
				orderList.add(order);
			}else{
				return MisRespResult.error("未查询到订单信息，请检查");
			}
		}
		// 2、更新
		for(int i=0; i<orderList.size(); i++){
			ImsOrderVO order = orderList.get(i) ;
			// 发放记录是否已经审批通过,必须审批通过才能发货
			ImsPrizeRecord record = imsPrizeRecordManager.findByRecordNo(order.getRecordNumber(), order.getActivityPeriods()); 
			if(null == record){
				return MisRespResult.error("未查询到对应的发放记录信息，请检查");
			}
			if(order.getDeliveryStatus() == 0 || order.getDeliveryStatus() == 1){
				// 只有未发货-变为已发货时，才更新发放记录
				// 并且是在库
				// 只有未发货-变为已发货时，才更新发放记录
				if(!IssuingStatusEnum.in_library.getValue().equals(record.getGivedStatus())){
					// 非在库时，提示不能发货
					return MisRespResult.error("对应发放记录的物品非在库，请检查");
				}
				// 1、 更新发放记录-发放状态为已出库
				record.setGivedStatus(IssuingStatusEnum.out_library.getValue());
				imsPrizeRecordManager.saveOrUpdate(record);
				// 2、更新物品数量
				int giftAmountStep = -1;//实际剩余数量-1
				int giftTmpAmountStep =0;//可用数量不变
				actItemsSettingManager.updateActItemsAmount(record.getItemNo(), giftAmountStep, giftTmpAmountStep);
			}
            // 3、更新订单
			ImsBeanUtil.copyNotNull(order,imsOrderVo);
			if(order.getDeliveryDate() == null){
				order.setDeliveryDate(new Date());// 出库日期
			}
			updateOrder(order);
		}
		
		return MisRespResult.success();
	}
	
}
