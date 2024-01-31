
function fnOpenUserDetailedPage(targetUserID){  
    var url="";
    var userId = targetUserID.split("-");
    var selectedUserID = $("#user-id-"+userId[1]).val();   
    createCookie('selectedUser',selectedUserID,7);
    url = "/content/myaem65training/us/en/leadership/leadershipdetails.html?wcmmode=disabled"; 
    window.open(url, '_blank');
}
