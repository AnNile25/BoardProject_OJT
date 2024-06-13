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
<script src="${CP}/resources/js/eUtil.js"></script>
</head>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded",function(){ 
	console.log("DOMContentLoaded");
	
	const moveToListBTN = document.querySelector("#moveToList");
	const moveToModBTN 	= document.querySelector("#moveToMod");
	const withdrawalMemberBTN = document.querySelector("#withdrawalMember");
	
	const memberId = document.querySelector("#memberId").value;
	
	function moveToList(){
    	window.location.href = "/member/retrieveMember";
    }
	
	moveToListBTN.addEventListener("click", function(e){
		console.log("moveToListBTN click");
		moveToList()
	});
	
	moveToModBTN.addEventListener("click", function(e){
		console.log("moveToModBTN click");
		window.location.href = "/member/moveToMod?memberId=" + memberId;
	});
	
});//-- DOMContentLoaded
</script>
<body>
<div class="container-main">
  <div class="content">
    <!-- 버튼 -->
    <div class="row justify-content-end">
      <div class="col-auto">
        <input type="button" value="목록" class="button" id="moveToList">
        <input type="button" value="수정" class="button" id="moveToMod">
        <input type="button" value="탈퇴" class="button" id="withdrawalMember">
      </div>
    </div>

    <!-- form -->
    <form action="#" name="regFrm" id="regFrm">
      <div class="form-group">
        <h1 id="memberId" class="form-label">${vo.memberId}</h1>
      </div>

      <div class="form-group">
        <label for="memberName" class="form-label">이름</label>
        <input type="text" id="memberName" name="memberName" value="${vo.memberName}" class="form-control readonly-input" readonly>
      </div>

      <div class="form-group">
        <label for="password" class="form-label">비밀번호</label>
        <input type="text" id="password" name="password" value="${vo.password}" class="form-control readonly-input" readonly>
      </div>

      <div class="form-group">
        <label for="tel" class="form-label">전화번호</label>
        <input type="text" id="tel" name="tel" value="010${vo.tel}" class="form-control readonly-input" readonly>
      </div>

      <div class="form-group">
        <label for="nickName" class="form-label">닉네임</label>
        <input type="text" id="nickName" name="nickName" value="${vo.nickName}" class="form-control readonly-input" readonly>
      </div>

      <div class="form-group">
        <label for="email" class="form-label">이메일</label>
        <input type="text" id="email" name="email" value="${vo.email}" class="form-control readonly-input" readonly>
      </div>
    </form>
  </div>
</div>
</body>
</html>