package ru.otus.dao;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    Book findByTitle(String title);

    Book findByTitleAndAuthorNameAndGenreTitle(String title, String authorName, String genreTitle);

    List<Book> findByAuthorIsNotNull();

    List<Book> findByGenreIsNotNull();

}
