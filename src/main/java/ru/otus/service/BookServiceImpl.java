package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.dao.BookDao;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }


    @Override
    public void storeNewBook(String bookName, String author, String genre) {

    }

    @Override
    public void printAllBooks() {

    }

    @Override
    public void printAllByGenre(String genre) {

    }

    @Override
    public void printByName(String name) {

    }
}
