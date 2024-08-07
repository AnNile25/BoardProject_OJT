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
<script src="${CP}/resources/js/sendAjaxRequest.js"></script>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded",function(){
	console.log("DOMContentLoaded"); 
	
	const viewQnaArticleRegBTN = document.querySelector("#viewQnaArticleReg");//등록 버튼
	const rows = document.querySelectorAll("#boardTable>tbody>tr");
	
	// 에러 메시지 처리
    const errorMessage = '${errorMessage}';
	if (errorMessage && errorMessage.trim() !== '') {
	    alert(errorMessage);
	    history.back(); 
        return;
	} 
	
	// boardTable 이벤트
	rows.forEach(function(row) {
		row.addEventListener('click', function(e) {
			let cells = row.getElementsByTagName("td");
			
			const boardSeq = cells[7].innerText; //게시글 seq 받기
			console.log('boardSeq:' + boardSeq);
		    
			window.location.href = "/qna/viewQnaArticleDetail?boardSeq=" + boardSeq;
		});
	});
	
	viewQnaArticleRegBTN.addEventListener("click", function(e) {
		console.log("viewQnaArticleRegBTN click");
		window.location.href = "${CP}/qna/viewQnaArticleReg"
	});
	
	document.querySelector("#searchKeywordBtn").addEventListener("click", function(e) {
		e.preventDefault();
        document.querySelector("#listForm").submit(); 
	});
	
	// 검색 날짜 제한 (max)
	const today = new Date();
	const todayStr = today.toISOString().split('T')[0];
	const sevenDaysAgo = new Date();
	sevenDaysAgo.setDate(today.getDate() - 7);
	const sevenDaysAgoStr = sevenDaysAgo.toISOString().split('T')[0];
    
    const startDateInput = document.getElementById("startDate");
    const endDateInput = document.getElementById("endDate");
    
    startDateInput.setAttribute("max", todayStr);
    endDateInput.setAttribute("max", todayStr);

    startDateInput.setAttribute("min", sevenDaysAgoStr);
    endDateInput.setAttribute("min", sevenDaysAgoStr);
    
    startDateInput.addEventListener("change", function() {
        endDateInput.setAttribute("min", startDateInput.value);
    });
    
    endDateInput.addEventListener("change", function() {
        startDateInput.setAttribute("max", endDateInput.value);
    });
	
});
</script>
</head>
<body>
<div id="error-message" style="display:none;">${errorMessage}</div>
<div>
	<form action="${CP}/qna/retrieveQnaArticle" method="get" id="listForm">
	
		<!-- 제목 -->
	    <div class="row">
	        <div class="col-lg-12">
	            <h1 class="page-header">게시글 목록</h1>
	        </div>
	    </div>
	    
	    <!-- 버튼 -->
		<div style="margin-bottom:10px">
			<input type="button" value="글쓰기" class="button" id="viewQnaArticleReg">
		</div>		
		
	    <!-- 검색 -->
		<div class="card-header py-3" style="text-align: center;">
		    <input type="date" id="startDate" name="startDate" value="${paging.startDate}" placeholder="시작 날짜">~
            <input type="date" id="endDate" name="endDate" value="${paging.endDate}" placeholder="종료 날짜">
		    <input type="text" id="searchKeyword" name="searchKeyword" value="${paging.searchKeyword }" style="width:200px; margin-top:10px;" placeholder="검색어를 입력하세요." />
			<a href="#" id="searchKeywordBtn" class="btn btn-primary">검색</a>
		</div>
		<br>
	    
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
								<td><c:out value="${((paging.nowPage-1) * paging.cntPerPage) + status.index + 1}"/> </td>
								<td><c:out value="${vo.title}"/>  (<c:out value="${vo.replyCnt}"/>)</td>
								<td><c:out value="${vo.content}"/></td>
								<td><c:out value="${vo.modDt}"/></td>
								<td><c:out value="${vo.memberId}" /></td>
								<td><c:out value="${vo.readCnt}" /></td>
								<td style="display: none;"><c:out value="${vo.likeCnt}" /></td>
								<td style="display: none;"><c:out value="${vo.boardSeq}" /></td>
							</tr>              
						</c:forEach> 
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
	    <div style="display: block; text-align: center;">		
		    <c:url var="baseUrl" value="${CP}/qna/retrieveQnaArticle">
		        <c:param name="searchKeyword" value="${paging.searchKeyword}" />
		        <c:param name="startDate" value="${paging.startDate}" />
		        <c:param name="endDate" value="${paging.endDate}" />
		        <c:param name="cntPerPage" value="${paging.cntPerPage}" />
		    </c:url>
		    
			<c:if test="${paging.startPage != 1 }">
				<a href="${baseUrl }&nowPage=${paging.startPage-1}">&lt;</a>
			</c:if>			
			<c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="p">
		        <c:choose>
		            <c:when test="${p == paging.nowPage }">
		                <b>${p }</b>
		            </c:when>
		            <c:when test="${p != paging.nowPage }">
		                <a href="${baseUrl}&nowPage=${p }">${p }</a>
		            </c:when>
		        </c:choose>
		    </c:forEach>		    
		    <c:if test="${paging.endPage != paging.lastPage}">
		        <a href="${baseUrl}&nowPage=${paging.endPage+1 }">&gt;</a>
		    </c:if>
		</div>
						
    </form>
</div>

</body>
</html>