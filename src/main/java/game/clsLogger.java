package game;

import java.io.IOException;
import java.util.logging.*;
import java.text.*;
import java.util.Date;

/**
 * Naplózást megvalósító osztály.
 * A program eseményeit, a játék menetét és állását naplózza egy XML formátumú naplófájlba.
 * A játék minden egyes indításakor új naplófájl keletkezik, az indítás pillanatának időbélyege
 *  bekerül a fájl nevébe.
 * A logolás szintje a program konfigurációs fájljában van megadva, 
 *  csak az ettől magasabb szintü bejegyzéseket tárolja.
 * A naplófájl a játék alkönyvtárában keletkezik.
 */
public class clsLogger
{
	/**
	 * Létrehozzuk a loggert.
	 */
	private static final Logger gameLogger = Logger.getLogger(Game.class.getName());
	
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
	*   Az utóbbi az INI fájlban tárolt érték alapján történik,
	*   így lehetőség nyílik az igényeknek megfelelő naplózás beállítására.
	*/
	public static void initLogger()
	{
		try
		{
			String stmp = clsINI.readINIFileXML("EnableLog");
			Date date = new Date() ;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
			if (stmp.equals("yes") || stmp.equals(""))
			{
				enableLog = true;  
			}
			fh = new FileHandler(System.getProperty("user.home") + "//game//" + "game" + df.format(date) + ".log");
			stmp = clsINI.readINIFileXML("logLevel");
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
					gameLogger.setLevel(Level.SEVERE );
					break;
			}
			fh.setFormatter(new XMLFormatter());
			gameLogger.addHandler(fh);
		} catch (IOException | SecurityException e) {
//		  System.out.println("Exception thrown: " + e);
		}
	}
	
	/**
	* Naplófájl lezárása.
	*/
	public static void closeLogger()
	{
		try
		{
			fh.flush();
			fh.close();
		} catch (Exception e) {
//		  System.out.println("Exception thrown: " + e);
		}
	}
	
	/**
	* Írás a naplófájlba.
	* @param sLevel naplózás szintje
	* @param logMsg bejegyzés szövege
	* @param ex a warning esetén a kivételobjektum, egyébként null
	*/
	public static void addLog(String sLevel, String logMsg, Object ex)
	{
		if (enableLog)
		{
			try
			{
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
			catch (Exception e) 
			{
//			  System.out.println("Exception thrown: " + e);
			}
		}
	}
}