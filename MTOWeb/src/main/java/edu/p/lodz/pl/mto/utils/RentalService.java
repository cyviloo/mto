/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.entities.Book;
import edu.p.lodz.pl.mto.entities.Rental;
import edu.p.lodz.pl.mto.web.mok.AccountSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.T;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;


/**
 *
 * @author Tomasz
 */
@Singleton
public class RentalService {

    private Client client;
    private WebTarget target;
    
    @Inject
    AccountSession accountSession;

    @PostConstruct
    protected void init() {
        client = ClientBuilder.newClient();
        target = client.target(
                "http://localhost:18344/MTORest/rental");
    }

    public void borrowBook(Book book) {
        Rental workRental = new Rental();
        workRental.setBook(book);
        workRental.setAccount(new Account(accountSession.showCurrentUser()));
        Response response = target.request().post(Entity.entity(workRental, MediaType.APPLICATION_JSON));
//        MultivaluedHashMap myHeaders = new MultivaluedHashMap<>();
//myHeaders.add("book",book);
//myHeaders.add("login", accountSession.showCurrentUser());
//Invocation.Builder builder = target.request();
////Response response = builder.post(Entity.json(myHeaders));
// final Client client3 = ClientBuilder.newBuilder()
//        .register(MultiPartFeature.class)
//        .build();
//final ClientConfig clientConfig = new ClientConfig();
//clientConfig.register(MultiPartFeature.class).register(JacksonFeature.class);
//Client client2 = ClientBuilder.newClient(clientConfig);
//
//
//
//WebTarget target2 = client3.target(
//                "http://localhost:18344/MTORest/rental");
//MultiPart multipartEntity = new FormDataMultiPart()
//            .field("book", "hahaha", MediaType.APPLICATION_JSON_TYPE);
//
//            
//MediaType mt = MediaType.MULTIPART_FORM_DATA_TYPE;
//FormDataMultiPart mp = new FormDataMultiPart();
//  //mp.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("book").build(), Entity.entity(book, MediaType.APPLICATION_JSON),mt));
//    mp.bodyPart(new FormDataBodyPart("book", Entity.entity(book, MediaType.APPLICATION_JSON),mt));
//
////  mp.bodyPart(new FormDataBodyPart(FormDataContentDisposition.name("login").build(), 
////          Entity.entity(accountSession.showCurrentUser(), MediaType.APPLICATION_JSON),mt));
////Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.form(myHeaders));
////        Response response = target2.request(mt).post(Entity.entity(mp, MediaType
////    .MULTIPART_FORM_DATA_TYPE));
//        Response response = target2.request().post(
//            Entity.entity(multipartEntity, multipartEntity.getMediaType()));
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
        }
    }

    public List<Rental> findByUser() {
        Response dd = target.path(accountSession.showCurrentUser()).
                request(MediaType.APPLICATION_JSON).get();
        List<Rental> allRentals = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = dd.readEntity(String.class);
            allRentals = mapper.readValue(json, new TypeReference<List<Rental>>() {
            });

        } catch (IOException ex) {
            Logger.getLogger(RentalService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allRentals;
    }

    public List<Rental> findHistoryByUser() {
       
        Response dd = target.path("history").path(accountSession.showCurrentUser()).
                request(MediaType.APPLICATION_JSON).get();
        List<Rental> allRentals = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = dd.readEntity(String.class);
            allRentals = mapper.readValue(json, new TypeReference<List<Rental>>() {
            });

        } catch (IOException ex) {
            Logger.getLogger(RentalService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allRentals;
    }

    public void returnBook(Rental rental) {
        Entity en = Entity.json(rental);
        Response response = target.request().put(Entity.entity(rental, MediaType.APPLICATION_JSON));

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            System.out.println(response.getStatus() + " - " + response.getStatusInfo());
            System.out.println(response.readEntity(String.class));
        }
    }
}
