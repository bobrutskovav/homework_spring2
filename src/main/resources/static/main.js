const app = new Vue({
    el: '#vue-app',
    data: {
        displayedBooks: [],
        currentBookToSearch: ''
    },
    created() {
        let self = this;
        fetch('/library').then(response => response.json())
            .then((data) => self.displayedBooks = data);
    },
    methods: {
        searchBook: function () {
            let filteredBooks = [];

            fetch('/library')
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
        }
    }
});











