package darkapi.gui.javafx.component.impl;

import darkapi.script.DarkScript;
 import darkapi.gui.javafx.component.FXGUIComponent;
 import darkapi.gui.javafx.component.util.FXList;
import darkapi.script.utils.InfoTracker;
import darkapi.script.utils.Settings;
import javafx.application.Platform;


public class FXInfoTracker extends FXGUIComponent {


    private final FXList<String> listView = new FXList<>();

    public FXInfoTracker(DarkScript script) {
        super(script, "Info Tracker");
        final Thread listUpdater = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        listView.getItems().clear();
                        for (String text : InfoTracker.getReturnables().keySet()) {
                            listView.getItems().add(text + InfoTracker.getReturnables().get(text));
                        }
                    });
                    listView.refresh();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        listUpdater.start();

        addComponents("Script Stats", listView);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                addComponents("Debug Console", Settings.getFxConsoleComponent());
            }
        });
    }

}
