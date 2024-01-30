$(document).ready(function() {
    /*var getUrlParameter = function getUrlParameter(sParam) {
        var sPageURL = window.location.search.substring(1),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
            }
        }
        return false;
    };
    var userId = getUrlParameter('userID');
    console.log(userId);*/
    
    /* To read the cookie value and send the cookie value as a request parameter to the servlet */

    if (readCookie('selectedUser') != null) {
        var userId = readCookie('selectedUser');       
        loadUserDetails(userId);
    } else{
        var url = "/content/myaem65training/us/en/leadership.html?wcmmode=disabled"; 
         $(location).attr('href',url);
    }

});

function loadUserDetails(targetUserId) {
    $.ajax({
        url: "/bin/fetchUserDetails?userID=" + targetUserId,
        type: 'GET',
        dataType: 'json',
        success: function(myresponse) {
            $.each(myresponse.userDetails, function(key, value) {
                if (value["user_givenname"].toLowerCase() != value["user_group_name"].toLowerCase()) {
                    $("#userName").html(toTitleCase(value["user_givenname"]) + " " + toTitleCase(value["user_group_name"]));
                } else {
                    $("#userName").html(toTitleCase(value["user_givenname"]) );
                }
                $("#userProfilePic").attr("srcset", value["user_profile_picture"]);
                $("#minImg992").attr("srcset", value["user_profile_picture"]);
                $("#minImg800").attr("srcset", value["user_profile_picture"]);
                $("#usergivenname").html(value["user_givenname"].toUpperCase());
                $("#usergroupname").html(value["user_group_name"].toUpperCase());
                $("#user-id").html(value["userID"].toUpperCase());
                $("#user-path").html(value["user_path"].toUpperCase());
                $("#user-emailid").html(value["user_email"].toUpperCase());

            });
        },
        failure: function(myresponse) {
            console.log('Error in Page creation ..... !!');
        }
    });
}
function toTitleCase(str) {
    return str.replace(/(?:^|\s)\w/g, function(match) {
        return match.toUpperCase();
    });
}
