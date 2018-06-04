<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ims" uri="/ims-tags"%>
<%
	String path_left = request.getContextPath();
	String basePath_left = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path_left + "/";
%>
<html>
<head>
</head>
<body>
	<!-- 左侧菜单 -->
	<div class="main-sidebar">
		<div class="left-side">
			
<!-- 			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true"> -->
<%-- 				<c:forEach var="record" items="${treeBeanList}"> --%>
<%-- 					<c:set var="menuIcon" value="glyphicon glyphicon-plus"></c:set> --%>
<%-- 					<c:if test="${record.text eq '系统管理' }"> --%>
<%-- 						<c:set var="menuIcon" value="glyphicon glyphicon-cog"></c:set> --%>
<%-- 					</c:if> --%>
<%-- 					<c:if test="${record.text eq '基础设置' }"> --%>
<%-- 						<c:set var="menuIcon" value="glyphicon glyphicon-wrench"></c:set> --%>
<%-- 					</c:if> --%>
<!-- 						<div class="panel panel-default"> -->
<%-- 							<div class="panel-heading" role="tab" id="#menuHeading_${record.id}"> --%>
<!-- 								<h4 class="panel-title"> -->
<!-- 									<a class="" role="button" data-toggle="collapse" data-parent="#accordion" -->
<%-- 										href="#menu_${record.id}" aria-expanded="true" --%>
<%-- 										aria-controls="menu_${record.id}" > --%>
<%-- 										<span class="${menuIcon}"></span><span class="nav-text">${record.text}</span> --%>
<!-- 									</a> -->
<!-- 								</h4> -->
<!-- 							</div> -->
<%-- 							<div id="menu_${record.id}" class="panel-collapse collapse in" --%>
<%-- 								role="tabpanel" aria-labelledby="menuHeading_${record.id}" --%>
<!-- 								aria-expanded="true" style=""> -->
<!-- 								<ul class="list-group"> -->
<%-- 									<c:forEach var="subRecord" items="${record.children}"> --%>
<%-- 										<c:set var="subMenuIcon" value="glyphicon glyphicon-file"></c:set> --%>
<%-- 										<c:if test="${subRecord.attributes.url eq 'SystemUserController/page' }"> --%>
<%-- 											<c:set var="subMenuIcon" value="glyphicon glyphicon-user"></c:set> --%>
<%-- 										</c:if> --%>
<%-- 										<c:if test="${subRecord.attributes.url eq 'SystemMenuController/page' }"> --%>
<%-- 											<c:set var="subMenuIcon" value="glyphicon glyphicon-list-alt"></c:set> --%>
<%-- 										</c:if> --%>
<%-- 										<c:if test="${subRecord.attributes.url eq 'SystemLogController/page' }"> --%>
<%-- 											<c:set var="subMenuIcon" value="glyphicon glyphicon-file"></c:set> --%>
<%-- 										</c:if> --%>
<%-- 										<c:if test="${subRecord.attributes.url eq 'SystemDictController/page' }"> --%>
<%-- 											<c:set var="subMenuIcon" value="glyphicon glyphicon-file"></c:set> --%>
<%-- 										</c:if> --%>
										
<%-- 										<li class="list-group-item" onclick="index.addTabs('${subRecord.text}', '${subRecord.attributes.url}');"> --%>
<%-- 											<a href="javascript:void(0);"><span class="${subMenuIcon}" style="padding-left: 14px;"></span><span class="nav-text">${subRecord.text}</span></a> --%>
<!-- 										</li> -->
<%-- 									</c:forEach> --%>
<!-- 								</ul> -->
<!-- 							</div> -->
<!-- 						</div> -->
<%-- 				</c:forEach> --%>
<!-- 			</div> -->
			
			<ul class="nav nav-pills nav-stacked">
				<c:forEach var="record" items="${treeBeanList}">
					
					<c:set var="menuIcon" value="glyphicon glyphicon-plus"></c:set>
					<c:if test="${record.text eq '系统管理' }">
						<c:set var="menuIcon" value="glyphicon glyphicon-cog"></c:set>
					</c:if>
					<c:if test="${record.text eq '基础设置' }">
						<c:set var="menuIcon" value="glyphicon glyphicon-wrench"></c:set>
					</c:if>
					<li onclick="javascript:void(0);" class="leftMenu">
						<a href="#menu_${record.id}" class="nav-header" data-toggle="collapse">
							<span class="${menuIcon}"></span><span class="nav-text">${record.text}</span>
						</a>
						<ul id="menu_${record.id}" class="nav nav-list collapse" >
							<c:forEach var="subRecord" items="${record.children}">
								
								<c:set var="subMenuIcon" value="glyphicon glyphicon-file"></c:set>
								<c:if test="${subRecord.attributes.url eq 'SystemUserController/page' }">
									<c:set var="subMenuIcon" value="glyphicon glyphicon-user"></c:set>
								</c:if>
								<c:if test="${subRecord.attributes.url eq 'SystemMenuController/page' }">
									<c:set var="subMenuIcon" value="glyphicon glyphicon-list-alt"></c:set>
								</c:if>
								<c:if test="${subRecord.attributes.url eq 'SystemLogController/page' }">
									<c:set var="subMenuIcon" value="glyphicon glyphicon-file"></c:set>
								</c:if>
								<c:if test="${subRecord.attributes.url eq 'SystemDictController/page' }">
									<c:set var="subMenuIcon" value="glyphicon glyphicon-file"></c:set>
								</c:if>
								
								<li onclick="index.addTabs('${subRecord.text}', '${subRecord.attributes.url}');" class="leftSubMenu">
									<a href="javascript:void(0);"><span class="${subMenuIcon}" style="padding-left: 14px;"></span><span class="nav-text">${subRecord.text}</span></a>
								</li>
							</c:forEach>
						</ul>
					</li>
				</c:forEach>
			</ul>
			
		</div>
		
		
		<div class="right-side">
			<div id="menu_tabs" class="easyui-tabs" data-options="fit:false, plain:true, onClose: function(){index.closeTabsEvent();}"></div>
			<div id="welcome" style=" font-weight: bold; min-height: 768px; height: 30px; line-height: 30px; padding-left: 10px;">${client.user.userName }，您好，欢迎您!</div>
		</div>
	</div>

</body>
</html>