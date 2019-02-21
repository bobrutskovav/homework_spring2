fetch('/library').then(response = > response.json()
).
then(books = > new Vue({
    el: '#vue-app',
    data: {
        allBooks: books
    }
})
)
;

