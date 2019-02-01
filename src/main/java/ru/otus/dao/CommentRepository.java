package ru.otus.dao;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, UUID> {
}
