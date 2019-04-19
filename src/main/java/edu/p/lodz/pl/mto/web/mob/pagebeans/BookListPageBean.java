/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.mob.pagebeans;

import edu.p.lodz.pl.mto.entities.Book;
import edu.p.lodz.pl.mto.interceptors.binding.MessagingApplicationExceptionHandler;
import edu.p.lodz.pl.mto.web.mob.MOBSession;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Tomasz
 */
@RequestScoped
@MessagingApplicationExceptionHandler
@Named("bookListPageBean")
public class BookListPageBean {
    
    @Inject
    private MOBSession mobSession;

    private List<Book> books;
    private DataModel<Book> bookDataModel;
  
    
    public DataModel<Book> getBookDataModel() { return bookDataModel; }

    @PostConstruct
    private void initModel() {
        books = mobSession.getAllBooks();
        bookDataModel = new ListDataModel<>(books);
        //bookDataModel.getRowData().getRentalList().get(0).isActive()
        //navigateAllBooksList();
    }
    
}
