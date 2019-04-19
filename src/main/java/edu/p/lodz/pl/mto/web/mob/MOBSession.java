/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.mob;

import edu.p.lodz.pl.mto.beans.MessagesBean;
import edu.p.lodz.pl.mto.ejb.mob.endpoints.MOBEndpointLocal;
import edu.p.lodz.pl.mto.entities.Book;
import edu.p.lodz.pl.mto.entities.Rental;
import edu.p.lodz.pl.mto.enums.MessageLevel;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Tomasz
 */
@SessionScoped
@Named("MobSession")
public class MOBSession implements Serializable {

    @EJB
    private MOBEndpointLocal mOBEndpoint;

    //@Resource(name = "messagesBean")
    @Inject
    private MessagesBean messagesBean;

    @PermitAll
    public List<Book> getAllBooks() {
        return mOBEndpoint.getAllBooks();
    }

    @PermitAll
    public void showMessage(String message, MessageLevel messageLevel) {
        messagesBean.showMessage(message, messageLevel);
    }

    public void borrowBook(Book book, String login) {
        mOBEndpoint.borrowBook(book, login);
    }

    public List<Rental> getRentalsByUser() {
        return mOBEndpoint.getRentalsByUser();
    }

    public void returnBook(Rental rental) {
        mOBEndpoint.returnBook(rental);
    }

    public List<Rental> getHistoryRentalsByUser() {
       return mOBEndpoint.getHistoryRentalsByUser();
    }

}
