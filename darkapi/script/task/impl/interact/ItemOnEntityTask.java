package darkapi.script.task.impl.interact;

import com.runemate.game.api.hybrid.entities.details.Interactable;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import darkapi.script.task.ChainableTask;
import darkapi.script.utils.PlayerInfo;
import darkapi.script.utils.OnymousQueryBuilder;

import java.util.function.Predicate;

/**
 * Created by Valkyr on 12/09/15.
 */
public class ItemOnEntityTask extends ChainableTask {

    private final Predicate<SpriteItem> itemFilter;
    private final Predicate<String> entityFilter;

    public ItemOnEntityTask(String itemName, String entityName) {
        this.itemFilter = item -> item.getDefinition().getName().equalsIgnoreCase(itemName);
        this.entityFilter = entity -> entity.equalsIgnoreCase(entityName);
    }

    public ItemOnEntityTask(Predicate<SpriteItem> itemFilter, Predicate<String> entityFilter) {
        this.itemFilter = itemFilter;
        this.entityFilter = entityFilter;
    }

    @Override
    public boolean execute() {
        SpriteItem item = Inventory.newQuery().filter(itemFilter).results().first();
        Interactable entity = new OnymousQueryBuilder().string(entityFilter).results().first();
        if (item != null && entity != null) {
            SpriteItem selected = Inventory.getSelectedItem();
            if (selected == null || (selected.getDefinition().getName().equals(item.getDefinition().getName()) && item.interact("Use")))
                return entity.interact("Use");
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
