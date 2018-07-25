package ru.otus.service;

import ru.otus.domain.Author;
import ru.otus.domain.Genre;

public interface BookService {
     void storeNewBook(String bookName, Author author, Genre genre);
     void printAllBooks();
     void printAllByGenre(String genre);
     void printByName(String name);
}
