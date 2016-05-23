package model;

/*
 * #%L
 * prgkorny-jatek
 * %%
 * Copyright (C) 2016 Debreceni Egyetem, Informatikai Kar
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


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * XML formátumú properties fájl kezelésére szolgáló osztály.
 * A game.xml konfigurációs fájl a program beállításait tartalmazza.
 */
public class clsINI {
    /**
     * properties objektum, a program beállításait tartalmazza.
     */
    private static Properties p;

    /**
     * A properties objektum (beállítások) mentésének helye.
     */
    private static String myConfigFileName;

    /**
     * A konfigurációs fájl meglétének ellenőrzése.
     * A fájl alapértelmezetten a játék alkönyvtárában van a user home könyvtár alatt.
     * Ha nem létezik, akkor létrehozza, és beírja a szükséges kulcs-érték párokat.
     * Ha létezik, akkor betölti az összes kulcs-érték párt.
     * Használt kulcsok:
     * EnableLog   - logolás engedélyezése vagy tiltása (alapértelmezés no)
     * logLevel	- logolás szintjének beállítása (alapértelmezés FINE)
     * Player1, és Player2 az aktuális játékosok
     */
    public static void createConfigFile() {
        p = new Properties();
        myConfigFileName = System.getProperty("user.home") + "//game//" + "game.xml";
        File myConfigFile = new File(myConfigFileName);
        if (!myConfigFile.exists()) {
            try {
                setKeyValue("EnableLog", "no");
                setKeyValue("logLevel", "FINE");
                setKeyValue("Player1", "Player1");
                setKeyValue("Player2", "Player2");
                setKeyValue("HighScoreName", "SenkiSe");
                setKeyValue("HighScorePoint", "0");
                p.storeToXML(new FileOutputStream(myConfigFileName), "Game settings");
            } catch (Exception ex) {
                System.out.println("Konfigurációs állomány létrehozása sikertelen: " + ex);
            }
        } else {
            try {
                p.loadFromXML(new FileInputStream(myConfigFileName));
            } catch (IOException ex) {
                System.out.println("Beállítások olvasása sikertelen! " + ex.toString());
            }
        }
    }

    /**
     * Kulcs-érték pár felülírása/beszúrása.
     *
     * @param key   a tulajdonság kulcsa
     * @param value a kulcshoz tartozó érték
     */
    public static void setKeyValue(String key, String value) {
        p.setProperty(key, value);
    }


    public static void setPlayers(String p1, String p2) {
        p.setProperty("Player1", p1);
        p.setProperty("Player2", p2);
        writeSettingsFileXML();
    }

    /**
     * Properties fájl olvasása.
     *
     * @param sParam kiolvasandó kulcs
     * @return a kulcshoz tartozó érték
     */
    public static String readSettingsFileXML(String sParam) {
        String sValue;
        sValue = p.getProperty(sParam);
        if (sValue != null)
            return sValue;
        else
            return "";
    }

    public static String[] readSettingsFileHScore() {
        String sName, sValue;
        sName = p.getProperty("HighScoreName");
        sValue = p.getProperty("HighScorePoint");
        if (sName == null || sValue == null) {
            sName = "SenkiSe";
            sValue = "0";
        }
        return (new String[]{sName, sValue});
    }

    public static void writeSettingsFileHScore(String name, String point) {
        setKeyValue("HighScoreName", name);
        setKeyValue("HighScorePoint", point);
        writeSettingsFileXML();
    }

    /**
     * A játék beállításait tárolni fogó mappa létrehozása.
     * Ha már létezik, nem történik semmi.
     */
    public static void createGameFolder() {
        File gameFolder = new File(System.getProperty("user.home") + "//game");
        if (!gameFolder.exists()) {
            try {
                if (gameFolder.mkdir()) {
                    clsLogger.addLog("I", "A játék könyvtára létre lett hozva.", null);
                } else {
                    throw new SecurityException("gameFolder.mkdir() visszatérési értéke hamis.");
                }
            } catch (SecurityException se) {
                clsLogger.addLog("S", "Nem sikerült létrehozni a játék könyvtárát!", se);
            }
        } else {
            clsLogger.addLog("I", "A játék könyvtára már létezik!", null);
        }
    }

    /**
     * A beállítások mentése az XML fájlba.
     */
    public static void writeSettingsFileXML() {
        try {
            p.storeToXML(new FileOutputStream(myConfigFileName), "Game settings");
        } catch (IOException io) {
            clsLogger.addLog("S", "A beállítások kiírása nem sikerült!", io);
        }
    }
}