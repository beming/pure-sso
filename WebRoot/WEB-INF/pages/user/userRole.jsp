<%@page import="org.apache.taglibs.standard.tag.el.core.ForEachTag"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<div class="pageContent">
<form:form id="form1" action="/user.do" commandName="TblUser" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<form:hidden path="userId"/>
	<input type="hidden" name="tag" value="${tag }"/>
	<input type="hidden" name="rel" value="${rel }"/>
	<div class="pageFormContent nowrap" layoutH="360">
	<fieldset>
	<h2 class="contentTitle">被授权用户信息</h2>
	<br/>
	<p>
			<label>用户登录名：</label>
			<form:input path="loginName" readonly="true"/>
		</p>
		<p>
			<label>用户名称：</label>
			<form:input path="userName" readonly="true"/>
		</p>
		<p>
			<label>部门：</label>
			<input name="depName" type="text" value="${dep_name }" readonly="true"/>
		</p>
		<p>
			<label>岗位：</label>
			<input name="postName" type="text" value="${post_name }" readonly="true"/>
		</p>
		<p>
			<label>电话：</label>
			<form:input path="phone" readonly="true"/>
		</p>
		<p>
			<label>移动电话：</label>
			<form:input path="mobile" readonly="true"/>
		</p>
		<p>
			<label>Email：</label>
			<form:input path="email" readonly="true"/>
		</p>
	</fieldset>
	</div>
	<div class="panel collapse" minH="150" defH="270">
		<h1>系统可授权角色列表</h1>
		<div>
		<fieldset>
		<c:forEach items="${userRoleList }" var="ur">
		<input type="checkbox" name="roleIds" value="${ur.roleId }" ${ur.checked?"checked":"" }/> ${ur.roleName }
		</c:forEach>
		</fieldset>
       <div class="formBar">
			<ul>
				<li>
				<div class="buttonActive"><div class="buttonContent"><button type="submit">提 交</button></div></div>
				</li>
			</ul>
		</div>
		</div>
		</div>
</form:form>
</div>