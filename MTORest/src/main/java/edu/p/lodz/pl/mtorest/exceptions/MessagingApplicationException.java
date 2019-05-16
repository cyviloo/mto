/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.exceptions;

import edu.p.lodz.pl.mtorest.enums.MessageLevel;

/**
 * Bazowy wyjątek dla przekazywania poziomu informacji.
 * @author Tomasz
 */
@javax.ejb.ApplicationException(rollback = true)
public class MessagingApplicationException extends RuntimeException{

    private final MessageLevel messageLevel;

    public MessagingApplicationException(MessageLevel messageLevel, String message) {
        super(message);
        this.messageLevel = messageLevel;
    }

    public MessagingApplicationException(MessageLevel messageLevel, String message, Throwable cause) {
        super(message, cause);
        this.messageLevel = messageLevel;
    }

    public MessagingApplicationException(MessageLevel messageLevel, Throwable cause) {
        super(cause);
        this.messageLevel = messageLevel;
    }

    public MessageLevel getMessageLevel() {
        return messageLevel;
    }
}
