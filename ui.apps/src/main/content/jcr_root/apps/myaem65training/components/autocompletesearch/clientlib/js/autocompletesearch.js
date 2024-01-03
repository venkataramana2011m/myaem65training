$(document).ready(function() {
    let names = [
        "Ayla",
        "Jake",
        "Sean",
        "Henry",
        "Brad",
        "Stephen",
        "Taylor",
        "Timmy",
        "Cathy",
        "John",
        "Amanda",
        "Amara",
        "Sam",
        "Sandy",
        "Danny",
        "Ellen",
        "Camille",
        "Chloe",
        "Emily",
        "Nadia",
        "Mitchell",
        "Harvey",
        "Lucy",
        "Amy",
        "Glen",
        "Peter",
    ];
    
    //reference
    let autocompleteinput = document.getElementById("autocomplete-input");  
    
    //Sort names in ascending order
    let sortedNames = names.sort();

    //Execute function on keyup
    autocompleteinput.addEventListener("keyup", (e) => {
        //loop through above array
        //Initially remove all elements ( so if user erases a letter or adds new letter then clean previous outputs)
        removeElements();
        for (let i of sortedNames) {
            //convert input to lowercase and compare with each string

            if (i.toLowerCase().startsWith(autocompleteinput.value.toLowerCase()) && autocompleteinput.value != "") {
                //create li element
                let listItem = document.createElement("li");
                //One common class name
                listItem.classList.add("list-items");
                listItem.style.cursor = "pointer";
                listItem.setAttribute("onclick", "displayNames('" + i + "')");
                //Display matched part in bold
                let word = "<b>" + i.substr(0, autocompleteinput.value.length) + "</b>";
                word += i.substr(autocompleteinput.value.length);
                //display the value in array
                listItem.innerHTML = word;
                document.querySelector(".list").appendChild(listItem);
            }
        }
    });
    
    
    
    let targetPath = $("#autocomplete-body").attr("data-target-search-path");
    let trgtQuery = $("#autocomplete-body").val();
    console.log(trgtQuery);
    console.log(targetPath);
    $.ajax({
        url: "/bin/servlet/autoCompleteSearch?trgtQuery=" + trgtQuery + "&trgtSearchPath="+ targetPath,
        method: "GET",
        dataType : 'json',
        success: function(myresponse) {
            $.each(myresponse.DamAssetList,function(key, value){   
                const damTitle = value["title"].split(".");
                names.push(damTitle[0].toUpperCase());
            }); 
            names.sort();                
        },
        failure: function(myresponse) {
            CQ.Notification.notifyFromResponse(myresponse) ;
        }
    });
    

    autocompleteinput.addEventListener("keypress", (e) => {
        if (e.keyCode == 13) {
            console.log("Enter pressed");
            //loop through above array
			//Initially remove all elements ( so if user erases a letter or adds new letter then clean previous outputs)
			removeElements();
			for (let i of sortedNames) {
				//convert input to lowercase and compare with each string

				if (i.toLowerCase().startsWith(autocompleteinput.value.toLowerCase()) && autocompleteinput.value != "") {
					//create li element
					let listItem = document.createElement("li");
					//One common class name
					listItem.classList.add("list-items");
					listItem.style.cursor = "pointer";
					listItem.setAttribute("onclick", "displayNames('" + i + "')");
					//Display matched part in bold
					let word = "<b>" + i.substr(0, autocompleteinput.value.length) + "</b>";
					word += i.substr(autocompleteinput.value.length);
					//display the value in array
					listItem.innerHTML = word;
					document.querySelector(".list").appendChild(listItem);
				}
			}
        }
	});
});
    
   



function displayNames(value) {
    let autocompleteinput = document.getElementById("autocomplete-input");
    autocompleteinput.value = value;
    removeElements();
}

function removeElements() {
    //clear all the item
    let items = document.querySelectorAll(".list-items");
    items.forEach((item) => {
        item.remove();
    });
}