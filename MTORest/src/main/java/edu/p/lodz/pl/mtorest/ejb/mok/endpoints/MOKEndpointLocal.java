/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.ejb.mok.endpoints;

import edu.p.lodz.pl.mtorest.entities.Account;
import javax.ejb.Local;

/**
 *
 * @author Tomasz
 */
@Local
public interface MOKEndpointLocal {

     
    public String registerAccount(Account account);
    
    public String showCurrentUser();    
    
    public Account getAccountByLogin(String login);

}
