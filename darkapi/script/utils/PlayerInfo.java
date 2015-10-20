package darkapi.script.utils;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.Coordinate;

/**
 * Created by Valkyr on 18/10/2015.
 */
public class PlayerInfo {

    private static Player myPlayer;

    public static boolean isPlayerIdle() {
        if (myPlayer != null) {
            return !isPlayerMoving() && !isPlayerAnimating() && isPlayerInteracting();
        }
        return false;
    }

    public static boolean isPlayerMoving() {
        return myPlayer != null &&  myPlayer.isMoving();
    }

    public static boolean isPlayerAnimating() {
        return myPlayer != null &&  myPlayer.getAnimationId() == -1;
    }

    public static boolean isPlayerInteracting() {
        return myPlayer != null &&  myPlayer.getTarget() != null;
    }

    public static Player myPlayer() {
        return myPlayer;
    }

    public static Coordinate myPosition() {
        return myPlayer != null ? myPlayer.getPosition() : null;
    }

    public static void setPlayer(Player player) {
        myPlayer = player;
    }
}
