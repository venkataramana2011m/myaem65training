$(document).ready(function() {
    $.ajax({
        url: $(location).attr('protocol') + "//" + $(location).attr('host') + $("#trgtmodeljson").val(),
        method: "GET",
        dataType: 'json',
        success: function(myresponse) {
            var trgtli = '';
            var spanli = '';
            var jsonObject = JSON.stringify(myresponse.cardDetailsWithMap);
            var count = JSON.parse(jsonObject).length;
            var i = 0;
            trgtli = trgtli + "<div class='mycarousel-body'>";
            trgtli = trgtli + "<div class='slide-container swiper'>";
            trgtli = trgtli + "<div class='slide-content'>";
            trgtli = trgtli + "<div class='card-wrapper swiper-wrapper'>";
            $.each(myresponse.cardDetailsWithMap, function(key, value) {
                i = i + 1;
                trgtli = trgtli + "<div class='card swiper-slide fade'>";
                trgtli = trgtli + "<div class='image-content'> <span class='overlay'></span>";
                trgtli = trgtli + "<div class='card-image'> <img src='" + value["cardImage"] + "' alt='' class='card-img' /> </div>";
                trgtli = trgtli + "</div>";
                trgtli = trgtli + "<div class='card-content'>";
                trgtli = trgtli + "<h2 class='name'>" + value["cardTitle"] + "</h2>";
                trgtli = trgtli + "<button class='button'>View More</button>";
                trgtli = trgtli + "</div>";
                trgtli = trgtli + "</div>";
            });
            trgtli = trgtli + "</div>";
            trgtli = trgtli + "	</div>";
            trgtli = trgtli + "	<div class='swiper-button-next swiper-navBtn' id='nextBtn' onclick='plusSlides(1)'></div> ";
            trgtli = trgtli + "	<div class='swiper-button-prev swiper-navBtn' id='prevBtn' onclick='plusSlides(-1)'></div> ";
            trgtli = trgtli + "	<div class='swiper-pagination'></div>";
            trgtli = trgtli + "</div>";
            trgtli = trgtli + "</div>";
            $("#myResponse").html(trgtli);
            

        },
        failure: function(myresponse) {
            CQ.Notification.notifyFromResponse(myresponse);
        }
    });
    

});
/*const carouselItems = document.querySelectorAll('.card');
let currentIndex = 0;

function showSlide(index) {
  // Hide all carousel items
  carouselItems.forEach(item => {
    item.style.display = 'none';
  });
}
function nextSlide() {
  currentIndex = (currentIndex + 1) % carouselItems.length;
  showSlide(currentIndex);
}

function previousSlide() {
  currentIndex = (currentIndex - 1 + carouselItems.length) % carouselItems.length;
  showSlide(currentIndex);
}*/


let myslideIndex = 1;

function plusSlides(n) {
  showSlides(myslideIndex += n);
}

function currentSlide(n) {
  showSlides(myslideIndex = n);
}

function showSlides(n) {
  let i;
  var slides = document.getElementsByClassName("card");
  if (n > slides.length) {myslideIndex = 1}    
  if (n < 1) {myslideIndex = slides.length}
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";  
  }
  
  slides[myslideIndex-1].style.display = "block"; 
}