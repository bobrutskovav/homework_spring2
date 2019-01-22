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

    @Column(name = "ENTITY_ID", updatable = false, nullable = false)
    private UUID entity_id;
    @Column(name = "COMMENTTEXT")
    private String text;


    public Comment(UUID entity_id, String text) {
        this.entity_id = entity_id;
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

    public UUID getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(UUID entity_id) {
        this.entity_id = entity_id;
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
                ", entity_id=" + entity_id +
                ", text='" + text + '\'' +
                '}';
    }
}
