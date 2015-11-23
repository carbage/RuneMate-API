package darkapi.script.task.impl.walking;

import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.web.Web;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import darkapi.script.utils.InfoTracker;
import darkapi.script.utils.WebTools;

/**
 * Created by Valkyr on 21/10/2015.
 */
public class NativeWebWalkTask extends WebWalkTask {

    private Coordinate destination;

    public NativeWebWalkTask(Coordinate destination) {
        super(destination);
        this.destination = destination;
    }

    @Override
    public boolean execute() {
        log("Searching for path to " + destination);
        // Find path in default web
        WebPath path = Traversal.getDefaultWeb().getPathBuilder().buildTo(destination);
        if (path == null || path.getVertices().isEmpty())
            // Find path in custom web
            path = WebTools.getWeb().getPathBuilder().buildTo(destination);
        if (path != null && !path.getVertices().isEmpty()) {
            log("Found path (" + path.getVertices().size() + " nodes) stepping...");
            InfoTracker.setPath(path);
            path.step();
            return true;
        }
        log("Could not create path to " + destination);
        return false;
    }
}
