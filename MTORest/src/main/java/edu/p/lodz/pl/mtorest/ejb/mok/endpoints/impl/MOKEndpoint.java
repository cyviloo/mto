/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.ejb.mok.endpoints.impl;

import edu.p.lodz.pl.mtorest.ejb.mok.endpoints.MOKEndpointLocal;
import edu.p.lodz.pl.mtorest.entities.Account;
import edu.p.lodz.pl.mtorest.enums.MessageLevel;
import edu.p.lodz.pl.mtorest.exceptions.MessagingApplicationException;
import edu.p.lodz.pl.mtorest.exceptions.ValidationException;
import edu.p.lodz.pl.mtorest.mok.dao.AccountFacadeMOKLocal;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.transaction.TransactionRolledbackException;

/**
 *
 * @author Tomasz
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MOKEndpoint implements MOKEndpointLocal, SessionSynchronization {
    
    @EJB
    AccountFacadeMOKLocal accountFacade;
    
    @Resource
    SessionContext sessionContext;
   
    static final Logger loger = Logger.getGlobal();
    
    long transactionID;
    
    
    @Override
    @PermitAll
    public String registerAccount(Account account)
    {
       String newLogin;
        try
        {
            if (accountFacade.findByLogin(account.getLogin()) != null)
            {
                throw new ValidationException("Login already taken", MessageLevel.ERROR);
            }
        } catch (TransactionRolledbackException ex)
        {
            throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }      

        Account accountRegistry = new Account();
        accountRegistry.setLogin(account.getLogin());

        accountRegistry.setName(account.getName());
        accountRegistry.setSurname(account.getSurname());
        accountRegistry.setBirthDate(account.getBirthDate());

        try
        {
            newLogin = accountFacade.create(accountRegistry);
        } catch (TransactionRolledbackException ex)
        {
            throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }
        return newLogin;
    }

   
    @Override
    @PermitAll
    public Account getAccountByLogin(String login)
    {
        Account account;
        try
        {
            account = accountFacade.findByLogin(login);
        } catch (TransactionRolledbackException ex)
        {
            throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }

        if (account == null)
        {
            throw new ValidationException("Provided login is incorrect.", MessageLevel.ERROR);
        } else return account;
    }


    @Override
    @PermitAll
    public String showCurrentUser()
    {
        return sessionContext.getCallerPrincipal().getName();
    }

    @Override
    public void afterBegin() throws EJBException, RemoteException {
        transactionID = System.currentTimeMillis();
        loger.log(Level.SEVERE, "Transakcja o ID: " + transactionID + " zostala rozpoczeta w "+this.getClass().getName()+" tożsamość "+sessionContext.getCallerPrincipal().getName());
    }

   
    @Override
    public void beforeCompletion() throws EJBException, RemoteException {
        loger.log(Level.SEVERE, "Transakcja o ID: " + transactionID + " przed zakonczeniem w "+this.getClass().getName()+" tożsamość "+sessionContext.getCallerPrincipal().getName());
    }

   
    @Override
    public void afterCompletion(boolean committed) throws EJBException, RemoteException {
        loger.log(Level.SEVERE, "Transakcja o ID: " + transactionID + " zostala zakonczona przez: " + (committed?"zatwierdzenie":"wycofanie")+" tożsamość "+sessionContext.getCallerPrincipal().getName());
    }
    
}