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


import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;
 
/**
 * Perzisztens adattárolás XML fájlban.
 * Az adatbázis alternatívája.
 * A játékosok nevét és eredményét tárolja.
 * Az 'adatbázis' fájl a játék alkönyvtárában keletkezik.
*/
public class clsXML
{
	/**
	 * A konfigurációs fájl teljes elérési útja.
	 */
	static String url = System.getProperty("user.home") + "//game//" + "gameLog.xml";

	/**
	* Adatok mentése XML fájlba.
	* @param player a játékos neve
	* @param score a játékosnak a játék során gyüjtött pontszáma
	*/
	public static void saveData(String player, int score)
	{
		try 
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			File xmlFile = new File(url);
			if (!xmlFile.exists())
			{
				Document doc = dBuilder.newDocument();
				Element rootElement = doc.createElement("Game");
				doc.appendChild(rootElement);
				Element participants = doc.createElement("Players");
				rootElement.appendChild(participants);
				Attr attr = doc.createAttribute("id");
				attr.setValue("1");
				participants.setAttributeNode(attr);
				Element playerName = doc.createElement("playerName");
				playerName.appendChild(doc.createTextNode(player));
				participants.appendChild(playerName);
				Element playerScore = doc.createElement("score");
				playerScore.appendChild(doc.createTextNode(score + ""));
				participants.appendChild(playerScore);

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(url));
				transformer.transform(source, result);
			}
			else
			{
				Document doc = dBuilder.parse(xmlFile);
				NodeList list = doc.getElementsByTagName("Players");
				int nodeCount = list.getLength() + 1;
				System.out.println(nodeCount);
				Element participants = doc.createElement("Players");
				Element rootElement = doc.getDocumentElement();
				rootElement.appendChild(participants);
				Attr attr = doc.createAttribute("id");
				attr.setValue(nodeCount + "");
				participants.setAttributeNode(attr);
				Element playerName = doc.createElement("playerName");
				playerName.appendChild(doc.createTextNode(player));
				participants.appendChild(playerName);
				Element playerScore = doc.createElement("score");
				playerScore.appendChild(doc.createTextNode(score + ""));
				participants.appendChild(playerScore);

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(url));
				transformer.transform(source, result);
			}
		} 
		catch (ParserConfigurationException | DOMException | TransformerException | SAXException | IOException e) 
		{
			clsLogger.addLog("W", "Probléma adódott", e);
		}
	}

	/**
	* A játékosok és pontszámaik listázása.
	*/
	public static void getData()
	{
		try 
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			File xmlFile = new File(url);
			if (xmlFile.exists())
			{
				Document doc = dBuilder.parse(xmlFile);
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("Players");
				for (int temp = 0; temp < nList.getLength(); temp++) 
				{
					Node nNode = nList.item(temp);
					clsLogger.addLog("FT", "Current Element :" + nNode.getNodeName(), null);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) 
					{
						Element eElement = (Element) nNode;
						String msg = "participants id : " + eElement.getAttribute("id");
						msg +=  " Player's Name : " + eElement.getElementsByTagName("playerName").item(0).getTextContent();
						msg += " Score : " + eElement.getElementsByTagName("score").item(0).getTextContent();
						clsLogger.addLog("FT", msg, null);
					}
				}
			}
		} 
		catch (ParserConfigurationException | SAXException | IOException | DOMException e) 
		{
			clsLogger.addLog("W", "Probléma adódott", e);
		}
	}
}