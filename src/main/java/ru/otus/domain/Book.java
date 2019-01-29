package ru.otus.domain;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;
    private String title;

    @JoinColumn(name = "AUTHOR_ID")
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Author author;

    @JoinColumn(name = "GENRE_ID")
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Genre genre;

    @JoinColumn(name = "COMMENT_FOR_ENTITY_ID")
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Comment> comment;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
