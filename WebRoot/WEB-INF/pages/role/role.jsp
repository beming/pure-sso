<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<div class="pageContent">
<h2 class="contentTitle">角色信息维护</h2>
<form:form id="form1" action="/role.do" commandName="TblRole" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent nowrap" layoutH="137">
		<form:hidden path="roleId"/>
		<input type="hidden" name="tag" value="${tag}" />
		<input type="hidden" name="rel" value="${rel}"/>
		<fieldset>
		<p>
			<label>角色名称：</label>
			<form:input class="required" path="roleName" />
		</p>
		<p>
			<label>状态：</label>
			<form:radiobutton path="roleStatus" value="1"/>有效
			<form:radiobutton path="roleStatus" value="0"/>无效
		</p>
		<p>
			<label>角色备注：</label>
			<form:textarea path="roleRemark" rows="4" cols="30" />
		</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
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