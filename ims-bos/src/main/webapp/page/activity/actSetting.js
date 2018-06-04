$(function() {
	actSetting.init();
});

var actSetting = {
	dataGridId : "dataGrid",
	rowOperationId : "rowOperation",
	searchFormId : "searchForm",
	addDlogId : "addDlog",
	addDlogFormId : "addDlogForm",
	addDlogToolbarId : "addDlogToolbar",
	addDlogOkId : "addDlogOk",
	addDlogCancelId : "addDlogCancel",
	/**
	 * 初始化
	 */
	init : function() {
		actSetting.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function() {
		// 初始参数
		var url = BASE_PATH + 'ActivitySettingController/pageList';
		var queryParams = {

		};
		var columns = [ [
		    {field : 'id', checkbox : true},
 			{field : 'activityType', hidden: true},
 			{field : 'pno', title : '提案号', sortable : true, width : 120},
 			{field : 'activityPeriods', title : '活动编号', sortable : true, width : 150,
				formatter : function(value, rowData, rowIndex) {
					return "<a href='javascript:void(0);' onclick='actSetting.view(\""
							+ rowData.id + "\")'>" + value + "</a>";
				}},
 			{field : 'activityName', title : '活动名称', sortable : true, width : 150},
 			{field : 'activityTypeText', title : '活动类型', sortable : true, width : 60},
 			{field : 'startTime', title : '活动开始时间', sortable : true, width : 100},
 			{field : 'endTime', title : '活动结束时间', sortable : true, width : 100},
 			{field : 'enableFlag', title : '活动状态', sortable : true, width : 60},
 			{field : 'proposalStatus', title : '提案状态', sortable : true, width : 60},
 			{field : 'approver', title : '审批人', sortable : true, width : 50},
 			{field : 'approveDate', title : '审批时间', sortable : true, width : 100},
 			{field : 'updateUser', title : '更新人', sortable : true, width : 50}
		] ];
		$('#' + actSetting.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'id', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams : function() {
		var options = $("#" + actSetting.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['activityPeriods'] = $("#activityPeriodsSearch").val();
		queryParams['activityName'] = $("#activityNameSearch").val();
		queryParams['activityType'] = $("#activityTypeSearch").val();
		queryParams['pno'] = $("#pnoSearch").val();
		queryParams['proposalStatus'] = $("#proposalStatusSearch").val();
		queryParams['enableFlag'] = $("#enableFlagSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find : function() {
		actSetting.getQueryParams();
		common.loadGrid(actSetting.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset : function() {
		$('#' + actSetting.searchFormId).form('reset');
	},
	/**
	 * close新增窗口
	 */
	cancel : function() {
		$('#' + actSetting.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看 operFlag 0新增，1修改，2查看 id 修改或查看实体的id
	 */
	addDlog : function(operFlag, id) {
		$("#" + actSetting.addDlogFormId).form('reset');
		if (operFlag == 0) {
			$("#" + actSetting.addDlogId).dialog('open').dialog('setTitle',
					'新增');
		}
	},
	/**
	 * 功能：新增活动提案
	 */
	add : function(actType) {
		$("#" + actSetting.addDlogId).dialog("close");
		parent.index.addOrUpdateTabs('新增活动提案',
				'ActivitySettingController/add/' + actType);
	},
	/**
	 * 新增提交
	 */
	save : function() {

	},
	/**
	 * 功能：修改
	 */
	edit : function() {
		var checkeds = $("#" + actSetting.dataGridId).datagrid(
				'getChecked');
		var len = checkeds.length;
		if (len == 1) {
			var id = checkeds[0].id;
			if (id > 0) {
				parent.index.addOrUpdateTabs('修改活动提案', 'ActivitySettingController/edit/' + id);
			}
		} else {
			$.messager.alert("操作提示", "请选择一条记录进行操作!", 'info');
		}
	},
	/**
	 * 查看
	 */
	view : function(id) {
		parent.index.addOrUpdateTabs('查看活动提案', 'ActivitySettingController/view/' + id);
	},
	/**
	 * 复制
	 */
	copy : function(id) {
		var checkeds = $("#" + actSetting.dataGridId).datagrid(
				'getChecked');
		var len = checkeds.length;
		if (len == 1) {
			var id = checkeds[0].id;
			if (id > 0) {
				parent.index.addOrUpdateTabs('新增活动提案', 'ActivitySettingController/copy/' + id);
			}
		} else {
			$.messager.alert("操作提示", "请选择一条记录进行操作!", 'info');
		}
	},
	/**
	 * 单个删除
	 */
	deleteById : function(idArray, tip) {
		var url = BASE_PATH + "ActivitySettingController/deleteById";
		if (null == tip || tip == '') {
			tip = '确认删除该行记录吗?'
		}
		$.messager.confirm('确认', tip, function(r) {
			if (r) {
				$.ajax({
					url : url,
					data : {
						"idArray" : idArray + ""
					},
					success : function(data) {
						if (common.isSuccess(data)) {
							common.reloadGrid(actSetting.dataGridId);
						}
					}
				});
			}
		});
	},
	/**
	 * 批量删除
	 */
	deleteBatch : function() {
		var checkeds = $("#" + actSetting.dataGridId).datagrid('getChecked');
		var ids = "";
		for (var i = 0; i < checkeds.length; i++) {
			if (i == checkeds.length - 1) {
				ids += checkeds[i].id;
			} else {
				ids += checkeds[i].id + ",";
			}
		}
		if (ids.length > 0) {
			actSetting.deleteById(ids, "确认删除选择的记录吗?");
		} else {
			$.messager.alert('提示', "请选择要删除的记录", 'info');
		}
	}
}
