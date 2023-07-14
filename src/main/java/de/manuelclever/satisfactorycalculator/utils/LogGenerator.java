package de.manuelclever.satisfactorycalculator.utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Date;
import java.util.logging.*;

public class LogGenerator {
    private static Logger logger = Logger.getLogger(LogGenerator.class.getName());
    public static final String ABSOLUTE_PATH = FileSystems.getDefault()
            .getPath("src", "main", "resources", "log.log").toAbsolutePath().toString();
    public enum CLASS {
        MAIN,
        MAIN_WINDOW,
        TAB_RECIPE,
        BROWSER,
        READ_AND_WRITE,
        FILE_MENU
    }

    static {
        FileHandler fileHandler;

        try {
            fileHandler = new FileHandler(ABSOLUTE_PATH, true);

            fileHandler.setFormatter(new SimpleFormatter() {
                private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

                @Override
                public String formatMessage(LogRecord record) {
                    return String.format(format,
                            new Date(record.getMillis()),
                            record.getLevel().getLocalizedName(),
                            record.getMessage()
                    );
                }
            });

            logger.addHandler(fileHandler);
        } catch(IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        logger.setUseParentHandlers(false);
    }

    private LogGenerator() {}

    public static void log(Level level, CLASS ENUM, String string) {
        String classOfLog = "DEFAULT";

        switch (ENUM) {
            case MAIN:
                classOfLog = "MAIN";
                break;
            case MAIN_WINDOW:
                classOfLog = "MAIN WINDOW";
                break;
            case TAB_RECIPE:
                classOfLog = "TAB RECIPE";
                break;
            case BROWSER:
                classOfLog = "BROWSER";
                break;
            case READ_AND_WRITE:
                classOfLog = "READ AND WRITE";
                break;
            case FILE_MENU:
                classOfLog = "FILE MENU";
                break;
        }

        logger.log(level, classOfLog + ": " + string);
    }

}
