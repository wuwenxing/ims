<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/common.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/page/activity/base/rw/addActivitySetting.js"
	charset="UTF-8"></script>
</head>
<body>
	<div class="easyui-tabs" id="addActSetting"
		data-options="tools:'#tab-tools-open-onestep'" style="height: 10000">
		<form id="addDlogForm" class="commonForm" method="post">
			<input type="hidden" name="id" id="addActivitySettingId">
			<table class="commonTable">
				<tbody id="table_activity_baseInfo">
					<tr>
						<td colspan="6" align="center" height="40px"><b>活动基础设置</b></td>
					</tr>
					<tr>
						<th>活动编号<span class="spanRed">*</span></th>
						<td><input type="hidden" name="activityType"
							value="${activityType}" /> <input type="text"
							name="activityPeriods" id="activityPeriods" style="width: 380px;"
							value="${defaultActCode}" class="easyui-validatebox"
							data-options="required:true,validType: 'length[0,100]'" /></td>
						<th>活动名称<span class="spanRed">*</span></th>
						<td><input type="text" name="activityName"
							style="width: 380px;" value="" class="easyui-validatebox"
							data-options="required:true,validType: 'length[0,100]'" /></td>
						<th>活动状态</th>
						<td colspan="3"><select id="enableFlag" name="enableFlag"
							style="width: 160px;" class="easyui-combobox easyui-validatebox"
							data-options="validType:'selectValueRequired[\'#enableFlag\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
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
						<th>活动完成时间</th>
						<td><input class="easyui-numberbox" type="text"
							name="finishDays" id="finishDays" maxlength="3" value="0"
							data-options="min:0" />天</td>
					</tr>
					<tr>
						<th>代币有效期</th>
						<td><input class="easyui-numberbox" type="text"
							name="coinDays" id="coinDays" maxlength="3" value="0"
							data-options="min:0" />天</td>
						<th>发放方式</th>
						<td colspan="3"><select id="autoHandOut" name="autoHandOut"
							style="width: 160px;" class="easyui-combobox"
							data-options="panelHeight:'auto', editable:false">
								<option value="0">人工审核</option>
								<option value="1" selected>自动发放</option>
						</select></td>
					</tr>
					<tr>
						<th>活动链接地址</th>
						<td colspan="5"><textarea name="activityUrl" id="activityUrl"
								maxlength="500" rows="30" cols="80" style="width: 600px"></textarea></td>
					</tr>
				</tbody>
				<tbody id="table_activity_condition">
					<tr>
						<td colspan="6" align="center" height="40px"><b>参与活动条件</b><br />
							<span class="spanRed">(温馨提示：活动一旦审批通过且已开始，则不能修改活动的开始时间及参与条件（除黑白名单有权限的人可以修改外），请知悉！)</span></td>
					</tr>
					<tr>
						<th>账号类型<span class="spanRed">*</span></th>
						<td><select id="accountType"
							name="custActCondition.accountType" class="easyui-combobox"
							data-options="validType:'selectValueRequired[\'#accountType\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<ims:dictList dataList="${ActAccountType}" />
						</select></td>
						<th>账号唯一性<span class="spanRed">*</span></th>
						<td><input type="hidden" id="accountOnlyDA"
							name="custActCondition.accountOnly" value="0" /> <select
							id="accountOnly" style="width: 160px;" disabled="disabled"
							class="easyui-combobox easyui-validatebox"
							data-options="validType:'selectValueRequired[\'#accountOnly\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
								<option value="1">是</option>
								<option value="0" selected>否</option>
						</select></td>
						<th>账号级别<span id="accountLevelSpan" class="spanRed">*</span></th>
						<td><c:forEach var="aLevel" items="${ActAccountLevel}">
								<input type="checkbox" name="custActCondition.accountLevel"
									value="${aLevel.dictCode}" />${aLevel.dictName}
					     </c:forEach> <span id="accountLevelDA" style="display: none"
							class="spanRed">请选择账户级别</span></td>
					</tr>
					<tr>
						<th>参与活动的平台<span class="spanRed">*</span></th>
						<td><c:forEach var="pform" items="${ActPlatformCcy}"
								varStatus="vs">
								<input type="checkbox" name="custActCondition.platformsCcy"
									value="${pform.dictCode}" />${pform.dictName}
					      <c:if test="${vs.count%3==0}">
									<br />
								</c:if>
							</c:forEach> <span id="platformsDA" style="display: none" class="spanRed">请选择参与活动的平台</span>
						</td>

						<th>账号注册日期</th>
						<td colspan="3"><input class="easyui-datetimebox" type="text"
							id="custRegisterStartTime"
							name="custActCondition.registerStartTime"
							data-options="onShowPanel:function(){$(this).datetimebox('spinner').timespinner('setValue','00:00:00');},editable:false,validType:'compareToEndNew[\'custRegisterEndTime\']'" />
							至 <input class="easyui-datetimebox" type="text"
							id="custRegisterEndTime" name="custActCondition.registerEndTime"
							data-options="onShowPanel:function(){$(this).datetimebox('spinner').timespinner('setValue','23:59:59');},editable:false,validType:'compareToStart[\'custRegisterStartTime\']'" />
						</td>
					</tr>
					<tr>
						<th>只允许白名单用户参加活动</th>
						<td><select id="allowWhiteUsers"
							name="custActCondition.allowWhiteUsers" style="width: 160px;"
							class="easyui-combobox easyui-validatebox"
							data-options="panelHeight:'auto', editable:false">
								<option value="1">是</option>
								<option value="0" selected>否</option>
						</select></td>
						<th>上传白名单 <span id="allowWhiteUsersSpan" class="spanRed"
							style="display: none">*</span>
						</th>
						<td><a class="easyui-linkbutton"
							data-options="plain:true,iconCls:'icon-download'"
							href="<%=request.getContextPath()%>/template/list_white_black_template.xlsx">下载模板</a>
							<br /> <input type="file" name="file" id="file1"
							style="width: 155px;"><span id="file1NameSpan"></span> <input
							type="hidden" name="custActCondition.whiteListFilePath"
							id="file1Path" /> <input type="hidden"
							name="custActCondition.whiteListFileName" id="file1Name" /> <a
							class="easyui-linkbutton"
							data-options="plain:true,iconCls:'icon-upload',disabled:false"
							onclick="javascript:$('#file1').uploadify('upload', '*');">上传文件</a>
							<a id="delWhiteFile" class="easyui-linkbutton"
							data-options="plain:true,iconCls:'icon-cancel',disabled:false"
							onclick="addActivitySetting.deleteFile(1);">刪除文件</a> <span
							id="whiteListFileDA" style="display: none" class="spanRed">请选择</span></td>
						<th>上传黑名单
						<td><a class="easyui-linkbutton"
							data-options="plain:true,iconCls:'icon-download'"
							href="<%=request.getContextPath()%>/template/list_white_black_template.xlsx">下载模板</a>
							<br /> <input type="file" name="file" id="file2"
							style="width: 155px;"><span id="file2NameSpan"></span> <input
							type="hidden" name="custActCondition.blackListFilePath"
							id="file2Path" /> <input type="hidden"
							name="custActCondition.blackListFileName" id="file2Name" /> <a
							class="easyui-linkbutton"
							data-options="plain:true,iconCls:'icon-upload',disabled:false"
							onclick="javascript:$('#file2').uploadify('upload', '*');">上传文件</a> <a id="delBackFile"
							class="easyui-linkbutton"
							data-options="plain:true,iconCls:'icon-cancel',disabled:false"
							onclick="addActivitySetting.deleteFile(2);">刪除文件</a></td>
					</tr>
					<tr>
						<th>注销过账号参加活动</th>
						<td><input type="hidden" id="allowCancelAccount"
							name="custActCondition.allowCancelAccount" value="0" /> <select
							disabled="disabled" style="width: 160px;"
							class="easyui-combobox easyui-validatebox"
							data-options="validType:'selectValueRequired[\'#allowCancelAccount\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
								<option value="0" selected="selected">否</option>
								<option value="1">是</option>
						</select></td>
						<th>交易产品</th>
						<td colspan="3"><select id="custActConditionItems"
							name="custActCondition.items" class="easyui-combobox"
							data-options="" style="width: 400; height: 80">
						</select> <a href="javascript:void(0);" class="easyui-linkbutton"
							data-options=""
							onclick="addActivitySetting.allCheckCombobox(true)">全选</a> <a
							href="javascript:void(0);" class="easyui-linkbutton"
							data-options=""
							onclick="addActivitySetting.allCheckCombobox(false)">取消全选</a></td>
					</tr>
					<tr>
				</tbody>
			</table>
		</form>
		<form id="addTaskForm" name="addTaskForm" class="commonForm"
			method="post">
			<table class="commonTable">
				<tbody id="table_activity_task">
					<tr>
						<td colspan="6" align="center"><b>任务与奖励 <span
								class="spanRed">* </span></b> <span id="tableActivityTaskDA"
							style="display: none" class="spanRed">请添加任务与奖励</span></td>
					</tr>
				</tbody>
				<tbody>
					<tr>
						<td colspan="6" align="center">【 <a
							href="javascript:void(0);" class="easyui-linkbutton"
							onclick="addActivitySetting.addTaskItem();"
							data-options="iconCls:'icon-add', plain:'true'">添加任务项</a>】
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<table class="commonTable" style="display: none">
			<tbody id="taskItem" style="display: none">
				<tr id="tr1__#_">
					<td colspan="6" align="right">&nbsp;【<a
						href="javascript:void(0);" class="easyui-linkbutton"
						onclick="addActivitySetting.deleteTaskItem(_#_);"
						data-options="iconCls:'icon-remove', plain:'true'">删除任务项</a>】
					</td>
				</tr>
				<tr id="tr2__#_">
					<th>任务<span class="spanRed">*</span></th>
					<td colspan="5"><select id="taskItemCode__#_"
						name="taskItemCode" style="width: 250px;" curNum="_#_"
						class="easyui-validatebox"
						onchange="addActivitySetting.switchTask(_#_)">
							<option value="" unitStr="" demo="" disableCode="">--请选择--</option>
							<c:forEach items="${rwActityTaskTypes}" var="t">
								<option value="${t.code}" title="${t.name}"
									unitStr="${t.unitStr}" unitValidTypeStr="${t.unitValidTypeStr}"
									itemUnitStr="${t.itemUnitStr}" demo="${t.demo}"
									outCode="${t.outCode}" isReceiveMaxNum="${t.isReceiveMaxNum}"
									isReceiveMaxMoney="${t.isReceiveMaxMoney}">${t.name}</option>
							</c:forEach>
					</select> <span id="taskItemValSpan__#_" style="display: none"> <input
							style="display: none" type="text" id="temp_taskItemVal__#_"
							curNum="_#_" name="temp_taskItemVal"
							onblur="addActivitySetting.setNameParamValue('taskItemVal',this);"
							class="easyui-validatebox"
							data-options="required:false,missingMessage:'请输入'"
							style="width: 120px;" maxlength="10" value="" />
					</span> <span id="taskItemUnitStrSpan__#_" style="display: none"> </span>
						<span id="taskItemTimeSpan__#_" style="display: none"> <input
							style="display: none" type="text" id="temp_taskItemTime__#_"
							curNum="_#_" name="temp_taskItemTime"
							onblur="addActivitySetting.setNameParamValue('taskItemTime',this);"
							class="easyui-validatebox"
							data-options="required:false,missingMessage:'请输入'"
							style="width: 120px;" maxlength="10" value="" /> 小时
					</span> <input type="hidden" id="taskItemVal__#_" name="taskItemVal"
						value="" /> <input type="hidden" id="taskItemTime__#_"
						name="taskItemTime" value="" /> <span id="taskItemTip__#_"
						style="display: none" class="spanRed">任务已重复,请检查！</span> <span
						id="taskItemTip2__#_" style="display: none" class="spanRed">请先选择任务！</span>
						<span id="taskItemTip3__#_" style="display: none" class="spanRed">不符合参与条件的账号类型，请检查！</span>
						<span id="taskItemTip4__#_" style="display: none" class="spanRed"></span>
					</td>

				</tr>
				<tr id="tr4__#_">
					<th>任务描述</th>
					<td colspan="5"><input type="text" id="taskDesc__#_"
						name="taskDesc" style="width: 300px;" class="easyui-validatebox"
						data-options="required:false,validType: 'length[0,100]'" /></td>
				</tr>
				<tr id="tr3__#_">
					<th>奖励物品<span class="spanRed">*</span></th>
					<td colspan="5">
						<table id="items__#_" style="width: 100%">
							<tr>
								<td>【<a href="javascript:void(0);"
									class="easyui-linkbutton"
									onclick="addActivitySetting.addItem(_#_);"
									data-options="iconCls:'icon-add', plain:'true'">添加物品</a>】 <span
									id="addItemCodeTip__#_" style="display: none; font-size: 1"
									class="spanRed">奖励物品存在重复,请检查！</span> <span
									id="noAddItemCodeTip__#_" style="display: none; font-size: 1"
									class="spanRed">请添加奖励物品！</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</tbody>
			<tbody id="addItem" style="display: none">
				<tr name="items_tr__#_">
					<td><span style="font-size: 1">物品:</span> <select
						name="addItemCode__#_"
						onchange="addActivitySetting.switchItem(this,_#_)">
							<option value="">---请选择---</option>
							<c:forEach items="${actItems}" var="item">
								<c:if test="${item.effectiveItem}">
									<option value="${item.giftNumber}" giftType="${item.giftType}">${item.giftName}</option>
								</c:if>
							</c:forEach>
					</select> <span name="itemParamVarTip__#_" style="display: none">请选择物品!</span>
						<span isParam="true" name="itemParamVar__#_" style="display: none">
							<input type="text" name="tempItemParamVar__#_"
							onblur="addActivitySetting.setNameParamValue('itemParamVar__#_',this);"
							maxlength="10" value="0" />
					</span> <span isParam="true" name="itemUnitStr__#_" style="display: none"></span>
						<span isParam="true" name="receiveMaxMoneySpan__#_"
						style="font-size: 1; display: none">最高领取金额: <input
							type="text" name="tempReceiveMaxMoney__#_"
							onblur="addActivitySetting.setNameParamValue('receiveMaxMoney__#_',this);"
							maxlength="10" value="" />&nbsp;元
					</span> <span isParam="true" name="receiveMaxNumSpan__#_"
						style="font-size: 1; display: none">最高领取次数: <input
							type="text" name="tempReceiveMaxNum__#_"
							onblur="addActivitySetting.setNameParamValue('receiveMaxNum__#_',this);"
							maxlength="10" value="" />&nbsp;次
					</span> <span isParam="true" name="tradeNumSpan__#_"
						style="font-size: 1; display: none">交易手数: <input
							type="text" name="tempTradeNum__#_"
							onblur="addActivitySetting.setNameParamValue('tradeNum__#_',this);"
							maxlength="10" value="" />&nbsp;手
					</span> <input type="hidden" isParam="true" name="itemParamVar__#_"
						value="" /> <input type="hidden" isParam="true"
						name="receiveMaxMoney__#_" value="" /> <input type="hidden"
						isParam="true" name="receiveMaxNum__#_" value="" /> <input
						type="hidden" isParam="true" name="tradeNum__#_" value="" /> 【<a
						href="javascript:void(0);" class="easyui-linkbutton"
						onclick="addActivitySetting.deleteItem(this);"
						data-options="iconCls:'icon-remove', plain:'true'">取消</a>】</td>
				</tr>
			</tbody>
		</table>

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