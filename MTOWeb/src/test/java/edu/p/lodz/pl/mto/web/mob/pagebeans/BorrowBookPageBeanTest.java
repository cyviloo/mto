/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.mob.pagebeans;

import edu.p.lodz.pl.mto.entities.Book;
import edu.p.lodz.pl.mto.enums.MessageLevel;
import edu.p.lodz.pl.mto.web.mob.MOBSession;
import edu.p.lodz.pl.mto.web.mok.AccountSession;
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
public class BorrowBookPageBeanTest {
    
    private class TestException extends Exception {}
    
    private final BorrowBookPageBean bean;
    
    public BorrowBookPageBeanTest() {
        bean = new BorrowBookPageBean();
        bean.accountSession = mock(AccountSession.class);
        bean.mobSession = mock(MOBSession.class);
        bean.bookDataModel = mock(DataModel.class);
    }
    
    @Before
    public void setUp() {
        doNothing().when(bean.mobSession).borrowBook(any(Book.class), anyString());
        doNothing().when(bean.mobSession).
                showMessage(anyString(), any(MessageLevel.class));
        when(bean.accountSession.showCurrentUser()).thenReturn("TEST-USER");
        when(bean.bookDataModel.getRowData()).thenReturn(new Book());
    }
    
    @Test (expected = TestException.class)
    public void shouldCallMOBSessionBorrowBookOnBorrowBook() {
        doThrow(TestException.class).when(bean.mobSession)
                .borrowBook(any(Book.class), anyString());
        bean.borrowBook();
    }
    
    @Test (expected = TestException.class)
    public void shouldCallMOBSessionShowMessageOnBorrowBook() {
        doThrow(TestException.class).when(bean.mobSession)
                .showMessage(anyString(), any(MessageLevel.class));
        bean.borrowBook();
    }

    @Test
    public void shouldNavigateToHomePageWhenBorrowingBook() {
        Assert.assertEquals("homePage", bean.borrowBook());
    }
}
