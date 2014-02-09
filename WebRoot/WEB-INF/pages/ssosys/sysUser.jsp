<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<div class="pageContent">
<h2 class="contentTitle">单点系统信息</h2>
<form:form id="form1" action="/ssosys.do" commandName="TblSystem" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent nowrap" layoutH="470">
		<form:hidden path="sysId"/>
		<input type="hidden" name="tag" value="${tag}" />
		<input type="hidden" name="rel" value="${rel}"/>
		<fieldset>
		<p>
			<label>单点系统名称：</label>
			<form:input readonly="true" path="sysName" />
		</p>
		<p>
			<label>单点系统链接：</label>
			<form:input readonly="true" path="sysUrl" />
		</p>
		<p>
			<label>单点系统登录字段：</label>
			<form:input readonly="true" path="loginField" />
		</p>
		<p>
			<label>单点系统密码字段：</label>
			<form:input readonly="true" path="pwdField" />
		</p>
		<p>
			<label>登录标志字段：</label>
			<form:input path="loginFlag1" />
		</p>
		<p>
			<label>单点系统状态：</label>
			<form:radiobutton path="sysStatus" value="1"/>有效
			<form:radiobutton path="sysStatus" value="0"/>无效
		</p>
		</fieldset>
		</div>
		<div class="divider"></div>
		<h3 class="contentTitle">关联用户</h3>
		<div class="tabs">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li class="selected"><a href="javascript:void(0)"><span>手动增加管理用户</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent" style="height: 290px;">
				<div>
					<table class="list nowrap itemDetail" addButton="新增管理用户" width="95%">
						<thead>
							<tr>
								<th type="lookup" name="items[#index#].loginName" lookupGroup="items[#index#]" lookupUrl="/user.do?tag=toQuery&rel=${rel }&isLoginName=true&returnId=true" suggestUrl="/user.do?tag=listUsers&isLoginName=true&returnId=true" suggestFields="loginName" postField="keywords" size="12" fieldClass="required">用户登录名称</th>
								<th type="text" name="items[#index#].sysuserId" size="12" fieldClass="required" fieldAttrs="{maxlength:10}">单点系统登录名</th>
								<th type="text" name="items[#index#].sysuserPwd" size="12" fieldClass="required" fieldAttrs="{maxlength:16}">单点系统登录密码</th>
								<th type="text" name="items[#index#].sysuserType" size="12" fieldAttrs="{maxlength:10}">登录用户标志位值</th>
								<th type="del" width="60">操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
		<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
</form:form>
</div>