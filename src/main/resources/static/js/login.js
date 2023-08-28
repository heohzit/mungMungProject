$("#searchIdBtn").on("click",function(){
    const memberName = $("#id_memberName").val();
    const memberEmail = $("#id_memberEmail").val();
    $.ajax({
        url : "/member/searchId",
        data : {memberName : memberName, memberEmail : memberEmail},
        type : "get",
        success : function(data){
            console.log(data);
            if(data){
         		// 아이디는[]입니다.
         		alert("아이디는 " + data + " 입니다.");
         	}else{
         		// 해당 정보와 일치하는 아이디가 없습니다.
         		alert("해당 정보와 일치하는 아이디가 없습니다.");
         	}
        }
    });
});

$("#searchPwBtn").on("click",function(){
    const memberId = $("#pw_memberId").val();
    const memberEmail = $("#pw_memberEmail").val();
    $.ajax({
        url : "/member/searchPw",
        data : {memberId : memberId, memberEmail : memberEmail},
        type : "get",
        success : function(data){
            console.log(data);
            if(data == "1"){
         		// 해당 메일로 비밀번호 전송
         		alert("회원님의 이메일로 비밀번호가 전송되었습니다.");
         	}else{
         		// 해당 정보와 일치하는 비밀번호가 없습니다.
         		alert("해당 정보와 일치하는 비밀번호가 없습니다.");
         	}
        }
    });
});
