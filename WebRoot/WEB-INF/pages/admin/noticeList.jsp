<%@page import="org.apache.taglibs.standard.tag.el.core.ForEachTag"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<form id="pagerForm" method="post" action="/admin.do?tag=toNoticeList">
	<input type="hidden" name="status" value="${PageUtil.status}">
	<input type="hidden" name="keywords" value="${keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${PageUtil.numPerPage}" />
	<input type="hidden" name="orderField" value="${PageUtil.orderField}" />
</form>

<div class="pageHeader">
	<form:form onsubmit="return navTabSearch(this);" commandName="TblNotice" action="/admin.do?tag=toNoticeList" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					公告标题：<form:input path="noticeTitle" />
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
			<li><a class="add" href="/admin.do?tag=toNotice" target="navTab"><span>添加</span></a></li>
			<li><a class="delete" href="/admin.do?tag=delNotice&noticeId={sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="/admin.do?tag=toEditNotice&noticeId={sid_user}" target="navTab"><span>修改</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20"></th>
				<th>公告标题</th>
				<th width="120">发布时间</th>
				<th width="100">有效时间</th>
				<th width="150">发布人员</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${PageUtil.dataSet}" var="notice" varStatus="vst">
			<tr target="sid_user" rel="${notice.noticeId}">
				<td>${vst.index+1 }</td>
				<td>${notice.noticeTitle}</td>
				<td>${notice.noticeTime}</td>
				<td>${notice.noticeValidTime}</td>
				<td>${notice.noticePublisher}</td>
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
