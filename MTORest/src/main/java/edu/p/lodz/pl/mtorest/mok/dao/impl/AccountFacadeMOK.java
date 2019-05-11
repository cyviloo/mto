/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.mok.dao.impl;

import edu.p.lodz.pl.mtorest.entities.Account;
import edu.p.lodz.pl.mtorest.mok.dao.AccountFacadeMOKLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Tomasz
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountFacadeMOK  implements AccountFacadeMOKLocal {

    @PersistenceContext(unitName = "mtomok_pu")
    private EntityManager em;
    
     protected static final Logger loger = Logger.getGlobal();

    protected EntityManager getEntityManager() {
        return em;
    }
  
    @Override
    @PermitAll
    public String create(Account entity) {
        try{
              getEntityManager().persist(entity);
        getEntityManager().flush();    
        } catch (ConstraintViolationException e) {
       loger.log(Level.SEVERE,"Exception: ");
       e.getConstraintViolations().forEach(err->loger.log(Level.SEVERE,err.toString()));
    }
      return entity.getLogin();
    }

    @Override
    @PermitAll
    public Account findByLogin(String login) {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findByLogin", Account.class);
        tq.setParameter("login", login);
        List<Account> acc = tq.getResultList();
        if (acc.isEmpty()) {
            return null;
        }
        return acc.get(0);
    }
    
    @Override
    public Account find(Object id) {
        return getEntityManager().find(Account.class, id);
    }
    
}
