package de.manuelclever.satisfactorycalculator.gui.graph.utils;

import de.manuelclever.satisfactorycalculator.content.items.Edge;
import de.manuelclever.satisfactorycalculator.content.items.ItemMap;
import javafx.scene.control.Label;

import java.util.List;

public class ItemNode extends Label {
    private int id;
    private List<Edge> edges;

    private double[] connL;
    private double[] connR;

    public ItemNode(int id, List<Edge> edges) {
        this.id = id;
        this.edges = edges;
        this.setText(ItemMap.getItem(id).getName());
    }

    public int getItemId() {
        return id;
    }

    public double[] getConnL() {
        return connL;
    }

    public double[] getConnR() {
        return connR;
    }

    // set connection points at each end of label at half the height
    public void setConns() {
        double width = this.getWidth();
        connL = new double[]{0, this.getHeight() / 2};
        connR = new double[]{width, this.getHeight() / 2};
    }
}
