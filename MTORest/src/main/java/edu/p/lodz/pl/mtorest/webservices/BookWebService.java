/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.webservices;

import edu.p.lodz.pl.mtorest.entities.Account;
import edu.p.lodz.pl.mtorest.entities.Book;
import edu.p.lodz.pl.mtorest.mob.dao.BookFacadeLocal;
import edu.p.lodz.pl.mtorest.mok.dao.impl.AccountFacadeMOK;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.transaction.TransactionRolledbackException;
import javax.validation.Valid;
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
@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookWebService {

    @Inject
    @SuppressWarnings("unused")
    private BookFacadeLocal bookFacade;
    
    static Logger loger = Logger.getGlobal();

    @GET
    public Response getAllBooks() {
        List<Book> allBooks = new ArrayList<Book>();
        try {
            allBooks = bookFacade.getAllBooks();
        } catch (TransactionRolledbackException ex) {
            //dunno yet
        }
        return Response.ok(allBooks).build();
    }

    @GET
    @Path("{id}")
    public Response find(@PathParam("id") int id) {
        Book bookToBorrow = null;
       
        try {
            bookToBorrow = bookFacade.find(id);
        } catch (TransactionRolledbackException ex) {
            ///to do
        }
        if (bookToBorrow != null) {
            return Response.ok(bookToBorrow).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
