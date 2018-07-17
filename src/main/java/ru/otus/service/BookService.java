package ru.otus.service;

public interface BookService {
     void storeNewBook(String bookName,String author,String genre);
     void printAllBooks();
     void printAllByGenre(String genre);
     void printByName(String name);
}
