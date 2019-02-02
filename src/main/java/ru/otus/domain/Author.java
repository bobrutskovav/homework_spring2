package ru.otus.domain;

import java.util.List;


public class Author {


    private String name;

    private List<Comment> comments;

    public Author(String name) {
        this.name = name;
    }


    public Author() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", comments=" + comments +
                '}';
    }
}
