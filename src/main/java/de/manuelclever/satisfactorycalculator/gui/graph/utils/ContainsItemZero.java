package de.manuelclever.satisfactorycalculator.gui.graph.utils;

import de.manuelclever.satisfactorycalculator.content.items.Edge;

import java.util.function.Predicate;

public class ContainsItemZero implements Predicate<Edge> {
    @Override
    public boolean test(Edge edge) {
        return edge.getResourceId() == 0;
    }
}
