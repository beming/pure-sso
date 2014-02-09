<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<li>
  <a href="javascript:" onclick="$.bringBack({parentId:'${menuId}', parentMenuName:'${menuName }', menuLevel:'${menuLevel+1 }'})">${menuName}</a>
	<c:if test="${not empty children}">
		<ul>
			<c:forEach var="menu" items="${children}">
				<c:set var="menu" value="${menu}" scope="request" />
				<jsp:include page="recursive.jsp" />
			</c:forEach>
		</ul>
	</c:if>
</li>