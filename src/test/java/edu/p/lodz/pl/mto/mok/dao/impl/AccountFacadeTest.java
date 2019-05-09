/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.mok.dao.impl;

import edu.p.lodz.pl.mto.entities.Account;
import java.util.ArrayList;
import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author Tomasz
 */
public class AccountFacadeTest {
    
    private class TestException extends Exception {}
    
    private final AccountFacadeMOK facade;
    private final TypedQuery<Account> tq;
    private final Account account1, account2;
    
    public AccountFacadeTest() {
        facade = new AccountFacadeMOK();
        facade.em = mock(EntityManager.class);
        tq = mock(TypedQuery.class);
        
        account1 = new Account();
        account2 = new Account();
        
        account1.setIdAccount(333);
        account1.setLogin("ABC");
        account1.setName("TEST-NAME");
        
        account2.setIdAccount(444);
        account2.setLogin("CDE");
        account2.setName("TEST_NAME");
    }
    
    @Before
    public void setUp() {
        when(facade.em.createNamedQuery(anyString(), eq(Account.class)))
                .thenReturn(tq);
        when(tq.getResultList()).thenReturn(Arrays.asList(account1, account2));
        when(tq.setParameter(anyString(), anyString())).thenReturn(tq);
        doNothing().when(facade.em).persist(eq(Account.class));
        doNothing().when(facade.em).flush();
    }

    @Test (expected = TestException.class)
    public void shouldCallEntityManagerPersistOnCreate() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            throw new AccountFacadeTest.TestException();
        }).when(facade.em).persist(any());
        facade.create(new Account());
    }
    
    @Test (expected = TestException.class)
    public void shouldCallEntityManagerFlushOnCreate() {
        doThrow(TestException.class).when(facade.em).flush();
        facade.create(new Account());
    }
    
    @Test
    public void shouldReturnNullOnLoginNotFound() {
        when(tq.getResultList()).thenReturn(new ArrayList<>());
        Account result = facade.findByLogin("TEST-LOGIN");
        Assert.assertNull(result);
    }
    
    @Test
    public void shouldReturnFirstFoundAccountOnLoginFound() {
        Account result = facade.findByLogin("TEST-LOGIN");
        Assert.assertEquals(result, account1);
    }
}
