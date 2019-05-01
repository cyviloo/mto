/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.ejb.mob.endpoints.impl;

import edu.p.lodz.pl.mto.entities.Book;
import edu.p.lodz.pl.mto.mob.dao.BookFacadeLocal;
import edu.p.lodz.pl.mto.mok.dao.AccountFacadeMOKLocal;
import java.util.Arrays;
import javax.transaction.TransactionRolledbackException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Borys
 */
public class MOBTest {
    
    private MOBEndpoint endpoint;
    
    public MOBTest() {
        endpoint = new MOBEndpoint();
        endpoint.accountFacade = mock(AccountFacadeMOKLocal.class);
        endpoint.bookFacade = mock(BookFacadeLocal.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws TransactionRolledbackException {
        Book b1 = new Book("A", "B", 1992);
        Book b2 = new Book("Aa", "Bb", 1392);
        when(endpoint.bookFacade.getAllBooks()).thenReturn(Arrays.asList(b1, b2));
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void shouldReturnCorrectNumberOfAllBooks() {
        Assert.assertEquals(endpoint.getAllBooks().size(), 2);
    }
    
        @Test
    public void shouldReturnCorrectBooks() {
        Book book1 = endpoint.getAllBooks().get(0);
        Book book2 = endpoint.getAllBooks().get(1);
        Assert.assertEquals(book1.getAuthor(), "B");
        Assert.assertEquals(book2.getAuthor(), "Bb");
        Assert.assertEquals(book1.getTitle(), "A");
        Assert.assertEquals(book2.getTitle(), "Aa");
        Assert.assertEquals(book1.getYear(), 1992);
        Assert.assertEquals(book2.getYear(), 1392);
    }

}
