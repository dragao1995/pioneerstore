<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>销售系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=path%>/js/swfobject.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
  </head>
  
  <body id="mainp" leftmargin="0" rightmargin="0" topmargin="0" bottommargin="0">
    
  </body>
</html>
<script type="text/javascript">
<!--
	var so = new SWFObject("<%=path%>/flash/store.swf", "sotester", "100%", "100%", "9", "#ffffff");
		so.addParam("allowFullScreen", "true");
		so.addParam("allowEdit", "true");
		so.useExpressInstall('expressinstall.swf');
		
		so.write("mainp");
//-->
</script>
