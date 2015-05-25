package game;
 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.io.File;
import java.util.HashMap;

/**
 * 
 *  
 * Ini fájl kezelésére szolgáló osztály
 */
public class clsINI
{
    private static Properties p;
    private static String myINIFileName;
    private static Map<String, String> keyValueMapXML = new HashMap<String, String>();
    
    /**
    *Az INI fájl meglétének ellnőrzése
    *Ha nem létezik, akkor létrehozza, és beírja a szükséges kulcs-érték párokat
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
                if (myIniFile.createNewFile())
                {
                    p.load(new FileInputStream(myINIFileName));
                    setKeyValue("EnableLog", "yes");
                    setKeyValue("logLevel", "FINE");
                    setKeyValue("Player1", "Player1");
                    setKeyValue("Player2", "Player2");
                    writeINIFileXML();
                    closeINIFile();
                }
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
    
    public static void setKeyValue(String key, String value)
    {
        p.setProperty(key, value);
    }
    
    /**
     * 
     */
    public static void closeINIFile() 
    {
        try 
        {
            p.store(new FileOutputStream(myINIFileName, true), "");
        } 
        catch (Exception ex) 
        {
//            System.out.println("Exception thrown: " + ex);
        }
    }
    
    /**
    *Properties fájl olvasása
    *
    *@param sParam
    *           kiolvasandó kulcs
    *@return a kulcshoz tartozó érték
    */
    public static String readINIFileXML(String sParam) 
    {
//        try 
//        {
            String sValue;
//            p = new Properties();
//            p.load(new FileInputStream(myINIFileName));
//            p.loadFromXML(new FileInputStream(myINIFileName));
            sValue = p.getProperty(sParam);
//            setKeyValue(sParam, sValue);
            return sValue;
//        } 
//        catch (Exception ex) 
//        {
//            System.out.println("Exception thrown: " + ex);
//        }
//        return "";
    }

    /**
    *Írás a properties fájlba
    *
    *@param kulcs-érték párok 'listája'
    */
    public static void writeINIFileXML () 
    {
        try 
        {
//            p = new Properties();
//            p.load(new FileInputStream(myINIFileName));
            p.storeToXML(new FileOutputStream(myINIFileName), "");
        } 
        catch (Exception ex) 
        {
//            System.out.println("Exception thrown: " + ex);
        }
    }
}