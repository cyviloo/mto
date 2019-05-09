/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.mok.pagebeans;

import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.enums.MessageLevel;
import edu.p.lodz.pl.mto.web.mok.AccountSession;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Borys
 */
public class RegisterPageBeanTest {
    
    private class TestException extends Exception {}
    
    private final RegisterPageBean bean;
    private final Account account;
    
    public RegisterPageBeanTest() {
        bean = new RegisterPageBean();
        bean.accountSession = mock(AccountSession.class);
        account = new Account();
        account.setIdAccount(555);
        account.setLogin("TEST-LOGIN");
        account.setName("TEST-NAME");
        account.setSurname("TEST-SURNAME");
        account.setBirthDate(new Date(1970, 2, 2));
        bean.setAccount(account);
    }
    
    @Before
    public void setUp() {
        doNothing().when(bean.accountSession).registerAccount(any(Account.class));
        doNothing().when(bean.accountSession)
                .showMessage(anyString(), any(MessageLevel.class));
    }

    @Test
    public void shouldGetCorrectAccount() {
        Assert.assertEquals(account, bean.getAccount());
    }
    
    @Test (expected = TestException.class)
    public void shouldCallAccountSessionRegisterAccountOnRegisterAccount() {
        doThrow(TestException.class)
                .when(bean.accountSession).registerAccount(any(Account.class));
        bean.registerAccount();
    }
    
    @Test (expected = TestException.class)
    public void shouldCallAccountSessionShowMessageOnRegisterAccount() {
        doThrow(TestException.class).when(bean.accountSession)
                .showMessage(anyString(), any(MessageLevel.class));
        bean.registerAccount();
    }
    
    @Test
    public void shouldNavigateToHomePageAfterRegistering() {
        Assert.assertEquals("homePage", bean.registerAccount());
    }
}
