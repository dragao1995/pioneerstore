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
            商品浏览功能主要用于管理员对于商品信息的管理。选择商品浏览选项界面如下：<br><br>
            &nbsp;&nbsp;<img src="help/store/images/shangping-liulang.bmp" />
            <br><br>
            商品信息是通过商品的类型分类浏览的，左边是类型的树形结构，右边显示这个类型下的所有商品信息。<br><br>
			<b>使用方法如下：</b><br><br>
			<div>&nbsp;&nbsp;1. 选择一个类型，右边列表显示这个类型下的所有商品。</div><br>
			<div>&nbsp;&nbsp;2. 选择一个商品，右边显示这个商品的详细信息。</div><br>
			<div>&nbsp;&nbsp;3. 商品详细信息中，字体为蓝色的可以打开一个窗口，显示这个参数的详细信息。</div><br>
			<b>商品属性列表：</b><br><br>
			<div>&nbsp;&nbsp;1. 条码号:商品的标签，可以使用条码枪输入的号。</div><br>
			<div>&nbsp;&nbsp;2. 名称:商品的名称。</div><br>
			<div>&nbsp;&nbsp;3. 进货价:进货的价格，必须要的属性，用于计算成本。</div><br>
			<div>&nbsp;&nbsp;4. 出售价:商品的销售指导价格，销售过程中是可以改变的（如果管理员愿意）。</div><br>
			<div>&nbsp;&nbsp;5. 存货数量:存货的数量，每次进货累加进去，每销售一次，数量逐渐减少。</div><br>
			<div>&nbsp;&nbsp;6. 重量:商品的重量。</div><br>
			<div>&nbsp;&nbsp;7. 报警数量:当数量小于多少开始报警，通知管理员。</div><br>
			<div>&nbsp;&nbsp;8. 过期日期:商品再多少号过期，用过提醒。</div><br>
			<div>&nbsp;&nbsp;9. 提前几天提醒:在商品过期之前多少天提醒。</div><br>
			<div>&nbsp;&nbsp;10. 天销售指标:每天要求销售的数量，达不到要求，通知管理员。</div><br>
			<div>&nbsp;&nbsp;11. 月销售指标:一个月的销售数量，达不到要求，通知管理员。</div><br>
			<div>&nbsp;&nbsp;12. 类型:这个商品在那个商品类型下。</div><br>
			<div>&nbsp;&nbsp;13. 供应商:这个商品的供应商。</div><br>
			
			
			<br><br><br><br><br><br>
            <p>感谢使用易科软件!</p>
        </td>

    </tr>
</table>
	
	<jsp:include page="../boot.jsp"></jsp:include>
  </body>
</html>
