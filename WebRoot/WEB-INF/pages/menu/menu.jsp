<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<div class="pageContent">
<h2 class="contentTitle">菜单信息维护</h2>
<form:form id="form1" action="/menu.do" commandName="TblMenu" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent nowrap" layoutH="137">
		<form:hidden path="menuId"/>
		<input type="hidden" name="tag" value="${tag}" />
		<input type="hidden" name="rel" value="${rel}"/>
		<fieldset>
		<p>
			<label>菜单名称：</label>
			<form:input class="required" path="menuName" />
		</p>
		<p>
			<label>菜单NavCode：</label>
			<form:input class="required" path="navcode" />
		</p>
		<p>
			<label>父菜单：</label>
			<c:if test="${tag eq 'save' }">
			<input name="menuLevel" type="hidden" value=""/>
			<input class="required" name="parentId" value="" type="hidden"/>
			<input class="required" name="parentMenuName" type="text" readonly/>
			<a class="btnLook" href="/menu.do?tag=treeList" lookupGroup="">查找带回</a>
			</c:if>
			<c:if test="${tag eq 'update'}">
			<form:hidden path="parentId"/>
			<form:hidden path="menuLevel"/>
			<input name="parentMenuName" readonly="true" type="text" value="${parentMenuName }"/>
			</c:if>	
		</p>
		<p>
			<label>菜单链接：</label>
			<form:input path="menuUrl" />
		</p>
		<p>
			<label>排序辅助标记：</label>
			<form:input class="required" path="menuSort" />
		</p>
		<p>
			<label>顶层菜单标识：</label>
			<form:radiobutton path="topMenuFlag" value="1"/>业务菜单
			<form:radiobutton path="topMenuFlag" value="0"/>功能菜单
		</p>
		<p>
			<label>是否菜单：</label>
			<form:radiobutton path="isMenu" value="1"/>菜单
			<form:radiobutton path="isMenu" value="0"/>功能
		</p>
		<p>
			<label>状态：</label>
			<form:radiobutton path="menuStatus" value="1"/>有效
			<form:radiobutton path="menuStatus" value="0"/>无效
		</p>
		<p>
			<label>岗位备注：</label>
			<form:textarea path="menuRemark" rows="4" cols="30" />
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