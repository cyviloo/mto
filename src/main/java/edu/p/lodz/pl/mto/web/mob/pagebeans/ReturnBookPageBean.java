/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.mob.pagebeans;

import edu.p.lodz.pl.mto.entities.Book;
import edu.p.lodz.pl.mto.entities.Rental;
import edu.p.lodz.pl.mto.enums.MessageLevel;
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
@Named("returnBookPageBean")
@MessagingApplicationExceptionHandler
public class ReturnBookPageBean {
     @Inject
    private MOBSession mobSession;

    private List<Rental> rentals;
    private DataModel<Rental> rentalDataModel;
  
    
    public DataModel<Rental> getRentalDataModel() { return rentalDataModel; }

    @PostConstruct
    private void initModel() {
        rentals = mobSession.getRentalsByUser();
        rentalDataModel = new ListDataModel<>(rentals);
    }
    
    public String returnBook()
    {
        mobSession.returnBook(rentalDataModel.getRowData());
        
        mobSession.showMessage("SUCCESS", MessageLevel.INFO);
        return "homePage";   
    }
}
