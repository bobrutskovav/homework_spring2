package ru.otus.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    @Override
    List<Author> findAll();
}
