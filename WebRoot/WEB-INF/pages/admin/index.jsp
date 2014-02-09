<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>单点登录管理系统</title>

<link href="${webapp}/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${webapp}/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${webapp}/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="${webapp}/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<!--[if IE]>
<link href="/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<script src="${webapp}/js/speedup.js" type="text/javascript"></script>
<script src="${webapp}/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="${webapp}/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${webapp}/js/jquery.validate.js" type="text/javascript"></script>
<script src="${webapp}/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${webapp}/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>
<script src="${webapp}/uploadify/scripts/swfobject.js" type="text/javascript"></script>
<script src="${webapp}/uploadify/scripts/jquery.uploadify.v2.1.0.js" type="text/javascript"></script>

<script src="${webapp}/js/dwz.core.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.util.date.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.validate.method.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.drag.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.tree.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.accordion.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.ui.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.theme.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.navTab.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.tab.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.resize.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.dialog.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.stable.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.ajax.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.pagination.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.database.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.effects.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.panel.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.history.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.combox.js" type="text/javascript"></script>
<script src="${webapp}/js/dwz.print.js" type="text/javascript"></script>
<!--
<script src="${webapp}/bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="${webapp}/js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	DWZ.init("dwz.frag.xml", {
		loginUrl:"/frameset.do?tag=dialogLogin", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
		}
	});
});

</script>
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="#">标志</a>
				<ul class="nav">
					<li><font color="white">您好：${loginadmin.userName}，欢迎登录管理系统</font></li>
					<li><a href="/login.do?tag=logout">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<!--<li theme="red"><div>红色</div></li>-->
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul>
			</div>

			<!-- navMenu -->
			
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>

				<div class="accordion" fillSpace="sidebar">
					<div class="accordionHeader">
						<h2><span>Folder</span>业务功能</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							${cateHtml }
						</ul>
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>管理功能</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<c:forEach items="${userMenu.menuSet}" var="menu">
								<c:if test="${menu.topMenuFlag eq 0}">
							<li><a href="${menu.menuUrl}&rel=${menu.navcode}" target="navTab" rel="${menu.navcode}">${menu.menuName}</a></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="pageFormContent" layoutH="80" style="margin-right:230px">
							欢迎登陆到单点登录管理系统。
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div id="footer">Copyright &copy; 2012   </div>

</body>
</html>