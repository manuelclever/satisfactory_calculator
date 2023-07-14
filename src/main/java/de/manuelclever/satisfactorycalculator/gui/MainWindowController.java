package de.manuelclever.satisfactorycalculator.gui;

import de.manuelclever.satisfactorycalculator.utils.LogGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import de.manuelclever.satisfactorycalculator.content.items.ItemMap;
import de.manuelclever.satisfactorycalculator.languages.CurrentLanguage;
import de.manuelclever.satisfactorycalculator.settings.Settings;
import de.manuelclever.satisfactorycalculator.settings.SettingsHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Locale;
import java.util.logging.Level;

public class MainWindowController {
    public GridPane tabRecipePage;
    public BorderPane tabCalculator;
    @FXML AnchorPane mainAnchorPane;

    // inject tabs
    @FXML private TabPane tabPane;
    // Inject tab content
    @FXML private Tab tabRecipe;
    // Inject controller
    @FXML private TabRecipePageController tabRecipePageController;

    @FXML MenuButton menuLanguage;



    public void initialize() {
        initializeLanguageButton();
        saveWhenTabRecipeDeselected();

        if(Settings.wereLoaded()) {
            tabPane.getSelectionModel().select(Integer.parseInt(Settings.get(SettingsHandler.SELECTED_TAB)));
            LogGenerator.log(Level.INFO, LogGenerator.CLASS.MAIN_WINDOW, "Loaded last session");
        } else {
            tabPane.getSelectionModel().select(0);
            LogGenerator.log(Level.INFO, LogGenerator.CLASS.MAIN_WINDOW, "Couldn't find last session, started a new one.");

        }
        LogGenerator.log(Level.INFO, LogGenerator.CLASS.MAIN_WINDOW, "Initialized");
    }



/*  initializeLanguageButton    */
    public void initializeLanguageButton() {
        //create menuItems for the menuButton, source is the language list in CurrentLanguage
        CurrentLanguage.getLanguages().forEach((locale) -> {
            //creates name and image for menuItem
            MenuItem newMenuItem = new MenuItem(locale.getLanguage());
            newMenuItem.setGraphic(returnImageView(locale));
            setOnLanguageChange(newMenuItem);

            //add the menuItems to the menuButton
            menuLanguage.getItems().add(newMenuItem);
        });

        //set the currentLanguage on the menuButton
        menuLanguage.setText(CurrentLanguage.getCurrentLocale().getLanguage());
        menuLanguage.setGraphic(returnImageView(CurrentLanguage.getCurrentLocale()));
    }

    private MenuItem setOnLanguageChange(MenuItem newMenuItem) {
        //set what happens when language is changed
        newMenuItem.setOnAction((event) -> {
            //change the current language
            CurrentLanguage.changeLanguage(CurrentLanguage.getLanguages().get(newMenuItem.getText()));
            //set the name and image of the menuButton
            menuLanguage.setText(newMenuItem.getText());
            menuLanguage.setGraphic(returnImageView(CurrentLanguage.getCurrentLocale()));

            //clear the recipeTable and reinitialize the recipeTab
            tabRecipePageController.getFileMenu().refreshSubMenus();
            tabRecipePageController.getFileMenu().initialize();
            tabRecipePageController.getReadAndWrite().loadFile(
                    ItemMap.getFileSourceName().replace(".xml", ""), true);
        });
        return newMenuItem;
    }

/*  returnImageView */
    private ImageView returnImageView(Locale locale) {
        //creates a fileInputStream from the location of the imageFile
        //is in a try-ressource, so the stream gets closed afterwards
        try (FileInputStream fileInputStream = new FileInputStream(
                FileSystems.getDefault().getPath("src", "main", "resources", "images")
                        .toAbsolutePath().toString() + File.separator + locale.getLanguage() + ".png")){
            //path of image, image are named after Locale.getLanguage
            ImageView imView = new ImageView(new Image(fileInputStream));
            imView.setFitHeight(20);
            imView.setFitWidth(20);
            return imView;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



/*  saveWhenTabRecipeDeselected */
    private void saveWhenTabRecipeDeselected() {
        //if tabRecipe isn't selected anymore, save all changes made to it
        tabRecipe.setOnSelectionChanged(event -> {
            if(!tabRecipe.isSelected()) {
                tabRecipePageController.getReadAndWrite().saveFile(ItemMap.getFileSourceName().replace(".xml", ""));
            }
        });
    }



/*  getters */
    public int getCurrentTabPaneIndex() {
        return tabPane.getSelectionModel().getSelectedIndex();
    }
}
