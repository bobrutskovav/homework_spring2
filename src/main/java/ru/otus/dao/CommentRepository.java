package ru.otus.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
}
