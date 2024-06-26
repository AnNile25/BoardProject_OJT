<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />     
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script src="${CP}/resources/js/sendAjaxRequest.js"></script>
<meta charset="UTF-8">
<title>Board Modify</title>
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
<link rel="stylesheet" type="text/css" href="${CP}/resources/css/common.css">
<script src="${CP}/resources/js/jquery-3.7.1.js"></script>
<script src="${CP}/resources/js/eUtil.js"></script>
<script>
document.addEventListener("DOMContentLoaded",function(){ 
	const retrieveQnaArticleBTN 	= document.querySelector("#retrieveQnaArticle");
	const updateArticleBTN 	= document.querySelector("#updateArticle");
	const deleteArticleBTN 	= document.querySelector("#deleteArticle");
	
	const boardSeq = document.querySelector("#boardSeq").value;
	const regForm       = document.querySelector("#regFrm");
	
	function retrieveQnaArticle(){
    	window.location.href = "/qna/retrieveQnaArticle";
    }
	
	/* 목록으로 돌아가기 */
	retrieveQnaArticleBTN.addEventListener("click", function(e){
		retrieveQnaArticle()
	});
	
	/* 수정 이벤트 */
	updateArticleBTN.addEventListener("click", function(e){
		const title 	= document.querySelector("#title").value;
		const content 	= document.querySelector("#content").value; 
				
		if(window.confirm('수정 하시겠습니까?')==false){
            return;
        }
		sendAjaxRequest("POST", `${CP}/qna/updateQnaArticle`, {
			boardSeq: boardSeq,
			title: title,
			content: content
		}, function(data) {
			alert(data.msgContents);
			 if('1'==data.msgId){
				 retrieveQnaArticle();
			 }
		});
	});//--updateArticleBTN
	
	/* 삭제 이벤트 */
	deleteArticleBTN.addEventListener("click", function(e){
		if(window.confirm('삭제 하시겠습니까?')==false){
            return;
        }
		sendAjaxRequest("GET", `${CP}/qna/deleteQnaArticle`, {boardSeq: boardSeq}, function(data) {
			alert(data.msgContents);
			 if('1'==data.msgId){
				 retrieveQnaArticle();
			 }
		});
	});//--deleteArticleBTN
	
});//--DOMContentLoaded
</script>
</head>
<body>

<div class="container-main">
	<div class="content">
	    <!-- 버튼 -->
	    <div class="row justify-content-end">
	        <div class="col-auto">
	            <input type="button" value="목록" class="button" id="retrieveQnaArticle">
	            <input type="button" value="수정" class="button" id="updateArticle">
	            <input type="button" value="삭제" class="button" id="deleteArticle">
	        </div>
	    </div>

        <!-- 제목 -->
	    <div class="row">
	        <div class="col-lg-12">
	            <h1>게시글 수정</h1>
	        </div>
	    </div>  
	    
	    <!-- form -->
	    <form action="#" name="regFrm" id="regFrm">
	    	<input type="hidden" id="boardSeq" name="boardSeq" value="${vo.boardSeq }" readonly>
	  
			<div class="col-auto" style="display: flex; align-items: center;">
				<label id="memberId" style="margin-bottom: 10px;">${vo.memberId}</label>
			</div>
			
			<div style="margin-bottom: 10px; flex-grow: 1; text-align: left; color: gray;">
				조회 ${vo.readCnt} | 추천 ${vo.likeCnt} | ${vo.regDt}</div>
	        
	        <!-- 제목 -->
	        <div class="form-group">
	            <label for="title" class="form-label">제목</label>
	            <input type="text" class="form-control" id="title" name="title" maxlength="100" 
	             value='${vo.title }' style="color:black;" placeholder="제목을 입력 하세요">
	        </div> 
	        
	        <!-- 내용 -->     
	        <div class="form-group">
	            <label for="content" class="form-label">내용</label>
	            <textarea rows="7" class="form-control" id="content" name="content" style="color:black;">${vo.content }</textarea>
	        </div>
	    </form> 
	</div>    
</div>

</body>
</html>
