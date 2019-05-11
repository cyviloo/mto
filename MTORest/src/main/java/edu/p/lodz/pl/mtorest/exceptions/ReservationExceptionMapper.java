/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.exceptions;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ReservationExceptionMapper implements ExceptionMapper<AccountException> {

    public static class ErrorMessage {

        public final String error;

        public ErrorMessage(String error) {
            this.error = error;
        }
    }

    @Override
    public Response toResponse(AccountException exception) {
        return Response
                .status(exception.getStatusCode())
                .entity(new ErrorMessage(exception.getResponseMessage()))
                .build();
    }
}
