/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.entities;

import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Borys
 */
public class AccountTest {
    
    private Account account, account2;
    
    public AccountTest() {
        account = new Account();
        account2 = new Account();
    }
    
    @Before
    public void setUp() {
        account.setIdAccount(111);
        account.setLogin("TEST-LOGIN");
        account.setName("TEST-NAME");
        account.setSurname("TEST-SURNAME");
        account.setBirthDate(new Date(1980, 1, 2));
        account2.setIdAccount(Integer.MAX_VALUE);
        account2.setLogin("TEST-LOGIN2");
        account2.setName("TEST-NAME2");
        account2.setSurname("TEST-SURNAME2");
        account2.setBirthDate(new Date(1999, 11, 11));
    }

    @Test
    public void shouldGetCorrectAccountData() {
        Assert.assertEquals(new Integer(111), account.getIdAccount());
        Assert.assertEquals("TEST-LOGIN", account.getLogin());
        Assert.assertEquals("TEST-NAME", account.getName());
        Assert.assertEquals("TEST-SURNAME", account.getSurname());
        Assert.assertEquals(new Date(1980, 1, 2), account.getBirthDate());
    }
    
    @Test
    public void shouldNotEqualOnDifferentId() {
        Assert.assertNotEquals(account, account2);
    }
    
    @Test
    public void shouldEqualOnSameId() {
        account2.setIdAccount(111);
        Assert.assertEquals(account, account2);
    }
    
    @Test
    public void shouldReturnCorrectToString() {
        Assert.assertEquals(
                "edu.p.lodz.pl.mtorest.entities.Account[ id=111 ]", account.toString());
    }
}
