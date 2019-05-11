/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.mob.dao.impl;

import edu.p.lodz.pl.mto.entities.Account;
import edu.p.lodz.pl.mto.entities.Rental;
import edu.p.lodz.pl.mto.mob.dao.RentalFacadeLocal;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionRolledbackException;

/**
 *
 * @author Tomasz
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class RentalFacade implements RentalFacadeLocal {

    @PersistenceContext(unitName = "mtomob_pu")
    EntityManager em;

    protected static final Logger loger = Logger.getGlobal();

    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(Rental entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    @Override
    public void edit(Rental entity) {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    @Override
    public List<Rental> findByUser(Account account) throws TransactionRolledbackException {
        TypedQuery<Rental> tq = em.createNamedQuery("Rental.findByUser", Rental.class);
        tq.setParameter("account", account);
        return tq.getResultList();
    }

    @Override
    public List<Rental> findHistoryByUser(Account account) throws TransactionRolledbackException {
        TypedQuery<Rental> tq = em.createNamedQuery("Rental.findHistoryByUser", Rental.class);
        tq.setParameter("account", account);
        return tq.getResultList();
    }

}
