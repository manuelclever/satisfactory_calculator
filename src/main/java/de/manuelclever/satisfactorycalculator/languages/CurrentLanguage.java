package de.manuelclever.satisfactorycalculator.languages;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CurrentLanguage {
    private static myLanguageList<Locale> languages = new myLanguageList<>(Arrays.asList(
            new Locale("en"),
            new Locale("de")
    ));
    private static myLanguageList<Locale> wikiLanguages = new myLanguageList<>(Arrays.asList(
            languages.get("en"),
            languages.get("de"),
            new Locale("fr")
    ));
    private static Locale currentLocale = languages.get("en");
    private static ResourceBundle language;
    private static ResourceBundle itemNames;

    static {
        //the changeLanguage method is executed in static brackets, because the getBundle method for itemNames
        //could return an exception which needs to be handled
        changeLanguage(currentLocale);
    }

    private CurrentLanguage() {
    }

    public static myLanguageList<Locale> getLanguages() {
        return languages;
    }

    public static Locale getLocale(String language) {
        for(Locale locale : languages) {
            if(locale.getLanguage().equals(language)) {
                return locale;
            }
        }
        return null;
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static void changeLanguage(Locale locale) {
        ClassLoader loader = null;
        try {
            File file = new File(FileSystems.getDefault().getPath("src", "main", "resources", "properties").toAbsolutePath().toString());
            URL[] urls = {file.toURI().toURL()};
            loader = new URLClassLoader(urls);
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }


        currentLocale = locale;
        language = ResourceBundle.getBundle("languageBundle", currentLocale, loader);
        //tries to set the the itemBundle, if no such bundle is found, set the itemNames to null
        //the resulting errors while be handled by the getItemName method
        //the if brackets are used, because if only one itemNameBundle property file exists,
        //the getBundle method returns this property file for the en locale, which is not the expected behavior
        if(currentLocale != languages.get("en")) {
            try {
                itemNames = ResourceBundle.getBundle("itemNameBundle", currentLocale, loader);
            } catch (MissingResourceException e) {
                itemNames = null;
            }
        } else {
            itemNames = null;
        }
    }

    public static String getString(String key) {
        return language.getString(key);
    }

    public static String getItemName(String key) {
        //tries to get the name of item, if the itemNameBundle doesn't exist (en doesn't exist for example) or if the
        //specified item can't be found in the properties file, return the given argument
        try {
            //replaces whitespaces, because it's saved without them in ItemMap.writeNameProperties()
            return itemNames.getString(key.replace(" ", ""));
        } catch(MissingResourceException | NullPointerException e) {
            return key;
        }
    }

    //if the given local is included in the wiki languages, return true, else false
    public static boolean isWikiLanguage(Locale locale) {
        for(Locale wikiLocale : wikiLanguages) {
            if(locale == wikiLocale) {
                return true;
            }
        }
        return false;
    }

}
