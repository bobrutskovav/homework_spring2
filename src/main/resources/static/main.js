const app = new Vue({
    el: 'vue-app',
    data: {
        displayedBooks: []
    },
    created() {
        fetch('/library').then(response => response.json())
            .then(data => self.data.displayedBooks = data);
    }
});






