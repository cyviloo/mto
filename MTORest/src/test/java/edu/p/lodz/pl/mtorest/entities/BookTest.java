/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.entities;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Borys
 */
public class BookTest {
    
    Book book, book2;
    
    public BookTest() {
        book = new Book("TEST-TITLE", "TEST-AUTHOR", 2005);
        book.setIdBook(666);
        
        book2 = new Book("TEST-TITLE2", "TEST-AUTHOR2", 2005);
    }

    @Test
    public void shouldGetCorrectBookData() {
        Assert.assertEquals("TEST-TITLE", book.getTitle());
        Assert.assertEquals("TEST-AUTHOR", book.getAuthor());
        Assert.assertEquals(2005, book.getYear());
        Assert.assertEquals(new Integer(666), book.getIdBook());
    }
    
    @Test
    public void shouldNotBeEqualIfDifferentId() {
        book2.setIdBook(667);
        Assert.assertNotEquals(book, book2);
    }
    
    @Test
    public void shouldBeEqualIfSameId() {
        book2.setIdBook(book.getIdBook());
        Assert.assertEquals(book, book2);
    }
    
    @Test
    public void shouldReturnCorrectToString() {
        Assert.assertEquals(
                "edu.p.lodz.pl.mtorest.entities.Book[ id=666 ]", book.toString());
    }
    
    @Test
    public void shouldReturnZeroHashCodeOnNullId() {
        book.setIdBook(null);
        Assert.assertEquals(0, book.hashCode());
    }
    
    @Test
    public void shouldReturnIdHashCodeOnNotNullId() {
        Assert.assertEquals(book.getIdBook().hashCode(), book.hashCode());
    }
}
