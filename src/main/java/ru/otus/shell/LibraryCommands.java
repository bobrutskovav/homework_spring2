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
        bookService.printAllBooks().subscribe();

    }


    @ShellMethod(value = "Show all genres in Library", key = "showAllGenres")
    public void printAllGenres() {
        bookService.printAllGenres().subscribe();

    }

    @ShellMethod(value = "Add comment to Book", key = "addComment")
    public void addComment(@ShellOption({"-t", "--title"}) String bookTitle,
                           @ShellOption({"-c", "--comment"}) String comment) {
        bookService.addCommentToBook(comment, bookTitle).subscribe();

    }

    @ShellMethod(value = "Show All Authors", key = "showAllAuthors")
    public void showAllAuthors() {
        bookService.printAllAuthors().subscribe();
    }

    @ShellMethod(value = "Put new book in Library", key = "newBook")
    public void storeNewBook(@ShellOption({"-t", "--title"}) String title,
                             @ShellOption({"-a", "--author"}) String author, @ShellOption({"-g", "--genre"}) String genre) {


        bookService.storeNewBook(title, author, genre).subscribe();
    }

    @ShellMethod(value = "Show book in Library by title", key = "findByTitle")
    public void showBookByTitle(@ShellOption String title) {
        bookService.printByName(title);
    }

    @ShellMethod(value = "Remove book from library with Title", key = "removeBook")
    public void removeByTitle(@ShellOption({"-t", "--title"}) String title) {
        bookService.deleteBook(title);
    }

    @ShellMethod(value = "Prints all Comments", key = "showAllComments")
    public void showAllComments() {
        bookService.printAllComments().subscribe();
    }

}
