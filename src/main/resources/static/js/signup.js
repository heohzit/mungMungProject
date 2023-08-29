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
            authTime();
        }
    });
});
		let intervalId = null;
		function authTime(){
			$("#timeZone").html("<span id='min'>5</span> : <span id='sec'>00</span>");
			intervalId = window.setInterval(function(){
				const min = $("#min").text();
				const sec = $("#sec").text();
				if(sec == "00"){
					if(min == "0"){
						window.clearInterval(intervalId);
						authCode = null;
						$("#authMsg").text("인증 시간이 만료되었습니다.");
						$("#authMsg").css("color","red");
						
					}else{
						const newMin = Number(min) -1;
						$("#min").text(newMin);
						$("#sec").text(59);
					}
				
				}else{
					const newSec = Number(sec) - 1;
					if(newSec < 10){
						$("#sec").text("0"+newSec);
					}else{
					$("#sec").text(newSec);
				}
			}
		},1000);
	}
	
$("#authBtn").on("click", function(){
	if(authCode != null){
		const inputCode = $("#authCode").val();
		if(authCode == inputCode){
			$("#authMsg").text("인증 완료되었습니다.");
			$("#authMsg").css("color","blue");
			window.clearInterval(intervalId);
			$("#timeZone").empty();
			$("#memberEmail").val(email);
			console.log($("#memberEmail").val());
		}else{
			$("#authMsg").text("인증번호를 다시 확인해주세요.");
			$("#authMsg").css("color","red");
		}
	}
});
$("#memberId").on("change",function(){
			const memberId = $(this).val();
			//정규표현식을 통한 유효성 검사
			const idReg = /^[a-z0-9]{4,10}$/;
			if(idReg.test(memberId)){
				$.ajax({
					//MemberController에 작성
					url:"/member/ajaxCheckId",
					type : "get",
					data : {memberId : memberId},
					success : function(data){
					console.log(data);
						if(data == "0"){
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
				$("#ajaxCheckId").text(" " + "영문 소문자/숫자 4~10글자 이내로 입력해주세요.");
				$("#ajaxCheckId").css("color","red");
				$(this).css("border","1px solid red");
				
			}
			//db에서 중복체크(ajax)
		});

//비밀번호
const checkArr =[false,false,false,false,false,false,false,false];
const memberArr = ["user01","user02","user"];
const comment1 = $(".comment1");
const comment2 = $(".comment2");
$("#memberPw").on("change",function(){
    const pwReg = /^[a-z0-9\d$@$!%*?&]{4,10}$/;
    const inputPw = $(this).val();
    const check = pwReg.test(inputPw);
    if(check){
        //정규표현식 만족한 경우
        //중복체크
        comment1.text(" " + "사용 가능한 비밀번호입니다.")
        comment1.css("color","blue");
        $(this).css("border","1px solid blue");
        checkArr[1] = true;
    }else{
        //정규표현식 만족하지 못한 경우
        comment1.text(" " + "영문 소문자/숫자/특수문자 4글자~10글자 이내로 입력해주세요.");
        comment1.css("color","red");
        $(this).css("border","1px solid red");
        checkArr[1] = false;
    }
    if(comment1.text() != ""){
        pwDupCheck();
    }
});
//비밀번호확인 
$("#memberPwRe").on("change",function(){
    pwDupCheck();
});

function pwDupCheck(){
    //비밀번호확인 
    const inputPw = $("#memberPw").val();
    const inputPwRe = $("#memberPwRe").val();
    if(inputPw == inputPwRe){
        comment2.text(" " +"비밀번호와 일치합니다.")
        comment2.css("color","blue");
        $("#memberPwRe").css("border","1px solid blue");
        checkArr[2] = true;
    }else{
        comment2.text(" " +"비밀번호와 일치하지 않습니다.")
        comment2.css("color","red");
        $("#memberPwRe").css("border","1px solid red");
        checkArr[2] = false;
    }
}


	