package ru.otus.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.domain.Book;

import java.util.List;


public interface BookRepository extends MongoRepository<Book, String> {

    Book findByTitle(String title);

    Book findByTitleAndAuthorNameAndGenreTitle(String title, String authorName, String genreTitle);

    @Query
    List<Book> findByAuthorIsNotNull();

    @Query
    List<Book> findByGenreIsNotNull();

}
