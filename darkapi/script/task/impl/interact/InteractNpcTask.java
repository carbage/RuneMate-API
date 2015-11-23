package darkapi.script.task.impl.interact;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.Npcs;
import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.ChainExecutor;
import darkapi.script.task.impl.conditional.WaitForConditionTask;
import darkapi.script.task.impl.walking.NativeWebWalkTask;
import darkapi.script.utils.Logger;
import darkapi.script.utils.OnymousQueryBuilder;
import darkapi.script.utils.PlayerInfo;

import java.util.function.Predicate;

/**
 * Created by Valkyr on 22/10/2015.
 */
public class InteractNpcTask extends ChainableTask {

    private final OnymousQueryBuilder queryBuilder = new OnymousQueryBuilder();
    private final Predicate<Npc> predicate;
    private final String action;
    private final Coordinate location;

    public InteractNpcTask(Predicate<Npc> predicate, String action, Coordinate location) {
        this.predicate = predicate;
        this.action = action;
        this.location = location;
    }

    public InteractNpcTask(Predicate<Npc> predicate, String action) {
        this(predicate, action, PlayerInfo.myPosition());
    }

    public InteractNpcTask(String name, String action, Coordinate location) {
        this(npc -> npc.getName().equals(name), action, location);
    }

    public InteractNpcTask(String name, String action) {
        this(name, action, PlayerInfo.myPosition());
    }

    @Override
    public boolean execute() {
        log("Searching for entity");
        LocatableEntity entity = Npcs.newQuery().filter(predicate).results().nearestTo(PlayerInfo.myPosition());
        if (entity != null) {
            log("Found entity, attempting to interact...");
            if (entity.interact(action)) {
                chain(new WaitForConditionTask(() -> !PlayerInfo.isPlayerIdle()));
                return true;
            } else {
                log("Could not interact with entity!");
                if(entity.getPosition().distanceTo(PlayerInfo.myPosition()) < 16) {
                    log("Entity is on minimap, operating camera...");
                    Camera.turnTo(entity);
                } else {
                    log("Entity is not on minimap, attempting to walk...");
                    RegionPath.buildTo(entity).step(true);
                }
            }
            return false;
        }
        log("No entity found!");
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