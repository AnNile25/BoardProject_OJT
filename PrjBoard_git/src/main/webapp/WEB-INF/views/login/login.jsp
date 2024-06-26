<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />  
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" type="text/css" href="${CP}/resources/css/common.css">
<script src="${CP}/resources/js/jquery-3.7.1.js"></script>
<script src="${CP}/resources/js/eUtil.js"></script>
<script src="${CP}/resources/js/sendAjaxRequest.js"></script>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded",function(){
	const loginBTN = document.querySelector("#loginBtn");
	
	loginBTN.addEventListener("click", function(e) {
		const memberId = document.querySelector("#memberId").value;		
		if(eUtil.isEmpty(memberId)==true){
			 alert('아이디를 입력 하세요.');
			 document.querySelector("#memberId").focus();
			 return;
		 }
		const password = document.querySelector("#password").value;
        if(eUtil.isEmpty(password)==true){
            alert('비밀번호를 입력 하세요.');
            document.querySelector("#password").focus();
            return;
        }        
        if(confirm("로그인 하시겠습니까?")===false) return;
        
        sendAjaxRequest("POST", `${CP}/login/login.do`, {
       		memberId: memberId,
       		password: password
       	 }, function(data){
       		 if("10" == data.msgId){
				alert(data.msgContents);
				document.querySelector("#memberId").focus();
           	 }else if("20" == data.msgId){
                alert(data.msgContents);
                document.querySelector("#password").focus();                 
			}else if("30" == data.msgId){
				alert(data.msgContents);
	           	window.location.href = "/qna/retrieveQnaArticle";
	         }
		});
	});
	
});//--DOMContentLoaded
</script>
</head>
<body>
 <div class="container-main">
        <h1>로그인</h1>
        <c:if test="${not empty errorMessage}">
            <p style="color: red;">${errorMessage}</p>
        </c:if>
        <form action="${CP}/member/login" method="post">
            <div class="form-group">
                <label for="memberId">아이디</label>
                <input type="text" id="memberId" name="memberId" required>
            </div>
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" required>
            </div>
            <input type="button" value="로그인" class="button" id="loginBtn">
            <a href="${CP}/main/mainView.do" class="button">취소</a>
        </form>
    </div>
</body>
</body>
</html>