package ru.otus.dao;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.Book;


public interface BookRepository extends MongoRepository<Book, UUID> {

    Book findByTitle(String title);

    Book findByTitleAndAuthorNameAndGenreTitle(String title, String authorName, String genreTitle);

}
