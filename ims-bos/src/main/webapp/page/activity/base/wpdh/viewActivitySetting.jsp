<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/activity/base/wpdh/viewActivitySetting.js" charset="UTF-8"></script>
</head>
<body>
 	<div class="easyui-tabs" id="addActSetting" data-options="tools:'#tab-tools-open-onestep'" style="height:10000">
		<form id="addDlogForm" class="commonForm" method="post">
			<input type="hidden" name="id" id="addActivitySettingId">
			<table class="commonTable">
			 <tbody id="table_activity_baseInfo">
			     <tr>
					<td colspan="6" align="center" height="40px"><b>活动基础设置</b></td>
				 </tr>
				<tr>
				    <th>活动类型<span class="spanRed">*</span></th>
					<td>
						${actSetting.activityTypeStr}
					</td>
					<th>活动编号<span class="spanRed">*</span></th>
					<td>
						${actSetting.activityPeriods}
					</td>
					
					<th>活动名称<span class="spanRed">*</span></th>
					<td>
						${actSetting.activityName}
					</td>
				</tr>
				<tr>
			    	<th>活动开始时间<span class="spanRed">*</span></th>
					<td>
						<fmt:formatDate value='${actSetting.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
					</td>
					<th>活动结束时间<span class="spanRed">*</span></th>
					<td>
					<fmt:formatDate value='${actSetting.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
					</td>
					<th>模拟账号保留金额</th>
					<td>
						${actSetting.demoKeepAmount}元
					</td>
				</tr>
                 <tr>
					<th>最高兑换次数</th>
					<td>
						${actSetting.maxExchangeCount}次
					</td>
					<th>活动状态</th>
					<td colspan="3">
					   ${actSetting.enableFlag}
					</td>
				</tr>
				<tr>
					<th>活动链接地址</th> 
					<td>
					${actSetting.activityUrl}
					<th>其他信息</th> 
					<td colspan="3" >
					   ${actSetting.otherMsg}
					</td>
				</tr>
			   </tbody>
				   <tbody id="table_activity_condition">
				          <tr>
						<td colspan="6" align="center"  height="40px"><b>参与活动条件</b></td>
					</tr>
					<tr>
					    <c:set var="custActCondition" value="${actSetting.custActCondition.conditionValObj}"/>
						<th>账号类型<span class="spanRed">*</span></th>
						<td>
							${custActCondition.accountType}
						</td>
						<th>账号唯一性<span class="spanRed">*</span></th>
						<td>
						   <c:choose>
						      <c:when test="${custActCondition.accountOnly}">是
						      </c:when>
						      <c:otherwise>否
						      </c:otherwise>
						   </c:choose>
						</td>
						<th>账号级别<span class="spanRed">*</span></th>
						<td>
						    ${custActCondition.accountLevel}
						</td>
					</tr>
					 <tr>
						<th>注销过账号参加活动<span class="spanRed">*</span></th>
						<td>
						<c:choose>
						      <c:when test="${custActCondition.allowCancelAccount}">是
						      </c:when>
						      <c:otherwise>否
						      </c:otherwise>
						   </c:choose>
						</td>
						<th>参与活动的平台<span class="spanRed">*</span></th>
						<td  colspan="3">
						    ${custActCondition.platformsCcy}
						</td>
					  </tr>
					  <tr>
					  <th>交易产品</th>
						<td colspan="5">
						    ${custActCondition.items}
						</td>
					  </tr>
				    </tbody>
			</table>
		</form>
		<c:if test="${not empty viewActAllSettingInfoFlag && viewActAllSettingInfoFlag}">
			<form id="addTaskForm" name="addTaskForm" class="commonForm" method="post">
			 <table class="commonTable">
			       <tbody id="table_activity_task">
				        <tr>
						  <td colspan="6" align="center"><b>任务与奖励</b></td>
					    </tr>
					    <c:choose>
					    
					    <c:when test="${not empty actSetting.actTaskSettings}">
					       <c:forEach var="actTask" items="${actSetting.actTaskSettings}" varStatus="index">
							   <tr>
									<th>任务<span class="spanRed">*</span></th>
									<td colspan="5">
									   <c:choose>
									      <c:when test="${'add_demo_account'==actTask.taskItemCode}">
									                          注册模拟账号
									      </c:when>
									       <c:when test="${'demo_total_close_times'==actTask.taskItemCode}">
									                        模拟账号累计平仓次数:${actTask.taskItemVal}次
									      </c:when>
									   </c:choose>
									</td>								 
							  </tr>
							   <tr>
								<th>任务描述</th>
								<td colspan="5">
								  ${actTask.taskDesc}
								</td>
							  </tr>
							  <tr>
									<th>奖励物品<span class="spanRed">*</span></th>
									<td colspan="5">
					                     <table id="items_${index.count}" style="width:100%">
					                       <c:if test="${not empty actTask.actTaskItems}">
					                        <c:forEach var="actItem" items="${actTask.actTaskItems}">
					                           <tr>
												<td>
													<span style="font-size: 1">物品:${actItem.itemName},等额值：${actItem.equalValue}</span>
												</td>
											   </tr>
					                        </c:forEach>
					                       </c:if>
					                     </table>
									</td>
							  </tr>
							  <tr><td colspan="6">&nbsp;</td></tr>
					       </c:forEach>
					    </c:when>
					    <c:otherwise>
					     <tr><td colspan="6">无</td></tr>
					    </c:otherwise>
					    </c:choose>
					</tbody>
					 
			  </table>
	         </form>
		 </c:if>
		    
	</div>
	<div id="tab-tools-open-onestep">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"  onclick="viewActivitySetting.back()"><spring:message code="common.return"/><!--返回--></a>
	</div>
</body>
</html>