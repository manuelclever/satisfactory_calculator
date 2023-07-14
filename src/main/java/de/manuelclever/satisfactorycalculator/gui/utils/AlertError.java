package de.manuelclever.satisfactorycalculator.gui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

public class AlertError extends Alert {

    public AlertError( Pane parent, String header, String content, boolean show, ButtonType... butt) {
        super(AlertType.ERROR, content, butt);

        setHeaderText(header);
        initOwner(parent.getScene().getWindow());

        if(show) {
            showAndWait();
        }
    }
}
