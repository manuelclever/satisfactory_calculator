package de.manuelclever.satisfactorycalculator.gui.browser;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//this method is static, so the ThreadPoolExecutor can be shutdown by Main after the TabRecipeController has ben closed

public class WebEngineThreadPoolExecutor {
    //creates an array for the queued webEngines tasks
    private static ArrayBlockingQueue webEngineQueue = new ArrayBlockingQueue<>(1);
    //initializes threadPoolExecutor of size 1, queued tasks wait 30s before being discharged
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
                                                        30, TimeUnit.SECONDS, webEngineQueue);

    private WebEngineThreadPoolExecutor() {
    }

    public static ThreadPoolExecutor get() {
        return threadPoolExecutor;
    }

    public static ArrayBlockingQueue getQueue() {
        return webEngineQueue;
    }
}
