const allAgree = document.querySelector("#allAgree");
allAgree.addEventListener("click",function(){
    const status = this.checked;
    const agree = document.querySelectorAll("[name=agree]");
    agree.forEach(function(item){
        item.checked = status;
    })
});

const select = document.querySelector(".email-choice");
select.addEventListener("change",function(){
    const input = document.querySelector("[name=memberEmail2]");
    input.value = select.value;
});

let authCode = null;
let email = null;
$("#sendBtn").on("click",function(){
    email = $("#memberEmail1").val() + '@' + $("#memberEmail2").val();
    //이메일주소 유효성검사를 통해서 정상적인 이메일인지 확인 후 다음로직진행(비정상이면 안내메세지 전송)
    $.ajax({
        url : "/api/auth",
        data : {email : email},
        type : "post",
        success : function(data){
            console.log(data);
            authCode = data;
            $("#auth").slideDown();
        }
    });
});
$("#authBtn").on("click", function(){
	if(authCode != null){
		const inputCode = $("#authCode").val();
		if(authCode == inputCode){
			$("#authMsg").text("인증 완료되었습니다.");
			$("#authMsg").css("color","blue");
			$("#memberEmail").val(email);
			console.log($("#memberEmail").val());
		}else{
			$("#authMsg").text("인증번호를 확인해주세요.");
			$("#authMsg").css("color","red");
		}
	}
})
	