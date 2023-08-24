const tabs = $(".tabs>li");
tabs.on("click",function(){
    tabs.removeClass("active-tab");
    $(this).addClass("active-tab");
    const tabcontent = $(".tabcontent");
    tabcontent.hide();
    const index = tabs.index(this);
    tabcontent.eq(index).show();
});
$(function(){
    tabs.eq(0).click();
})