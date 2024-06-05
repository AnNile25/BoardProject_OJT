<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />     
<!DOCTYPE html>
<html>
<head>
	<title>Main</title>
</head>
<body>

<h1>
	Main
</h1>

<ul class="nav">
	<li class="nav-item">
		<a class="nav-link" href="<c:url value='/board/doRetrieve.do'/>">게시목록</a>
	</li>
	<li class="nav-item">
    	<a class="nav-link" href="<c:url value='/board/moveToReg.do'/>">게시등록</a>
	</li>
</ul>

</body>
</html>
