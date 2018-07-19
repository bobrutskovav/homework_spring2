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

    @ShellMethod(value = "Put new book in Library", key = "newBook")
    public void storeNewBook(@ShellOption String bookName,@ShellOption String author,@ShellOption String genre) {

    }

    @ShellMethod(value = "Show book in Library by Name", key = "showBooksByName")
    public void showBookByName(@ShellOption String bookName) {

    }

    @ShellMethod(value = "Show book in Library by Genre", key = "showBooksByGenre")
    public void showBooksByGenre(@ShellOption String bookName) {

    }

}
