<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/common.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/page/activity/base/qt/viewActivitySetting.js"
	charset="UTF-8"></script>
</head>
<body>
	<div class="easyui-tabs" id="addActSetting"
		data-options="fit:true,tools:'#tab-tools-open-onestep'"
		style="height: auto">
		<form id="addDlogForm" class="commonForm" method="post">
			<input type="hidden" name="id" id="id">
			<table class="commonTable">
				<tbody id="table_activity_baseInfo">
					<tr>
						<td colspan="6" align="center" height="40px"><b>活动基础设置</b></td>
					</tr>
					<tr>
						<th>活动类型<span class="spanRed">*</span></th>
						<td>${actSettingDTO.activityTypeText}</td>
						<th>活动编号<span class="spanRed">*</span></th>
						<td>${actSettingDTO.activityPeriods}</td>

						<th>活动名称<span class="spanRed">*</span></th>
						<td>${actSettingDTO.activityName}</td>
					</tr>
					<tr>
						<th>活动开始时间<span class="spanRed">*</span></th>
						<td><fmt:formatDate value='${actSettingDTO.startTime}'
								pattern='yyyy-MM-dd HH:mm:ss' /></td>
						<th>活动结束时间<span class="spanRed">*</span></th>
						<td><fmt:formatDate value='${actSettingDTO.endTime}'
								pattern='yyyy-MM-dd HH:mm:ss' /></td>
						<th>活动状态</th>
						<td>${actSettingDTO.enableFlag}</td>
					</tr>
					<tr>
						<th>活动地址</th>
						<td>${actSettingDTO.activityUrl}</td>
						<th>其他信息</th>
						<td colspan="3">${actSettingDTO.otherMsg}</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div id="tab-tools-open-onestep">
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-cancel'"
			onclick="viewActivitySetting.cancel();">返回</a>
	</div>
</body>
</html>