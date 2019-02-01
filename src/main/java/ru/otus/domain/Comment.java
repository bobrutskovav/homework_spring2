package ru.otus.domain;


import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document

public class Comment {

    @Id
    private UUID id;
    private UUID commentForEntityId;
    private String text;


    public Comment(UUID commentForEntityId, String text) {
        this.commentForEntityId = commentForEntityId;
        this.text = text;
    }

    public Comment() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCommentForEntityId() {
        return commentForEntityId;
    }

    public void setCommentForEntityId(UUID commentForEntityId) {
        this.commentForEntityId = commentForEntityId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", commentForEntityId=" + commentForEntityId +
                ", text='" + text + '\'' +
                '}';
    }
}
