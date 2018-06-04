<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/activity/actSetting.js?version=20170306"></script>
</head>
<body>
<div class="searchTitle">查询条件</div>
<form id="searchForm" class="searchForm" action="">
	<table class="commonTable">
		<tr>
			<th>活动编号</th>
			<td>
				<input type="text" id="activityPeriodsSearch" name="activityPeriodsSearch" maxlength="50" />
			</td>
			<th>活动名称</th>
			<td>
				<input type="text" id="activityNameSearch" name="activityNameSearch" maxlength="50" />
			</td>
			<th>活动类型</th>
			<td>
				<select id="activityTypeSearch" name="activityTypeSearch" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
	        		<option value="">---请选择---</option>
	        		<ims:dictList parentDictCode="ActivityType"/>
				</select>
			</td>
		</tr>
		<tr>
			<th>提案号</th>
			<td>
				<input type="text" id="pnoSearch" name="pnoSearch" maxlength="50" />
			</td>
			<th>提案状态</th>
			<td>
				<select id="proposalStatusSearch" name="proposalStatusSearch" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
					<option value="">---请选择---</option>
	        		<ims:dictList parentDictCode="AuditStatus" />
				</select>
			</td>
			<th>活动状态</th>
			<td>
				<select id="enableFlagSearch" name="enableFlagSearch" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
					<option value="">---请选择---</option>
					<ims:enumList dataList="${activityStatusEnum}"/>
				</select>
			</td>
		</tr>
			<tr>
				<td colspan="8">
					<div class="searchButton">
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="actSetting.find();"
							data-options="iconCls:'icon-search', plain:'true'">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="actSetting.reset();"
							data-options="iconCls:'icon-empty', plain:'true'">重置</a>
					</div>
				</td>
			</tr>
	</table>
</form>

<div id="toolbar">
	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="actSetting.addDlog(0, 0);"
		data-options="iconCls:'icon-add', plain:'true'">新增</a>
   <a href="javascript:void(0);" class="easyui-linkbutton" onclick="actSetting.copy();"
		data-options="iconCls:'icon-add', plain:'true'">复制</a>
	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="actSetting.edit();"
		data-options="iconCls:'icon-edit', plain:'true'">修改</a>
	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="actSetting.remove();"
		data-options="iconCls:'icon-remove', plain:'true'">取消</a>
</div>
<div class="resultTitle">数据列表</div>
<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="actSetting.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', modal:true, bgiframe:true, resizable:true, height:280, width:400, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<table class="commonTable">
			<tr>
				<td align="center">选择活动类型
					<hr /> 
					<c:forEach items="${activityType}" var="t">
						<c:if test="${t.enableFlag=='Y'}">
							<a class="easyui-linkbutton" data-options="plain:true"
								onclick="actSetting.add('${t.dictCode}');">${t.dictName}</a>
							<br />
						</c:if>
					</c:forEach>
				</td>
			</tr>
		</table>
	</form>
</div>


</body>
</html>
