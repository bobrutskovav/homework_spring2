package ru.otus.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Book;


public interface BookRepository extends CrudRepository<Book, Long> {

    Book findByTitle(String title);

    @Override
    List<Book> findAll();

    Book findByTitleAndAuthorNameAndGenreTitle(String title, String authorName, String genreTitle);


}
