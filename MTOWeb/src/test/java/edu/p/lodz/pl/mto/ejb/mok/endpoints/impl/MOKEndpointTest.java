/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.ejb.mok.endpoints.impl;
import edu.p.lodz.pl.mto.exceptions.ValidationException;
import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.exceptions.MessagingApplicationException;
import edu.p.lodz.pl.mto.mok.dao.AccountFacadeMOKLocal;
import edu.p.lodz.pl.mto.utils.AccountService;
import java.security.Principal;
import java.util.Date;
import javax.ejb.SessionContext;
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
public class MOKEndpointTest {
    
    static private MOKEndpoint endpoint;
    static private Principal principal;
    
    private class TestException extends Exception {}
    
    @BeforeClass
    public static void setUpClass() {
        endpoint = new MOKEndpoint();
        endpoint.accountService = mock(AccountService.class);
        endpoint.sessionContext = mock(SessionContext.class);
        endpoint.transactionID = 1;
        
        principal = mock(Principal.class);
        when(principal.getName()).thenReturn("TEST-PRINCIPAL");
        when(endpoint.sessionContext.getCallerPrincipal()).thenReturn(principal);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws TransactionRolledbackException {
    }
    
    @After
    public void tearDown() {
    }


    @Test (expected = ValidationException.class)
    public void shouldThrowOnRegisteringExistingAccount()
            throws TransactionRolledbackException {
        @SuppressWarnings("UseInjectionInsteadOfInstantion")
        MOKEndpoint tmpEndpoint = new MOKEndpoint();
        tmpEndpoint.accountService = mock(AccountService.class);
        Account tmpAccount = new Account();
        when(tmpEndpoint.accountService.findByLogin(anyString()))
                .thenReturn(tmpAccount);
        
        tmpEndpoint.registerAccount(tmpAccount);
    }

//    @Test (expected = MessagingApplicationException.class)
//    public void shouldThrowOnCatchingTransactionRolledbackException()
//            throws TransactionRolledbackException {
//        @SuppressWarnings("UseInjectionInsteadOfInstantion")
//        MOKEndpoint tmpEndpoint = new MOKEndpoint();
//        tmpEndpoint.accountService = mock(AccountService.class);
//        Account account = new Account();
//        when(tmpEndpoint.accountService.findByLogin(anyString())).
//                thenThrow(new TransactionRolledbackException());
//        
//        tmpEndpoint.registerAccount(account);
//    }
    
    @Test (expected = TestException.class)
    public void shouldCreateAccountWhenNotAlreadyTaken()
            throws TransactionRolledbackException {
        @SuppressWarnings("UseInjectionInsteadOfInstantion")
        MOKEndpoint tmpEndpoint = new MOKEndpoint();
        tmpEndpoint.accountService = mock(AccountService.class);
        Account account = new Account();
        account.setLogin("TEST-LOGIN");
        when(tmpEndpoint.accountService.findByLogin("TEST-LOGIN")).
                thenReturn(null);
        doThrow(TestException.class).
                when(tmpEndpoint.accountService).create(any());
                
        tmpEndpoint.registerAccount(account);
    }
    
    @Test (expected = ValidationException.class)
    public void shouldReturnNullAccountOnIncorrectLogin()
            throws TransactionRolledbackException {
        @SuppressWarnings("UseInjectionInsteadOfInstantion")
        MOKEndpoint tmpEndpoint = new MOKEndpoint();
        tmpEndpoint.accountService = mock(AccountService.class);
        when(tmpEndpoint.accountService.findByLogin(anyString())).
                thenReturn(null);
                
        tmpEndpoint.getAccountByLogin("TEST-LOGIN");
    }
    
    @Test
    public void shouldFindAccountOncorrectLogin()
            throws TransactionRolledbackException {
        @SuppressWarnings("UseInjectionInsteadOfInstantion")
        MOKEndpoint tmpEndpoint = new MOKEndpoint();
        tmpEndpoint.accountService = mock(AccountService.class);
        String login = "TEST-LOGIN";
        Account account = new Account();
        account.setIdAccount(Integer.MIN_VALUE);
        account.setLogin(login);
        account.setName("TEST-NAME");
        account.setSurname("TEST-SURNAME");
        account.setBirthDate(new Date(1989, 1, 1));
        when(tmpEndpoint.accountService.findByLogin(login)).thenReturn(account);
                
        Account result = tmpEndpoint.getAccountByLogin("TEST-LOGIN");
        
        Assert.assertEquals(new Integer(Integer.MIN_VALUE), result.getIdAccount());
        Assert.assertEquals(login, result.getLogin());
        Assert.assertEquals("TEST-NAME", result.getName());
        Assert.assertEquals("TEST-SURNAME", result.getSurname());
        Assert.assertEquals(new Date(1989, 1, 1), result.getBirthDate());
    }
    
    @Test
    public void shouldReturnCurrentUser() {
        Assert.assertEquals("TEST-PRINCIPAL", endpoint.showCurrentUser());
    }
    
}
