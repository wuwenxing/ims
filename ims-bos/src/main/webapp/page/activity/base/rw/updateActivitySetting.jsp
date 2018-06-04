<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/third/uploadify/css/uploadify.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/third/uploadify/jquery.uploadify.min.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/activity/base/rw/updateActivitySetting.js" charset="UTF-8"></script>
</head>
<body>
 	<div class="easyui-tabs" id="addActSetting" data-options="tools:'#tab-tools-open-onestep'" style="height:10000">
		<form id="addDlogForm" class="commonForm" method="post">
			<input type="hidden" name="id"  value="${actSetting.id}">
			<table class="commonTable">
			 <tbody id="table_activity_baseInfo">
			     <tr>
					<td colspan="6" align="center" height="40px"><b>活动基础设置</b>
					 <input	type="hidden" name="id" value="${actSetting.id}"/>
					</td>
				 </tr>
				<tr>
					<th>活动编号<span class="spanRed">*</span></th>
					<td>
						<input	type="text" name="activityPeriods" id="activityPeriods" style="width: 180px;" disabled="disabled"  value="${actSetting.activityPeriods}" class="easyui-validatebox" 
						data-options="required:true,validType: 'length[0,100]'" />
					</td>
					<th>活动名称<span class="spanRed">*</span></th>
					<td>
						<input	type="text" name="activityName" style="width: 160px;" class="easyui-validatebox" <c:if test="${actSetting.proposalStatus=='ActHasApproved'}">disabled="disabled"</c:if>  value="${actSetting.activityName}"
						data-options="required:true,validType: 'length[0,100]'" />
					</td>
					<th>活动状态</th>
					<td>
						<select id="enableFlag" name="enableFlag"  style="width: 160px;" class="easyui-combobox easyui-validatebox" data-options="validType:'selectValueRequired[\'#enableFlag\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
							<option value="">---请选择---</option>
							<option value="Y" <c:if test="${actSetting.enableFlag=='Y'}">selected</c:if>>启动</option>
							<option value="N"  <c:if test="${actSetting.enableFlag=='N'}">selected</c:if>>禁用</option>
						</select>
					</td>
				</tr>
				<tr>
			    	<th>活动开始时间<span class="spanRed">*</span></th>
					<td><input type="hidden" id="initStartTimeStr" value="<fmt:formatDate value='${actSetting.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						<input class="easyui-datetimebox" type="text" name="startTime" id="startTimeStr" <c:if test="${isActStart}">disabled="disabled"</c:if>  data-options="onShowPanel:function(){var vstr = $(this).datetimebox('getValue'); if(vstr==''){$(this).datetimebox('spinner').timespinner('setValue','00:00:00');}},required:true,validType:'compareToEnd[\'initEndTimeStr\']',missingMessage:'请选择活动开始时间',editable:false"/>
					</td>
					<th>活动结束时间<span class="spanRed">*</span></th>
					<td><input type="hidden" id="initEndTimeStr" value="<fmt:formatDate value='${actSetting.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
						<input class="easyui-datetimebox" type="text" name="endTime" id="endTimeStr"  data-options="onShowPanel:function(){var vstr = $(this).datetimebox('getValue'); if(vstr==''){$(this).datetimebox('spinner').timespinner('setValue','23:59:59');}},required:true,validType:'compareToStart[\'startTimeStr\']',missingMessage:'请选择活动结束时间',editable:false"/>
					</td>
					<th>活动完成时间</th>
					<td>
						<input class="easyui-numberbox" type="text" name="finishDays" id="finishDays" <c:if test="${isActStart}">disabled="disabled"</c:if> value="${actSetting.finishDays}"  maxlength="3"/>天
					</td>
				</tr>
				<tr>
					<th>代币有效期</th> 
					<td>
						<input class="easyui-numberbox" type="text" name="coinDays" id="coinDays" value="${actSetting.coinDays}" maxlength="3" />天
					</td>
					<th>发放方式</th> 
					<td colspan="3">
					    <select id="autoHandOut" name="autoHandOut"  style="width: 160px;" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							<option value="1" <c:if test="${actSetting.autoHandOut==1}">selected</c:if>>自动发放</option>
							<option value="0" <c:if test="${actSetting.autoHandOut==0}">selected</c:if>>人工审核</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>活动链接地址</th> 
					<td colspan="5">
					 <textarea name="activityUrl" id="activityUrl" maxlength="500" rows="30" cols="80"  style="width: 600px"  >${actSetting.activityUrl}</textarea></td>
				</tr>
			   </tbody>
			    <tbody id="table_activity_condition">
			      <tr>
				    <td colspan="6" align="center"  height="40px"><b>参与活动条件</b>
				    <c:if test="${!isActStart}"><br/><span class="spanRed">(温馨提示：活动一旦审批通过且已开始，则不能修改活动的开始时间及参与条件（除黑白名单有权限的人可以修改外），请知悉！)</span></c:if>
					</td>
				  </tr>
				<tr>
				    <c:set var="custActCondition" value="${actSetting.custActCondition.conditionValObj}"/>
				    <input type="hidden" name="custActCondition.actConditionSettingId" value="${actSetting.custActCondition.id}"/>
					<th>账号类型<span class="spanRed">*</span></th>
					<td>
					    <c:if test="${isActStart}"><input type="hidden"    name="custActCondition.accountType" value="${custActCondition.accountType}"/></c:if>
						<select id="accountType" <c:choose><c:when test="${isActStart}">disabled="disabled"</c:when><c:otherwise>name="custActCondition.accountType"</c:otherwise></c:choose>  class="easyui-combobox" data-options="validType:'selectValueRequired[\'#accountType\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
								<option value=""><spring:message code="common.search.selectoption"/></option>
								<bos:dictListTag  dataList="${ActAccountType}" defaultVal="${custActCondition.accountType}"/>
						</select>
					</td>
					<th>账号唯一性<span class="spanRed">*</span></th>
					<td>
					    <input type="hidden"    name="custActCondition.accountOnly" value="${custActCondition.accountOnly}"/>
						<select id="accountOnly" disabled="disabled" style="width:160px;" class="easyui-combobox easyui-validatebox" data-options="validType:'selectValueRequired[\'#accountOnly\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
							<option value="1" <c:if test="${custActCondition.accountOnly}">selected</c:if>>是</option>
							<option value="0" <c:if test="${!custActCondition.accountOnly}">selected</c:if>>否</option>
						</select>
					</td>
					<th>账号级别<span  id="accountLevelSpan"  class="spanRed">*</span></th>
					<td>
					  <c:if test="${isActStart}"><input type="hidden"   name="custActCondition.accountLevel" value="${custActCondition.accountLevel}"/></c:if>
					    <c:set var="accountLevel" value="${custActCondition.accountLevel},"/><!-- 尾号加个逗号，避免相关前缀 -->
						 <c:forEach var="aLevel" items="${ActAccountLevel}" >
						    <c:set var="dictCode" value="${aLevel.dictCode},"/><!-- 尾号加个逗号，避免相关前缀 -->
					        <input type="checkbox" <c:choose><c:when test="${isActStart}">disabled="disabled"</c:when><c:otherwise>name="custActCondition.accountLevel"</c:otherwise></c:choose> <c:if test="${custActCondition.accountType=='type_demo'}">disabled="disabled"</c:if> value="${aLevel.dictCode}" <c:if test="${fn:contains(accountLevel,dictCode)}">checked="checked"</c:if>/>${aLevel.dictName}
					     </c:forEach>
					      <span id="accountLevelDA" style="display:none" class="spanRed">请选择账号级别</span>
					</td>
				</tr>
				 <tr>
				 <th>参与活动的平台<span class="spanRed">*</span></th>
					<td>
					<c:if test="${isActStart}"><input type="hidden"  name="custActCondition.platformsCcy" value="${custActCondition.platformsCcy}"/></c:if>
					   <c:set var="platforms" value="${custActCondition.platformsCcy},"/><!-- 尾号加个逗号，避免相关前缀代号eg:gts,gts2 -->
					   <c:forEach var="pform" items="${ActPlatformCcy}" varStatus="vs">
					       <c:set var="dictCode" value="${pform.dictCode},"/>
					      <input type="checkbox" <c:choose><c:when test="${isActStart}">disabled="disabled"</c:when><c:otherwise>name="custActCondition.platformsCcy"</c:otherwise></c:choose> value="${pform.dictCode}" <c:if test="${fn:contains(platforms,dictCode)}">checked="checked"</c:if>/>${pform.dictName}
					      <c:if test="${vs.count%3==0}"><br/></c:if>
					   </c:forEach>
					   <span id="platformsDA" style="display:none" class="spanRed">请选择参与活动的平台</span>
					</td>
					
					<th>账号注册日期</th>
					<td colspan="3">
					   <c:if test="${isActStart}">
						<input type="hidden" name="custActCondition.registerStartTime" value="${custActCondition.registerStartTime}"/>
						<input type="hidden" name="custActCondition.registerEndTime" value="${custActCondition.registerEndTime}"/>
					   </c:if>
					   <input class="easyui-datetimebox" <c:choose><c:when test="${isActStart}">disabled="disabled"</c:when><c:otherwise>name="custActCondition.registerStartTime"</c:otherwise></c:choose>  type="text"    id="registerStartTimeStr" value="${custActCondition.registerStartTime}" data-options="editable:false,validType:'compareToEndNew[\'registerEndTimeStr\']'"/>
					        至
					    <input class="easyui-datetimebox" <c:choose><c:when test="${isActStart}">disabled="disabled"</c:when><c:otherwise>name="custActCondition.registerEndTime"</c:otherwise></c:choose>  type="text"    id="registerEndTimeStr" value="${custActCondition.registerEndTime}" data-options="editable:false,validType:'compareToStart[\'registerStartTimeStr\']'"/>
					</td>
				</tr>
				<tr>
				 <th>只允许白名单用户参加活动</th>
					<td>
					  <select id="allowWhiteUsers" name="custActCondition.allowWhiteUsers"  style="width:160px;" class="easyui-combobox easyui-validatebox" data-options="panelHeight:'auto', editable:false">
							<option value="1" <c:if test="${custActCondition.allowWhiteUsers}">selected</c:if>>是</option>
							<option value="0" <c:if test="${!custActCondition.allowWhiteUsers}">selected</c:if>>否</option>
						</select>
					</td>
				    <th>上传白名单<span id="allowWhiteUsersSpan" class="spanRed" <c:if test="${!custActCondition.allowWhiteUsers}">style="display:none"</c:if>>*</span></th>
					<td>
				    	  <a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-download'" href="<%=request.getContextPath()%>/template/list_white_black_template.xlsx"><spring:message code="common.click.download.template"/></a>
					    <br/>
						<input type="file" name="file" id="file1" style="width:155px;"><span id="file1NameSpan">${custActCondition.whiteListFileName}</span>
			        	<input type="hidden" name="custActCondition.whiteListFilePath" id="file1Path" value="${custActCondition.whiteListFilePath}"/>
			        	<input type="hidden" name="custActCondition.whiteListFileName" id="file1Name" value="${custActCondition.whiteListFileName}"/>
			        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-upload',disabled:false"  onclick="javascript:$('#file1').uploadify('upload', '*');"><spring:message code="common.uploadFile"/></a> 
			        	<a id ="delWhiteFile" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel',disabled:false" onclick="addActivitySetting.deleteFile(1);"><spring:message code="customer.file.delete"/><!--刪除文件--></a>
					   <span id="whiteListFileDA" style="display:none" class="spanRed">请选择</span>		
					</td>
					<th>上传黑名单</th>
					<td>
				    	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-download'" href="<%=request.getContextPath()%>/template/list_white_black_template.xlsx"><spring:message code="common.click.download.template"/></a>
				    	<br/>				    	
						<input type="file" name="file" id="file2" style="width:155px;"><span id="file2NameSpan">${custActCondition.blackListFileName}</span>
			        	<input type="hidden" name="custActCondition.blackListFilePath" id="file2Path" value="${custActCondition.blackListFilePath}"/>
			        	<input type="hidden" name="custActCondition.blackListFileName" id="file2Name" value="${custActCondition.blackListFileName}"/>
			        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-upload',disabled:false"  onclick="javascript:$('#file2').uploadify('upload', '*');"><spring:message code="common.uploadFile"/></a> 
			        	<a id="delBackFile" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel',disabled:false" onclick="addActivitySetting.deleteFile(2);"><spring:message code="customer.file.delete"/><!--刪除文件--></a> 
					</td>
				  </tr>
				
					<tr>
					<th>注销过账号参加活动<span class="spanRed">*</span></th>
					<td>
					<input type="hidden"  id="allowCancelAccount" name="custActCondition.allowCancelAccount" value="${custActCondition.allowCancelAccount}"/>
						<select  disabled="disabled"  style="width:160px;" class="easyui-combobox easyui-validatebox" data-options="validType:'selectValueRequired[\'#allowCancelAccount\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
							<option value="1" <c:if test="${custActCondition.allowCancelAccount}">selected</c:if>>是</option>
							<option value="0" <c:if test="${!custActCondition.allowCancelAccount}">selected</c:if>>否</option>
						</select>
					</td>
					<th>交易产品</th>
					<td colspan="3">
					    <c:if test="${isActStart}">
						<input type="hidden" name="custActCondition.items" value="${custActCondition.items}"/>
					   </c:if>
					     <input type="hidden" id="custActConditionItemsValue" value="${custActCondition.items}"/>
				    	 <select id="custActConditionItems" <c:choose><c:when test="${isActStart}">disabled="disabled"</c:when><c:otherwise>name="custActCondition.items"</c:otherwise></c:choose>  class="easyui-combobox"    data-options="" style="width: 400;height: 80" >  
				    	 </select>
				    	 <c:if test="${!isActStart}">
				    	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options=""  onclick="addActivitySetting.allCheckCombobox(true)">全选</a> 
				    	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options=""  onclick="addActivitySetting.allCheckCombobox(false)">取消全选</a>
				    	 </c:if>
					</td>
				  </tr>
			    </tbody>
			</table>
		</form>
		<form id="addTaskForm" name="addTaskForm" class="commonForm" method="post">
		 <table class="commonTable">
		       <tbody id="table_activity_task">
			        <tr>
					  <td colspan="6" align="center"><b>任务与奖励
					   <span class="spanRed">*   </span></b>
					   <span id="tableActivityTaskDA" style="display:none" class="spanRed">请添加任务与奖励</span></td>
				    </tr>
				    <c:set var="addInitStartNum" value="0"/>
				    <c:if test="${not empty actSetting.actTaskSettings}">
				       <c:forEach var="actTask" items="${actSetting.actTaskSettings}" varStatus="index">
				           <c:set var="addInitStartNum" value="${index.count}"/>
				          <tr id="tr1_${index.count}">
						       <td colspan="6" align="right">&nbsp;【<a href="javascript:void(0);" class="easyui-linkbutton" onclick="addActivitySetting.deleteTaskItem(${index.count});"
				              data-options="iconCls:'icon-remove', plain:'true'">删除任务项</a>】</td>
						   </tr>
						   <tr id="tr2_${index.count}">
								<th>任务<span class="spanRed">*</span></th>
								<td colspan="5">
								    <input type="hidden" name="taskItemSettingId" value="${actTask.id}"/>
								     <c:set var="curUnitStr" value=""/>
								     <c:set var="curUnitValidTypeStr" value=""/>
									 <c:set var="curItemUnitStr" value=""/>
								     <select id="taskItemCode_${index.count}" name="taskItemCode"  style="width: 250px;" curNum="${index.count}" onchange="addActivitySetting.switchTask(${index.count})" >
										<option value="" unitStr="" demo="" disableCode="">--请选择--</option>
										<c:forEach items="${rwActityTaskTypes}" var="t">
										    <c:if test='${actTask.taskItemCode==t.code}'>
										      <c:set var="curUnitStr" value="${t.unitStr}"/>
										      <c:set var="curUnitValidTypeStr" value="${t.unitValidTypeStr}"/>
										      <c:set var="curItemUnitStr" value="${t.itemUnitStr}"/>
										    </c:if>
										   <option value="${t.code}" title="${t.name}" unitStr="${t.unitStr}" unitValidTypeStr="${t.unitValidTypeStr}" itemUnitStr="${t.itemUnitStr}" demo="${t.demo}"  outCode="${t.outCode}" isReceiveMaxNum="${t.isReceiveMaxNum}"  isReceiveMaxMoney="${t.isReceiveMaxMoney}" <c:if test='${actTask.taskItemCode==t.code}'>selected="selected"</c:if>>${t.name}</option>
										 </c:forEach>
									</select>
									<span id="taskItemValSpan_${index.count}" <c:if test='${empty curUnitStr}'>style="display:none"</c:if>>
									   <input  type="text" id="temp_taskItemVal_${index.count}"    onblur="addActivitySetting.setNameParamValue('taskItemVal',this);"   curNum="${index.count}" name="temp_taskItemVal"  class="easyui-validatebox" data-options="<c:choose><c:when test='${empty curUnitStr}'>required:false,</c:when><c:otherwise>required:true,</c:otherwise></c:choose><c:if test="${not empty curUnitStr && not empty curUnitValidTypeStr}">validType: '${curUnitValidTypeStr}',</c:if>missingMessage:'请输入'"  style="width: 120px;" maxlength="10"  value="${actTask.taskItemVal}"/>
									</span>
									<span id="taskItemUnitStrSpan_${index.count}" <c:if test='${empty curUnitStr}'>style="display:none"</c:if>>
									    ${curUnitStr}
									</span>
									<span id="taskItemTimeSpan_${index.count}" <c:if test='${empty actTask.taskItemTime ||actTask.taskItemTime<=0}'>style="display:none"</c:if>>
									   <input <c:if test='${empty actTask.taskItemTime ||actTask.taskItemTime<=0}'> style="display:none"</c:if> type="text" id="temp_taskItemTime_${index.count}" curNum="${index.count}" name="temp_taskItemTime"    onblur="addActivitySetting.setNameParamValue('taskItemTime',this);" class="easyui-validatebox" data-options="<c:choose><c:when test='${empty actTask.taskItemTime ||actTask.taskItemTime<=0}'>required:false,</c:when><c:otherwise>required:true,</c:otherwise></c:choose>missingMessage:'请输入',validType: 'gtZeroDecimalTwo'"  style="width: 120px;" maxlength="10"  value="${actTask.taskItemTime}"/> 小时
									</span>
								    <input type="hidden" id="taskItemVal_${index.count}" name="taskItemVal"  value="${actTask.taskItemVal}"/>
								    <input type="hidden" id="taskItemTime_${index.count}" name="taskItemTime"  value="${actTask.taskItemTime}"/>
								    <span id="taskItemTip_${index.count}" style="display:none" class="spanRed">任务已重复,请检查！</span>
								    <span id="taskItemTip2_${index.count}" style="display:none" class="spanRed">请先选择任务！</span>
								    <span id="taskItemTip3_${index.count}" style="display:none" class="spanRed">不符合参与条件的账号类型，请检查！</span>
								    <span id="taskItemTip4_${index.count}" style="display:none" class="spanRed"></span>
								</td>
						  </tr>
						  <tr id="tr4_${index.count}">
							<th>任务描述</th>
							<td colspan="5">
						      <input type="text" id="taskDesc_${index.count}"  name="taskDesc" style="width: 300px;" value="${actTask.taskDesc}" />
							</td>
						  </tr>
						  <tr id="tr3_${index.count}">
								<th>奖励物品<span class="spanRed">*</span></th>
								<td colspan="5">
				                     <table id="items_${index.count}" style="width:100%">
				                        <tr><td>
				                                                                      【<a href="javascript:void(0);" class="easyui-linkbutton" onclick="addActivitySetting.addItem(${index.count});"
				                           data-options="iconCls:'icon-add', plain:'true'">添加物品</a>】
				                          <span id="addItemCodeTip_${index.count}" style="display:none;" class="spanRed" >奖励物品存在重复,请检查！</span>
				                          <span id="noAddItemCodeTip_${index.count}" style="display:none;" class="spanRed">请添加奖励物品！</span>
				                       </td></tr>
				                       <c:if test="${not empty actTask.actTaskItems}">
				                        <c:forEach var="actItem" items="${actTask.actTaskItems}">
				                           <tr name="items_tr_${index.count}">
											<td>
											   <input type="hidden" name="addItemId_${index.count}" value="${actItem.id}"/>
												<span style="font-size: 1">物品:</span>
												<select   name="addItemCode_${index.count}" onchange="addActivitySetting.switchItem(this,${index.count})">
											         <option value="">--请选择--</option>
											          <c:forEach items="${actItems}"  var="item">
												          <c:if test="${actItem.itemCode==item.giftNumber || item.effectiveItem}">
												            <option value="${item.giftNumber}" giftType="${item.giftType}" <c:if test="${actItem.itemCode==item.giftNumber}">selected</c:if>>${item.giftName}</option>
												          </c:if>
												      </c:forEach>
											     </select>
											     <span  name="itemParamVarTip_${index.count}" style="display:none" >请选择物品!</span>
											     <span isParam="true" name="itemParamVar_${index.count}">
												  <input type="text" name="tempItemParamVar_${index.count}" onblur="addActivitySetting.setNameParamValue('itemParamVar_${index.count}',this);"  class="easyui-validatebox" data-options="required:true,validType: 'gtZeroDecimalTwo'"  maxlength="10" value="${actItem.itemParamVar}"/>
												 </span>
												 <span isParam="true" name="itemUnitStr_${index.count}" style="font-size: 1;<c:if test='${empty curItemUnitStr}'>display:none</c:if>">${curItemUnitStr}</span>
												 <span isParam="true"  name="receiveMaxMoneySpan_${index.count}" style="font-size: 1;<c:if test='${empty actItem.receiveMaxMoney}'>display:none</c:if>" >最高领取金额:
												    <input type="text" name="tempReceiveMaxMoney_${index.count}"    onblur="addActivitySetting.setNameParamValue('receiveMaxMoney_${index.count}',this);"  class="easyui-validatebox" data-options="<c:choose><c:when test='${empty actItem.receiveMaxMoney}'>required:false,</c:when><c:otherwise>required:true,validType: 'gtZeroDecimalTwo',</c:otherwise></c:choose>" maxlength="10" value="${actItem.receiveMaxMoney}"/>&nbsp;元
												 </span>
												 <span isParam="true"  name="receiveMaxNumSpan_${index.count}"  style="font-size: 1;<c:if test='${empty actItem.receiveMaxNum}'>display:none</c:if>">最高领取次数:
												    <input type="text" name="tempReceiveMaxNum_${index.count}"   onblur="addActivitySetting.setNameParamValue('receiveMaxNum_${index.count}',this);"   class="easyui-validatebox" data-options="<c:choose><c:when test='${empty actItem.receiveMaxNum}'>required:false,</c:when><c:otherwise>required:true,validType: 'int',</c:otherwise></c:choose>" maxlength="10" value="${actItem.receiveMaxNum}"/>&nbsp;次
												 </span>
												 <span isParam="true"  name="tradeNumSpan_${index.count}" style="font-size: 1;<c:if test='${empty actItem.tradeNum}'>display:none</c:if>" >交易手数:
												    <input type="text" name="tempTradeNum_${index.count}"   onblur="addActivitySetting.setNameParamValue('tradeNum_${index.count}',this);"   class="easyui-validatebox" data-options="<c:choose><c:when test='${empty actItem.tradeNum}'>required:false,</c:when><c:otherwise>required:true,validType: 'zeroDecimalTwo',</c:otherwise></c:choose>" maxlength="10" value="${actItem.tradeNum}"/>&nbsp;手
												 </span>
												 <input type="hidden"  isParam="true" name="itemParamVar_${index.count}"  value="${actItem.itemParamVar}"/>
												 <input type="hidden"  isParam="true"  name="receiveMaxMoney_${index.count}"  value="${actItem.receiveMaxMoney}"/>
												 <input type="hidden"  isParam="true" name="receiveMaxNum_${index.count}" value="${actItem.receiveMaxNum}"/>
												 <input type="hidden" isParam="true" name="tradeNum_${index.count}"  value="${actItem.tradeNum}"/>
												【<a href="javascript:void(0);" class="easyui-linkbutton" onclick="addActivitySetting.deleteItem(this);"  data-options="iconCls:'icon-remove', plain:'true'">取消</a>】
											</td>
										   </tr>
				                        </c:forEach>
				                       </c:if>
				                     </table>
								</td>
						  </tr>
				       </c:forEach>
				    </c:if>
				</tbody>
				<tbody>
				   <tr>
				     <td colspan="6" align="center">【 <a href="javascript:void(0);" class="easyui-linkbutton" onclick="addActivitySetting.addTaskItem(${addInitStartNum+1});"
				     data-options="iconCls:'icon-add', plain:'true'">添加任务项</a>】
				      </td>
				   </tr>
				</tbody>
		  </table>
         </form>
		 <table class="commonTable" style="display:none">
		      <tbody id="taskItem" style="display:none">
						   <tr id="tr1__#_">
						       <td colspan="6" align="right">&nbsp;【<a href="javascript:void(0);" class="easyui-linkbutton" onclick="addActivitySetting.deleteTaskItem(_#_);"
				     data-options="iconCls:'icon-remove', plain:'true'">删除任务项</a>】</td>
						   </tr>
						   <tr id="tr2__#_">
								<th>任务<span class="spanRed">*</span></th>
								<td colspan="5">
									<select id="taskItemCode__#_" name="taskItemCode" style="width: 250px;" curNum="_#_" onchange="addActivitySetting.switchTask(_#_)" >
										<option value="" unitStr="" demo="" disableCode="">--请选择--</option>
										<c:forEach items="${rwActityTaskTypes}" var="t">
										   <option value="${t.code}" title="${t.name}" unitStr="${t.unitStr}"  unitValidTypeStr="${t.unitValidTypeStr}" itemUnitStr="${t.itemUnitStr}" demo="${t.demo}" outCode="${t.outCode}"  isReceiveMaxNum="${t.isReceiveMaxNum}"  isReceiveMaxMoney="${t.isReceiveMaxMoney}">${t.name}</option>
										 </c:forEach>
									</select>
									<span id="taskItemValSpan__#_" style="display:none">
									   <input style="display:none" type="text" id="temp_taskItemVal__#_" curNum="_#_" name="temp_taskItemVal"    onblur="addActivitySetting.setNameParamValue('taskItemVal',this);" class="easyui-validatebox" data-options="required:false,missingMessage:'请输入'"  style="width: 120px;" maxlength="10"  value=""/>
									</span>
									<span id="taskItemUnitStrSpan__#_" style="display:none">
									</span>
									<span id="taskItemTimeSpan__#_" style="display:none">
									   <input style="display:none" type="text" id="temp_taskItemTime__#_" curNum="_#_" name="temp_taskItemTime"    onblur="addActivitySetting.setNameParamValue('taskItemTime',this);" class="easyui-validatebox" data-options="required:false,missingMessage:'请输入'"  style="width: 120px;" maxlength="10"  value=""/> 小时
									</span>
								    <input type="hidden" id="taskItemVal__#_" name="taskItemVal"  value=""/>
								     <input type="hidden" id="taskItemTime__#_" name="taskItemTime"  value=""/>
								    <span id="taskItemTip__#_" style="display:none" class="spanRed">任务已重复,请检查！</span>
								    <span id="taskItemTip2__#_" style="display:none" class="spanRed">请先选择任务！</span>
								    <span id="taskItemTip3__#_" style="display:none" class="spanRed">不符合参与条件的账号类型，请检查！</span>
								    <span id="taskItemTip4__#_" style="display:none" class="spanRed"></span>
								</td>
								 
						  </tr>
						  <tr id="tr4__#_">
							<th>任务描述</th>
							<td colspan="5">
						      <input type="text" id="taskDesc__#_" name="taskDesc" style="width: 300px;" class="easyui-validatebox"  data-options="required:false,validType: 'length[0,100]'" />
							</td>
						  </tr>
						  <tr id="tr3__#_">
								<th>奖励物品<span class="spanRed">*</span></th>
								<td colspan="5">
				                     <table id="items__#_" style="width:100%">
				                        <tr><td>
				                                                                      【<a href="javascript:void(0);" class="easyui-linkbutton" onclick="addActivitySetting.addItem(_#_);"
				                           data-options="iconCls:'icon-add', plain:'true'">添加物品</a>】
				                          <span id="addItemCodeTip__#_" style="display:none;font-size: 1" class="spanRed" >奖励物品存在重复,请检查！</span>
				                           <span id="noAddItemCodeTip__#_" style="display:none;font-size: 1" class="spanRed">请添加奖励物品！</span>
				                       </td></tr>
				                     </table>
								</td>
						  </tr>
			    </tbody>
			     <tbody id="addItem" style="display:none">
					  <tr name="items_tr__#_">
							<td>
								<span style="font-size: 1">物品:</span>
								<select   name="addItemCode__#_" onchange="addActivitySetting.switchItem(this,_#_)">
								     <option value="">--请选择--</option>
								      <c:forEach items="${actItems}"  var="item">
								      <c:if test="${item.effectiveItem}">
								        <option value="${item.giftNumber}" giftType="${item.giftType}">${item.giftName}</option>
								      </c:if>
								  </c:forEach>
								</select>
								<span  name="itemParamVarTip__#_" style="display:none" >请选择物品!</span>
								<span isParam="true" name="itemParamVar__#_" style="display:none">
								  <input type="text" name="tempItemParamVar__#_"     onblur="addActivitySetting.setNameParamValue('itemParamVar__#_',this);"  maxlength="10" value="0"/>
								</span>
								<span isParam="true" name="itemUnitStr__#_" style="display:none"></span>
								<span isParam="true"  name="receiveMaxMoneySpan__#_" style="font-size: 1;display:none" >最高领取金额:
								    <input type="text" name="tempReceiveMaxMoney__#_"     onblur="addActivitySetting.setNameParamValue('receiveMaxMoney__#_',this);" maxlength="10" value=""/>&nbsp;元
								</span>
								<span isParam="true"  name="receiveMaxNumSpan__#_"   style="font-size: 1;display:none">最高领取次数:
								    <input type="text" name="tempReceiveMaxNum__#_"    onblur="addActivitySetting.setNameParamValue('receiveMaxNum__#_',this);"  maxlength="10" value=""/>&nbsp;次
								</span>
								<span isParam="true"  name="tradeNumSpan__#_" style="font-size: 1;display:none" >交易手数:
								    <input type="text" name="tempTradeNum__#_"     onblur="addActivitySetting.setNameParamValue('tradeNum__#_',this);"   maxlength="10" value=""/>&nbsp;手
								</span>
								<input type="hidden"  isParam="true" name="itemParamVar__#_"  value=""/>
								<input type="hidden"  isParam="true"  name="receiveMaxMoney__#_"  value=""/>
								<input type="hidden"  isParam="true" name="receiveMaxNum__#_" value=""/>
								<input type="hidden" isParam="true" name="tradeNum__#_"  value=""/>
								【<a href="javascript:void(0);" class="easyui-linkbutton" onclick="addActivitySetting.deleteItem(this);"  data-options="iconCls:'icon-remove', plain:'true'">取消</a>】
							</td>
						</tr>
			    </tbody>
		  </table>
	</div>
	<div id="tab-tools-open-onestep">
		<a href="javascript:void(0);" class="easyui-linkbutton" id="saveBtn" data-options="iconCls:'icon-save'"  onclick="addActivitySetting.save()"><spring:message code="common.btn.submit"/><!--提交--></a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"  onclick="addActivitySetting.back()"><spring:message code="common.return"/><!--返回--></a>
	</div>
</body>
</html>