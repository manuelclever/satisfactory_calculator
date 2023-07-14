package de.manuelclever.satisfactorycalculator.gui.graph.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawGraphUtil {
    Map<Integer, Integer> keys;
    Map<Integer, List<ItemNode>> layer;
    Map<Integer, Integer> layerOfNode;

    int panePosition;

    public DrawGraphUtil() {
        this.keys = new HashMap<>();
        this.layer = new HashMap<>();
        this.layerOfNode = new HashMap<>();

        this.panePosition = 0;
    }

    public Map<Integer, Integer> getKeys() {
        return keys;
    }

    public Map<Integer, List<ItemNode>> getLayer() {
        return layer;
    }

    public Map<Integer, Integer> getLayerOfNode() {
        return layerOfNode;
    }

    public int getPanePosition() {
        return panePosition;
    }
}
