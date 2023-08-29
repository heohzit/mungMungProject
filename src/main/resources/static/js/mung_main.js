var slideIndex = 0;
    showSlides();

    function showSlides() {
        var i;
        var slides = document.getElementsByClassName("mySlides");

        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        slideIndex++;
        if (slideIndex > slides.length) {
            slideIndex = 1
        }
        slides[slideIndex - 1].style.display = "block";
    
        setTimeout(showSlides, 5000); // 이미지 변경 시간 
    }
    
///////////////숙소 슬라이더////////////////////

    const slides1 = document.querySelector('.slides1'); //전체 슬라이드 컨테이너
const slideImg1 = document.querySelectorAll('.slides1 li'); //모든 슬라이드들
let currentIdx1 = 0; //현재 슬라이드 index
const slideCount1 = slideImg1.length; // 슬라이드 개수
const prev1 = document.querySelector('.prev1'); //이전 버튼
const next1 = document.querySelector('.next1'); //다음 버튼
const slideWidth1 = 300; //한개의 슬라이드 넓이
const slideMargin1 = 100; //슬라이드간의 margin 값

//전체 슬라이드 컨테이너 넓이 설정
slides1.style.width = (slideWidth1 + slideMargin1) * slideCount1 + 'px';

function moveSlide1(num) {
  slides1.style.left = -num * 950 + 'px';
  currentIdx1 = num;
}

prev1.addEventListener('click', function () {
  /*첫 번째 슬라이드로 표시 됐을때는 
  이전 버튼 눌러도 아무런 반응 없게 하기 위해 
  currentIdx !==0일때만 moveSlide 함수 불러옴 */

  if (currentIdx1 !== 0) moveSlide1(currentIdx1 - 1);
});

next1.addEventListener('click', function () {
  /* 마지막 슬라이드로 표시 됐을때는 
  다음 버튼 눌러도 아무런 반응 없게 하기 위해
  currentIdx !==slideCount - 1 일때만 
  moveSlide 함수 불러옴 */
  console.log(1111);
  if (currentIdx1 !== slideCount1 - 1) {
    moveSlide1(currentIdx1 + 1);
  }
});

    ///////////////관광지 슬라이더////////////////////
    const slides2 = document.querySelector('.slides2'); //전체 슬라이드 컨테이너
const slideImg2 = document.querySelectorAll('.slides2 li'); //모든 슬라이드들
let currentIdx2 = 0; //현재 슬라이드 index
const slideCount2 = slideImg2.length; // 슬라이드 개수
const prev2 = document.querySelector('.prev2'); //이전 버튼
const next2 = document.querySelector('.next2'); //다음 버튼
const slideWidth2 = 400; //한개의 슬라이드 넓이
const slideMargin2 = 140; //슬라이드간의 margin 값

//전체 슬라이드 컨테이너 넓이 설정
slides2.style.width = (slideWidth2 + slideMargin2) * slideCount2 + 'px';

function moveSlide2(num) {
  slides2.style.left = -num * 370+ 'px';
  currentIdx2 = num;
}

prev2.addEventListener('click', function () {
  /*첫 번째 슬라이드로 표시 됐을때는 
  이전 버튼 눌러도 아무런 반응 없게 하기 위해 
  currentIdx !==0일때만 moveSlide 함수 불러옴 */

  if (currentIdx2 !== 0) moveSlide2(currentIdx2 - 1);
});

next2.addEventListener('click', function () {
  /* 마지막 슬라이드로 표시 됐을때는 
  다음 버튼 눌러도 아무런 반응 없게 하기 위해
  currentIdx !==slideCount - 1 일때만 
  moveSlide 함수 불러옴 */
  if (currentIdx2 !== slideCount2 - 1) {
    moveSlide2(currentIdx2 + 1);
  }
});

// 식음료 

var slideIndex2 = 0;
    showSlides2();

    function showSlides2() {
        var i;
        var slides2 = document.getElementsByClassName("mySlides2");

        for (i = 0; i < slides2.length; i++) {
            slides2[i].style.display = "none";
        }
        slideIndex2++;
        if (slideIndex2 > slides2.length) {
            slideIndex2 = 1
        }
        slides2[slideIndex2 - 1].style.display = "block";
    
        setTimeout(showSlides2, 1000); // 이미지 변경 시간 
    }