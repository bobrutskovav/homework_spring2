package ru.otus.service;

public interface BookService {

     void storeNewBook(String bookName, String authorName, String genreTitle);
     void printAllBooks();

     void printAllComments();
     void printAllByGenre(String genre);
     void printByName(String name);

     void printAllAuthors();

     void printAllGenres();

     void addCommentToBook(String comment, String title);

     void deleteBook(String title);
}
