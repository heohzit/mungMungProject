// 결제api
$("#payBtn").on("click", function(){
    const price = $("#price").val();
    const d = new Date();
    const date = d.getFullYear() + "" + (d.getMonth() + 1) + "" + d.getDate() + "" + d.getHours() + "" + d.getMinutes() + "" + d.getSeconds();
    IMP.init("imp15740857");
    IMP.request_pay({
        pg: "html5_inicis",
        pay_method: "card",
        merchant_uid: "상품번호_" + date,	// 상점에서 관리하는 주문번호
        name: "결제 테스트",
        amount: price,	// 결제금액
        buyer_email: "user01@naver.com",
        buyer_name: "유저1",
        buyer_tel: "010-1111-1111"
    }, function(rsp){
        if(rsp.success){
            alert("결제 성공");
            // 결제 성공시 DB작업 필요(결제내역 업데이트)
        }else{
            alert("결제 실패");
        }
    });
});

