<%@page import="org.apache.taglibs.standard.tag.el.core.ForEachTag"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<form id="deptForm" method="post" action="/dep.do?tag=list">
	<input type="hidden" name="status" value="${PageUtil.status}">
	<input type="hidden" name="keywords" value="${keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${PageUtil.numPerPage}" />
	<input type="hidden" name="orderField" value="${PageUtil.orderField}" />
	<input type="hidden" name="rel" value="${rel}"/>
</form>

<div class="pageHeader">
	<form:form onsubmit="return navTabSearch(this);" commandName="TblDepartment" action="/dept.do?tag=list" method="post">
	<input type="hidden" name="rel" value="${rel}"/>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					部门名称：<form:input path="depName" />
				</td>
				<td>
					有效性：<form:select path="depStatus">
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
			<li><a class="add" href="/dept.do?tag=add&rel=${rel }" target="navTab"><span>添加部门信息</span></a></li>
			<li><a class="edit" href="/dept.do?tag=edit&rel=${rel }&depId={sid_user}" target="navTab"><span>修改部门信息</span></a></li>
			<li><a class="delete" href="/dept.do?tag=delete&rel=${rel }&depId={sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="5%">序号</th>
				<th width="10%">部门名称</th>
				<th width="10%">部门联系人</th>
				<th width="20%">部门联系电话</th>
				<th width="20%">部门email</th>
				<th width="5%">是否有效</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${PageUtil.dataSet}" var="dep" varStatus="vst">
			<tr target="sid_user" rel="${dep.dep_id}">
				<td>${vst.index+1 }</td>
				<td>${dep.dep_name}</td>
				<td>${dep.dep_linker}</td>
				<td>${dep.dep_phone}</td>
				<td>${dep.dep_email}</td>
				<td>${dep.dep_status eq "1"?"有效":"无效"}</td>
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
