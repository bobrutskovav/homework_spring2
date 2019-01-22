package ru.otus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;

import java.util.UUID;


public interface BookRepository extends JpaRepository<Book, UUID> {

    Book findByTitle(String title);

    Book findByTitleAndAuthorNameAndGenreTitle(String title, String authorName, String genreTitle);

    @Transactional
    int deleteBookByTitle(String title);


}
