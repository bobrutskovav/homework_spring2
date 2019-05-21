package ru.otus.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.dao.BookRepository;
import ru.otus.dao.CommentRepository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    private Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @CircuitBreaker(name = "booksBackend")
    @Override
    public Mono<Void> storeNewBook(String bookTitle, String bookGenre, String authorName) {
        Book book = new Book();
        book.setTitle(bookTitle);
        book.setAuthor(new Author(authorName));
        book.setGenre(new Genre(bookGenre));
        book.setComments(new ArrayList<>());

        return bookRepository.findByTitleAndAuthorNameAndGenreTitle(
                bookTitle
                , authorName
                , bookGenre).switchIfEmpty(Mono.just(book)).flatMap(bookRepository::save).then();

    }

    @Override
    public Mono<Void> printAllBooks() {
        log.info("Here is all book we have:");
        return bookRepository.findAll().map(this::printInfoAboutBook).then();
    }

    @Override
    public Mono<Void> printAllByGenre(String genre) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> printByName(String name) {
        return bookRepository.findByTitle(name).map(this::printInfoAboutBook).then();
    }

    @Override
    public Mono<Void> printAllAuthors() {
        return bookRepository.findByAuthorIsNotNull().map(b -> {
            log.info("====Author====");
            log.info(b.getAuthor().toString());
            return b;
        }).then();
    }

    @Override
    public Mono<Void> printAllGenres() {
        return bookRepository.findByGenreIsNotNull().map(g -> {
            log.info("====Genre====");
            log.info(g.getGenre().getTitle());
            return g;
        }).then();
    }

    public Mono<Void> printAllComments() {
        return commentRepository.findAll().map(comment -> {
            System.out.println(comment);
            return comment;
        }).then();
    }

    @Override
    public Mono<Void> addCommentToBook(String comment, String title) {
        return bookRepository.findByTitle(title).map(foundedBook -> {

            String idOFoundedBook = foundedBook.getId();
            List<Comment> currentComments = foundedBook.getComments();
            currentComments.add(new Comment(comment));
            bookRepository.save(foundedBook);
            bookRepository.findById(idOFoundedBook).map(this::printInfoAboutBook);
            return foundedBook;
        }).then();

    }

    @Override
    public Mono<Void> deleteBook(String title) {

        return bookRepository.findByTitle(title).map(book -> {
            log.info("Found book :\n" + book);
            bookRepository.delete(book);
            log.info("Done");
            return book;
        }).then();
    }


    private Book printInfoAboutBook(Book book) {

            log.info("==============");
        log.info("ID: " + book.getId());
        log.info("Title: " + book.getTitle());
        log.info("Author: " + book.getAuthor().getName());
        log.info("Genre: " + book.getGenre().getTitle());
        book.getComments().forEach(c -> log.info("\nComment :" + c.getText()));
        return book;

    }


}
