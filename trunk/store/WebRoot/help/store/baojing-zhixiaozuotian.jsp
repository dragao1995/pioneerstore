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
            昨天滞销报警，用于向系统管理员报警昨天那些商品滞销了，这样有力于管理员分析这种商品的市场表现，及早做出营销策略。
			<br><br>
			&nbsp;&nbsp;<img src="help/store/images/yujing-zhixiaozuotian.bmp" />
            <br><br>
            
			左边列表中显示的商品是昨天滞销的商品。在列表中选中每个商品，在右边市场表现分析器中分析。<br><br>
			选中一个商品，就类似于给市场表现分析器中添加一条商品信息。<br><br>
			<a href="help/store/shichangbiaoxiang.jsp">市场表现分析器使用</a>
            <p>感谢使用易科软件!</p>
        </td>

    </tr>
</table>
	
	<jsp:include page="../boot.jsp"></jsp:include>
  </body>
</html>
