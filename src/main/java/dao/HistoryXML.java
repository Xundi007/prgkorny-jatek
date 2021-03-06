package dao;

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



import model.GameLogger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * Perzisztens adattárolás XML fájlban.
 * A játékosok nevét és eredményét tárolja.
 * Az 'adatbázis' fájl a játék alkönyvtárában keletkezik.
 * Singleton megvalósítás.
 */
public class HistoryXML {
    private static HistoryXML instance = new HistoryXML();

    protected HistoryXML() {
    }

    public static HistoryXML getInstance() {
        if (instance == null) {

            synchronized (HistoryXML.class) {
                if (instance == null) {
                    instance = new HistoryXML();
                }
            }
        }
        return instance;
    }

    /**
     * A konfigurációs fájl teljes elérési útja.
     */
    private String url = System.getProperty("user.home") + "//game//" + "History.xml";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Adatok mentése XML fájlba.
     *
     * @param player a játékos neve
     * @param score  a játékosnak a játék során gyűjtött pontszáma
     */
    public void saveData(String player, int score) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            File xmlFile = new File(url);
            if (!xmlFile.exists()) {    //Ha a fájl nem létezik.
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
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(url));
                transformer.transform(source, result);
            } else {    //Ha már létezik a fájl.
                Document doc = dBuilder.parse(xmlFile);
                NodeList list = doc.getElementsByTagName("Players");
                int nodeCount = list.getLength() + 1;
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
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(url));
                transformer.transform(source, result);
            }
        } catch (ParserConfigurationException | DOMException | TransformerException | SAXException | IOException e) {
            GameLogger.addLog("W", "Probléma adódott a pontszám lista elmentésekor.", e);
        }
    }

    /**
     * A játékosok és pontszámaik listázása.
     * @return a kiolvasott eredmények.
     */
    public String getData() {
        GameLogger.addLog("FT", "Eredmények kiolvasása elkezdődött...", null);
        String data = "Még semmi.    _@/\"";
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            File xmlFile = new File(url);
            if (xmlFile.exists()) {
                Document doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("Players");
                String tempStr = "";
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        String msg = "id : " + eElement.getAttribute("id");
                        msg += " Játékos neve : " + eElement.getElementsByTagName("playerName").item(0).getTextContent();
                        msg += " pontszám : " + eElement.getElementsByTagName("score").item(0).getTextContent();
                        tempStr += msg;
                    }
                    tempStr += "\n";
                }
                data = tempStr;
            }
            GameLogger.addLog("FT", "Eredmények kiolvasása sikeresen megtörtént.", null);
        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            GameLogger.addLog("W", "Probléma adódott a pontszám lista lekérdezésekor.", e);
        }
        return data;
    }
}