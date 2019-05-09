/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.mok;

import edu.p.lodz.pl.mto.beans.MessagesBean;
import edu.p.lodz.pl.mto.ejb.mok.endpoints.MOKEndpointLocal;
import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.enums.MessageLevel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Borys
 */
public class AccountSessionTest {
    
    private class TestException extends Exception {}
    
    private final AccountSession session;
    private final Account account;
    
    public AccountSessionTest() {
        session = new AccountSession();
        session.mOKEndpoint = mock(MOKEndpointLocal.class);
        session.messagesBean = mock(MessagesBean.class);
        account = new Account();
        account.setIdAccount(111);
        account.setLogin("TEST-LOGIN");
    }
    
    @Before
    public void setUp() {
        when(session.mOKEndpoint.getAccountByLogin(anyString()))
                .thenReturn(account);
    }

    @Test (expected = TestException.class)
    public void shouldCallEndpointRegisterAccountOnRegisterAccount() {
        doThrow(TestException.class).when(session.mOKEndpoint)
                .registerAccount(any(Account.class));
        session.registerAccount(new Account());
    }
    
    @Test (expected = TestException.class)
    public void shouldCallMessagesBeanShowMessageOnShowMessage() {
        doThrow(TestException.class).when(session.messagesBean)
                .showMessage(anyString(), any(MessageLevel.class));
        session.showMessage("TEST-MESSAGE", MessageLevel.FATAL);
    }
    
    @Test
    public void shouldReturnFalseOnTryingToLoginOnNonExistentLogin() {
        AccountSession lsession;
        lsession = new AccountSession();
        lsession.mOKEndpoint = mock(MOKEndpointLocal.class);
        lsession.messagesBean = mock(MessagesBean.class);
        when(lsession.mOKEndpoint.getAccountByLogin(anyString()))
                .thenReturn(null);
        
        Assert.assertFalse(lsession.login("TEST-LOGIN"));
    }
    
    @Test
    public void shouldLoginWithExistingLogin() {
        Assert.assertTrue(session.login("TEST-LOGIN"));
        Assert.assertTrue(session.isLoggedIn());
        Assert.assertEquals("TEST-LOGIN", session.showCurrentUser());
    }
}
