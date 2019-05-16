/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.utils;

import edu.p.lodz.pl.mto.entities.Account;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Tomasz
 */
@Singleton
public class AccountService {
    
    private Client client;
    private WebTarget target;

    @PostConstruct
    protected void init() {
        client = ClientBuilder.newClient();
        target = client.target(
                "http://localhost:18344/MTORest/account")
                ;
    }

    public Account getAccountByLogin(String login) {
        return target.path(login).request(MediaType.APPLICATION_JSON).get(Account.class);
    }

    public void registerAccount(Account account) {

    Response response = target.request().post(Entity.entity(account, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
        }
    }
}
