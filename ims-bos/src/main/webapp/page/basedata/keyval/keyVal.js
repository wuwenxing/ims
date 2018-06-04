$(function() {
	keyVal.init();
});

var keyVal = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	addDlogId: "addDlog",
	addDlogFormId: "addDlogForm",
	addDlogToolbarId: "addDlogToolbar",
	addDlogOkId: "addDlogOk",
	addDlogCancelId: "addDlogCancel",
	/**
	 * 初始化
	 */
	init:function(){
		keyVal.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'KeyValController/pageList';
		var queryParams = {};
		var columns = [ [
 			    {field : 'id', checkbox : true},
	 			{field : 'oper', title : '操作', sortable : false, width : 100, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.id);
					    });
						return $("#rowOperation").html();
				}},
				{title : '键',field : 'dataKey', sortable : true, width : 50},
	            {title : '值',field : 'dataVal', sortable : true, width : 50},
	 			{title : '状态',field : 'enableFlag', sortable : true, width : 50,
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.enableFlag == 'Y'){
							return '启用';
						}else{
							return '禁用';
						}
				}},
	 			{field : 'createUser', title : '更新人', sortable : true, width : 50},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 50}
 			] ];
		$('#' + keyVal.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'id', // 唯一字段
			sortName : 'id',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				keyVal.addDlog("2", rowData.id);
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + keyVal.dataGridId).datagrid('options').queryParams;
		queryParams['dataKey'] = $("#dataKeySearch").val();
		queryParams['dataVal'] = $("#dataValSearch").val();
		queryParams['enableFlag'] = $("#enableFlagSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		keyVal.getQueryParams();
		common.loadGrid(keyVal.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + keyVal.searchFormId).form('reset');
		common.setEasyUiCss();
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + keyVal.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + keyVal.addDlogOkId).css("display", "");
		$("#" + keyVal.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#id').val("");
			$("#" + keyVal.addDlogId).dialog('open').dialog('setTitle', '新增');
		}else{
			var url = BASE_PATH + "KeyValController/findById";
			$.ajax({
				url : url,
				data : {
					"id" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + keyVal.addDlogFormId).form('load', data);
						if(operFlag == 1){
							$("#" + keyVal.addDlogOkId).css("display", "");
							$("#" + keyVal.addDlogId).dialog('open').dialog('setTitle', '修改');
						}else if(operFlag == 2){
							$("#" + keyVal.addDlogOkId).css("display", "none");
							$("#" + keyVal.addDlogId).dialog('open').dialog('setTitle', '查看');
						}
					}
				}
			});
		}
	},
	/**
	 * 新增提交
	 */
	save: function(){
		if(!common.submitFormValidate(keyVal.addDlogFormId)){
			return false;
		}
		$.ajax({
			url: BASE_PATH + "KeyValController/save",
			data : $("#" + keyVal.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + keyVal.addDlogId).dialog('close');
					common.reloadGrid(keyVal.dataGridId);
				}
			}
		});
	},
	/**
	 * 单个删除
	 */
	deleteById : function(idArray, tip){
		var url = BASE_PATH + "KeyValController/deleteById";
		if(null == tip || tip == ''){
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
						if(common.isSuccess(data)){
							common.reloadGrid(keyVal.dataGridId);
						}
					}
				});
			}
		});
	},
	/**
	 * 批量删除
	 */
	deleteBatch : function(){
		var checkeds = $("#" + keyVal.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].id;
			}else{
				ids += checkeds[i].id + ",";
			}
		}
		if (ids.length > 0) {
			keyVal.deleteById(ids, "确认删除选择的记录吗?");
		} else {
			$.messager.alert('提示', "请选择要删除的记录", 'info');
		}
	}
	
}




















