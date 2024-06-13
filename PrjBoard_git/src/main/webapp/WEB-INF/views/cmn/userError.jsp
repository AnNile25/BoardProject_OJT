<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/common.css">
</head>
<body>
<div class="container-main">
    <div class="content">
        <h1>작업이 실패했습니다.</h1>
        <p>${errorMessage}</p>
        <a href="${pageContext.request.contextPath}/qna/retrieveQnaArticle" class="button">목록으로</a>
    </div>
</div>
</body>
</html>
