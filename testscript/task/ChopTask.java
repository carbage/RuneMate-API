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
public class ChopTask extends ChainableTask {
    @Override
    public boolean execute() {
        log("Chopping trees...");
        return ChainExecutor.exec(new InteractGameObjectTask("Tree", "Chop down", new Coordinate(2888, 3536)));
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return PlayerInfo.isPlayerIdle();
    }
}
