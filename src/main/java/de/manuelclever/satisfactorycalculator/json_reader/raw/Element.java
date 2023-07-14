package de.manuelclever.satisfactorycalculator.json_reader.raw;

import de.manuelclever.satisfactorycalculator.content.items.Edge;
import de.manuelclever.satisfactorycalculator.languages.CurrentLanguage;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Element implements Comparable<Element> {
    private SimpleIntegerProperty id;

    private SimpleStringProperty className;
    private SimpleStringProperty mDisplayName;
    private SimpleStringProperty mDescription;
    
    //FGRecipe
    private SimpleStringProperty fullName;
    private Map<Integer, Integer> mIngredients;
    private SimpleStringProperty mProduct;
    private SimpleDoubleProperty mManufacturingMenuPriority;
    private SimpleDoubleProperty mManufactoringDuration;
    private SimpleDoubleProperty mManualManufacturingMultiplier;
    private SimpleStringProperty mProducedIn;
    private SimpleDoubleProperty mVariablePowerConsumptionConstant;
    private SimpleDoubleProperty mVariablePowerConsumptionFactor;

    private LinkedList<Edge> edges;

    public Element() {
        this.id = new SimpleIntegerProperty();
        this.className = new SimpleStringProperty();
        this.mDisplayName = new SimpleStringProperty();
        this.mDescription = new SimpleStringProperty();

        this.fullName = new SimpleStringProperty();
        this.mIngredients = new HashMap<>();
        this.mProduct = new SimpleStringProperty();
        this.mManufacturingMenuPriority = new SimpleDoubleProperty();
        this.mManufactoringDuration = new SimpleDoubleProperty();
        this.mManualManufacturingMultiplier = new SimpleDoubleProperty();
        this.mProducedIn = new SimpleStringProperty();
        this.mVariablePowerConsumptionConstant = new SimpleDoubleProperty();
        this.mVariablePowerConsumptionFactor = new SimpleDoubleProperty();
        this.edges = new LinkedList<>();
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getClassName() {
        return className.get();
    }

    public String getClassNameOnlyName() {
        Pattern pattern = Pattern.compile("[^\\W_]+\\w(\\w+)[_][C]");
        Matcher matcher = pattern.matcher(className.get());

        if(matcher.find()) {
            return matcher.group(1);
        }
        throw new NullPointerException();
    }

    public SimpleStringProperty classNameProperty() {
        return className;
    }

    public void setClassName(String className) {
        this.className.set(className);
    }

    public SimpleStringProperty mDisplayNameProperty() {
        return mDisplayName;
    }

    public String getmDisplayName() {
        //if language is other than english, return the name from the properties list
        //if name is not found, return the english name
        if(CurrentLanguage.getCurrentLocale() != CurrentLanguage.getLocale("en")) {
            try {
                return CurrentLanguage.getItemName(mDisplayName.get());
            } catch (MissingResourceException e) {
                return mDisplayName.get();
            }
        }
        return mDisplayName.get();
    }

    public String getOriginalDisplayName() {
        return mDisplayName.get();
    }

    public void setmDisplayName(String mDisplayName) {
        this.mDisplayName.set(mDisplayName);
    }

    public String getmDescription() {
        return mDescription.get();
    }

    public SimpleStringProperty mDescriptionProperty() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription.set(mDescription);
    }

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public Map<Integer, Integer> getmIngredients() {
        return mIngredients;
    }

    public void setmIngredients(Map<Integer, Integer> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public String getmProduct() {
        return mProduct.get();
    }

    public SimpleStringProperty mProductProperty() {
        return mProduct;
    }

    public void setmProduct(String mProduct) {
        this.mProduct.set(mProduct);
    }

    public double getmManufacturingMenuPriority() {
        return mManufacturingMenuPriority.get();
    }

    public SimpleDoubleProperty mManufacturingMenuPriorityProperty() {
        return mManufacturingMenuPriority;
    }

    public void setmManufacturingMenuPriority(double mManufacturingMenuPriority) {
        this.mManufacturingMenuPriority.set(mManufacturingMenuPriority);
    }

    public double getmManufactoringDuration() {
        return mManufactoringDuration.get();
    }

    public SimpleDoubleProperty mManufactoringDurationProperty() {
        return mManufactoringDuration;
    }

    public void setmManufactoringDuration(double mManufactoringDuration) {
        this.mManufactoringDuration.set(mManufactoringDuration);
    }

    public double getmManualManufacturingMultiplier() {
        return mManualManufacturingMultiplier.get();
    }

    public SimpleDoubleProperty mManualManufacturingMultiplierProperty() {
        return mManualManufacturingMultiplier;
    }

    public void setmManualManufacturingMultiplier(double mManualManufacturingMultiplier) {
        this.mManualManufacturingMultiplier.set(mManualManufacturingMultiplier);
    }

    public String getmProducedIn() {
        return mProducedIn.get();
    }

    public SimpleStringProperty mProducedInProperty() {
        return mProducedIn;
    }

    public void setmProducedIn(String mProducedIn) {
        this.mProducedIn.set(mProducedIn);
    }

    public double getmVariablePowerConsumptionConstant() {
        return mVariablePowerConsumptionConstant.get();
    }

    public SimpleDoubleProperty mVariablePowerConsumptionConstantProperty() {
        return mVariablePowerConsumptionConstant;
    }

    public void setmVariablePowerConsumptionConstant(double mVariablePowerConsumptionConstant) {
        this.mVariablePowerConsumptionConstant.set(mVariablePowerConsumptionConstant);
    }

    public double getmVariablePowerConsumptionFactor() {
        return mVariablePowerConsumptionFactor.get();
    }

    public SimpleDoubleProperty mVariablePowerConsumptionFactorProperty() {
        return mVariablePowerConsumptionFactor;
    }

    public void setmVariablePowerConsumptionFactor(double mVariablePowerConsumptionFactor) {
        this.mVariablePowerConsumptionFactor.set(mVariablePowerConsumptionFactor);
    }

    public LinkedList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(LinkedList<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getId()).append(", ");
        sb.append(getmDisplayName()).append(", ");

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
    public int compareTo(Element o) {
        return this.getmDisplayName().compareTo(o.getmDisplayName());
    }
}
