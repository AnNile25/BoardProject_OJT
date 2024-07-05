function sendAjaxRequest(type, url, value, callback) {
    $.ajax({
        type: type,
        url: url,
        async: true,
        dataType: "json",
        data: value,
        success: callback,
        error: function(error) {
            console.log("error:", error);
            if (error.responseJSON && error.responseJSON.msgContents) {
                alert(error.responseJSON.msgContents);
            } else {
            	alert("정의되지 않은 에러 발생");
            }
        }
    });
}