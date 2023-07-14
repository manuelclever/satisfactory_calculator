package de.manuelclever.satisfactorycalculator.content;

import de.manuelclever.satisfactorycalculator.content.items.Edge;
import de.manuelclever.satisfactorycalculator.content.items.ItemMap;

import java.util.*;

/*
    WeightedGraph
 */

public class WeightedGraph {
    //vertices in graphs are what nodes are in trees
    private int vertices;
    //adjacencyList with fixed size
    protected Map<Integer, LinkedList<Edge>> adjacencyMap;

    public WeightedGraph() {
        this.vertices = 0;
        adjacencyMap = new HashMap<>();
    }

    public LinkedList<Edge> get(int key) {
        return adjacencyMap.get(key);
    }

    public Map<Integer, LinkedList<Edge>> getAdjacencyMap() {
        return adjacencyMap;
    }

    public void addEdge(Edge edge) {
        addEdge(edge.getProduct(), edge.getResourceId(), edge.getWeight());
    }

    public void addEdge(int product, int ressource, int weight) {
        //used ItemMap as size, because it should be the same as all items that exists
        this.vertices = ItemMap.size();

        if(adjacencyMap.containsKey(product)) {
            adjacencyMap.get(product).add(new Edge(product, ressource, weight));
        } else {
            adjacencyMap.put(product, new LinkedList<>(
                    Collections.singletonList(new Edge(product, ressource, weight))));
        }
    }

    public void clear() {
        vertices = 0;
        adjacencyMap.clear();
    }

    public int getVertices() {
        return vertices;
    }

    public boolean contains(int id) {
        return adjacencyMap.containsKey(id);
    }

    public void printGraph() {
        for(int id : adjacencyMap.keySet()) {
            LinkedList<Edge> list = adjacencyMap.get(id);

            for(int j = 0; j < list.size(); j++) {
                System.out.println("vertex-" + id + ", " + ItemMap.getItem(id).getName() +  " is connected to " +
                        list.get(j).getResourceId() + " with weight " + list.get(j).getWeight());
            }
        }
    }

//    // The function to do Depth-First-Search traversal
//    public void DFS(int v) {
//        // Mark all the vertices as
//        // not visited(set as
//        // false by default in java)
//        //visited[] is 1 bigger than needed, because [0] is empty
//        boolean visited[] = new boolean[vertices+1];
//
//        DFSUtil(v, visited);
//        //next line
//        System.out.println("");
//    }
//
//    private void DFSUtil(int v, boolean visited[]) {
//        // Mark the current node as visited and print it
//        int currentNode = v;
//
//        visited[currentNode] = true;
//        System.out.print(currentNode + " ");
//
//        // Recurse for all the vertices adjacent to this vertex
//        if(adjacencyMap.get(currentNode) != null) {
//            Iterator<Edge> i = adjacencyMap.get(currentNode).listIterator();
//            while (i.hasNext()) {
//                int n = i.next().getResourceId();
//                if (!visited[n])
//                    DFSUtil(n, visited);
//            }
//        }
//    }
//
//    // The function to do Depth-First-Search traversal
//    public void DFSDoubleVisits(int v) {
//        // Mark all the vertices as
//        // not visited(set as
//        // false by default in java)
//
//        DFSDoubleVisitsUtil(v);
//        //next line
//        System.out.println("");
//    }
//
//    private void DFSDoubleVisitsUtil(int v) {
//        // Mark the current node as visited and print it
//        int currentNode = v;
//
//        System.out.print(currentNode + " ");
//
//        // Recurse for all the vertices adjacent to this vertex
//        if(adjacencyMap.get(currentNode) != null) {
//            Iterator<Edge> i = adjacencyMap.get(currentNode).listIterator();
//            while (i.hasNext()) {
//                int n = i.next().getResourceId();
//                DFSDoubleVisitsUtil(n);
//            }
//        }
//    }
}