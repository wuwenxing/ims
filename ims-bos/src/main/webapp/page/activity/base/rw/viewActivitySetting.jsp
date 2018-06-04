<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/activity/base/rw/viewActivitySetting.js" charset="UTF-8"></script>
</head>
<body>
<iframe id="downloadframe"  style="display: none"></iframe>
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
					<th>活动完成时间</th>
					<td>
						${actSetting.finishDays}天
					</td>
				</tr>
                 <tr>
					<th>代币有效期</th> 
					<td>
						${actSetting.coinDays}天
 					</td>
					<th>活动状态</th>
					<td>
					   ${actSetting.enableFlag}
					</td>
					<th>发放方式</th> 
					<td>
					  <c:choose>
							<c:when test="${actSetting.autoHandOut==1}">自动发放</c:when>
							<c:otherwise>人工审核</c:otherwise>
				      </c:choose>
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
					<th>账号级别</th>
					<td>
					    ${custActCondition.accountLevel}
					</td>
				</tr>
				 <tr>
				   <th>参与活动的平台<span class="spanRed">*</span></th>
					<td>
					    ${custActCondition.platformsCcy}
					</td>
					<th>账号注册日期</th>
					<td colspan="3">
					  ${custActCondition.registerStartTime}
					        至
					   ${custActCondition.registerEndTime}
					</td>
				 </tr>
				<tr>
				 <th>只允许白名单用户参加活动</th>
					<td>
					 <c:choose>
					      <c:when test="${custActCondition.allowWhiteUsers}">是
					      </c:when>
					      <c:otherwise>否
					      </c:otherwise>
					   </c:choose>
					</td>
				    <th>上传白名单</th>
					<td>
					<a href="#"
								class="easyui-linkbutton" plain="true" iconCls="ope-save"
								onclick="return common.downloadFile('${custActCondition.whiteListFilePath}','${custActCondition.whiteListFileName}')"><span
									 >${custActCondition.whiteListFileName}</span></a>
					</td>
					<th>上传黑名单</th>
					<td>
					<a href="#"
								class="easyui-linkbutton" plain="true" iconCls="ope-save"
								onclick="return common.downloadFile('${custActCondition.blackListFilePath}','${custActCondition.blackListFileName}')"><span
									 >${custActCondition.blackListFileName}</span></a>
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
					<th>交易产品</th>
					<td colspan="3">
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
								  ${actTask.taskItemCode}<c:if test="${not empty actTask.taskItemTime &&actTask.taskItemTime>0}">&nbsp;(${actTask.taskItemTime}小时)</c:if>
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
												<span style="font-size: 1">物品:${actItem.itemName}
												<c:if test="${not empty actItem.receiveMaxMoney}">
											    	  &nbsp;最高领取金额：${actItem.receiveMaxMoney}元
												</c:if>
												<c:if test="${not empty actItem.receiveMaxNum}">
											    	  &nbsp;最高领取次数：${actItem.receiveMaxNum}次
												</c:if>
												  <c:if test="${not empty actItem.tradeNum}">
											    	 &nbsp; 交易手数：${actItem.tradeNum}手
												</c:if>
												</span>
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