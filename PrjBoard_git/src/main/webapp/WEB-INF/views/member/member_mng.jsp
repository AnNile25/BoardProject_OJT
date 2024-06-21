<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />     
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<meta charset="UTF-8">
<title>회원 정보</title>
<style type="text/css">
#list table {
    width: 100%;
    table-layout: fixed; /* 테이블 내 셀의 너비 고정 */
}

#list td {
    word-wrap: break-word; /* 긴 단어나 주소도 셀 내에서 줄바꿈 */
    white-space: normal;   /* 공백 문자가 있는 경우 자연스럽게 줄바꿈 */
}

textarea.form-control {
    width: 100%;
    padding: 8px;
    box-sizing: border-box; /* 패딩과 보더가 너비에 포함되도록 설정 */
    border: 1px solid #ccc;
    border-radius: 4px;
    resize: none; /* 사용자가 크기를 조정하지 못하도록 설정 */
}
</style>
<link rel="stylesheet" type="text/css" href="${CP}/resources/css/common.css">
<script src="${CP}/resources/js/jquery-3.7.1.js"></script>
<script src="${CP}/resources/js/eUtil.js"></script>
<script src="${CP}/resources/js/addressSearchFunction.js"></script>
</head>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded",function(){ 
	console.log("DOMContentLoaded");
	
	const retrieveQnaArticleBTN = document.querySelector("#retrieveQnaArticle");
	const updateMemberBTN 	= document.querySelector("#updateMember");
	const withdrawalMemberBTN = document.querySelector("#withdrawalMember");
	
	const memberId = document.querySelector("#memberId").innerText;
    const memberNameInput = document.querySelector("#memberName");
    const passwordInput = document.querySelector("#password");
    const telInput = document.querySelector("#tel");
    const nickNameInput = document.querySelector("#nickName");
    const emailInput = document.querySelector("#email"); 
    const addressInput = document.querySelector("#address");
    
	 /* 게시글 목록 조회 */
	retrieveQnaArticleBTN.addEventListener("click", function(e){
		console.log("retrieveQnaArticleBTN click");
		window.location.href = "/qna/retrieveQnaArticle";
	});
	 
	withdrawalMemberBTN.addEventListener("click", function(e){
		console.log("withdrawalMemberBTN click");
		if(window.confirm('탈퇴 하시겠습니까?')==false){
            return;
        }
		$.ajax({
    		type: "GET",
    		url:"/member/withdrawalMember",
    		async:"true",
    		dataType:"json",
    		data:{
    			"memberId": memberId
    		},
    		success:function(data){
        		console.log("success data.msgId:"+data.msgId);
        		console.log("success data.msgContents:"+data.msgContents);
                if("1" == data.msgId){
                   alert(data.msgContents);
                   window.location.href = "/login/loginView";
                }else{
                    alert(data.msgContents);
                }
        	},
        	error:function(data){
        		console.log("error:"+data);
        	}
    	});
	});
	
	 /* 회원정보 수정 */
	updateMemberBTN.addEventListener("click", function(e){
        const memberName = memberNameInput.value;
        const password = passwordInput.value;
        const tel = telInput.value;
        const address = addressInput.value + " " + document.getElementById("detailAddress").value;

        /* if (eUtil.isEmpty(memberName) || eUtil.isEmpty(password) || eUtil.isEmpty(tel) |  eUtil.isEmpty(address)) {
                alert("모든 필드를 입력하세요.");
                return;
        } */
        
		if(window.confirm('수정사항을 저장하시겠습니까?')==false){
            return;
        }
		$.ajax({
    		type: "POST",
    		url:"/member/updateMemberInfo",
    		async:true,
    		dataType:"json",
    		data:{
    			"memberId": memberId,
    			"memberName": memberName,
    			"password": password,
    			"tel": tel,
    			"address": address
    		},
    		success:function(data){
        		/* console.log("success data.msgId:"+data.msgId);
        		console.log("success data.msgContents:"+data.msgContents);
                if("1" == data.msgId){
                   alert(data.msgContents);
                   return;
                }else{
                    alert(data.msgContents);
                } */
    			alert(data.msgContents);
                if (data.msgId === "1") { // 성공적으로 데이터가 처리되었다면
                    window.location.reload(); // 페이지를 새로 고침
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
	 
});//-- DOMContentLoaded
</script>
<body>
<div class="container-main">
  <div class="content">
    <!-- 버튼 -->
    <div class="row justify-content-end">
      <div class="col-auto">
        <input type="button" value="게시판" class="button" id="retrieveQnaArticle">
        <input type="button" value="저장" class="button" id="updateMember">
        <input type="button" value="탈퇴" class="button" id="withdrawalMember">
      </div>
    </div>

    <!-- form -->
    <form action="#" name="regFrm" id="regFrm">
      <div class="form-group">
        <h1 id="memberId" class="form-label">${memberId}</h1>
      </div>

      <div class="form-group">
        <label for="memberName" class="form-label">이름</label>
        <input type="text" id="memberName" name="memberName" value="${vo.memberName}" class="form-control" >
      </div>

      <div class="form-group">
        <label for="password" class="form-label">비밀번호</label>
        <input type="text" id="password" name="password" value="${vo.password}" class="form-control" >
      </div>

      <div class="form-group">
        <label for="tel" class="form-label">전화번호</label> 
        <input type="text" id="tel" name="tel" value="${vo.tel}" class="form-control" >
      </div>
      
      <div class="form-group">
        <label for="nickName" class="form-label">닉네임</label> 
       <input type="text" id="nickName" name="nickName" value="${vo.nickName}" class="form-control"  readonly>
      </div>
      
      <div class="form-group">
        <label for="email" class="form-label">전화번호</label> 
       <input type="text" id="email" name="email" value="${vo.email}" class="form-control" readonly >
      </div>

      <div class="form-group">
        <label for="address" class="form-label">주소</label>
        <textarea id="address" name="address"  class="form-control"  rows="2">${vo.address }</textarea>
      </div>
    </form>
    
    <form name="addrForm" id="addrForm" style="margin-top:20px;">
      <div class="form-group">
        <input type="hidden" name="currentPage" value="1">
        <input type="hidden" name="countPerPage" value="10">
        <input type="hidden" name="resultType" value="json">
        <input type="hidden" name="confmKey" id="confmKey" value="devU01TX0FVVEgyMDI0MDYxMjE3MDc1MTExNDgzODM=">
        <input type="text" name="keyword" onkeydown="enterSearch();" placeholder="검색할 주소를 입력하세요.">
        <input type="button" onClick="getAddrLoc();" value="주소검색">
      </div>
      <div class="form-group">
        <label for="detailAddress">상세 주소:</label>
        <input type="text" id="detailAddress" name="detailAddress" placeholder="동, 호수 등 입력" class="form-control">
    </div>
    </form>
    <div id="list"></div>
    
  </div>
</div>
</body>
</html>