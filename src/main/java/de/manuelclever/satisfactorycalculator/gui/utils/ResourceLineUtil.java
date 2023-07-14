package de.manuelclever.satisfactorycalculator.gui.utils;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import de.manuelclever.satisfactorycalculator.content.items.ItemMap;
import de.manuelclever.satisfactorycalculator.languages.CurrentLanguage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourceLineUtil {
    //settings
    private final int WIDTH_BTT_1_CHARACTER = 25;
    private final int WIDTH_RESSOURCE_FIELDS = 50;

    //from parent
    private TextField nameField;
    private ToggleButton bttEnableEditMode;
    private Label labelResources;
    private VBox vBoxLabelId;
    private VBox vBoxFieldId;
    private VBox vBoxLabelQuantity;
    private VBox vBoxFieldQuantity;
    private VBox vBoxButton;

    private List<VBox> allVBoxes;

    private Label labelId;
    private TextField txtFieldId;
    private Label labelQuantity;
    private TextField txtFieldQuantity;
    private Button bttRemoveLine;


    public ResourceLineUtil(TextField nameField,
                            ToggleButton bttEnableEditMode, Label labelResources, VBox vBoxLabelId,
                            VBox vBoxFieldId, VBox vBoxLabelQuantity, VBox vBoxFieldQuantity, VBox vBoxButton) {
        this.nameField = nameField;
        this.bttEnableEditMode = bttEnableEditMode;
        this.labelResources = labelResources;
        this.vBoxLabelId = vBoxLabelId;
        this.vBoxFieldId = vBoxFieldId;
        this.vBoxLabelQuantity = vBoxLabelQuantity;
        this.vBoxFieldQuantity = vBoxFieldQuantity;
        this.vBoxButton = vBoxButton;

        allVBoxes = new ArrayList<>(Arrays.asList(
                vBoxLabelId, vBoxFieldId, vBoxLabelQuantity, vBoxFieldQuantity, vBoxButton));
    }

    public void addResourceLine() {
        addResourceLine("", "");
    }

    public void addResourceLine(String id, String quantity) {
        //checks if the given id is different than zero, because the zero edge
        // points to the dummy item and the user shouldn't be able to see that
        if(!id.equals("0")) {
            setLabelAndTextID(id);
            setLabelAndTextQuantity(quantity);
            createBttRemoveLine();

            //checks if edit mode is not active, if so, the field can't be edited and the button disappears
            if (!bttEnableEditMode.isSelected()) {
                txtFieldId.setEditable(false);
                txtFieldQuantity.setEditable(false);
                bttRemoveLine.setVisible(false);
            }
            //the resources label will be invisible if no predecessor exists,
            //so make sure it's visible now
            labelResources.setVisible(true);

        } else {
            //if no predecessor items exists, make ressource label invisible
            labelResources.setVisible(false);
        }
    }

    public void clearRessourceLines() {
        for(VBox vBox : allVBoxes) {
            if(vBox != null) {
                vBox.getChildren().clear();
            }
        }
    }

    private void setLabelAndTextID(String id) {
        //creates and sets the label for the id
        labelId = new Label(CurrentLanguage.getString("idNotFound"));
        labelId.setPrefHeight(nameField.getHeight());
        vBoxLabelId.getChildren().add(labelId);

        //creates and sets the txtField for the id
        txtFieldId = new TextField();
        txtFieldId.setPrefHeight(nameField.getHeight());
        txtFieldId.setPrefWidth(WIDTH_RESSOURCE_FIELDS);
        //set a listener to change the labe of the predecessor item to the name of the inputted id
        txtFieldId.textProperty().addListener((observable, oldValue, newValue) -> {
            //if newValue is not empty and id exists on ItemMap, set Label to name of the item
            if (!newValue.equals("")) {
                int newID = Integer.parseInt(newValue);

                if (newID != 0 && ItemMap.getItem(newID) != null) {
                    labelId.setText(ItemMap.getItem(newID).getName());
                } else {
                    labelId.setText(CurrentLanguage.getString("idNotFound"));
                }
            } else {
                labelId.setText(CurrentLanguage.getString("idNotFound"));
            }


        });
        txtFieldQuantity = new TextField();
        txtFieldId.setText(id);
        vBoxFieldId.getChildren().add(txtFieldId);
    }

    private void setLabelAndTextQuantity(String quantity) {
        //creates and sets the label for the quantity
        labelQuantity = new Label(CurrentLanguage.getString("quantity"));
        labelQuantity.setPrefHeight(nameField.getHeight());
        GridPane.setFillWidth(labelQuantity, true);
        labelQuantity.setMaxWidth(Double.MAX_VALUE);
        labelQuantity.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        labelQuantity.setPadding(new Insets(0, 0, 0, 10));
        vBoxLabelQuantity.getChildren().add(labelQuantity);

        //creates and sets the txtField for the quantity
        txtFieldQuantity = new TextField();
        txtFieldQuantity.setPrefHeight(nameField.getHeight());
        txtFieldQuantity.setText(quantity);
        txtFieldQuantity.setPrefWidth(WIDTH_RESSOURCE_FIELDS);
        vBoxFieldQuantity.getChildren().add(txtFieldQuantity);
    }

    private void createBttRemoveLine() {
        //creates and sets the button to remove the specific predecessorItem
        bttRemoveLine = new Button("-");
        bttRemoveLine.setPrefWidth(WIDTH_BTT_1_CHARACTER);
        bttRemoveLine.setPrefHeight(nameField.getHeight());
        bttRemoveLine.setOnAction((event) -> {
            vBoxLabelId.getChildren().remove(labelId);
            vBoxFieldId.getChildren().remove(txtFieldId);
            vBoxLabelQuantity.getChildren().remove(labelQuantity);
            vBoxFieldQuantity.getChildren().remove(txtFieldQuantity);
            vBoxButton.getChildren().remove(bttRemoveLine);
        });
        vBoxButton.getChildren().add(bttRemoveLine);
    }
}
