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
	
	const retrieveQnaArticleBTN = document.querySelector("#retrieveQnaArticle");
	const viewQnaArticleModBTN 	 = document.querySelector("#viewQnaArticleMod");
	const deleteArticleBTN 	 = document.querySelector("#deleteArticle");
	const replySaveBTN = document.querySelector("#replySave");
	
	const errorMessage = '${errorMessage}';
	console.log("errorMessage: " + errorMessage);
	if (errorMessage && errorMessage.trim() !== '') {
        alert(errorMessage);
    }
		
	console.log("memberId:" + memberId);
	console.log("sessionMemberId:" + sessionMemberId);
	
	function retrieveQnaArticle(){
    	window.location.href = "/qna/retrieveQnaArticle";
    }
	 
	retrieveQnaArticleBTN.addEventListener("click", function(e){
		console.log("retrieveQnaArticleBTN click");
		retrieveQnaArticle()
	});
	
	viewQnaArticleModBTN.addEventListener("click", function(e){
		console.log("viewQnaArticleModBTN click");
		if (errorMessage && errorMessage.trim() !== '') {
	        alert(errorMessage);
	        return;
	    } else {
	        const boardSeq = document.querySelector("#boardSeq").value;
	        window.location.href = "/qna/viewQnaArticleMod?boardSeq=" + boardSeq;
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
                   retrieveQnaArticle();
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
                	replyRetrieve();
                	document.querySelector('#replyContent').value = ''; //등록 댓글 초기화
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
	});//--replySaveBTN
	
	function replyRetrieve(){
		const boardSeq = document.querySelector("#boardSeq").value
	
        $.ajax({
            type: "GET",
            url:"/reply/retrieveReply",
            asyn:"true",
            dataType:"json",
            data:{
                "boardSeq": boardSeq  
            },
            success:function(data){
            	let replyDiv = '';
                document.getElementById("replySaveArea").innerHTML = "";
                if(0==data.length){
                	console.log("댓글이 없습니다.");
                	return;
                }
                
                for(let i=0;i<data.length;i++){
                	replyDiv += '<span>'+data[i].memberId+'\n'+data[i].modDt+'</span> \n';
                	replyDiv += '<div class="mb-3"> \n';
                	replyDiv += '<input type="hidden" name="replySeq" value="'+data[i].replySeq +'"> \n';
                	replyDiv += '<textarea rows="3" class="form-control dyReplyContent" name="dyReplyContent">'+data[i].content+'</textarea> \n';
                	replyDiv += '<input type="button" class="button deleteReplyBtn" value="삭제">';
                	replyDiv += '<input type="button" class="button updateReplyBtn" value="수정">';
                	replyDiv += '</div> \n';
                }
            	document.getElementById("replySaveArea").innerHTML = replyDiv;
            
	            /* 이벤트 리스너 등록 (댓글 삭제/ 수정) */
	            document.querySelectorAll(".deleteReplyBtn").forEach(function(button) {
	            	button.addEventListener("click", function() {
	            		const replySeq = this.parentElement.querySelector("input[name=replySeq]").value;
	            		deleteReply(replySeq);
	            	});
	            });
	            document.querySelectorAll(".updateReplyBtn").forEach(function(button) {
	            	button.addEventListener("click", function() {
	            		const replySeq = this.parentElement.querySelector("input[name='replySeq']").value;
                        const content = this.parentElement.querySelector("textarea[name='dyReplyContent']").value;
	            		updateReply(replySeq, content);
	            	});
	            });
	    	},
	        error:function(data){
	            console.log("error:"+data);
	        },
	        complete:function(data){
	            console.log("complete:"+data);
	        }
		});		
	}//--replyRetrieve()
	
	/* deleteReply */
	function deleteReply(replySeq){
		if(window.confirm("댓글을 삭제하시겠습니까?") == false) {
			return;
		}
		$.ajax({
            type:"GET",
            url: "/reply/deleteReply",
            asyn:"true",
            dataType:"json",
            data:{
                "replySeq": replySeq
            },
            success:function(data){                
               	alert(data.msgContents);
                if("1"==data.msgId){
                	replyRetrieve();
                }
            },
            error:function(data){
                console.log("error:"+data);
            }
        });
	}
	/* updateReply */
	function updateReply(replySeq, content){
		if(eUtil.isEmpty(content)){
			alert("댓글을 입력하세요.");
			return;
		}
		if(window.confirm("댓글을 수정하시겠습니까?") == false) {
			return;
		}
		$.ajax({
            type:"POST",
            url: "/reply/updateReply",
            asyn:"true",
            dataType:"json",
            data:{
                "replySeq": replySeq,
                "content": content
            },
            success:function(data){                
               	alert(data.msgContents);
                if("1"==data.msgId){
                	replyRetrieve();
                }
            },
            error:function(data){
                console.log("error:"+data);
            }
        });
	}
});//-- DOMContentLoaded
</script>
</head>
<body>

<div class="container-main">
	<div class="content">
		<!-- 버튼 -->
	    <div class="row justify-content-end">
	        <div class="col-auto">
	            <input type="button" value="목록" class="button" id="retrieveQnaArticle">
	            <input type="button" value="수정" class="button" id="viewQnaArticleMod" >
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
		            <input type="button" class="button deleteReplyBtn" value="삭제">
		            <input type="button" class="button updateReplyBtn" value="수정">
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
