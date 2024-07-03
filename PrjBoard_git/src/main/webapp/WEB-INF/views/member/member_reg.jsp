<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />     
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" type="text/css" href="${CP}/resources/css/common.css">
<script src="${CP}/resources/js/jquery-3.7.1.js"></script>
<script src="${CP}/resources/js/eUtil.js"></script>
<script src="${CP}/resources/js/sendAjaxRequest.js"></script>
<script>
function openAddressSearchPopup() {
    window.open("/main/addressSearchPopup", "주소 검색", "width=600,height=400,scrollbars=yes");
}
document.addEventListener("DOMContentLoaded", function(){
    const saveMemberBTN = document.getElementById("saveMember");
        
    let isMemberIdChecked = false;
    let isNickNameChecked = false;
    let isEmailChecked = false;
    
    $("#memberIdCheckBtn").click(function() {
	    const memberId = document.getElementById("memberId").value;
        sendAjaxRequest("POST", `${CP}/member/checkMemberIdDuplicate`, { memberId: memberId }, function(data) {
            alert(data.msgContents);
            isMemberIdChecked = data.msgId === "1";
        });
    });

    document.querySelector("#nickNameCheckBtn").addEventListener("click", function() {
	    const nickName = document.getElementById("nickName").value;
        sendAjaxRequest("POST", `${CP}/member/checkNickNameDuplicate`, { nickName: nickName }, function(data) {
            alert(data.msgContents);
            isNickNameChecked = data.msgId === "1";
        });
    });

    document.querySelector("#emailCheckBtn").addEventListener("click", function() {
	    const email= document.getElementById("email").value;
        sendAjaxRequest("POST", `${CP}/member/checkEmailDuplicate`, { email: email }, function(data) {
            alert(data.msgContents);
            isEmailChecked = data.msgId === "1";
        });
    });
    
    saveMemberBTN.addEventListener("click", function(e){
    	e.preventDefault();
    	
    	const memberId = document.getElementById("memberId").value;
        const memberName = document.getElementById("memberName").value;
        const password = document.getElementById("password").value;
        const tel = document.getElementById("tel").value;
        const nickName = document.getElementById("nickName").value;
        const email= document.getElementById("email").value;
        const address = document.getElementById("keyword").value + " " + document.getElementById("detailAddress").value;

        if (!isMemberIdChecked) {
            alert("아이디 중복 체크를 해주세요.");
            return;
        }
        if (!isNickNameChecked) {
            alert("닉네임 중복 체크를 해주세요.");
            return;
        }
        if (!isEmailChecked) {
            alert("이메일 중복 체크를 해주세요.");
            return;
        }
        if(window.confirm("등록 하시겠습니까?") == false){
            return;
        }
        
        sendAjaxRequest("POST", `${CP}/member/joinMember`, {
        	"memberId": memberId,
            "memberName": memberName,
            "password": password,
            "tel": tel,
            "nickName": nickName,
            "email": email,
            "address": address
        }, function(data) {
        	alert(data.msgContents);
        	 if ('1' == data.msgId) {
                 window.location.href = `${CP}/login/loginView`;
             }
        });
    }); //-- saveMemberBTN
}); // --DOMContentLoaded
</script>
<style>
.form-group {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}
.form-group label {
  flex: 0 0 100px;
  margin-right: 10px;
  text-align: right;
}
.form-group input, .form-group textarea {
  flex: 1;
}
#list table {
    width: 100%;
    table-layout: fixed; /* 테이블 내 셀의 너비 고정 */
}
#list td {
    word-wrap: break-word; /* 긴 단어나 주소도 셀 내에서 줄바꿈 */
    white-space: normal;   /* 공백 문자가 있는 경우 자연스럽게 줄바꿈 */
}
</style>
</head>
<body>

<div class="container-main">
    <div class="content">
        <h1>회원가입</h1>
        <form id="registerForm" name="registerForm">
            <div class="form-group">
                <label for="memberId">아이디</label>
                <input type="text" id="memberId" name="memberId" class="form-control" />
                <button type="button" id="memberIdCheckBtn">중복확인</button>
            </div>
            <div class="form-group">
                <label for="memberName">이름</label>
                <input type="text" id="memberName" name="memberName" class="form-control" />
            </div>
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" class="form-control" />
            </div>
            <div class="form-group">
                <label for="tel">전화번호</label>
                <input type="tel" id="tel" name="tel" class="form-control" pattern="\d{8}" placeholder="전화번호 8자리를 입력하세요.(010 - xxxx - xxxx)" />
            </div>
            <div class="form-group">
                <label for="nickName">닉네임</label>
                <input type="text" id="nickName" name="nickName" class="form-control" />
                <button type="button" id="nickNameCheckBtn">중복확인</button>
            </div>
            <div class="form-group">
                <label for="email">이메일</label>
                <input type="email" id="email" name="email" class="form-control" />
                <button type="button" id="emailCheckBtn">중복확인</button>
            </div>	
            <div class="form-group">
                <label for="keyword">주소</label>
                <input type="text" id="keyword" name="keyword" class="form-control" readonly placeholder="주소 검색을 해주세요."/>
                <input type="button" onClick="openAddressSearchPopup();" value="주소검색" style="flex: 0 0 auto; width: 100px;">
            </div>
            <div class="form-group">
		        <label for="detailAddress">상세 주소:</label>
		        <input type="text" id="detailAddress" name="detailAddress" placeholder="동, 호수 등 입력" class="form-control">
		    </div>
			
            <div class="form-group">
                <button type="button" class="button" id="saveMember">가입하기</button>
                <a href="${CP}/main/mainView.do" class="button">취소</a>
            </div>
        </form>
    	<div id="list"></div>
    </div>
</div>

</body>
</html>
