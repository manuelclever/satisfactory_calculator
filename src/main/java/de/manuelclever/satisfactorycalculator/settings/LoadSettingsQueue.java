package de.manuelclever.satisfactorycalculator.settings;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class LoadSettingsQueue {
    private static List<Task> queue = new ArrayList<>();;

    private LoadSettingsQueue() {
    }

    public static void add(Task task) {
        queue.add(task);
    }

    public static void execute() {
        for(Task task : queue) {
            task.run();
        }
    }
}


