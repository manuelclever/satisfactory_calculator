package de.manuelclever.satisfactorycalculator.content.items;


import de.manuelclever.satisfactorycalculator.content.RecipeGraph;
import de.manuelclever.satisfactorycalculator.gui.TabRecipePageController;
import javafx.collections.FXCollections;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/*
    Static Map for all items. Contains all infos to the items and recipes. Gets it's content from the save file,
    or from being added in the program.

    Structure:
        Map<ID, Item(ID, name, mj, LinkedList<Edge(IDItem, IDRessourceItem, count)>)

            The Map contains the ID of the item as key, and the item itself
            The Item contains
                the ID of this Item,
                the name of this item,
                the energy worth of this item if it can be used to generate electricity.
                    For most items this will stay 0.
                And an linkedList which contains the recipe. Each edge contains one item which is
                    needed to produce the source item. Each edge contains
                        the ID of the ProductItem,
                        the ID of the RessourceItem which is needed to produce the ProductItem,
                        the quantity of how many RessourceItems of this typ are needed

    ! The item ItemMap contains also a dummy item with an ID  of "0". Why this is needed and should never be removed
        can be read in the calculator class !

     To understand closer in which way the item connections are represented in the LinkedList,
     refer to the Calculator class.
 */

public final class ItemMap {
    private static Map<Integer, Item> items = new HashMap<>();
    public static final String FILE_DIRECTORY_PATH = FileSystems.getDefault()
            .getPath("src", "main", "resources", "recipes").toAbsolutePath().toString();
    //the sourceFile the ItemMap got populated with
    private static String fileSourceName;
    //
    private static boolean readOnly;
    //to determine if changes to an item or the itemMap have been made, that need to be saved
    private static boolean madeChanges = false;

    // Inject controller
    @FXML
    private static TabRecipePageController tabRecipePageController;

    private ItemMap() {
    }

    public static Map<Integer, Item> get() {
        return items;
    }

    public static Item getItem(int i) {
        return items.get(i);
    }

