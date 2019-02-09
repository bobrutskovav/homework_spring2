package ru.otus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.dao.BookRepository;
import ru.otus.service.BookService;

@Controller
public class BookController {


    private BookService bookService;
    private BookRepository bookRepository;


    @Autowired
    public BookController(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/library")
    public String libraryPage(Model model) {
        model.addAttribute("allBooks", bookRepository.findAll());
        return "library";
    }
}
