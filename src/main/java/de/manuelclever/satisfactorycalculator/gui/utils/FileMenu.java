package de.manuelclever.satisfactorycalculator.gui.utils;

import de.manuelclever.satisfactorycalculator.gui.TabRecipePageController;
import de.manuelclever.satisfactorycalculator.utils.LogGenerator;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.util.Duration;
import de.manuelclever.satisfactorycalculator.content.items.ItemMap;
import de.manuelclever.satisfactorycalculator.languages.CurrentLanguage;
import de.manuelclever.satisfactorycalculator.utils.FileUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class FileMenu {
    //parent
    private TabRecipePageController tabRecipePageController;

    //from parent
    private MenuButton fileMenuBtt;
    private ToggleButton bttEnableEditMode;
    private Label labelInfo;

    //buttons
    private Menu subMenuLoad = new Menu();
    private MenuItem menuItemSave = new MenuItem();
    private MenuItem menuItemSaveAs = new MenuItem();
    private Menu subMenuDelete = new Menu();
    private CustomMenuItem subMenuCreateTemplate = new CustomMenuItem();

    private static List<String> fileNames = new ArrayList<>();

    private static Path saveFilePath = FileSystems.getDefault().getPath("src", "main", "resources", "recipes");




    public FileMenu(TabRecipePageController tabRecipePageController, ToggleButton bttEnableEditMode,
                    MenuButton fileMenuBtt, Label labelInfo) {
        this.tabRecipePageController = tabRecipePageController;
        this.fileMenuBtt = fileMenuBtt;
        this.bttEnableEditMode = bttEnableEditMode;
        this.labelInfo = labelInfo;
    }



/*  initialize  */
    public void initialize() {
        nameButtons();

        setActionSave(menuItemSave);
        setActionSaveAs(menuItemSaveAs);
        setActionCreateTemplate();
        setTooltipCreateTemplate();

        fileMenuBtt.getItems().setAll(subMenuLoad, menuItemSave, menuItemSaveAs, subMenuDelete, subMenuCreateTemplate);

        LogGenerator.log(Level.INFO, LogGenerator.CLASS.FILE_MENU, "Initialized");
    }

    private void nameButtons() {
        //naming buttons
        bttEnableEditMode.setText(CurrentLanguage.getString("bttToggleEdit"));
        fileMenuBtt.setText(CurrentLanguage.getString("bttFiles"));
        subMenuLoad.setText(CurrentLanguage.getString("bttLoad"));
        menuItemSave.setText(CurrentLanguage.getString("bttSave"));
        menuItemSaveAs.setText(CurrentLanguage.getString("bttSaveAs"));
        subMenuDelete.setText(CurrentLanguage.getString("bttDelete"));
        Label labelSubMenuCreateTemplate = new Label(CurrentLanguage.getString("bttCreateTemplate"));
        subMenuCreateTemplate.setContent(labelSubMenuCreateTemplate);
    }

    private void setActionSave(MenuItem menuItemSave) {
        //setAction for menuItemSave
        menuItemSave.setOnAction(event ->
                tabRecipePageController.getReadAndWrite()
                        .saveFile(ItemMap.getFileSourceName().replace(".xml", "")));

    }

    private void setActionSaveAs(MenuItem menuItemSaveAs) {
        //setAction for menuItemSaveAs
        menuItemSaveAs.setOnAction(event -> {
            //creates textInput dialog
            TextInputDialog dialog = textInputDialog();

            while(true) {
                Optional<String> resultInput = dialog.showAndWait();
                if (resultInput.isPresent()) {
                    String input = resultInput.get().trim();

                    //if name is valid
                    if(FileUtils.isFilenameValid(input)) {

                        //if it's not a duplicate, write file, else show error
                        if (notADuplicate(input)) {
                            tabRecipePageController.getReadAndWrite().saveFileAndReload(input);
                            refreshSubMenus();
                            LogGenerator.log(Level.INFO, LogGenerator.CLASS.FILE_MENU, "Saved file: " + input);
                        } else {
                            //if confirmation, overwrite file, else stay in outer loop and show input dialog again
                            Optional<ButtonType> result = fileExistsConfirmationAlert().showAndWait();

                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                tabRecipePageController.getReadAndWrite().saveFileAndReload(input);
                                refreshSubMenus();
                                LogGenerator.log(Level.INFO, LogGenerator.CLASS.FILE_MENU, "Saved file: " + input);
                            }
                        }
                        break;
                    } else { //if name is not valid
                        nameNotAcceptedInformationAlert().showAndWait();
                    }
                } else {
                    break;
                }
            }
        });
    }

    private boolean notADuplicate(String input) {
        for (String file : fileNames) {
            //checks if inputted String already exists as save file, if so break loop
            if (file.equals(input)) {
                return false;
            }
        }
        return true;
    }

    private TextInputDialog textInputDialog() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(CurrentLanguage.getString("enterFileTitle"));
        dialog.setHeaderText(null);
        dialog.setContentText(CurrentLanguage.getString("enterFile"));

        return dialog;
    }

    private Alert fileExistsConfirmationAlert()  {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(CurrentLanguage.getString("fileExistsTitle"));
        alert.setHeaderText(null);
        alert.setContentText(CurrentLanguage.getString("fileExists"));

        return alert;
    }

    private Alert nameNotAcceptedInformationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(CurrentLanguage.getString("itemNotSaved"));
        alert.setHeaderText(null);
        alert.setContentText(CurrentLanguage.getString("nameNotAccepted"));

        return alert;
    }

    private void setActionCreateTemplate() {
        subMenuCreateTemplate.setOnAction(event -> {
            String absolutePath = ItemMap.createEmptyLanguageNameTemplate();
            labelInfo.setText(CurrentLanguage.getString("createdTemplate") + absolutePath);
        });
    }

    private void setTooltipCreateTemplate() {
        Tooltip tooltip = new Tooltip(CurrentLanguage.getString("tooltipCreateTemplate"));
        tooltip.setShowDuration(Duration.seconds(8));
        tooltip.setHideDelay(Duration.seconds(2));
        tooltip.setMaxWidth(250);
        tooltip.setWrapText(true);

        Tooltip.install(subMenuCreateTemplate.getContent(), tooltip);
    }



