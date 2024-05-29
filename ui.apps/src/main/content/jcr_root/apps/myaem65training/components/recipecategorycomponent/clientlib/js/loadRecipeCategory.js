$(document).ready(function() {	
    fnLoadRecipeCategories();
});

function fnLoadRecipeCategories(){
    $("#myRecipeCategoryResponse").css("display","block");
    $.ajax({
        url: "/bin/myaem65training/recipiecategorylisting",
		method : 'GET',
        dataType : 'json',
        success : function (myresponse){
            var trgtli = ''; var i = 0;
            trgtli = trgtli + "<div class='recipe-row'>";
            $.each(myresponse.categories, function(key, value) {
                trgtli = trgtli + "<div class='recipe-column' id='" + i + "' >";
                trgtli = trgtli + "<img src='" + value["strCategoryThumb"] + "' alt='' />";
                trgtli = trgtli + "<div class='responsive-blog-content'>";
                trgtli = trgtli + "<h2 class='responsive-blog-title' >" + value["strCategory"] + "</h2>";
                trgtli = trgtli + "<p class='responsive-blog-p'>Baked Cod with Vegetables. 30 minute meal!</p>";
                trgtli = trgtli + "</div>";
                trgtli = trgtli + "<a href='#' class='responsive-blog-button' id='" + value["strCategory"] + "' onclick='fnFilterRecipesByCategories(this.id)'>Read More</a>";
                trgtli = trgtli + "</div>";
                i = i + 1;
            });
            trgtli = trgtli + "</div>";
            $("#myRecipeCategoryResponse").html(trgtli);
        },
        failure: function(myresponse) { CQ.Notification.notifyFromResponse(myresponse); }
    });
    $("#myRecipeListByCategoryResponse").html("");
    $("#myRecipeListByCategoryResponse").css("display", "none");
    $("#myRecipeDetailResponse").html("");
    $("#myRecipeDetailResponse").css("display", "none");

}

function fnFilterRecipesByCategories(selectedCategory){
    console.log("Selected Recipe Category from fnFilterRecipesByCategories :: "+ selectedCategory);
    var x = document.getElementById("myRecipeListByCategoryResponse");
    if (x.style.display === "none") {
        $("#myRecipeListByCategoryResponse").css("display", "block");
        $("#myRecipeCategoryResponse").css("display","none");
        $("#myRecipeDetailResponse").css("display","none");
        $.ajax({ 
            url: "/bin/myaem65training/recipefilterbycategory.json?selectedCategory="+ selectedCategory ,
            method : 'GET',
            dataType : 'json',
            success : function (myresponse){
                var recipetrgtli = ''; var i = 0;
                recipetrgtli = recipetrgtli + "<div class='back-button'><a href='#' class='label' onclick='fnLoadRecipeCategories()'>back</a></div>";
                recipetrgtli = recipetrgtli + "<h2 class='recipe-selected-category'>Category :" + selectedCategory + "</h2>";
                recipetrgtli = recipetrgtli + "<div class='recipe-row'>";
                $.each(myresponse.meals, function(key, value) {                
                    recipetrgtli = recipetrgtli + "<div class='recipe-column' id='" +  value["idMeal"] + "' >";
                    recipetrgtli = recipetrgtli + "<img src='" + value["strMealThumb"] + "' alt='' />";
                    recipetrgtli = recipetrgtli + "<div class='responsive-blog-content'>";                
                    recipetrgtli = recipetrgtli + "<h2 class='responsive-blog-title' >" + value["strMeal"] + "</h2>";
                    recipetrgtli = recipetrgtli + "<p class='responsive-blog-p'>Baked Cod with Vegetables. 30 minute meal!</p>";
                    recipetrgtli = recipetrgtli + "</div>";
                    recipetrgtli = recipetrgtli + "<a href='#' class='responsive-blog-button' id='" + value["idMeal"] + "' onclick='fnLoadRecipeDetails(this.id, " + selectedCategory + ")'>Read More</a>";
                    recipetrgtli = recipetrgtli + "</div>";
                    i = i + 1;
                });
                recipetrgtli = recipetrgtli + "</div>";            
                $("#myRecipeListByCategoryResponse").html(recipetrgtli);
            },
            failure: function(myresponse) { CQ.Notification.notifyFromResponse(myresponse); }
        });
    }   
}

