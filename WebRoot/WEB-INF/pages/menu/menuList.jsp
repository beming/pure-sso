<%@page import="org.apache.taglibs.standard.tag.el.core.ForEachTag"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<form id="menuForm" method="post" action="/menu.do?tag=list">
	<input type="hidden" name="status" value="${PageUtil.status}">
	<input type="hidden" name="keywords" value="${keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${PageUtil.numPerPage}" />
	<input type="hidden" name="orderField" value="${PageUtil.orderField}" />
	<input type="hidden" name="rel" value="${rel}"/>
</form>

<div class="pageHeader">
	<form:form onsubmit="return navTabSearch(this);" commandName="TblMenu" action="/menu.do?tag=list" method="post">
	<input type="hidden" name="rel" value="${rel}"/>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					菜单名称：<form:input path="menuName" />
				</td>
				<td>
					有效性：<form:select path="menuStatus">
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
			<li><a class="add" href="/menu.do?tag=add&rel=${rel }" target="navTab"><span>添加菜单</span></a></li>
			<li><a class="edit" href="/menu.do?tag=edit&rel=${rel }&menuId={sid_user}" target="navTab"><span>修改菜单</span></a></li>
			<li><a class="delete" href="/menu.do?tag=delete&rel=${rel }&menuId={sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20"></th>
				<th>菜单名称</th>
				<th>菜单层级</th>
				<th>上级菜单名称</th>
				<th>标识</th>
				<th width="150">是否有效</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${PageUtil.dataSet}" var="menu" varStatus="vst">
			<tr target="sid_user" rel="${menu.TblMenu.menuId}">
				<td>${vst.index+1 }</td>
				<td>${menu.TblMenu.menuName}</td>
				<td>${menu.TblMenu.menuLevel}</td>
				<td>${menu.parentMenuName}</td>
				<td>${menu.TblMenu.isMenu eq 1?"菜单":"功能"}</td>
				<td>${menu.TblMenu.menuStatus eq "1"?"有效":"无效"}</td>
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
