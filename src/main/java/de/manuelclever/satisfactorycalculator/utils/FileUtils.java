package de.manuelclever.satisfactorycalculator.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static boolean isFilenameValid(String file) {
        File f = new File(file);
        try {
            f.getCanonicalPath();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
