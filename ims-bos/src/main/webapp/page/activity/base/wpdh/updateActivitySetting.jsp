<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/activity/base/wpdh/updateActivitySetting.js" charset="UTF-8"></script>
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
				    <th>活动类型<span class="spanRed">*</span></th>
					<td>
						<select id="activityType" name="activityType" class="easyui-combobox"  disabled="disabled"  data-options="validType:'selectValueRequired[\'#activityType\']', invalidMessage:'请选择活动类型', panelHeight:'auto', editable:false">
				        		<option value=""><spring:message code="common.search.selectoption"/></option>
								<bos:dictListTag  dataList="${ActivityType}" defaultVal="${actSetting.activityType}" />
						</select>
					</td>
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
				</tr>
				<tr>
			    	<th>活动开始时间<span class="spanRed">*</span></th>
					<td><input type="hidden" id="initStartTimeStr" value="<fmt:formatDate value='${actSetting.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
						<input class="easyui-datetimebox" type="text" name="startTime" id="startTimeStr"  <c:if test="${isActStart}">disabled="disabled"</c:if>  data-options="onShowPanel:function(){var vstr = $(this).datetimebox('getValue'); if(vstr==''){$(this).datetimebox('spinner').timespinner('setValue','00:00:00');}},required:true,validType:'compareToEnd[\'initEndTimeStr\']',missingMessage:'请选择活动开始时间',editable:false"/>
					</td>
					<th>活动结束时间<span class="spanRed">*</span></th>
					<td><input type="hidden" id="initEndTimeStr" value="<fmt:formatDate value='${actSetting.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
						<input class="easyui-datetimebox" type="text" name="endTime" id="endTimeStr"  data-options="onShowPanel:function(){var vstr = $(this).datetimebox('getValue'); if(vstr==''){$(this).datetimebox('spinner').timespinner('setValue','23:59:59');}},required:true,validType:'compareToStart[\'startTimeStr\']',missingMessage:'请选择活动结束时间',editable:false"/>
					</td>
					<th>模拟账号保留金额<span class="spanRed">*</span></th>
					<td>
						<input class="easyui-numberbox" type="text" name="demoKeepAmount" id="demoKeepAmount" value="${actSetting.demoKeepAmount}"  precision="2"  min="0.00" max="100000000" data-options="min:0,precision:2,required:true,missingMessage:'请输入'"/>元
					</td>
				</tr>
                 <tr>
					<th>最高兑换次数<span class="spanRed">*</span></th>
					<td>
						<input class="easyui-numberbox" type="text" name="maxExchangeCount" id="maxExchangeCount" value="${actSetting.maxExchangeCount}" maxlength="5" data-options="min:0,required:true,missingMessage:'请输入'" />次
				    	<input  type="hidden"  name="autoHandOut"  value="1"/>
					</td>
					<th>活动状态</th>
					<td colspan="3">
						<select id="enableFlag" name="enableFlag"  style="width: 160px;" class="easyui-combobox easyui-validatebox" data-options="validType:'selectValueRequired[\'#enableFlag\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
							<option value="">---请选择---</option>
							<option value="Y" <c:if test="${actSetting.enableFlag=='Y'}">selected</c:if>>启动</option>
							<option value="N"  <c:if test="${actSetting.enableFlag=='N'}">selected</c:if>>禁用</option>
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
					    
					</td>
				 </tr>
				<tr>
				    <c:set var="custActCondition" value="${actSetting.custActCondition.conditionValObj}"/>
				    <input type="hidden" name="custActCondition.actConditionSettingId" value="${actSetting.custActCondition.id}"/>
					<th>账号类型<span class="spanRed">*</span></th>
					<td>
					<c:if test="${isActStart}"><input type="hidden"    name="custActCondition.accountType" value="${custActCondition.accountType}"/></c:if>
						<select id="accountType" <c:choose><c:when test="${isActStart}">disabled="disabled"</c:when><c:otherwise>name="custActCondition.accountType"</c:otherwise></c:choose> class="easyui-combobox" data-options="validType:'selectValueRequired[\'#accountType\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
								<option value=""><spring:message code="common.search.selectoption"/></option>
								<bos:dictListTag  dataList="${ActAccountType}" defaultVal="${custActCondition.accountType}"/>
						</select>
					</td>
					<th>账号唯一性<span class="spanRed">*</span></th>
					<td>
					   <input type="hidden"    name="custActCondition.accountOnly" value="${custActCondition.accountOnly}"/>
						<select id="accountOnly" disabled="disabled"   style="width:160px;" class="easyui-combobox easyui-validatebox" data-options="validType:'selectValueRequired[\'#accountOnly\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
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
					      <input type="checkbox" <c:choose><c:when test="${isActStart}">disabled="disabled"</c:when><c:otherwise>name="custActCondition.accountLevel"</c:otherwise></c:choose> <c:if test="${custActCondition.accountType=='type_demo'}">disabled="disabled"</c:if> name="custActCondition.accountLevel" value="${aLevel.dictCode}" <c:if test="${fn:contains(accountLevel,dictCode)}">checked="checked"</c:if>/>${aLevel.dictName}
					     </c:forEach>
					      <span id="accountLevelDA" style="display:none" class="spanRed">请选择账号级别</span>
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
					<th>交易产品</th>
					<td>
					    <c:if test="${isActStart}">
						   <input type="hidden" name="custActCondition.items" value="${custActCondition.items}"/>
					   </c:if>
					     <input type="hidden" id="custActConditionItemsValue" value="${custActCondition.items}"/>
				    	 <select id="custActConditionItems" <c:choose><c:when test="${isActStart}">disabled="disabled"</c:when><c:otherwise>name="custActCondition.items"</c:otherwise></c:choose> class="easyui-combobox"   data-options="" style="width: 400;height: 80" >  
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
								     <c:set var="isSelectDemoCloseTimes" value="false"/><!-- 是否选中的是模拟账号累计平仓次数 -->
								    <c:if test="${actTask.taskItemCode=='demo_total_close_times'}">
								       <c:set var="isSelectDemoCloseTimes" value="true"/>
								    </c:if>
									<select id="taskItemCode_${index.count}" name="taskItemCode" style="width: 160px;" curNum="${index.count}" class="easyui-validatebox" data-options="required:false" data-options="" onchange="addActivitySetting.switchTask(${index.count})" >
										<option value="add_demo_account" <c:if test="${actTask.taskItemCode=='add_demo_account'}">selected</c:if>>注册模拟账号</option>
										<option value="demo_total_close_times" <c:if test="${actTask.taskItemCode=='demo_total_close_times'}">selected</c:if>>模拟账号累计平仓次数</option>
									</select>
									<span id="taskItemValSpan_${index.count}" <c:if test="${!isSelectDemoCloseTimes}"> style="display:none"</c:if>>
									   <input <c:if test="${!isSelectDemoCloseTimes}"> style="display:none"</c:if> 
									   type="text" id="temp_taskItemVal_${index.count}" curNum="${index.count}" name="temp_taskItemVal"  onblur="addActivitySetting.setNameParamValue('taskItemVal',this);" class="easyui-validatebox" data-options="required:${isSelectDemoCloseTimes},validType: 'int',missingMessage:'请输入'"  style="width: 120px;" maxlength="10"  value="${actTask.taskItemVal }"/>次
									</span>
								    <input type="hidden" id="taskItemVal_${index.count}" name="taskItemVal"  value="${actTask.taskItemVal}"/>
								    <span id="taskItemTip_${index.count}" style="display:none" class="spanRed">任务已重复,请检查！</span>
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
												<select  name="addItemCode_${index.count}"  onchange="addActivitySetting.switchItem(this,_#_)">
												  <c:forEach items="${actItems}"  var="item">
												    <c:if test="${actItem.itemCode==item.giftNumber || item.effectiveItem}">
												        <option value="${item.giftNumber}" <c:if test="${actItem.itemCode==item.giftNumber}">selected</c:if>>${item.giftName}</option>
												     </c:if>
												  </c:forEach>
												</select>
												<span>等额值:</span><input type="text" name="addItemEq_${index.count}"  value="${actItem.equalValue}"    class="easyui-validatebox" data-options="required:true,validType: 'zeroDecimalTwo'"  maxlength="10" />
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
								    <input type="hidden" name="taskItemSettingId" value=""/>
									<select id="taskItemCode__#_" name="taskItemCode" style="width: 160px;" curNum="_#_" onchange="addActivitySetting.switchTask(_#_)" >
										<option value="add_demo_account" selected>注册模拟账号</option>
										<option value="demo_total_close_times" >模拟账号累计平仓次数</option>
									</select>
									<span id="taskItemValSpan__#_" style="display:none">
									   <input style="display:none" type="text" id="temp_taskItemVal__#_" curNum="_#_" name="temp_taskItemVal"  onblur="addActivitySetting.setNameParamValue('taskItemVal',this);" class="easyui-validatebox" data-options="required:false"  style="width: 120px;" maxlength="10"  value=""/>次
									</span>
								    <input type="hidden" id="taskItemVal__#_" name="taskItemVal"  value=""/>
								    <span id="taskItemTip__#_" style="display:none" class="spanRed">任务已重复,请检查！</span>
								</td>
								 
						  </tr>
						  <tr id="tr4__#_">
							<th>任务描述</th>
							<td colspan="5">
						      <input type="text" id="taskDesc__#_" name="taskDesc" style="width: 300px;" class="easyui-validatebox"  data-options="required:false,validType: 'length[0,100]'" />
							</td>
						  </tr>
						  <tr id="tr3__#_">
								<th>奖励物品</th>
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
								<input type="hidden" name="addItemId__#_" value=""/>
								<select   name="addItemCode__#_"  onchange="addActivitySetting.switchItem(this,_#_)">
								<option value=""  selected="selected">请选择</option>
								  <c:forEach items="${actItems}"  var="item">
								     <c:if test="${item.effectiveItem}">
								        <option value="${item.giftNumber}">${item.giftName}</option>
								     </c:if>
								  </c:forEach>
								</select>
								<span style="font-size: 1">等额值:</span><input type="text" name="addItemEq__#_"  data-options="required:false,validType: 'zeroDecimalTwo'"   value=""      maxlength="10" />
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