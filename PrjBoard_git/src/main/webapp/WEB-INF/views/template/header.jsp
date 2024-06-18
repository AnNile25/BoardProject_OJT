<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />    
<style>
"header" {
    display: flex; /* 항목들을 가로로 정렬 */
    align-items: center; /* 항목들을 세로 중앙에 위치 */
    background-color: #f8f9fa; /* 배경색 설정 */
    padding: 10px 20px; /* 패딩 추가 */
}
</style>
<div class="header">
	<ul> 
		<li>
			<a href="<c:url value='/'/>">메인</a>
			<a href="<c:url value='/qna/retrieveQnaArticle'/>">Q&A 게시판</a>
			<a href="<c:url value='/login/loginView'/>">로그인</a>
			<a href="<c:url value='/member/moveToReg'/>">회원가입</a>
			<c:if test="${not empty sessionScope.memberId}">
                <a href="<c:url value='/member/personalMemberInfo'/>">내 정보</a>
            </c:if>
            <c:if test="${not empty sessionScope.memberId}">
                <a href="<c:url value='/login/logout'/>">로그아웃</a>
            </c:if>
		</li>
	</ul> 
</div>