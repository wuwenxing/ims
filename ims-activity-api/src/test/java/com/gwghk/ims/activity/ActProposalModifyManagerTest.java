package com.gwghk.ims.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActTaskTypeEnum;
import com.gwghk.ims.common.enums.ActUnitEnum;
import com.gwghk.ims.common.enums.FrequencyTimeUnitEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActProposalModifyDubboService;
import com.gwghk.ims.common.inf.mis.activity.MisActSettingDubboService;
import com.gwghk.ims.common.vo.activity.ActProposalModifyVO;
import com.gwghk.ims.common.vo.activity.ActSettingDetailsVO;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：后台-活动任务测试用例
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月13日
 */
public class ActProposalModifyManagerTest extends BaseTest{

	@Autowired
	private MisActProposalModifyDubboService misActProposalModifyDubboService;
	
	@Autowired
	private MisActSettingDubboService misActSettingDubboService;
	
	private Long getCompanyId(){
		return 1L;
	}
	
	  
	@Test
	@Ignore
	public void testFindPageList(){
		System.out.println("------->findPageList");
		ActProposalModifyVO actProposalModifyVO = new ActProposalModifyVO();
		MisRespResult<PageR<ActProposalModifyVO>> misResult = misActProposalModifyDubboService.findPageList(actProposalModifyVO, getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				List<ActProposalModifyVO> list = misResult.getData().getList();
				System.err.println(list.size());
				System.out.println(JsonUtil.obj2Str(list));
			}
		}else{
			System.out.println("<-------fail");
		}
	}
	
	@Test
	@Ignore
	public void testGetActProposalModifyByPno(){
		System.out.println("------->testGetActProposalModifyByPno");
		MisRespResult<Map<String,Object>> misResult = misActProposalModifyDubboService.getActProposalModifyByPno("A1804170396B05296386", getCompanyId());
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
	public void testUpdateApproveModifyProposal(){
		System.out.println("------->testUpdateApproveModifyProposal");
		MisRespResult<Void> misResult = misActProposalModifyDubboService.updateApproveModifyProposal("A1804170374B05282695", false,null,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
		}else{
			System.out.println("<-------fail");
		}
	}
	
	@Test
	@Ignore
	public void testCreateModifyProposal(){
		String activityPeriods = "FX_1523934454149";
		ActSettingDetailsVO actSetting = findByActivityPeriods(activityPeriods);
		if(actSetting!=null){
			//活动基本信息
//			ActSettingVO baseInfo = actSetting.getActBaseInfo();
//			baseInfo.setFinishDays(22);//完成天数 0-->22
//			baseInfo.setCoinDays(23);//代币有效期 null->23
//			baseInfo.setTurnGroup(1);//允许 0->1
//			baseInfo.setWithGoldDays(null);// 1>null
//			
//			ActConditionSettingVO actCondSetting = actSetting.getActCondSetting();
//			actCondSetting.setAccountStatus(ActActivateStatusEnum.ACTIVATED.getCode());//null-->activated
//			actCondSetting.setActivateStartTime(ImsDateUtil.addDay(new Date(), 1));//null-->今天+1
//			actCondSetting.setActivateEndTime(null);//1524042305436--> null
//			actCondSetting.setAllowCancelAccount(1);//0--->1
//			actCondSetting.setPlatformsCcy("GTS2#CNY,MT4#CNY");//MT4#USDGTS2#USD,MT4#USD, --->GTS2#CNY,MT4#CNY
//			actCondSetting.setPlatforms("GTS2,MT4");
//			actCondSetting.setCcy("CNY");
//		 
			List<ActTaskSettingVO> taskList = actSetting.getActTaskSettings();
//			//修改物品
//			taskList.get(0).getTaskItems().get(0).setFrequencyNum(10);//FrequencyNum 2-->10
//			taskList.get(0).getTaskItems().get(0).setFrequencyTime(null);//FrequencyTime 1-->null
			
			//添加任务
			ActTaskSettingVO task1 = getTempActTaskSettingVO(activityPeriods,ActTaskTypeEnum.REAL_CLOSE_TIMES.getCode(),"1",ActUnitEnum.TIMES.getCode(),null,null,null,null,true,true);
			taskList.add(task1);
			actSetting.setActTaskSettings(taskList);
			misActSettingDubboService.saveOrUpdate(actSetting, this.getCompanyId());
			
		}
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
		vo.setCompanyId(this.getCompanyId());
		if(addItems){
			List<ActTaskItemsVO> taskItems = new ArrayList<ActTaskItemsVO>();
			taskItems.add(getTempVirtualActTaskItems(cycleTask));
			taskItems.add(getTempGoldActTaskItems(cycleTask));
		 
			 vo.setTaskItems(taskItems);
		}
		return vo;
	}
	
	private ActTaskItemsVO getTempVirtualActTaskItems(boolean cycleTask){
		//virtual
		ActTaskItemsVO vo = new ActTaskItemsVO();
		vo.setItemNumber("eva_virtual_13182344");
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
		vo.setItemNumber("eva_withGold_13182040");
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
	
	public ActSettingDetailsVO findByActivityPeriods(String activityPeriods){
		Long companyId = 1L;
		MisRespResult<ActSettingDetailsVO> misResult = misActSettingDubboService.findByActivityPeriods(activityPeriods,companyId);
		if(misResult.isOk()){
			return misResult.getData();
		}
		return null;
	}
	
}
