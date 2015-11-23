package darkapi.gui.javafx.component.impl;

import darkapi.script.DarkScript;
 import darkapi.gui.javafx.component.FXGUIComponent;
 import darkapi.gui.javafx.component.util.FXList;
import javafx.application.Platform;

import java.sql.Timestamp;

public class FXConsoleComponent extends FXGUIComponent {

    private final FXList<String> debugList;

    public FXConsoleComponent(DarkScript script) {
        super(script, "Debug Console");
        debugList = new FXList<>();
        addComponents(debugList);

    }


    public void log(String s) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                debugList.getItems().add("[" + getTimeStamp() + "]:" + s);
                debugList.scrollTo(debugList.getItems().size() - 1);
            }
        });
    }

    public String getTimeStamp() {
        return new Timestamp(new java.util.Date().getTime()).toString();
    }
}
