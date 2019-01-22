package ru.otus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Genre;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {


}
