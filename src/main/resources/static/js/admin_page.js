const tabs = $(".admin-page-select>li");
tabs.on("click", function(){
    tabs.removeClass("active-tab");
    $(this).addClass("active-tab");
    const tabcontent = $(".admin-page-content");
    tabcontent.hide();
    const index = tabs.index(this);
    tabcontent.eq(index).show();
});
$(function(){
    tabs.eq(0).click();
});