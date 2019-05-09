/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.mob.dao.impl;

import edu.p.lodz.pl.mto.entities.Book;
import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionRolledbackException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Borys
 */
public class BookFacadeTest {
    
    private final BookFacade bookFacade;
    private final TypedQuery<Book> tq;
    private final Book book1;
    private final Book book2;
    
    public BookFacadeTest() {
        bookFacade = new BookFacade();
        bookFacade.em = mock(EntityManager.class);
        tq = mock(TypedQuery.class);
        
        book1 = new Book("TEST-TITLE", "TEST-AUTHOR", 2000);
        book1.setIdBook(1);
        book2 = new Book("TEST-TITLE2", "TEST-AUTHOR", 2000);
        book2.setIdBook(2);
    }
    
    @Before
    public void setUp() {
        when(tq.getResultList()).thenReturn(Arrays.asList(book1, book2));
        when(bookFacade.em.createNamedQuery(anyString(), eq(Book.class)))
                .thenReturn(tq);
        when(bookFacade.em.find(eq(Book.class), any())).thenReturn(book1);
    }

    @Test
    public void shouldGetCorrectNumberOfBooks()
            throws TransactionRolledbackException {
        Assert.assertEquals(2, bookFacade.getAllBooks().size());
    }
    
    @Test
    public void shouldGetAllBooks() throws TransactionRolledbackException {
        Book b1 = bookFacade.getAllBooks().get(0);
        Book b2 = bookFacade.getAllBooks().get(1);
        Assert.assertEquals(b1, book1);
        Assert.assertEquals(b2, book2);
    }
    
    @Test
    public void shouldFindCorrectBook() {
        Assert.assertEquals(book1, bookFacade.find(0));
    }
}
