<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ page import="java.io.*" %>
<%!
	String conStr(String str){
		if(str == null){
			return "";
		}
		str = str.replaceAll("<","<&nbsp;");
		str.replaceAll(">","&nbsp;>");
		str = str.replaceAll("\n","<br>");
		return str;
	}
%>
<%
	if(exception == null)
		exception = (Throwable)request.getAttribute("javax.servlet.error.exception"); 
	if(exception == null){
		exception = (Throwable)request.getAttribute("javax.servlet.jsp.jspException");
	}
	if(exception == null) {
		exception = (Throwable) request.getAttribute("org.apache.jasper.JasperException");
	}
	System.out.println(exception == null); 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8">
<title>系统错误信息</title>
<script>
function view(){
	var tmp=document.all("dtlMsg");
	if(tmp.rows(1).style.display  == "none"){
	    tmp.rows(1).style.display = "";
	}else{
		tmp.rows(1).style.display = "none";
	}
}
</script>
</head>
<body> 
<br> 
<br> 
<br> 
<br> 
<table cellpadding="3" cellspacing="0" align="center" width="60%"> 
  <tr align="center"> 
    <td height="25" bgcolor="#8DC647" style="border-left:1px #8CA5B5 solid;border-right:1px #8CA5B5 solid; border-top:1px #8CA5B5 solid;border-bottom:1px #8CA5B5 solid;">系统发生错误</td> 
  </tr> 
  <tr> 
    <td align="left" style="border-left:1px #8CA5B5 solid;border-right:1px #8CA5B5 solid;border-bottom:1px #8CA5B5 solid;">
      发生异常: <%= exception %><br> 
      异常信息: <%= conStr(exception.getMessage()) %><br> 
  </tr> 
</table> 
<br> 
<table width="90%"  border="0" align="center" cellpadding="1" cellspacing="1" id="dtlMsg"> 
  <tr> 
    <td><a href="#" onclick="view();">显示或隐藏详细的错误</a></td> 
  </tr> 
  <tr style="display:none"> 
    <td><br>
    <%
    	StringWriter   sw   =   new   StringWriter();  
    	exception.printStackTrace(new PrintWriter(sw));
    	out.print(conStr(sw.toString()));
    %>
    <br></td> 
  </tr> 
</table> 
<p>&nbsp;</p> 
</body>
</html>
