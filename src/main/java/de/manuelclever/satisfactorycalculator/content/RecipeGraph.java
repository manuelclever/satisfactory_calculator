package de.manuelclever.satisfactorycalculator.content;

/*
    Static class for all recipes, saved in a graph. Gets it content solely from the ItemMap.
    Doesn't extend WeightedGraph, because a static instance is preferred.

    To understand closer in which way the items are represented in this graph, refer to
    the Calculator class.
 */

import de.manuelclever.satisfactorycalculator.content.items.ItemMap;

public final class RecipeGraph {

    private static WeightedGraph weightedGraph = new WeightedGraph();

    private RecipeGraph() {
    }

    public static WeightedGraph getGraph() {
        return weightedGraph;
    }

    public static void setGraph(WeightedGraph weightedGraph) {
        RecipeGraph.weightedGraph = weightedGraph;
    }

    public static int getVertices() {
        return weightedGraph.getVertices();
    }

    public static void addEdge(int product, int ressource, int weight) {
        weightedGraph.addEdge(product, ressource, weight);
    }

    //populates graph with content of ItemMap
    public static void refresh() {
        weightedGraph.clear();
        //loop for each item from ItemMap
        ItemMap.get().values().forEach((item) -> {
            //loop for each edge the Item contains
            item.getEdges().forEach((edge) -> {

                //edge gets added
                weightedGraph.addEdge(edge);
            });
        });
    }

    public static void printGraph() {
        weightedGraph.printGraph();
    }

//    public static void DFS(int i) {
//        weightedGraph.DFS(i);
//    }
//
//    public static void DFSDoubleVisits(int i) {
//        weightedGraph.DFSDoubleVisits(i);
//    }
}
