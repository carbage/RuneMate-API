package darkapi.script.task.impl.walking;

import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.region.Region;
import darkapi.script.task.ChainableTask;

/**
 * Created by Valkyr on 12/09/15.
 */
public class LocalWalkTask extends ChainableTask {

    private final Locatable destination;

    public LocalWalkTask(int dX, int dY, int dZ) {
        this(new Coordinate(dX, dY, dZ));
    }

    public LocalWalkTask(int dX, int dY) {
        this(new Coordinate(dX, dY, 0));
    }

    public LocalWalkTask(Locatable destination) {
        this.destination = destination;
    }

    @Override
    public boolean execute() {
        return RegionPath.build(Players.getLocal().getPosition(), destination).step();
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return !Players.getLocal().isMoving() || (Players.getLocal().distanceTo(Traversal.getDestination()) <= 6);
    }
}
