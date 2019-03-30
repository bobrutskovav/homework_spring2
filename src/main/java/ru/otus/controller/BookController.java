package ru.otus.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dao.BookRepository;
import ru.otus.domain.Book;
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

    @GetMapping({"/library"})
    public Flux<Book> libraryPage() {
        log.debug("===> GET ===> AllBooks");
        return bookRepository.findAll();
    }

    @PostMapping("/library/book")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> newBook(@RequestBody Book newBook) {
        log.debug("===> POST ===> CreateBook {}", newBook);
        return bookService.storeNewBook(newBook.getTitle(), newBook.getAuthor().getName(), newBook.getGenre().getTitle());
    }


    @PatchMapping("/library/book/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> updateBook(@PathVariable("id") String id, @RequestBody Book bookData) {
        log.debug("===> PATCH ===> UpdateBook {}", id);
        return bookRepository.findById(id).flatMap(book -> {
                    book.setTitle(bookData.getTitle());
                    book.setAuthor(bookData.getAuthor());
                    book.setGenre(bookData.getGenre());
                    bookRepository.save(book);
                    return Mono.empty();
                }
        );

    }

    @DeleteMapping("/library/book/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        log.debug("===> DELETE ===> DeleteBook {}, id");
        return bookRepository.findById(id).flatMap(book -> bookRepository.delete(book));
    }
}