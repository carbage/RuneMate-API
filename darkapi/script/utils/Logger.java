package darkapi.script.utils;

import darkapi.gui.javafx.component.impl.FXConsoleComponent;

/**
 * Created by Valkyr on 18/10/2015.
 */
public class Logger {

    public static void log(Object obj) {
        String out = obj.toString();
        FXConsoleComponent console = Settings.getFxConsoleComponent();
        if (console != null) console.log(out);
        System.out.println(out);
    }
}
