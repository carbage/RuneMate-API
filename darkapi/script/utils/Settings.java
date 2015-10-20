package darkapi.script.utils;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.Coordinate;

/**
 * Created by Valkyr on 18/10/2015.
 */
public class Settings {

    // Settings variables
    private static boolean useBanking;
    private static boolean useTeleports;
    private static boolean useAntiban;
    private static boolean useFatigue;

    public static boolean getUseBanking() {
        return useBanking;
    }

    public static void setUseBanking(boolean useBanking) {
        Settings.useBanking = useBanking;
    }

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

}
