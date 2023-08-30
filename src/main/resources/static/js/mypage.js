//비밀번호 변경
let checkArr = false;
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
        checkArr = true;
    }else{
        //정규표현식 만족하지 못한 경우
        comment.eq(0).text(" " + "영문 소문자/숫자/특수문자(!,@,#,$,%만 가능) 8글자~12글자 이내로 입력해주세요.");
        comment.eq(0).css("color","red");
        comment.eq(0).css("font-size", "14px");
        $(this).css("border","1px solid red");
        checkArr = false;
    }
    if(comment.eq(0).text() != ""){
        pwDupCheck();
    }
});

// submit금지
$(".updatebtn").on("click",function(event){
		if(!checkArr){
		 event.preventDefault();
			alert("비밀번호를 확인해주세요.");
	}
});
