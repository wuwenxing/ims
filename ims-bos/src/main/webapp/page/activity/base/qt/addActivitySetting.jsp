<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/common.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/page/activity/base/qt/addActivitySetting.js"
	charset="UTF-8"></script>
</head>
<body>
	<input type="hidden" id="actType" value="${actType}">
	<div class="easyui-tabs" id="addActSetting"
		data-options="tools:'#tab-tools-open-onestep'" style="height: 500">
		<form id="addDlogForm" class="commonForm" method="post">
			<input type="hidden" name="id" id="id" /> <input type="hidden"
				name="activityType" value="${activityType}" />
			<table class="commonTable">
				<tbody id="table_activity_baseInfo">
					<tr>
						<td colspan="6" align="center" height="40px"><b>活动基础设置</b></td>
					</tr>
					<tr>
						<th>活动编号<span class="spanRed">*</span></th>
						<td><input type="text" name="activityPeriods"
							id="activityPeriods" style="width: 380px;"
							value="${activityPeriods}" class="easyui-validatebox"
							data-options="required:true,validType: 'length[0,100]'" /></td>

						<th>活动名称<span class="spanRed">*</span></th>
						<td><input type="text" name="activityName" id="activityName"
							style="width: 380px;" value="" class="easyui-validatebox"
							data-options="required:true,validType: 'length[0,100]'" /></td>

					</tr>
					<tr>
						<th>活动状态</th>
						<td><select id="enableFlag" name="enableFlag"
							style="width: 160px;" class="easyui-combobox easyui-validatebox"
							data-options="validType:'selectValueRequired[\'#enableFlag\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
								<option value="Y" selected>启动</option>
								<option value="N">禁用</option>
						</select></td>
					</tr>
					<tr>
						<th>活动开始时间<span class="spanRed">*</span></th>
						<td><input class="easyui-datetimebox" type="text"
							name="startTime" id="startTimeStr"
							data-options="onShowPanel:function(){var vstr = $(this).datetimebox('getValue'); if(vstr==''){$(this).datetimebox('spinner').timespinner('setValue','00:00:00');}},required:true,validType:'compareToNowAndEnd[\'\']',missingMessage:'请选择活动开始时间',editable:false" />
						</td>
						<th>活动结束时间<span class="spanRed">*</span></th>
						<td><input class="easyui-datetimebox" type="text"
							name="endTime" id="endTimeStr"
							data-options="onShowPanel:function(){var vstr = $(this).datetimebox('getValue'); if(vstr==''){$(this).datetimebox('spinner').timespinner('setValue','23:59:59');}},required:true,validType:'compareToStart[\'startTimeStr\']',missingMessage:'请选择活动结束时间',editable:false" />
						</td>
					</tr>
					<tr>
						<th>活动链接地址</th>
						<td><textarea name="activityUrl" id="activityUrl"
								maxlength="500" rows="30" cols="80" style="width: 600px"></textarea></td>
						<th>其他信息</th>
						<td><textarea name="otherMsg" id="otherMsg" maxlength="2048"
								rows="30" cols="80" style="width: 600px"></textarea></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div id="tab-tools-open-onestep">
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-ok'" onclick="addActivitySetting.save();">确定</a>
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-cancel'"
			onclick="addActivitySetting.cancel();">返回</a>
	</div>
</body>
</html>