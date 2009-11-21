<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
	<base href="<%=basePath%>">
    
    <title> 先锋，让你体验科技！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/help.css" rel="stylesheet" type="text/css">
   
    
  </head>
  
  <body>
    <jsp:include page="help/head.jsp"></jsp:include>
<br>
<!-- Table of Contents -->
<table height="54%">
    <tr>
        <td valign="top">
            <jsp:include page="help/store/storeMenu.jsp"></jsp:include>
        <br>
            
        </td>

        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

        <!-- Body -->
        <td align="left" valign="top">
           <br><br>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;先锋店铺管理平台，是由国内多名信息系统专家，根据多年的软件开发经验，深入了解市场需求研发而成，系统融合了销售，管理，监控分析等功能，让你轻松把握市场需求，做出合理的营销策略。</p>

            
			<br/>
           	<p>
               <table width="100%">
               		<tr>
               			<td width="30%">&nbsp;&nbsp;</td>
               			<td><a href="main.jsp">进入系统</a></td>
               			<td><a href="client.rar">销售端下载</a></td>
               			<td width="30%">&nbsp;&nbsp;</td>
               		</tr>
               </table>
            	   
            </p>
			<br/>
			<br/><br/>

            <p>感谢使用先锋软件!</p>
        </td>

    </tr>
</table>
	
	<jsp:include page="help/boot.jsp"></jsp:include>
  </body>
</html>
