const heart = $(".productview-top-heart>span");
const share = $(".productview-top-share>span");
// ----------------찜하기----------------- 
heart.on("click",function(){
    if(heart.text() == "favorite_border"){
        heart.text("favorite");
        heart.css("color","red");
    }else if(heart.text() == "favorite"){
        heart.text("favorite_border");
        heart.css("color","black");
    }
    
});


