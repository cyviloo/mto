/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.ejb.mob.endpoints.impl;

import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.entities.Book;
import edu.p.lodz.pl.mto.entities.Rental;
import edu.p.lodz.pl.mto.mob.dao.BookFacadeLocal;
import edu.p.lodz.pl.mto.mob.dao.RentalFacadeLocal;
import edu.p.lodz.pl.mto.mok.dao.AccountFacadeMOKLocal;
import edu.p.lodz.pl.mto.web.mok.AccountSession;
import edu.p.lodz.pl.mto.exceptions.ValidationException;
import java.rmi.RemoteException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.transaction.TransactionRolledbackException;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import static org.mockito.Mockito.*;

/**
 *
 * @author Borys
 */
public class MOBEndpointTest {
    
    private class TestException extends Exception {};
    
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
        doNothing().when(MOBEndpoint.loger).log(anyVararg());
        
        principal = mock(Principal.class);
        when(principal.getName()).thenReturn("MockPrincipal");
        when(endpoint.sessionContext.getCallerPrincipal()).thenReturn(principal);
        
        account = new Account();
        account.setIdAccount(1);
        account.setBirthDate(new Date(1970, 1, 1));
        account.setLogin("MockAccount");
        account.setName("Mock1");
        account.setSurname("Mock2");

