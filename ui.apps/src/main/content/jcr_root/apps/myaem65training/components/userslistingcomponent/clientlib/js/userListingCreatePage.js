$(document).ready(function() {
   fnFetchPages();
    $("#btnCreateUser").click(function(){
        var e = document.querySelector(".user");
        const elements = document.querySelectorAll('.user');
        const count = elements.length;
        for(var i=0;i<count;i++){
        	fnCreateUserDetailedPage($("#user-"+i).val());            
        }        
    });
});
function fnOpenUserDetailedPage(targetUserID){  
    var url="";
    var userId = targetUserID.split("-");
    var selectedUserID = $("#user-id-"+userId[1]).val();   
    createCookie('selectedUser',selectedUserID,7);
    url = "/content/myaem65training/us/en/leadership/leadershipdetails.html"; 
    window.open(url, '_blank');
}
function fnCreateUserDetailedPage(targetUserID){
    $.ajax({
        url: "/bin/myaem65training/createuserdetailpage?userID="+ targetUserID,
        type: 'GET',
        dataType: 'json',
        success: function(myresponse) {            
            console.log('Page created successfully ..... !!');            
        },
        failure: function(myresponse) {
            console.log('Error in Page creation ..... !!');
        }
    });    
   
}
function fnFetchPages(){
    $.ajax({
        url: "/bin/myaem65training/fetchUserDetailPages",
        type: 'GET',
        dataType: 'json',
        success: function(myresponse) {            
            console.log('Page created successfully ..... !!');
            $.each(myresponse.pageList,function(key, value){
               console.log("Title : " + value["pageTitle"] + "Name : " + value["pageName"] + "Page Path :: " + value["pagePath"] + " my Property : " + value["myprop"]);  
               console.log($("a .myhref").attr("id"));
            });
        },
        failure: function(myresponse) {
            console.log('Error in Page creation ..... !!');
        }
    });   
}

