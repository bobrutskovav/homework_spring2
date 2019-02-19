package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void storeNewBook(String bookName, String authorName, String genreTitle) {

        Book foundedBook = bookRepository.findByTitleAndAuthorNameAndGenreTitle(bookName, authorName, genreTitle);
        if (foundedBook != null) {
            log.info("This book already here!");
            printInfoAboutBook(foundedBook);
            return;
        }
        Book newBook = new Book();
        newBook.setTitle(bookName);
        newBook.setAuthor(new Author(authorName));
        newBook.setGenre(new Genre(genreTitle));
        newBook.setComments(new ArrayList<>());
        //newBook -t tttt -a aaaaa -g ggggg
        bookRepository.save(newBook);
        log.info("Book is stored");
    }

    @Override
    public void printAllBooks() {
        log.info("Here is all book we have:");
        bookRepository.findAll().forEach(this::printInfoAboutBook);
    }

    @Override
    public void printAllByGenre(String genre) {

    }

    @Override
    public void printByName(String name) {
        List<Book> books = bookRepository.findByTitle(name);
        if (!books.isEmpty()) {
            books.forEach(this::printInfoAboutBook);
        } else {
            log.info("No book found by Title " + name);
        }
    }

    @Override
    public void printAllAuthors() {
        bookRepository.findByAuthorIsNotNull().forEach(b -> {
            log.info("====Author====");
            log.info(b.getAuthor().toString());
        });
    }

    @Override
    public void printAllGenres() {
        List<Book> allGenres = bookRepository.findByGenreIsNotNull();
        allGenres.forEach(g -> {
            log.info("====Genre====");
            log.info(g.getGenre().getTitle());
        });
    }

    public void printAllComments() {
        commentRepository.findAll().forEach(System.out::println);
    }

    @Override
    public void addCommentToBook(String comment, String title) {
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
    public void deleteBook(String title) {

        List<Book> foundBooks = bookRepository.findByTitle(title);
        foundBooks.forEach(book -> {
            log.info("Found book :\n" + book);
            bookRepository.delete(book);
            log.info("Done");
        });
    }


    private void printInfoAboutBook(Book book) {
        log.info("==============");
        log.info("ID: " + book.getId());
        log.info("Title: " + book.getTitle());
        log.info("Author: " + book.getAuthor().getName());
        log.info("Genre: " + book.getGenre().getTitle());
        book.getComments().forEach(c -> log.info("\nComment :" + c.getText()));
    }


}
