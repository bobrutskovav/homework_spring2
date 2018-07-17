package ru.otus.dao;

import ru.otus.domain.Book;

import java.util.List;

public interface BookDao {
    void storeBook(Book book);
    List<Book> getAllBooks();
    Book getBookByName(String name);
    List<Book> getAllByGenre(String genre);
}
