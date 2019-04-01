/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.mok.pagebeans;

import edu.p.lodz.pl.mto.interceptors.binding.MessagingApplicationExceptionHandler;
import edu.p.lodz.pl.mto.web.mok.AccountSession;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Tomasz
 */
@RequestScoped
@Named("loginPageBean")
@MessagingApplicationExceptionHandler
public class LoginPageBean {
    
    private String login;

    @Inject
    AccountSession accountSession;
    
    @PermitAll
    public String login() {
        boolean success = accountSession.login(login);
        if (success == false) {
            return navigateLogin();
        }
        return navigateIndex();
    }
    
    @PermitAll
    public String getLogin() {
        return login;
    }

    @PermitAll
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String navigateIndex()
    {
        return "homePage";
    }
    
    public String navigateLogin()
    {
        return "loginPage";
    }
}
