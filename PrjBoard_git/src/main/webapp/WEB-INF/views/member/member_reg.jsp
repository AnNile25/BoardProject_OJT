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
<script>
document.addEventListener("DOMContentLoaded", function(){
    console.log("DOMContentLoaded");    

    const saveMemberBTN = document.querySelector("#saveMember");
    
    const memberIdInput = document.querySelector("#memberId");
    const memberNameInput = document.querySelector("#memberName");
    const passwordInput = document.querySelector("#password");
    const telInput = document.querySelector("#tel");
    const nickNameInput = document.querySelector("#nickName");
    const emailInput = document.querySelector("#email");
    
    const memberIdCheckBtn = document.querySelector("#memberIdCheckBtn");
    const nickNameCheckBtn = document.querySelector("#nickNameCheckBtn");
    const emailCheckBtn = document.querySelector("#emailCheckBtn");
    
    let isMemberIdChecked = false;
    let isNickNameChecked = false;
    let isEmailChecked = false;

    memberIdCheckBtn.addEventListener("click", function() {
        const memberId = memberIdInput.value;
        $.ajax({
            type: "POST",
            url: "${CP}/member/checkIdDuplicate",
            async: true,
            dataType: "json",
            data: { memberId: memberId },
            success: function(data) {
                alert(data.msgContents);
                isMemberIdChecked = data.msgId === "1";
            },
            error: function(data) {
                console.log("error:", data);
            }
        });
    });

    nickNameCheckBtn.addEventListener("click", function() {
        const nickName = nickNameInput.value;
        $.ajax({
            type: "POST",
            url: "${CP}/member/checkNickNameDuplicate",
            async: true,
            dataType: "json",
            data: { nickName: nickName },
            success: function(data) {
                alert(data.msgContents);
                isNickNameChecked = data.msgId === "1";
            },
            error: function(data) {
                console.log("error:", data);
            }
        });
    });

    emailCheckBtn.addEventListener("click", function() {
        const email = emailInput.value;
        $.ajax({
            type: "POST",
            url: "${CP}/member/checkEmailDuplicate",
            async: true,
            dataType: "json",
            data: { email: email },
            success: function(data) {
                alert(data.msgContents);
                isEmailChecked = data.msgId === "1";
            },
            error: function(data) {
                console.log("error:", data);
            }
        });
    });

    saveMemberBTN.addEventListener("click", function(e){
        console.log("saveMemberBTN click");

        const memberId = memberIdInput.value;
        const memberName = memberNameInput.value;
        const password = passwordInput.value;
        const tel = telInput.value;
        const nickName = nickNameInput.value;
        const email = emailInput.value;

        if (eUtil.isEmpty(memberId) || eUtil.isEmpty(memberName) || eUtil.isEmpty(password) ||
                eUtil.isEmpty(nickName) || eUtil.isEmpty(tel) || eUtil.isEmpty(email)) {
                alert("모든 필드를 입력하세요.");
                return;
        }

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

        $.ajax({
            type: "POST",
            url: "${CP}/member/joinMember",
            async: true,
            dataType: "json",
            data: {
                "memberId": memberId,
                "memberName": memberName,
                "password": password,
                "tel": tel,
                "nickName": nickName,
                "email": email
            },
            success: function(data) {
                console.log("data.msgId:", data.msgId);
                console.log("data.msgContents:", data.msgContents);

                if ('1' == data.msgId) {
                    alert(data.msgContents);
                    window.location.href = "${CP}/login/loginView";
                } else {
                    alert(data.msgContents);
                }
            },
            error: function(data) {
                console.log("error:", data);
            },
            complete: function(data) {
                console.log("complete:", data);
            }
        });

    });
});
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
</style>
</head>
<body>

<div class="container-main">
    <div class="content">
        <h1>회원가입</h1>
        <form id="registerForm">
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
                <button type="button" class="button" id="saveMember">가입하기</button>
                <a href="${CP}/main/mainView.do" class="button">취소</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
