/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.pagebeans;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Borys
 */
public class MenuPageBeanTest {
    
    private final MenuPageBean bean;
    
    public MenuPageBeanTest() {
        bean = new MenuPageBean();
    }
    
    @Test
    public void shouldReturnCorrectNavigationStrings() {
        Assert.assertEquals("registerPage", bean.navigateRegister());
        Assert.assertEquals("booksPage", bean.navigateAllBooksList());
        Assert.assertEquals("borrowPage", bean.navigateBorrowBook());
        Assert.assertEquals("returnPage", bean.navigateReturnBook());
        Assert.assertEquals("historyPage", bean.navigateBorrowHistory());
    }
}
