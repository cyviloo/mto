/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.web.pagebeans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Tomasz
 */
@RequestScoped
@Named(value = "menuPageBean")
public class MenuPageBean {

    public String navigateRegister() {
        return "registerPage";
    }

}
