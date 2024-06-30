<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set var="CP" value="${pageContext.request.contextPath}" />     
<html>
<head>
<script src="${CP}/resources/js/jquery-3.7.1.js"></script>
<script src="${CP}/resources/js/eUtil.js"></script>
<meta charset="UTF-8">
<title>search address</title>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded", function(){
    const keyword = document.getElementById("addressKeyword").value;

    document.getElementById("addressKeyword").addEventListener("keydown", function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            getAddrLoc();
        }
    });

    document.getElementById("searchBtn").addEventListener("click", function() {
        getAddrLoc();
    });
});

function getAddrLoc(){
    if (!checkSearchedWord(document.getElementById("addressKeyword"))) {
        return;
    }

    $.ajax({
        url: "${CP}/member/getAddrApi.do",
        type: "post",
        data: { 
            keyword: $("#addressKeyword").val(),
            currentPage: "1",
            countPerPage: "10",
            resultType: "json"
        },
        dataType: "json",
        success: function(jsonStr){
            $("#list").html("");
            var errCode = jsonStr.results.common.errorCode;
            var errDesc = jsonStr.results.common.errorMessage;
            if(errCode != "0"){
                alert(errCode + "=" + errDesc);
            } else {
                if(jsonStr != null){
                    makeListJson(jsonStr);
                }
            }
        },
        error: function(xhr, status, error){
            alert("에러가 발생했습니다. " + error);
        }
    });
}

function makeListJson(jsonStr){
    var htmlStr = "<table>";
    $(jsonStr.results.juso).each(function(){
        htmlStr += "<tr>";
        htmlStr += "<td onclick='selectAddress(\"" + this.roadAddr + "\")'>" + this.roadAddr + "</td>";
        htmlStr += "</tr>";
    });
    htmlStr += "</table>";
    $("#list").html(htmlStr);
}

function selectAddress(address) {
	console.log("Selected address: " + address);
	window.opener.document.getElementById("keyword").value = address;
    window.close();
}

function checkSearchedWord(obj){
    if(obj.value.length > 0){
        var expText = /[%=><]/ ;
        if(expText.test(obj.value) == true){
            alert("특수문자를 입력 할 수 없습니다.");
            obj.value = obj.value.split(expText).join(""); 
            return false;
        }
        var sqlArray = new Array("OR", "SELECT", "INSERT", "DELETE", "UPDATE", "CREATE", "DROP", "EXEC", "UNION",  "FETCH", "DECLARE", "TRUNCATE");
        var regex;
        for(var i = 0; i < sqlArray.length; i++){
            regex = new RegExp(sqlArray[i], "gi");
            if (regex.test(obj.value)) {
                alert("\"" + sqlArray[i] + "\"와(과) 같은 특정문자로 검색할 수 없습니다.");
                obj.value = obj.value.replace(regex, "");
                return false;
            }
        }
    }
    return true;
}
</script>
</head>
<body>
	<form id="registerForm" name="registerForm">
		<div class="form-group">
			<label for="keyword">주소</label>
	        <input type="text" name="keyword" id="addressKeyword" placeholder="검색할 주소를 입력하세요." >
	        <input type="button" id="searchBtn" value="주소검색" style="flex: 0 0 auto; width: 100px;">
		</div>
    </form>
    <div id="list"></div> 
</body>
</html>