<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>单点登录管理系统</title>
		<link href="${webapp}/css/xtree.css" type=text/css rel=stylesheet />
		<link href="${webapp}/css/User_Login.css" type=text/css rel=stylesheet />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	</head>
<body id=userlogin_body onload="javascript:loginForm.loginName.focus();">
<form name="loginForm" action="/login.do?tag=login" method="post">
<div></div>
<div id=user_login>
<dl>
  <dd id=user_top>
  <ul>
    <li class=user_top_l></li>
    <li class=user_top_c></li>
    <li class=user_top_r></li></ul>
   </dd>
  <dd id=user_main>
  <ul>
    <li class=user_main_l></li>
    <li class=user_main_c>
    <div class=user_main_box>
    <ul>
      <li class=user_main_text>用户名： </li>
      <li class=user_main_input><input class=TxtUserNameCssClass id="loginName" maxLength=20 name="loginName"/></li>
    </ul>
    <ul>
      <li class=user_main_text>密 码： </li>
      <li class=user_main_input><input class=TxtPasswordCssClass id="passwd" type=password name="passwd"/></li>
    </ul>
    </div>
    </li>
    <li class=user_main_r><input class=IbtnEnterCssClass id=IbtnEnter 
    style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px" 
    onclick='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("IbtnEnter", "", true, "", "", false, false))' 
    type=image src="${webapp}/images/user_botton.gif" name=IbtnEnter/> </li></ul>
    </dd>
  <dd id=user_bottom>
  <ul>
    <li class=user_bottom_l></li>
    <li class=user_bottom_c><font color="red">${msg}</font><span style="MARGIN-TOP: 40px">单点登录管理系统</span> </li>
    <li class=user_bottom_r></li></ul></dd></dl></div>
    
    <span id=ValrUserName 
style="DISPLAY: none; COLOR: red"></span><span id=ValrPassword 
style="DISPLAY: none; COLOR: red"></span><span id=ValrValidateCode 
style="DISPLAY: none; COLOR: red"></span>
<div id=ValidationSummary1 style="DISPLAY: none; COLOR: red"></div>
<div></div>

</form>
</body>
</html>
