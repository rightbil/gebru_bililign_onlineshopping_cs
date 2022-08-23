function searchTerm(){
    if(inputVal().length !=0)
        window.document.location.href= "/product/search/"+ inputVal();
    else
        window.document.location.href= "/product"
}
function inputVal(){
    return document.getElementById("input").value;
}