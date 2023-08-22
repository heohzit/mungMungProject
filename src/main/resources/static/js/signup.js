const allAgree = document.querySelector("#allAgree");
allAgree.addEventListener("click",function(){
    const status = this.checked;
    const agree = document.querySelectorAll("[name=agree]");
    agree.forEach(function(item){
        item.checked = status;
    })
});

const select = document.querySelector(".email-choice");
select.addEventListener("change",function(){
    const input = document.querySelector("[name=memberEmail2]");
    input.value = select.value;
});

