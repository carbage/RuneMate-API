package darkapi.script.task.impl.interact;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import darkapi.script.task.ChainableTask;
import darkapi.script.utils.PlayerInfo;

import java.util.Random;
import java.util.function.Predicate;

/**
 * Created by Valkyr on 12/09/15.
 */
public class ItemOnItemTask extends ChainableTask {

    private final Predicate<SpriteItem> firstItemFilter;
    private final Predicate<SpriteItem> secondItemFilter;

    public ItemOnItemTask(boolean allowRandomize, String firstItemName, String secondItemName) {
        this(allowRandomize,
                item -> item.getDefinition().getName().equalsIgnoreCase(firstItemName),
                entity -> entity.getDefinition().getName().equalsIgnoreCase(secondItemName));
    }

    public ItemOnItemTask(boolean allowRandomize, Predicate<SpriteItem> firstItemFilter, Predicate<SpriteItem> secondItemFilter) {
        boolean randomize = !allowRandomize || new Random().nextBoolean();
        this.firstItemFilter = randomize ? firstItemFilter : secondItemFilter;
        this.secondItemFilter = randomize ? secondItemFilter : firstItemFilter;
    }

    @Override
    public boolean execute() {
        SpriteItem firstItem = Inventory.newQuery().filter(firstItemFilter).results().first();
        SpriteItem secondItem = Inventory.newQuery().filter(secondItemFilter).results().first();
        if (firstItem != null && secondItem != null) {
            if (firstItem.interact("Use")) return secondItem.interact("Use");
        }
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
