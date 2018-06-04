package com.gwghk.ims.mis.gateway.enums;

/**
 * 
 * 摘要：通用模板文件下载
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月13日
 */
public enum DownTemplateEnum {
	TplImsPrizeRecordBatchSave("批量保存物品发放记录模板", "prize_record_tpl", "/template/prize_record_batch_save.xlsx"),
	TplActBlackWhiteListBatchSave("批量保存黑白名单模板", "black_white_tpl", "/template/black_white_batch_save_template.xlsx"),
	TplActStringCodeListBatchSave("批量上传串码模板", "act_stringCode_tpl", "/template/act_stringCode_add_template.xlsx"),
	TplUserBatchSave("批量上传用户模板", "user_tpl", "/template/user_add_template.xlsx"),
	TplImsRechargeListBatchSave("批量上传流量话费模板", "recharge_add_tpl", "/template/imsRecharge_add_template.xlsx"),
	SystemUserInfo("系统用户列表", "UserInfo", "/template/SystemUserInfo.xlsx"),
	BlackListUserInfo("黑名单用户列表", "BlackListUserInfo", "/template/list_white_black.xlsx"),
	WhiteListUserInfo("白名单用户列表", "WhiteListUserInfo", "/template/list_white_black.xlsx"),
	ActPrizeRecordList("物品发放记录列表", "_imsPrizeRecord", "/template/imsPrizeRecord.xlsx"),
	ActDemoPrizeRecordList("Demo物品发放记录列表", "_actDemoItemsRecordList", "/template/actDemoItemsRecordList.xlsx"),
	ActDemoWeekIncomeList("Demo收益排行榜", "_actDemoWeekIncomeList", "/template/actDemoWeekincomeList.xlsx"),
	ActAccountStatList("活动参与用户列表", "_actAccountStatRecordList", "/template/actAccountStatRecordList.xlsx"),
	ActTempPrizeRecordList("常规活动物品发放记录列表", "_actTempItemsRecordList", "/template/actTempItemsRecordList.xlsx"),
	ActTempPrizeRecordCountList("常规活动物品发放记录列表-根据用户统计", "_actTempPrizeRecordCountList", "/template/actTempPrizeRecordCountList.xlsx"),
	ActAccountActivityList("用户参与的活动列表", "_actAccountActivityList", "/template/actAccountActivityList.xlsx"),
	ActJoinQualifyList("活动参与资格列表","_actJoinQualifyList","/template/actJoinQualifyList.xlsx"),
	actTempVotingRecordList("投票明细列表","actTempVotingRecordList","/template/actTempVotingRecordList.xlsx"),
	actTempVotingCountList("投票统计列表","actTempVotingCountList","/template/actTempVotingCountList.xlsx"),
	actStringCodeList("串码列表", "_actStringCodeList", "/template/actStringCodeList.xlsx"),
	ImsPrizeRecordList("物品发放记录列表", "_imsPrizeRecord", "/template/imsPrizeRecord.xlsx"),
	smsLogDetail("短信发送记录","smsLogDetail","/template/smsLogDetail.xlsx"),
	ImsPrizeRecordBatchSave("物品发放记录批量保存", "_imsPrizeRecordBatchSave", "/template/imsPrizeRecordBatchSave.xlsx"),
	gwBlackList("全局黑名单", "_gwBlackList", "/template/gw_black_template.xlsx"),
	rechargeDetail("充值记录", "rechargeDetail", "/template/rechargeDetail.xlsx"),
	;
	
	private final String value;//模板名称
	private final String key;//模板唯一标示
	private final String path;//模板路径
	
	DownTemplateEnum(String _operator, String key, String path) {
		this.value = _operator;
		this.key = key;
		this.path = path;
	}
	
	public String getValue() {
		return value;
	}

	public String getKey() {
		return key;
	}

	public String getPath() {
		return path;
	}
	
	public static DownTemplateEnum getTpl(String key){
		for(DownTemplateEnum ae : DownTemplateEnum.values()){
			if(ae.getKey().equals(key)){
				return ae ;
			}
		}
		return null;
	}
}
