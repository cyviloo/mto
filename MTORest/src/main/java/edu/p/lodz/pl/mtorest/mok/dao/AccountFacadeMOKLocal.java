/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.mok.dao;

import edu.p.lodz.pl.mtorest.entities.Account;
import javax.ejb.Local;
import javax.transaction.TransactionRolledbackException;

/**
 *
 * @author Tomasz
 */
@Local
public interface AccountFacadeMOKLocal {

    String create(Account account) throws TransactionRolledbackException;

    Account findByLogin(String login) throws TransactionRolledbackException;
    
    Account find(Object id) throws TransactionRolledbackException;
}
