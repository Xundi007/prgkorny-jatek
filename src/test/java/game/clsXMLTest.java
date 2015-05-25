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
 */
public class clsXMLTest {
    
    public clsXMLTest() {
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
     * Test of saveData method, of class clsXML.
     */
    @Test
    public void testSaveData() {
        System.out.println("saveData");
        String player = "";
        int score = 0;
        clsXML.saveData(player, score);
        assertTrue(true);
    }

    /**
     * Test of getData method, of class clsXML.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        clsXML.getData();
        assertTrue(true);
    }
    
}
