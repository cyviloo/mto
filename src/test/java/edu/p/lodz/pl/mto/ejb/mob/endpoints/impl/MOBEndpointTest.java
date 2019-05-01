/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.ejb.mob.endpoints.impl;

import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.entities.Book;
import edu.p.lodz.pl.mto.mob.dao.BookFacadeLocal;
import edu.p.lodz.pl.mto.mob.dao.RentalFacadeLocal;
import edu.p.lodz.pl.mto.mok.dao.AccountFacadeMOKLocal;
import edu.p.lodz.pl.mto.web.mok.AccountSession;
import edu.p.lodz.pl.mto.exceptions.ValidationException;
import java.rmi.RemoteException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.transaction.TransactionRolledbackException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Borys
 */
public class MOBEndpointTest {
    
    static private MOBEndpoint endpoint;
    static private Principal principal;
    static private Account account;
    
    @BeforeClass
    public static void setUpClass() throws TransactionRolledbackException {
        endpoint = new MOBEndpoint();
        endpoint.accountFacade = mock(AccountFacadeMOKLocal.class);
        endpoint.accountSession = mock(AccountSession.class);
        endpoint.bookFacade = mock(BookFacadeLocal.class);
        endpoint.rentalFacade = mock(RentalFacadeLocal.class);
        endpoint.sessionContext = mock(SessionContext.class);
        endpoint.transactionID = 666;
        MOBEndpoint.loger = mock(Logger.class);
        
        principal = mock(Principal.class);
        when(principal.getName()).thenReturn("MockPrincipal");
        when(endpoint.sessionContext.getCallerPrincipal()).thenReturn(principal);
        
        doNothing().when(MOBEndpoint.loger).log(anyVararg());
        
        account = new Account();
        account.setIdAccount(1);
        account.setBirthDate(new Date(1970, 1, 1));
        account.setLogin("MockAccount");
        account.setName("Mock1");
        account.setSurname("Mock2");
        
        when(endpoint.accountSession.showCurrentUser()).thenReturn("MockUser");
        when(endpoint.accountFacade.findByLogin("MockUser")).thenReturn(account);
        
        Book b1 = new Book("A", "B", 1992);
        Book b2 = new Book("Aa", "Bb", 1392);
        when(endpoint.bookFacade.getAllBooks()).thenReturn(Arrays.asList(b1, b2));
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Test
    public void shouldReturnCorrectNumberOfAllBooks() {
        Assert.assertEquals(2, endpoint.getAllBooks().size());
    }
    
    @Test
    public void shouldReturnCorrectBooks() {
        Book book1 = endpoint.getAllBooks().get(0);
        Book book2 = endpoint.getAllBooks().get(1);
        Assert.assertEquals("B", book1.getAuthor());
        Assert.assertEquals("Bb", book2.getAuthor());
        Assert.assertEquals("A", book1.getTitle());
        Assert.assertEquals("Aa", book2.getTitle());
        Assert.assertEquals(1992, book1.getYear());
        Assert.assertEquals(1392, book2.getYear());
    }
    
    @Test
    public void shouldDoNothingOnAfterBegin() throws EJBException, RemoteException {
        endpoint.afterBegin();
    }
    
    @Test
    public void shouldDoNothingOnBeforeCompletion() throws EJBException, RemoteException {
        endpoint.beforeCompletion();
    }
    
    @Test
    public void shouldDoNothingOnAfterCompletion() throws EJBException, RemoteException {
        endpoint.afterCompletion(true);
        endpoint.afterCompletion(false);
    }
    
    @Test
    public void shouldFetchCorrectAccountFromSession() {
        Account acc = endpoint.fetchAccountFromSession();
        Assert.assertEquals(new Integer(1), acc.getIdAccount());
        Assert.assertEquals("MockAccount", acc.getLogin());
        Assert.assertEquals(new Date(1970, 1, 1), acc.getBirthDate());
        Assert.assertEquals("Mock1", acc.getName());
        Assert.assertEquals("Mock2", acc.getSurname());
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowOnBorrowingNonExistentBook() throws TransactionRolledbackException {
        Book book = mock(Book.class);
        when(book.getIdBook()).thenReturn(Integer.MIN_VALUE);
        when(endpoint.bookFacade.find(Integer.MIN_VALUE)).thenReturn(null);
        
        endpoint.borrowBook(book, "test_login");
    }
}
