package de.manuelclever.satisfactorycalculator.json_reader.utils;

import java.util.LinkedList;
import java.util.List;

public class KeyGenerator {
    private static KeyGenerator keyGenerator;
    private List<Integer> keys;

    private KeyGenerator() {
        this.keys = new LinkedList<>();
    }

    public static KeyGenerator getInstance() {
        if(keyGenerator == null) {
            keyGenerator = new KeyGenerator();
        }
        return keyGenerator;
    }

    public Integer createNewKey() {
        int key = keys.size();
        keys.add(key);
        return key;
    }
}
