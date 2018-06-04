package com.gwghk.ims.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActAccountLevelEnum;
import com.gwghk.ims.common.enums.ActActivateStatusEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActProposalStatusEnum;
import com.gwghk.ims.common.enums.ActRuleTypeEnum;
import com.gwghk.ims.common.enums.ActTaskTypeEnum;
import com.gwghk.ims.common.enums.ActTypeEnum;
import com.gwghk.ims.common.enums.ActUnitEnum;
import com.gwghk.ims.common.enums.FrequencyTimeUnitEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActItemsSettingDubboService;
import com.gwghk.ims.common.inf.mis.activity.MisActSettingDubboService;
import com.gwghk.ims.common.util.ImsDateUtil;
import com.gwghk.ims.common.vo.activity.ActConditionSettingVO;
import com.gwghk.ims.common.vo.activity.ActSettingDetailsVO;
import com.gwghk.ims.common.vo.activity.ActSettingVO;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
import com.gwghk.ims.common.vo.system.SystemUserVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：后台-活动测试用例
 * @author eva.liu
 * @version 1.0
 * @Date 2018年3月29日
 */
public class MisActSettingManagerTest extends BaseTest{

	@Autowired
	private MisActSettingDubboService misActSettingDubboService;
	
	@Autowired
	private MisActItemsSettingDubboService actItemsSettingDubboService;
	
	private Long getCompanyId(){
		return 1L;
	}
	
