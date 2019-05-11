/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.mob.dao;

import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.entities.Book;
import java.util.List;
import javax.ejb.Local;
import javax.transaction.TransactionRolledbackException;

/**
 *
 * @author Tomasz
 */
@Local
public interface BookFacadeLocal {
    
    List<Book> getAllBooks() throws TransactionRolledbackException;

    public Book find(Object id) throws TransactionRolledbackException;;

}
