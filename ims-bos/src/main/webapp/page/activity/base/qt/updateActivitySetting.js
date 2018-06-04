/**
 * 摘要：活动基础设置-添加主页面--其他活动
 * 
 * @author misa
 * @version 1.0
 * @Date 2017年6月14日
 */

// 初始化
$(function() {
	updateActivitySetting.init();
});

var updateActivitySetting = {
	addDlogFormId : 'addDlogForm',
	init : function() {
		easyui.combineDateTimeBox("startTimeStr", "endTimeStr");
		easyui.combineStartDateTimeBox("startTimeStr");
		easyui.combineStartDateTimeBox("endTimeStr");
		// 当开始时间/结束时间发生变更的时候触发验证
		$('#startTimeStr').datetimebox({
			onChange : function(date) {
				$('#endTimeStr').datetimebox("validate");
			}
		});
		$('#endTimeStr').datetimebox({
			onChange : function(date) {
				$('#startTimeStr').datetimebox("validate");
			}
		});
		common.setEasyUiCss();
	},
	/**
	 * 功能：保存-新增
	 */
	save : function() {
		// 验证：活动基础设置与参与活动条件
		if (!common.submitFormValidate(updateActivitySetting.addDlogFormId)) {
			return false;
		}
		var url = BASE_PATH + "ActivitySettingController/save/qt";
		$.ajax({
			url : url,
			data : $("#" + updateActivitySetting.addDlogFormId).serialize(),
			success : function(data) {
				if (common.isSuccess(data)) {
					parent.index.closeOrUpdateTabs('活动提案');
				}
			}
		});
	},
	/**
	 * 功能：返回到主列表页面
	 */
	cancel : function() {
		parent.index.closeTabs();
	},
};
