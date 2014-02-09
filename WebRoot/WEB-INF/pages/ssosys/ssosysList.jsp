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

<div class="pageHeader">
	<form:form onsubmit="return navTabSearch(this);" commandName="TblSystem" action="/ssosys.do?tag=list" method="post">
	<input type="hidden" name="rel" value="${rel}" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					单点系统名称：<form:input path="sysName" />
				</td>
				<td>
					有效性：<form:select path="sysStatus">
					<form:option value="">全部</form:option>
					<form:option value="1">有效</form:option>
					<form:option value="0">无效</form:option>
					</form:select>
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
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="/ssosys.do?tag=add&rel=${rel }" target="navTab"><span>添加单点系统</span></a></li>
			<li><a class="edit" href="/ssosys.do?tag=edit&rel=${rel }&sysId={sid_user}" target="navTab"><span>修改单点系统</span></a></li>
			<li><a class="edit" href="/ssosys.do?tag=grantUser&rel=${rel }&sysId={sid_user}" target="navTab"><span>单点系统关联用户</span></a></li>
			<li><a class="edit" href="/ssosys.do?tag=grantUserList&rel=${rel }&sysId={sid_user}" target="navTab"><span>已关联用户</span></a></li>
			<li><a class="delete" href="/ssosys.do?tag=delete&rel=${rel }&sysId={sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20"></th>
				<th>单点系统名称</th>
				<th>单点系统URL</th>
				<th>单点系统登录字段</th>
				<th>单点系统密码字段</th>
				<th>单点系统登录标志字段</th>
				<th>单点系统登录标志位字段</th>
				<th width="10">是否有效</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${PageUtil.dataSet}" var="sys" varStatus="vst">
			<tr target="sid_user" rel="${sys.sysId}">
				<td>${vst.index+1 }</td>
				<td>${sys.sysName}</td>
				<td>${sys.sysUrl}</td>
				<td>${sys.loginField}</td>
				<td>${sys.pwdField}</td>
				<td>${sys.loginFlag1}</td>
				<td>${sys.securityLevel eq 0?"无安全级别":sys.securityLevel eq 1?"一级":sys.securityLevel eq 2?"二级":"三级"}</td>
				<td>${sys.sysStatus eq 1?"有效":"无效"}</td>
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
