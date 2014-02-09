<%@page import="org.apache.taglibs.standard.tag.el.core.ForEachTag"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<form id="sysForm" method="post" action="/ssosys.do?tag=list">
	<input type="hidden" name="status" value="${PageUtil.status}">
	<input type="hidden" name="keywords" value="${keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${PageUtil.numPerPage}" />
	<input type="hidden" name="orderField" value="${PageUtil.orderField}" />
	<input type="hidden" name="rel" value="${rel}" />
</form>

<div class="pageContent">
<form:form id="form1" action="/ssosys.do" commandName="TblSystem" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<form:hidden path="sysId"/>
	<input type="hidden" name="tag" value="${tag }"/>
	<input type="hidden" name="rel" value="${rel }"/>
	<fieldset>
	<h2 class="contentTitle">单点系统信息</h2>
	<br/>
	<p>
	<label>单点系统名称：</label>
	<form:input path="sysName" readonly="true"/>
	</p><br>
	<p>
	<label>单点系统URL：</label>
	<form:input path="sysUrl" readonly="true"/>
	</p>
	<br/>
	</fieldset>
	<div class="panel collapse" minH="150" defH="430">
		<h1>单点系统已关联用户列表</h1>
		<div>
		<form:form onsubmit="return navTabSearch(this);" commandName="TblSystem" action="/ssosys.do?tag=list" method="post">
	<input type="hidden" name="rel" value="${rel}" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					系统登录名：<input type="text" name="loginName" />
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form:form>
	<div class="panelBar">
		<ul class="toolBar">
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" href="/ssosys.do?tag=batchDelUserSys&sysId=${sysId }&rel=${rel }" class="delete"><span>批量删除关联用户</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="list" width="100%">
		<thead>
			<tr>
				<th width="22"></th>
				<th>系统登录名</th>
				<th>关联登录名</th>
				<th>关联登录密码</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${PageUtil.dataSet}" var="pu" varStatus="vst">
			<tr target="sid_user" rel="${pu.user_sys_id}">
				<td><input name="ids" value="${pu.user_sys_id }" type="checkbox"/></td>
				<td>${pu.login_name}</td>
				<td>${pu.sysuser_id}</td>
				<td>${pu.sysuser_pwd}</td>
				<td><a title="删除" target="ajaxTodo" href="/ssosys.do?tag=deleteUserSys&userSysId=${pu.user_sys_id }&rel=${rel}" class="btnDel">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共${totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${PageUtil.totalCount}" numPerPage="${PageUtil.numPerPage}" pageNumShown="${PageUtil.pageNumShown}" currentPage="${PageUtil.currentPage}"></div>
	</div>
		</div>
		</div>
</form:form>
</div>