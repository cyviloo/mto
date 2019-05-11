/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.mok.pagebeans;

import edu.p.lodz.pl.mto.web.mok.AccountSession;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Borys
 */
public class LoginPageBeanTest {
    
    private final LoginPageBean bean;
    private static final String login = "TEST-LOGIN";
    
    public LoginPageBeanTest() {
        bean = new LoginPageBean();
        bean.accountSession = mock(AccountSession.class);
    }
    
    @Before
    public void setUp() {
        when(bean.accountSession.login(anyString())).thenReturn(true);
        bean.setLogin(login);
    }
    
    @Test
    public void shouldGetCorrectLogin() {
        Assert.assertEquals(login, bean.getLogin());
    }
    
    @Test
    public void shouldReturnCorrectNavgationStrings() {
        Assert.assertEquals("homePage", bean.navigateIndex());
        Assert.assertEquals("loginPage", bean.navigateLogin());
    }

    @Test
    public void shouldNavigateToHomePageOnSuccessfulLogin() {
        Assert.assertEquals(bean.navigateIndex(), bean.login());
    }
    
    @Test
    public void shouldNavigateToLoginOnUnsuccessfulLogin() {
        when(bean.accountSession.login(anyString())).thenReturn(false);
        Assert.assertEquals(bean.navigateLogin(), bean.login());
    }
}
