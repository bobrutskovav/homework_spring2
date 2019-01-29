package ru.otus;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.AuthorRepository;
import ru.otus.dao.BookRepository;
import ru.otus.dao.CommentRepository;
import ru.otus.dao.GenreRepository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.BookService;
import ru.otus.service.BookServiceImpl;

@RunWith(SpringRunner.class)
public class BookServiceTest {


    private final static String TITLE = "testTitle";
    private final static String GENRE = "testGenre";
    private final static String AUTHOR = "testAuthor";
    @Autowired
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private CommentRepository commentRepository;


    private ByteArrayOutputStream baos;
    private PrintStream ps;


    @Before
    public void setUp() {
        Book book = new Book();
        book.setGenre(new Genre(GENRE));
        book.setAuthor(new Author(AUTHOR));
        book.setComment(new ArrayList<>());
        book.setTitle(TITLE);
        Mockito.when(bookRepository.findByTitle(TITLE)).thenReturn(book);
        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos);
        System.setOut(ps);
    }

    @Test
    public void bookServicePrintValidTitle() {
        bookService.printByName(TITLE);
        Assert.assertTrue("Title is not Printed!", baos.toString().contains("Title: " + TITLE));
    }


    @Test
    public void bookServicePrintValidGenre() {
        bookService.printByName(TITLE);
        Assert.assertTrue("Genre is not Printed!", baos.toString().contains("Genre: " + GENRE));
    }

    @Test
    public void bookServicePrintValidAuthor() {
        bookService.printByName(TITLE);
        Assert.assertTrue("Author is not Printed!", baos.toString().contains("Author: " + AUTHOR));
    }

    @After
    public void setDefaultSysOut() throws IOException {
        System.out.flush();
        System.setOut(System.out);
        ps.close();
        baos.close();
    }

    @TestConfiguration
    static class BookServiceImplTestContextConfiguration {

        @Bean
        public BookService bookService() {
            return new BookServiceImpl();
        }
    }
}
