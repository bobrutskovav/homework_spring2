package ru.otus.service;

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


    @Override
    public Mono<Void> storeNewBook(String bookTitle, String bookGenre, String authorName) {
        Book book = new Book();
        book.setTitle(bookTitle);
        book.setAuthor(new Author(authorName));
        book.setGenre(new Genre());
        book.setComments(new ArrayList<>());

        return Mono.just(book).doOnNext(b -> {
            String bTitle = b.getTitle();
            String aName = b.getAuthor().getName();
            String gTitle = b.getGenre().getTitle();

            bookRepository.findByTitleAndAuthorNameAndGenreTitle(
                    bTitle
                    , aName
                    , gTitle).doOnNext(foundedBook -> {
                if (foundedBook != null) {
                    log.info("This book already here!");
                    printInfoAboutBook(foundedBook);
                    return;
                }

                //newBook -t tttt -a aaaaa -g ggggg
                bookRepository.save(book).subscribe();
                log.info("Book is stored");
            }).subscribe();
        }).then();
    }

    @Override
    public Mono<Void> printAllBooks() {
        log.info("Here is all book we have:");
        return Mono.from(bookRepository.findAll().doOnNext(this::printInfoAboutBook).then());
    }

    @Override
    public Mono<Void> printAllByGenre(String genre) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> printByName(String name) {
        return Mono.from(bookRepository.findByTitle(name).doOnNext(this::printInfoAboutBook).then());
    }

    @Override
    public Mono<Void> printAllAuthors() {
        return Mono.from(bookRepository.findByAuthorIsNotNull().doOnNext(b -> {
            log.info("====Author====");
            log.info(b.getAuthor().toString());
        }).then());
    }

    @Override
    public Mono<Void> printAllGenres() {
        return Mono.from(bookRepository.findByGenreIsNotNull().doOnNext(g -> {
            log.info("====Genre====");
            log.info(g.getGenre().getTitle());
        }).then());
    }

    public Mono<Void> printAllComments() {
        return Mono.from(commentRepository.findAll().doOnNext(System.out::println).then());
    }

    @Override
    public Mono<Void> addCommentToBook(String comment, String title) {
        return Mono.from(bookRepository.findByTitle(title).doOnNext(foundedBook -> {

            String idOFoundedBook = foundedBook.getId();
            List<Comment> currentComments = foundedBook.getComments();
            currentComments.add(new Comment(comment));
            bookRepository.save(foundedBook).subscribe();
            bookRepository.findById(idOFoundedBook).doOnNext(this::printInfoAboutBook).subscribe();
        }).then());

    }

    @Override
    public Mono<Void> deleteBook(String title) {

        return Mono.from(bookRepository.findByTitle(title).doOnNext(book -> {
            log.info("Found book :\n" + book);
            bookRepository.delete(book);
            log.info("Done");
        }).then());
    }


    private Mono<Void> printInfoAboutBook(Book book) {

        return Mono.just(book).doOnNext(b -> {
            log.info("==============");
            log.info("ID: " + b.getId());
            log.info("Title: " + b.getTitle());
            log.info("Author: " + b.getAuthor().getName());
            log.info("Genre: " + b.getGenre().getTitle());
            b.getComments().forEach(c -> log.info("\nComment :" + c.getText()));
        }).then();

    }


}