	@Test
	@Ignore
	public void testFindPageList(){
		System.out.println("------->testFindPageList");
		ActSettingVO actSettingVO = new ActSettingVO();
		actSettingVO.setCompanyId(getCompanyId());
//		actSettingVO.setProposalStatus(ActProposalStatusEnum.ACTHASAPPROVED.getCode());
		MisRespResult<PageR<ActSettingVO>> misResult = misActSettingDubboService.findPageList(actSettingVO,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				List<ActSettingVO> list = misResult.getData().getList();
				System.err.println(list.size());
				System.out.println(JsonUtil.obj2Str(list));
			}
		}else{
			System.out.println("<-------fail");
		}
	}
	
	@Test
	@Ignore
	public void testFindList(){
		System.out.println("------->testFindList");
		ActSettingVO actSettingVO = new ActSettingVO();
		actSettingVO.setCompanyId(getCompanyId());
		MisRespResult<List<ActSettingVO>> misResult = misActSettingDubboService.findList(actSettingVO,true,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				List<ActSettingVO> list = misResult.getData();
				System.err.println("<-------"+list.size());
				System.out.println("<-------"+JsonUtil.obj2Str(list));
			}
		}else{
			System.out.println("<-------fail");
		}
	}
	
	@Test
	 
	@Ignore
	public void testSaveOrUpdateItem(){
		String actType = null;
		 actType = ActTypeEnum.RW.getCode();
//		 actType = ActTypeEnum.RJZJ.getCode();
         testCreateAct(actType);
	}
	
	
    @Test
    @Ignore
	public void testUpdateItem(){
		String activityPeriods="fx_20180413134723";
		testUpdateAct(activityPeriods);
	}
	
	
	/**
	 * 新增活动
	 */
	 
	public String testCreateAct(String actType){
		System.out.println("------->testCreateAct_"+actType);
		ActSettingDetailsVO actItemsSettingVO = new ActSettingDetailsVO();
		actItemsSettingVO = getTempActVo(actType);
		MisRespResult<Void> misResult = misActSettingDubboService.saveOrUpdate(actItemsSettingVO,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
		}else{
			System.out.println("<-------fail");
		}
		return actItemsSettingVO.getActBaseInfo().getActivityPeriods();
	}
	
	/**
	 * 修改活动
	 */
	public void testUpdateAct(String actType){
		System.out.println("------->testUpdateItem_"+actType);
		ActSettingDetailsVO oldVO = findByActivityPeriods(actType);
		ActSettingVO actBaseInfo = oldVO.getActBaseInfo();
		actBaseInfo.setActivityName(actBaseInfo.getActivityName()+"--修改");
		actBaseInfo.setTurnGroup(0);
		actBaseInfo.setTransfer(0);
		actBaseInfo.setPriorityPlatform("GTS2");
		oldVO.setActBaseInfo(actBaseInfo);
		
		ActConditionSettingVO actCondSetting = oldVO.getActCondSetting();
		oldVO.setActCondSetting(actCondSetting);
		
		List<ActTaskSettingVO> taskList = oldVO.getActTaskSettings();
		 int i=0;
		for(ActTaskSettingVO task : taskList){
			if(i==0){//第一个任
				 //其它物品都删除
				//增加一个新物品
				List<ActTaskItemsVO>  items = new ArrayList<ActTaskItemsVO>();
				items.add(getTempVirtualActTaskItems(true));
				task.setTaskItems(items);
			}
			else if(i==1){
					//修改物品
					List<ActTaskItemsVO>  items = task.getTaskItems();
					List<ActTaskItemsVO>  newItems = new ArrayList<ActTaskItemsVO>();
					int j=0;
					 for(ActTaskItemsVO vo : items){
						  if(j==0){//修改第一个物品
							  ActTaskItemsVO itemsVO = getTempVirtualActTaskItems(true);
							  itemsVO.setId(vo.getId());
							  newItems.add(itemsVO);
						  }else{
							  newItems.add(vo);
						  }
						  j++;
					 }
					 task.setTaskItems(newItems);
			} 
			i++;
		}
		oldVO.setActTaskSettings(taskList);
 
		MisRespResult<Void> misResult = misActSettingDubboService.saveOrUpdate(oldVO,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
		}else{
			System.out.println("<-------fail");
		}
		 
	}
	 
	
	/**
	 * 实例化一个界面不同类型的物品参数
	 * @param s
	 * @return
	 */
	private ActSettingDetailsVO getTempActVo(String actType){
		ActSettingDetailsVO vo = new ActSettingDetailsVO();
 
		//基本信息
		ActSettingVO actBaseInfo =getTempActBaseInfo(actType);
		vo.setActBaseInfo(actBaseInfo);
		String activityPeriods = actBaseInfo.getActivityPeriods();
		 
		//参与条件
		ActConditionSettingVO actCondSetting =getTempActCondSetting(activityPeriods);
		vo.setActCondSetting(actCondSetting);
		
		//任务
		List<ActTaskSettingVO> taskList = new ArrayList<ActTaskSettingVO>();
		int taskOrder = 0;
		ActTaskSettingVO task0 =  null;
		if(actType.equals(ActTypeEnum.RJZJ.getCode())){
			 task0 = getTempActTaskSettingVO(activityPeriods,ActTaskTypeEnum.REAL_DEPOSIT.getCode(),null,null,null,null,new BigDecimal(1),ActUnitEnum.HOUR.getCode(),false,false);
			 task0.setTaskOrder(taskOrder++);
			List<ActTaskSettingVO> subTaskSettings = new ArrayList<ActTaskSettingVO>();
			ActTaskSettingVO task = getTempActTaskSettingVO(activityPeriods,ActTaskTypeEnum.REAL_DEPOSIT.getCode(),"100",ActUnitEnum.RMB.getCode(),"500",ActUnitEnum.RMB.getCode(),null,null,false,true);
			task.setTaskOrder(0);
			subTaskSettings.add(task);
			task = getTempActTaskSettingVO(activityPeriods,ActTaskTypeEnum.REAL_DEPOSIT.getCode(),"500",ActUnitEnum.RMB.getCode(),"1000",ActUnitEnum.RMB.getCode(),null,null,false,true);
			task.setTaskOrder(1);
			subTaskSettings.add(task);
			 
			task0.setSubTaskSettings(subTaskSettings);
			taskList.add(task0);
		}
		ActTaskSettingVO task1 = getTempActTaskSettingVO(activityPeriods,ActTaskTypeEnum.REAL_CLOSE_TIMES.getCode(),"1",ActUnitEnum.TIMES.getCode(),null,null,null,null,true,true);
//		ActTaskSettingVO task2 = getTempActTaskSettingVO(activityPeriods,ActTaskTypeEnum.REAL_TOTAL_CLOSE_TIMES.getCode(),"1",ActUnitEnum.TIMES.getCode(),null,null,null,null,true,true);
		ActTaskSettingVO task3 = getTempActTaskSettingVO(activityPeriods,ActTaskTypeEnum.REAL_ACTIVATION.getCode(),"100",ActUnitEnum.RMB.getCode(),null,null,new BigDecimal(2),ActUnitEnum.HOUR.getCode(),false,true);
//		ActTaskSettingVO task4 = getTempActTaskSettingVO(activityPeriods,ActTaskTypeEnum.REAL_CLOSE_LOSS_PERCENT.getCode(),"10",ActUnitEnum.RMB.getCode(),null,null,null,null,true,true);
	    if(actType.equals(ActTypeEnum.RJZJ.getCode())){
	    	task1.setTaskOrder(taskOrder++);
//	    	task2.setTaskOrder(taskOrder++);
	    	task3.setTaskOrder(taskOrder++);
//	    	task4.setTaskOrder(taskOrder++);
	    }
	    taskList.add(task1);
//	    taskList.add(task2);
	    taskList.add(task3);
//	    taskList.add(task4);
		vo.setActTaskSettings(taskList);
		return vo;
	}
	
	private ActSettingVO getTempActBaseInfo(String actType){
		String str = ImsDateUtil.toddHhmmss();
		String activityPeriods = getNewActivityPeriods();
		String activityName ="test_"+ActItemTypeEnum.formatValue(actType)+str;
		ActSettingVO actBaseInfo = new ActSettingVO();
		actBaseInfo.setActivityPeriods(activityPeriods);
		actBaseInfo.setActivityName(activityName);
		actBaseInfo.setActivityType(actType);
		actBaseInfo.setActivityUrl("http://www.baidu.com");
		actBaseInfo.setAutoHandOut(2);//自动发放
		actBaseInfo.setTransfer(2);
		actBaseInfo.setTurnGroup(2);
		actBaseInfo.setWithGoldDays(11);
		actBaseInfo.setWithGoldDaysUnit(ActUnitEnum.DAY.getCode());
		actBaseInfo.setCoinDays(10);//代币有效期
		actBaseInfo.setCoinDaysUnit(ActUnitEnum.DAY.getCode());
		actBaseInfo.setCompanyId(this.getCompanyId());
		actBaseInfo.setCreateDate(new Date());
		actBaseInfo.setUpdateDate(new Date());
		actBaseInfo.setDemoKeepAmount(new BigDecimal(1000));//demo账号保留余额
		actBaseInfo.setDemoKeepAmountUnit(ActUnitEnum.RMB.getCode());
		actBaseInfo.setSettlement(0);
		actBaseInfo.setSettlementDate(new Date());
		actBaseInfo.setEnableFlag("Y");
		actBaseInfo.setFinishDays(10);
		actBaseInfo.setFinishDaysUnit(ActUnitEnum.DAY.getCode());
		actBaseInfo.setMaxExchangeCount(5);
		actBaseInfo.setMaxExchangeCountUnit(ActUnitEnum.TIMES.getCode());
		actBaseInfo.setOtherMsg("其它信息test....");
		actBaseInfo.setPriorityPlatform("MT4");
		actBaseInfo.setRemark("备注test...");
		
		actBaseInfo.setStartTime(new Date());
		actBaseInfo.setEndTime(ImsDateUtil.addDay(new Date(), 30));
		
		actBaseInfo.setWithGoldDays(1);//赠金有效期
		return actBaseInfo;
	}
	 
	private ActConditionSettingVO getTempActCondSetting(String activityPeriods){
		ActConditionSettingVO vo = new ActConditionSettingVO();
		vo.setActivityPeriods(activityPeriods);
		vo.setCompanyId(this.getCompanyId());
		vo.setConditionType(ActRuleTypeEnum.CUSTINFO.getCode());
		vo.setAccountLevel(ActAccountLevelEnum.MINI.getCode()+","+ActAccountLevelEnum.STANDARD.getCode()+","+ActAccountLevelEnum.VIP.getCode());
		vo.setAccountOnly(0);//是否账号唯一
		vo.setAccountStatus(ActActivateStatusEnum.ACTIVATED.getCode());
		vo.setAccountType("type_noLimit");//账号类型:无限制
		vo.setActivateStartTime(ImsDateUtil.addDay(new Date(), 1));
		vo.setActivateEndTime(ImsDateUtil.addDay(new Date(), 3));
		vo.setAllowCancelAccount(1);
		vo.setAllowWhiteUsers(0);
		vo.setAllowWithdrawals(1);
		vo.setRegisterStartTime(ImsDateUtil.addDay(new Date(), 5));
		vo.setRegisterEndTime(ImsDateUtil.addDay(new Date(), 7));
		return vo;
	}
	
	private ActTaskSettingVO getTempActTaskSettingVO(String activityPeriods,
			String taskItemCode,String taskItemVal,String taskItemValUnit,String taskItemVal2
			,String taskItemValUnit2,
			BigDecimal taskItemTime,String taskItemTimeUnit,boolean cycleTask,boolean addItems){
		ActTaskSettingVO vo = new ActTaskSettingVO();
		vo.setActivityPeriods(activityPeriods);
		vo.setTaskItemCode(taskItemCode);
		vo.setTaskItemVal(taskItemVal);
		vo.setTaskItemValUnit(taskItemValUnit);
		vo.setTaskItemVal2(taskItemVal2);
		vo.setTaskItemVal2Unit(taskItemValUnit2);
		vo.setTaskItemTime(taskItemTime);
		vo.setTaskItemTimeUnit(taskItemTimeUnit);
		vo.setTaskDesc("任务描述...");
		vo.setCompanyId(this.getCompanyId());
		if(addItems){
			List<ActTaskItemsVO> taskItems = new ArrayList<ActTaskItemsVO>();
			taskItems.add(getTempRealActTaskItems(cycleTask));
			taskItems.add(getTempGoldActTaskItems(cycleTask));
			taskItems.add(getTempIntefaceActTaskItems(cycleTask));
			 vo.setTaskItems(taskItems);
		}
		return vo;
	}
	
	private ActTaskItemsVO getTempVirtualActTaskItems(boolean cycleTask){
		//virtual
		ActTaskItemsVO vo = new ActTaskItemsVO();
		vo.setItemNumber("eva_virtual_19094842");
		if(cycleTask){
			vo.setReceiveMaxNum(5);
			vo.setReceiveMaxNumUnit(ActUnitEnum.TIMES.getCode());
		}
//		vo.setEqualValue(new BigDecimal(1000));
		vo.setFrequencyTime(new BigDecimal(2));//2天
		vo.setFrequencyTimeUnit(FrequencyTimeUnitEnum.DAY.getValue());//天
		
		vo.setFrequencyNum(10);//10个
		vo.setFrequencyNumUnit(ActUnitEnum.NUM.getCode());
        return vo;
	}
	
	private ActTaskItemsVO getTempRealActTaskItems(boolean cycleTask){
		//实物
		ActTaskItemsVO vo = new ActTaskItemsVO();
		vo.setItemNumber("eva_real_19094759");
		if(cycleTask){
			vo.setReceiveMaxNum(5);
			vo.setReceiveMaxNumUnit(ActUnitEnum.TIMES.getCode());
		}
//		vo.setEqualValue(new BigDecimal(1000));
		vo.setFrequencyTime(new BigDecimal(2));//2天
		vo.setFrequencyTimeUnit(FrequencyTimeUnitEnum.DAY.getValue());//天
		
		vo.setFrequencyNum(10);//10个
		vo.setFrequencyNumUnit(ActUnitEnum.NUM.getCode());
        return vo;
	}
	
	private ActTaskItemsVO getTempGoldActTaskItems(boolean cycleTask){
		//赠金
		ActTaskItemsVO vo = new ActTaskItemsVO();
		vo.setItemNumber("eva_withGold_19094925");
		if(cycleTask){
			vo.setReceiveMaxNum(5);
			vo.setReceiveMaxNumUnit(ActUnitEnum.TIMES.getCode());
		}	
		vo.setItemParamVal("50");
		vo.setItemParamValUnit("%");
		vo.setTradeNum(new BigDecimal(10));
		vo.setTradeNumUnit("%");
		vo.setFrequencyTimeUnit(FrequencyTimeUnitEnum.DAY.getValue());//天
		vo.setFrequencyTime(new BigDecimal(2));//2天
		vo.setFrequencyNum(10);//10个
		vo.setFrequencyNumUnit(ActUnitEnum.NUM.getCode());
        return vo;
	}
	
	
	private ActTaskItemsVO getTempIntefaceActTaskItems(boolean cycleTask){
		//接口
		ActTaskItemsVO vo = new ActTaskItemsVO();
		vo.setItemNumber("eva_interface_19094925");
		if(cycleTask){
			vo.setReceiveMaxNum(5);
			vo.setReceiveMaxNumUnit(ActUnitEnum.TIMES.getCode());
		}
		vo.setInfParamMax(new BigDecimal(3));//最高额度
		vo.setInfParamMaxUnit("G");
		vo.setInfParamY(new BigDecimal(0.1));
		vo.setInfParamZ(new BigDecimal(0.2));
		vo.setItemParamVal("X*Y*Z");
		vo.setItemParamValUnit("M");
		vo.setEqualValue(new BigDecimal(1000));
		vo.setFrequencyTime(new BigDecimal(2));//2天
		vo.setFrequencyTimeUnit(FrequencyTimeUnitEnum.DAY.getValue());//天
		vo.setFrequencyNum(10);//10个
		vo.setFrequencyNumUnit(ActUnitEnum.NUM.getCode());
        return vo;
	}
	
	 
	
	@Test
	@Ignore
    public void testFindByActivityPeriods(){
		System.out.println("------->testFindByItemNumber");
		Long companyId = 1L;
		MisRespResult<ActSettingDetailsVO> misResult = misActSettingDubboService.findByActivityPeriods("fx_20180419095423",companyId);
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				System.out.println(JsonUtil.obj2Str(misResult.getData()));
			}
		}else{
			System.out.println("<-------fail");
		}
	}

	@Test
