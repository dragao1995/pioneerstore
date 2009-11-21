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
            用于扩展系统使用属性如：开支要添加一个属性(开支类型)。管理界面如下图<br><br>
            &nbsp;&nbsp;<img src="help/store/images/ziduan.bmp" />
	          
		  <br><br>
		 给开支添加一个开支类型，操作步骤如下。
		 <br><br>
			<div>&nbsp;&nbsp;1. 在左边的列表中选中开支，右边列表显示开支的所有字段，如下图：</div><br>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;
				<img src="help/store/images/xtong-kaizhi.bmp" />
			</div>
			<div>&nbsp;&nbsp;2. 单击添加按钮，打开添加属性窗口，如下图：</div><br>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;
				<img src="help/store/images/xtong-addCol.bmp" />
			</div>
			<div>&nbsp;&nbsp;3. 添加字段后，选中开支类型，显示的界面如下图：</div><br>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;
				<img src="help/store/images/xtong-kaizhiliex.bmp" />
			</div>
			<div>&nbsp;&nbsp;4. 可以给这个字段添加可以选择值的类别如下图：</div><br>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;
				<img src="help/store/images/xtong-kaizhileibieval.bmp" />
			</div>
			<div>&nbsp;&nbsp;5. 打开日常开支，添加一个新开支界面如下：</div><br>
			<div>&nbsp;&nbsp;&nbsp;&nbsp;
				<img src="help/store/images/xtong-kaizhiadd.bmp" />
			</div>
			<div>&nbsp;&nbsp;新添加的字段完成了，当然，如果不给出取值列表，新添加的界面就是输入而不是选择列表了。</div><br>
            <p>感谢使用易科软件!</p>
        </td>

    </tr>
</table>
	
	<jsp:include page="../boot.jsp"></jsp:include>
  </body>
</html>
