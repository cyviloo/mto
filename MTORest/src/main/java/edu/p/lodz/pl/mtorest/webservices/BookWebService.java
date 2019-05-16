/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.webservices;

import edu.p.lodz.pl.mtorest.ejb.mob.endpoints.MOBEndpointLocal;
import edu.p.lodz.pl.mtorest.ejb.mok.endpoints.MOKEndpointLocal;
import edu.p.lodz.pl.mtorest.entities.Book;
import edu.p.lodz.pl.mtorest.mob.dao.BookFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookWebService {

    @Inject
    @SuppressWarnings("unused")
    private BookFacadeLocal bookFacade;
    
    
    @EJB
    private MOKEndpointLocal accountMok;
    @EJB
    private MOBEndpointLocal mOBEndpoint;
    
    static Logger loger = Logger.getGlobal();

    @GET
    public Response getAllBooks() {
        List<Book> allBooks = new ArrayList<Book>();
        allBooks = mOBEndpoint.getAllBooks(); 
        return Response.ok(allBooks).build();
    }

    @GET
    @Path("{id}")
    public Response find(@PathParam("id") int id) {
        Book bookToBorrow = null;
       
        bookToBorrow = mOBEndpoint.find(id); 
        if (bookToBorrow != null) {
            return Response.ok(bookToBorrow).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
