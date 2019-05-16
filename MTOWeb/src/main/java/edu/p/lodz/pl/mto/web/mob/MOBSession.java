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
import java.io.Serializable;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Tomasz
 */
@SessionScoped
@Named("MobSession")
public class MOBSession implements Serializable {

//    @EJB
//    MOBEndpointLocal mOBEndpoint;
    @EJB
    RentalService rentalService;
    @EJB
    BookService bookService;

    //@Resource(name = "messagesBean")
    @Inject
    MessagesBean messagesBean;

    @PermitAll
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PermitAll
    public void showMessage(String message, MessageLevel messageLevel) {
        messagesBean.showMessage(message, messageLevel);
    }

    public void borrowBook(Book book, String login) {
        rentalService.borrowBook(book);
    }

    public List<Rental> getRentalsByUser() {
        return rentalService.findByUser();
    }

    public void returnBook(Rental rental) {
        rentalService.returnBook(rental);
    }

    public List<Rental> getHistoryRentalsByUser() {
       return rentalService.findHistoryByUser();
    }

}
