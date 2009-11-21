<!--edit page create by pionner at Tue Feb 03 16:12:48 CST 2009-->
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'uploadfile.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  
    <form name="addform"  action="list.action" enctype="multipart/form-data">
			<table width='100%' border='0' cellspacing='1' class="ipttable">
				<input type="file" name="tmplFile" value="上传文件"/>
				

			</table>
			<input type="submit"></input>
	</form>
	
	<s:form name="addform"  action="list.action" enctype="multipart/form-data">
			<table width='100%' border='0' cellspacing='1' class="ipttable">
				<s:select name='type' label='类别名称' list="#{'sms':'短信模板','salesys':'营销模板','ogsm':'任务管理','quliday':'质量管理','other':'其他'}"></s:select>
				<s:textfield name='name' label='名称'/>
				<s:hidden id="filePathnew" name="filePathnew"></s:hidden>
				<s:file name="tmplFile" onchange="document.all.filePathnew.value=this.value" label="上传文件"/>
				<s:textarea name='remark' label='注释'></s:textarea>
				<tr><td colspan="2"><input type="submit"></input></td></tr>
			</table>
		</s:form>
  </body>
</html>
<script type="text/javascript">
<!--
 window.close=function(){
 	alert('close this window');
 }
//-->
</script>
