package ru.otus.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import java.util.ArrayList;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

@ChangeLog
public class MongoChangeLog {


    @ChangeSet(order = "001", id = "addBook", author = "a.bobrutskov")
    public void insertBook(MongoTemplate mongoTemplate) {

        Book book1 = new Book();
        book1.setTitle("book1");
        book1.setAuthor(new Author("author1"));
        book1.setGenre(new Genre("genre1"));
        book1.setComments(new ArrayList<>());
        book1.getComments().add(new Comment("comment1"));
        mongoTemplate.save(book1);

    }

}
