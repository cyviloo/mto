/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.webservices;

import edu.p.lodz.pl.mtorest.entities.Account;
import edu.p.lodz.pl.mtorest.mok.dao.AccountFacadeMOKLocal;
import edu.p.lodz.pl.mtorest.mok.dao.impl.AccountFacadeMOK;
import static edu.p.lodz.pl.mtorest.webservices.RentalWebService.loger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.transaction.TransactionRolledbackException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountWebService {

    @Inject
    @SuppressWarnings("unused")
    private AccountFacadeMOKLocal accountFacade;

    static Logger loger = Logger.getGlobal();

    @GET
    @Path("{login}")
    public Response getAccount(@PathParam("login") String login) {
        Account account = null;
        try {
            account = accountFacade.findByLogin(login);
        } catch (TransactionRolledbackException ex) {
            //throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }

//        if (account != null) {
            return Response.ok(account).build();
//        }
//        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createAccount(@Valid Account account) {
        String newLogin = null;
        try { 
            newLogin = accountFacade.create(account);
        } catch (ConstraintViolationException e) {
            loger.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations().forEach(err -> loger.log(Level.SEVERE, err.toString()));
        } catch (TransactionRolledbackException ex) {
           // throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);
        }
        URI location;
        try {
            location = new URI("accounts/" + newLogin);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return Response.created(location).build();
    }
}   //-X POST -d '{"birthDate":"2019-03-13T00:00:00+01:00","idAccount":3,"login":"test","name":"tomasz","surname":"kozlowicz"}'
