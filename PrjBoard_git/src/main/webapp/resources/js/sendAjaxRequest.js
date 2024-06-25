function sendAjaxRequest(type, url, value, callback) {
    $.ajax({
        type: type,
        url: url,
        async: true,
        dataType: "json",
        data: value,
        success: callback,
        error: function(data) {
            console.log("error:", data);
            alert("서버 요청 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }
    });
}