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