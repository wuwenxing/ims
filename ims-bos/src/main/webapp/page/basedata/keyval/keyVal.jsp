<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/basedata/keyval/keyVal.js?version=20170306"></script>
</head>
<body>

<div class="searchTitle">查询条件</div>
<form id="searchForm" class="searchForm" action="">
	<div>
		<table class="commonTable">
			<tr>
				<th>键</th>
				<td>
					<input type="text" id="dataKeySearch" name="dataKeySearch" maxlength="50"/>
				</td>
				<th>值</th>
				<td>
					<input type="text" id="dataValSearch" name="dataValSearch" maxlength="50"/>
				</td>
				<th>状态</th>
				<td>
					<select id="enableFlagSearch" name="enableFlagSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
						<option value="">---请选择---</option>
						<ims:enumList dataList="${enableFlagEnum}"/>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="8">
					<div class="searchButton">
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="keyVal.find();"
							data-options="iconCls:'icon-search', plain:'true'">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="keyVal.reset();"
							data-options="iconCls:'icon-empty', plain:'true'">重置</a>
					</div>
				</td>
			</tr>
		</table>
	</div>
</form>

<div id="toolbar">
	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="keyVal.addDlog(0, 0);"
		data-options="iconCls:'icon-add', plain:'true'">新增</a>
	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="keyVal.deleteBatch();"
		data-options="iconCls:'icon-remove', plain:'true'">删除</a>
</div>
<div class="resultTitle">数据列表</div>
<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="keyVal.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="keyVal.deleteById(this.id)">删除</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="keyVal.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="keyVal.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="id" id="id">
		<table class="commonTable">
			<tr>
				<th>键<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="dataKey" id="dataKey" data-options="required:true, validType:'length[0,50]'" /></td>
			</tr>
			<tr>
				<th>值<span class="spanRed">*</span></th>
				<td>
					<textarea name="dataVal"
					 class="textarea easyui-validatebox"
					 data-options="required:true, validType:'length[0,100]'"
					 style="width: 400px; height: 100px;"></textarea>
				</td>
			</tr>
			<tr>
				<th>状态<span class="spanRed">*</span></th>
				<td>
					<select id="enableFlag" name="enableFlag" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
						<ims:enumList dataList="${enableFlagEnum}"/>
					</select>
				</td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>
