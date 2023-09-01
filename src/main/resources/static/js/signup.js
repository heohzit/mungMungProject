//이용약관 동의
$(document).ready(function() {
    $("#allAgree").click(function() {
        if($("#allAgree").is(":checked")) $("input[name=agree]").prop("checked", true);
        else $("input[name=agree]").prop("checked", false);
    });
    
    $("input[name=agree]").click(function() {
        var total = $("input[name=agree]").length;
        var checked = $("input[name=agree]:checked").length;
        
        if(total != checked) $("#allAgree").prop("checked", false);
        else $("#allAgree").prop("checked", true); 
    });
});

//이메일연결
const select = document.querySelector(".email-choice");
select.addEventListener("change",function(){
    const input = document.querySelector("[name=memberEmail2]");
    input.value = select.value;
});

//이메일 인증번호
let authCode = null;
let email = null;
$("#sendBtn").on("click",function(){
    email = $("#memberEmail1").val() + '@' + $("#memberEmail2").val();
    $.ajax({
        url : "/api/auth",
        data : {email : email},
        type : "post",
        success : function(data){
            authCode = data;
            $("#auth").show();
            authTime();
        }
    });
});

//이메일 인증시간
let intervalId = null;
function authTime() {
	$("#timeZone").html("<span id='min'>5</span> : <span id='sec'>00</span>");
	intervalId = window.setInterval(function () {
		const min = $("#min").text();
		const sec = $("#sec").text();
		if (sec == "00") {
			if (min == "0") {
				window.clearInterval(intervalId);
				authCode = null;
				$("#authMsg").text("인증 시간이 만료되었습니다.");
				$("#authMsg").css("color", "red");

			} else {
				const newMin = Number(min) - 1;
				$("#min").text(newMin);
				$("#sec").text(59);
			}

		} else {
			const newSec = Number(sec) - 1;
			if (newSec < 10) {
				$("#sec").text("0" + newSec);
			} else {
				$("#sec").text(newSec);
			}
		}
	}, 1000);
}


//유효성검사 아이디
const checkArr =[false,false,false,false,false,false,false];
$("#memberId").on("change",function(){
	//정규표현식을 통한 유효성 검사
	const idReg = /^[a-z0-9]{4,12}$/;
	const memberId = $(this).val();
	const check = idReg.test(memberId);
	if (idReg.test(memberId)) {
		$.ajax({
			//MemberController에 작성
			url: "/member/ajaxCheckId",
			type: "get",
			data: { memberId: memberId },
			success: function (data) {
				console.log(data);
				if (data == "0") {
					$("#ajaxCheckId").text(" " + "사용 가능한 아이디입니다.");
					$("#ajaxCheckId").css("color", "green");
					$("#ajaxCheckId").css("font-size", "14px");
					$("#memberId").css("border", "1px solid green");
					checkArr[0] = true;
				} else {
					$("#ajaxCheckId").text(" " + "이미 사용 중인 아이디입니다.");
					$("#ajaxCheckId").css("color", "red");
					$("#ajaxCheckId").css("font-size", "14px");
					$("#memberId").css("border", "1px solid red");
					checkArr[0] = false;
				}
			}
		});
	} else {
		$("#ajaxCheckId").text(" " + "영문 소문자/숫자 4~12글자 이내로 입력해주세요.");
		$("#ajaxCheckId").css("color", "red");
		$("#ajaxCheckId").css("font-size", "14px");
		$(this).css("border", "1px solid red");
	}
	//db에서 중복체크(ajax)
});


