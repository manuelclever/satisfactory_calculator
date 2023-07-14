package de.manuelclever.satisfactorycalculator.gui;

import de.manuelclever.satisfactorycalculator.calculating.Calculator;
import de.manuelclever.satisfactorycalculator.content.WeightedGraph;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class TabCalculatorController {
//    @FXML ScrollPane paneGraph;
    @FXML BorderPane tabCalculator;
    @FXML Pane graphPane;

    private final int PADDING_X = 100;
    private final int PADDING_Y = 60;

    // create customLine class and add runLater

    public void initialize() {
        Calculator calculator = new Calculator();
        calculator.addInput(8,4);
        calculator.addInput(6,4);
        calculator.addInput(5, 10);
        calculator.addInput(1, 40);

        WeightedGraph result = calculator.calculate();
        result.printGraph();
//        drawGraph(result);
    }

//    public void calculate() {
//        Calculator calculator = new Calculator();
//
//        // calculator.addInput()
//
//        WeightedGraph outputGraph = calculator.calculate();
//
//        drawGraph(outputGraph);
//    }

//    private void drawGraph(WeightedGraph outputGraph) {
//        DrawGraphUtil drawGraphUtil = new DrawGraphUtil();
//
//        int vertexId = Integer.MAX_VALUE;
//        Map<Integer, List<Integer>> posAllocated = new HashMap<>();
//
//        // create node and add to LayerList
//        outputGraph.getAdjacencyMap().entrySet().forEach(
//                entrySet -> fillLayerList(entrySet.getKey(), entrySet.getValue(), drawGraphUtil, outputGraph));
//
//
//
//        // for each node on the output graph
//        for(Map.Entry<Integer, LinkedList<Edge>> entrySet : outputGraph.getAdjacencyMap().entrySet()) {
//            Integer id = entrySet.getKey();
//            LinkedList<Edge> edges = entrySet.getValue();
//
//            // get node
//            ItemNode itemNode = (ItemNode) graphPane.getChildren().get(drawGraphUtil.getKeys().get(id));
//
//            // for each edge
//            for(Edge edge : edges) {
//                if(edge.getResourceId() != 0) {
//                    int resId = edge.getResourceId();
//                    int weight = edge.getWeight();
//
//                    // if resource node hasn't been created yet, create it
//                    ItemNode resNode;
//                    if (!posOfNode.containsKey(resId)) {
//                        resNode = new ItemNode(resId, outputGraph.get(resId));
//                        Interaction.makeDraggable(resNode);
//
//                        graphPane.getChildren().add(resNode);
//                        Integer[] posAdd = new Integer[]{0,
//                                posAllocated.get(0).get(posAllocated.get(0).size() - 1) + 1};
//                        keys.put(resId, panePosition);
//                        panePosition++;
//
//                        resNode.getTransforms().add(
//                                new Translate( 50 + (posAdd[0] * PADDING_Y), 50 + (posAdd[1] * PADDING_X)));
//                        posOfNode.put(id, posAdd);
//                        if(posAllocated.containsKey(posAdd[0])) {
//                            List<Integer> filled = posAllocated.get(posAdd[0]);
//                            filled.add(filled.size());
//                            posAllocated.put(posAdd[0], filled);
//                        } else {
//                            posAllocated.put(posAdd[0], Collections.singletonList(posAdd[1]));
//                        }
//                    } else {
//                        resNode = (ItemNode) graphPane.getChildren().get(keys.get(resId));
//                    }
//
//                    VertexLine vertex = new VertexLine(graphPane, resNode, itemNode);
//
//
//                }
//            }
//        }
//    }
//
//    private void fillLayerList(Integer id, LinkedList<Edge> edges, DrawGraphUtil drawGraphUtil,
//                                   WeightedGraph outputGraph) {
//
//        // if node hasn't been created yet, create it
//        if(!drawGraphUtil.getKeys().containsKey(id)) {
//            ItemNode itemNode = new ItemNode(id, edges);
//            Interaction.makeDraggable(itemNode);
//
//            int layerSection = 0;
//            // if edges point to dummyItem, put node on layer 0
//            if(edges.stream().anyMatch(new ContainsItemZero())) {
//                addToLayer(itemNode, layerSection, drawGraphUtil);
//
//                //if it only has 1 resource item, find that and put on the next layer
//            } else if(edges.size() == 1) {
//                int resId = edges.get(0).getResourceId();
//
//                if(drawGraphUtil.getKeys().containsKey(resId)) {
//                    layerSection = drawGraphUtil.getLayerOfNode().get(resId) + 1;
//                    addToLayer(itemNode, layerSection, drawGraphUtil);
//                } else {
//                    // recursive if resource hasn't been created yet
//                    fillLayerList(resId, outputGraph.get(resId), drawGraphUtil, outputGraph);
//                }
//            } else {
//
//                for (Edge edge : edges) {
//                    int edgeId = edge.getProduct();
//                    if (drawGraphUtil.getLayerOfNode().containsKey(edgeId)) {
//                        int resLayer = drawGraphUtil.getLayerOfNode().get(edgeId);
//                        layerSection = Math.max(resLayer, layerSection);
//                    }
//                }
//                layerSection++;
//                addToLayer(itemNode, layerSection, drawGraphUtil);
//            }
//            graphPane.getChildren().add(itemNode);
//            drawGraphUtil.getKeys().put(id, layerSection);
//
//        }
//    }
//
//    private void addToLayer(ItemNode itemNode, int layerSection, DrawGraphUtil drawGraphUtil) {
//        if(drawGraphUtil.getLayer().get(layerSection) == null ){
//            List<ItemNode> nodes = new ArrayList<>();
//            nodes.add(itemNode);
//            drawGraphUtil.getLayer().put(layerSection, nodes);
//        } else {
//            drawGraphUtil.getLayer().get(layerSection).add(itemNode);
//        }
//        drawGraphUtil.getLayerOfNode().put(itemNode.getItemId(), layerSection);
//    }
}
