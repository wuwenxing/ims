package com.gwghk.ims.activity.manager.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.gwghk.ims.activity.cache.ActSettingLocalCache;
import com.gwghk.ims.activity.dao.entity.ActAccountActiviStat;
import com.gwghk.ims.activity.dao.entity.ActBlackList;
import com.gwghk.ims.activity.dao.entity.ActConditionSetting;
import com.gwghk.ims.activity.dao.entity.ActCustomerInfo;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ActTaskItems;
import com.gwghk.ims.activity.dao.entity.ActTaskSetting;
import com.gwghk.ims.activity.dao.entity.ActWhiteList;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.activity.dao.entity.ImsTaskRecord;
import com.gwghk.ims.activity.manager.ActAccountActiviStatManager;
import com.gwghk.ims.activity.manager.ActBlackListManager;
import com.gwghk.ims.activity.manager.ActCustomerInfoManager;
import com.gwghk.ims.activity.manager.ActTaskItemsManager;
import com.gwghk.ims.activity.manager.ActTaskSettingManager;
import com.gwghk.ims.activity.manager.ActWhiteListManager;
import com.gwghk.ims.activity.manager.ImsPrizeRecordManager;
import com.gwghk.ims.activity.manager.ImsTaskRecordManager;
import com.gwghk.ims.activity.manager.prize.ActPrizeRecordApiManager;
import com.gwghk.ims.activity.util.BeanFactoryUtil;
import com.gwghk.ims.common.common.ApiRespResult;
import com.gwghk.ims.common.common.ApiResultCode;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.enums.ActAccountTypeEnum;
import com.gwghk.ims.common.enums.ActProposalStatusEnum;
import com.gwghk.ims.common.enums.ActTypeEnum;
import com.gwghk.ims.common.enums.IsPrizeRecordEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.util.DateUtil;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ActCustomerInfoVO;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;

/**
 * 
 * 摘要：活动任务API入口，负责产生发放记录
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年5月3日
 */
@Component
public class ActivityApiManager {

	Logger logger = LoggerFactory.getLogger(ActivityApiManager.class);

	@Autowired
	private ActSettingLocalCache actSettingLocalCache;
	@Autowired
	private ActWhiteListManager actWhiteListManager;
	@Autowired
	private ActBlackListManager actBlackListManager;
	@Autowired
	private ActCustomerInfoManager actCustomerInfoManager;
	@Autowired
	private ImsPrizeRecordManager imsPrizeRecordManager;
	@Autowired
	private ImsTaskRecordManager imsTaskRecordManager;
	@Autowired
	private ActTaskItemsManager actTaskItemsManager;
	@Autowired
	private ActTaskSettingManager actTaskSettingManager ;
	@Autowired
	private ActAccountActiviStatManager actAccountActiviStatManager ;
	@Autowired
	private ActPrizeRecordApiManager actPrizeRecordApiManager;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor ;
	
	List<ImsTaskRecord> waitTask = new ArrayList<>() ;
	

	/**
	 * 自动判断用户是否有符合的活动，并产生相应的发放记录
	 * 
	 * @param accountNo
	 *            账号
	 * @param platform
	 *            平台
	 * @param accType
	 *            账号类型 demo|real
	 * @param companyId
	 */
	public void autoPrizeRecord(String accountNo, String platform, String accType, Long companyId) {
		logger.info("autoPrizeRecord:当前账号：{}，平台：{}，账号类型：{}，公司ID：{}",
				new Object[] { accountNo, platform, accType, companyId });
		// step1 获取进行中的活动（根据公司ID，活动开始时间，结束时间，活动状态），排除物品兑换型活动
		List<ActSetting> acts = findOngoingAct(companyId);
		if (null == acts) {
			return;
		}
		// step2 判断账号的参与条件是否符合要求
		for (int i = 0; i < acts.size(); i++) {
			ActSetting act = acts.get(i);
			if (!ActProposalStatusEnum.ACTHASAPPROVED.getCode().equals(act.getProposalStatus())) {
				continue;
			}
			//已经清算的用户不允许继续参加
			if(!checkSettleByAccount(act.getActivityPeriods(), accountNo, platform)){
				continue ;
			}
			/*if(!"fx_rw_20180531154451".equals(act.getActivityPeriods())){
				continue ;
			}*/
			try{
				ActConditionSetting cond = actSettingLocalCache.getActCondCustomInfoByActivityPeriods(act.getActivityPeriods()) ;
				if(null == cond) {
					logger.error("autoPrizeRecord-->活动编号：{},查询不到对应的参与条件！");
					continue;
				}
				if (!checkActCondition(cond, accountNo, platform, accType)) {
					acts.remove(act);
					i--;
				}
				logger.info("autoPrizeRecord:当前用户：{}，可以符合条件的活动：{}", new Object[] { accountNo, acts.size() });
				// step4 判断任务是否完成
				List<ImsTaskRecord> records = beginActivity(act.getActivityType(), act.getActivityPeriods(), accountNo,
						platform, accType, companyId);
				logger.info("autoPrizeRecord:当前用户：{}，已完成任务：{}", new Object[] { accountNo, records });
				if (null != records) {
					// step4.1 保存任务记录
					saveTaskRecord(records);
				}
			} catch (Exception e) {
				logger.error("autoPrizeRecord:异常，活动编号：{}，当前用户：{}",
						new Object[] { act.getActivityPeriods(), accountNo });
				e.printStackTrace();
			}

		}
	}

