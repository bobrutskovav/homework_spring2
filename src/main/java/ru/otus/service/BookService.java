package ru.otus.service;

public interface BookService {

     void storeNewBook(String bookName, String authorName, String genreTitle);
     void printAllBooks();
     void printAllByGenre(String genre);
     void printByName(String name);

     void printAllGenres();

     void addCommentToBook(String comment, Long bookId);

     void deleteBook(Long id);
}
