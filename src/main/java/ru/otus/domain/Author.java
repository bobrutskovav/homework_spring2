package ru.otus.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;



public class Author {

    @Id
    private UUID id;
    private String name;
    private List<Comment> comment;

    public Author(String name) {
        this.name = name;
    }


    public Author() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comment=" + comment +
                '}';
    }
}
