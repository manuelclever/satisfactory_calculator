package de.manuelclever.satisfactorycalculator.gui.graph.utils;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class Interaction {


    public static Node makeDraggable(final Node node) {
        final DragContext dragContext = new DragContext();

        node.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            // remember mouse cursor coordinates and node position
            dragContext.mouseX = mouseEvent.getX();
            dragContext.mouseY = mouseEvent.getY();
            });

        node.addEventFilter(MouseDragEvent.MOUSE_DRAGGED, mouseEvent -> {
            // shift node from its initial position by delta calculated from mouse cursor movement
            node.setTranslateX(node.getTranslateX()
                + mouseEvent.getX()
                - dragContext.mouseX);
            node.setTranslateY(node.getTranslateY()
                + mouseEvent.getY()
                - dragContext.mouseY);

            });

        node.addEventFilter(MouseEvent.MOUSE_ENTERED, mouseEvent -> node.setCursor(Cursor.HAND));

        return node;

    }

    public static void bindLineStart(Line line, ItemNode node) {
        line.setStartX(node.getLocalToParentTransform().getTx() + node.getConnR()[0]);
        line.setStartY(node.getLocalToParentTransform().getTy() + node.getConnR()[1]);

        node.translateXProperty().addListener(event -> {
            line.setStartX(node.getLocalToParentTransform().getTx() + node.getConnR()[0]);
        });

        node.translateYProperty().addListener(event -> {
            line.setStartY(node.getLocalToParentTransform().getTy() + node.getConnL()[1]);
        });
    }

    public static void bindLineEnd(Line line, ItemNode node) {
        line.setEndX(node.getLocalToParentTransform().getTx() + node.getConnL()[0]);
        line.setEndY(node.getLocalToParentTransform().getTy() + node.getConnL()[1]);

        node.translateXProperty().addListener(event -> {
            line.setEndX(node.getLocalToParentTransform().getTx() + node.getConnL()[0]);
        });

        node.translateYProperty().addListener(event -> {
            line.setEndY(node.getLocalToParentTransform().getTy() + node.getConnL()[1]);
        });

    }
}
