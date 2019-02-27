const app = new Vue({
    el: '#vue-app',
    data: {
        displayedBooks: [],
        currentBookToSearch: '',
        newBookAreaDisplayed: false;
    },
    created() {
        let self = this;
        getAllBooks()
            .then(response => response.json())
            .then((data) => self.displayedBooks = data);
    },
    methods: {
        searchBook: function () {
            let filteredBooks = [];

            getAllBooks()
                .then(function (response) {
                    response.json().then(
                        function (json) {
                            app.displayedBooks = json;
                            app.displayedBooks.forEach(book => {
                                if (book.title.includes(app.currentBookToSearch)) {
                                    filteredBooks.push(book);
                                }
                            });
                            app.displayedBooks = filteredBooks;
                        })


                })
        },
        deleteBook: function (bookId) {
            console.log(bookId);
            fetch("/library/book/" + bookId,
                {

                    method: "DELETE",
                }).then((response) => {
                if (!response.ok) {
                    throw new Error('Delete response was not ok for book with id ' + bookId);
                }
            }).finally(() => {
                getAllBooks()
                    .then(response => response.json())
                    .then((data) => app.displayedBooks = data)
            });

        }
    }
});

//ToDo https://stackoverflow.com/questions/42694457/getting-form-data-on-submit

function getAllBooks() {
    return fetch('/library');
}












