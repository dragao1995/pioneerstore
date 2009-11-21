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
           <br><br>
            商品检索功能主要用于管理员对于商品信息的检索与管理。选择商品检索选项界面如下：<br><br>
            &nbsp;&nbsp;<img src="help/store/images/shangping-jiangsuo.bmp" />
            <br><br>
            管理员选择检索类型（标签，名称）用扫描枪扫描商品条形码或输入商品的名称回车，在左下方列表中显示检索到的商品。<br><br>
			<b>注意：</b><br><br>
			<div>&nbsp;&nbsp;1. 如果是条形码检索，出现多条记录，要排查两种商品是否录入重复。</div><br>
			<div>&nbsp;&nbsp;2. 注意选择检索类型。</div><br>
            <p>感谢使用易科软件!</p>
        </td>

    </tr>
</table>
	
	<jsp:include page="../boot.jsp"></jsp:include>
  </body>
</html>
