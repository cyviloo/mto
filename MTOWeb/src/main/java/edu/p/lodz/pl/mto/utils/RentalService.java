/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.entities.Rental;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.T;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Tomasz
 */
@Singleton
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class RentalService {

    private Client client;
    private WebTarget target;

    @PostConstruct
    protected void init() {
        client = ClientBuilder.newClient();
        target = client.target(
                "http://localhost:18344/MTORest/rental");
    }

    public void create(Rental rental) {

        Response response = target.request().post(Entity.entity(rental, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
        }
    }

    public List<Rental> findByUser(Account account) {
        Response dd = target.path(account.getIdAccount().toString()).
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

    public List<Rental> findHistoryByUser(Account account) {
       
        Response dd = target.path("history").path(account.getIdAccount().toString()).
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

    public void edit(Rental rental) {
        Entity en = Entity.json(rental);
        Response response = target.request().put(Entity.entity(rental, MediaType.APPLICATION_JSON));

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            System.out.println(response.getStatus() + " - " + response.getStatusInfo());
            System.out.println(response.readEntity(String.class));
        }
    }
}
