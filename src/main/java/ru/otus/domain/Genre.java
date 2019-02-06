package ru.otus.domain;

import java.util.ArrayList;
import java.util.List;

public class Genre {

    private String title;

    private List<Comment> comments = new ArrayList<>();

    public Genre(String genreTitle) {
        this.title = genreTitle;
    }

    public Genre() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "title='" + title + '\'' +
                ", comments=" + comments +
                '}';
    }
}
