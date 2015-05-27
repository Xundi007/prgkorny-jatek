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


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.io.File;

/**
 * XML formátumú properties fájl kezelésére szolgáló osztály.
 * A game.xml konfigurációs fájl a program beállításait tartalmazza.
 */
public class clsINI
{
	/**
	 * properties objektum, a program beállításait tartalmazza.
	 */
	private static Properties p;
	
	/**
	 * A properties objektum (beállítások) mentésének helye.
	 */
	private static String myINIFileName;
	
	/**
	* Az INI fájl meglétének ellnőrzése.
	* A fájl alapértelmezetten a játék alkönyvtárában van a user home könytár alatt.
	* Ha nem létezik, akkor létrehozza, és beírja a szükséges kulcs-érték párokat.
	* Ha létezik, akkor betölti az öszzes kulcs-érték párt.
	* Használt kulcsok:
	*   EnableLog   - logolás engedélyezése vagy tiltása (alapértelmezés yes)
	*   logLevel	- logolás szintjének beállítása (alapértelmezés FINE)
	*   Player1, és Player2 az aktuális játékosok
	*/
	public static void cretaeINIFile()
	{
		p = new Properties();
		myINIFileName = System.getProperty("user.home")+"//game//"+"game.xml";
		File myIniFile = new File(myINIFileName);
		if (!myIniFile.exists())
		{
			try
			{
				setKeyValue("EnableLog", "yes");
				setKeyValue("logLevel", "FINE");
				setKeyValue("Player1", "Player1");
				setKeyValue("Player2", "Player2");
				writeINIFileXML();
			}
			catch (Exception ex)
			{
				System.out.println("Exception thrown: " + ex);
			}
		}
		else
		{
			try
			{
				p.loadFromXML(new FileInputStream(myINIFileName));

			}
			catch(Exception ex)
			{
				System.out.println("Exception thrown: " + ex);
				
			}
		}
			
	}
	
	/**
	 * Kulcs-érték pár felülírása/beszúrása.
	 * @param key a tulajdonság kulcsa
	 * @param value a kulcshoz tartozó érték
	 */
	public static void setKeyValue(String key, String value)
	{
		p.setProperty(key, value);
	}
	
   
	/**
	* Properties fájl olvasása.
	* @param sParam kiolvasandó kulcs
	* @return a kulcshoz tartozó érték
	*/
	public static String readINIFileXML(String sParam) 
	{
		try 
		{
			String sValue;
			sValue = p.getProperty(sParam);
			return sValue;
		} 
		catch (Exception ex) 
		{
			System.out.println("Exception thrown: " + ex);
		}
		return "";
	}

	/**
	* A beállítások mentése az XML fájlba.
	*/
	public static void writeINIFileXML () 
	{
		try 
		{
			p.storeToXML(new FileOutputStream(myINIFileName), "Game settings");
		} 
		catch (Exception ex) 
		{
			System.out.println("Exception thrown: " + ex);
		}
	}
}