/*  refreshSubMenus */
    public void refreshSubMenus() {
        clearMenus();
        populateFileNames();
        forEachFileAddToSubMenu();
    }

    private void clearMenus() {
        subMenuLoad.getItems().clear();
        subMenuDelete.getItems().clear();
        fileNames.clear();
    }

    public static List<String> populateFileNames() {
        //search directory for all files ending with ".xml" and populate fileNames
        DirectoryStream.Filter<Path> filter = Files::isRegularFile;
        try (DirectoryStream<Path> contents = Files.newDirectoryStream(saveFilePath, filter)) {

            for(Path file: contents) {
                if(file.getFileName().toString().endsWith(".xml")) {
                    fileNames.add(file.getFileName().toString().replace(".xml", ""));
                }
            }
        } catch(IOException | DirectoryIteratorException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return fileNames;
    }

    private void forEachFileAddToSubMenu()  {
        fileNames.forEach(fileName -> {
            MenuItem menuItemLoad = new MenuItem(fileName);
            MenuItem menuItemDelete = new MenuItem(fileName);

            //add file to subMenus
            subMenuLoad.getItems().add(menuItemLoad);
            subMenuDelete.getItems().add(menuItemDelete);

            setActionLoad(menuItemLoad, fileName);
            setActionDelete(menuItemDelete, fileName);
        });
    }

    private void setActionLoad(MenuItem menuItemLoad, String fileName) {
        menuItemLoad.setOnAction(event -> Platform.runLater(() ->
                tabRecipePageController.getReadAndWrite().loadFile(fileName, false)));
    }

    private void setActionDelete(MenuItem menuItemDelete, String fileName) {
        menuItemDelete.setOnAction(event -> {
            //if file is not the currently loaded file continue, else show an error
            if(!(fileName + ".xml").equals(ItemMap.getFileSourceName())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(CurrentLanguage.getString("deleteThisTitle"));
                alert.setHeaderText(null);
                alert.setContentText(CurrentLanguage.getString("deleteThis") + "\n\n\t" + fileName + ".xml");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    //try deleting, if file can't be found show error
                    try {
                        Files.delete(FileSystems.getDefault().getPath("src", "main", "resources", "recipes", fileName + ".xml"));
                        refreshSubMenus();
                        labelInfo.setText(CurrentLanguage.getString("deletedFile") + fileName + ".xml");
                    } catch (IOException e) {
                        Alert alertError = new Alert(Alert.AlertType.ERROR);
                        alertError.setTitle(CurrentLanguage.getString("fileNotFoundTitle"));
                        alertError.setHeaderText(null);
                        alertError.setContentText(CurrentLanguage.getString("fileNotFound"));

                        alertError.showAndWait();
                        e.printStackTrace();
                    }
                } //else do nothing
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(CurrentLanguage.getString("cantDeleteTitle"));
                alert.setHeaderText(null);
                alert.setContentText(CurrentLanguage.getString("cantDelete"));

                alert.showAndWait();
            }
        });
    }
}
