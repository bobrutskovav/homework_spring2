package ru.otus.service;

import java.util.Random;
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
        newBook.setId(generator.nextInt() + 1);
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
            System.out.println("==============");
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthorName());
            System.out.println("Genre: " + book.getGenreTitle());
        }
    }

    @Override
    public void printAllByGenre(String genre) {

    }

    @Override
    public void printByName(String name) {

    }
}
