//비밀번호 유효성검사
const checkArr =[false,false,false,false,false,false,false,false];
const comment = $(".comment");
$("#memberPw").on("change",function(){
    const pwReg = /^[a-z0-9\d$@$!%*?&]{4,10}$/;
    const inputPw = $(this).val();
    const check = pwReg.test(inputPw);
    if(check){
        //정규표현식 만족한 경우
        //중복체크
        comment.text(" " + "사용 가능한 비밀번호입니다.")
        comment.css("color","blue");
        $(this).css("border","1px solid blue");
        checkArr[1] = true;
    }else{
        //정규표현식 만족하지 못한 경우
        comment.text(" " + "영문 소문자/숫자/특수문자 4글자~10글자 이내로 입력해주세요.");
        comment.css("color","red");
        $(this).css("border","1px solid red");
        checkArr[1] = false;
    }
    if(comment.text() != ""){
        pwDupCheck();
    }
});
