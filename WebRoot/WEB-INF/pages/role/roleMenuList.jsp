<%@page import="org.apache.taglibs.standard.tag.el.core.ForEachTag"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<div class="pageContent">
<form:form id="form1" action="/role.do" commandName="TblRole" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<form:hidden path="roleId"/>
	<input type="hidden" name="tag" value="${tag }"/>
	<input type="hidden" name="rel" value="${rel }"/>
	<fieldset>
	<h2 class="contentTitle">被授权角色信息</h2>
	<br/>
	<p>
	<label>角色名称</label>
	<form:input path="roleName" readonly="true"/>
	</p>
	<br/>
	</fieldset>
	<div class="panel collapse" minH="150" defH="430">
		<h1>系统可授权菜单或功能列表</h1>
		<div>
	<table class="list" width="100%">
		<thead>
			<tr>
				<th width="22"></th>
				<th>菜单名称</th>
				<th>标识</th>
				<th>菜单URL</th>
				<th>查找权限</th>
				<th>增加权限</th>
				<th>修改权限</th>
				<th>删除权限</th>
				<!-- 
				<th>执行权限</th>
				 -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${menuList}" var="menu" varStatus="vst">
			<tr target="sid_user" rel="${menu.menuId}">
				<td><input name="ids" value="${menu.menuId }" type="checkbox" ${menu.checked?"checked":"" }></td>
				<td>${menu.menuName}</td>
				<td>${menu.isMenu eq 1?"菜单":"功能"}</td>
				<td>${menu.menuUrl}</td>
				<td><input name="r_q_${menu.menuId}" value="1" type="checkbox" ${menu.RSelect?"checked":"" }></td>
				<td><input name="r_a_${menu.menuId}" value="1" type="checkbox" ${menu.RAdd?"checked":"" }></td>
				<td><input name="r_u_${menu.menuId}" value="1" type="checkbox" ${menu.RUpdate?"checked":"" }></td>
				<td><input name="r_d_${menu.menuId}" value="1" type="checkbox" ${menu.RDel?"checked":"" }></td>
				<!-- <td><input name="r_e_${menu.menuId}" value="1" ${menu.isMenu eq 1?"disabled":""} type="checkbox" ${menu.RExec?"checked":"" }></td> -->
			</tr>
		</c:forEach>
		</tbody>
	</table>
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