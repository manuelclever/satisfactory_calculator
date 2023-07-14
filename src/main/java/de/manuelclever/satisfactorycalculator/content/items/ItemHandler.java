package de.manuelclever.satisfactorycalculator.content.items;

import de.manuelclever.satisfactorycalculator.languages.CurrentLanguage;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
    SAX-Event-Handler
 */

public class ItemHandler extends DefaultHandler {
    //elements in xml
    public static final String ITEMS = "items";
    public static final String ITEM = "item";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String MJ = "mj";
    public static final String IN = "in";
    public static final String READ_ONLY = "read_only";

    //boolean if element was spotted
    private boolean item = false;
    private boolean id = false;
    private boolean name = false;
    private boolean mj = false;
    private boolean in = false;
    private boolean readOnly = false;

    private boolean isEn = true;

    private String elementVal;
    private Item currentItem;

    @Override
    public void startDocument() throws SAXException {
        if(!CurrentLanguage.getCurrentLocale().equals(CurrentLanguage.getLocale("en"))) {
            isEn = false;
        }
    }

    //a start element(bracket) got found
    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        //which element, set true for type
        switch (qName) {
            case READ_ONLY:
                readOnly = true;
                break;
            case ITEM:
                currentItem = new Item();
                item = true;
                break;
            case ID:
                id = true;
                break;
            case NAME:
                name = true;
                break;
            case MJ:
                mj = true;
                break;
            case IN:
                in = true;
                break;
        }
    }

    //the value between start and end element
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementVal = new String(ch, start, length);
    }

    //an end element has been found
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //check which element, then set the current item
        //if item element got closed put item in static ItemMap class
        if(id) {
            currentItem.setId(Integer.parseInt(elementVal));
            id = false;
        } else if(name) {
            //set name
            currentItem.setName(elementVal);
            name = false;
        } else if(mj) {
            currentItem.setMj(Integer.parseInt(elementVal));
            mj = false;
        } else if(in) {
            //adds edges to (recipe-) graph
            //splits in in id and count, which then gets added together with the product id
            //off the current item
            String[] element = elementVal.split(",");
            int ressource = Integer.parseInt(element[0]);
            int weight = Integer.parseInt(element[1]);
            currentItem.addEdge(currentItem.getId(), ressource, weight);
            in = false;
        } else if(readOnly) {
            ItemMap.setReadOnly(Boolean.parseBoolean(elementVal));
            readOnly = false;
        } else if(item) {
            //after content of each item got saved in currentItem and edges, the ItemMap gets populated
            ItemMap.put(currentItem);
        }
    }
}