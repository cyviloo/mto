/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.ejb.mob.endpoints;

import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.entities.Book;
import edu.p.lodz.pl.mto.entities.Rental;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tomasz
 */
@Local
public interface MOBEndpointLocal {
    
     public List<Book> getAllBooks();

    public void borrowBook(Book book, String login);

    public List<Rental> getRentalsByUser();

    public void returnBook(Rental rental);

    public List<Rental> getHistoryRentalsByUser();

    public Account fetchAccountFromSession();
    
}
