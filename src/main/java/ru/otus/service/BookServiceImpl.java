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
    public Mono<Void> storeNewBook(Mono<Book> bookToStoreMono) {

        bookToStoreMono.doOnNext(book -> {
            String bookTitle = book.getTitle();
            String authorName = book.getAuthor().getName();
            String genreTitle = book.getGenre().getTitle();

            bookRepository.findByTitleAndAuthorNameAndGenreTitle(
                    book.getTitle()
                    , book.getAuthor().getName()
                    , book.getGenre().getTitle()).doOnNext(foundedBook -> {
                if (foundedBook != null) {
                    log.info("This book already here!");
                    printInfoAboutBook(foundedBook);
                    return;
                }
                Book newBook = new Book();
                newBook.setTitle(bookTitle);
                newBook.setAuthor(new Author(authorName));
                newBook.setGenre(new Genre(genreTitle));
                newBook.setComments(new ArrayList<>());
                //newBook -t tttt -a aaaaa -g ggggg
                bookRepository.save(newBook);
                log.info("Book is stored");
            });
        }
    }

    @Override
    public Mono<Void> printAllBooks() {
        log.info("Here is all book we have:");
        bookRepository.findAll().doOnNext(this::printInfoAboutBook);
    }

    @Override
    public Mono<Void> printAllByGenre(String genre) {

    }

    @Override
    public Mono<Void> printByName(String name) {
        bookRepository.findByTitle(name).doOnNext(book -> printInfoAboutBook(book));
    }

    @Override
    public Mono<Void> printAllAuthors() {
        bookRepository.findByAuthorIsNotNull().forEach(b -> {
            log.info("====Author====");
            log.info(b.getAuthor().toString());
        });
    }

    @Override
    public Mono<Void> printAllGenres() {
        List<Book> allGenres = bookRepository.findByGenreIsNotNull();
        allGenres.forEach(g -> {
            log.info("====Genre====");
            log.info(g.getGenre().getTitle());
        });
    }

    public Mono<Void> printAllComments() {
        commentRepository.findAll().forEach(System.out::println);
    }

    @Override
    public Mono<Void> addCommentToBook(String comment, String title) {
        List<Book> foundedBooks = bookRepository.findByTitle(title);
        if (foundedBooks.isEmpty()) {
            log.info("Book with Title {} not found", title);
            return;
        }

        foundedBooks.forEach(foundedBook -> {
            String idOFoundedBook = foundedBook.getId();
            List<Comment> currentComments = foundedBook.getComments();
            currentComments.add(new Comment(comment));
            bookRepository.save(foundedBook);
            printInfoAboutBook(bookRepository.findById(idOFoundedBook).get());
        });

    }

    @Override
    public Mono<Void> deleteBook(String title) {

        List<Book> foundBooks = bookRepository.findByTitle(title);
        foundBooks.forEach(book -> {
            log.info("Found book :\n" + book);
            bookRepository.delete(book);
            log.info("Done");
        });
    }


    private Mono<Void> printInfoAboutBook(Book book) {
        log.info("==============");
        log.info("ID: " + book.getId());
        log.info("Title: " + book.getTitle());
        log.info("Author: " + book.getAuthor().getName());
        log.info("Genre: " + book.getGenre().getTitle());
        book.getComments().forEach(c -> log.info("\nComment :" + c.getText()));
    }


}
