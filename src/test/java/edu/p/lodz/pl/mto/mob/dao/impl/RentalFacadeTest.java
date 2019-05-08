/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.mob.dao.impl;

import edu.p.lodz.pl.mto.entities.Rental;
import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author Tomasz
 */
public class RentalFacadeTest {
    
    private class TestException extends Exception {}
    
    private RentalFacade rentalFacade;
    private TypedQuery<Rental> tq;
    private Rental rental1, rental2;
    
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
        doNothing().when(rentalFacade.em).persist(eq(Rental.class));
        doNothing().when(rentalFacade.em).flush();
        when(rentalFacade.em.merge(eq(Rental.class))).thenReturn(null);
    }

    @Test (expected = TestException.class)
    public void shouldCallPersistOnCreate() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            throw new TestException();
        }).when(rentalFacade.em).persist(any());
        
        rentalFacade.create(rental1);
    }
    
    @Test (expected = TestException.class)
    public void shouldCallFlushOnCreate() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            throw new TestException();
        }).when(rentalFacade.em).flush();
        
        rentalFacade.create(rental1);
    }
    
    @Test (expected = TestException.class)
    public void shouldCallMergeOnEdit() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            throw new TestException();
        }).when(rentalFacade.em).merge(any());
        
        rentalFacade.edit(rental1);
    }
    
    @Test (expected = TestException.class)
    public void shouldCallFlushOnEdit() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            throw new TestException();
        }).when(rentalFacade.em).flush();
        
        rentalFacade.edit(rental1);
    }
    
}
