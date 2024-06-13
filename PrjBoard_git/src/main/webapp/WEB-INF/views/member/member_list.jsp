<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />     
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<meta charset="UTF-8">
<title>회원 목록</title>
<link rel="stylesheet" type="text/css" href="${CP}/resources/css/common.css">
<script src="${CP}/resources/js/eUtil.js"></script>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded",function(){
	console.log("DOMContentLoaded");
	
	const moveToRegBTN = document.querySelector("#moveToReg"); // 회원가입 버튼
	const rows = document.querySelectorAll("#memberTable>tbody>tr");
	
	// memberTable 이벤트
	rows.forEach(function(row) {
		row.addEventListener('click', function(e) {
			let cells = row.getElementsByTagName("td");
			
			const memberId = cells[1].innerText;
			
			// 회원 정보 조회
			window.location.href = "/member/personalMemberInfo?memberId=" + memberId;
		});
	});
	
	moveToRegBTN.addEventListener("click", function(e) {
		console.log("moveToRegBTN click");
	    		
		window.location.href = "${CP}/member/moveToReg"
	});
	
});//--DOMContentLoaded
</script>
<body>

<div>
	<!-- 제목 -->
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">회원 목록</h1>
        </div>
    </div>
    
    <!-- 버튼 -->
	<div style="margin-bottom:10px">
		<input type="button" value="회원 가입" class="button" id="moveToReg">
	</div>
	
	<!-- 게시글 table -->
    <table class="table" id="memberTable">
    	<thead>
			<tr >
				<th>NO</th>
				<th>아이디</th>
				<th>이름</th>
				<th>닉네임</th>
				<th>이메일</th>
				<th>가입일</th>
			</tr>
		</thead> 
		<tbody>
			<c:choose>
				<c:when test="${ not empty list }">
					<c:forEach var="vo" items="${list}" varStatus="status">
						<tr>
							<td><c:out value="${vo.no}"/> </td>
							<td><c:out value="${vo.memberId}"/></td>
							<td><c:out value="${vo.memberName}"/></td>
							<td><c:out value="${vo.nickName}"/></td>
							<td><c:out value="${vo.email}" /></td>
							<td><c:out value="${vo.joinDt}" /></td>
						</tr>              
					</c:forEach>
					<!--// 반복문 -->      
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="99" class="text-center">조회된 데이터가 없습니다..</td>
					</tr>              
				</c:otherwise>
			</c:choose>
		</tbody>
    </table>
</div>

</body>
</html>