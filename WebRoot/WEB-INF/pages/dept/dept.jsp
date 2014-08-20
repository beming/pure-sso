<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<div class="pageContent">
<h2 class="contentTitle">部门信息维护</h2>
<form:form id="form1" action="/dept.do" commandName="TblDepartment" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent nowrap" layoutH="137">
		<form:hidden path="depId"/>
		<form:hidden path="depLevel"/>
		<form:hidden path="depLevelCode"/>
		<input type="hidden" name="tag" value="${tag}" />
		<input type="hidden" name="rel" value="${rel}"/>
		<fieldset>
		<p>
			<label>部门编码：</label>
			<form:input class="required" path="depCode" />
		</p>
		<p>
			<label>部门名称：</label>
			<form:input class="required" path="depName" />
		</p>
		<p>
			<label>上级部门：</label>
			<form:hidden path="parentDepId"/>
			<input class="required" name="depName" type="text" readonly/>
			<a class="btnLook" href="/dept.do?tag=depTreeLookup" lookupGroup="">查找带回</a>
		</p>
		<p>
			<label>状态：</label>
			<form:radiobutton path="depStatus" value="1"/>有效
			<form:radiobutton path="depStatus" value="0"/>无效
		</p>
		<p>
			<label>部门联系电话：</label>
			<form:input path="depPhone" />
		</p>
		<p>
			<label>部门联系人：</label>
			<form:input path="depLinker" />
		</p>
		<p>
			<label>部门联系Email：</label>
			<form:input path="depEmail" />
		</p>
		<p>
			<label>部门人数：</label>
			<form:input path="depTeacher" />
		</p>
		<p>
			<label>部门备注：</label>
			<form:textarea path="depRemark" rows="4" cols="30" />
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