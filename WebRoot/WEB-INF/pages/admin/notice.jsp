<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<div class="pageContent">
<form:form id="form1" action="/admin.do" commandName="TblNotice" method="POST" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
<form:hidden path="noticeId"/>
<input type="hidden" name="tag" value="${tag}"/>
<div class="pageFormContent" layoutH="60">
	<fieldset>
		<legend>发布公告</legend>
		<dl class="nowrap">
			<dt>公告标题：</dt>
			<dd><form:input class="required" path="noticeTitle" size="80"/></dd>
		</dl>
		<dl class="nowrap">
			<dt>有效期至：</dt>
			<dd><form:input path="noticeValidTime" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"/>
			    <a class="inputDateButton" href="javascript:;">选择</a>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>必填：</dt>
			<dd><form:textarea class="required" path="noticeContent" cols="80" rows="12"/></dd>
		</dl>
		<div class="formBar">
			<ul>
				<li>
				<div class="buttonActive"><div class="buttonContent"><button type="submit">提 交</button></div></div>
				</li>
			</ul>
		</div>
	</fieldset>
</div>
</form:form>
</div>