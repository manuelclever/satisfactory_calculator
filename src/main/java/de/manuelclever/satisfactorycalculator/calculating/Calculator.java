package de.manuelclever.satisfactorycalculator.calculating;

import de.manuelclever.satisfactorycalculator.content.RecipeGraph;
import de.manuelclever.satisfactorycalculator.content.WeightedGraph;
import de.manuelclever.satisfactorycalculator.content.items.Edge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/*
    This class contains a list with all items which are to be calculated
    and outputs the result in form of a graph

    The input is saved in a Map. Key is the ID, value is the quantity.

    The output is saved as a weightedGraph, which contains a map:

    Map<ID, LinkedList<Edge(product, ressourceProduct, weight)

    In contrary to the RecipeGraph, this outputGraph contains the quantity of the product as weight.
    Which means the weight doesn't show how many ressource products are needed to produce one product,
    but all the inputted products.

    Example (the characters are just for easier recognition, they are replaced by the ids of the product):
    D needs 2 As, 3 Bs and 2 Cs:        -   in the recipeGraph/ItemMap this is saved in three edges for D:

            A,2                             D, A, 2
        D   B,3                             D, B, 3
            C,2                             D, C, 2

    4 times D would need following resources: - The output graph would show the following three edges for D:

            A,8(4x2)                        D, A, 4
       4xD  B,12(4x3)                       D, B, 4
            C,8(4x2)                        D, C, 4

    The quantity for A, B and C can be found in their own LinkedList<Edge>.
    Because all edges now have the same weight, only the weight of the one edge in the
    LinkedList (the first edge) will be used to determine the needed amount.

    This is done this way, because the result is calculated from endProduct to ressourceProduct.
    So when D refers to A as ressource, A now knows it needs 4 times the amount saved in the recipeGraph,
    so 4 x 2.

    Primary resources have one edge only, which contains the id of the primary ressource and points to the
    dummy item with a weight of zero. The dummy item exists, because the way the Depth-First-Search is structured
    the item on the lowest end of the linkages won't be recognised.

 */
public final class Calculator {
    Map<Integer, Integer> input;
    WeightedGraph outputGraph;

    public Calculator() {
        this.input = new HashMap<>();
    }

    public void addInput(int id, int c) {
        input.put(id, c);
    }

    public void removeInput(int id) {
        input.remove(id);
    }

    public WeightedGraph calculate() {
        outputGraph = new WeightedGraph();

        for(Map.Entry<Integer, Integer> entry : input.entrySet()) {
            int id = entry.getKey();
            int c = entry.getValue();

            DFSCalculation(id, c);
        }

        return outputGraph;
    }

    // Depth-First-Search traversal
    public void DFSCalculation(int id, int c) {
        // Mark all the vertices as not visited(set as false by default in java)

        DFSCalculationUtil(id, c);
        //next line
        System.out.println("");
    }

    private void DFSCalculationUtil(int id, int c) {
        // Recurse for all the vertices adjacent to this vertex

        //checks if product has resources, if not, the method ends
        LinkedList<Edge> listForIterator = RecipeGraph.getGraph().get(id);
        if(listForIterator != null) {
            Iterator<Edge> i = listForIterator.listIterator();

            while (i.hasNext()) {
                Edge nextEdge = i.next();

                //if edge is already on output graph
                if (outputGraph.contains(id) && outputGraph.get(id).contains(
                        new Edge(nextEdge.getProduct(), nextEdge.getResourceId(), c))) {

                    //get linkedList and get the edge, change the weight on edge and lastly put again on linkedList
                    LinkedList<Edge> list = outputGraph.get(id);
                    int index = list.indexOf(new Edge(nextEdge.getProduct(), nextEdge.getResourceId(), c));
                    Edge edge = list.get(index);
                    edge.setWeight(edge.getWeight() + c);

                } else {
                    outputGraph.addEdge(nextEdge.getProduct(), nextEdge.getResourceId(), c);
                }

                int n = nextEdge.getResourceId();
                int multiplier = nextEdge.getWeight() * c;
                DFSCalculationUtil(n, multiplier);
            }
        }
    }

}
