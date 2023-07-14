module SatisfactoryCalculator {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    requires java.xml;
    requires javafx.web;
    requires java.logging;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires jackson.annotations;

    opens de.manuelclever.satisfactorycalculator;
    opens de.manuelclever.satisfactorycalculator.content;
    opens de.manuelclever.satisfactorycalculator.content.items;
    opens de.manuelclever.satisfactorycalculator.gui;
}
