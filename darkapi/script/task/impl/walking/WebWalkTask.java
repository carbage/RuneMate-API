package darkapi.script.task.impl.walking;

import com.runemate.game.api.hybrid.location.Coordinate;
import darkapi.script.task.ChainableTask;
import darkapi.script.utils.PlayerInfo;
import darkapi.webwalker.WebWalker;
import darkapi.webwalker.web.WebNode;

import java.util.function.Predicate;

/**
 * Created by Valkyr on 12/09/15.
 */
public class WebWalkTask extends ChainableTask {

    private final WebNode destination;

    public WebWalkTask(Coordinate destination) {
        this(webNode -> webNode.getPlane() == destination.getPlane(), destination);
    }
    public WebWalkTask(Predicate<WebNode> nodeFilter) {
        this(nodeFilter, PlayerInfo.myPosition());
    }

    public WebWalkTask(Predicate<WebNode> nodeFilter, Coordinate destination) {
        WebNode found = WebWalker.getClosest(nodeFilter, destination);
        this.destination = found;
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
        if (!WebWalker.walk(destination)) {
            log("Could not webwalk to destination, falling back on native webwalker...");
           // return chain(new NativeWebWalkTask(destination.construct()));
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

    public final WebNode getDestination() {
        return destination;
    }
}
