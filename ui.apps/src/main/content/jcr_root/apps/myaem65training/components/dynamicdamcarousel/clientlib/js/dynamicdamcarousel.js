 $(document).ready(function(){    
       $.ajax({
            url: "/bin/myaem65training/damassetlist?trgtGalleryPath="+ $("#trgtselectedpath").val(),
            method: "GET",
            dataType : 'json',
            success: function(myresponse) {
                var trgtli='';
                var spanli='';
                var jsonObject= JSON.stringify(myresponse.DamAssetList); var count = JSON.parse(jsonObject).length;
                var i=0;
                $.each(myresponse.DamAssetList,function(key, value){
                    i=i+1;
                    trgtli = trgtli + "<div class='mySlides fade'><div class='numbertext'>" + i +" / " + count + "</div><img src='"+ value["path"] +"' style='width:100%; border-radius: 25px;'><div class='text'>" + value["title"]+ "</div></div>";
                    spanli = spanli + "<span class='dot' onclick='currentSlide("+ i + ")'></span>";
                });
                trgtli = trgtli + "<a class='prev' onclick='plusSlides(-1)'>❮</a><a class='next' onclick='plusSlides(1)'>❯</a>";
                
                $("#myajaxResponse").html(trgtli);
                $("#carouseldots").html(spanli);
                
            },
            failure: function(myresponse) {
                CQ.Notification.notifyFromResponse(myresponse) ;
            }
       });
     
    });

let slideIndex = 1;
showSlides(slideIndex);

function plusSlides(n) {
  showSlides(slideIndex += n);
}

function currentSlide(n) {
  showSlides(slideIndex = n);
}

function showSlides(n) {
  let i;
  let slides = document.getElementsByClassName("mySlides");
  let dots = document.getElementsByClassName("dot");
  if (n > slides.length) {slideIndex = 1}    
  if (n < 1) {slideIndex = slides.length}
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";  
  }
  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" active", "");
  }
  slides[slideIndex-1].style.display = "block";  
  dots[slideIndex-1].className += " active";
}