package dao;

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


import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HistoryXMLTest {

    static String url;
    String player;
    int score;
    File tmpFile = null;

    public HistoryXMLTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        url = HistoryXML.getInstance().getUrl();
        SettingsXML.getInstance().createGameFolder();
    }

    @AfterClass
    public static void tearDownClass() {
        HistoryXML.getInstance().setUrl(url);

        File testXML = new File(System.getProperty("user.home") + "//game//" + "HistoryTest.xml");
        testXML.delete();
    }

    @Before
    public void setUp() {
        player = "TestPlayer";
        score = 0;
        InputStream cpResource = getClass().getResourceAsStream("HistoryPremade.xml");
        try {
            tmpFile = File.createTempFile("HistoryPremade.xml", "");
            FileUtils.copyInputStreamToFile(cpResource, tmpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        tmpFile.delete();
    }

    /**
     * A HistoryXML osztály saveData metódusának tesztje.
     */
    @Test
    public void testSaveData() {
        HistoryXML history = HistoryXML.getInstance();
        history.setUrl(System.getProperty("user.home") + "//game//" + "HistoryTest.xml");
        history.saveData(player, score);
        File expect = new File(System.getProperty("user.home") + "//game//" + "HistoryTest.xml");
        assertTrue(expect.exists());
    }

    /**
     * A HistoryXML osztály getData metódusának tesztje.
     */
    @Test
    public void testGetData() {
        HistoryXML history = HistoryXML.getInstance();
        history.setUrl(tmpFile.getAbsolutePath());
        assertEquals("GetData teszt: ", "id : 1 Játékos neve : TestedPlayerOK pontszám : 0\n", history.getData());
    }
}