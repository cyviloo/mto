/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.pagebeans;

import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.web.mok.AccountSession;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Borys
 */
public class HeaderPageBeanTest {
    
    private final HeaderPageBean headerPageBean;
    private static final String testUser = "TEST-USER";
    
    public HeaderPageBeanTest() {
        headerPageBean = new HeaderPageBean();
        headerPageBean.accountSession = mock(AccountSession.class);
    }
    
    @Before
    public void setUp() {
        when(headerPageBean.accountSession.showCurrentUser())
                .thenReturn(testUser);
    }

    @Test
    public void shouldReturnCurrentUser() {
        Assert.assertEquals(testUser, headerPageBean.showCurrentUser());
    }
    
    @Test
    public void shouldReturnCorrectLoginNavigationString() {
        Assert.assertEquals("loginPage", headerPageBean.navigateLogin());
    }
    
    @Test (expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionOnTryingToLogoutWhenNoSession() {
        headerPageBean.logout();
    }
}
