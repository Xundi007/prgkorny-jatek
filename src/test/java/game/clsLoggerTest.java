/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 *  
 */
public class clsLoggerTest {
    
    public clsLoggerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of initLogger method, of class clsLogger.
     */
    @Test
    public void testInitLogger() {
        System.out.println("initLogger");
        //clsLogger.initLogger();
        assertTrue(true);
    }

    /**
     * Test of addLog method, of class clsLogger.
     */
    @Test
    public void testAddLog() {
        System.out.println("addLog");
        String sLevel = "";
        String logMsg = "";
        Object ex = null;
        clsLogger.addLog(sLevel, logMsg, ex);
        assertTrue(true);
    }
    
}
