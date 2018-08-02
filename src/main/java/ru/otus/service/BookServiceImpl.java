package ru.otus.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.dao.LibraryDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

@Service
public class BookServiceImpl implements BookService {

    private final LibraryDao libraryDao;

    @Autowired
    public BookServiceImpl(LibraryDao libraryDao) {
        this.libraryDao = libraryDao;
    }


    @Override
    public void storeNewBook(String bookName, String authorName, String genreTitle) {
        Book newBook = new Book();
        newBook.setTitle(bookName);
        newBook.setAuthor(new Author(authorName));
        newBook.setGenre(new Genre(genreTitle));

        Book foundedBook = libraryDao.getBook(newBook);
        if (foundedBook != null) {
            System.out.println("This book already here!");
            printInfoAboutBook(foundedBook);
            return;
        }
        libraryDao.storeBook(newBook);
        System.out.println("Book is stored");
    }

    @Override
    public void printAllBooks() {
        System.out.println("Here is all book we have:");
        for (Book book :
                libraryDao.getAllBooks()) {
            printInfoAboutBook(book);
        }
    }

    @Override
    public void printAllByGenre(String genre) {

    }

    @Override
    public void printByName(String name) {
        Book book = libraryDao.getBookByTitle(name);
        if (book != null) {
            printInfoAboutBook(book);
        } else {
            System.out.println("No book found by Title " + name);
        }
    }

    @Override
    public void printAllGenres() {
        List<Genre> allGenres = libraryDao.getAllGenres();

        allGenres.forEach(g -> {
            System.out.println("====Genre====");
            System.out.println(g.toString());
        });
    }


    private void printInfoAboutBook(Book book) {
        System.out.println("==============");
        System.out.println("ID: " + book.getId());
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor().getName());
        System.out.println("Genre: " + book.getGenre().getTitle());
        book.getComments().forEach(c -> System.out.println("\n Comment :" + c.getText()));
    }
}
