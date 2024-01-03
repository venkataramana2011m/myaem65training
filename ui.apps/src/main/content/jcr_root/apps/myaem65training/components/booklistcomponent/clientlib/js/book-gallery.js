$(document).ready(function() {
    loadCategories();
    loadAllCategories();
});

function loadCategories() {
    let apiendpoint = $("#nytimes-URL").val();
    let apid = $("#nytimes-app-id").val();
    let apikey = $("#nytimes-api-key").val();
    let secretkey = $("#nytimes-secret-key").val();
    let booklistendpoint = $("#nytimes-book-list-url").val();
    let booklistbycategory = $("#nytimes-book-list-bycategory").val();
    $.ajax({
        url: apiendpoint + booklistbycategory + "?api-key=" + apikey,
        method: "GET",
        success: function(myresponse) {
            var trgtchkbxli = '';
            $.each(myresponse.results, function(key, value) {
                trgtchkbxli = trgtchkbxli + "<input type='checkbox' id='" + value["list_name_encoded"] + "' name='booksByCategory' value='" + value["list_name_encoded"] + "' onclick='loadBooksByCategories()'/> " + value["display_name"] + "<br/>";
            });
            /*$('#booksCategoryList').html(trgtchkbxli);*/
            $("#accordioncontent_1").html(trgtchkbxli);
        },
        failure: function(myresponse) {
            CQ.Notification.notifyFromResponse(myresponse);
        }
    });
}

function loadBooksByCategories() {
    let apiendpoint = $("#nytimes-URL").val();
    let apid = $("#nytimes-app-id").val();
    let apikey = $("#nytimes-api-key").val();
    let secretkey = $("#nytimes-secret-key").val();
    let booklistendpoint = $("#nytimes-book-list-url").val();
    let booklistbycategory = $("#nytimes-book-list-bycategory").val();
    var selectedBooksCategory = [];
    var trgtli = '';
    $('input[name="booksByCategory"]:checked').each(function() {
        selectedBooksCategory.push(this.value);
    });
    if (selectedBooksCategory.length === 0) {
        loadAllCategories();
    } else {
        for (var j = 0; j < selectedBooksCategory.length; j++) {
            $.ajax({
                url: apiendpoint + booklistendpoint + "?api-key=" + apikey + "&list=" + selectedBooksCategory[j],
                method: "GET",
                success: function(myresponse) {
                    if (myresponse != null) {
                        $.each(myresponse.results, function(key, value) {
                            trgtli = trgtli + "<div class='card'>";
                            trgtli = trgtli + "<div class='image'>";
                            trgtli = trgtli + "<img src='/content/dam/myaem65training/mycarouselfolder/img_woods_wide.jpg' />";
                            trgtli = trgtli + '</div>';
                            var book_details = value["book_details"];
                            for (var i = 0; i < book_details.length; i++) {
                                trgtli = trgtli + "<div class='title'><h4>" + book_details[i].title + "</h4></div>";
                                trgtli = trgtli + "<div class='des'><p>" + book_details[i].description + "</p>";
                            }
                            trgtli = trgtli + "<a href='" + value["amazon_product_url"] + "' target='_blank'><button>Read More...</button></a></div>";
                            trgtli = trgtli + '</div>';

                        });
                    }
                    $('#booklist').html(trgtli);
                },
                failure: function(myresponse) {
                    CQ.Notification.notifyFromResponse(myresponse);
                }
            });
        }
    }
}

function loadAllCategories() {
    let apiendpoint = $("#nytimes-URL").val();
    let apid = $("#nytimes-app-id").val();
    let apikey = $("#nytimes-api-key").val();
    let secretkey = $("#nytimes-secret-key").val();
    let booklistendpoint = $("#nytimes-book-list-url").val();
    let booklistbycategory = $("#nytimes-book-list-bycategory").val();
    var trgtli = '';
    $.ajax({
        url: apiendpoint + booklistendpoint + "?api-key=" + apikey + "&list=combined-print-and-e-book-fiction",
        method: "GET",
        success: function(myresponse) {
            if (myresponse != null) {
                $.each(myresponse.results, function(key, value) {
                    trgtli = trgtli + "<div class='card'>";
                    trgtli = trgtli + "<div class='image'>";
                    trgtli = trgtli + "<img src='/content/dam/myaem65training/mycarouselfolder/img_woods_wide.jpg' />";
                    trgtli = trgtli + '</div>';
                    var book_details = value["book_details"];
                    for (var i = 0; i < book_details.length; i++) {
                        trgtli = trgtli + "<div class='title'><h4>" + book_details[i].title + "</h4></div>";
                        trgtli = trgtli + "<div class='des'><p>" + book_details[i].description + "</p>";
                    }
                    trgtli = trgtli + "<a href='" + value["amazon_product_url"] + "' target='_blank'><button>Read More...</button></a></div>";
                    trgtli = trgtli + '</div>';

                });
            }
            $('#booklist').html(trgtli);
        },
        failure: function(myresponse) {
            CQ.Notification.notifyFromResponse(myresponse);
        }
    });

}