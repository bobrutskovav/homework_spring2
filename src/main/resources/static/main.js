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
            fetch('/library').then(response => response.json())
                .then((data) => app.displayedBooks = data);
            let filteredBooks = [];
            app.displayedBooks.forEach(book => {
                if (book.title.includes(this.currentBookToSearch)) {
                    filteredBooks.push(book);
                }
            });
            app.displayedBooks = filteredBooks;
        }
    }
});











