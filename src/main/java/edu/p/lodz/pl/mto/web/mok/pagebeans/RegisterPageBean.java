/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.mok.pagebeans;

import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.enums.MessageLevel;
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
@Named("registerPageBean")
public class RegisterPageBean {
    
    @Inject
    AccountSession accountSession;
    
    private Account account;
    
    public RegisterPageBean() {
        account = new Account();
    }
    
    @PermitAll
    public Account getAccount() {
        return account;
    }

    @PermitAll
    public void setAccount(Account account) {
        this.account = account;
    }
    
    @PermitAll
    public String registerAccount() {

        accountSession.registerAccount(account);

        accountSession.showMessage("Register success", MessageLevel.INFO);
        return "homePage";
    }
}