//비밀번호
const comment = $(".comment");
$("#memberPw").on("change",function(){
    const inputPw = $(this).val();
	const pwReg = [
		/^.{8,12}$/,
		/[a-z]/,
		/[0-9]/,
		/[!@#$%]/
	];
	let count = 0;
	for(let i = 0; i<pwReg.length; i++){
		const check = pwReg[i].test(inputPw);
		if(check){
			count++;
		}
	}
    if(count == pwReg.length){
        //정규표현식 만족한 경우
        //중복체크
        comment.eq(0).text(" " + "사용 가능한 비밀번호입니다.")
        comment.eq(0).css("color","green");
        comment.eq(0).css("font-size", "14px");
        $(this).css("border","1px solid green");
        checkArr[1] = true;
    }else{
        //정규표현식 만족하지 못한 경우
        comment.eq(0).text(" " + "영문 소문자/숫자/특수문자(!,@,#,$,%만 가능) 8글자~12글자 이내로 입력해주세요.");
        comment.eq(0).css("color","red");
        comment.eq(0).css("font-size", "14px");
        $(this).css("border","1px solid red");
        checkArr[1] = false;
    }
    if(comment.eq(0).text() != ""){
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
        comment.eq(1).text(" " +"비밀번호와 일치합니다.")
        comment.eq(1).css("color","green");
        comment.eq(1).css("font-size", "14px");
        $("#memberPwRe").css("border","1px solid green");
        checkArr[2] = true;
    }else{
        comment.eq(1).text(" " +"비밀번호와 일치하지 않습니다.")
        comment.eq(1).css("color","red");
        comment.eq(1).css("font-size", "14px");
        $("#memberPwRe").css("border","1px solid red");
        checkArr[2] = false;
    }
}

// 이름
$("#memberName").on("change",function(){
    const nameReg = /^[가-힣]{1,7}$/;
    const inputName = $(this).val();
    const check = nameReg.test(inputName);
    if(check){
        comment.eq(2).text(" " +"사용 가능한 이름입니다.");
        comment.eq(2).css("color","green");
        comment.eq(2).css("font-size", "14px");
        $(this).css("border","1px solid green");
        checkArr[3] = true;
    }else{
        comment.eq(2).text(" " +"한글로 1~7글자입니다.");
        comment.eq(2).css("color","red");
        comment.eq(2).css("font-size", "14px");
        $(this).css("border","1px solid red");
        checkArr[3] = false;
    }
});

// 전화번호
$("#memberPhone").on("change", function(){
	const phoneReg = /^010-?([0-9]{3,4})-?([0-9]{4})$/;
	const inputPhone = $(this).val();
	const check = phoneReg.test(inputPhone);
	if(check){
        comment.eq(3).text(" " +"사용 가능한 휴대폰 번호입니다.");
        comment.eq(3).css("color","green");
        comment.eq(3).css("font-size", "14px");
        $(this).css("border","1px solid green");
        checkArr[4] = true;
    }else{
        comment.eq(3).text(" " +"휴대폰 번호를 입력해주세요.");
        comment.eq(3).css("color","red");
        comment.eq(3).css("font-size", "14px");
        $(this).css("border","1px solid red");
        checkArr[4] = false;
    }
});

// 이메일 인증번호 일치
$("#authBtn").on("click", function(){
	if(authCode != null){
		const inputCode = $("#authCode").val();
		if(authCode == inputCode){
			$("#authMsg").text(" " +"인증이 완료되었습니다.");
			$("#authMsg").css("color","green");
			$("#authMsg").css("font-size", "14px");
			window.clearInterval(intervalId);
			$("#timeZone").empty();
			$("#memberEmail").val(email);
			console.log($("#memberEmail").val());
			checkArr[5] = true;
		}else{
			$("#authMsg").text(" " +"인증번호를 다시 확인해주세요.");
			$("#authMsg").css("color","red");
			$("#authMsg").css("font-size", "14px");
			checkArr[5] = false;
		}
	}
});


// submit금지
$(".signbtn").on("click",function(event){
	if($("#agree1").is(":checked") && $("#agree2").is(":checked")){
		checkArr[6] = true;
	}else{
		checkArr[6] = false;
	}
    const check = checkArr[0] && checkArr[1] && checkArr[2] && checkArr[3] && checkArr[4] && checkArr[5] && checkArr[6];
    if(!check){
        event.preventDefault();
		if(!checkArr[0]){
			alert("아이디를 확인해주세요.");
		}else if(!checkArr[1]){
			alert("비밀번호를 확인해주세요.");
		}else if(!checkArr[2]){
			alert("비밀번호 확인을 확인해주세요.");
		}else if(!checkArr[3]){
			alert("이름을 확인해주세요.");
		}else if(!checkArr[4]){
			alert("휴대폰 번호를 확인해주세요.");
		}else if(!checkArr[5]){
			alert("이메일 인증이 필요합니다.");
		}else if(!checkArr[6]){
			alert("약관동의가 필요합니다.");
		}
    }
});
