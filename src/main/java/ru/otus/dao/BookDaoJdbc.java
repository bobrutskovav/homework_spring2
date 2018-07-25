//package ru.otus.dao;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
//import org.springframework.stereotype.Repository;
//import ru.otus.domain.Book;
//
//@Repository
//public class BookDaoJdbc implements BookDao {
//
//    private final NamedParameterJdbcOperations jdbc;
//
//    @Autowired
//    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
//        this.jdbc = jdbc;
//    }
//
//
//    @Override
//    public void storeBook(Book book) {
//        Map<String, String> params = new HashMap<>();
//        params.put("bookId", book.getId());
//        params.put("title", book.getTitle());
//        params.put("authorName", book.getAuthor());
//        params.put("genreTitle", book.getGenre());
//        String authorId = getObjectOrNull("SELECT A.ID FROM AUTHOR A WHERE A.AUTHORNAME = :authorName", params,
//                String.class);
//        if (authorId == null) {
//            authorId = UUID.randomUUID().toString();
//            params.put("authorId", authorId);
//            jdbc.update("INSERT INTO AUTHOR (ID,AUTHORNAME) VALUES (:authorId,:authorName)", params);
//        }
//        params.put("authorId", authorId);
//
//        String genreId = getObjectOrNull("SELECT G.ID FROM GENRE G WHERE G.GENRETITLE = :genreTitle", params,
//                String.class);
//
//        if (genreId == null) {
//            genreId = UUID.randomUUID().toString();
//            params.put("genreId", genreId);
//            jdbc.update("INSERT INTO GENRE (ID,GENRETITLE) VALUES (:genreId,:genreTitle)", params);
//        }
//        params.put("genreId", genreId);
//        jdbc.update("INSERT INTO BOOKS (ID, TITLE,AUTHORID,GENREID) VALUES (:bookId ,:title,:authorId,:genreId)",
//                params);
//
//    }
//
//    @Override
//    public List<Book> getAllBooks() {
//        return jdbc
//                .query("SELECT * FROM BOOKS JOIN AUTHOR ON BOOKS.AUTHORID = AUTHOR.ID JOIN GENRE ON BOOKS.GENREID = GENRE.ID",
//                        new HashMap<>(), new BookMapper());
//    }
//
//    @Override
//    public Book getBookByTitle(String title) {
//        Map<String, String> params = new HashMap<>();
//        params.put("title", title);
//        String id = getObjectOrNull("SELECT ID FROM BOOKS WHERE BOOKS.TITLE = :title", params, String.class);
//        if (id == null) {
//            return null;
//        }
//        return getBookByID(id);
//    }
//
//    @Override
//    public Book getBookByID(String id) {
//        Book book = null;
//        Map<String, String> params = new HashMap<>();
//        params.put("id", id);
//        try {
//            book = jdbc.queryForObject(
//                    "SELECT * FROM BOOKS JOIN AUTHOR ON BOOKS.AUTHORID = AUTHOR.ID JOIN GENRE ON BOOKS.GENREID = GENRE.ID WHERE BOOKS.ID = :id",
//                    params, new BookMapper());
//        } catch (EmptyResultDataAccessException e) {
//
//        }
//        return book;
//
//    }
//
//    private <T> T getObjectOrNull(String query, Map<String, String> params, Class<T> clazz) {
//        T founded;
//        try {
//            founded = jdbc.queryForObject(query, params, clazz);
//        } catch (EmptyResultDataAccessException e) {
//            founded = null;
//        }
//        return founded;
//    }
//
//    private static class BookMapper implements RowMapper<Book> {
//
//        @Override
//        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
//            Book book = new Book();
//            book.setId(resultSet.getString("id"));
//            book.setTitle(resultSet.getString("title"));
//            book.setAuthor(resultSet.getString("authorName"));
//            book.setGenre(resultSet.getString("genreTitle"));
//            return book;
//        }
//    }
//}
