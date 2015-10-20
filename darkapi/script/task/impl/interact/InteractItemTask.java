package darkapi.script.task.impl.interact;

import com.runemate.game.api.hybrid.entities.Item;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.util.Filter;
import darkapi.script.task.ChainableTask;

import java.util.function.Predicate;

/**
 * Created by Valkyr on 12/09/15.
 */
public class InteractItemTask extends ChainableTask {
    private final Predicate<SpriteItem> itemFilter;
    private final String action;

    public InteractItemTask(Predicate<SpriteItem> itemFilter, String action) {
        this.itemFilter = itemFilter;
        this.action = action;
    }

    public InteractItemTask(String itemName, String action) {
        this(spriteItem -> spriteItem.getDefinition().getName().equalsIgnoreCase(itemName), action);
    }

    @Override
    public boolean execute() {
        SpriteItem item = Inventory.newQuery().filter(itemFilter).results().first();
        return item == null || item.interact(action);
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return true;
    }
}
