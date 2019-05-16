/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.utils;

import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.entities.Book;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Tomasz
 */
@Singleton
public class BookService {
    private Client client;
    private WebTarget target;

    @PostConstruct
    protected void init() {
        client = ClientBuilder.newClient();
        target = client.target(
                "http://localhost:18344/MTORest/book")
                ;
    }

    public Book find(String id) {
        return target.path(id).request(MediaType.APPLICATION_JSON).get(Book.class);
    }
    
     public List<Book> getAllBooks(){
         List<Book> allBooks = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Book>>() {
            });
         return allBooks;
     }
} 
