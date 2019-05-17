/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.mob;

import edu.p.lodz.pl.mto.beans.MessagesBean;
import edu.p.lodz.pl.mto.entities.Book;
import edu.p.lodz.pl.mto.entities.Rental;
import edu.p.lodz.pl.mto.enums.MessageLevel;
import edu.p.lodz.pl.mto.utils.BookService;
import edu.p.lodz.pl.mto.utils.RentalService;
import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Borys
 */
public class MOBSessionTest {
    
    private class TestException extends Exception {}
    
    private final MOBSession session;
    private final Book book1, book2;
    private final Rental rental1;
    
    public MOBSessionTest() {
        session = new MOBSession();
        session.bookService = mock(BookService.class);
        session.rentalService = mock(RentalService.class);
        session.messagesBean = mock(MessagesBean.class);
        
        book1 = new Book("TEST-TITLE", "TEST-AUTHOR", 1999);
        book1.setIdBook(11);
        book2 = new Book("TEST-TITLE", "TEST-AUTHOR", 1999);
        book2.setIdBook(22);
        
        rental1 = new Rental();
        rental1.setIdRental(1000);
    }
    
    @Before
    public void setUp() {
        when(session.bookService.getAllBooks()).
                thenReturn(Arrays.asList(book1, book2));
        doThrow(TestException.class).when(session.messagesBean)
                .showMessage(anyString(), any(MessageLevel.class));
        doThrow(TestException.class).when(session.rentalService)
                .borrowBook(any(Book.class));
        doThrow(TestException.class).when(session.rentalService)
                .returnBook(any(Rental.class));
        when(session.rentalService.findByUser())
                .thenReturn(Arrays.asList(rental1));
        when(session.rentalService.findHistoryByUser())
                .thenReturn(Arrays.asList(rental1));
    }

    @Test
    public void shouldGetAllBooks() {
        List<Book> books = session.getAllBooks();
        Assert.assertEquals(2, books.size());
        Assert.assertEquals(book1, books.get(0));
        Assert.assertEquals(book2, books.get(1));
    }
    
    @Test (expected = TestException.class)
    public void shouldCallMessagesBeanShowMessageOnShowMessage() {
        session.showMessage("TEST-MESSAGE", MessageLevel.FATAL);
    }
    
    @Test (expected = TestException.class)
    public void shouldCallMOBEndpointBorrowBookOnBorrowBook() {
        session.borrowBook(book1, "TEST-LOGIN");
    }
    
    @Test
    public void shouldReturnRentalsOfUser() {
        List<Rental> result = session.getRentalsByUser();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(rental1, result.get(0));
    }
    
    @Test (expected = TestException.class)
    public void shouldCallMOBEndpointReturnBookOnReturnBook() {
        session.returnBook(rental1);
    }
    
    @Test
    public void shouldReturnHistoryOfUsersRentals() {
        List<Rental> result = session.getHistoryRentalsByUser();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(rental1, result.get(0));
    }
}
