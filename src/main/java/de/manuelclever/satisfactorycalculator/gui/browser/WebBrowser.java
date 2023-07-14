package de.manuelclever.satisfactorycalculator.gui.browser;

import de.manuelclever.satisfactorycalculator.utils.LogGenerator;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import de.manuelclever.satisfactorycalculator.content.items.Item;
import de.manuelclever.satisfactorycalculator.content.items.ItemMap;
import de.manuelclever.satisfactorycalculator.languages.CurrentLanguage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;

public class WebBrowser {
    //wiki site
    private String wikiP1 = "https://satisfactory.fandom.com/";
    private String wikiP2 = "wiki/";

    private WebEngine webEngine;
    //userAgent to make sure website loads as a desktop version,
    //else small possibility that the mobile version loads
    private String userAgent = "mozilla/5.0 (windows nt 10.0; win64; x64; rv:87.0) gecko/20100101 firefox/87.0";
    private ArrayBlockingQueue<Runnable> webEngineQueue;
    private ThreadPoolExecutor threadPoolExecutor;

    private TableView<Item> recipeTable;




    public WebBrowser(TableView<Item> recipeTable, WebView webView) {
        this.recipeTable = recipeTable;
        webEngine = webView.getEngine();
        webEngine.setUserAgent(userAgent);
        setListenerWebsiteChange(recipeTable);

        LogGenerator.log(Level.INFO, LogGenerator.CLASS.BROWSER, "Initialized");
    }

    private void setListenerWebsiteChange(TableView<Item> recipeTable) {
        webEngine.locationProperty().addListener(event -> {
            String currentWebsite = webEngine.getLocation();

            if (currentWebsite.contains(wikiP1)) {
                String[] urlSplit = currentWebsite.split("/");
                String itemNameInUrl = urlSplit[urlSplit.length - 1];
                Boolean isWikiLanguage = CurrentLanguage.isWikiLanguage(CurrentLanguage.getCurrentLocale());

                selectItemOnTableIfNotSameAsCurrentWebsite(itemNameInUrl, isWikiLanguage);

            } else {
                //denies user access to sites other than the wiki
                //executes the loading of the page in a ThreadPoolExecutor
                //this is done, so the user can't overload the GUI Thread queue via the runLater method
                WebEngineThreadPoolExecutor.get().execute(new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater(() -> webEngine.load(wikiP1 + wikiP2));
//                        URL url = this.getClass().getResource("/com/satisfactorycalculator/other/style.html");
//                        Platform.runLater(() -> webEngine.load(url.toString()));
                        return null;
                    }
                });

                LogGenerator.log(Level.INFO, LogGenerator.CLASS.BROWSER, "Denied access to external site");
            }
        });
    }

    private void selectItemOnTableIfNotSameAsCurrentWebsite(String itemNameInUrl, Boolean isWikiLanguage) {
        //if the currently selected language is not supported on the wiki,
        //use the original (english) name of the item and show the english version of the wiki.
        //for that use the getOriginalName of the Item, which returns not the translated, but the english name
        if(isWikiLanguage) {
            selectItem(itemNameInUrl);
            LogGenerator.log(Level.INFO, LogGenerator.CLASS.BROWSER, "Wiki language: en");
        } else {
           selectItemOriginalName(itemNameInUrl);
            LogGenerator.log(Level.INFO, LogGenerator.CLASS.BROWSER, "Wiki language: " + CurrentLanguage.getCurrentLocale());
        }
    }

    private void selectItem(String itemNameInUrl) {
        String nameCurrentSelection = recipeTable.getSelectionModel().getSelectedItem().
                getName().replace(" ", "_");

        if (!nameCurrentSelection.equals(itemNameInUrl)) {
            Item item = ItemMap.getItem(itemNameInUrl.replace("_", " "));
            if (item != null) {
                recipeTable.getSelectionModel().select(item);
            }
        }
    }

    private void selectItemOriginalName(String itemNameInUrl) {
        String nameCurrentSelection = recipeTable.getSelectionModel().getSelectedItem().
                getOriginalName().replace(" ", "_");

        if (!nameCurrentSelection.equals(itemNameInUrl)) {
            Item item = ItemMap.getOriginalItem(itemNameInUrl.replace("_", " "));
            if (item != null) {
                recipeTable.getSelectionModel().select(item);
            }
        }
    }

    public void reactToSelectionChange(Item oldSelected, Item newItem) {
        //because the selection got refreshed, the queue of the threadPoolExecutor gets cleared
        //the old queued task are not needed anymore
        WebEngineThreadPoolExecutor.getQueue().clear();
        //only reloads website if oldSelected is different from newItem and if it's not an empty new item
        if(!newItem.equals(oldSelected) && newItem.getName() != null) {
            //if the selection gets refreshed, the website has to be reloaded as well
            //same sa in initialization, the runLater method is run inside a ThreadPoolExecutor to
            //deny the user an overload of the GUI Thread queue
            changeWebsiteAccordingToSelectedItemAndCurrentLangauge(newItem);
        }
    }

    private void changeWebsiteAccordingToSelectedItemAndCurrentLangauge(Item newItem) {
        WebEngineThreadPoolExecutor.get().execute(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //if the currentLanguage is not english and the wiki supports this language,
                //create the url-part for the currentLanguage
                //if no url-part was created, the english wiki version is loaded
                String language = "";
                if (CurrentLanguage.getCurrentLocale() == CurrentLanguage.getLocale("de")) {
                    language = "de/";
                } else if(CurrentLanguage.getCurrentLocale() == CurrentLanguage.getLocale("fr")) {
                    language = "fr/";
                }
                String itemName = CurrentLanguage.getItemName(newItem.getName()).replace(" ", "_");
                String webSite = wikiP1 + language + wikiP2 + itemName;

                Platform.runLater(() -> webEngine.load(webSite));
                return null;
            }
        });
    }
}
