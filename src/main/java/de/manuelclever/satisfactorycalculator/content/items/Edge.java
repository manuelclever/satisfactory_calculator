package de.manuelclever.satisfactorycalculator.content.items;

import javafx.beans.property.SimpleIntegerProperty;

/*
    More to the content of an Edge can be read in the ItemMap class.
 */

public class Edge {
    private SimpleIntegerProperty product;
    private SimpleIntegerProperty resource;
    private SimpleIntegerProperty weight;

    public Edge(int product, int resource, int weight) {
        this.product = new SimpleIntegerProperty(product);
        this.resource = new SimpleIntegerProperty(resource);
        this.weight = new SimpleIntegerProperty(weight);
    }

    public int getProduct() {
        return product.get();
    }

    public SimpleIntegerProperty productProperty() {
        return product;
    }

    public int getResourceId() {
        return resource.get();
    }

    public void setResource(int i) {
        resource.set(i);
    }

    public SimpleIntegerProperty resourceProperty() {
        return resource;
    }

    public int getWeight() {
        return weight.get();
    }

    public SimpleIntegerProperty weightProperty() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight.set(weight);
    }

    //has hashCode and equals to make sure, that weight doesn't matter when comparing edges
    @Override
    public int hashCode() {
        return product.get() * 72 + resource.get() * 14;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().equals(this.getClass())) {
            return ((Edge) obj).getProduct() == product.get() && ((Edge) obj).getResourceId() == resource.get();
        }
        return false;
    }
}
