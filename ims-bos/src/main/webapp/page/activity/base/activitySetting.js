/**
 * 摘要：活动基础设置-主页面---
 * 
 * @author eva
 * @version 1.0
 * @Date 2017年5月18日
 */
var activitySetting = {
	dataGridId : 'dataGrid',
	addDlogId : 'addDlog',
	init : function() {
		$("#enableFlagSearch").combobox("setValue", "Y");// 默认搜索启用的
		$("#proposalStatusSearch").combobox("setValue", "ActWaitForApprove");// 默认搜索待审批的
		this.loadDataGrid();
		this.setEvent();
	},

	/**
	 * 功能：加载dataGrid
	 */
	loadDataGrid : function() {
		// 初始参数
		var url = BASE_PATH + 'activitySettingController/pageList';
		var columns = [ [
				{
					title : 'id',
					field : 'id',
					checkbox : true
				},
				{
					title : '提案号',
					field : 'pno',
					width : 80
				},
				{
					title : '活动编号',
					field : 'activityPeriods',
					width : 100,
					sortable : true,
					formatter : function(value, rowData, rowIndex) {
						return "<a  href='javascript:void(0);'  onclick='funcCommon.viewActivity(\""
								+ rowData.activityPeriods
								+ "\")'>"
								+ value
								+ "</a>";
					}
				},
				{
					title : '活动名称',
					field : 'activityName',
					width : 140,
					sortable : true,
					formatter : function(value, rowData, rowIndex) {
						return "<a  href='javascript:void(0);'  onclick='funcCommon.viewActivity(\""
								+ rowData.activityPeriods
								+ "\")'>"
								+ value
								+ "</a>";
					}
				}, {
					title : '活动类型',
					field : 'activityTypeStr',
					width : 80
				}, {
					title : '活动开始时间',
					field : 'startTime',
					width : 120,
					sortable : true
				}, {
					title : '活动结束时间',
					field : 'endTime',
					width : 120,
					sortable : true
				}, {
					title : '活动状态',
					field : 'enableFlag',
					width : 50
				}, {
					title : '提案状态',
					field : 'proposalStatus',
					width : 60,
					sortable : true
				}, {
					title : '审批人',
					field : 'approver',
					width : 80,
					formatter : function(value, rowData, rowIndex) {
						if (common.isBlank(value)) {
							return rowData.canceller;
						}
						return value;
					}
				}, {
					title : '审批时间',
					field : 'approveDate',
					width : 110,
					sortable : true,
					formatter : function(value, rowData, rowIndex) {
						if (common.isBlank(value)) {
							return rowData.cancelDate;
						}
						return value;
					}
				}, {
					title : '创建人',
					field : 'createUser',
					width : 80
				},

		] ];
		common.dataGrid({
			gridId : activitySetting.dataGridId,
			url : url,
			columns : columns,
			idField : 'id', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			queryParams : {
				enableFlag : $("#enableFlagSearch").combobox("getValue"),
				proposalStatus : $("#proposalStatusSearch")
						.combobox("getValue")
			},
		});
	},
	getQueryParams : function() {
		var queryParams = $("#" + activitySetting.dataGridId).datagrid(
				'options').queryParams;
		$('#searchForm input[name]').each(function() {
			queryParams[this.name] = $(this).val();
		})
		return queryParams;
	},
	/**
	 * 功能：条件查询
	 */
	query : function() {
		activitySetting.getQueryParams();
		common.loadGrid(activitySetting.dataGridId);
	},

	/**
	 * 功能：新增
	 */
	add : function(actType) {
		// 新增活动提案
		$("#" + activitySetting.addDlogId).dialog("close");
		common.closeTab($.i18n.prop("activity.add.proposal")); // 关闭主页面
		common.addNewTab($.i18n.prop("activity.add.proposal"),
				'/activitySettingController/' + actType + '/add');
	},

	/**
	 * 功能：新增活动类型
	 */
	addDlog : function() {
		$("#" + activitySetting.addDlogId).dialog({
			title : '新增活动',
			iconCls : 'icon-add',
			cache : false
		}).dialog('open');
	},

	/**
	 * 功能：修改
	 * 
	 * id 修改或查看实体的id
	 */
	edit : function() {
		var checkeds = $("#" + activitySetting.dataGridId).datagrid(
				'getChecked');
		var len = checkeds.length;
		if (len == 1) {
			var selId = checkeds[0].id;
			if (selId > 0) {
				var activityType = checkeds[0].activityType;
				// 修改活动提案
				common.closeTab($.i18n.prop("activity.edit.proposal")); // 关闭主页面
				common.addNewTab($.i18n.prop("activity.edit.proposal"),
						'/activitySettingController/' + activityType
								+ '/edit?id=' + selId + "&mainMenu=proposal");
			}
		} else {
			$.messager.alert("操作提示", "请选择一条记录进行操作!", 'info');
		}
	},

	getSelect : function() {
		var checkeds = $("#" + activitySetting.dataGridId).datagrid(
				'getChecked');
		var len = checkeds.length;
		if (len > 0) {
			var ids = "";
			for (var i = 0; i < len; i++) {
				if (i == len - 1) {
					ids += checkeds[i].id;
				} else {
					ids += checkeds[i].id + ",";
				}
			}
			return ids;
		} else {
			$.messager.alert("操作提示", "请选择一条记录进行操作!", 'info');
		}
	},
	/**
	 * 取消
	 */
	canclel : function() {
		var selId = activitySetting.getSelect();
		if (common.isValid(selId)) {
			var url = BASE_PATH + "activitySettingController/canclel";
			$.messager.confirm('确认', "确认需要取消的记录吗?", function(r) {
				if (r) {
					common.openProgress();
					common.ajax({
						url : url,
						data : {
							"ids" : selId
						},
						success : function(data) {
							common.closeProgress();
							if (common.isSuccess(data)) {
								common.loadGrid(activitySetting.dataGridId);
							}
						},
						error : function(data) {
							common.closeProgress();
							common.error();
						}
					});
				}
			});
		}
	},
	/**
	 * 审批
	 */
	approve : function() {
		var selId = activitySetting.getSelect();
		if (common.isValid(selId)) {
			// 确认删除选择的记录吗?
			var url = BASE_PATH + "activitySettingController/approve";
			$.messager.confirm('确认', "确认要审批通过此记录吗?", function(r) {
				if (r) {
					common.openProgress();
					common.ajax({
						url : url,
						data : {
							"ids" : selId
						},
						success : function(data) {
							common.closeProgress();
							if (common.isSuccess(data)) {
								common.reloadGrid(activitySetting.dataGridId);
							}
						},
						error : function(data) {
							common.closeProgress();
							common.error();
						}
					});
				}
			});
		}
	},
	/**
	 * 功能：复制活动
	 */
	copy : function() {
		var checkeds = $("#" + activitySetting.dataGridId).datagrid(
				'getChecked');
		var len = checkeds.length;
		if (len == 1) {
			var selId = checkeds[0].id;
			var startDate = new Date(checkeds[0].startTime.replace("-", "/"));
			var endDate = new Date(checkeds[0].endTime.replace("-", "/"));
			var curDate = new Date().getTime();
			if (curDate > endDate.getTime()
					&& checkeds[0].proposalStatus == '已审批') {
				if (selId > 0) {
					var activityType = checkeds[0].activityType;
					// 修改活动提案
					common.closeTab('复制活动信息'); // 关闭主页面
					common.addNewTab('复制活动信息', '/activitySettingController/'
							+ activityType + '/copyById?id=' + selId);
				}
			} else {
				$.messager.alert("操作提示", "操作失败，状态不匹配！只能对已审批通过且已失效的活动进行复制!",
						'warning');
			}
		} else {
			$.messager.alert("操作提示", "请选择一条记录进行操作!", 'info');
		}
	},

	/**
	 * 功能：设置事件
	 */
	setEvent : function() {
		$('#queryBtn').focus();
		$(document).keyup(function(e) {
			var curKey = e.which;
			if (curKey == 13) {
				activitySetting.query();
			}
		});
	}
};

// 初始化
$(function() {
	activitySetting.init();
});

var comboBoxLoader = function(param, success, error) {
	var q = param.q || '';
	// 访问后台的方法
	var method = $(this).attr("method");
	$.ajax({
		url : BASE_PATH + "activitySettingController/" + method,
		data : {
			queryParam : q,
			activityTypeStr : 'qt,rw,wpdh'
		},
		success : function(data) {

			var items = $.map(data, function(item, index) {
				return {
					id : item,
					name : item
				};
			});
			success(items);

		},
		error : function() {
			error.apply(this, arguments);
		}
	});

};
