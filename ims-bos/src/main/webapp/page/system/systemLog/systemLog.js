$(function() {
	systemLog.init();
});

var systemLog = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		systemLog.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'SystemLogController/pageList';
		var queryParams = {};
		queryParams['startTime'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endDateSearch").datetimebox('getValue');
		var columns = [ [
		        {field : 'companyId', title : '业务类型', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
						if(!common.isBlank(value)){
							if(value == '0'){
								return "集团";
							}else if(value == '1'){
								return "外汇";
							}else if(value == '2'){
								return "贵金属";
							}else if(value == '3'){
								return "恒信";
							}else if(value == '4'){
								return "创富";
							}
						}
						return value;
				}},
			    {field : 'logType', title : '系统模块', sortable : true, width : 50},
	 			{field : 'method', title : '请求地址', sortable : true, width : 100},
	 			{field : 'params', title : '请求参数', sortable : true, width : 200},
	 			{field : 'userNo', title : '操作人', sortable : true, width : 50},
	 			{field : 'createIp', title : '操作IP', sortable : true, width : 50},
	 			{field : 'createDate', title : '操作时间', sortable : true, width : 50}
 			] ];
		$('#' + systemLog.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'id', // 唯一字段
			sortName : 'id',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + systemLog.dataGridId).datagrid('options').queryParams;
		queryParams['logType'] = $("#logTypeSearch").combobox('getValue');
		queryParams['companyId'] = $("#companyIdSearch").combobox('getValue');
		queryParams['method'] = $("#urlSearch").val();
		queryParams['params'] = $("#paramSearch").val();
		queryParams['createIp'] = $("#updateIpSearch").val();
		queryParams['userNo'] = $("#updateUserSearch").val();
		queryParams['startTime'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endDateSearch").datetimebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		systemLog.getQueryParams();
		common.loadGrid(systemLog.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + systemLog.searchFormId).form('reset');
		$('#startDateSearch').datebox({
			formatter:easyui.formatterYYYYMMDD000000,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		$('#endDateSearch').datebox({
			formatter:easyui.formatterYYYYMMDD235959,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		common.setEasyUiCss();
	}
	
}




















