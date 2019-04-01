/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.interceptors;

import edu.p.lodz.pl.mto.beans.MessagesBean;
import edu.p.lodz.pl.mto.exceptions.MessagingApplicationException;
import edu.p.lodz.pl.mto.interceptors.binding.MessagingApplicationExceptionHandler;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Tomasz
 */
@Interceptor
@MessagingApplicationExceptionHandler
public class MessagingApplicationExceptionHandlerInterceptor {

    //@Resource(name = "messagesBean")
    @Inject
    private MessagesBean messagesBean;

    /**
     * Metoda odpowiedzialna za przechwycenie wyjątku aplikacyjnego. W przypadku
     * przechwycenia takiego wyjątku do widoku przesyłana jest odppowiednia
     * wiadomość.
     *
     */
    @AroundInvoke
    public Object checkMessagingApplicationException(InvocationContext ictx) throws Exception {

        Object result = null;
        try {
            result = ictx.proceed();
        } catch (MessagingApplicationException e) {
            messagesBean.showMessage(e.getMessage(), e.getMessageLevel());
        }

        return result;
    }

}
