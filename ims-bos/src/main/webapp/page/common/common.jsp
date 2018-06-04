<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ims" uri="/ims-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Long companyId = (Long)request.getSession().getAttribute("companyId");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- 对移动设备友好 -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>金道活动平台</title>

<base href="<%=basePath%>">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="das">
<meta http-equiv="description" content="das">
<link rel="shortcut icon" type="image/x-icon" href="<%=basePath%>img/favicon.ico" />
<!-- js全局变量 -->
<script type="text/javascript">
var BASE_PATH = '<%=basePath%>' + "";
var COMPANY_ID = '<%=companyId%>' + "";
</script>

<!-- jquery -->
<script type="text/javascript"
	src="<%=basePath%>third/jquery-2.1.1/jquery.min.js?version=20170306"></script>

<!-- jquery json -->
<script type="text/javascript"
	src="<%=basePath%>third/jquery_json_2.4/jquery.json-2.4.js?version=20170306"></script>

<!-- jquery_easy_ui -->
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>third/jquery-easyui-1.5.1/themes/bootstrap/easyui.css?version=20170306">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>third/jquery-easyui-1.5.1/themes/icon.css?version=20170306">
<script type="text/javascript"
	src="<%=basePath%>third/jquery-easyui-1.5.1/jquery.easyui.min.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>third/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js?version=20170306"></script>

<!-- jquery UI -->
<link type="text/css" rel="stylesheet" href="<%=basePath%>third/jqueryui/jquery-ui.css?version=20170306" />
<script type="text/javascript" src="<%=basePath%>third/jqueryui/jquery-ui-1.9.2.custom.min.js?version=20170306" charset="UTF-8"></script>

<!-- jquery multiselect -->
<link type="text/css" rel="stylesheet" href="<%=basePath%>third/multiselect/jquery.multiselect.css?version=20170306" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>third/multiselect/jquery.multiselect.filter.css?version=20170306" />
<script type="text/javascript" src="<%=basePath%>third/multiselect/src/jquery.multiselect.min.js?version=20170306" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>third/multiselect/src/jquery.multiselect.filter.min.js?version=20170306" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>third/multiselect/i18n/jquery.multiselect.zh-cn.js?version=20170306" charset="UTF-8"></script>

<!-- echarts -->
<script type="text/javascript" src="<%=basePath%>third/echarts/echarts.min.js?version=20170306"></script>
<script type="text/javascript" src="<%=basePath%>third/echarts/shine.js?version=20170306"></script>

<!-- common css js -->
<script type="text/javascript"
	src="<%=basePath%>js/common/formatDate.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>js/common/common.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>js/common/backspace.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>js/common/easyui.js?version=20170306"></script>
<script type="text/javascript"
	src="<%=basePath%>js/common/upload.js?version=20170306"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/common.css?version=20170306">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/easyui.css?version=20170306">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/multiselect.css?version=20170306">

	
	