<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board 상세조회</title>
<style>
.form-group {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}
.form-group label {
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
	console.log("DOMContentLoaded");
	
	const boardSeq = document.querySelector("#boardSeq").value;
	const title 	= document.querySelector("#title").value; 
	const memberId = document.querySelector("#memberId").value;
	const content 	= document.querySelector("#content").value; 
	
	const moveToListBTN = document.querySelector("#moveToList");
	const moveToModBTN 	 = document.querySelector("#moveToMod");
	const doDeleteBTN 	 = document.querySelector("#doDelete");
	
	function moveToList(){
    	window.location.href = "/board/doRetrieve.do";
    }
	 
	moveToListBTN.addEventListener("click", function(e){
		console.log("moveToListBTN click");
		moveToList()
	});
	
	moveToModBTN.addEventListener("click", function(e){
		console.log("moveToModBTN click");
		window.location.href = "/board/moveToMod.do?boardSeq=" + boardSeq;
	});
	
	doDeleteBTN.addEventListener("click", function(e){
		console.log("doDeleteBTN click");
		
		if(window.confirm('삭제 하시겠습니까?')==false){
            return;
        }
		
		$.ajax({
    		type: "GET",
    		url:"/board/doDelete.do",
    		async:"true",
    		dataType:"json",
    		data:{
    			"boardSeq": boardSeq
    		},
    		success:function(data){
        		console.log("success data.msgId:"+data.msgId);
        		console.log("success data.msgContents:"+data.msgContents);
                if("1" == data.msgId){
                   alert(data.msgContents);
                   moveToList();     
                }else{
                    alert(data.msgContents);
                }
        	},
        	error:function(data){
        		console.log("error:"+data);
        	}
    	});
	});	
});//-- DOMContentLoaded
</script>
</head>
<body>

<div class="container-main">
	<div class="content">
		<!-- 버튼 -->
	    <div class="row justify-content-end">
	        <div class="col-auto">
	            <input type="button" value="목록" class="button" id="moveToList">
	            <input type="button" value="수정" class="button" id="moveToMod" >
	            <input type="button" value="삭제" class="button" id="doDelete" >
	        </div>
	    </div>

	    <!-- form -->
	    <form action="#" name="regFrm" id="regFrm">
	    	<input type="hidden" id="boardSeq" name="boardSeq" value="${vo.boardSeq }" readonly>
	  
	        <div class="form-group">
				<h1 id="title" class="form-label">${vo.title}</h1>
			</div> 
			
			<div class="form-group" style="display: flex; align-items: center;">
				<label id="memberId" style="margin-right: 10px;">${vo.memberId}</label>
				<div style="flex-grow: 1; text-align: left; color: gray;">
					조회 ${vo.readCnt} | 추천 ${vo.likeCnt} | ${vo.modDt}</div>
			</div>
	        
	        <div class="form-group">
				<label for="content" class="form-label"></label>
				<textarea rows="7" class="form-control readonly-input"
					id="content" name="content" readonly="readonly">${vo.content}</textarea>
			</div>
	    </form> 
	</div>    
</div>

</body>
</html>
