/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.exceptions;

import edu.p.lodz.pl.mtorest.enums.MessageLevel;
import javax.ejb.ApplicationException;

/**
 * Wyjątek rzucany jesli jakakolwiek walidacja biznezoswa nie została spełniona.
 * @author Tomasz
 */
@ApplicationException(rollback = true)
public class ValidationException extends MessagingApplicationException {

    public ValidationException(String message, MessageLevel messageLevel) {
        super(messageLevel, message);
    }

    public ValidationException(MessageLevel messageLevel, String message, Throwable cause) {
        super(messageLevel, message, cause);
    }

    public ValidationException(MessageLevel messageLevel, Throwable cause) {
        super(messageLevel, cause);
    }
    
   
}