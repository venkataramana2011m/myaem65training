$(document).ready(function() {
    /*$.ajax({
        url: "/bin/myaem65training/createuserdetailpage?userID="+ $("#trgtselectedpath").val(),
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            $.each(data, function(index, value) {
                $('.country-list').append("<li class='country-list-item'><a class='country-list-item-name' href='#' id='" + value.countryCode + "' >" + value.countryName + "</a></li>");
            });
        }
    });*/
});
function fnCreatePage(trgtUserId){
    console.log(trgtUserId);
    var targetUserID = trgtUserId;
    fnCreateUserDetailedPage(targetUserID);
}
function fnCreateUserDetailedPage(targetUserID){
    $.ajax({
        url: "/bin/myaem65training/createuserdetailpage?userID="+ targetUserID,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.log('Page created successfully ..... !!');
        },
        failure: function(data) {
            console.log('Error in Page creation ..... !!');
        }
    });
}