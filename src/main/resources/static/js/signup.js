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
    $.ajax({
        url : "/api/auth",
        data : {email : email},
        type : "post",
        success : function(data){
            console.log(data);
            authCode = data;
            $("#auth").show();
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
});
$("#memberId").on("change",function(){
			const memberId = $(this).val();
			//정규표현식을 통한 유효성 검사
			const idReg = /^[a-z0-9]{2,8}$/;
			if(idReg.test(memberId)){
				//유효성이 만족되면 -> db에서 중복체크(ajax)
				$.ajax({
					//MemberController에 작성
					url:"/member/ajaxCheckId",
					type : "get",
					data : {memberId : memberId},
					success : function(data){
					console.log(data);
						if(data == 0){
							$("#ajaxCheckId").text(" " + "사용 가능한 아이디입니다.");
							$("#ajaxCheckId").css("color","blue");
							$("#memberId").css("border","1px solid blue");
						}else{
							$("#ajaxCheckId").text(" " + "이미 사용 중인 아이디입니다.");
							$("#ajaxCheckId").css("color","red");
							$("#memberId").css("border","1px solid red");
							
						}
					}
				});
			}else{
				$("#ajaxCheckId").text(" " + "아이디는 영어소문자/숫자 2~8글자입니다.");
				$("#ajaxCheckId").css("color","red");
				$(this).css("border","1px solid red");
				
			}
			//db에서 중복체크(ajax)
		});

	