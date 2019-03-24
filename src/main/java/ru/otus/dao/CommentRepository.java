package ru.otus.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Comment;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
}
