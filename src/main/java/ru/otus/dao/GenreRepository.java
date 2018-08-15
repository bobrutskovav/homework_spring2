package ru.otus.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    @Override
    List<Genre> findAll();
}
