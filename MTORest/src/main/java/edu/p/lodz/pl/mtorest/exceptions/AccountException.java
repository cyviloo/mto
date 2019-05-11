/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.exceptions;

import javax.ws.rs.core.Response;

/**
 *
 * @author Tomasz
 */
public class AccountException extends RuntimeException {

    private final int statusCode;

    private final String responseMessage;

    public AccountException(String responseMessage) {
        this(Response.Status.BAD_REQUEST.getStatusCode(), responseMessage);
    }

    public AccountException(int statusCode, String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

}
