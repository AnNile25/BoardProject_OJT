<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />     
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<meta charset="UTF-8">
<title>Board Register</title>
<link rel="stylesheet" type="text/css" href="${CP}/resources/css/common.css">
<script src="${CP}/resources/js/jquery-3.7.1.js"></script>
<script src="${CP}/resources/js/eUtil.js"></script>
<script src="${CP}/resources/js/sendAjaxRequest.js"></script>
<script>
document.addEventListener("DOMContentLoaded",function(){
	const retrieveQnaArticleBTN	 = document.querySelector("#retrieveQnaArticle");
	const saveArticleBTN =  document.querySelector("#saveArticle");
	const regForm        = document.querySelector("#regFrm");
	
	/* 저장 이벤트 */
	saveArticleBTN.addEventListener("click", function(e){
		const memberId = document.querySelector("#memberId").value;
		const title = document.querySelector("#title").value;
		const content = document.querySelector("#content").value;
		
		if(window.confirm("등록 하시겠습니까?")==false){
			return;
		}
		sendAjaxRequest("POST", `${CP}/qna/saveQnaArticle`, {
			memberId : memberId,
	           title: title,
	           content: content,
	           likeCnt: 0,
	           readCnt: 0
		}, function(data) {
			alert(data.msgContents);
			 if('1'==data.msgId){
				 retrieveQnaArticleFun();
			 }
		});
	});
	
	/* 목록 이벤트 */
	retrieveQnaArticleBTN.addEventListener("click", function(e){
		console.log("retrieveQnaArticleBTN click");
		retrieveQnaArticleFun();
	});
	
	function retrieveQnaArticleFun(){
		window.location.href = "/qna/retrieveQnaArticle";
	}
});
</script>
<style>
.form-group {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}
.form-group label {
  flex: 0 0 50px;
  margin-right: 10px;
  text-align: right;
}
.form-group input, .form-group textarea {
  flex: 1;
}
</style>
</head>
<body>

<div class="container-main">
	<!-- 제목 -->
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">게시글 등록</h1>
        </div>
    </div>
    
    <!-- 버튼 -->
    <div class="row justify-content-end">
        <div class="col-auto" style="margin-bottom: 10px;">
            <input type="button" value="목록" class="button" id="retrieveQnaArticle">
            <input type="button" value="등록" class="button" id="saveArticle">
        </div>
    </div>
    
    <!-- form -->
    <form action="#" name="regFrm" id="regFrm">
        <div class="form-group"> 
            <label for="title">제목</label>
            <input type="text" class="form-control" id="title" name="title" maxlength="100" placeholder="제목을 입력 하세요">
        </div>
        <div class="form-group">
            <label for="memberId">등록자</label>
            <input type="text" class="form-control" id="memberId" name="memberId" value=${sessionScope.memberId } readonly="readonly">        
        </div>
        <div class="form-group">
            <label for="content">내용</label>
            <textarea rows="7" class="form-control" id="content" name="content"></textarea>
        </div>
    </form>
</div>

</body>
</html>
