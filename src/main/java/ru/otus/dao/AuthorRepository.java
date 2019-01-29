package ru.otus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Author;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

}
