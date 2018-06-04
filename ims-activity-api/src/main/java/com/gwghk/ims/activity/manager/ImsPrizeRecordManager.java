package com.gwghk.ims.activity.manager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.dao.entity.ActItemsSetting;
import com.gwghk.ims.activity.dao.entity.ActPrizeRecordExt;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ActTaskCustomSetting;
import com.gwghk.ims.activity.dao.entity.ActTaskItems;
import com.gwghk.ims.activity.dao.entity.ActTaskSetting;
import com.gwghk.ims.activity.dao.entity.ImsOrder;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.activity.dao.entity.VActPrizeRecord;
import com.gwghk.ims.activity.dao.inf.ImsPrizeRecordDao;
import com.gwghk.ims.activity.dao.inf.ImsPrizeRecordExtDao;
import com.gwghk.ims.activity.dao.inf.VActPrizeRecordDao;
import com.gwghk.ims.activity.enums.ActWaitSendReasonEnum;
import com.gwghk.ims.activity.enums.UnitEnums;
import com.gwghk.ims.activity.service.mis.CommonDubboService;
import com.gwghk.ims.activity.util.WaitSendReasonUtil;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.DemoCashAdjustDto;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.ActItemCategoryEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActTypeEnum;
import com.gwghk.ims.common.enums.AuditStatusEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.enums.SeqEnum;
import com.gwghk.ims.common.inf.SeqDubboService;
import com.gwghk.ims.common.util.BigDecimalUtil;
import com.gwghk.ims.common.util.DateUtil;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.BaseVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordWaitingVO;
import com.gwghk.ims.common.vo.order.ImsOrderVO;

/**
 * 
 * 摘要：物品发放记录业务
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月9日
 */
@Component
@Transactional
public class ImsPrizeRecordManager {
	private static final Logger logger = LoggerFactory.getLogger(ImsPrizeRecordManager.class);
	
