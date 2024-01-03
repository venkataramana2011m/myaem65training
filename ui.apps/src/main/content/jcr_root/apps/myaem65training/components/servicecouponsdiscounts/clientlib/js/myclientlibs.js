$(document).ready(function () {	 
    $(".metros").hide();
    $(".metro-list-item").empty();
    $.ajax({ url: "/bin/servicecouponsdiscounts/statelist",
		type : 'GET',
        dataType : 'json',
        success : function (data){
          $.each(data, function (index, value) {
              console.log(value.stateName + ' ====>> ' + value.stateCode );
              $('.state-list').append("<li class='state-list-item'><a class='state-list-item-name' href='#' id='" + value.stateCode + "' >" + value.stateName + "</a></li>");

          });
        }
    });
});
$(document).on('click', '.state-list-item-name', function () {
    var selectedStateCode=$(this).attr('id');
    $.ajax({ url: "/bin/servicecouponsdiscounts/statelist",
		 type : 'GET',
         dataType : 'json',
         success : function (data){
            $.each(data, function (index, value) {
				if(value.stateCode === selectedStateCode){
                    if(value.hasOwnProperty('metroList')){
                        $('.metros').show();
            			$('.states').hide();
            			$(".metro-list-item").empty();
                        $.each(value.metroList, function(key, val){
                            console.log('=====>>' + val.metroName );
                            $('.metro-list').append("<li class='metro-list-item'><a class='metro-list-item-name' href='#'>"+ val.metroName +"</a></li>");
                            if(val.hasOwnProperty('pageDesc')){/*$('.state-list').append("<li class='metro-list-item'><a href='#'>"+ val.pageDesc +"</a></li>");*/}
                            if(val.hasOwnProperty('pageKeywords')){/*$('.state-list').append("<li class='metro-list-item'><a href='#'>"+ val.pageKeywords +"</a></li>");*/}
                            if(val.hasOwnProperty('metroName')){/*$('.state-list').append("<li class='metro-list-item'><a href='#'>"+ val.metroName +"</a></li>");*/}
                            if(val.hasOwnProperty('pageTitle')){/*$('.state-list').append("<li class='metro-list-item'><a href='#'>"+ val.pageTitle +"</a></li>");*/}
                            if(val.hasOwnProperty('localisedContent')){/*$('.state-list').append("<li class='metro-list-item'><a href='#'>"+ val.localisedContent +"</a></li>");*/}
                        });
                    }
                }
            });
         }
    });
});
$(document).on('click', '.backtostatelist',function () {
    $('.states').show();
    $(".metros").hide();
    $(".metro-list-item").empty();
    $('.state-list').empty();
     $.ajax({ url: "/bin/servicecouponsdiscounts/statelist",
		type : 'GET',
        dataType : 'json',
        success : function (data){
           $.each(data, function (index, value) {
               console.log(value.stateName + ' ====>> ' + value.stateCode );
               $('.state-list').append("<li class='state-list-item'><a class='state-list-item-name' href='#' id='" + value.stateCode + "' >" + value.stateName + "</a></li>");

           });
        }
    });
});
