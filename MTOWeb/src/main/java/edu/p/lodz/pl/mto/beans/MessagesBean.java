/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.beans;

import edu.p.lodz.pl.mto.enums.MessageLevel;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Tomasz
 * Klasa służąca do obsługi poprzez dodanie do contextu konkretnej wiadomości o danym poziomie
 */

//@ManagedBean
@Named("messagesBean")
@RequestScoped
public class MessagesBean {
    
    public void showMessage(String message, MessageLevel messageLevel) {
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        switch(messageLevel)
        {
            case ERROR:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                break;
            case FATAL:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, null));
                break;
            case INFO:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
                break;
            case WARN:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, null));
                break;
        }
    }
}
