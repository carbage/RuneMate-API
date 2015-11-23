package darkapi.script.utils;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.Coordinate;
import darkapi.gui.javafx.component.impl.FXConsoleComponent;

/**
 * Created by Valkyr on 18/10/2015.
 */
public class Settings {

    // Settings variables
    private static boolean useTeleports;
    private static boolean useAntiban;
    private static boolean useFatigue;
    private static FXConsoleComponent fxConsoleComponent;

    public static boolean getUseTeleports() {
        return useTeleports;
    }

    public static void setUseTeleports(boolean useTeleports) {
        Settings.useTeleports = useTeleports;
    }

    public static boolean getUseAntiban() {
        return useAntiban;
    }

    public static void setUseAntiban(boolean useAntiban) {
        Settings.useAntiban = useAntiban;
    }

    public static boolean getUseFatigue() {
        return useFatigue;
    }

    public static void setUseFatigue(boolean useFatigue) {
        Settings.useFatigue = useFatigue;
    }

    public static void setFxConsoleComponent(FXConsoleComponent fxConsoleComponent) {
        Settings.fxConsoleComponent = fxConsoleComponent;
    }

    public static FXConsoleComponent getFxConsoleComponent() {
        return fxConsoleComponent;
    }
}
