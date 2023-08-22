//서브메뉴 탭 동작 구현  
$(function(){
    $(".sub-menu").prev().append("<span class='more'></span>");
    $(".more").on("click",function(event){
        $(this).parent().next().slideToggle();
        $(this).toggleClass("active");
        event.stopPropagation();
    });
    $(".more").parent().on("click",function(){
        $(this).children().last().click();
    });
});
/*서브 메뉴를 품고있는 li와 a가
글자hover를 뗀순간 다시 대기 색상으로 변함
->>글씨를 떼도 생성된 영역 안에만 있다면
hover색상대로 유지 하게끔 수정


$(".sub").on("click",function(){
    $(".sub").addClass("sub-a");

});
*/