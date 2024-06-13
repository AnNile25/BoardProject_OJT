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
<script>
document.addEventListener("DOMContentLoaded",function(){
	console.log("DOMContentLoaded");
	
	const moveToListBTN	 = document.querySelector("#moveToList");
	const saveArticleBTN =  document.querySelector("#saveArticle");
	const regForm        = document.querySelector("#regFrm");
	
	/* 저장 이벤트 */
	saveArticleBTN.addEventListener("click", function(e){
		console.log("saveArticleBTN click");
		
		const memberId = document.querySelector("#memberId").value;
		const title = document.querySelector("#title").value;
		const content = document.querySelector("#content").value;
		
		console.log("memberId:"+memberId);
		console.log("title:"+title);
		console.log("content:"+content);
		
		if(eUtil.isEmpty(title) == true){
			alert("제목을 입력하세요.")
			regForm.title.focus();
			return;
		}
		
		if(eUtil.isEmpty(content) == true){
            alert("내용을 입력하세요.")
            regForm.content.focus();
            return;
        }
		
		if(window.confirm("등록 하시겠습니까?")==false){
			return;
		}
		
		$.ajax({
            type: "POST",
            url:"/qna/saveQnaArticle",
            async:"true",
            dataType:"json",
            data:{
            	"memberId" : memberId,
                "title": title,
                "content": content,
                "likeCnt": 0,
                "readCnt": 0
                },
            success:function(data){
                console.log("data.msgId:"+data.msgId);
                console.log("data.msgContents:"+data.msgContents);
                
                if('1'==data.msgId){
                	alert(data.msgContents);
                	moveToListFun();
                }else{
                	alert(data.msgContents);
                }
            },
            error:function(data){
                console.log("error:"+data);
            },
            complete:function(data){
                console.log("complete:"+data);
            }
        });
	});
	
	/* 목록 이벤트 */
	moveToListBTN.addEventListener("click", function(e){
		console.log("moveToListBTN click");
		moveToListFun();
	});
	
	function moveToListFun(){
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
            <input type="button" value="목록" class="button" id="moveToList">
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
            <input type="text" class="form-control" id="memberId" name="memberId" value=${sessionScope.member.memberId} readonly="readonly">
            <input type="text" class="form-control" id="nickName" name="nickName" value=${sessionScope.member.nickName} readonly="readonly">        
        </div>
        <div class="form-group">
            <label for="content">내용</label>
            <textarea rows="7" class="form-control" id="content" name="content"></textarea>
        </div>
    </form>
</div>

</body>
</html>
