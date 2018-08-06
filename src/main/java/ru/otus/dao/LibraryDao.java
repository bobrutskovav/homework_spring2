package ru.otus.dao;

import java.util.List;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

public interface LibraryDao {
    void storeBook(Book book);
    List<Book> getAllBooks();

    Book getBook(Book book);

    Book getBookByTitle(String title);

    Book getBookByID(Long id);

    List<Genre> getAllGenres();

    void removeBookById(Long id);
}
