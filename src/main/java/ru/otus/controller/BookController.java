package ru.otus.controller;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dao.BookRepository;
import ru.otus.domain.Book;
import ru.otus.domain.BookNotFoundException;
import ru.otus.service.BookService;

@RestController
public class BookController {


    private Logger log = LoggerFactory.getLogger(BookController.class);

    private BookService bookService;
    private BookRepository bookRepository;


    @Autowired
    public BookController(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @GetMapping({"/library", "/"})
    public List<Book> libraryPage() {
        return bookRepository.findAll();
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
    public String editPage(@RequestParam("id") String id, Model model) {
        Book bookForEdit = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        model.addAttribute("bookForEdit", bookForEdit);
        return "edit";
    }

    @PostMapping("/library/edit")
    public String updateBook(@ModelAttribute Book book, Model model) {
        bookRepository.save(book);
        model.addAttribute("allBooks", bookRepository.findAll());
        return "library";

    }

    @PostMapping("/library/search")
    public String searchBook(@RequestParam(name = "titleForSearch") String titleForSearch, Model model) {
        List<Book> foundBooks = bookRepository.findByTitle(titleForSearch);
        model.addAttribute("allBooks", foundBooks);
        return "library";
    }
}
