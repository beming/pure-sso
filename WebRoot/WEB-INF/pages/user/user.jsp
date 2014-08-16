<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<div class="pageContent">
<h2 class="contentTitle">用户信息维护</h2>
<form:form id="userForm" action="/user.do" commandName="TblUser" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent nowrap" layoutH="137">
		<form:hidden path="userId"/>
		<input type="hidden" name="tag" value="${tag}" />
		<input type="hidden" name="rel" value="${rel}"/>
		<fieldset>
		<p>
			<label>用户登录名：</label>
			<form:input class="required" path="loginName" remote="/login.do?tag=queryUserByLoginName&loginName=loginName" />
		</p>
		<p>
			<label>用户名称：</label>
			<form:input class="required" path="userName" />
		</p>
		<c:if test="${tag eq 'save'}">
		<p>
			<label>用户密码：</label>
			<form:password class="required" path="passwd" id="passwd"/>
		</p>
		<p>
			<label>重输密码：</label>
			<input type="password" class="required" equalTo="#passwd" name="repasswd"/>
		</p>
		</c:if>
		<p>
			<label>状态：</label>
			<form:radiobutton path="status" value="1"/>有效
			<form:radiobutton path="status" value="0"/>无效
		</p>
		<p>
			<label>性别：</label>
			<form:radiobutton path="sexy" value="1"/>男
			<form:radiobutton path="sexy" value="0"/>女
		</p>
		<p>
			<label>部门：</label>
			<form:hidden path="depId" id="input"/>
			<input name="depName" type="text" value="${dep_name }" postField="keyword" suggestFields="depName" suggestUrl="/dept.do?tag=listDepts" lookupGroup=""/>
			<a class="btnLook" href="/dept.do?tag=toQuery" lookupGroup="">查找带回</a>
		</p>
		<p>
			<label>岗位：</label>
			<form:hidden id="input" path="postId"/>
			<input name="postName" type="text" value="${post_name }" postField="keyword" suggestFields="postName" suggestUrl="/post.do?tag=listPosts" lookupGroup=""/>
			<a class="btnLook" href="/post.do?tag=toQuery" lookupGroup="">查找带回</a>
		</p>
		<p>
			<label>生日：</label>
			<form:input path="birthday" class="date" dateFmt="yyyy-MM-dd" readonly="true"/>
			<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
		<p>
			<label>电话：</label>
			<form:input path="phone" />
		</p>
		<p>
			<label>移动电话：</label>
			<form:input path="mobile" />
		</p>
		<p>
			<label>Email：</label>
			<form:input path="email" />
		</p>
		<p>
			<label>地址：</label>
			<form:input path="addr" size="40"/>
		</p>
		<p>
			<label>用户备注：</label>
			<form:textarea path="remark" rows="4" cols="30" />
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