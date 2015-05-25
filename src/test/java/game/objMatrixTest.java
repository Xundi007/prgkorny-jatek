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
public class objMatrixTest {
    
    public objMatrixTest() {
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
     * Test of objMatrix method, of class objMatrix.
     */
    @Test
    public void testObjMatrix() {
        System.out.println("objMatrix");
        objMatrix instance = new objMatrix();
        instance.objMatrix();
        assertNotNull("Az objektum nem jött létre",instance);
    }

    /**
     * Test of resetMatrix method, of class objMatrix.
     */
    @Test
    public void testResetMatrix() {
        System.out.println("resetMatrix");
        objMatrix instance = new objMatrix();
        instance.resetMatrix();
        instance.setPlayerCell(2, 2, 3);
        instance.resetMatrix();
        int expResult = 0;
        int result = instance.getPlayerCell(2, 2);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayerCell method, of class objMatrix.
     */
    @Test
    public void testGetPlayerCell() {
        System.out.println("getPlayerCell");
        int row = 0;
        int col = 0;
        objMatrix instance = new objMatrix();
        instance.resetMatrix();
        int expResult = 1;
        int result = instance.getPlayerCell(row, col);
        assertEquals(expResult, result);
    }

    /**
     * Test of setPlayerCell method, of class objMatrix.
     */
    @Test
    public void testSetPlayerCell() {
        System.out.println("setPlayerCell");
        int row = 0;
        int col = 0;
        int player = 0;
        objMatrix instance = new objMatrix();
        instance.resetMatrix();
        instance.setPlayerCell(row, col, player);
        int expResult = 0;
        int result = instance.getPlayerCell(row, col);
        assertEquals(expResult, result);
    }

    /**
     * Test of getgameMatrix method, of class objMatrix.
     */
    @Test
    public void testGetgameMatrix() {
        System.out.println("getgameMatrix");
        objMatrix instance = new objMatrix();
        instance.resetMatrix();
        int[][] expResult = 
                {{1,0,0,0,0,2}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,3,0,0}
                ,{0,0,0,0,0,0}
                ,{2,0,0,0,0,1}};
        int[][] result = instance.getgameMatrix();
        assertArrayEquals(expResult, result);
    }
    
}