//	@Ignore
	public void testFindActDetailInfoAndTemplateConfig(){
		MisRespResult<Map<String,Object>> misResult = misActSettingDubboService.findActDetailInfoAndTemplateConfig(null, "fx_20180419095423", getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				System.out.println(JsonUtil.obj2Str(misResult.getData()));
			}
		}else{
			System.out.println("<-------fail");
		}
	}
	
	@Test
//	@Ignore
	public void testFindActConfigByActivityType(){
		MisRespResult<Map<String,Object>> misResult = misActSettingDubboService.findActConfigByActivityType("rw",getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				System.out.println(JsonUtil.obj2Str(misResult.getData()));
			}
		}else{
			System.out.println("<-------fail");
		}
	}
	
	@Test
	@Ignore
	public void testApprove(){
		SystemUserVO approver = new SystemUserVO();
		approver.setUpdateUser("systemTest");
		MisRespResult<Void> misResult = misActSettingDubboService.approve("3990", approver, getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				System.out.println(JsonUtil.obj2Str(misResult.getData()));
			}
		}else{
			System.out.println("<-------fail");
			if(misResult!=null){
				System.out.println(misResult.getMsg());
			}
		}
	}
	
	@Test
	@Ignore
	public void testCancel(){
		SystemUserVO canceler = new SystemUserVO();
		canceler.setUpdateUser("systemTest");
		MisRespResult<Void> misResult = misActSettingDubboService.cancel("3927", canceler, getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
		}else{
			System.out.println("<-------fail");
		}
	}
	
	public ActSettingDetailsVO findByActivityPeriods(String activityPeriods){
		Long companyId = 1L;
		MisRespResult<ActSettingDetailsVO> misResult = misActSettingDubboService.findByActivityPeriods(activityPeriods,companyId);
		if(misResult.isOk()){
			return misResult.getData();
		}
		return null;
	}
	
	public String getNewActivityPeriods(){
		MisRespResult<String> misResult = misActSettingDubboService.getNewActivityPeriods(getCompanyId());
		if(misResult.isOk()){
			return misResult.getData();
		}
		return null;
	}
 
}
