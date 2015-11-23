package darkapi.script.task.impl.interact;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.ChainExecutor;
import darkapi.script.task.impl.conditional.WaitForConditionTask;
import darkapi.script.task.impl.walking.NativeWebWalkTask;
import darkapi.script.task.impl.walking.WebWalkTask;
import darkapi.script.utils.Logger;
import darkapi.script.utils.OnymousQueryBuilder;
import darkapi.script.utils.PlayerInfo;

import java.util.function.Predicate;

/**
 * Created by Valkyr on 22/10/2015.
 */
public class InteractGameObjectTask extends ChainableTask {

    private final OnymousQueryBuilder queryBuilder = new OnymousQueryBuilder();
    private final Predicate<GameObject> predicate;
    private final String action;
    private final Coordinate location;

    public InteractGameObjectTask(Predicate<GameObject> predicate, String action, Coordinate location) {
        this.predicate = predicate;
        this.action = action;
        this.location = location;
    }

    public InteractGameObjectTask(Predicate<GameObject> predicate, String action) {
        this(predicate, action, PlayerInfo.myPosition());
    }

    public InteractGameObjectTask(String name, String action, Coordinate location) {
        this(gameObject -> gameObject != null && gameObject.getDefinition() != null && gameObject.getDefinition().getName() != null && gameObject.getDefinition().getName().equals(name), action, location);
    }

    public InteractGameObjectTask(String name, String action) {
        this(name, action, PlayerInfo.myPosition());
    }

    public InteractGameObjectTask(GameObject object, String action) {
        this(entity -> entity.equals(object), action);
    }

    @Override
    public boolean execute() {
        log("Searching for entity");
        LocatableEntity entity = GameObjects.newQuery().filter(predicate).results().nearestTo(PlayerInfo.myPosition());
        if (entity != null) {
            log("Found entity, attempting to interact...");
            if (entity.interact(action)) {
                chain(new WaitForConditionTask(() -> !PlayerInfo.isPlayerIdle()));
                return true;
            } else {
                log("Could not interact with entity!");
                if (entity.getPosition().distanceTo(PlayerInfo.myPosition()) < 16) {
                    log("Entity is on minimap, operating camera...");
                    Camera.turnTo(entity);
                } else {
                    log("Entity is not on minimap, attempting to walk...");
                    RegionPath path = RegionPath.buildTo(entity);
                    if (path != null) {
                        log("Local path found, stepping...");
                        path.step(true);
                    }
                }
            }
            return false;
        } else if (!location.isReachable()) {
            log("Destination unreachable, attempting to webwalk...;");
            chain(new WebWalkTask(location));
        }
        log("Waiting for entity to spawn...");
        return false;
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return PlayerInfo.isPlayerIdle();
    }
}