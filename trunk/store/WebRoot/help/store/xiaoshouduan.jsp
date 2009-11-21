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
           	销售人员进入系统后，看到的界面如下图：<br><br>
            &nbsp;&nbsp;<img src="help/store/images/xiaoshou.bmp" />
			<br>
			<b>使用方法如下：</b><br><br>
				
				<div>&nbsp;&nbsp;1. 确保条码输入框获得光标：单击界面或条码输入框，让条码输入框获得光标。</div><br>
				<div>&nbsp;&nbsp;2. 输入获得商品信息：手工输入商品条码回车或使用扫描枪输入，商品的详细信息在列表中显示。如果有多个相同的商品，修改商品数量后输入条码。</div><br>
				
				<div>&nbsp;&nbsp;3. 修改数量，价格：提交交易前，如果要修改商品的价格，购买的数量，可以单击要修改的信息，弹出修改窗口如下图：</div> 
				&nbsp;&nbsp;<img src="help/store/images/xgaijiage.bmp" />
				<div>&nbsp;&nbsp;4. 结算：让实付输入框获得光标，输入实付金额。在左下脚显示商品的合计，实付，找零金额信息，注意不要找零金额为负就提交交易。</div><br>
			&nbsp;&nbsp;
				<div>&nbsp;&nbsp;5. 打印小票： 输入金额后，单击打印按钮，打印购物小票，此次交易完成。</div><br>
				<div>&nbsp;&nbsp;6. 不打印小票，完成交易： 输入金额后，如果没有打印机或购物人不需要购物小票，直接单击提交按钮即可。</div><br>
           		<div>&nbsp;&nbsp;7. 为了提高工作效率，请注意使用快捷键。 </div><br>
           	
           	
           	<b>快捷键有：</b><br><br>
           		<div>&nbsp;&nbsp;1. 输入条码前修改商品数量。 f1，f2，f3，f4，f5，f6，f7，f8，f9,修改为1，2，3，4，5，6，7，8，9 如果数额较大，单击数量，输入数额，继续输入商品，注意把数量修改过来，并且条码输入框获得光标。</div><br>
           		<div>&nbsp;&nbsp;2. 条码输入框获得光标：ctrl键</div><br>
           		<div>&nbsp;&nbsp;3. 实付金额输入框获得光标：shift键</div><br>
           		<div>&nbsp;&nbsp;4. 打印结束交易：end[num lk]键</div><br>
           		<div>&nbsp;&nbsp;5. 提交结束交易：pg dn [break]键</div><br>
           <br><br><br><br><br><br>
            <p>感谢使用易科软件!</p>
        </td>

    </tr>
</table>
	
	<jsp:include page="../boot.jsp"></jsp:include>
  </body>
</html>
