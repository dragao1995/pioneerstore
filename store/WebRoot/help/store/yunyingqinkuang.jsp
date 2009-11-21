<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
	<base href="<%=basePath%>">
    
    <title> 易科，让你体验科技！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/help.css" rel="stylesheet" type="text/css">
   
    
  </head>
  
  <body>
    <jsp:include page="../head.jsp"></jsp:include>
<br>
<!-- Table of Contents -->
<table height="54%">
    <tr>
        <td valign="top">
            <jsp:include page="storeMenu.jsp"></jsp:include>
        <br>
            
        </td>

        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

        <!-- Body -->
        <td align="left" valign="top">
           <br><br>
           	管理人员进入系统后，右键选择管理，打开管理界面的第一个功能就是店铺的运营概况，界面如下图：<br><br>
			&nbsp;&nbsp;<img src="help/store/images/yunyingaikuang.bmp" />
			
			<br>
			<b>商店运营情况分为两种情况：</b><br><br>
			<div>&nbsp;&nbsp;一、 商品情况。</div><br>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;1. 有多少种商品库存不足。</div><br>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;2. 有多少种商品昨天滞销了。</div><br>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;3. 有多少种商品近期滞销了（最近一个月）。</div><br>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;4. 有多少种商品将要过期了。</div><br><br>
			
			<div>&nbsp;&nbsp;二、 收入情况。</div><br>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;1. 销售额。</div><br>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;2. 成本。</div><br>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;3. 日常开支。</div><br>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;4. 运营收入。</div><br><br>
			<br>
			<br>
			<br>
			<br>
            <p>感谢使用易科软件!</p>
        </td>

    </tr>
</table>
	
	<jsp:include page="../boot.jsp"></jsp:include>
  </body>
</html>
