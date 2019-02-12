package ru.otus.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.dao.BookRepository;
import ru.otus.dao.CommentRepository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public void storeNewBook(String bookName, String authorName, String genreTitle) {

        Book foundedBook = bookRepository.findByTitleAndAuthorNameAndGenreTitle(bookName, authorName, genreTitle);
        if (foundedBook != null) {
            System.out.println("This book already here!");
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
        System.out.println("Book is stored");
    }

    @Override
    public void printAllBooks() {
        System.out.println("Here is all book we have:");
        bookRepository.findAll().forEach(this::printInfoAboutBook);
    }

    @Override
    public void printAllByGenre(String genre) {

    }

    @Override
    public void printByName(String name) {
        Book book = bookRepository.findByTitle(name).orElse(null);
        if (book != null) {
            printInfoAboutBook(book);
        } else {
            System.out.println("No book found by Title " + name);
        }
    }

    @Override
    public void printAllAuthors() {
        bookRepository.findByAuthorIsNotNull().forEach(b -> {
            System.out.println("====Author====");
            System.out.println(b.getAuthor());
        });
    }

    @Override
    public void printAllGenres() {
        List<Book> allGenres = bookRepository.findByGenreIsNotNull();
        allGenres.forEach(g -> {
            System.out.println("====Genre====");
            System.out.println(g.getGenre().getTitle());
        });
    }

    public void printAllComments() {
        commentRepository.findAll().forEach(System.out::println);
    }

    @Override
    public void addCommentToBook(String comment, String title) {
        Book foundedBook = bookRepository.findByTitle(title).orElse(null);
        if (foundedBook.getTitle() == null) {
            System.out.println(String.format("Book with Title %s not found", title));
            return;
        }
        String idOFoundedBook = foundedBook.getId();
        List<Comment> currentComments = foundedBook.getComments();
        currentComments.add(new Comment(comment));
        bookRepository.save(foundedBook);
        printInfoAboutBook(bookRepository.findById(idOFoundedBook).orElse(null));
    }

    @Override
    public void deleteBook(String title) {

        Book foundBook = bookRepository.findByTitle(title).orElse(null);
        System.out.println("Found book :\n" + foundBook);
        bookRepository.delete(foundBook);
        System.out.println("Done");
    }


    private void printInfoAboutBook(Book book) {
        System.out.println("==============");
        System.out.println("ID: " + book.getId());
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor().getName());
        System.out.println("Genre: " + book.getGenre().getTitle());
        book.getComments().forEach(c -> System.out.println("\nComment :" + c.getText()));
    }


}