	@Autowired
	private ImsPrizeRecordDao imsPrizeRecordDao;
	@Autowired
	private ActSettingManager actSettingManager ;
	@Autowired
	private ActItemsSettingManager actItemsSettingManager ;
	@Autowired
	private SeqDubboService seqDubboService;
	@Autowired
	private ActTaskItemsManager actTaskItemsManager ;
	@Autowired
	private CommonDubboService commonDubboService ;
	@Autowired
	private ActTaskSettingManager actTaskSettingManager ;
	@Autowired
	private MisOrderManager misOrderManager ;
	@Autowired
	private ActStringCodeManager actStringCodeManager ;
	@Autowired
	private ActDemoCashAdjustManager actDemoCashAdjustManager ;
	@Autowired
	private ActCustomerInfoManager actCustomerInfoManager ;
	@Autowired
	private ActTaskCustomSettingManager actTaskCustomSettingManager ;
	@Autowired
	private ImsPrizeRecordWaitingManager imsPrizeRecordWaitingManager ;
	@Autowired
	private VActPrizeRecordDao  vActPrizeRecordDao;
	@Autowired
	private ImsPrizeRecordExtDao imsPrizeRecordExtDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<ImsPrizeRecordVO> findPageList(ImsPrizeRecordVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActItemsSetting.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ImsPrizeRecord>(this.findList(vo)),new PageR<ImsPrizeRecordVO>(), ImsPrizeRecordVO.class);
	}
 
	/**
	 * 功能：根据查询条件->查询列表
	 */
	@SuppressWarnings("unchecked")
	public  List<ImsPrizeRecord> findList(ImsPrizeRecordVO vo) {
		Map<String, Object> map = ImsBeanUtil.toMap(vo);
		List<ImsPrizeRecord> list =  imsPrizeRecordDao.findListByMap(map);
		return list;
	}
	/**
	 * 根据任务记录的ID查看发放记录
	 * @param actNo
	 * @param taskRecordId
	 * @return
	 */
	public ImsPrizeRecord findObjectByTaskRecordId(String actNo,Integer taskRecordId){
		Map<String, Object> map = new HashMap<>() ;
		map.put("actNo", actNo) ;
		map.put("taskRecordId", taskRecordId) ;
		return imsPrizeRecordDao.findObjectByMap(map) ;
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ImsPrizeRecord findById(Integer id,String actNo) {
		Map<String, Object> map = new HashMap<>() ;
		map.put("id", id) ;
		map.put("actNo", actNo) ;
		return imsPrizeRecordDao.findObjectByMap(map) ;
	}
	
	public Integer countByDate(String beginDate,String endDate,String actNo,String taskCode,String itemNo) {
		Map<String,Object> map = new HashMap<>() ;
		map.put("actNo", actNo) ;
		map.put("taskCode", taskCode) ;
		map.put("itemNo", itemNo) ;
		map.put("beginDate", beginDate) ;
		map.put("endDate", endDate) ;
		return imsPrizeRecordDao.findTotal(map) ;
	}
	
	public Integer countByAccount(String actNo,String accountNo,String taskCode,String itemNo) {
		Map<String,Object> map = new HashMap<>() ;
		map.put("actNo", actNo) ;
		map.put("taskCode", taskCode) ;
		map.put("itemNo", itemNo) ;
		map.put("accountNo", accountNo) ;
		return imsPrizeRecordDao.findTotal(map) ;
	}

	/**
	 * 功能：根据流水号->获取信息
	 */
	public ImsPrizeRecord findByRecordNo(String recordNo,String actNo){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("actNo", actNo) ;
		map.put("recordNo", recordNo);
		return imsPrizeRecordDao.findObjectByMap(map);
	}
	
	public void saveOrUpdate(ImsPrizeRecord vo) {
		if(vo.getId() == null || vo.getId() == 0){
			vo.setDeleteFlag("N");
			vo.setEnableFlag("Y");
			//如果发放状态不是可发放状态，则需要新增一条待发放记录
			if(null != vo.getWaitingStatus() && vo.getWaitingStatus() != 0){
				ImsPrizeRecordWaitingVO imsPrizeRecordWaitingVO = ImsBeanUtil.copyNotNull(new ImsPrizeRecordWaitingVO(), vo) ;
				imsPrizeRecordWaitingManager.saveOrUpdate(imsPrizeRecordWaitingVO); 
			}else{
				imsPrizeRecordDao.save(vo) ;
			}
		}else{
			//如果发放状态不是可发放状态，则需要新增一条待发放记录，本记录则删除
			if(null != vo.getWaitingStatus() && vo.getWaitingStatus() != 0){
				ImsPrizeRecord imsPrizeRecord = findById(vo.getId(), vo.getActNo());
				if(null != imsPrizeRecord ){
					ImsPrizeRecordWaitingVO imsPrizeRecordWaitingVO = ImsBeanUtil.copyNotNull(new ImsPrizeRecordWaitingVO(), vo) ;
					imsPrizeRecordWaitingManager.saveOrUpdate(imsPrizeRecordWaitingVO); 
					imsPrizeRecordDao.delete(vo.getId()) ;
				}
			}else{
				imsPrizeRecordDao.update(vo) ;
			}
		}
	}
	
	/**
	 * 批量保存物品发放记录
	 * @param vo
	 * @param list
	 * @return
	 */
	public MisRespResult<Void> batchSave(ImsPrizeRecordVO vo, List<List<Object>> list){
		String itemType = vo.getItemType() ;
		MisRespResult<Void> result = checkAccountList(list, itemType) ;
		if(result.isNotOk()){
			return result ;
		}
		for(int i = 0 ;i< list.size() ;i++){
			List<Object> lls = list.get(i) ;
			if(lls.size() != 3){
				return MisRespResult.error(MisResultCode.Error10012) ;
			}
			String accountNo = lls.get(0).toString() ;
			String platform = lls.get(1).toString() ;
			if(itemType.equals(ActItemTypeEnum.ANALOGCOIN.getValue()) || itemType.equals(ActItemTypeEnum.TOKENCOIN.getValue()) || itemType.equals(ActItemTypeEnum.WITHGOLD.getValue())){
					vo.setItemAmount(new BigDecimal(lls.get(2).toString())) ;
			}
			vo.setAccountNo(accountNo);
			vo.setPlatform(platform);
			result = save(vo) ;
			if(result.isNotOk()){
				return result ;
			}
		}
		return result ;
	}
	
	/**
	 * 功能：新增或修改时保存信息
	 */
	public MisRespResult<Void> save(ImsPrizeRecordVO vo)  {
		MisRespResult<Void> result = new MisRespResult<>() ;
		ImsPrizeRecord entity = ImsBeanUtil.copyNotNull(new ImsPrizeRecord(), vo);
		//查询账号信息是否存在
		ActCustomerInfo actCustomerInfo = actCustomerInfoManager.findByAccountNo(vo.getAccountNo(), vo.getPlatform()) ;
		if(null == actCustomerInfo){
			result.setRespMsg(MisResultCode.Prize23002) ;
			return result ;
		}
		// 查询活动信息
		ActSetting actSetting = actSettingManager.findByactivityPeriods(vo.getActNo()) ;
		if (actSetting == null) {
			logger.error("活动不存在，活动编号：{}", new Object[] { vo.getActNo() });
			result.setRespMsg(MisResultCode.Act20000) ;
			return result;
		}
		//任务物品
		ActTaskItems actTaskItems = actTaskItemsManager.findById(vo.getTaskItemId()) ;
		if (actTaskItems == null) {
			logger.error("奖励物品不存在，奖励物品id：{}", new Object[] { vo.getTaskItemId() });
			result.setRespMsg(MisResultCode.Item31005) ;
			return result;
		}
		// 查询发放的物品
		ActItemsSetting actItemsSetting = actItemsSettingManager.findByItemNumber(actTaskItems.getItemNumber()) ;
		if (actItemsSetting == null) {
			result.setRespMsg(MisResultCode.Item31005) ;
			return result;
		}
		//任务设置信息
		ActTaskSetting ats = actTaskSettingManager.findById(actTaskItems.getTaskSettingId()) ;
		ActTaskCustomSetting task = actTaskCustomSettingManager.findObjectByTaskCode(ats.getTaskItemCode()) ;
		if(null == task){
			logger.error("活动任务不存在，任务taskCode：{}", new Object[] { entity.getTaskCode() });
			result.setRespMsg(MisResultCode.Task22000) ;
			return result;
		}
		
		ActTaskSettingVO actTaskSettingVO = ImsBeanUtil.copyNotNull(new ActTaskSettingVO(), ats) ;
		commonDubboService.wrapTaskFullName(actTaskSettingVO,task.getTaskName());
		
		// 实物：发放状态：在库，非实物：发放中
		if (ActItemTypeEnum.REAL.getValue().equals(actItemsSetting.getItemType())) {// 实物
			entity.setItemPrice(actItemsSetting.getItemPrice());// 物品价格
			entity.setItemPriceUnit(actItemsSetting.getItemUnit());// 物品单位
			if(null == entity.getGivedStatus())
			entity.setGivedStatus(IssuingStatusEnum.in_library.getValue());
		} else {
			if(null == entity.getGivedStatus())
			entity.setGivedStatus(IssuingStatusEnum.issuing.getValue());
		}
		if (ActItemTypeEnum.VIRTUAL.getValue().equals(actItemsSetting.getItemType())) {// 虚拟物品,直接取虚拟物品设置的
			// 非串码
			if (!ActItemCategoryEnum.STRINGCODE.getValue().equals(actItemsSetting.getItemCategory())) {
				// 话费
				entity.setItemAmount(new BigDecimal(actItemsSetting.getItemCategoryAmount()));
				entity.setItemAmountUnit(UnitEnums.RMB.getCode());// 发放额度单位
				if(null != actItemsSetting.getItemPrice()){
					entity.setItemAmount(BigDecimalUtil.roundDown(actItemsSetting.getItemPrice().toString(),2));// 领取额度
					entity.setItemAmountUnit(actItemsSetting.getItemUnit());// 发放额度单位
					
				}
			}
		}
		if (ActItemTypeEnum.INTERFACE.getValue().equals(actItemsSetting.getItemType())) {
			// 接口物品
			if (ActItemCategoryEnum.MEMBERVIP.getValue().equals(actItemsSetting.getItemCategory())) {
				// 会员
				entity.setItemAmountUnit(UnitEnums.MONTH.getCode());// 发放额度单位
			}
		}
		String pno = seqDubboService.getSeq(SeqEnum.ActivityPrizeRecordNumber.getSeqCode(), vo.getCompanyId()); 
		if (pno == null) {
			result.setRespMsg(MisResultCode.Error10011) ;
			logger.error("获取发放物品流水号失败！");
			return result;
		}
		entity.setRecordNo(pno);
		entity.setAuditStatus(AuditStatusEnum.waitForApprove.getValue());// 审核通过
		if(actSetting.getAutoHandOut() == 1){
			entity.setAuditStatus(AuditStatusEnum.auditPass.getValue());
		}
		entity.setTaskType(task.getTaskType());
		entity.setTaskName(actTaskSettingVO.getTaskFullName());
		entity.setTaskCode(actTaskSettingVO.getTaskItemCode());
		entity.setActName(actSetting.getActivityName());
		entity.setCustEmail(actCustomerInfo.getEmail());
		entity.setCustMobile(actCustomerInfo.getMobile());
		entity.setCustName(actCustomerInfo.getChineseName());
		entity.setItemName(actItemsSetting.getItemName());// 物品名称
		entity.setItemProbability(actItemsSetting.getItemRate());
		entity.setItemCategory(actItemsSetting.getItemCategory());
		entity.setItemType(actItemsSetting.getItemType());
		entity.setItemPrice(actItemsSetting.getItemPrice());
		entity.setItemPriceUnit(actItemsSetting.getItemUnit());
		entity.setItemAmountUnit(actItemsSetting.getItemUnit());
		entity.setWaitingStatus(getWaitSendReason(actItemsSetting));
		if (entity.getWaitingStatus() == 0) {// 可发时
			// 实物、虚拟物品、接品物品有数量控制和时间限制
			Integer itemUsableAmount = null;
			Integer itemStockAmount = null;
			if (ActItemTypeEnum.REAL.getValue().equals(actItemsSetting.getItemType())
					|| ActItemTypeEnum.VIRTUAL.getCode().equals(actItemsSetting.getItemType())
					|| ActItemTypeEnum.INTERFACE.getCode().equals(actItemsSetting.getItemType())) {
				itemUsableAmount = -1;//  实际可用数量需要减一
				if (!ActItemTypeEnum.REAL.getValue().equals(actItemsSetting.getItemType())) {// 非实物，实物的话当出库时，实际剩余数量再减一
					itemStockAmount = -1;//非实物的话，库存数量也要减一
				}
				if(actItemsSetting.getItemStockAmount() > 0 && actItemsSetting.getItemUsableAmount() > 0){
					actItemsSettingManager.updateActItemsAmount(actItemsSetting.getItemNumber(), itemStockAmount, itemUsableAmount);
				}else{
					Set<String> waitSendReason = new HashSet<String>();// 应发原因集合
					waitSendReason.add(ActWaitSendReasonEnum.NOTENOUGH.getCode());
					entity.setWaitingStatus(WaitSendReasonUtil.coverReason(waitSendReason));
				}
			}
		}
		if (ActTypeEnum.WPDH.getCode().equals(actSetting.getActivityType())) {
			entity.setExt3(actTaskItems.getEqualValue() == null ? null : actTaskItems.getEqualValue().toString());// 等额值
		}
		// 后台添加记录，保存其它信息，以供后续核对
		this.saveOrUpdate(entity);// 添加发放记录
		
		//如果是实物，则需要产生订单记录
		if (ActItemTypeEnum.REAL.getValue().equals(actItemsSetting.getItemType())) {// 实物
			createOrder(entity, actSetting.getActivityName(),task.getTaskCode());
		}
		result.setExtendData(pno) ;
		return result;
	}
	
	/**
	 * 保存发放记录之前，判断是否可发放
	 * @param vo
	 * @return
	 */
	public MisRespResult<Void> beforeSave(ImsPrizeRecord entity,ActSetting actSetting,ActItemsSetting actItemsSetting,ActTaskItems actTaskItems)  {
		MisRespResult<Void> result = new MisRespResult<>() ;
		
		return result ;
	}
	
	/**
	 * 生成订单
	 * 
	 * @param prizeRecord
	 */
	private void createOrder(ImsPrizeRecord prizeRecord, String activityName, String taskCode) {
		ImsOrder imsOrder = new ImsOrder();
		imsOrder.beforeSave(); 
		imsOrder.setRecordNumber(prizeRecord.getRecordNo());
		imsOrder.setActivityPeriods(prizeRecord.getActNo());
		imsOrder.setActivityName(activityName);
		imsOrder.setTaskCode(taskCode);
		imsOrder.setTaskName(prizeRecord.getTaskName());
		imsOrder.setItemsCode(prizeRecord.getItemNo());
		imsOrder.setItemsName(prizeRecord.getItemName());
		imsOrder.setAccountNo(prizeRecord.getAccountNo());
		imsOrder.setChineseName(prizeRecord.getCustName());
		imsOrder.setMobile(prizeRecord.getCustMobile());
		imsOrder.setDeliveryStatus(0);// 未发货
		imsOrder.setCreateUser(prizeRecord.getCreateUser());
		imsOrder.setCreateIp(prizeRecord.getCreateIp());
		imsOrder.setCompanyId(prizeRecord.getCompanyId());
		imsOrder.setGivedStatus(prizeRecord.getGivedStatus());
		imsOrder.setAuditStatus(prizeRecord.getAuditStatus()); 
		misOrderManager.saveOrder(imsOrder);
	}
	
	/**
	 * 获取应发的原因
	 * 
	 * @param actItemsSetting
	 * @return
	 */
	private int getWaitSendReason(ActItemsSetting actItemsSetting) {
		Set<String> waitSendReason = new HashSet<String>();// 应发原因集合
		// 是否禁用
		if (!"Y".equals(actItemsSetting.getEnableFlag())) {// 禁用
			waitSendReason.add(ActWaitSendReasonEnum.DISABLED.getCode());
		}
		// 数量是否足够
		if (ActItemTypeEnum.REAL.getValue().equals(actItemsSetting.getItemType())
				|| ActItemTypeEnum.VIRTUAL.getCode().equals(actItemsSetting.getItemType())
				|| ActItemTypeEnum.INTERFACE.getCode().equals(actItemsSetting.getItemType())) {// 实物/虚拟物品/接口物品，有数品数量限制
			if (null == actItemsSetting.getItemUsableAmount() || actItemsSetting.getItemUsableAmount() <= 0) {
				waitSendReason.add(ActWaitSendReasonEnum.NOTENOUGH.getCode());
			}
		}
		Date curTime = new Date();
		// 时间限制：
		if (actItemsSetting.getEndDate() != null && curTime.after(actItemsSetting.getEndDate())) { // 时间过期
			waitSendReason.add(ActWaitSendReasonEnum.TIMEEXPIRED.getCode());
		} else if (actItemsSetting.getStartDate() != null && curTime.before(actItemsSetting.getStartDate())) { // 时间未到
			waitSendReason.add(ActWaitSendReasonEnum.TIMENOTREACHED.getCode());
		}
		return WaitSendReasonUtil.coverReason(waitSendReason);
	}
	
	
	
	
	/**
	 * 功能：批量操作发放记录
	 * @param ids 操作的ID，多个用,隔开
	 * @param action  pass审批通过  refuse审批拒绝  outlib出库  issue发放成功  redo重新发放
	 * @param remark 操作的备注
	 * @return
	 */
	public MisRespResult<Void> batchOperate(String id,String action,String remark,String actNo,BaseVO base) {
		MisRespResult<Void> result = new MisRespResult<Void>() ;
		if(StringUtils.isEmpty(action)){
			return result.setRespMsg(MisResultCode.FAIL) ;
		}
		List<String> ids = Arrays.asList(id.split(","));
		List<ImsPrizeRecord> list = imsPrizeRecordDao.findByIds(ids,actNo) ;
		if(list.size() == 0){
			return result.setRespMsg(MisResultCode.Prize23001) ;
		}
		result = validBeforeBatchOperate(action, list) ;
		if(result.isNotOk()){
			return result ;
		}
		for(ImsPrizeRecord imsPrizeRecord:list){
			ImsBeanUtil.copyNotNull(imsPrizeRecord, base); 
			ActItemsSetting item = actItemsSettingManager.findByItemNumber(imsPrizeRecord.getItemNo()) ;
			if(null == item){
				item = new ActItemsSetting() ;
			}
			if(null != remark){
				imsPrizeRecord.setRemark(remark);
			}
			switch (action) {
				case "pass":
					imsPrizeRecord.setAuditStatus(AuditStatusEnum.auditPass.getValue());
					if (ActItemTypeEnum.REAL.getValue().equals(imsPrizeRecord.getItemType())) {
						// 实物修改原先订单的审核状态
						ImsOrderVO imsOrder = misOrderManager.findByRecordNumber(imsPrizeRecord.getRecordNo());
						if (imsOrder != null) {
							imsOrder.setAuditStatus(AuditStatusEnum.auditPass.getValue());
							misOrderManager.updateOrder(imsOrder) ;
						}
					}
					break;
				case "refuse":
					imsPrizeRecord.setAuditStatus(AuditStatusEnum.auditRefuse.getValue());
					imsPrizeRecord.setGivedStatus(IssuingStatusEnum.issue_cancel.getValue());
					//如果是实物 
					if (ActItemTypeEnum.REAL.getValue().equals(imsPrizeRecord.getItemType())) {
						//实物物品可用数量加1
						item.setItemUsableAmount(item.getItemUsableAmount()+1);
						// 实物修改原先订单收货状态
						ImsOrderVO imsOrder = misOrderManager.findByRecordNumber(imsPrizeRecord.getRecordNo());
						if (imsOrder != null && imsOrder.getDeliveryStatus() == 0) {// 为未发货时修改为已取消
							imsOrder.setDeliveryStatus(2);// 修改已取消
							imsOrder.setDeliveryDate(new Date());
							imsOrder.setAuditStatus(AuditStatusEnum.auditRefuse.getValue());//修改为审批不通过状态 ： george.li
							misOrderManager.updateOrder(imsOrder) ;
						}
					}else{
						//虚拟物品 数量都加1
						item.setItemUsableAmount(item.getItemUsableAmount()==null?null:item.getItemUsableAmount()+1);
						item.setItemStockAmount(item.getItemStockAmount()==null?null:item.getItemStockAmount()+1);
					}
					//物品兑换活动被扣除的模拟币对等值返回给用户
					if(ActTypeEnum.WPDH.getCode().equals(imsPrizeRecord.getActNo()) && !StringUtils.isEmpty(imsPrizeRecord.getExt3())){
						ActSetting actSetting = actSettingManager.findByactivityPeriods(imsPrizeRecord.getActNo()); 
	                    DemoCashAdjustDto reqDto = new DemoCashAdjustDto();
	                    reqDto.setAccountNo(imsPrizeRecord.getAccountNo());
	                    reqDto.setAmount(new BigDecimal(imsPrizeRecord.getExt3()));
	                    reqDto.setCompanyId(imsPrizeRecord.getCompanyId());
	                    reqDto.setDemoKeepAmount(actSetting.getDemoKeepAmount());
	                    reqDto.setEnv(imsPrizeRecord.getEnv());
	                    reqDto.setOperateType("1");//充值
	                    reqDto.setPlatform(imsPrizeRecord.getPlatform());
	                    MisRespResult<Void> apiResult = actDemoCashAdjustManager.demoCashAdjust(reqDto) ;
	                    if(apiResult.isNotOk()){
	                        return result;
	                    }else  if(apiResult.isOk() && apiResult.getExtendData()!=null){//成功时有扩展信息需保存到其它信息中
	                        imsPrizeRecord.setOtherMsg(apiResult.getExtendData().toString()+";");
	                    } 
					}
					break;
				case "cancel": //取消发放
					imsPrizeRecord.setGivedStatus(IssuingStatusEnum.issue_cancel.getValue());
					//如果是实物 
					if (ActItemTypeEnum.REAL.getValue().equals(imsPrizeRecord.getItemType())) {
						//实物物品可用数量加1
						item.setItemUsableAmount(item.getItemUsableAmount()+1);
						// 实物修改原先订单收货状态
						ImsOrderVO imsOrder = misOrderManager.findByRecordNumber(imsPrizeRecord.getRecordNo());
						if (imsOrder != null && imsOrder.getDeliveryStatus() == 0) {// 为未发货时修改为已取消
							imsOrder.setDeliveryStatus(2);// 修改已取消
							imsOrder.setDeliveryDate(new Date());
							imsOrder.setAuditStatus(AuditStatusEnum.auditRefuse.getValue());//修改为审批不通过状态 ： george.li
							misOrderManager.updateOrder(imsOrder) ;
						}
					}else{
						//虚拟物品 数量都加1
						item.setItemUsableAmount(item.getItemUsableAmount()==null?null:item.getItemUsableAmount()+1);
						item.setItemStockAmount(item.getItemStockAmount()==null?null:item.getItemStockAmount()+1);
					}
					//物品兑换活动被扣除的模拟币对等值返回给用户
					if(ActTypeEnum.WPDH.getCode().equals(imsPrizeRecord.getActNo()) && !StringUtils.isEmpty(imsPrizeRecord.getExt3())){
						ActSetting actSetting = actSettingManager.findByactivityPeriods(imsPrizeRecord.getActNo()); 
	                    DemoCashAdjustDto reqDto = new DemoCashAdjustDto();
	                    reqDto.setAccountNo(imsPrizeRecord.getAccountNo());
	                    reqDto.setAmount(new BigDecimal(imsPrizeRecord.getExt3()));
	                    reqDto.setCompanyId(imsPrizeRecord.getCompanyId());
	                    reqDto.setDemoKeepAmount(actSetting.getDemoKeepAmount());
	                    reqDto.setEnv(imsPrizeRecord.getEnv());
	                    reqDto.setOperateType("1");//充值
	                    reqDto.setPlatform(imsPrizeRecord.getPlatform());
	                    MisRespResult<Void> apiResult = actDemoCashAdjustManager.demoCashAdjust(reqDto) ;
	                    if(apiResult.isNotOk()){
	                        return result;
	                    }else  if(apiResult.isOk() && apiResult.getExtendData()!=null){//成功时有扩展信息需保存到其它信息中
	                        imsPrizeRecord.setOtherMsg(apiResult.getExtendData().toString()+";");
	                    } 
					}
					break;
				case "issue":
					imsPrizeRecord.setGivedStatus(IssuingStatusEnum.issue_success.getValue());
					// 代币，增加代币有效期
					if (ActItemTypeEnum.TOKENCOIN.getCode().equals(item.getItemType())) {
						Date curTime = new Date();
						imsPrizeRecord.setUseStartTime(curTime);
						ActSetting actSetting = actSettingManager.findByactivityPeriods(imsPrizeRecord.getActNo()) ;
						if (actSetting != null && actSetting.getCoinDays() != null
								&& actSetting.getCoinDays() > 0) {
							imsPrizeRecord.setUseEndTime(DateUtil.addDay(curTime, actSetting.getCoinDays()));
						} else {
							imsPrizeRecord.setUseEndTime(curTime);
						}
					}else if (ActItemTypeEnum.WITHGOLD.getCode().equals(item.getItemType())){
						//释放指定账号的赠金--------------------------------待处理------------------------
						if(ActEnvEnum.DEMO.getValue().equals(imsPrizeRecord.getEnv())){
						  //  notifyApprovePrizeRecord(notifyDemoApprovePrizeAccountNos,imsPrizeRecord.getCompanyCode(),ActEnvEnum.DEMO.getValue());
						}else{
						  //  notifyApprovePrizeRecord(notifyApprovePrizeAccountNos,imsPrizeRecord.getCompanyCode(),ActEnvEnum.REAL.getValue());
						}
					}
					break;
				case "outlib":
					imsPrizeRecord.setGivedStatus(IssuingStatusEnum.out_library.getValue());
					item.setItemStockAmount(item.getItemStockAmount()-1);
					break;
				case "redo":
					MisRespResult<Void> res = updateForRedo(imsPrizeRecord, remark); 
					if(res.isOk()){
						imsPrizeRecord = (ImsPrizeRecord) res.getExtendData() ;
						//重发贈金,代币
						//redoBonusFailureRecord(id2prizeRecordMap.values(), UserContext.get().getCompanyCode());
					}else{
						return res ;
					}
					break; 
				default:
					result.setRespMsg(MisResultCode.Err10002) ;
					return result;
			}
			this.saveOrUpdate(imsPrizeRecord);
			if(null != item.getId() && item.getId() > 0){
				if(null != item.getItemCategoryAmount() && null != item.getItemUsableAmount())
				actItemsSettingManager.updateActItemsAmount(item.getItemNumber(), item.getItemCategoryAmount(), item.getItemUsableAmount());
			}
			
		}
		
		return result ;
	}
	
	
	/**
     * 功能：批量执行前验证是否通过
     */
    private MisRespResult<Void> validBeforeBatchOperate(String action,List<ImsPrizeRecord> voList){
		String msg = null ;
        for (ImsPrizeRecord imsPrizeRecord : voList) {
            if (imsPrizeRecord != null) {
            	switch (action) {
            		case "pass":
            			if(!AuditStatusEnum.waitForApprove.getValue().equals(imsPrizeRecord.getAuditStatus())){
            				 msg = String.format("操作失败:流水号%s,非待审核状态，不能审核，请确认！", imsPrizeRecord.getRecordNo());
            			}
            			break;
            		case "refuse":
            			if(!AuditStatusEnum.waitForApprove.getValue().equals(imsPrizeRecord.getAuditStatus())){
           				 	msg = String.format("操作失败:流水号%s,非待审核状态，不能审核，请确认！", imsPrizeRecord.getRecordNo());
            			}
            			break;
            		case "cancel":
            			if(IssuingStatusEnum.out_library.getValue().equals(imsPrizeRecord.getGivedStatus()) || IssuingStatusEnum.issue_success.getValue().equals(imsPrizeRecord.getGivedStatus())|| IssuingStatusEnum.issue_cancel.getValue().equals(imsPrizeRecord.getGivedStatus())){
           				 	msg = String.format("操作失败:流水号%s,当前状态不允许取消发放，请确认！", imsPrizeRecord.getRecordNo());;
            			}
            			break;
            		case "outlib":
            			if(!ActItemTypeEnum.REAL.getValue().equals(imsPrizeRecord.getItemType())){
            				 msg = String.format("操作失败:流水号%s,只有物品类型为实物才允许出库，请确认！", imsPrizeRecord.getRecordNo());
            			}else if(!AuditStatusEnum.auditPass.getValue().equals(imsPrizeRecord.getAuditStatus())){
	                		msg = String.format("操作失败:流水号%s,此记录未审核通过，不允许出库，请确认！", imsPrizeRecord.getRecordNo());
	                	}else if(!IssuingStatusEnum.in_library.getValue().equals(imsPrizeRecord.getGivedStatus())){
            				msg = String.format("操作失败:流水号%s,此记录发放状态为非在库，不允许出库，请确认！", imsPrizeRecord.getRecordNo());
            			}
            			break;
            		case "issue":
            			if(ActItemTypeEnum.REAL.getValue().equals(imsPrizeRecord.getItemType())){
            				msg = String.format("操作失败:流水号%s,只有物品类型为非实物才允许设置发放成功，请确认！", imsPrizeRecord.getRecordNo());
            			}else if(!AuditStatusEnum.auditPass.getValue().equals(imsPrizeRecord.getAuditStatus())){
            				msg = String.format("操作失败:流水号%s,此记录未审核通过，不允许设置发放成功，请确认！", imsPrizeRecord.getRecordNo());
            			}
            			break;
            		case "redo":
            			if( ActItemTypeEnum.REAL.getValue().equals(imsPrizeRecord.getItemType())){
            				msg = String.format("操作失败:流水号%s,实物不支持重新发放，请确认！", imsPrizeRecord.getRecordNo());
            			}else if(!IssuingStatusEnum.issue_fail.getValue().equals(imsPrizeRecord.getGivedStatus())){
            				msg = String.format("操作失败:流水号%s,此记录发放状态非发放失败，不允许重新发放，请确认！", imsPrizeRecord.getRecordNo());
            			}else if(!AuditStatusEnum.auditPass.getValue().equals(imsPrizeRecord.getAuditStatus())){
            				msg = String.format("操作失败:流水号%s,此记录未审核通过，不允许重新发放，请确认！", imsPrizeRecord.getRecordNo());
            			}
            			break;
            		default:
            			break;
            	}
            }
        }
        if(StringUtils.isEmpty(msg)){
        	return MisRespResult.success() ;
        }else{
        	return MisRespResult.error(msg) ;      	
        }
    }
    
    /**
	 * 非实物重新发放
	 * 
	 * @param idArray
	 * @return
	 */
	public MisRespResult<Void> updateForRedo(ImsPrizeRecord imsPrizeRecord, String remark) {
		MisRespResult<Void> result = new MisRespResult<>() ;
		if (imsPrizeRecord == null) {
			result.setRespMsg(MisResultCode.FAIL) ;
			return result ;
		}
		ActSetting actSetting = actSettingManager.findByactivityPeriods(imsPrizeRecord.getActNo());
		if (actSetting != null) {
			ImsPrizeRecord entity = new ImsPrizeRecord();
			if (!StringUtils.isEmpty(imsPrizeRecord.getThirdRecordNo())) {// 重新生成新记录
				ImsBeanUtil.copyNotNull(entity, imsPrizeRecord) ;
				entity.setId(null);
				entity.setCreateDate(new Date());
				String pno = seqDubboService.getSeq(SeqEnum.ActivityPrizeRecordNumber.getSeqCode(), imsPrizeRecord.getCompanyId()) ;
				if (pno == null) {
					logger.error("获取发放物品流水号失败！");
					result.setRespMsg(MisResultCode.Error10011) ;
					return result;
				}
				entity.setRecordNo(pno);
			} else {
				entity.setId(imsPrizeRecord.getId());
			}
			// 根据物品编号
			ActItemsSetting actItemsSetting = actItemsSettingManager.findByItemNumber(imsPrizeRecord.getItemNo()) ;
			if (actItemsSetting != null) {
				Date curTime = new Date();
				// 数品数量限制和时间限制，（发放失败前，之前是可发的，可发的话数量之前有减,此次无需考虑各物品数量），只需考虑时间限制及是否禁用
				Set<String> waitSendReason = new HashSet<String>();// 应发原因集合
				// 是否禁用
				if (!"Y".equals(actItemsSetting.getEnableFlag())) {// 禁用
					waitSendReason.add(ActWaitSendReasonEnum.DISABLED.getCode());
				}
				// 时间限制：
			   if (actItemsSetting.getStartDate() != null && curTime.before(actItemsSetting.getStartDate())) { // 时间未到
					waitSendReason.add(ActWaitSendReasonEnum.TIMENOTREACHED.getCode());
				}else if (actItemsSetting.getEndDate() != null && curTime.after(actItemsSetting.getEndDate())) { // 时间过期
                    waitSendReason.add(ActWaitSendReasonEnum.TIMEEXPIRED.getCode());
                }
				entity.setWaitingStatus(WaitSendReasonUtil.coverReason(waitSendReason));// 可发
				entity.setGivedStatus(IssuingStatusEnum.issuing.getValue());
				entity.setRemark(remark);
				if (actSetting.getAutoHandOut() == 1) {// 自动发放
					entity.setAuditStatus(AuditStatusEnum.auditPass.getValue());// 审核通过
				} else {
					entity.setAuditStatus(AuditStatusEnum.waitForApprove.getValue());// 待审核
				}
				result.setExtendData(entity) ;
			}
		}
		return result;
	}
	
	public MisRespResult<Void> checkAccountList(List<List<Object>> list,String itemType){
		for(int i = 0 ;i< list.size() ;i++){
			List<Object> lls = list.get(i) ;
			if(lls.size() != 3){
				return MisRespResult.error(MisResultCode.Error10012) ;
			}
			String accountNo = lls.get(0).toString() ;
			String platform = lls.get(1).toString() ;
			if(itemType.equals(ActItemTypeEnum.ANALOGCOIN.getValue()) || itemType.equals(ActItemTypeEnum.TOKENCOIN.getValue()) || itemType.equals(ActItemTypeEnum.WITHGOLD.getValue())){
				try{
					new BigDecimal(lls.get(2).toString()) ;
				}catch(Exception e){
					return MisRespResult.error(MisResultCode.Prize23003) ;
				}
			}
			ActCustomerInfo actCustomerInfo = actCustomerInfoManager.findByAccountNo(accountNo, platform) ;
			if(null == actCustomerInfo){
				return MisRespResult.error(MisResultCode.Prize23002) ;
			}
			
		}
		return MisRespResult.success() ;
	}
	
	/**
	 * 查询用户参加本活动所有的发放记录
	 * @param account
	 * @param actNo
	 * @return
	 */
	public List<VActPrizeRecord> findVByAccount(String account,String itemType,String actNo){
		return vActPrizeRecordDao.findListByAccount(account,itemType,actNo);
	}
	
	/**
	 * 查找发放记录
	 * @param recordNo
	 * @param actNo
	 * @return
	 */
	public VActPrizeRecord findVByRecordNo(String recordNo, String actNo) {
		return vActPrizeRecordDao.getActPriceRecord(recordNo,actNo);
	}
	
	/**
	 * 
	 * @param imsPrizeRecordExt
	 */
	public boolean saveOrUpdateExt(ActPrizeRecordExt imsPrizeRecordExt) {
		//寻找是否存在来决定是否更新
		ActPrizeRecordExt recordExt=imsPrizeRecordExtDao.getActPrizeRecordExt(imsPrizeRecordExt.getRecordNumber(),imsPrizeRecordExt.getActNo());
		return recordExt==null ? imsPrizeRecordExtDao.save(imsPrizeRecordExt)>0 : imsPrizeRecordExtDao.updateByRecordNumber(imsPrizeRecordExt)>0;
	}
}