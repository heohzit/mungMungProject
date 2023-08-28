// 시작시 결제버튼 숨김
$(function(){
    $("#payBtn").hide();
});

// 일정 예약
$("input[name=daterange]").daterangepicker({
    "autoApply": true,
    "maxSpan": {
        "days": $("input[name=productDay]").val()
    },
    "locale": {
        "format": "YYYY/MM/DD",
        "separator": " ~ ",
        "applyLabel": "확인",
        "cancelLabel": "취소",
        "daysOfWeek": ["일", "월", "화", "수", "목", "금", "토"],
        "monthNames": ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
        "firstDay": 0
    },
    "showCustomRangeLabel": false,
    "alwaysShowCalendars": true,
    "parentEl": "pay-box-tbl-td",        
    "minDate": $("input[name=productStart]").val(),
    "maxDate": $("input[name=productEnd]").val(),
    "startDate": $("input[name=productStart]").val(),
    "endDate": $("input[name=productStart]").val() + $("input[name=productDay]").val(),
    "opens": "left",
    "drops": "down",
    "buttonClasses": "btn bc1"            
}, function(start, end) {
    const dateDiff = (end - start) / (1000*60*60*24);
    if(dateDiff < $("input[name=productDay]").val()){
        alert("일정을 다시 선택해주세요.");
        $("#payBtn").hide();
    }else{
        $("#payBtn").show();
    }
});

// 결제api
$("#payBtn").on("click", function(){
    const price = $("#price").val();
    const payMemberNo = $("#payMemberNo").val();
    const payMemberName = $("#payMemberName").val();
    const payMemberEmail = $("#payMemberEmail").val();
    const payMemberPhone = $("#payMemberPhone").val();
    const d = new Date();
    const date = d.getFullYear() + "" + (d.getMonth() + 1) + "" + d.getDate() + "" + d.getHours() + "" + d.getMinutes() + "" + d.getSeconds();
    IMP.init("imp15740857");
    IMP.request_pay({
        pg: "html5_inicis",
        pay_method: "card",
        merchant_uid: "상품번호_" + date,	// 상점에서 관리하는 주문번호
        name: "결제 테스트",
        amount: price,	// 결제금액
        buyer_email: payMemberEmail,
        buyer_name: payMemberName,
        buyer_tel: payMemberPhone
    }, function(rsp){
        if(rsp.success){
            alert("결제 성공");
            // 결제 성공시 DB작업 필요(결제내역 업데이트)
        }else{
            alert("결제 실패");
        }
    });
});