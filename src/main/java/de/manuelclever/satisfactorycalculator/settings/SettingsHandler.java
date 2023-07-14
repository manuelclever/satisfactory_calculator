package de.manuelclever.satisfactorycalculator.settings;

import de.manuelclever.satisfactorycalculator.content.items.Item;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
    SAX-Event-Handler
 */

public class SettingsHandler extends DefaultHandler {
    //elements in xml
    public static final String LAST_SESSION = "last_session";
    public static final String SELECTED_TAB = "selected_tab";
    public static final String SELECTED_RECIPE = "selected_recipe";

    //boolean if element was spotted
    private boolean last_session = false;
    private boolean selected_tab = false;
    private boolean selected_recipe = false;

    private String elementVal;
    private Item currentItem;

    @Override
    public void startDocument() throws SAXException {

    }

    //a start element(bracket) got found
    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        //which element, set true for type
        switch (qName) {
            case LAST_SESSION:
                last_session = true;
                break;
            case SELECTED_TAB:
                selected_tab = true;
                break;
            case SELECTED_RECIPE:
                selected_recipe = true;

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
        if(selected_tab) {
            Settings.put(SELECTED_TAB, elementVal);
            selected_tab = false;
        } else if(selected_recipe) {
            Settings.put(SELECTED_RECIPE, elementVal);
            selected_recipe = false;
        } else if(last_session) {

            last_session = false;
        }
    }
}
