/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.mob.pagebeans;

import edu.p.lodz.pl.mto.entities.Book;
import edu.p.lodz.pl.mto.enums.MessageLevel;
import edu.p.lodz.pl.mto.interceptors.binding.MessagingApplicationExceptionHandler;
import edu.p.lodz.pl.mto.web.mob.MOBSession;
import edu.p.lodz.pl.mto.web.mok.AccountSession;
import java.util.List;
import java.util.stream.Collectors;
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
@Named("borrowBookPageBean")
@MessagingApplicationExceptionHandler
public class BorrowBookPageBean {
    
    @Inject
    private MOBSession mobSession;
    @Inject
    private AccountSession accountSession;

    private List<Book> books;
    private DataModel<Book> bookDataModel;
  
    
    public DataModel<Book> getBookDataModel() { return bookDataModel; }

    @PostConstruct
    private void initModel() {
        books = mobSession.getAllBooks();
        
        List<Book> listOutput =
    books.stream()
               .filter(book -> book.getRentalList()==null)  
               .collect(Collectors.toList());
        bookDataModel = new ListDataModel<>(listOutput);
    }
    
      public String borrowBook()
    {
        mobSession.borrowBook(bookDataModel.getRowData(), accountSession.showCurrentUser());
        
        mobSession.showMessage("SUCCESS", MessageLevel.INFO);
        return "homePage";   
    }
}
