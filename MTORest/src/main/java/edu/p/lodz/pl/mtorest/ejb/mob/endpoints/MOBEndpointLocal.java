/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.ejb.mob.endpoints;

import edu.p.lodz.pl.mtorest.entities.Account;
import edu.p.lodz.pl.mtorest.entities.Book;
import edu.p.lodz.pl.mtorest.entities.Rental;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tomasz
 */
@Local
public interface MOBEndpointLocal {
    
     public List<Book> getAllBooks();

    public Integer borrowBook(Book book, String login);

    public List<Rental> getRentalsByUser(String login);

    public void returnBook(Rental rental);

    public List<Rental> getHistoryRentalsByUser(String login);

    public Account fetchAccountFromLogin(String login);

    public Book find(int id);
    
}
