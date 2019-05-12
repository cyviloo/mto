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
public class RentalTest {
    
    private Rental rental, rental2;
    private Account account;
    private Book book;
    
    public RentalTest() {
        rental = new Rental();
        rental2 = new Rental();
        account = new Account();
        book = new Book("TEST-TITLE", "TEST-AUTHOR", 1999);
    }
    
    @Before
    public void setUp() {
        book.setIdBook(222);
        account.setIdAccount(111);
        rental.setAccount(account);
        rental.setActive(true);
        rental.setBook(book);
        rental.setIdRental(333);
        rental.setStartDate(new Date(2000, 1, 2));
        rental.setEndDate(new Date(2020, 1, 2));
        rental2.setAccount(account);
        rental2.setActive(true);
        rental2.setBook(book);
        rental2.setIdRental(334);
        rental2.setStartDate(new Date(2000, 1, 2));
        rental2.setEndDate(new Date(2020, 1, 2));
    }

    @Test
    public void shouldGetCorrectRentalData() {
        Assert.assertEquals(new Integer(333), rental.getIdRental());
        Assert.assertEquals(account, rental.getAccount());
        Assert.assertEquals(book, rental.getBook());
        Assert.assertEquals(new Date(2000, 1, 2), rental.getStartDate());
        Assert.assertEquals(new Date(2020, 1, 2), rental.getEndDate());
        Assert.assertTrue(rental.isActive());
    }
    
    @Test
    public void shouldNotBeActiveWhenDeactivating() {
        rental.setActive(false);
        Assert.assertFalse(rental.isActive());
    }
    
    @Test
    public void shouldNotEqualOnDifferentId() {
        Assert.assertNotEquals(rental, rental2);
    }
    
    @Test
    public void shouldEqualOnSameId() {
        rental2.setIdRental(rental.getIdRental());
        Assert.assertEquals(rental, rental2);
    }
    
    @Test
    public void shouldReturnCorrectToString() {
        Assert.assertEquals(rental.toString(),
                "edu.p.lodz.pl.mtorest.entities.Rental[ id=333 ]");
    }
    
    @Test
    public void shouldReturnZeroHashCodeOnNullId() {
        rental.setIdRental(null);
        Assert.assertEquals(0, rental.hashCode());
    }
    
    @Test
    public void shouldReturnIdHashCodeOnNotNullId() {
        Assert.assertEquals(rental.getIdRental().hashCode(), rental.hashCode());
    }
}
