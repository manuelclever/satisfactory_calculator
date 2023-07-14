package de.manuelclever.satisfactorycalculator;

import de.manuelclever.satisfactorycalculator.content.items.ItemMap;
import de.manuelclever.satisfactorycalculator.gui.MainWindowController;
import de.manuelclever.satisfactorycalculator.gui.browser.WebEngineThreadPoolExecutor;
import de.manuelclever.satisfactorycalculator.gui.utils.FileMenu;
import de.manuelclever.satisfactorycalculator.settings.Settings;
import de.manuelclever.satisfactorycalculator.settings.SettingsHandler;
import de.manuelclever.satisfactorycalculator.utils.LogGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.logging.Level;

/*
    toDo
        Improve log performance?
        when saving items show warning regarding language of item name
        static or singleton?
        create gui calculator
 */

public class Main extends Application {
    private static MainWindowController mainWindowController;

    @Override
    public void start(Stage primaryStage) throws Exception {
//        System.out.println("java version: "+System.getProperty("java.version"));
//        System.out.println("javafx.version: " + System.getProperty("javafx.version"));

        // load itemList xml
        if(Settings.load()) {
            ItemMap.load(Settings.get(SettingsHandler.SELECTED_RECIPE));
            LogGenerator.log(Level.INFO, LogGenerator.CLASS.MAIN, "Loaded the last selected recipe");
        } else {
            List<String> fileNames = FileMenu.populateFileNames();
            if(fileNames != null) {
                ItemMap.load(fileNames.get(0));
                LogGenerator.log(Level.INFO, LogGenerator.CLASS.MAIN, "Loaded the first recipe in recipe directory");

            } else {
                LogGenerator.log(Level.SEVERE, LogGenerator.CLASS.MAIN, "Couldn't load items, no files found");
                throw new Exception("Couldn't load items, no files");
            }
        }

        // fxml and controller are initialized
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml") );
        Parent root = loader.load();
        mainWindowController = loader.getController();
        primaryStage.setTitle("Satisfactory Calculator");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
        LogGenerator.log(Level.INFO, LogGenerator.CLASS.MAIN, "Primary Stage loaded");

    }

    public static void main(String[] args) {
        //launches the start method
        launch(args);
        //shuts down WebEngineThreadPool
        WebEngineThreadPoolExecutor.get().shutdownNow();
        LogGenerator.log(Level.INFO, LogGenerator.CLASS.MAIN, "WebEngineThreadPoolGenerator shutdown");

//        RecipeGraph.DFS(8);
//        RecipeGraph.DFSDoubleVisits(8);
//
//        Calculator calculator = new Calculator();
//        calculator.addInput(8,4);
//        calculator.addInput(6,4);
//        calculator.addInput(5, 10);
//        calculator.addInput(1, 40);
//
//        WeightedGraph result = calculator.calculate();
//        result.printGraph();
//        ItemMap.print();
    }

    public static MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    @Override
    public void stop() {
        Settings.write();
        LogGenerator.log(Level.INFO, LogGenerator.CLASS.MAIN, "Session infos saved");
    }
}
