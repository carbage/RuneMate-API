package darkapi.script.task.impl.interact;

import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.queries.InterfaceComponentQueryBuilder;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.Filter;
import darkapi.script.task.ChainableTask;

import java.util.function.Predicate;

/**
 * Created by Valkyr on 12/09/15.
 */
public class InteractWidgetTask extends ChainableTask {

    private final Predicate<InterfaceComponent> widgetFilter;
    private final String action;

    public InteractWidgetTask(Predicate<InterfaceComponent> widgetFilter, String action) {
        super();
        this.widgetFilter = widgetFilter;
        this.action = action;
    }

    @Override
    public boolean execute() {
        InterfaceComponent widget = new InterfaceComponentQueryBuilder().filter(widgetFilter).results().first();
        if (widget != null) {
            return widget.interact(action);
        }
        return false;
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return true;
    }
}
