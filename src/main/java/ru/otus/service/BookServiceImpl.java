package ru.otus.service;

import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.dao.BookDao;
import ru.otus.domain.Book;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }


    @Override
    public void storeNewBook(String bookName, String author, String genre) {
        Book newBook = new Book();
        Random generator = new Random();
        newBook.setId(UUID.randomUUID().toString());
        newBook.setTitle(bookName);
        newBook.setAuthorName(author);
        newBook.setGenreTitle(genre);
        bookDao.storeBook(newBook);
        System.out.println("Book is stored");
    }

    @Override
    public void printAllBooks() {
        System.out.println("Here is all book we have:");
        for (Book book :
                bookDao.getAllBooks()) {
            printInfoAboutBook(book);
        }
    }

    @Override
    public void printAllByGenre(String genre) {

    }

    @Override
    public void printByName(String name) {
        Book book = bookDao.getBookByTitle(name);
        if (book != null) {
            printInfoAboutBook(book);
        } else {
            System.out.println("No book found by Title " + name);
        }
    }


    private void printInfoAboutBook(Book book) {
        System.out.println("==============");
        System.out.println("ID: " + book.getId());
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthorName());
        System.out.println("Genre: " + book.getGenreTitle());
    }
}
