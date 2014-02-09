<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<div class="pageContent">
<h2 class="contentTitle">用户信息维护</h2>
<form:form id="userForm" action="/user.do" commandName="TblUser" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent nowrap" layoutH="137">
		<form:hidden path="userId"/>
		<input type="hidden" name="tag" value="${tag}" />
		<input type="hidden" name="rel" value="${rel}"/>
		<fieldset>
		<p>
			<label>用户旧密码：</label>
			<input type="password" class="required" name="oldPwd"/>
		</p>
		<p>
			<label>新密码：</label>
			<input type="password" class="required" name="passwd" id="passwd"/>
		</p>
		<p>
			<label>重复新密码：</label>
			<input type="password" class="required" equalTo="#passwd" name="repasswd"/>
		</p>
		</fieldset>
		</div>
		<div class="formBar">
			<ul>
				<li>
				<div class="buttonActive"><div class="buttonContent"><button type="submit">提 交</button></div></div>
				</li>
			</ul>
		</div>
</form:form>
</div>