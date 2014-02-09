<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<div class="pageContent">
<h2 class="contentTitle">角色信息维护</h2>
<form:form id="form1" action="/ssosys.do" commandName="TblSystem" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent nowrap" layoutH="137">
		<form:hidden path="sysId"/>
		<input type="hidden" name="tag" value="${tag}" />
		<input type="hidden" name="rel" value="${rel}"/>
		<fieldset>
		<p>
			<label>单点系统名称：</label>
			<form:input class="required" path="sysName" />
		</p>
		<p>
			<label>单点系统链接：</label>
			<form:input class="required" path="sysUrl" />
		</p>
		<p>
			<label>单点系统登录字段：</label>
			<form:input class="required" path="loginField" />
		</p>
		<p>
			<label>单点系统密码字段：</label>
			<form:input class="required" path="pwdField" />
		</p>
		<p>
			<label>安全（认证）级别：</label>
			<form:select path="securityLevel">
				<form:option value="0">无安全级别</form:option>
				<form:option value="1">一级安全级别</form:option>
				<form:option value="2">二级安全级别</form:option>
				<form:option value="3">三级安全级别</form:option>
			</form:select>
		</p>
		<p>
			<label>登录标志字段：</label>
			<form:input path="loginFlag1" />
		</p>
		<p>
			<label>单点系统状态：</label>
			<form:radiobutton path="sysStatus" value="1"/>有效
			<form:radiobutton path="sysStatus" value="0"/>无效
		</p>
		<p>
			<label>单点系统备注：</label>
			<form:textarea path="sysRemark" rows="4" cols="30" />
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