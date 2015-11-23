package darkapi.script.utils;

import com.runemate.game.api.hybrid.location.Coordinate;

/**
 * Created by Valkyr on 18/10/2015.
 */
public class Banking {

    private static boolean useBanking;

    private static Coordinate bankLocation;

    public static boolean getUseBanking() {
        return useBanking;
    }

    public static void setUseBanking(boolean useBanking) {
        Banking.useBanking = useBanking;
    }

    public static void setBankLocation(Coordinate bankLocation) {
        Banking.bankLocation = bankLocation;
    }

    public static Coordinate getBankLocation() {
        return bankLocation == null ? (bankLocation = PlayerInfo.myPosition()) : bankLocation;
    }
}
