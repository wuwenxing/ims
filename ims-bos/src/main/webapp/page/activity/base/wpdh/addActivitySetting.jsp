<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/activity/base/wpdh/addActivitySetting.js" charset="UTF-8"></script>
</head>
<body >
 	<div class="easyui-tabs" id="addActSetting" data-options="tools:'#tab-tools-open-onestep'" style="height:10000">
		<form id="addDlogForm" class="commonForm" method="post">
			<input type="hidden" name="id" id="addActivitySettingId">
			<table class="commonTable">
			 <tbody id="table_activity_baseInfo">
			     <tr>
					<td colspan="6" align="center" height="40px"><b>活动基础设置</b></td>
				 </tr>
				<tr>
					<th>活动编号<span class="spanRed">*</span></th>
					<td>
					 <input type="hidden" name="activityType" value="${activityType}"/>
					<input	type="text" name="activityPeriods" id="activityPeriods" style="width: 380px;" value="${defaultActCode}" class="easyui-validatebox" 
						data-options="required:true,validType: 'length[0,100]'" />
					</td>
					<th>活动名称<span class="spanRed">*</span></th>
					<td>
						<input	type="text" name="activityName" style="width: 380px;" value="" class="easyui-validatebox" 
						data-options="required:true,validType: 'length[0,100]'" />
					</td>
					<th>活动状态</th>
					<td>
						<select id="enableFlag" name="enableFlag"  style="width: 160px;" class="easyui-combobox easyui-validatebox" data-options="validType:'selectValueRequired[\'#enableFlag\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
							<option value="">---请选择---</option>
							<option value="Y" selected>启动</option>
							<option value="N" >禁用</option>
						</select>
					</td>
				</tr>
				<tr>
			    	<th>活动开始时间<span class="spanRed">*</span></th>
					<td>
						<input class="easyui-datetimebox" type="text" name="startTime" id="startTimeStr" data-options="onShowPanel:function(){var vstr = $(this).datetimebox('getValue'); if(vstr==''){$(this).datetimebox('spinner').timespinner('setValue','00:00:00');}},required:true,validType:'compareToNowAndEnd[\'\']',missingMessage:'请选择活动开始时间',editable:false"/>
					</td>
					<th>活动结束时间<span class="spanRed">*</span></th>
					<td>
						<input class="easyui-datetimebox" type="text" name="endTime" id="endTimeStr" data-options="onShowPanel:function(){var vstr = $(this).datetimebox('getValue'); if(vstr==''){$(this).datetimebox('spinner').timespinner('setValue','23:59:59');}},required:true,validType:'compareToStart[\'startTimeStr\']',missingMessage:'请选择活动结束时间',editable:false"/>
					</td>
					<th>模拟账号保留金</th>
					<td>
						<input class="easyui-numberbox" type="text" name="demoKeepAmount" id="demoKeepAmount" precision="2"  min="0.00" max="100000000" value="0.0" data-options="min:0,precision:2,required:true,missingMessage:'请输入'"/>元
					</td>
				</tr>
                 <tr>
					<th>最高兑换次数<span class="spanRed">*</span></th>
					<td colspan="5">
						<input class="easyui-numberbox" type="text" name="maxExchangeCount" id="maxExchangeCount" maxlength="5"   data-options="min:0,required:true,missingMessage:'请输入'"/>次
					    <input  type="hidden"  name="autoHandOut"  value="1"/>
					</td>
				</tr>
				<tr>
					<th>活动链接地址</th> 
					<td colspan="5">
					 <textarea name="activityUrl" id="activityUrl" maxlength="500" rows="30" cols="80"  style="width: 600px"  ></textarea></td>
				</tr>
			   </tbody>
			    <tbody id="table_activity_condition">
			          <tr>
					<td colspan="6" align="center"  height="40px"><b>参与活动条件</b></td>
				 </tr>
				<tr>
					<th>账号类型<span class="spanRed">*</span></th>
					<td>
						<select id="accountType" name="custActCondition.accountType" class="easyui-combobox" data-options="validType:'selectValueRequired[\'#accountType\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
								<option value=""><spring:message code="common.search.selectoption"/></option>
								<bos:dictListTag  dataList="${ActAccountType}" />
						</select>
					</td>
					<th>账号唯一性<span class="spanRed">*</span></th>
					<td>
					<input type="hidden"    name="custActCondition.accountOnly" value="${custActCondition.accountOnly}"/>
						<select id="accountOnly"  disabled="disabled" style="width:160px;" class="easyui-combobox easyui-validatebox" data-options="validType:'selectValueRequired[\'#accountOnly\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
							<option value="1" >是</option>
							<option value="0" selected>否</option>
						</select>
					</td>
					<th>账号级别<span id="accountLevelSpan" class="spanRed">*</span></th>
					<td>
						 <c:forEach var="aLevel" items="${ActAccountLevel}" >
					      <input type="checkbox" name="custActCondition.accountLevel" value="${aLevel.dictCode}"/>${aLevel.dictName}
					     </c:forEach>
					      <span id="accountLevelDA" style="display:none" class="spanRed">请选择账号级别</span>
					</td>
				</tr>
				 <tr>
					<th>注销过账号参加活动<span class="spanRed">*</span></th>
					<td>
					    <input type="hidden"   id="allowCancelAccount" name="custActCondition.allowCancelAccount" value="0"/>
						<select  disabled="disabled" style="width:160px;" class="easyui-combobox easyui-validatebox" data-options="validType:'selectValueRequired[\'#allowCancelAccount\']', invalidMessage:'请选择', panelHeight:'auto', editable:false">
							<option value="0" selected>否</option>
							<option value="1" >是</option>
						</select>
					</td>
					<th>参与活动的平台<span class="spanRed">*</span></th>
					<td>
					   <c:forEach var="pform" items="${ActPlatformCcy}" >
					      <input type="checkbox" name="custActCondition.platformsCcy" value="${pform.dictCode}"/>${pform.dictName}
					   </c:forEach>
					   <span id="platformsDA" style="display:none" class="spanRed">请选择参与活动的平台</span>
					</td>
					<th>交易产品</th>
					<td>
				    	 <select id="custActConditionItems" name="custActCondition.items" class="easyui-combobox" data-options="" style="width: 400;height: 80" >  
				    	</select>
				    	<a href="javascript:void(0);" class="easyui-linkbutton" data-options=""  onclick="addActivitySetting.allCheckCombobox(true)">全选</a> 
				    	<a href="javascript:void(0);" class="easyui-linkbutton" data-options=""  onclick="addActivitySetting.allCheckCombobox(false)">取消全选</a>
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
				</tbody>
				<tbody>
				   <tr>
				     <td colspan="6" align="center">【 <a href="javascript:void(0);" class="easyui-linkbutton" onclick="addActivitySetting.addTaskItem();"
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
									<select id="taskItemCode__#_" name="taskItemCode" style="width: 160px;" curNum="_#_" data-options="" onchange="addActivitySetting.switchTask(_#_)" >
										<option value="" unitStr="" demo="" disableCode="">--请选择--</option>
										<option value="add_demo_account"  demo="true">注册模拟账号</option>
										<option value="demo_total_close_times"   demo="true">模拟账号累计平仓次数</option>
									</select>
									<span id="taskItemValSpan__#_" style="display:none">
									   <input style="display:none" type="text" id="temp_taskItemVal__#_" curNum="_#_" name="temp_taskItemVal"   onkeyup="common.allowNumDecimal2KeyUp(this)" onblur="addActivitySetting.setNameParamValue('taskItemVal',this);"  class="easyui-validatebox"   style="width: 120px;" maxlength="10"  value=""/>次
									</span>
								    <input type="hidden" id="taskItemVal__#_" name="taskItemVal"  value=""/>
								    <span id="taskItemTip__#_" style="display:none" class="spanRed">任务已重复,请检查！</span>
									<span id="taskItemTip2__#_" style="display:none" class="spanRed">请先选择任务！</span>
									<span id="taskItemTip3__#_" style="display:none" class="spanRed">不符合参与条件的账号类型，请检查！</span>
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
				                        <tr id="items_tr__#_"><td>
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
								<select   name="addItemCode__#_"   onchange="addActivitySetting.switchItem(this,_#_)">
								  <option value="" selected="selected">请选择</option>
								  <c:forEach items="${actItems}"  var="item">
								    <c:if test="${item.effectiveItem}">
								       <option value="${item.giftNumber}">${item.giftName}</option>
								     </c:if>
								  </c:forEach>
								</select>
								<span style="font-size: 1">等额值:</span><input type="text" name="addItemEq__#_"     data-options="required:false,validType: 'zeroDecimalTwo'"   maxlength="10" value="0"/>
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