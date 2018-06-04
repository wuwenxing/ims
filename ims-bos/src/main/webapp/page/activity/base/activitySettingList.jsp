<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/activity/base/activitySetting.js" charset="UTF-8"></script>

</head>
<body>

	<div class="searchTitle">查询条件</div>
	<form id="searchForm" class="searchForm" action="">
		<div>
			<table class="commonTable">
				<tr>
					<th>活动编号</th>
					<td><input type="text" name="activityPeriods" style="width: 380px;"
							   method="getPeriodsList" placeholder="支持模糊查询"
							   class="easyui-combobox" data-options="
	                              loader: comboBoxLoader,
	                              valueField: 'id',
	                              textField: 'name',"/></td>
					<th>活动名称</th>
					<td><input type="text" name="activityName" style="width: 380px;"  placeholder="支持模糊查询"
							   class="easyui-combobox" method="getActivityNameList"
							   data-options="
	                              loader: comboBoxLoader,
	                              valueField: 'id',
	                              textField: 'name',"/></td>
					<th>活动类型</th>
					<td>
					 <select id="activityTypeSearch" name="activityType" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
			        		<option value="">---请选择---</option>
							<ims:dictList parentDictCode="ActivityType"/>
					</select>
				</tr>
				<tr>
					<th>提案号</th>
					<td><input type="text" name="pno" style="width: 380px;"   /></td>
					<th>提案状态</th>
					<td><select id="proposalStatusSearch" name="proposalStatus" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
			        		<option value="">---请选择---</option>
							<ims:dictList parentDictCode="ActivitytProposalStatus"/>
					</select></td>
					<th>活动状态</th>
					<td><select id="enableFlagSearch" name="enableFlag"
						class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							<option value="">---请选择---</option>
							<c:forEach items="${activityStatusList}" var="as">
							   <option value="${as.code}">${as.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					 <td colspan="8">
						<div class="searchButton">
							<a href="javascript:void(0);" id="queryBtn" class="easyui-linkbutton" onclick="activitySetting.query();"
								data-options="iconCls:'icon-search'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="common.reset('searchForm');"
								data-options="iconCls:'icon-empty'">重置</a>
						</div>
					 </td>
				</tr>
			</table>
		</div>
	</form>
	
	<div class="toolbar">
		<a href="javascript:void(0);" class="easyui-linkbutton add" onclick="activitySetting.addDlog();"
			data-options="iconCls:'icon-add', plain:'true'">新增</a>
		<a href="javascript:void(0);" class="easyui-linkbutton edit" onclick="activitySetting.edit();"
			data-options="iconCls:'icon-edit', plain:'true'">修改</a>
		<a href="javascript:void(0);" class="easyui-linkbutton approve" onclick="activitySetting.approve();"
			data-options="iconCls:'icon-redo', plain:'true'">审批通过</a>
		<a href="javascript:void(0);" class="easyui-linkbutton cancel" onclick="activitySetting.canclel();"
			data-options="iconCls:'icon-remove', plain:'true'">取消</a>
	   <a href="javascript:void(0);" class="easyui-linkbutton copy" onclick="activitySetting.copy();"
			data-options="iconCls:'icon-resetPwd', plain:'true'">复制</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="activitySetting.query();" 
			data-options="iconCls:'icon-reload', plain:'true'">刷新</a>
	</div>
	<div class="resultTitle">活动列表</div>
	<table id="dataGrid" data-options="toolbar:'#toolbar'"></table>
	
	<!-- datagrid-操作按钮 -->
	<div id="rowOperation" style="display:none;"></div>
	
	<div id="addDlog" class="easyui-dialog" style="width:450px;height:200px" data-options="closed:'true', buttons:'#addDlogToolbar', modal:true, bgiframe : true">
		<form id="addDlogForm" class="commonForm" method="post">
			<table class="commonTable">
				 <tr>
					 <td align="center">
					    选择活动类型<hr/>
				      <c:forEach items="${ActivityType}" var="t">
				         <c:if test="${t.enableFlag=='Y'}">
							<a class="easyui-linkbutton" data-options="plain:true" onclick="activitySetting.add('${t.dictCode}');">${t.dictName}</a>
							<br/>
				         </c:if>
				      </c:forEach>
					</td>
				</tr>
			</table>
		</form>
	</div>
 
</body>
</html>