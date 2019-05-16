/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.webservices;

import edu.p.lodz.pl.mtorest.ejb.mob.endpoints.MOBEndpointLocal;
import edu.p.lodz.pl.mtorest.ejb.mok.endpoints.MOKEndpointLocal;
import edu.p.lodz.pl.mtorest.entities.Rental;
import edu.p.lodz.pl.mtorest.mob.dao.RentalFacadeLocal;
import edu.p.lodz.pl.mtorest.mok.dao.AccountFacadeMOKLocal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
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
@RequestScoped
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
    @EJB
    private MOKEndpointLocal accountMok;
    @EJB
    private MOBEndpointLocal mOBEndpoint;

    static Logger loger = Logger.getGlobal();

    @POST
    public Response createRental(Rental rental) {

        Integer id = null;
        try {

            id = mOBEndpoint.borrowBook(rental.getBook(), rental.getAccount().getLogin());
        } catch (ConstraintViolationException e) {
            loger.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations().forEach(err -> loger.log(Level.SEVERE, err.toString()));
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
    @Path("/history/{login}")
    public Response findHistoryByUser(@PathParam("login") String login) {
        List<Rental> userHistoryRentals = null;
        try {
            userHistoryRentals = mOBEndpoint.getHistoryRentalsByUser(login);
        } catch (ConstraintViolationException e) {
            loger.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations().forEach(err -> loger.log(Level.SEVERE, err.toString()));
        }
        return Response.ok(userHistoryRentals).build();
    }

    @GET
    @Path("{login}")
    public Response findByUser(@PathParam("login") String login) {
        List<Rental> userRentals = null;

        try {
            userRentals = mOBEndpoint.getRentalsByUser(login);
        } catch (ConstraintViolationException e) {
            loger.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations().forEach(err -> loger.log(Level.SEVERE, err.toString()));
        }
        return Response.ok(userRentals).build();
    }

    @PUT
    public Response edit(Rental rental) {
        mOBEndpoint.returnBook(rental);

        return Response.noContent().build();
    }
}
