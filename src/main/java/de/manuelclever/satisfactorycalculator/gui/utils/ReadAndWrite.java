package de.manuelclever.satisfactorycalculator.gui.utils;

import de.manuelclever.satisfactorycalculator.gui.TabRecipePageController;
import de.manuelclever.satisfactorycalculator.utils.LogGenerator;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import de.manuelclever.satisfactorycalculator.content.items.Item;
import de.manuelclever.satisfactorycalculator.content.items.ItemMap;
import de.manuelclever.satisfactorycalculator.languages.CurrentLanguage;

import java.util.Optional;
import java.util.logging.Level;

public class ReadAndWrite {
    //parent
    private TabRecipePageController tabRecipePageController;

    //from parent
    private GridPane tabRecipePage;
    private TableView<Item> recipeTable;
    private Label labelFileName;
    private Label labelInfo;

    //status
    private Boolean madeChanges;


    public ReadAndWrite(TabRecipePageController tabRecipePageController, GridPane tabRecipePage,
                        TableView<Item> recipeTable, Label labelFileName, Label labelInfo) {
        this.tabRecipePageController = tabRecipePageController;
        this.tabRecipePage = tabRecipePage;
        this.recipeTable = recipeTable;
        this.labelFileName = labelFileName;
        this.labelInfo = labelInfo;
        LogGenerator.log(Level.INFO, LogGenerator.CLASS.READ_AND_WRITE, "Initialized");
    }

    public void loadFile(String fileName, boolean selectPreviousSelectedItem) {
        if(alertToSaveCurrentFile()) {
            saveFile(ItemMap.getFileSourceName().replace(".xml", ""));
        }

        //ItemMap loads content
        ItemMap.load(fileName);
        //refreshes observableList
        tabRecipePageController.repopulateObservableList();
        labelFileName.setText(ItemMap.getFileSourceName());
        labelInfo.setText(CurrentLanguage.getString("loadedFile") + fileName);

        //if previous selected item should be selected again
        Item oldSelected = tabRecipePageController.getOldSelected();
        System.out.println(oldSelected.getOriginalName());
        if(selectPreviousSelectedItem) {
            recipeTable.getSelectionModel().select(ItemMap.getItem(oldSelected.getId()));
        } else {
            recipeTable.getSelectionModel().selectFirst();
        }
    }

    public void saveFile(String fileName) {
        //ItemMap writes content to file
        ItemMap.write(fileName);
        labelInfo.setText(CurrentLanguage.getString("savedFile") + fileName);

        madeChanges = false;
    }


    public void saveFileAndReload(String fileName) {
        //ItemMap writes content to file
        ItemMap.write(fileName);
        ItemMap.load(fileName);
        labelFileName.setText(ItemMap.getFileSourceName());
        labelInfo.setText(CurrentLanguage.getString("savedFile") + fileName);

        madeChanges = false;
    }

    private Boolean alertToSaveCurrentFile() {
        //if the current file was changed, shown an alert asking the user if the changes should be saved
        if(ItemMap.madeChanges()) {
            Alert alert = new AlertError(tabRecipePage, CurrentLanguage.getString("fileNotSavedTitle"),
                    CurrentLanguage.getString("fileNotSaved"),
                    false, ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        }
        return false;
    }
}
