function getAddrLoc(){
    if (!checkSearchedWord(document.addrForm.keyword)) {
        return;
    }

    $.ajax({
        url: "/member/getAddrApi.do",
        type: "post",
        data: $("#addrForm").serialize(),
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
            alert("에러발생");
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
    document.getElementById("address").value = address;
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

function enterSearch() {
    var evt_code = (window.netscape) ? ev.which : event.keyCode;
    if (evt_code == 13) {    
        event.keyCode = 0;  
        getAddrLoc(); 
    } 
}
