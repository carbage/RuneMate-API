package darkapi.script.utils;

import com.runemate.game.api.hybrid.location.Coordinate;

/**
 * Created by Aiden on 18/10/2015.
 */
public class Banking {

    private static boolean useCustomBank;
    private static Coordinate bankLocation;

    public static void setUseCustomBank(boolean useCustomBank) {
        Banking.useCustomBank = useCustomBank;
    }
    public static boolean useCustomBank() {
        return useCustomBank;
    }

    public static void setBankLocation(Coordinate bankLocation) {
        Banking.bankLocation = bankLocation;
    }

    public static Coordinate getBankLocation() {
        return bankLocation;
    }
}
