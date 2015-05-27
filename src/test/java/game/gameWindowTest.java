package game;

/*
 * #%L
 * prgkorny-jatek
 * %%
 * Copyright (C) 2015 Debreceni Egyetem, Informatikai Kar
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
	 * A gameWindow osztály setNames metódusának tesztje.
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
	 * A gameWindow osztály newGame metódusának tesztje.
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
	 * A gameWindow osztály mouseClicked metódusának tesztje.
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
	 * A gameWindow osztály mouseEntered metódusának tesztje.
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
	 * A gameWindow osztály mouseExited metódusának tesztje.
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
	 * A gameWindow osztály mousePressed metódusának tesztje.
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
	 * A gameWindow osztály mouseReleased metódusának tesztje.
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
	 * A gameWindow osztály mouseDragged metódusának tesztje.
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
	 * A gameWindow osztály mouseMoved metódusának tesztje.
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
	 * A gameWindow osztály gotFocus metódusának tesztje.
	 */
	@Test
	public void testGotFocus() {
		System.out.println("gotFocus");
		gameWindow instance = new gameWindow();
		instance.gotFocus();
		assertTrue(true);
	}

	/**
	 * A gameWindow osztály resetBoard metódusának tesztje.
	 */
	@Test
	public void testResetBoard() {
		System.out.println("resetBoard");
		gameWindow instance = new gameWindow();
		instance.resetBoard();
		assertTrue(true);
	}

	/**
	 * A gameWindow osztály setupGame metódusának tesztje.
	 */
	@Test
	public void testSetupGame() {
		System.out.println("setupGame");
		gameWindow instance = new gameWindow();
		instance.setupGame();
		assertTrue(true);
	}

	/**
	 * A gameWindow osztály legalMove metódusának tesztje.
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
	 * A gameWindow osztály IsThereAnyStep metódusának tesztje.
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
				,{0,0,0,3,2,2}
				,{0,0,0,2,2,2}
				,{2,0,0,2,2,1}};
		gameWindow instance = new gameWindow();
		boolean result = instance.IsThereAnyStep(startRow, startCol, gameMatrix);
		assertFalse(result);
	}

	/**
	 * A gameWindow osztály isGameOver metódusának tesztje.
	 */
	@Test
	public void testisGameOver() {
		System.out.println("isGameOver");
		int player = 1;
		int[][] gameMatrix = 
				{{1,2,2,0,0,2}
				,{2,2,0,0,0,0}
				,{2,2,2,0,0,0}
				,{0,0,0,3,2,2}
				,{0,0,0,2,2,2}
				,{2,0,0,2,2,1}};
		gameWindow instance = new gameWindow();
		boolean result = instance.isGameOver(gameMatrix, player);
		assertTrue(result);
	}
}