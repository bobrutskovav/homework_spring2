package ru.otus.domain;

import javax.persistence.*;


@Entity
@Table(name="Books")
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;
    private String title;

    @JoinColumn(referencedColumnName="id")
    @ManyToOne
    private Author author;

    @JoinColumn(referencedColumnName="id")
    @ManyToOne
    private Genre genre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
