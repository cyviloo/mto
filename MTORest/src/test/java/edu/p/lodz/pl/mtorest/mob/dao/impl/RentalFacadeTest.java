/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.mob.dao.impl;

import edu.p.lodz.pl.mtorest.entities.Account;
import edu.p.lodz.pl.mtorest.entities.Rental;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionRolledbackException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author Borys
 */
public class RentalFacadeTest {

    private class TestException extends Exception {
    }

    private final RentalFacade rentalFacade;
    private final TypedQuery<Rental> tq;
    private final Rental rental1;
    private final Rental rental2;

    public RentalFacadeTest() {
        rentalFacade = new RentalFacade();
        rentalFacade.em = mock(EntityManager.class);
        tq = mock(TypedQuery.class);

        rental1 = new Rental();
        rental1.setIdRental(11);
        rental2 = new Rental();
        rental2.setIdRental(22);
    }

    @Before
    public void setUp() {
        when(tq.getResultList()).thenReturn(Arrays.asList(rental1, rental2));
        when(rentalFacade.em.createNamedQuery(anyString(), eq(Rental.class)))
                .thenReturn(tq);
        when(rentalFacade.em.find(eq(Rental.class), any())).thenReturn(rental1);
        when(tq.setParameter(anyString(), eq(Account.class))).thenReturn(tq);
        doNothing().when(rentalFacade.em).persist(eq(Rental.class));
        doNothing().when(rentalFacade.em).flush();
        when(rentalFacade.em.merge(eq(Rental.class))).thenReturn(null);
    }

    @Test(expected = TestException.class)
    public void shouldCallPersistOnCreate() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            throw new TestException();
        }).when(rentalFacade.em).persist(any());

        rentalFacade.create(rental1);
    }

    @Test(expected = TestException.class)
    public void shouldCallFlushOnCreate() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            throw new TestException();
        }).when(rentalFacade.em).flush();

        rentalFacade.create(rental1);
    }

    @Test(expected = TestException.class)
    public void shouldCallMergeOnEdit() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            throw new TestException();
        }).when(rentalFacade.em).merge(any());

        rentalFacade.edit(rental1);
    }

    @Test(expected = TestException.class)
    public void shouldCallFlushOnEdit() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            throw new TestException();
        }).when(rentalFacade.em).flush();

        rentalFacade.edit(rental1);
    }

    @Test
    public void shouldReturnRentalsByUser()
            throws TransactionRolledbackException {
        Account account = new Account();
        List<Rental> list = rentalFacade.findByUser(account);

        Assert.assertEquals(2, list.size());
        Assert.assertEquals(rental1, list.get(0));
        Assert.assertEquals(rental2, list.get(1));
    }

    @Test
    public void shouldReturnRentalsHistoryByUser()
            throws TransactionRolledbackException {
        Account account = new Account();
        List<Rental> list = rentalFacade.findHistoryByUser(account);

        Assert.assertEquals(2, list.size());
        Assert.assertEquals(rental1, list.get(0));
        Assert.assertEquals(rental2, list.get(1));
    }
}
