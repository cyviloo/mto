/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.mob.dao;

import edu.p.lodz.pl.mtorest.entities.Account;
import edu.p.lodz.pl.mtorest.entities.Rental;
import java.util.List;
import javax.ejb.Local;
import javax.transaction.TransactionRolledbackException;

/**
 *
 * @author Tomasz
 */
@Local
public interface RentalFacadeLocal {

    public int create(Rental entity) throws TransactionRolledbackException;
    
    public Rental find(Object id) throws TransactionRolledbackException;

    public List<Rental> findByUser(Account account) throws TransactionRolledbackException;

    public void edit(Rental entity) throws TransactionRolledbackException;

    public List<Rental> findHistoryByUser(Account acc) throws TransactionRolledbackException;
    
}
