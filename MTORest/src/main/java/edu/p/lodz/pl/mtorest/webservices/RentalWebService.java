/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.webservices;

import edu.p.lodz.pl.mtorest.entities.Account;
import edu.p.lodz.pl.mtorest.entities.Rental;
import edu.p.lodz.pl.mtorest.mob.dao.RentalFacadeLocal;
import edu.p.lodz.pl.mtorest.mok.dao.AccountFacadeMOKLocal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.transaction.TransactionRolledbackException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Tomasz
 */
@Path("/rental")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RentalWebService {

    @Inject
    @SuppressWarnings("unused")
    private RentalFacadeLocal rentalFacade;
    @Inject
    @SuppressWarnings("unused")
    private AccountFacadeMOKLocal accountFacade;
    
    static Logger loger = Logger.getGlobal();

    @POST
    public Response createRental(@Valid Rental rentalToCreate) {
        Integer id = null;
        try {
            id = rentalFacade.create(rentalToCreate);
        } catch (ConstraintViolationException e) {
            loger.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations().forEach(err -> loger.log(Level.SEVERE, err.toString()));
        } catch (TransactionRolledbackException ex) {
           // throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }
        URI location;
        try {
            location = new URI("rentals/" + id);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return Response.created(location).build();
    }
    
    @GET
    @Path("/history/{accountId}")
    public Response findHistoryByUser(@PathParam("accountId") int accountId) {
        List<Rental> userHistoryRentals = null;
        try {
            Account account = accountFacade.find(accountId);
             userHistoryRentals = rentalFacade.findHistoryByUser(account);
        } catch (ConstraintViolationException e) {
            loger.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations().forEach(err -> loger.log(Level.SEVERE, err.toString()));
        } catch (TransactionRolledbackException ex) {
            //throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }
        return Response.ok(userHistoryRentals).build();
    }

    @GET
    @Path("{accountId}")
    public Response findByUser(@PathParam("accountId") int accountId) {
        List<Rental> userRentals = null;
        
        try {
            Account account = accountFacade.find(accountId);
            userRentals = rentalFacade.findByUser(account);
        } catch (ConstraintViolationException e) {
            loger.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations().forEach(err -> loger.log(Level.SEVERE, err.toString()));
        } catch (TransactionRolledbackException ex) {
            //throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }
        return Response.ok(userRentals).build();
    }
    
    @PUT
    @Path("{edit}")
    public Response edit(@Valid Rental rental) {
        boolean hasRental = false;
        try {
            hasRental = rentalFacade.find(rental.getIdRental()) != null;
        } catch (TransactionRolledbackException ex) {
            ///to do
        }
        if (hasRental) {
            try {
            rentalFacade.edit(rental);
        } catch (TransactionRolledbackException ex) {
            //throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}