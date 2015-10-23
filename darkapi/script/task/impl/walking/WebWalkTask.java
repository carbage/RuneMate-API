package darkapi.script.task.impl.walking;

import com.runemate.game.api.hybrid.location.Coordinate;
import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.ChainExecutor;
import darkapi.webwalker.WebWalker;
import darkapi.webwalker.web.Web;
import darkapi.webwalker.web.WebNode;

import java.util.function.Predicate;

/**
 * Created by Valkyr on 12/09/15.
 */
public class WebWalkTask extends ChainableTask {

    private final Coordinate destination;

    public WebWalkTask(Coordinate destination) {
        this.destination = destination;
    }

    public WebWalkTask(Predicate<WebNode> nodeFilter) {
        this(WebWalker.getClosest(nodeFilter).construct());
    }

    public WebWalkTask(String... names) {
        this(webNode -> {
            for (String name : names) if (name.equalsIgnoreCase(webNode.getName())) return true;
            return false;
        });
    }

    public WebWalkTask(int dX, int dY, int dZ) {
        this(new Coordinate(dX, dY, dZ));
    }

    public WebWalkTask(int dX, int dY) {
        this(new Coordinate(dX, dY, 0));
    }

    @Override
    public boolean execute() {
        if (!WebWalker.walk(WebWalker.getClosest(webNode -> webNode.equals(destination)))) {
            log("Could not webwalk to destination, falling back on native webwalker...");
            return ChainExecutor.exec(new NativeWebWalkTask(destination));
        }
        return true;
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return destination != null;
    }
}
