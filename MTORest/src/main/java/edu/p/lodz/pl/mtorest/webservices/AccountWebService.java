/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.webservices;

import edu.p.lodz.pl.mtorest.ejb.mok.endpoints.MOKEndpointLocal;
import edu.p.lodz.pl.mtorest.entities.Account;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Tomasz
 */
@RequestScoped
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountWebService implements Serializable {

    @EJB
    private MOKEndpointLocal accountMok;

    static Logger loger = Logger.getGlobal();

    @GET
    @Path("{login}")
    public Response getAccount(@PathParam("login") String login) {
        Account account = null;
        account = accountMok.getAccountByLogin(login); //throw new MessagingApplicationException(MessageLevel.FATAL, "Transaction rollbacked", ex);

        if (account != null) {
            return Response.ok(account).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createAccount(Account account) {
        String newLogin = null;
        try { 
            newLogin = accountMok.registerAccount(account);
        }catch (ConstraintViolationException e) {
            loger.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations().forEach(err -> loger.log(Level.SEVERE, err.toString()));
        }
        URI location;
        try {
            location = new URI("accounts/" + newLogin);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return Response.created(location).build();
    }
}  