function fnLoadRecipeDetails(selectedRecipe, selectedCategory){
    console.log("Selected Recipe from fnLoadRecipeDetails :: "+ selectedRecipe);
    var x = document.getElementById("myRecipeDetailResponse");
    if (x.style.display === "none") {
        $("#myRecipeListByCategoryResponse").css("display", "none");
        $("#myRecipeCategoryResponse").css("display","none");
        $("#myRecipeDetailResponse").css("display","block");
        $.ajax({ 
            url: "/bin/myaem65training/recipedetail.json?selectedRecipe="+ selectedRecipe ,
            method : 'GET',
            dataType : 'json',
            success : function (myresponse){
                console.log("Selected Recipe Category from fnLoadRecipeDetails :: "+ myresponse.meals[0]["strCategory"]);
                var recipedetailtrgtli = ''; var i = 0;
                recipedetailtrgtli = recipedetailtrgtli + "<div class='back-button'><a href='#' id='" + myresponse.meals[0]["strCategory"] + "' class='label' onclick='fnFilterRecipesByCategories(this.id)'>back</a></div>";
                recipedetailtrgtli = recipedetailtrgtli + "<h2 class='recipe-selected-category'>Category :" + myresponse.meals[0]["strMeal"] + "</h2>";                   
                recipedetailtrgtli = recipedetailtrgtli + "<div class='recipedetailcontainer'>";
                recipedetailtrgtli = recipedetailtrgtli + "<div class='recipedetail-left-column'><img class='active' src='" + myresponse.meals[0]["strMealThumb"] + "' alt='' id='featured-image' />";
                /*recipedetailtrgtli = recipedetailtrgtli + "<div class='recipedetail-description'>";*/
                recipedetailtrgtli = recipedetailtrgtli + "<span>" + myresponse.meals[0]["strCategory"] + "</span>";
                recipedetailtrgtli = recipedetailtrgtli + "<h1>" + myresponse.meals[0]["strMeal"] + "</h1>";
                recipedetailtrgtli = recipedetailtrgtli + "<p>" + myresponse.meals[0]["strInstructions"] + "</p>";
                /*recipedetailtrgtli = recipedetailtrgtli + "</div>";*/
                recipedetailtrgtli = recipedetailtrgtli + "</div>";
                recipedetailtrgtli = recipedetailtrgtli + "<div class='recipedetail-right-column'>";
                for(var j=1;j<=20; j++){
                    var ingredientName = myresponse.meals[0]["strIngredient"+j];
                    if(ingredientName != "" && ingredientName != null){
                        recipedetailtrgtli = recipedetailtrgtli + "<div class='recipe-ingredient-gallery-responsive'>";
                        recipedetailtrgtli = recipedetailtrgtli + "<div class='recipe-ingredient-gallery'>";
                        recipedetailtrgtli = recipedetailtrgtli + "<a target='_blank' href='#'><img src='https://www.themealdb.com/images/ingredients/" + ingredientName.replace(" ", "%20") + ".png' alt=''/></a>";
                        recipedetailtrgtli = recipedetailtrgtli + "<div class='recipe-ingredient-gallery-desc'>" + myresponse.meals[0]["strMeasure"+j] + " " + ingredientName + "</div>";          
                        recipedetailtrgtli = recipedetailtrgtli + "</div>";
                        recipedetailtrgtli = recipedetailtrgtli + "</div>";
                    }
                }                
                recipedetailtrgtli = recipedetailtrgtli + "</div>";                
                recipedetailtrgtli = recipedetailtrgtli + "</div>";
                
                $("#myRecipeDetailResponse").html(recipedetailtrgtli);
                console.log($("#myRecipeDetailResponse").html());
            },
            failure: function(myresponse) { CQ.Notification.notifyFromResponse(myresponse); }
        });
    }
}