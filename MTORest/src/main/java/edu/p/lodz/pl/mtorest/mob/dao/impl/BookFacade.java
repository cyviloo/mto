/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.mob.dao.impl;

import edu.p.lodz.pl.mtorest.entities.Book;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import edu.p.lodz.pl.mtorest.mob.dao.BookFacadeLocal;

/**
 *
 * @author Tomasz
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class BookFacade implements BookFacadeLocal{

    @PersistenceContext(unitName = "mtomob_pu")
    EntityManager em;

    protected static final Logger loger = Logger.getGlobal();

    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<Book> getAllBooks() {
        TypedQuery<Book> tq = em.createNamedQuery("Book.findAll", Book.class);
        return tq.getResultList();
    }
    @Override
     public Book find(Object bookId) {
        TypedQuery<Book> tq = em.createNamedQuery("Book.findById", Book.class);
        tq.setParameter("idBook", bookId);
        List<Book> acc = tq.getResultList();
        if (acc.isEmpty()) {
            return null;
        }
        return acc.get(0);
    }
    
}
