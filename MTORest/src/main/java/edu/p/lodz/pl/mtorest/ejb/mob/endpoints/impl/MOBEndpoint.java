/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.ejb.mob.endpoints.impl;

import edu.p.lodz.pl.mtorest.ejb.mob.endpoints.MOBEndpointLocal;
import edu.p.lodz.pl.mtorest.entities.Account;
import edu.p.lodz.pl.mtorest.entities.Book;
import edu.p.lodz.pl.mtorest.entities.Rental;
import edu.p.lodz.pl.mtorest.enums.MessageLevel;
import edu.p.lodz.pl.mtorest.exceptions.MessagingApplicationException;
import edu.p.lodz.pl.mtorest.exceptions.ValidationException;
import edu.p.lodz.pl.mtorest.mob.dao.BookFacadeLocal;
import edu.p.lodz.pl.mtorest.mob.dao.RentalFacadeLocal;
import edu.p.lodz.pl.mtorest.mok.dao.AccountFacadeMOKLocal;
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
import javax.transaction.TransactionRolledbackException;
import javax.validation.ConstraintViolationException;

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

    static Logger loger = Logger.getGlobal();

    long transactionID;

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
    public Integer borrowBook(Book book, String login) throws ValidationException {

        Account acc = fetchAccountFromLogin(login);

        Book bookToBorrow;
        try {
            bookToBorrow = bookFacade.find(book.getIdBook());
        } catch (TransactionRolledbackException ex) {
            throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }

        if (bookToBorrow == null) {
            throw new ValidationException("Book not exist", MessageLevel.ERROR);
        }
        if (bookToBorrow.getRentalList()!=null) {
            throw new ValidationException("Book borrowed", MessageLevel.ERROR);
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
        return rentalToCreate.getIdRental();
    }

    @Override
    public List<Rental> getRentalsByUser(String login) {

        Account acc = fetchAccountFromLogin(login);
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
        boolean hasRental = false;
        try {
            hasRental = rentalFacade.find(rental.getIdRental()) != null;
        } catch (TransactionRolledbackException ex) {
            throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }
        if (hasRental) {
            try {
                rental.setEndDate(new Date());
                rental.setActive(false);
                rentalFacade.edit(rental);
            } catch (ConstraintViolationException e) {
                loger.log(Level.SEVERE, "Exception: ");
                e.getConstraintViolations().forEach(err -> loger.log(Level.SEVERE, err.toString()));
            } catch (TransactionRolledbackException ex) {
                throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
            }
        } else {
            throw new ValidationException("Book not rented", MessageLevel.ERROR);
        }
    }

    @Override
    public List<Rental> getHistoryRentalsByUser(String login) {
        Account acc = fetchAccountFromLogin(login);
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
    public Account fetchAccountFromLogin(String login) {
        Account acc;
        try {
            acc = accountFacade.findByLogin(login);
        } catch (TransactionRolledbackException ex) {
            throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }

        if (acc == null) {
            throw new ValidationException("Account not exist", MessageLevel.ERROR);
        }
        return acc;
    }

    @Override
    public Book find(int id) {

        Book book = null;
        try {
            book = bookFacade.find(id);
        } catch (TransactionRolledbackException ex) {
            Logger.getLogger(MOBEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        return book;
    }
}
