function getAllBooks(){
    var data;
    fetch('/library').then(response => data = response.json());
    return data;
}


var app = new Vue({
el: '#vue-app',
data: {
    allBooks : getAllBooks()
}
})
console.log(allBooks)
