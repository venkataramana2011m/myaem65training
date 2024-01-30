var slideIndex = 1;
function plusSlides(n) {
  showSlides(slideIndex += n);
}

function currentSlide(n) {
  showSlides(slideIndex = n);
}

function showSlides(n) {
  let i;
  let slides = document.getElementsByClassName("carouselSlides");
  let dots = document.getElementsByClassName("dot");
  if (n > slides.length) {slideIndex = 1}    
  if (n < 1) {slideIndex = slides.length}
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";  
  }
  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" active", "");
  }
  if(slideIndex > 0) {
      slides[slideIndex-1].style.display = "block";
      dots[slideIndex-1].className += " active";
  }
}

$(document).ready(function() {
	showSlides(slideIndex);
    slideIndex++;
    setInterval(autoSlide, 4000);
});

function autoSlide() {
    showSlides(slideIndex);
    let slides = document.getElementsByClassName("carouselSlides");
    if (slideIndex < slides.length){
		slideIndex++;
    }
    else {
		slideIndex=1;
    }
}