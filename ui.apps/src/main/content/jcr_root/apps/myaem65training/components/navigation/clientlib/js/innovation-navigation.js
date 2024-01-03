$(document).ready(function() {
    var navLinks = document.getElementById("navLinks");
    function showMenu(){
        navLinks.style.right="0";
    }
    function hideMenu(){
        navLinks.style.right="-200px";
    }
    $.ajax({
        url: "/bin/myaem65training/navigation",
        method: "GET",
        success: function(myresponse) {
            var trgtli='<i class="fa fa-times" onclick="hideMenu()"></i>';
			trgtli = trgtli + '<ul>';
            $.each(myresponse.PageList,function(key, value){
                var trgtPageTitle="English";
                var pageTitle;
                if(value["pageTitle"].toLowerCase() !=="new cars" && value["pageTitle"].toLowerCase() !=="order tracking" && value["pageTitle"].toLowerCase() !=="add to cart" ){
                    if(trgtPageTitle.toLowerCase()===value["pageTitle"].toLowerCase() || value["pageTitle"].toLowerCase()==="en"){
                        pageTitle = "Home";
                        trgtli = trgtli + "<li><a href='"+value["pagePath"]+".html'>" + pageTitle.toUpperCase() + "</a></li>";
                    } else{
                        pageTitle = value["pageTitle"];
                        trgtli = trgtli + "<li><a href='"+value["pagePath"]+".html'>" + pageTitle.toUpperCase() + "</a></li>";
                    }
                }


            });            
			trgtli = trgtli + '</ul>';
            $('#navLinks').html(trgtli);
        },
        failure: function(myresponse) {
            CQ.Notification.notifyFromResponse(myresponse) ;
        }
    });

});
