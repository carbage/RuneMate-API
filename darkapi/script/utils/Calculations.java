package darkapi.script.utils;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import darkapi.webwalker.web.WebNode;

/**
 * Created by Valkyr on 18/10/2015.
 */
public class Calculations {


    public static boolean canReach(Locatable locatable) {
        return canReach(PlayerInfo.myPlayer(), locatable);
    }

    private static boolean canReach(Locatable first, Locatable second) {
        return first != null && second != null && first.getPosition().isReachableFrom(second.getPosition());
    }

    public static double distance(Locatable locatable) {
        return distance(PlayerInfo.myPlayer(), locatable);
    }

    public static double distance(Locatable first, Locatable second) {
        return first.distanceTo(second);
    }
}
