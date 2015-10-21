package darkapi.script.task.impl.interact;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.location.Coordinate;
import darkapi.script.task.ChainableTask;
import darkapi.script.utils.Logger;
import darkapi.script.utils.PlayerInfo;
import darkapi.script.utils.onymous.OnymousQueryBuilder;

import java.util.function.Predicate;

/**
 * Created by Valkyr on 12/09/15.
 */
public class InteractEntityTask extends ChainableTask {

    private final OnymousQueryBuilder queryBuilder = new OnymousQueryBuilder();
    private final Predicate<String> stringFilter;
    private final String action;
    private final Coordinate location;

    public InteractEntityTask(Predicate<String> stringFilter, String action, Coordinate location) {
        this.stringFilter = stringFilter;
        this.action = action;
        this.location = location;
    }

    public InteractEntityTask(Predicate<String> stringFilter, String action) {
        this(stringFilter, action, PlayerInfo.myPosition());
    }

    public InteractEntityTask(String name, String action, Coordinate location) {
        this(eName -> eName.equals(name), action, location);
    }

    public InteractEntityTask(String name, String action) {
        this(name, action, PlayerInfo.myPosition());
    }

    @Override
    public boolean execute() {
        Logger.log("[" + getClass().getSimpleName() + "]: Searching for entity");
        LocatableEntity entity = queryBuilder.string(stringFilter).results().nearestTo(location);
        if (entity != null) {
            Logger.log("[" + getClass().getSimpleName() + "]: Found entity, attempting to interact...");
            return entity.interact(action);
        }
        Logger.log("[" + getClass().getSimpleName() + "]: No entity found!");
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
