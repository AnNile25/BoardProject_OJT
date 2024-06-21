<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
</head>
<script src="${CP}/resources/js/jquery-3.7.1.js"></script>
<script src="${CP}/resources/js/eUtil.js"></script>
<script type="text/javascript">
function changePassword(){
	const memberId = document.querySelector("#memberId").value;
	const password = document.querySelector("#password").value;
	if (eUtil.isEmpty(password)) {
        alert("변경하실 비밀번호를 입력하세요.");
        return;
	}
	if(window.confirm('수정사항을 저장하시겠습니까?')==false){
        return;
    }
	$.ajax({
		type: "POST",
		url:"/member/changeMemberPassword",
		async:true,
		dataType:"json",
		data:{
			"memberId": memberId,
			"password": password
		},
		success:function(data){
			alert(data.msgContents);
            if (data.msgId === "1") {
                window.close();
            }
    	},
    	error:function(data){
    		console.log("error:"+data);
    	},
        complete:function(data){
            console.log("complete:"+data);
        }
	});
}
</script>
<body>
<label for="memberId" class="form-label">${memberId } 회원</label>
<input type="hidden"	id="memberId" value=${memberId }>
<div class="form-group">
	<label for="password">변경할 비밀번호</label>	
  	<input type="password" id="password" name="password" class="form-control" />
  	<input type="button" onClick="changePassword();" value="등록">
</div>
      
</body>
</html>