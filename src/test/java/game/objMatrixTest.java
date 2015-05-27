package game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
	 * A objMatrix osztály objMatrix metódusának tesztje.
	 */
	@Test
	public void testObjMatrix() {
		System.out.println("objMatrix");
		objMatrix instance = new objMatrix();
		instance.objMatrix();
		assertNotNull("Az objektum nem jött létre",instance);
	}

	/**
	 * A objMatrix osztály resetMatrix metódusának tesztje.
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
	 * A objMatrix osztály getPlayerCell metódusának tesztje.
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
	 * A objMatrix osztály setPlayerCell metódusának tesztje.
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
	 * A objMatrix osztály getgameMatrix metódusának tesztje.
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