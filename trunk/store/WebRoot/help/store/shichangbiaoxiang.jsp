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
	           市场表现功能，帮助管理员分析商品的市场销售情况。这样有力于管理员及早做出促销策略。
				<br><br>
				<b>可以进行的分析有：</b><br><br>
				<div>&nbsp;&nbsp;1.一种商品的市场分析，如一种商品在某个时间段的销售量曲线图。</div><br><br>
				&nbsp;&nbsp;<img src="help/store/images/fxi-liang-x.bmp" />
	            <br><br>
	            
				图形表现上方是选择分析的时间，参数的。输入条码号。如果选中叠加，将是多个商品对比分析。<br><br>
				要分析其他参数，选择不同的参数即可，如盈利，价格。<br><br>
				<div>&nbsp;&nbsp;2.多种商品的对比分析，如两种商品在某个时间段的销售量线性对比图。</div><br><br>
				&nbsp;&nbsp;<img src="help/store/images/fxi-duibi-x.bmp" /><br><br><br><br>
				<div>&nbsp;&nbsp;3.多种商品的对比分析，如两种商品在某个时间段的销售量柱对比图。</div><br><br>
				&nbsp;&nbsp;<img src="help/store/images/fxi-duibi-z.bmp" /><br><br>
	            <br><br>
	            <div>&nbsp;&nbsp;4.多种商品的对比分析，如两种商品在某个时间段的销售量叠加柱对比图。</div><br><br>
				&nbsp;&nbsp;<img src="help/store/images/fxi-duibi-diejia.bmp" /><br><br>
	            <br><br>
	            <div>&nbsp;&nbsp;5.多种商品的对比分析，如两种商品在某个时间段的销售量总量对比图。</div><br><br>
				&nbsp;&nbsp;<img src="help/store/images/fxi-duibi-zl.bmp" /><br><br>
	            <br><br>
	            &nbsp;&nbsp;在多种商品对比过程中，要进行其他参数的比较（价格，销售量）进行比较，要取掉叠加选项后重新开始。
				
				<br><br><br><br><br><br>
	           

            <p>感谢使用易科软件!</p>
        </td>

    </tr>
</table>
	
	<jsp:include page="../boot.jsp"></jsp:include>
  </body>
</html>
