package ru.otus.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> findByTitle(String title);

    Mono<Book> findByTitleAndAuthorNameAndGenreTitle(String title, String authorName, String genreTitle);

    Flux<Book> findByAuthorIsNotNull();

    Flux<Book> findByGenreIsNotNull();


}
