$(function() {
	actItemsSetting.init();
});

var actItemsSetting = {
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
		actItemsSetting.loadDataGrid();

		// 1、物品类型change事件
		$("#giftType").combobox({
			onChange : function(newValue, oldValue) {
				if(newValue == 'virtual' || newValue == 'interface'){
					$("#wpzl_1").css("display", "");
					$("#wpzl_2").css("display", "");
					$("#itemCategory").combobox({
						value:"",
						required : true
					});

					$("#wpjg_1").css("display", "none");
					$("#wpjg_2").css("display", "none");
					$("#giftPrice").numberspinner({
						value:"",
						required : false
					});

					$("#wpsl_1").css("display", "");
					$("#wpsl_2").css("display", "");
					$("#giftAmount").numberspinner({
						value:"",
						required : true
					});
					
					$("#giftName").removeAttr("readonly");
					$("#giftName").val($("#companyCode").val());
				}else if(newValue == 'real'){
					$("#wpzl_1").css("display", "none");
					$("#wpzl_2").css("display", "none");
					$("#itemCategory").combobox({
						value:"",
						required : false
					});

					$("#wpjg_1").css("display", "");
					$("#wpjg_2").css("display", "");
					$("#giftPrice").numberspinner({
						value:"",
						required : true
					});

					$("#wpsl_1").css("display", "");
					$("#wpsl_2").css("display", "");
					$("#giftAmount").numberspinner({
						value:"",
						required : true
					});
					
					$("#giftName").removeAttr("readonly");
					$("#giftName").val($("#companyCode").val());
				}else if(newValue == 'analogCoin' || newValue == 'tokenCoin' || newValue == 'withGold'){
					$("#wpzl_1").css("display", "none");
					$("#wpzl_2").css("display", "none");
					$("#itemCategory").combobox({
						value:"",
						required : false
					});

					$("#wpjg_1").css("display", "none");
					$("#wpjg_2").css("display", "none");
					$("#giftPrice").numberspinner({
						value:"",
						required : false
					});

					$("#wpsl_1").css("display", "none");
					$("#wpsl_2").css("display", "none");
					$("#giftAmount").numberspinner({
						value:"",
						required : false
					});
					
					$("#giftName").attr("readonly", "readonly");
					if(newValue == 'analogCoin'){
						$("#giftName").val($("#companyCode").val() + "模拟币");
					}else if(newValue == 'tokenCoin'){
						$("#giftName").val($("#companyCode").val() + "代币");
					}else if(newValue == 'withGold'){
						$("#giftName").val($("#companyCode").val() + "赠金");
					}
				}else{
					$("#wpzl_1").css("display", "");
					$("#wpzl_2").css("display", "");
					$("#itemCategory").combobox({
						value:"",
						required : true
					});

					$("#wpjg_1").css("display", "");
					$("#wpjg_2").css("display", "");
					$("#giftPrice").numberspinner({
						value:"",
						required : true
					});

					$("#wpsl_1").css("display", "");
					$("#wpsl_2").css("display", "");
					$("#giftAmount").numberspinner({
						value:"",
						required : true
					});
					$("#giftName").removeAttr("readonly");
					$("#giftName").val($("#companyCode").val());
				}
				common.setEasyUiCss();
			}
		});

		// 2、物品种类change事件
		$("#itemCategory").combobox({
			onChange : function(newValue, oldValue) {
				// 必须为虚拟物品
				if($("#giftType").val() != 'virtual'){
					$("#itemAmount").validatebox({
						value:"",
						required : false
					});
					$("#itemsUnit").val("");
					$("#div_itemAmount").hide();
					$("#div_itemsUnitEnum_1").hide();
					$("#div_itemsUnitEnum_2").hide();
					$("#div_itemsUnitEnum_3").hide();
					common.setEasyUiCss();
					return;
				}

				$("#itemAmount").validatebox({
					value:"",
					required : true
				});
				$("#div_itemAmount").show();
				if(newValue == 'mobile_data'){
					$("#itemsUnit").val("");
					$("#div_itemsUnitEnum_1").show();
					$("#div_itemsUnitEnum_2").hide();
					$("#div_itemsUnitEnum_3").hide();
				}else if(newValue == 'mobile_charge'){
					$("#itemsUnit").val("");
					$("#div_itemsUnitEnum_1").hide();
					$("#div_itemsUnitEnum_2").show();
					$("#div_itemsUnitEnum_3").hide();
				}else if(newValue == 'member_vip'){
					$("#itemsUnit").val("");
					$("#div_itemsUnitEnum_1").hide();
					$("#div_itemsUnitEnum_2").hide();
					$("#div_itemsUnitEnum_3").show();
				}else{
					$("#itemsUnit").val("");
					$("#div_itemsUnitEnum_1").hide();
					$("#div_itemsUnitEnum_2").hide();
					$("#div_itemsUnitEnum_3").hide();
				}
				common.setEasyUiCss();
			}
		});
		
		
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'ActivityItemsSettingController/pageList';
		var queryParams = {
			
		};
		var columns = [ [
			    {field : 'id', checkbox : true},
	 			{field : 'oper', title : '操作', sortable : false, width : 80, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.id);
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'giftNumber', title : '物品编号', sortable : true, width : 100},
	 			{field : 'giftName', title : '物品名称', sortable : true, width : 100},
	 			{field : 'giftType', title : '物品类型', sortable : true, width : 50},
	 			{field : 'itemCategory', title : '物品种类', sortable : true, width : 50},
	 			{field : 'giftAmount', title : '物品数量', sortable : true, width : 50},
	 			{field : 'giftPrice', title : '物品价格', sortable : true, width : 50},
	 			{field : 'enableFlag', title : '物品状态', sortable : true, width : 50},
	 			{field : 'startDate', title : '物品有效期', sortable : true, width : 150, 
	 				formatter : function(value, rowData, rowIndex) {
	 					return value + "至" + rowData.endDate;
				}}
 			] ];
		$('#' + actItemsSetting.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'id', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				actItemsSetting.addDlog("2", rowData.id);
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + actItemsSetting.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['giftName'] = $("#giftNameSearch").val();
		queryParams['giftType'] = $("#giftTypeSearch").val();
		queryParams['itemCategory'] = $("#itemCategorySearch").val();
		queryParams['enableFlag'] = $("#enableFlagSearch").val();
		queryParams['startDate'] = $("#startDateSearch").val();
		queryParams['endDate'] = $("#endDateSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		actItemsSetting.getQueryParams();
		common.loadGrid(actItemsSetting.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + actItemsSetting.searchFormId).form('reset');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + actItemsSetting.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + actItemsSetting.addDlogOkId).css("display", "");
		$("#" + actItemsSetting.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#id').val("");
			$("#giftNumber").val($("#companyCode").val() + common.getNumByDate(new Date()));
			$("#giftName").val($("#companyCode").val());
			$("#giftType").combobox({disabled: false});
			common.setEasyUiCss();
			$("#" + actItemsSetting.addDlogId).dialog('open').dialog('setTitle', '新增');
		}else{
			var url = BASE_PATH + "ActivityItemsSettingController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"id" : id
				},
				success : function(data) {
					if (null != data) {
						$("#giftType").combobox({disabled: true});
						$("#" + actItemsSetting.addDlogFormId).form('load', data);
						$("#itemCategory").combobox('setValue', data.itemCategory);
						$("#giftAmount").numberspinner('setValue', data.giftAmount);
						$("#giftPrice").numberspinner('setValue', data.giftPrice);
						$("#giftName").val(data.giftName);
						$("#itemsUnitEnum_1").combobox('setValue', data.itemUnit);
						common.setEasyUiCss();
						
						if(operFlag == 1){
							$("#" + actItemsSetting.addDlogOkId).css("display", "");
							$("#" + actItemsSetting.addDlogId).dialog('open').dialog('setTitle', '修改');
						}else if(operFlag == 2){
							$("#" + actItemsSetting.addDlogOkId).css("display", "none");
							$("#" + actItemsSetting.addDlogId).dialog('open').dialog('setTitle', '查看');
						}
						
						// multiselect复选框勾选
						var companyIds = ',' + data.companyIds + ',';// 添加分隔符号，好indexOf进行比较
						$('#companyIds option').each(function(){
							if(companyIds.indexOf(',' + this.value + ',')!=-1)this.selected=true;
						});
						$("select[multiple='multiple']").multiselect("refresh");
						$(".ui-multiselect-menu").css("width", "217px");
						
					}
				},
				error: function(data){
					common.error();
		        }
			});
		}
	},
	/**
	 * 新增提交
	 */
	save: function(){

		if($("#giftType").val() == ''){
			$.messager.alert('提示', "请选择物品类型", 'error');
			return false;
		}
		
		// 物品类型='虚拟物品'&&物品种类
		var giftType = $("#giftType").val();
		var itemCategory = $("#itemCategory").val();
		if(giftType == 'virtual'){
			if(itemCategory == 'mobile_data'){
				$("#itemUnit").val($("#itemsUnitEnum_1").val());
			}else if(itemCategory == 'mobile_charge'){
				$("#itemUnit").val($("#itemsUnitEnum_2").val());
			}else if(itemCategory == 'member_vip'){
				$("#itemUnit").val($("#itemsUnitEnum_3").val());
			}else{
				$("#itemAmount").val("");
				$("#itemsUnit").val("");
			}
			$("#itemAmount").validatebox({
				required : true
			});
		}else{
			$("#itemAmount").val("");
			$("#itemsUnit").val("");
			$("#itemAmount").validatebox({
				required : false
			});
		}
		common.setEasyUiCss();
		
		if(!common.submitFormValidate(actItemsSetting.addDlogFormId)){
			return false;
		}
		
		$.ajax({
			url: BASE_PATH + "ActivityItemsSettingController/save",
			data : $("#" + actItemsSetting.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + actItemsSetting.addDlogId).dialog('close');
					common.reloadGrid(actItemsSetting.dataGridId);
				}
			}
		});
	},
	/**
	 * 单个删除
	 */
	deleteById : function(idArray, tip){
		var url = BASE_PATH + "ActivityItemsSettingController/deleteById";
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
							common.reloadGrid(actItemsSetting.dataGridId);
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
		var checkeds = $("#" + actItemsSetting.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].id;
			}else{
				ids += checkeds[i].id + ",";
			}
		}
		if (ids.length > 0) {
			actItemsSetting.deleteById(ids, "确认删除选择的记录吗?");
		} else {
			$.messager.alert('提示', "请选择要删除的记录", 'info');
		}
	}
}




















