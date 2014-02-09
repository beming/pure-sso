<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<li>
  <a href="${menu.menuUrl}&rel=${menu.navcode}" target="navTab" rel="${menu.navcode}">${menu.menuName}</a>
	<c:if test="${not empty menu.children}">
		<ul>
			<c:forEach var="menu" items="${menu.children}">
				<c:set var="menu" value="${menu}" scope="request" />
				<jsp:include page="recursive.jsp" />
			</c:forEach>
		</ul>
	</c:if>
</li>