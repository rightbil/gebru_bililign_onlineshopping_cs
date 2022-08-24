
// This function is use in the search product functionality
// It is used to pass the value from search text to the controller for matching
function searchTerm(){
    if(inputVal().length !=0)
        window.document.location.href= "/product/search/"+ inputVal();
    else
        window.document.location.href= "/product"
}
function inputVal(){
    return document.getElementById("input").value;
}