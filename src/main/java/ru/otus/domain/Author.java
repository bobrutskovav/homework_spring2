package ru.otus.domain;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "AUTHOR")
public class Author {

    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "AUTHORNAME")
    private String name;

    @JoinColumn(name = "ENTITY_ID")
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
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
