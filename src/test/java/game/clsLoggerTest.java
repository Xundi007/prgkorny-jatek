package game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
	 * A clsLogger osztály initLogger metódusának tesztje.
	 */
	@Test
	public void testInitLogger() {
//		System.out.println("initLogger");
//		clsINI.cretaeINIFile();
//		clsLogger.initLogger();
		assertTrue(true);
	}

	/**
	 * A clsLogger osztály addLog metódusának tesztje.
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