    public static Item getItem(String name) {
        for(Item item : items.values()) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    //used when the english name of the item is to be compared
    public static Item getOriginalItem(String name) {
        for(Item item : items.values()) {
            if(item.getOriginalName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public static String getFileSourceName() {
        return fileSourceName;
    }

    public static boolean isReadOnly() {
        return readOnly;
    }

    public static void setReadOnly(boolean readOnly) {
        ItemMap.readOnly = readOnly;
    }

    public static boolean madeChanges() {
        return madeChanges;
    }

    public static void put(Item item) {
        items.put(item.getId(), item);
        setMadeChanges();
    }

    public static void put(int id, String name, int mj, LinkedList<Edge> edges) {
        put(new Item(id, name, mj, edges));
    }

    public static void remove(int i) {
        items.remove(i);
        setMadeChanges();
    }

    public static void remove(Item item) {
        items.remove(item.getId());
        setMadeChanges();
    }

    private static void setMadeChanges() {
        if(!madeChanges) {
            madeChanges = true;
        }
    }

    public static boolean containsKey(int i) {
        return items.containsKey(i);
    }

    public static boolean containsName(String name) {

        for(Item item : items.values()) {
            if(item.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static int size(){
        return items.size();
    }

    public static void print() {
        items.values().forEach(item -> System.out.println(item.toString()));
    }

    public static void clear() {
        items.clear();
    }


    public static int[] getFreeID() {
        return getFreeID(-1);
    }


    public static int[] getFreeID(int id) {
        //puts all keys from the ItemMap in a sortedList
        SortedList<Integer> sortedKeys = new SortedList<>(FXCollections.observableArrayList(items.keySet()));
        int[] freeIds = new int[]{-1, -1, -1};

        //gets the first two free IDs from the ItemMap, returns an array
        //[0] not used [1] nextID [3] nexIDAfterThat
        if(id == -1) {
            freeIds[1] = getNextFreeID(sortedKeys, 0);
            freeIds[2] = getNextFreeID(sortedKeys, freeIds[1]);
        //gets the next and the previous free ID from the current ID, if the prev ID doesn't exists return -1 instead
        //[0] prevID [1]not used [3] nextID
        } else {
            freeIds[0] = getPrevFreeID(sortedKeys, id);
            freeIds[2] = getNextFreeID(sortedKeys, id);
        }

        return freeIds;
    }

    //return the next free ID from the current ID
    private static int getNextFreeID(SortedList<Integer> sortedKeys, int id) {
        boolean ranOnce = false;
        int preId = id;
        for(int i = sortedKeys.indexOf(id) + 1; i < sortedKeys.size(); i++) {
            if(sortedKeys.get(i) != preId + 1) {
                return i;
            }
            preId = i;
            if(ranOnce = false) {
                ranOnce = true;
            }
        }
        return !ranOnce ? preId + 1 : preId + 2;
    }

    //return the prev free ID from the current position
    private static int getPrevFreeID(SortedList<Integer> sortedKeys, int id) {
        int preId = id;
        for(int i = sortedKeys.indexOf(id) - 1; i > 0; i--) {
            if(sortedKeys.get(i) != preId - 1) {
                return i;
            }
            preId = i;
        }
        return -1;

    }

    //read from xml via SAX
    public static void load(String fileName) {
        clear();
        try {
            String absolutePath = FILE_DIRECTORY_PATH + FileSystems.getDefault().getSeparator() + fileName + ".xml";
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ItemHandler handler = new ItemHandler();
            saxParser.parse(absolutePath, handler);

            //when all items are added to the ItemMap, the RecipeGraph gets populated with these Items
            RecipeGraph.refresh();
            //sets the sourceName of the content the ItemMap got populated with
            File sourceFile = new File(absolutePath);
            fileSourceName = sourceFile.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        madeChanges = false;
    }

    //write to xml via SAX
    public static void write(String fileName) {
        String absolutePath = FILE_DIRECTORY_PATH + FileSystems.getDefault().getSeparator() + fileName + ".xml";
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = null;
        FileWriter fileWriter = null;

        try{
            fileWriter = new FileWriter(absolutePath);
            writer = factory.createXMLStreamWriter(fileWriter);

            //write header and after that each element in a loop
            writer.writeStartDocument();

            writer.writeStartElement(ItemHandler.ITEMS);

            writer.writeStartElement(ItemHandler.READ_ONLY);
            writer.writeCharacters(Boolean.toString(readOnly));
            writer.writeEndElement();

            for(Item item : items.values()) {
                writer.writeStartElement(ItemHandler.ITEM);

                writer.writeStartElement(ItemHandler.ID);
                writer.writeCharacters(Integer.toString(item.getId()));
                writer.writeEndElement();

                //write itemName to xml, use getOriginalMethod to get the english name
                //of the item and not the translated one from the properties file
                writer.writeStartElement(ItemHandler.NAME);
                writer.writeCharacters(item.getOriginalName());
                writer.writeEndElement();

                writer.writeStartElement(ItemHandler.MJ);
                writer.writeCharacters(Integer.toString(item.getMj()));
                writer.writeEndElement();

                //gets all edges of the item and for each writes line with ressource and weight to xml
                LinkedList<Edge> list = items.get(item.getId()).getEdges();
                if(list != null) {
                    for (Edge edge : list) {
                        writer.writeStartElement(ItemHandler.IN);
                        writer.writeCharacters(edge.getResourceId() + "," + edge.getWeight());
                        writer.writeEndElement();
                    }
                }

                writer.writeEndElement();
            }

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

    //method to format xml
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

    public static String createEmptyLanguageNameTemplate() {

        Path absolutePath = FileSystems.getDefault().getPath("src", "main", "resources", "properties", "itemNameBundleTemplate.txt").toAbsolutePath();
        try {
            BufferedWriter bw = Files.newBufferedWriter(absolutePath);
            bw.write("#Save this file as 'itemNameBundle_' + languageKey + '.properties'\n");
            for (Item item : ItemMap.get().values()) {
                String string = item.getOriginalName().replace(" ", "") + " = \n";
                bw.write(string);
            }
            bw.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
        return absolutePath.toString();

    }
}
