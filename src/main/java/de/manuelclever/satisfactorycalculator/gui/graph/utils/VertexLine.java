package de.manuelclever.satisfactorycalculator.gui.graph.utils;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class VertexLine extends Line {

    public VertexLine(Pane pane, ItemNode startItem, ItemNode endItem) {
        this.setFill(Paint.valueOf("black"));

        // draw line after labels have been drawn,
        // cause label width and height is initialized after being drawn
        Platform.runLater(() -> {
            // set connection points on nodes
            startItem.setConns();
            endItem.setConns();

            // bind lines to the specific connection point (leftConn is start, rightConn is end)
            Interaction.bindLineStart(this, startItem);
            Interaction.bindLineEnd(this, endItem);

            // add line to pane
            pane.getChildren().add(this);
        });
    }
}
