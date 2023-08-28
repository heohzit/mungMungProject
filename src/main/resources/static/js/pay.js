// 시작시 결제버튼 숨김
$(function(){
    $("#payBtn").hide();
});

// 일정 예약 api
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
    const payPrice = $("#payPrice").val();
    const payProductNo = $("#payProductNo").val();
    const payProductName = $(".pay-name-box>h3").text();
    const payMemberNo = $("#payMemberNo").val();
    const payMemberName = $("#payMemberName").val();
    const payMemberEmail = $("#payMemberEmail").val();
    const payMemberPhone = $("#payMemberPhone").val();
    const d = new Date();
    const payDate = d.getFullYear() + "" + (d.getMonth() + 1) + "" + d.getDate() + "" + d.getHours() + "" + d.getMinutes() + "" + d.getSeconds();
    console.log(payDate);
    const payBuyNo = payProductNo + "_" + payDate;
    IMP.init("imp15740857");
    IMP.request_pay({
        pg: "html5_inicis",
        pay_method: "card",
        merchant_uid: payProductNo + "_" + payDate,	// 상점에서 관리하는 주문번호
        name: payProductName + "_" + payMemberName + "_" + payDate,
        amount: payPrice,	// 결제금액
        buyer_email: payMemberEmail,
        buyer_name: payMemberName,
        buyer_tel: payMemberPhone
    }, function(rsp){
        if(rsp.success){
            $.ajax({
                url: "/pay/insertPay",
                type: "get",
                data: {
                    payProductNo: payProductNo,
                    payMemberNo: payMemberNo,
                    payPrice: payPrice,
                    payDate: payDate,
                    payBuyNo: payBuyNo,
                    productNo: payProductNo
                },
                success: function(data){
                    if(data > 1){
                        location.href="/pay/paySuccess";
                    }else{
                        location.href="/pay/payError";
                    }
                }
            });
        }else{
            location.href="/pay/payError";
        }
    });
});