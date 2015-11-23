package testscript.task;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.basic.ViewportPath;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import darkapi.script.DarkScript;
import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.ChainExecutor;
import darkapi.script.task.impl.interact.InteractGameObjectTask;
import darkapi.script.utils.Logger;
import darkapi.script.utils.PlayerInfo;

/**
 * Created by Valkyr on 20/10/2015.
 */
public class ChopTask extends InteractGameObjectTask {

    public ChopTask() {
        super("Tree", "Chop down", new Coordinate(3198, 3217));
    }

    @Override
    public boolean canExecute() {
        return PlayerInfo.isPlayerIdle();
    }
}
