package ru.otus.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.service.BookService;

@ShellComponent
public class LibraryCommands {

    private final BookService bookService;

    @Autowired
    public LibraryCommands(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(value = "Show all books in Library", key = "showAllBooks")
    public void printAllBooks() {
        bookService.printAllBooks();

    }


    @ShellMethod(value = "Show all genres in Library", key = "showAllGenres")
    public void printAllGenres() {
        bookService.printAllGenres();

    }

    @ShellMethod(value = "Add comment to Book", key = "addComment")
    public void addComment(@ShellOption({"-id", "--bookId"}) Long bookId,
            @ShellOption({"-c", "--comment"}) String comment) {
        bookService.addCommentToBook(comment, bookId);

    }

    @ShellMethod(value = "Put new book in Library", key = "newBook")
    public void storeNewBook(@ShellOption({"-t", "--title"}) String title,
            @ShellOption({"-a", "--author"}) String author, @ShellOption({"-g", "--genre"}) String genre) {
        bookService.storeNewBook(title, author, genre);
    }

    @ShellMethod(value = "Show book in Library by title", key = "findByTitle")
    public void showBookByTitle(@ShellOption String title) {
        bookService.printByName(title);
    }

    @ShellMethod(value = "Remove book from library with id", key = "removeBook")
    public void removeById(@ShellOption({"-id", "--bookId"}) Long id) {
        bookService.deleteBook(id);
    }

}
