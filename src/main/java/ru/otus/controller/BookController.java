package ru.otus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.service.BookService;

@Controller
public class BookController {


    BookService bookService;


    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/library")
    public String libraryPage(Model model) {
        bookService.printAllBooks();
        return "library";
    }
}
