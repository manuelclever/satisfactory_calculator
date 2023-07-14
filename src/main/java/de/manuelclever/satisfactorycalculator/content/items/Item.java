package de.manuelclever.satisfactorycalculator.content.items;

import de.manuelclever.satisfactorycalculator.languages.CurrentLanguage;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.LinkedList;
import java.util.MissingResourceException;

/*
    More to the content of an Item can be read in the ItemMap class.
 */

public final class Item implements Comparable<Item> {
    private SimpleStringProperty name;
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty mj;
    private LinkedList<Edge> edges;

    public Item() {
        this.name = new SimpleStringProperty();
        this.id = new SimpleIntegerProperty(0);
        this.mj = new SimpleIntegerProperty(0);
        this.edges = new LinkedList<>();
    }

    public Item(int id, String name, int mj, LinkedList<Edge> edges) {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleIntegerProperty(id);
        this.mj = new SimpleIntegerProperty(mj);
        this.edges = edges;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        //if language is other than english, return the name from the properties list
        //if name is not found, return the english name
        if(CurrentLanguage.getCurrentLocale() != CurrentLanguage.getLocale("en")) {
            try {
                return CurrentLanguage.getItemName(name.get());
            } catch (MissingResourceException e) {
                return name.get();
            }
        }
        return name.get();
    }

    //return the original, not translated, name of the item
    public String getOriginalName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        //if language is other than english, return a new SimpleStringProperty
        //formed out of the name from the properties list
        //if name is not found, return the english SimpleStringProperty
        if(CurrentLanguage.getCurrentLocale() != CurrentLanguage.getLocale("en")) {
            try {
                return new SimpleStringProperty(CurrentLanguage.getItemName(name.get()));
            } catch (MissingResourceException e) {
                return name;
            }
        }
        return name;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setMj(int mj) {
        this.mj.set(mj);
    }

    public int getMj() {
        return mj.get();
    }

    public SimpleIntegerProperty mjProperty() {
        return mj;
    }

    public LinkedList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(LinkedList<Edge> edges) {
        this.edges = edges;
    }

    public void addEdge(int product, int ressource, int weight) {
        edges.add(new Edge(product, ressource, weight));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getId()).append(", ");
        sb.append(getName()).append(", ");
        sb.append(getMj()).append("\n");

        getEdges().forEach((edge) -> sb.append("\t").append(edge.getResourceId()).append(", ").append(edge.getWeight()).append("\n"));

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return id.get();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compareTo(Item o) {
        return this.getName().compareTo(o.getName());
    }


}
