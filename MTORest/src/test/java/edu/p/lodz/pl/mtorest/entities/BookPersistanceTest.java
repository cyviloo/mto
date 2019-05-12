/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Tomasz
 */
@RunWith(Arquillian.class)
public class BookPersistanceTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Book.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    private static final String[] BOOK_TITLES = {
        "TEST_BOOK1",
        "TEST_BOOK2",
        "TEST_BOOK3"
    };

    private static final String BOOK_AUTHOR = "TEST_AUTHOR";

    @PersistenceContext(unitName = "test")
    EntityManager em;

    @Inject
    UserTransaction utx;

    @Before
    public void preparePersistenceTest() throws Exception {
        clearData();
        insertData();
        startTransaction();
    }

    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Dumping old records...");
        em.createQuery("delete from Book").executeUpdate();
        utx.commit();
    }

    private void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting records...");
        for (String title : BOOK_TITLES) {
            Book book = new Book(title, BOOK_AUTHOR, 2000);
            em.persist(book);
        }
        utx.commit();
        // clear the persistence context (first-level cache)
        em.clear();
    }

    private void startTransaction() throws Exception {
        utx.begin();
        em.joinTransaction();
    }

    @Test
    public void shouldFindAllBookUsingJpqlQuery() throws Exception {
        // given
        String fetchingAllBooksInJpql = "select b from Book b order by b.idBook";

        // when
        System.out.println("Selecting (using JPQL)...");
        List<Book> books = em.createQuery(fetchingAllBooksInJpql, Book.class).getResultList();

        // then
        System.out.println("Found " + books.size() + " books (using JPQL):");
        assertContainsAllBooks(books);
    }

    private static void assertContainsAllBooks(Collection<Book> retrievedBooks) {
        Assert.assertEquals(BOOK_TITLES.length, retrievedBooks.size());
        final Set<String> retrievedBooksTitles = new HashSet<String>();
        for (Book book : retrievedBooks) {
            System.out.println("* " + book);
            retrievedBooksTitles.add(book.getTitle());
        }
        Assert.assertTrue(retrievedBooksTitles.containsAll(Arrays.asList(BOOK_TITLES)));
    }

}
