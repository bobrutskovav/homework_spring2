package ru.otus.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;

@Transactional
@SuppressWarnings("JpaQlInspection")
@Repository
public class LibraryJpaRepository implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void storeBook(Book book) {
        em.persist(book);
    }

    @Override
    public List<Book> getAllBooks() {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b",
                Book.class);
        return query.getResultList();
    }

    @Override
    public Book getBookByTitle(String title) {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b where b.title = :title",
                Book.class);
        query.setParameter("title", title);
        return query.getSingleResult();
    }

    @Override
    public Book getBookByID(String id) {
        return em.find(Book.class, id);
    }
}