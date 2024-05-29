$(document).ready(function(){
  $(".responsive-blog-card-item").slice(0, 4).show();
  $("#card-design-loadMore").on("click", function(e){
    e.preventDefault();
    $(".responsive-blog-card-item:hidden").slice(0, 4).slideDown();
    if($(".responsive-blog-card-item:hidden").length == 0) {
      $("#card-design-loadMore").hide();
    }
  });
  
});