package ru.otus.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class BookDaoJdbc  implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }


    @Override
    public void storeBook(Book book) {

    }

    @Override
    public List<Book> getAllBooks() {
        return jdbc.query("select * from Books as B, Author AS A, Genre AS G JOIN B.authorName = A.name JOIN B.genreTilte = G.Title ",new HashMap<>(), new BookMapper());
    }

    @Override
    public Book getBookByName(String name) {
        return null;
    }

    @Override
    public List<Book> getAllByGenre(String genre) {
        return null;
    }


    private static class BookMapper implements RowMapper<Book>{

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Book book = new Book();
            book.setId(resultSet.getInt("id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthorName(resultSet.getString("authorName"));
            book.setGenreTitle(resultSet.getString("genreTitle"));
            return null;
        }
    }
}
