package de.manuelclever.satisfactorycalculator.settings;

import de.manuelclever.satisfactorycalculator.Main;
import de.manuelclever.satisfactorycalculator.content.items.ItemMap;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;

public class Settings {
    private static Map<String, String> allSettings = new HashMap<>();

    private static final String FILE_NAME = "settings";

    private static int tabIndex;
    private static String recipeFileName;
    private static boolean wereLoaded;

    private Settings() {
    }

    public static void put(String key, String val) {
        allSettings.put(key, val);
    }

    public static String get(String key) {
        return allSettings.get(key);
    }

    public static boolean wereLoaded() {
        return wereLoaded;
    }

    public static boolean load() {
        try {
            String absolutePath = FileSystems.getDefault().getPath("src", "main", "resources", FILE_NAME + ".xml").toAbsolutePath().toString();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SettingsHandler handler = new SettingsHandler();
            saxParser.parse(absolutePath, handler);
            wereLoaded = true;
            return true;
        } catch(IOException e) {
            wereLoaded = false;
            return false;
        } catch(SAXException e) {
            System.out.println("SAX Exception: " + e.getMessage());
        } catch(ParserConfigurationException e) {
            System.out.println("Parser Configuration Exception: " + e.getMessage());
        }
        wereLoaded = false;
        return false;
    }

    public static void write() {
        String absolutePath = FileSystems.getDefault().getPath("src", "main", "resources",
                FILE_NAME + ".xml").toAbsolutePath().toString();
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = null;
        FileWriter fileWriter = null;

        try{
            fileWriter = new FileWriter(absolutePath);
            writer = factory.createXMLStreamWriter(fileWriter);

            //write header and after that each element in a loop
            writer.writeStartDocument();

            writer.writeStartElement(SettingsHandler.LAST_SESSION);

            writer.writeStartElement(SettingsHandler.SELECTED_TAB);
            writer.writeCharacters(Integer.toString(Main.getMainWindowController().getCurrentTabPaneIndex()));
            writer.writeEndElement();

            writer.writeStartElement(SettingsHandler.SELECTED_RECIPE);
            writer.writeCharacters(ItemMap.getFileSourceName().replace(".xml", ""));
            writer.writeEndElement();

            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
        }
        catch (Exception e) {
            System.err.println("Unable to write the file: " + e.getMessage());
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                    //formats the xml document
                    format(absolutePath);
                    fileWriter.close();
                }
            }
            catch (Exception e) {
                System.err.println("Unable to close the file: " + e.getMessage());
            }
        }
    }

    private static void format(String file) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new InputStreamReader(new FileInputStream(file))));

        // Gets a new transformer instance
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        // Sets XML formatting
        xformer.setOutputProperty(OutputKeys.METHOD, "xml");
        // Sets indent
        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Source source = new DOMSource(document);
        Result result = new StreamResult(new File(file));
        xformer.transform(source, result);
    }
}
