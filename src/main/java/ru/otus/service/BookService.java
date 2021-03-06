package ru.otus.service;

import reactor.core.publisher.Mono;

public interface BookService {

    Mono<Void> storeNewBook(String bookTitle, String bookGenre, String authorName);

    Mono<Void> printAllBooks();

    Mono<Void> printAllComments();

    Mono<Void> printAllByGenre(String genre);

    Mono<Void> printByName(String name);

    Mono<Void> printAllAuthors();

    Mono<Void> printAllGenres();

    Mono<Void> addCommentToBook(String comment, String title);

    Mono<Void> deleteBook(String title);
}
