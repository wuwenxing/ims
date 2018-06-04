<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ims" uri="/ims-tags"%>
<%
	String path_top = request.getContextPath();
	String basePath_top = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path_top + "/";
%>
<script type="text/javascript" src="<%=basePath_top%>page/login/top.js?version=20170306"></script>
<html>
<head>
</head>
<body>

	<!-- 顶层导航 -->
	<div class="main-top">
		<div class="logo-container">金道活动平台</div>
		<div class="nav-container">
			<div class="navbar-header">
				<button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target="#bs-navbar" aria-controls="bs-navbar" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
			</div>
			<nav id="bs-navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
		         	<c:forEach var="record" items="${menuList}">
		         		
						<c:set var="menuIcon" value=""></c:set>
						<c:if test="${record.menuUrl eq 'LoginController/index/index' }">
							<c:set var="menuIcon" value="glyphicon glyphicon-home"></c:set>
						</c:if>
						<c:if test="${record.menuUrl eq 'LoginController/index/system' }">
							<c:set var="menuIcon" value="glyphicon glyphicon-cog"></c:set>
						</c:if>
						<c:if test="${record.menuUrl eq 'LoginController/index/home' }">
							<c:set var="menuIcon" value="glyphicon glyphicon-bullhorn"></c:set>
						</c:if>
						
						<li id="nav_${record.menuCode}" onclick="javascript:topObj.linkUrl('${record.menuUrl}', '${record.menuCode}');" class="${record.menuCode eq topTagMenuCode ? 'active':''} ">
							<a href="javascript:void(0);">
								<span class="${menuIcon}"></span><span class="nav-text">${record.menuName}</span>
							</a>
						</li>
					</c:forEach>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li onclick="javascript:void(0);">
						<a id="companyIdTabs" href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
					        <span class="glyphicon glyphicon-edit"></span><span class="nav-text">${client.companyEnum.value}</span>
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu" aria-labelledby="companyIdTabs">
							<c:forEach var="record" items="${companyEnum}">
								<li onclick="topObj.changeCompanyId('${record.labelKey}');"><a href="javascript:void(0);">${record.value }</a></li>
							</c:forEach>
						</ul>
					</li>
					<li onclick="javascript:topObj.changePassword();"><a
						href="javascript:void(0);"><span
							class="glyphicon glyphicon-edit"></span><span class="nav-text">修改密码</span></a></li>
					<li onclick="javascript:topObj.loginOut();"><a
						href="javascript:void(0);"><span
							class="glyphicon glyphicon-log-out"></span><span class="nav-text">退出</span></a></li>
<!-- 					<li onclick="javascript:void(0);"><a -->
<!-- 						href="javascript:void(0);"><span -->
<%-- 							class="glyphicon glyphicon-user"></span><span class="nav-text">${client.user.userNo}</span></a></li> --%>
				</ul>
			</nav>
		</div>
	</div>

	<!-- 修改密码 -->
	<div id="changePasswordDlogToolbar" style="display: none;">
		<a id="changePasswordDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-ok'" onclick="topObj.changePasswordSave();">确定</a>
		<a id="changePasswordDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-cancel'" onclick="javascript:$('#changePasswordDlog').dialog('close');">关闭</a>
	</div>
	<div id="changePasswordDlog" class="easyui-dialog" style="display: none;" data-options="closed:'true', buttons:'#changePasswordDlogToolbar', modal:true, bgiframe:true, resizable:true, width:400">
		<form id="changePasswordDlogForm" class="commonForm" method="post">
			<table class="commonTable">
				<tr>
					<th>账号</th>
					<td>${client.user.userNo }</td>
				</tr>
				<tr>
					<th>原密码<span class="spanRed">*</span></th>
					<td><input class="easyui-validatebox" type="password" name="oldPassword" id="oldPassword" data-options="required:true, validType:'length[0,20]'" /></td>
				</tr>
				<tr>
					<th>新密码<span class="spanRed">*</span></th>
					<td><input class="easyui-validatebox" type="password" name="newPassword" id="newPassword" data-options="required:true, validType:'length[0,20]'" /></td>
				</tr>
				<tr>
					<th>重新输入密码<span class="spanRed">*</span></th>
					<td><input class="easyui-validatebox" type="password" name="newPasswordAgin" id="newPasswordAgin" data-options="required:true, validType:'length[0,20]'" /></td>
				</tr>
			</table>
		</form>
	</div>
	
</body>
</html>