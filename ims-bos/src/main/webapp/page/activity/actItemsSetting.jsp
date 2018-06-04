<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/activity/actItemsSetting.js?version=20170306"></script>
</head>
<body>
<input type="hidden" id="companyCode" name="companyCode" value="${companyCode}"/>
<div class="searchTitle">查询条件</div>
<form id="searchForm" class="searchForm" action="">
	<table class="commonTable">
		<tr>
			<th>物品名称</th>
			<td>
				<input type="text" id="giftNameSearch" name="giftNameSearch" maxlength="50" />
			</td>
			<th>物品类型</th>
			<td>
				<select id="giftTypeSearch" name="giftTypeSearch" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
	        		<option value="">---请选择---</option>
	        		<ims:dictList parentDictCode="ActItemsType" />
				</select>
			</td>
			<th>物品种类</th>
			<td>
				<select id="itemCategorySearch" name="itemCategorySearch" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
	        		<option value="">---请选择---</option>
	        		<ims:dictList parentDictCode="ActItemCategory" />
				</select>
			</td>
		</tr>
		<tr>
			<th>物品状态</th>
			<td>
				<select id="enableFlagSearch" name="enableFlagSearch" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
					<option value="">---请选择---</option>
					<ims:enumList dataList="${enableFlagEnum}"/>
				</select>
			</td>
			<th>物品有效期</th>
			<td colspan="3">
				<input class="easyui-datetimebox" type="text" name="startDateSearch" id="startDateSearch" data-options="editable:false" value="" />
				至
				<input class="easyui-datetimebox" type="text" name="endDateSearch" id="endDateSearch" data-options="editable:false" value="" />
			</td>
		</tr>
			<tr>
				<td colspan="8">
					<div class="searchButton">
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="actItemsSetting.find();"
							data-options="iconCls:'icon-search', plain:'true'">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="actItemsSetting.reset();"
							data-options="iconCls:'icon-empty', plain:'true'">重置</a>
					</div>
				</td>
			</tr>
	</table>
</form>

<div id="toolbar">
	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="actItemsSetting.addDlog(0, 0);"
		data-options="iconCls:'icon-add', plain:'true'">新增</a>
	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="actItemsSetting.deleteBatch();"
		data-options="iconCls:'icon-remove', plain:'true'">删除</a>
</div>
<div class="resultTitle">数据列表</div>
<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="actItemsSetting.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="actItemsSetting.deleteById(this.id)">删除</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="actItemsSetting.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="actItemsSetting.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', modal:true, bgiframe:true, resizable:true, height:280, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="id" id="id">
		<table class="commonTable">
			<tr>
				<th id="wplx">物品类型<span class="spanRed">*</span></th>
				<td colspan="5">
					<select id="giftType" name="giftType" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
	        			<option value="">---请选择---</option>
	        			<ims:dictList parentDictCode="ActItemsType" />
					</select>
				</td>
			</tr>
			<tr>
				<th>物品编号<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" id="giftNumber" name="giftNumber" data-options="required:true, validType:'length[0,50]'" /></td>
				<th>物品名称<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" id="giftName" name="giftName" data-options="required:true, validType:'length[0,50]'" /></td>
				<th>物品状态<span class="spanRed">*</span></th>
				<td>
					<select id="enableFlag" name="enableFlag" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
						<ims:enumList dataList="${enableFlagEnum}"/>
					</select>
				</td>
			</tr>
			<tr>
				<th id="wpzl_1" style="display: none;">物品种类<span class="spanRed">*</span></th>
				<td id="wpzl_2" style="width: 300px; display: none;">
					<div style="float: left;">
						<select id="itemCategory" name="itemCategory" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false" style="width: 100px;">
	        				<ims:dictList parentDictCode="ActItemCategory" />
						</select>
					</div>
					<!-- 该种类的对应的物品数量 -->
					<div id="div_itemAmount" style="display: none; float: left;">
						<input class="easyui-validatebox" type="text" id="itemAmount" name="itemAmount" data-options="required:false, validType:'length[0,50]'" style="width: 50px;" />
					</div>
					<!-- 物品单位 -->
					<input type="hidden" id="itemUnit" name="itemUnit" value="" />
					<div id="div_itemsUnitEnum_1" style="display: none; float: left;">
						<select id="itemsUnitEnum_1" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false" style="width: 50px;">
							<ims:enumList dataList="${itemsUnitEnum_1}"/>
						</select>
					</div>
					<div id="div_itemsUnitEnum_2" style="display: none; float: left;">
						<input type="hidden" id="itemsUnitEnum_2" value="${itemsUnitEnum_2.labelKey}" />${itemsUnitEnum_2.value}
					</div>
					<div id="div_itemsUnitEnum_3" style="display: none; float: left;">
						<input type="hidden" id="itemsUnitEnum_3" value="${itemsUnitEnum_3.labelKey}" />${itemsUnitEnum_3.value}
					</div>
					
				</td>
				<th id="wpsl_1">物品数量<span class="spanRed">*</span></th>
				<td id="wpsl_2">
					<input class="easyui-numberspinner" type="text" id="giftAmount" name="giftAmount" data-options="required:true, precision:0" value=""/>
				</td>
				<th id="wpjg_1">物品价格<span class="spanRed">*</span></th>
				<td id="wpjg_2">
					<input class="easyui-numberspinner" type="text" id="giftPrice" name="giftPrice" data-options="required:true, precision:0" value=""/>
				</td>
			</tr>
			<tr>
				<th>物品有效期</th>
				<td colspan="5">
					<input class="easyui-datetimebox" type="text" id="startDate" name="startDate" data-options="required:true, editable:false" value="" />
					至
					<input class="easyui-datetimebox" type="text" id="endDate" name="endDate" data-options="required:true, editable:false" value="" />
				</td>
			</tr>
		</table>
	</form>
</div>




</body>
</html>