	/**
	 * 手动产生相应的发放记录(物品兑换型)
	 * 
	 * @param accountNo
	 *            账号
	 * @param platform
	 *            平台
	 * @param companyId
	 */
	public ApiRespResult<Void> handPrizeRecord(Integer taskItemId, String accountNo, String platform, Long companyId) {
		AbstractActivityType actTypeBean = BeanFactoryUtil.getActTypeBean(ActTypeEnum.WPDH.getCode());
		ActTaskItems item = actTaskItemsManager.findById(taskItemId.longValue());
		if (null == item) {
			return ApiRespResult.error(ApiResultCode.Task30002);
		}
		ActTaskSetting actTask = actTaskSettingManager.findById(item.getTaskSettingId());
		//已经清算的用户不允许继续参加
		if(!checkSettleByAccount(actTask.getActivityPeriods(), accountNo, platform)){
			return ApiRespResult.error(ApiResultCode.Task30004);
		}
		ActTaskSettingVO vo = ImsBeanUtil.copyNotNull(new ActTaskSettingVO(), actTask);
		List<ActTaskItemsVO> items = new ArrayList<>();
		items.add(ImsBeanUtil.copyNotNull(new ActTaskItemsVO(), item));
		vo.setTaskItems(items);
		List<ImsTaskRecord> records = actTypeBean.isFinishTask(vo, accountNo, platform, companyId);
		for (ImsTaskRecord ims : records) {
			if (taskItemId == ims.getTaskItemId().intValue()) {
				// 判断该用户该物品已经领取的次数
				if (null != item.getReceiveMaxNum()) {
					int current = imsPrizeRecordManager.countByAccount(actTask.getActivityPeriods(), accountNo,
							actTask.getTaskItemCode(), item.getItemNumber());
					if (current >= item.getReceiveMaxNum()) {
						return ApiRespResult.error(ApiResultCode.Task30003);
					}
				}
				// 扣除账户模拟币
				BigDecimal equalValue = ims.getEqualValue();
				ApiRespResult<Void> res = useAnalogCoin(equalValue, accountNo, platform);
				if (res.isNotOk()) {
					return ApiRespResult.error(res.getCode(), res.getMsg());
				}
				saveTaskRecord(ims);
			}
		}
		return ApiRespResult.success();
	}

	/**
	 * 获取当前公司正在进行中的任务
	 * 
	 * @param companyId
	 * @return
	 */
	private List<ActSetting> findOngoingAct(Long companyId) {
		Set<String> types = new HashSet<>();
		types.add(ActTypeEnum.CJ.getCode());
		types.add(ActTypeEnum.RJZJ.getCode());
		types.add(ActTypeEnum.RW.getCode());
		return actSettingLocalCache.getActSettingList(types, companyId);
	}

