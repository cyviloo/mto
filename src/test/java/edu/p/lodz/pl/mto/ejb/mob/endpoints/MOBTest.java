/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.ejb.mob.endpoints;

import edu.p.lodz.pl.mto.ejb.mob.endpoints.impl.MOBEndpoint;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.validation.constraints.AssertTrue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Borys
 */
public class MOBTest {
    
    @EJB
    private static MOBEndpoint endpoint;
    private static EJBContainer ejbContainer;
    private static Context ctx;
    
    public MOBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        ejbContainer = EJBContainer.createEJBContainer();
        //System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        //System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");  
        
        ctx = ejbContainer.getContext();
    }
    
    @AfterClass
    public static void tearDownClass() {
        ejbContainer.close();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void test1() throws NamingException {
        endpoint = (MOBEndpoint)ctx.lookup("java:global/classes/MOBEndpoint");
        Assert.assertEquals(endpoint.getAllBooks().size(), 20);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
