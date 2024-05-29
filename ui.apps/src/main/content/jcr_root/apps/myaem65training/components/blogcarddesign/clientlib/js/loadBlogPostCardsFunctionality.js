$(document).ready(function() {
    $.ajax({
        url: $(location).attr('protocol') + "//" + $(location).attr('host') + $("#trgtmodeljson").val(),
        method: "GET",
        dataType: 'json',
        success: function(myresponse) {
            var trgtli = '';
            var spanli = '';
            var jsonObject = JSON.stringify(myresponse.blogPostCardDetailsWithMap);
            var count = JSON.parse(jsonObject).length;
            var i = 0;
            var emptyStyleText="";
            var applyStyleText="style='--projcard-color: #F5AF41;'";
            trgtli = trgtli + "<div class='projcard-container'>";            
            $.each(myresponse.blogPostCardDetailsWithMap, function(key, value) {
                i = i + 1;
                trgtli = trgtli +"<div class='projcard " + value["blogPostCardColor"] + "' " + ((value["blogPostCardColor"] != "projcard-customcolor") ? emptyStyleText : applyStyleText) + ">";
                trgtli = trgtli +"<div class='projcard-innerbox'>";
                trgtli = trgtli +"<img class='projcard-img' src='" + value["blogPostCardImage"] + "' />";
                trgtli = trgtli +"<div class='projcard-textbox'>";
                trgtli = trgtli +"<div class='projcard-title'>" + value["blogPostCardTitle"] + "</div>";
                trgtli = trgtli +"<div class='projcard-subtitle'>" + value["blogPostCardSubTitle"] + "</div>";
                trgtli = trgtli +"<div class='projcard-bar'></div>";
                trgtli = trgtli +"<div class='projcard-description'>" + value["blogPostCardDescription"] + "</div>";
                const myArray = value["blogPostCardCategory"].split(";");
                trgtli = trgtli + "<div class='projcard-tagbox'>";
                for(var j=0; j<=myArray.length-1;j++){
                    trgtli = trgtli + "<span class='projcard-tag'>"+myArray[j]+"</span>";
                }
                trgtli = trgtli + "</div>";
                trgtli = trgtli +"</div>";
                trgtli = trgtli +"</div>";
                trgtli = trgtli +"</div>";                
                
            });
            trgtli = trgtli + "</div>";            
            $("#myResponse").html(trgtli);
            

        },
        failure: function(myresponse) {
            CQ.Notification.notifyFromResponse(myresponse);
        }
    });
    

});