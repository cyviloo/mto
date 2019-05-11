/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.mok;

import edu.p.lodz.pl.mto.beans.MessagesBean;
import edu.p.lodz.pl.mto.ejb.mok.endpoints.MOKEndpointLocal;
import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.enums.MessageLevel;
import edu.p.lodz.pl.mto.exceptions.ValidationException;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Tomasz
 */
@SessionScoped
@Named("AccountSession")
public class AccountSession implements Serializable {
    
    private ExternalContext ec;
    private Account currUser=null;
    
    //@Resource(name = "messagesBean")
    @Inject
    MessagesBean messagesBean;
    
    @EJB
    MOKEndpointLocal mOKEndpoint;
    
    @PermitAll
    public void registerAccount(Account account) throws ValidationException {

        mOKEndpoint.registerAccount(account);
    }
    
    @PermitAll
    public String showCurrentUser() {
        return currUser.getLogin();
    }
    
    @PermitAll
    public void showMessage(String message, MessageLevel messageLevel) {
        messagesBean.showMessage(message, messageLevel);
    }

    @PermitAll
    public boolean login(final String login) {
//        TO DO - sprawdzic czy to ma na pewno sens

currUser=mOKEndpoint.getAccountByLogin(login);
return currUser != null;
    }
    
     public boolean isLoggedIn() {
            return currUser != null;
    }
}
