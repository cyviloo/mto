/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.ejb.mok.endpoints;

import edu.p.lodz.pl.mto.entities.Account;
import javax.ejb.Local;
import javax.inject.Named;
import javax.validation.ValidationException;

/**
 *
 * @author Tomasz
 */
@Local
public interface MOKEndpointLocal {

     
    void registerAccount(Account account);
    
    public String showCurrentUser();    
    
    public Account getAccountByLogin(String login);

}
