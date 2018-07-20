package ru.otus.dao;

import java.util.List;
import ru.otus.domain.Book;

public interface BookDao {
    void storeBook(Book book);
    List<Book> getAllBooks();

    Book getBookByTitle(String title);

    Book getBookByID(String id);
}