	/**
	 * 检查账号是否能参加活动
	 * 
	 * @param cond
	 *            活动条件设置
	 * @param accountNo
	 *            账号
	 * @return
	 */
	private boolean checkActCondition(ActConditionSetting cond, String accountNo, String platform, String accType) {
		// 1检查黑白名单
		List<ActBlackList> blackList = actBlackListManager.findListByActivityPeriods(cond.getActivityPeriods());
		if(null != blackList){
			for (ActBlackList black : blackList) {
				if (black.getAccountNo().equals(accountNo)) {
					logger.info("checkActCondition account:{},黑名单用户不允许参与活动！",accountNo);
					return false;
				}
			}
		}
		// 检查白名单
		boolean isWhite = false;
		List<ActWhiteList> whiteList = actWhiteListManager.findListByActivityPeriods(cond.getActivityPeriods());
		if(null != whiteList){
			for (ActWhiteList white : whiteList) {
				if (white.getAccountNo().equals(accountNo)) {
					logger.info("checkActCondition account:{},白名单参与活动！",accountNo);
					isWhite = true;
					break;
				}
			}
		}
		ActCustomerInfo actCust = actCustomerInfoManager.findByAccountNo(accountNo, platform, accType);
		if (null == actCust) {
			return false;
		}
		if (null != cond.getAccountType() && !ActAccountTypeEnum.NOLIMIT.getCode().equals(cond.getAccountType())) {
			logger.info("checkActCondition account:{},账号类型：{},活动要求类型：{}",accountNo,cond.getAccountType(),"type_" + accType);
			// 检查账号类型
			if (!cond.getAccountType().equals("type_" + accType)) {
				return false;
			}
		}
		// 检查账号唯一性 检查手机号或email或身份证
		if (1 == cond.getAccountOnly()) {
			if (!checkAccountOnly(cond.getActivityPeriods(), actCust, platform)) {
				return false;
			}
		}
		// 不是白名单用户，还需要检查其他条件
		if (!isWhite) {
			if(null == cond.getAccountLevel()){
				return false ;
			}
			// 检查账号级别
			if (!cond.getAccountLevel().contains(actCust.getAccountLevel())) {
				return false;
			}
			// 检查参与的活动平台
			String pccy = platform + "#" + actCust.getAccountCurrency();
			if (!cond.getPlatformsCcy().contains(pccy)) {
				return false;
			}
			// 检查账号注册时间
			if (null != cond.getRegisterStartTime()) {
				if (actCust.getRegisterTime().compareTo(cond.getRegisterStartTime()) < 0) {
					return false;
				}
			}
			if (null != cond.getRegisterEndTime()) {
				if (actCust.getRegisterTime().compareTo(cond.getRegisterEndTime()) > 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 查询出当前活动可以执行的任务
	 * 
	 * @param actNo
	 * @return
	 */
	private List<ImsTaskRecord> beginActivity(String actType, String actNo, String accNo, String platform,
			String accType, Long companyId) {
		logger.info("autoPrizeRecord:开始判断可完成的任务：actType:{},", new Object[] { actType });
		AbstractActivityType actTypeBean = BeanFactoryUtil.getActTypeBean(actType);
		// 物品兑换型活动不能自动完成任务
		if (null == actTypeBean || ActTypeEnum.WPDH.getCode().equals(actType)) {
			logger.error("不支持该活动类型：{}，活动编号：{}", new Object[] { actType, actNo });
			return null;
		}
		return actTypeBean.beginActivity(actNo, accNo, platform, accType, companyId);
	}

	/**
	 * 判断账号是否唯一参与活动
	 * 
	 * @param actNo
	 *            账号
	 * @param actCust
	 *            用户对象
	 * @param platform
	 *            平台
	 * @return false不是唯一参与，true是唯一参与
	 */
	private boolean checkAccountOnly(String actNo, ActCustomerInfo actCust, String platform) {
		String mobile = actCust.getMobile();
		String email = actCust.getEmail();
		String idNumber = actCust.getIdNumber();
		ActCustomerInfoVO vo = new ActCustomerInfoVO();
		vo.setAccountType(actCust.getAccountEnv());
		vo.setPlatform(platform);
		vo.setMobile(mobile);
		// 先判断是否有相同手机号的账号
		List<ActCustomerInfo> list = actCustomerInfoManager.findList(vo);
		for (ActCustomerInfo info : list) {
			// 如果有账号，则判断该账号是否已经产生发放记录
			if (!actCust.getAccountNo().equals(info.getAccountNo())) {
				ImsPrizeRecordVO pvo = new ImsPrizeRecordVO();
				pvo.setActNo(info.getAccountNo());
				List<ImsPrizeRecord> rec = imsPrizeRecordManager.findList(pvo);
				if (rec.size() > 0) {
					return false;
				}
			}
		}
		vo.setMobile(null);
		vo.setEmail(email);
		// 先判断是否有相同手机号的Email
		list = actCustomerInfoManager.findList(vo);
		for (ActCustomerInfo info : list) {
			// 如果有账号，则判断该账号是否已经产生发放记录
			if (!actCust.getAccountNo().equals(info.getAccountNo())) {
				ImsPrizeRecordVO pvo = new ImsPrizeRecordVO();
				pvo.setActNo(info.getAccountNo());
				List<ImsPrizeRecord> rec = imsPrizeRecordManager.findList(pvo);
				if (rec.size() > 0) {
					return false;
				}
			}
		}
		vo.setEmail(null);
		vo.setIdNumber(idNumber);
		// 先判断是否有相同手机号的身份证
		list = actCustomerInfoManager.findList(vo);
		for (ActCustomerInfo info : list) {
			// 如果有账号，则判断该账号是否已经产生发放记录
			if (!actCust.getAccountNo().equals(info.getAccountNo())) {
				ImsPrizeRecordVO pvo = new ImsPrizeRecordVO();
				pvo.setActNo(info.getAccountNo());
				List<ImsPrizeRecord> rec = imsPrizeRecordManager.findList(pvo);
				if (rec.size() > 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 保存发放记录
	 * 
	 * @param actNo
	 *            活动编号
	 * @param accNo
	 *            账号
	 * @param platform
	 *            平台
	 * @param itemNo
	 *            物品编号
	 * @param taskItemId
	 *            任务物品ID act_task_items主键ID
	 */

	private MisRespResult<Void> savePrizeRecord(ImsTaskRecord record){
		//查询发放记录，判断该任务下的物品是否已到达领取上限
		ActTaskItemsVO reqVo = new ActTaskItemsVO() ;
		reqVo.setTaskSettingId(record.getActTaskSettingId().longValue());
		// 如果有子任务的话，拿子任务的ID
		if (null != record.getSubTaskSettingId()) {
			reqVo.setTaskSettingId(record.getSubTaskSettingId().longValue());
		}
		reqVo.setItemNumber(record.getItemNo());
		ActTaskItems task = actTaskItemsManager.findObject(reqVo);
		if (null != task) {
			if (null != task.getFrequencyNum()) {
				Integer count = task.getFrequencyNum();
				Integer day = task.getFrequencyTime().intValue();
				if (null != count && null != day) {
					Date begin = DateUtil.getCurrentDateStartTimeByDay(day);
					Date end = DateUtil.getCurrentDateEndTime();
					int current = imsPrizeRecordManager.countByDate(DateUtil.getDateSecondFormat(begin),
							DateUtil.getDateSecondFormat(end), record.getActNo(), record.getTaskCode(),
							record.getItemNo());
					if (current >= count) {
						record.setTaskRecordTime(end);
						return null;
					}
				}

			}
			ImsPrizeRecordVO vo = new ImsPrizeRecordVO();
			if (record.getIsPrizeRecord() == IsPrizeRecordEnum.ADD_WAITING_RECORD.getKey()) {
				vo.setGivedStatus(IssuingStatusEnum.waiting.getValue());
			} else {

			}
			vo.setTaskRecordId(record.getId());
			vo.setAccountNo(record.getAccountNo());
			vo.setPlatform(record.getPlatform());
			vo.setActNo(record.getActNo());
			vo.setTaskSettingId(record.getActTaskSettingId().longValue());
			vo.setItemNo(record.getItemNo());
			vo.setTaskItemId(record.getTaskItemId());
			vo.setIsAuto(1);
			vo.setAddFromBos(0);
			vo.setEnv(record.getAccountType());
			vo.setItemAmount(record.getItemAmount());
			vo.setItemAmountUnit(record.getItemAmountUnit());
			vo.setCompanyId(record.getCompanyId());
			vo.setTaskFinishedTime(record.getTaskFinishTime());
			vo.setCreateDate(new Date());
			// 如果是%的单位，需要计算实质发放的金额
			if ("%".equals(record.getItemAmountUnit())) {
				ActCustomerInfo cust = actCustomerInfoManager.findByAccountNo(record.getAccountNo(),
						record.getPlatform(), record.getAccountType());
				if (null != cust && null != record.getFinishAmount()) {
					vo.setItemAmount(
							record.getFinishAmount().multiply(record.getItemAmount()).divide(new BigDecimal(100)));
					vo.setItemAmountUnit(cust.getAccountCurrency());
				}
			}
			return imsPrizeRecordManager.save(vo);
		}
		return null;
	}

	/**
	 * 保存任务完成记录
	 * 
	 * @param actNo
	 * @param accountNo
	 * @param platform
	 * @param taskSettingId
	 * @param t
	 */
	protected void saveTaskRecord(List<ImsTaskRecord> records){
		//保存每个任务的完成记录
		for(ImsTaskRecord record :records) {
			saveTaskRecord(record);
		}
	}

	private void saveTaskRecord(ImsTaskRecord record){
		//保存每个任务的完成记录
		record.setEnableFlag("Y");
		record.setDeleteFlag("N");
		record.setCreateDate(new Date());
		imsTaskRecordManager.saveOrUpdate(record);
		if (record.getIsPrizeRecord() != -1 && record.getIsPrizeRecord() != 2) {
			waitTask.add(record);
		}
	}

	/**
	 * 根据任务完成时间，同步发放
	 */
	@PostConstruct
	private void sysnPrizeRecordByTaskTime() {
		// 启动的时候先初始化任务
		if (waitTask.size() == 0) {
			initWaitTask();
		}
		Thread th = new Thread() {
			@Override
			public void run() {
				while (true) {
					logger.debug("当前带发放记录有：" + waitTask.size());
					try {
						// 完成时间从小到大排序
						for (int i = 0; i < waitTask.size(); i++) {
							ImsTaskRecord record = waitTask.get(i);
							if (new Date().getTime() >= record.getTaskRecordTime().getTime()) {
								List<ImsTaskRecord> list = imsTaskRecordManager.findNeedRecordList(record.getActNo());
								if (list.isEmpty()) {
									waitTask.remove(record);
								}
								for (ImsTaskRecord rec : list) {
									MisRespResult<Void> res = savePrizeRecord(rec);
									if (null != res && res.isOk()) {
										rec.setIsPrizeRecord(2);
										imsTaskRecordManager.saveOrUpdate(rec);
										taskExecutor.execute(new Runnable() {
											@Override
											public void run() {
												try{
													actPrizeRecordApiManager.send(res.getExtendData().toString(),null,rec.getActNo(), rec.getAccountType(), rec.getCompanyId());
												}catch(Exception e){
													logger.error("actPrizeRecordApiDubboService send发放记录通知异常：e:{}",e);
												}
											}
										});
										waitTask.remove(record);
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		th.start();
	}

	/**
	 * 初始化需要等待完成的任务
	 */
	private void initWaitTask(){
		List<ActSetting> list = actSettingLocalCache.getActiveActSettingList() ;
		for(ActSetting act : list) {
			try{
				//等待时间到的记录进行发放
				List<ImsTaskRecord> rec = imsTaskRecordManager.findNeedRecordList(act.getActivityPeriods()) ;
				waitTask.addAll(rec) ;
			}catch(Exception e){
				logger.error("初始化任务记录异常：{}",e);
			}
		}
	}

	/**
	 * 扣除账号的模拟币
	 * 
	 * @param accountNo
	 * @param platform
	 * @return
	 */
	private ApiRespResult<Void> useAnalogCoin(BigDecimal equalValue,String accountNo,String platform){
		//调用第三方接口扣除模拟币
		return ApiRespResult.success() ;
	}

	private boolean checkSettleByAccount(String actNo,String accNo,String platform){
		 ActAccountActiviStat stat = actAccountActiviStatManager.findByAccountNo(accNo, actNo, platform) ;
		 if(null == stat || null == stat.getSettleStatus() || stat.getSettleStatus() == 0) return true ;
		 return false ;
	}
}
