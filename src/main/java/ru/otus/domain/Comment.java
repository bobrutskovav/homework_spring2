package ru.otus.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "COMMENT")
public class Comment {

    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "COMMENT_FOR_ENTITY_ID", updatable = false, nullable = false)
    private UUID commentForEntityId;
    @Column(name = "COMMENTTEXT")
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
