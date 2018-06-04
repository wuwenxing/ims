<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/common.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/page/activity/base/qt/updateActivitySetting.js"
	charset="UTF-8"></script>
</head>
<body>
	<div class="easyui-tabs" id="addActSetting"
		data-options="tools:'#tab-tools-open-onestep'" style="height: 3000">
		<form id="addDlogForm" class="commonForm" method="post">
			<input type="hidden" name="id" id="id" value="${actSettingDTO.id}">
			<table class="commonTable">
				<tbody id="table_activity_baseInfo">
					<tr>
						<td colspan="6" align="center" height="40px"><b>活动基础设置</b></td>
					</tr>
					<tr>
						<th>活动编号<span class="spanRed">*</span></th>
						<td><input type="text" name="activityPeriods"
							id="activityPeriods" style="width: 380px;" disabled="disabled"
							value="${actSettingDTO.activityPeriods}" class="easyui-validatebox"
							data-options="required:true,validType: 'length[0,100]'" /></td>

						<th>活动名称<span class="spanRed">*</span></th>
						<td><input type="text" name="activityName"
							style="width: 380px;" class="easyui-validatebox"
							<c:if test="${actSettingDTO.proposalStatus=='ActHasApproved'}">disabled="disabled"</c:if>
							value="${actSettingDTO.activityName}"
							data-options="required:true,validType: 'length[0,100]'" /></td>
					</tr>
					<tr>
						<th>活动状态</th>
						<td colspan="3"><select id="enableFlag" name="enableFlag"
							style="width: 160px;" class="easyui-combobox easyui-validatebox"
							data-options="validType:'selectValueRequired[\'#enableFlag\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<option value="Y"
									<c:if test="${actSettingDTO.enableFlag=='Y'}">selected</c:if>>启动</option>
								<option value="N"
									<c:if test="${actSettingDTO.enableFlag=='N'}">selected</c:if>>禁用</option>
						</select></td>
					</tr>
					<tr>
						<th>活动开始时间<span class="spanRed">*</span></th>
						<td><input type="hidden" id="initStartTimeStr"
							value="<fmt:formatDate value='${actSettingDTO.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							<input class="easyui-datetimebox" type="text" name="startTime"
							id="startTimeStr"
							<c:if test="${isActStart}">disabled="disabled"</c:if>
							value="<fmt:formatDate value='${actSettingDTO.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
							data-options="onShowPanel:function(){var vstr = $(this).datetimebox('getValue'); if(vstr==''){$(this).datetimebox('spinner').timespinner('setValue','00:00:00');}},required:true,validType:'compareToEnd[\'initEndTimeStr\']',missingMessage:'请选择活动开始时间',editable:false" />
						</td>
						<th>活动结束时间<span class="spanRed">*</span></th>
						<td><input type="hidden" id="initEndTimeStr"
							value="<fmt:formatDate value='${actSettingDTO.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							<input class="easyui-datetimebox" type="text" name="endTime"
							id="endTimeStr"
							value="<fmt:formatDate value='${actSettingDTO.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
							data-options="onShowPanel:function(){var vstr = $(this).datetimebox('getValue'); if(vstr==''){$(this).datetimebox('spinner').timespinner('setValue','23:59:59');}},required:true,validType:'compareToStart[\'startTimeStr\']',missingMessage:'请选择活动结束时间',editable:false" />
						</td>
					</tr>
					<tr>
						<th>活动链接地址</th>
						<td><textarea name="activityUrl" id="activityUrl"
								maxlength="500" rows="30" cols="80" style="width: 600px">${actSettingDTO.activityUrl}</textarea></td>
						<th>其他信息</th>
						<td><textarea name="otherMsg" id="otherMsg" maxlength="2048"
								rows="30" cols="80" style="width: 600px">${actSettingDTO.otherMsg}</textarea></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div id="tab-tools-open-onestep">
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-ok'" onclick="updateActivitySetting.save();">确定</a>
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-cancel'"
			onclick="updateActivitySetting.cancel();">返回</a>
	</div>
</body>
</html>