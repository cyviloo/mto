/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.pagebeans;

import edu.p.lodz.pl.mto.web.mok.AccountSession;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tomasz
 */
@RequestScoped
@Named("headerPageBean")
public class HeaderPageBean {
    
    @Inject
    private AccountSession accountSession;
    
    @PermitAll
    public String showCurrentUser()
    {
        return accountSession.showCurrentUser();
    }
    
    public String logout()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "homePage";
    }
    
     public String navigateLogin()
    {
        return "loginPage";
    }
}
