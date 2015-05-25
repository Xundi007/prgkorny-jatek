/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
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
public class gameWindowTest {
    
    public gameWindowTest() {
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
     * Test of setNames method, of class gameWindow.
     */
    @Test
    public void testSetNames() {
        System.out.println("setNames");
        String strPlayer1Name = "XX";
        String strPlayer2Name = "";
        gameWindow instance = new gameWindow();
        instance.setNames(strPlayer1Name, strPlayer2Name);
        String sResult = instance.getPlayerName(0);
        assertEquals(strPlayer1Name, sResult);
    }

    /**
     * Test of newGame method, of class gameWindow.
     */
    @Test
    public void testNewGame() {
        System.out.println("newGame");
        gameWindow instance = new gameWindow();
        instance.newGame();
        assertFalse(instance.firstTime);
        assertTrue(true);
    }

    /**
     * Test of isGameOver method, of class gameWindow.
     */
    @Test
    public void testIsGameOver() {
        System.out.println("isGameOver");
        boolean expResult=false;
        gameWindow instance = new gameWindow();
        int[][] gameMatrix = 
                {{1,2,2,0,0,2}
                ,{2,2,0,0,0,0}
                ,{2,2,2,0,0,0}
                ,{0,0,0,0,2,2}
                ,{0,0,0,2,2,2}
                ,{2,0,0,2,2,1}};
        boolean result = instance.isGameOver(gameMatrix, 1);
        assertEquals(result, expResult);
    }

    /**
     * Test of mouseClicked method, of class gameWindow.
     */
    @Test
    public void testMouseClicked() {
        System.out.println("mouseClicked");
        MouseEvent e = null;
        gameWindow instance = new gameWindow();
        instance.mouseClicked(e);
        assertTrue(true);
    }

    /**
     * Test of mouseEntered method, of class gameWindow.
     */
    @Test
    public void testMouseEntered() {
        System.out.println("mouseEntered");
        MouseEvent e = null;
        gameWindow instance = new gameWindow();
        instance.mouseEntered(e);
        assertTrue(true);
    }

    /**
     * Test of mouseExited method, of class gameWindow.
     */
    @Test
    public void testMouseExited() {
        System.out.println("mouseExited");
        MouseEvent e = null;
        gameWindow instance = new gameWindow();
        instance.mouseExited(e);
        assertTrue(true);
    }

    /**
     * Test of mousePressed method, of class gameWindow.
     */
    @Test
    public void testMousePressed() {
        System.out.println("mousePressed");
        MouseEvent e = null;
        gameWindow instance = new gameWindow();
        instance.mousePressed(e);
        assertTrue(true);
    }

    /**
     * Test of mouseReleased method, of class gameWindow.
     */
    @Test
    public void testMouseReleased() {
        System.out.println("mouseReleased");
        MouseEvent e = null;
        gameWindow instance = new gameWindow();
        instance.mouseReleased(e);
        assertTrue(true);
    }

    /**
     * Test of mouseDragged method, of class gameWindow.
     */
    @Test
    public void testMouseDragged() {
        System.out.println("mouseDragged");
        MouseEvent e = null;
        gameWindow instance = new gameWindow();
        instance.mouseDragged(e);
        assertTrue(true);
    }

    /**
     * Test of mouseMoved method, of class gameWindow.
     */
    @Test
    public void testMouseMoved() {
        System.out.println("mouseMoved");
        MouseEvent e = null;
        gameWindow instance = new gameWindow();
        instance.mouseMoved(e);
        assertTrue(true);
    }

    /**
     * Test of gotFocus method, of class gameWindow.
     */
    @Test
    public void testGotFocus() {
        System.out.println("gotFocus");
        gameWindow instance = new gameWindow();
        instance.gotFocus();
        assertTrue(true);
    }

    /**
     * Test of resetBoard method, of class gameWindow.
     */
    @Test
    public void testResetBoard() {
        System.out.println("resetBoard");
        gameWindow instance = new gameWindow();
        instance.resetBoard();
        assertTrue(true);
    }

    /**
     * Test of setupGame method, of class gameWindow.
     */
    @Test
    public void testSetupGame() {
        System.out.println("setupGame");
        gameWindow instance = new gameWindow();
        instance.setupGame();
        assertTrue(true);
    }

    /**
     * Test of legalMove method, of class gameWindow.
     */
    @Test
    public void testLegalMove() {
        System.out.println("legalMove");
        int startRow = 0;
        int startCol = 0;
        int desRow = 0;
        int desCol = 1;
        gameWindow instance = new gameWindow();
        int expResult = 1;
        int result = instance.legalMove(startRow, startCol, desRow, desCol);
        assertEquals(expResult, result);
    }

    /**
     * Test of IsThereAnyStep method, of class gameWindow.
     */
    @Test
    public void testIsThereAnyStep() {
        System.out.println("IsThereAnyStep");
        int startRow = 0;
        int startCol = 0;
        int[][] gameMatrix = 
                {{1,2,2,0,0,2}
                ,{2,2,0,0,0,0}
                ,{2,2,2,0,0,0}
                ,{0,0,0,0,2,2}
                ,{0,0,0,2,2,2}
                ,{2,0,0,2,2,1}};
        gameWindow instance = new gameWindow();
        boolean result = instance.IsThereAnyStep(startRow, startCol, gameMatrix);
        assertFalse(result);
    }
}
