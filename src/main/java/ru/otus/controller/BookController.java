package ru.otus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.dao.BookRepository;
import ru.otus.domain.Book;
import ru.otus.domain.BookNotFoundException;
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

    @GetMapping("/library/newbook")
    public String newBookPage(Model model) {
        model.addAttribute("book", new Book());
        return "newbook";
    }

    @PostMapping("/library/newbook")
    public String addBook(@ModelAttribute Book book, Model model) {
        bookService.storeNewBook(book.getTitle(), book.getAuthor().getName(), book.getGenre().getTitle());
        model.addAttribute("allBooks", bookRepository.findAll());
        return "library";
    }

    @GetMapping("/library/edit")
    public String editPage(@RequestParam("id") String id, Model model) throws BookNotFoundException {
        Book bookForEdit = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException());
        model.addAttribute("bookForEdit", bookForEdit);
        return "edit";
    }

    @PostMapping("/library/edit")
    public String updateBook(@ModelAttribute Book book, Model model) {
        bookRepository.save(book);
        model.addAttribute("allBooks", bookRepository.findAll());
        return "library";

    }
}
