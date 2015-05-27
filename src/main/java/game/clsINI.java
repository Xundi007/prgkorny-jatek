package game;

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