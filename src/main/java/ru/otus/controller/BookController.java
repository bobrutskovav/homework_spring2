package ru.otus.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.BookRepository;
import ru.otus.domain.Book;
import ru.otus.domain.BookNotFoundException;
import ru.otus.service.BookService;

import java.util.List;

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

    @GetMapping({"/library"})
    public List<Book> libraryPage() {
        log.debug("===> GET ===> AllBooks");
        return bookRepository.findAll();
    }

    @PostMapping("/library/book")
    @ResponseStatus(HttpStatus.CREATED)
    public void newBook(@RequestBody Book newBook) {
        log.debug("===> POST ===> CreateBook {}", newBook);
        bookService.storeNewBook(newBook.getTitle(), newBook.getAuthor().getName(), newBook.getGenre().getTitle());
    }


    @PatchMapping("/library/book/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBook(@PathVariable("id") String id, @RequestBody Book bookData) {
        log.debug("===> PATCH ===> UpdateBook {}", id);
        Book bookForEdit = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookForEdit.setTitle(bookData.getTitle());
        bookForEdit.setAuthor(bookData.getAuthor());
        bookForEdit.setGenre(bookData.getGenre());
        bookRepository.save(bookForEdit);
    }

    @DeleteMapping("/library/book/{id}")
    public void deleteBook(@PathVariable("id") String id) {
        log.debug("===> DELETE ===> DeleteBook {}, id");
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }


}
