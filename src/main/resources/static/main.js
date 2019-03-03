const app = new Vue({
    el: '#vue-app',
    data: {
        displayedBooks: [],
        currentBookToSearch: '',
        newBookAreaDisplayed: false,
        newBook: {
            title: '',
            author: '',
            genre: '',
            someInputIsEmpty: true
        }
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

        },
        addNewBook: function () {
            // if (this.newBook.someInputIsEmpty) {
            //     alert("Неоходимо заполнить все поля в информации о новой книге");
            //
            // }
            fetch("library/book", {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "POST",
                body: JSON.stringify({
                    title: this.newBook.title,
                    author: this.newBook.author,
                    genre: this.newBook.genre
                })
            }).then((response) => {
                if (response.status != '201') {
                    throw new Error('PostBook response was not ok for book with id ' + bookId);
                }
            }).finally(() => {
                this.newBook.title = '';
                this.newBook.author = '';
                this.newBook.genre = '';
                this.newBookAreaDisplayed = false;
                getAllBooks()
                    .then(response => response.json())
                    .then((data) => app.displayedBooks = data);
            })

        }
    }
});

//ToDo https://stackoverflow.com/questions/42694457/getting-form-data-on-submit

function getAllBooks() {
    return fetch('/library');
}












