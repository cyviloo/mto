/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.mob.dao;

import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.entities.Rental;
import java.util.List;
import javax.ejb.Local;
import javax.transaction.TransactionRolledbackException;

/**
 *
 * @author Tomasz
 */
@Local
public interface RentalFacadeLocal {

    public void create(Rental entity) throws TransactionRolledbackException;

    //public List<Rental> findByUser(Integer idAccount)throws TransactionRolledbackException;

    public List<Rental> findByUser(Account account) throws TransactionRolledbackException;

    public void edit(Rental entity) throws TransactionRolledbackException;

    public List<Rental> findHistoryByUser(Account acc) throws TransactionRolledbackException;
    
    

    
}
