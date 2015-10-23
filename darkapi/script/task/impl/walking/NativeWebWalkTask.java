package darkapi.script.task.impl.walking;

import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.location.navigation.web.WebVertex;

/**
 * Created by Aiden on 21/10/2015.
 */
public class NativeWebWalkTask extends WebWalkTask {

    private WebVertex destination;

    public NativeWebWalkTask(Coordinate destination) {
        this.destination = Traversal.getDefaultWeb().getVertexNearestTo(destination);
    }

    @Override
    public boolean execute() {
        final WebPath path = Traversal.getDefaultWeb().getPathBuilder().buildTo(destination);
        if (path != null)
            return path.step(true);
        return false;
    }
}
