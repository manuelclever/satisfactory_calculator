package de.manuelclever.satisfactorycalculator.json_reader;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.manuelclever.satisfactorycalculator.json_reader.raw.Element;

public class Classes {
    @JsonProperty("")
    private Element[] elements;

    public Element[] getItem() {
        return elements;
    }

    public void setItem(Element[] itemDescriptor) {
        this.elements = itemDescriptor;
    }
}
