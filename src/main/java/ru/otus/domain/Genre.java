package ru.otus.domain;

import java.util.List;

public class Genre {

    private String title;
    private List<Comment> comment;

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

    @Override
    public String toString() {
        return "Genre{" +
                ", title='" + title + '\'' +
                '}';
    }
}
