package ru.otus.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }


    @Override
    public void storeBook(Book book) {
        //ToDo подумать как записать в таблицу...Вероятно нужно сделать поиск по названию жанра и автору и взять айди...если их нет, надо сгенерить новый айди и записать в табличку...

        Map<String, String> params = new HashMap<>();
        params.put("authorName", book.getAuthorName());
        String id;
        try {
            id = jdbc
                    .queryForObject("SELECT A.ID FROM AUTHOR A WHERE A.AUTHORNAME = :authorName", params, String.class);
        } catch (EmptyResultDataAccessException e) {
            id = null;
        }
        if (id == null) {
            id = UUID.randomUUID().toString();
            // jdbc.update("INSERT")
        }

        //jdbc.query("insert into books (id, title,author,genre) values (, 'Book1','author1','genre1')")

    }

    @Override
    public List<Book> getAllBooks() {
        return jdbc
                .query("select * from Books JOIN Author ON Books.author = Author.authorName JOIN Genre ON Books.genre = Genre.genreTitle ",
                        new HashMap<>(), new BookMapper());
    }

    @Override
    public Book getBookByName(String name) {
        return null;
    }

    @Override
    public List<Book> getAllByGenre(String genre) {
        return null;
    }


    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Book book = new Book();
            book.setId(resultSet.getInt("id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthorName(resultSet.getString("authorName"));
            book.setGenreTitle(resultSet.getString("genreTitle"));
            return book;
        }
    }
}
