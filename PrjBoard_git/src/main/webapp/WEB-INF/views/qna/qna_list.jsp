<%@page import="com.gaea.work.qna.QnaVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="CP" value="${pageContext.request.contextPath}" scope="page" />     
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<link rel="stylesheet" href="${CP}/resources/css/common.css">
<meta charset="UTF-8">
<title>Board List</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="${CP}/resources/js/jquery-3.7.1.js"></script>
<script src="${CP}/resources/js/eUtil.js"></script>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded",function(){
	console.log("DOMContentLoaded"); 
	
	const moveToRegBTN = document.querySelector("#moveToReg");//등록 버튼
	const rows = document.querySelectorAll("#boardTable>tbody>tr");
	
	// 에러 메시지 처리
    const errorMessage = '${errorMessage}';
	if (errorMessage && errorMessage.trim() !== '') {
	    alert(errorMessage);
	}
	
	// boardTable 이벤트
	rows.forEach(function(row) {
		row.addEventListener('click', function(e) {
			let cells = row.getElementsByTagName("td");
			
			const boardSeq = cells[7].innerText; //게시글 seq 받기
			console.log('boardSeq:' + boardSeq);
		    
			window.location.href = "/qna/selectOneQna?boardSeq=" + boardSeq;
		});
	});
	
	moveToRegBTN.addEventListener("click", function(e) {
		console.log("moveToRegBTN click");
		window.location.href = "${CP}/qna/moveToReg"
	});
	
});//--DOMContentLoaded
	/* 
function fn_search(){
	$("#listForm").submit();
	return false;
} */

</script>
</head>
<body>

<div>
	<form action="#" method="get" id="listForm">
	
		<!-- 제목 -->
	    <div class="row">
	        <div class="col-lg-12">
	            <h1 class="page-header">게시글 목록</h1>
	        </div>
	    </div>
	    
	    <!-- 버튼 -->
		<div style="margin-bottom:10px">
			<input type="button" value="글쓰기" class="button" id="moveToReg">
		</div>
	    
	    <!-- 게시글 table -->
	    <table class="table" id="boardTable">
	    	<thead>
				<tr >
					<th>NO</th>
					<th>제목</th>
					<th>내용</th>
					<th>등록일</th>
					<th>작성자</th>
					<th>조회수</th>
				</tr>
			</thead> 
			<tbody>
				<c:choose>
					<c:when test="${ not empty list }">
						<c:forEach var="vo" items="${list}" varStatus="status">
							<tr>
								<td><c:out value="${paging.startRow + status.index}"/> </td>
								<td><c:out value="${vo.title}"/></td>
								<td><c:out value="${vo.content}"/></td>
								<td><c:out value="${vo.modDt}"/></td>
								<td><c:out value="${vo.memberId}" /></td>
								<td><c:out value="${vo.readCnt}" /></td>
								<td style="display: none;"><c:out value="${vo.likeCnt}" /></td>
								<td style="display: none;"><c:out value="${vo.boardSeq}" /></td>
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
	    
	    <!-- 페이징 -->
		<c:if test="${paging.page > 1}">
		    <a href="${CP}/qna/retrieveQnaArticle?page=1">First</a>
		    <a href="${CP}/qna/retrieveQnaArticle?page=${paging.page - 1}">Prev</a>
		</c:if>
		
		<!-- 페이지 네비게이션 -->
		<c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}">
		    <a href="${CP}/qna/retrieveQnaArticle?page=${i}" class="<c:if test='${i == paging.page}'>active</c:if>">${i}</a>
		</c:forEach>
		
		<c:if test="${paging.page < paging.maxPage}">
		    <a href="${CP}/qna/retrieveQnaArticle?page=${paging.page + 1}">Next</a>
		    <a href="${CP}/qna/retrieveQnaArticle?page=${paging.maxPage}">Last</a>
		</c:if>
    <%-- 
	    <!-- 검색[s] -->
		<div class="card-header py-3">
		    <input type="text" id="searchKeyword" name="searchKeyword" value="${paramVO.searchKeyword }" style="width:200px; height:40px; margin-top:10px;" placeholder="검색어를 입력하세요." />
			<a href="#" onclick="fn_search();" class="btn btn-primary">검색</a>
		</div> --%>
    </form>
</div>

</body>
</html>