        Book b1 = new Book("A", "B", 1992);
        Book b2 = new Book("Aa", "Bb", 1392);
        when(endpoint.bookFacade.getAllBooks()).thenReturn(Arrays.asList(b1, b2));
    }

    @Before
    public void setUp() throws TransactionRolledbackException {
        when(endpoint.accountSession.showCurrentUser()).thenReturn("MockUser");
        when(endpoint.accountFacade.findByLogin("MockUser")).thenReturn(account);
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
    public void shouldDoNothingOnAfterBegin()
            throws EJBException, RemoteException {
        endpoint.afterBegin();
    }

    @Test
    public void shouldDoNothingOnBeforeCompletion()
            throws EJBException, RemoteException {
        endpoint.beforeCompletion();
    }

    @Test
    public void shouldDoNothingOnAfterCompletion()
            throws EJBException, RemoteException {
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

    @Test (expected = ValidationException.class)
    public void shouldThrowValidationExceptionOnNullAccount()
            throws TransactionRolledbackException {
        when(endpoint.accountFacade.findByLogin(anyString())).thenReturn(null);
        endpoint.fetchAccountFromSession();
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowOnBorrowingNonExistentBook()
            throws TransactionRolledbackException {
        Book book = mock(Book.class);
        when(book.getIdBook()).thenReturn(Integer.MIN_VALUE);
        when(endpoint.bookFacade.find(Integer.MIN_VALUE)).thenReturn(null);

        endpoint.borrowBook(book, "test_login");
    }

    @Test
    public void shouldBorrowExistentBook()
            throws TransactionRolledbackException {
        Book book = new Book("A", "B", 1992);
        when(endpoint.bookFacade.find(anyInt())).thenReturn(book);

        ArgumentCaptor captor = ArgumentCaptor.forClass(Rental.class);
        doNothing().when(endpoint.rentalFacade).create((Rental)captor.capture());

        endpoint.borrowBook(book, "test_login");

        Rental resultRental = (Rental)captor.getValue();
        Account resultAccount = resultRental.getAccount();
        Book resultBook = resultRental.getBook();

        Assert.assertTrue(resultRental.isActive());
        
        Assert.assertEquals(new Integer(1), resultAccount.getIdAccount());
        Assert.assertEquals("MockAccount", resultAccount.getLogin());
        Assert.assertEquals("Mock1", resultAccount.getName());
        Assert.assertEquals("Mock2", resultAccount.getSurname());
        Assert.assertEquals(new Date(1970, 1, 1), resultAccount.getBirthDate());
        
        Assert.assertEquals("A", resultBook.getTitle());
        Assert.assertEquals("B", resultBook.getAuthor());
        Assert.assertEquals(1992, resultBook.getYear());
    }

    @Test
    public void shouldCatchConstraintViolationExceptionWhenBorrowingBook()
            throws TransactionRolledbackException {
        Book book = new Book("A", "B", 1992);
        when(endpoint.bookFacade.find(anyInt())).thenReturn(book);
        doThrow(new ConstraintViolationException(new HashSet<>())).
                when(endpoint.rentalFacade).create(any(Rental.class));
        doNothing().when(MOBEndpoint.loger).log(anyVararg());
        endpoint.borrowBook(book, "test_login");
    }

    @Test
    public void shouldReturnCorrectNumberOfUserRentals()
            throws TransactionRolledbackException {
        when(endpoint.rentalFacade.findByUser(account)).thenReturn(
                new ArrayList<>(Arrays.asList(
                        mock(Rental.class),
                        mock(Rental.class),
                        mock(Rental.class),
                        mock(Rental.class))));
        Assert.assertEquals(4, endpoint.getRentalsByUser().size());
    }

    @Test
    public void shouldReturnCorrectUserRentals()
            throws TransactionRolledbackException {
        Rental rental1 = mock(Rental.class);
        
        when(rental1.getAccount()).thenReturn(account);
        when(rental1.getIdRental()).thenReturn(1);
        when(rental1.getBook()).thenReturn(new Book("A", "B", 1999));
        when(rental1.getStartDate()).thenReturn(new Date(2003, 1, 2));
        when(rental1.getEndDate()).thenReturn(new Date(2003, 2, 3));
        
        when(endpoint.rentalFacade.findByUser(account)).thenReturn(
                new ArrayList<>(Arrays.asList(rental1)));
        
        Assert.assertEquals(1, endpoint.getRentalsByUser().size());
        Assert.assertEquals(
                account, endpoint.getRentalsByUser().get(0).getAccount());
        Assert.assertEquals(
                new Integer(1), endpoint.getRentalsByUser().get(0).getIdRental());
        Assert.assertEquals(
                new Book("A", "B", 1999), endpoint.getRentalsByUser().get(0).getBook());
        Assert.assertEquals(
                new Date(2003, 1, 2), endpoint.getRentalsByUser().get(0).getStartDate());
        Assert.assertEquals(
                new Date(2003, 2, 3), endpoint.getRentalsByUser().get(0).getEndDate());
    }

    @Test (expected = TestException.class)
    public void shouldGiveBackBook() throws TransactionRolledbackException {
        Rental rental1 = new Rental();
        rental1.setActive(true);
        rental1.setEndDate(new Date(0));
        doNothing().when(endpoint.rentalFacade).edit(Matchers.eq(rental1));
        
        endpoint.returnBook(rental1);
        Assert.assertFalse(rental1.isActive());
        Assert.assertNotEquals(new Date(0), rental1.getEndDate());
        
        doThrow(TestException.class).
                when(endpoint.rentalFacade).edit(Matchers.eq(rental1));
        
        endpoint.returnBook(rental1);
    }

    @Test
    public void shouldReturnCorrectNumberOfUsersHistoryRentals()
            throws TransactionRolledbackException {
        when(endpoint.rentalFacade.findHistoryByUser(account)).thenReturn(
                new ArrayList<>(Arrays.asList(
                        mock(Rental.class),
                        mock(Rental.class),
                        mock(Rental.class),
                        mock(Rental.class),
                        mock(Rental.class))));
        Assert.assertEquals(5, endpoint.getHistoryRentalsByUser().size());
    }

    @Test
    public void shouldReturnCorrectUsersHistoryRentals()
            throws TransactionRolledbackException {
        Rental rental1 = mock(Rental.class);
        
        when(rental1.getAccount()).thenReturn(account);
        when(rental1.getIdRental()).thenReturn(11);
        when(rental1.getBook()).thenReturn(new Book("B", "C", 1999));
        when(rental1.getStartDate()).thenReturn(new Date(2003, 4, 2));
        when(rental1.getEndDate()).thenReturn(new Date(2003, 5, 3));
        
        when(endpoint.rentalFacade.findHistoryByUser(account)).thenReturn(
                new ArrayList<>(Arrays.asList(rental1)));
        
        Assert.assertEquals(1, endpoint.getHistoryRentalsByUser().size());
        Assert.assertEquals(
                account,
                endpoint.getHistoryRentalsByUser().get(0).getAccount());
        Assert.assertEquals(
                new Integer(11),
                endpoint.getHistoryRentalsByUser().get(0).getIdRental());
        Assert.assertEquals(
                new Book("B", "C", 1999),
                endpoint.getHistoryRentalsByUser().get(0).getBook());
        Assert.assertEquals(
                new Date(2003, 4, 2),
                endpoint.getHistoryRentalsByUser().get(0).getStartDate());
        Assert.assertEquals(
                new Date(2003, 5, 3),
                endpoint.getHistoryRentalsByUser().get(0).getEndDate());
    }
}
