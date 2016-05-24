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


import view.View;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

/**
 * Naplózást megvalósító osztály.
 * A program eseményeit, a játék menetét és állását naplózza egy XML formátumú naplófájlba.
 * A játék minden egyes indításakor új naplófájl keletkezik, az indítás pillanatának időbélyege
 * bekerül a fájl nevébe.
 * A logolás szintje a program konfigurációs fájljában van megadva,
 * csak az ettől magasabb szintű bejegyzéseket tárolja.
 * A naplófájl a játék alkönyvtárában keletkezik.
 */
public class GameLogger {
    /**
     * Létrehozzuk a loggert.
     */
    private static final Logger gameLogger = Logger.getLogger(View.class.getName());

    /**
     * A naplófájlhoz rendelt fájlkezelő.
     */
    private static FileHandler fh;

    /**
     * A logolás engedélyezése/tiltása, a konfigurációs fájlból töltjük.
     */
    private static boolean enableLog = false;

    /**
     * A naplófájl megadása, és a naplózás szintjének a beállítása.
     * Az utóbbi az INI fájlban tárolt érték alapján történik,
     * így lehetőség nyílik az igényeknek megfelelő naplózás beállítására.
     */
    public static void initLogger() {
        String stmp = SettingsXML.readSettingsFileXML("EnableLog");
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        if (stmp.equals("yes") || stmp.equals("")) {
            enableLog = true;
        }
        if (enableLog) {
            try {
                fh = new FileHandler(System.getProperty("user.home") + "//game//" + "game" + df.format(date) + ".log");
                stmp = SettingsXML.readSettingsFileXML("logLevel");
                if (stmp.equals(""))
                    stmp = "INFO";
                switch (stmp) {
                    case "FINEST":
                        gameLogger.setLevel(Level.FINEST);
                        break;
                    case "FINER":
                        gameLogger.setLevel(Level.FINER);
                        break;
                    case "FINE":
                        gameLogger.setLevel(Level.FINE);
                        break;
                    case "INFO":
                        gameLogger.setLevel(Level.INFO);
                        break;
                    case "CONFIG":
                        gameLogger.setLevel(Level.CONFIG);
                        break;
                    case "WARNING":
                        gameLogger.setLevel(Level.WARNING);
                        break;
                    case "SEVERE":
                        gameLogger.setLevel(Level.SEVERE);
                        break;
                }
                fh.setFormatter(new XMLFormatter());
                gameLogger.addHandler(fh);
            } catch (IOException e) {
                System.out.println("Nem sikerült a log fájlt létrehozni: " + e);
            }
        }

    }

    /**
     * Naplófájl lezárása.
     */
    public static void closeLogger() {
        if(enableLog) {
            try {
                fh.flush();
                fh.close();
            } catch (SecurityException e) {
                System.out.println("Nem sikerült a log fájlt bezárni: " + e);
            }
        }
    }

    /**
     * Írás a naplófájlba.
     *
     * @param sLevel naplózás szintje
     * @param logMsg bejegyzés szövege
     * @param ex     a warning esetén a kivételobjektum, egyébként null
     */
    public static void addLog(String sLevel, String logMsg, Object ex) {
        if (enableLog) {
            switch (sLevel) {
                case "FT":
                    gameLogger.finest(logMsg);
                    break;
                case "FR":
                    gameLogger.finer(logMsg);
                    break;
                case "F":
                    gameLogger.fine(logMsg);
                    break;
                case "I":
                    gameLogger.log(Level.INFO, logMsg);
                    break;
                case "C":
                    gameLogger.log(Level.CONFIG, logMsg);
                    break;
                case "W":
                    gameLogger.log(Level.WARNING, logMsg, ex);
                    break;
                case "S":
                    gameLogger.log(Level.SEVERE, logMsg, ex);
                    break;
            }
            fh.flush();
        }
    }
}