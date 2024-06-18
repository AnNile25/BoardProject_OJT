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

</style>
<link rel="stylesheet" type="text/css" href="${CP}/resources/css/common.css">
<script src="${CP}/resources/js/jquery-3.7.1.js"></script>
<script src="${CP}/resources/js/eUtil.js"></script>
</head>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded",function(){ 
	console.log("DOMContentLoaded");
	
	const moveToListBTN = document.querySelector("#moveToList");
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
	moveToListBTN.addEventListener("click", function(e){
		console.log("moveToListBTN click");
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
		const memberId = document.querySelector("#memberId").innerText;
        const memberName = memberNameInput.value;
        const password = passwordInput.value;
        const tel = telInput.value;
        const address = addressInput.value;

        if (eUtil.isEmpty(memberName) || eUtil.isEmpty(password) || eUtil.isEmpty(tel) |  eUtil.isEmpty(address)) {
                alert("모든 필드를 입력하세요.");
                return;
        }
        
		if(window.confirm('수정사항을 저장하시겠습니까?')==false){
            return;
        }
		$.ajax({
    		type: "POST",
    		url:"/member/updateMember",
    		async:"true",
    		dataType:"json",
    		data:{
    			"memberId": memberId,
    			"memberName": memberName,
    			"password": password,
    			"tel": tel,
    			"address": address
    		},
    		success:function(data){
        		console.log("success data.msgId:"+data.msgId);
        		console.log("success data.msgContents:"+data.msgContents);
                if("1" == data.msgId){
                   alert(data.msgContents);
                   return;
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
	
});//-- DOMContentLoaded
</script>
<body>
<div class="container-main">
  <div class="content">
    <!-- 버튼 -->
    <div class="row justify-content-end">
      <div class="col-auto">
        <input type="button" value="게시판" class="button" id="moveToList">
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
        <input type="text" id="address" name="address" value="${vo.address}" class="form-control" >
      </div>
    </form>
  </div>
</div>
</body>
</html>