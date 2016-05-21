package model;

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


import org.junit.*;

import static org.junit.Assert.assertTrue;

public class clsXMLTest {

    String player;
    int score;

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
        player = "TestPlayer";
        score = 0;
    }

    @After
    public void tearDown() {
    }

    /**
     * A clsXML osztály saveData metódusának tesztje.
     */
    @Test
    public void testSaveData() {
        clsXML.saveData(player, score);
        assertTrue(true);
    }

    /**
     * A clsXML osztály getData metódusának tesztje.
     */
    @Test
    public void testGetData() {
        clsXML.getData();
        assertTrue(true);
    }

}