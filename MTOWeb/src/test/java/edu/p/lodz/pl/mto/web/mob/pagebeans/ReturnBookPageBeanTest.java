/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.mob.pagebeans;

import edu.p.lodz.pl.mto.entities.Book;
import edu.p.lodz.pl.mto.entities.Rental;
import edu.p.lodz.pl.mto.enums.MessageLevel;
import edu.p.lodz.pl.mto.web.mob.MOBSession;
import javax.faces.model.DataModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Borys
 */
public class ReturnBookPageBeanTest {
    
    private class TestException extends Exception {}
    
    private final ReturnBookPageBean bean;
    
    public ReturnBookPageBeanTest() {
        bean = new ReturnBookPageBean();
        bean.mobSession = mock(MOBSession.class);
        bean.rentalDataModel = mock(DataModel.class);
    }
    
    @Before
    public void setUp() {
        when(bean.rentalDataModel.getRowData()).thenReturn(new Rental());
        doNothing().when(bean.mobSession).returnBook(any(Rental.class));
        doNothing().when(bean.mobSession)
                .showMessage(anyString(), any(MessageLevel.class));
    }

    @Test (expected = TestException.class)
    public void shouldCallMOBSessionReturnBookOnReturnBook() {
        doThrow(TestException.class).when(bean.mobSession)
                .returnBook(any(Rental.class));
        bean.returnBook();
    }
    
    @Test (expected = TestException.class)
    public void shouldCallMOBSessionShowMessageOnReturnBook() {
        doThrow(TestException.class).when(bean.mobSession)
                .showMessage(anyString(), any(MessageLevel.class));
        bean.returnBook();
    }

    @Test
    public void shouldNavigateToHomePageWhenReturningBook() {
        Assert.assertEquals("homePage", bean.returnBook());
    }
}
