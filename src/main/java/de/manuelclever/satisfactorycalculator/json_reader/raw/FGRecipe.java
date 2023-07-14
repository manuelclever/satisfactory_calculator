package de.manuelclever.satisfactorycalculator.json_reader.raw;

import java.util.HashMap;
import java.util.Map;

public class FGRecipe extends Element {
    private Map<String, Integer> rawIngredientList;

    public FGRecipe() {
        super();
        this.rawIngredientList = new HashMap<>();
    }

    public void convertToElement(Element element) {
        element.setFullName(this.getFullName());
        element.setmIngredients(this.getmIngredients());
        element.setmProduct(this.getmProduct());
        element.setmManufacturingMenuPriority(this.getmManufacturingMenuPriority());
        element.setmManufactoringDuration(this.getmManufactoringDuration());
        element.setmManualManufacturingMultiplier(this.getmManualManufacturingMultiplier());
        element.setmProducedIn(this.getmProducedIn());
        element.setmVariablePowerConsumptionConstant(this.getmVariablePowerConsumptionConstant());
        element.setmVariablePowerConsumptionConstant(this.getmVariablePowerConsumptionFactor());

        element.setEdges(this.getEdges());
    }
}
