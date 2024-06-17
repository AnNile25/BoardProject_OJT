<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />     
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
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
	replyRetrieve(); // 댓글 조회
	
	const boardSeq = document.querySelector("#boardSeq").value;
	const title 	= document.querySelector("#title").value; 
	const memberId = document.querySelector("#memberId").innerText.trim();
	const content 	= document.querySelector("#content").value; 
	
	const sessionMemberId = '${sessionScope.memberId}';
	
	const moveToListBTN = document.querySelector("#moveToList");
	const moveToModBTN 	 = document.querySelector("#moveToMod");
	const deleteArticleBTN 	 = document.querySelector("#deleteArticle");
	const replySaveBTN = document.querySelector("#replySave");
	
	const errorMessage = '${errorMessage}';
	console.log("errorMessage: " + errorMessage);
	if (errorMessage && errorMessage.trim() !== '') {
        alert(errorMessage);
    }
		
	console.log("memberId:" + memberId);
	console.log("sessionMemberId:" + sessionMemberId);
	
	function moveToList(){
    	window.location.href = "/qna/retrieveQnaArticle";
    }
	 
	moveToListBTN.addEventListener("click", function(e){
		console.log("moveToListBTN click");
		moveToList()
	});
	
	moveToModBTN.addEventListener("click", function(e){
		console.log("moveToModBTN click");
		if (errorMessage && errorMessage.trim() !== '') {
	        alert(errorMessage);
	        return;
	    } else {
	        const boardSeq = document.querySelector("#boardSeq").value;
	        window.location.href = "/qna/moveToMod?boardSeq=" + boardSeq;
	    }	
	});
	
	deleteArticleBTN.addEventListener("click", function(e){
		console.log("deleteArticleBTN click");
		
		if(window.confirm('삭제 하시겠습니까?')==false){
            return;
        }
		
		$.ajax({
    		type: "GET",
    		url:"/qna/deleteQnaArticle",
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
	
	/* reply */
	replySaveBTN.addEventListener("click",function(e){
		console.log('replySaveBTN click');
		const memberId = '${sessionScope.member.memberId}';	
		const boardSeq = document.querySelector('#boardSeq').value;
		
		const replyContent = document.querySelector('#replyContent').value;
		if(eUtil.isEmpty(replyContent) == true){
            alert('댓글을 확인하세요.');
            document.querySelector('#replyContent').focus();
            return;
        }
		$.ajax({
            type:"POST",
            url: "/reply/saveReply",
            asyn:"true",
            dataType:"json",
            data:{
                "content": replyContent,
                "boardSeq": boardSeq,
                "memberId": memberId
            },
            success:function(data){//통신 성공
                console.log("success msgId:"+data.msgId);
                console.log("success msgContents:"+data.msgContents);
                
                if("1"==data.msgId){
                	alert(data.msgContents);
                	replyRetrieve();//댓글 조회
                	document.querySelector('#replyContent').value = ''; //등록 댓글 초기화
                }else{
                	alert(data.msgContents);
                }
            },
            error:function(data){//실패시 처리
                console.log("error:"+data);
            },
            complete:function(data){//성공/실패와 관계없이 수행!
                console.log("complete:"+data);
            }
        });
	});//--replySaveBTN
	
	function replyRetrieve(){
		const boardSeq = document.querySelector("#boardSeq").value
	
        $.ajax({
            type: "GET",
            url:"/reply/retrieveReply",
            asyn:"true",
            dataType:"json", //return type
            data:{
                "boardSeq": boardSeq  
            },
            success:function(data){//통신 성공
            	let replyDiv = '';
                document.getElementById("replySaveArea").innerHTML = "";
                if(0==data.length){
                	console.log("댓글이 없습니다.");
                	return;
                }
                
                for(let i=0;i<data.length;i++){
                	replyDiv += '<span>등록일:'+data[i].modDt+'</span> \n';                	
                	replyDiv += '<div class="mb-3"> \n';
                	replyDiv += '<input type="hidden" name="replySeq" value="'+data[i].replySeq +'"> \n';                	
                	replyDiv += '<textarea rows="3" class="form-control dyReplyContent" name="dyReplyContent">'+data[i].content+'</textarea> \n';
                	replyDiv += '</div> \n';                	
                }
            document.getElementById("replySaveArea").innerHTML = replyDiv;
	    	},
	        error:function(data){//실패시 처리
	            console.log("error:"+data);
	        },
	        complete:function(data){//성공/실패와 관계없이 수행!
	            console.log("complete:"+data);
	        }
		});
		
	}//--replyRetrieve()
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
	            <input type="button" value="삭제" class="button" id="deleteArticle" >
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
	    
	    <!-- reply -->
		<div id="commentsSection">
			<!-- 기존 댓글 목록 -->
			<div id="replySaveArea">
				<div class="mb-3">
		            <input type="hidden" name="replySeq" value="">
		            <textarea rows="3" class="form-control dyReplyContent" name="dyReplyContent"></textarea>
		        </div>
			</div>
			<!-- 댓글 등록 -->
			<div class="reply-input-area">
				<div class="mb-3">
					<textarea rows="3" class="form-control" id="replyContent"
						name="replyContent"></textarea>
				</div>
				<div class="row justify-content-end" style="margin-bottom: 5px;">
					<div class="col-auto">
						<input type="button" value="댓글 등록" class="button"
							id="replySave">
					</div>
				</div>
			</div>
		</div>
		<!--// reply -------------------------------------------------------------->
	</div>    
</div>

</body>
</html>
