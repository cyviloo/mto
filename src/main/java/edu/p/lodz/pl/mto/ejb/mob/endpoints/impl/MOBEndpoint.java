/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.ejb.mob.endpoints.impl;

import edu.p.lodz.pl.mto.ejb.mob.endpoints.MOBEndpointLocal;
import edu.p.lodz.pl.mto.ejb.mok.endpoints.MOKEndpointLocal;
import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.entities.Book;
import edu.p.lodz.pl.mto.entities.Rental;
import edu.p.lodz.pl.mto.enums.MessageLevel;
import edu.p.lodz.pl.mto.exceptions.MessagingApplicationException;
import edu.p.lodz.pl.mto.exceptions.ValidationException;
import edu.p.lodz.pl.mto.mob.dao.BookFacadeLocal;
import edu.p.lodz.pl.mto.mob.dao.RentalFacadeLocal;
import edu.p.lodz.pl.mto.mok.dao.AccountFacadeMOKLocal;
import edu.p.lodz.pl.mto.web.mok.AccountSession;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
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
import javax.inject.Inject;
import javax.transaction.TransactionRolledbackException;
import javax.validation.ConstraintViolationException;
import static org.eclipse.persistence.sessions.SessionProfiler.Logging;

/**
 *
 * @author Tomasz
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MOBEndpoint implements MOBEndpointLocal, SessionSynchronization {

    @Resource
    SessionContext sessionContext;
    @EJB
    BookFacadeLocal bookFacade;
    @EJB
    AccountFacadeMOKLocal accountFacade;
    @EJB
    RentalFacadeLocal rentalFacade;
    @Inject
    AccountSession accountSession;

    protected static final Logger loger = Logger.getGlobal();

    private long transactionID;

    @Override
    @PermitAll
    public List<Book> getAllBooks() {
        try {
            return bookFacade.getAllBooks();
        } catch (TransactionRolledbackException ex) {
            throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }
    }

    @Override
    public void afterBegin() throws EJBException, RemoteException {
        transactionID = System.currentTimeMillis();
        loger.log(Level.SEVERE, "Transakcja o ID: " + transactionID + " zostala rozpoczeta w " + this.getClass().getName() + " tożsamość " + sessionContext.getCallerPrincipal().getName());
    }

    @Override
    public void beforeCompletion() throws EJBException, RemoteException {
        loger.log(Level.SEVERE, "Transakcja o ID: " + transactionID + " przed zakonczeniem w " + this.getClass().getName() + " tożsamość " + sessionContext.getCallerPrincipal().getName());
    }

    @Override
    public void afterCompletion(boolean committed) throws EJBException, RemoteException {
        loger.log(Level.SEVERE, "Transakcja o ID: " + transactionID + " zostala zakonczona przez: " + (committed ? "zatwierdzenie" : "wycofanie") + " tożsamość " + sessionContext.getCallerPrincipal().getName());
    }

    @Override
    public void borrowBook(Book book, String login) throws ValidationException {

        Account acc = fetchAccountFromSession();

        Book bookToBorrow;
        try {
            bookToBorrow = bookFacade.find(book.getIdBook());
        } catch (TransactionRolledbackException ex) {
            throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }

        if (bookToBorrow == null) {
            throw new ValidationException("Book not exist", MessageLevel.ERROR);
        }
        Rental rentalToCreate = new Rental();
        rentalToCreate.setAccount(acc);
        rentalToCreate.setBook(book);
        rentalToCreate.setActive(true);
        rentalToCreate.setStartDate(new Date());

        try {
            rentalFacade.create(rentalToCreate);
        } catch (ConstraintViolationException e) {
            loger.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations().forEach(err -> loger.log(Level.SEVERE, err.toString()));
        } catch (TransactionRolledbackException ex) {
            throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }

    }

    @Override
    public List<Rental> getRentalsByUser() {

        Account acc = fetchAccountFromSession();
        List<Rental> userRentals = null;
        try {
            userRentals = rentalFacade.findByUser(acc);
        } catch (ConstraintViolationException e) {
            loger.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations().forEach(err -> loger.log(Level.SEVERE, err.toString()));
        } catch (TransactionRolledbackException ex) {
            throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }
        return userRentals;
    }

    @Override
    public void returnBook(Rental rental) {
        rental.setEndDate(new Date());
        rental.setActive(false);
        try {
            rentalFacade.edit(rental);
        } catch (TransactionRolledbackException ex) {
            throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }
    }

    @Override
    public List<Rental> getHistoryRentalsByUser() {
        Account acc = fetchAccountFromSession();
        List<Rental> userHistoryRentals = null;
        try {
            userHistoryRentals = rentalFacade.findHistoryByUser(acc);
        } catch (ConstraintViolationException e) {
            loger.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations().forEach(err -> loger.log(Level.SEVERE, err.toString()));
        } catch (TransactionRolledbackException ex) {
            throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }
        return userHistoryRentals;
    }
    
    @Override
    public Account fetchAccountFromSession(){
        Account acc;
        try {
            acc = accountFacade.findByLogin(accountSession.showCurrentUser());
        } catch (TransactionRolledbackException ex) {
            throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }

        if (acc == null) {
            throw new ValidationException("Account not exist", MessageLevel.ERROR);
        }
        return acc;
    }
}
