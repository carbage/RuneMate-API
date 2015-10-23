package darkapi.webwalker;

import com.runemate.game.api.hybrid.location.Coordinate;
import darkapi.script.utils.Logger;
import darkapi.script.utils.PlayerInfo;
import darkapi.webwalker.web.Web;
import darkapi.webwalker.web.WebNode;
import darkapi.webwalker.web.loader.AbstractWebLoader;
import darkapi.webwalker.web.loader.impl.OptimizedWebLoader;
import darkapi.webwalker.web.pathfinder.Pathfinder;
import darkapi.webwalker.web.pathfinder.impl.AStarPathfinder;

import java.util.ArrayList;
import java.util.function.Predicate;

public class WebWalker {
    private static Web web = new Web();
    private static final AbstractWebLoader loader = new OptimizedWebLoader(web);
    private static final Pathfinder pathfinder = new AStarPathfinder(web);
    private static final Walker walker = new Walker();

    public static boolean walk(WebNode v) {
        if (v != null) {
            Logger.log("Finding path to [" + v.toString() + "]");
            ArrayList<WebNode> p1 = findPath(v);
            walkPath(p1);
        }
        return false;
    }

    public static boolean walkPath(ArrayList<WebNode> path) {
        return path != null && !path.isEmpty() && walker.walkPath(path);
    }

    public static ArrayList<WebNode> findPath(WebNode start, WebNode finish) {
        return pathfinder.findPath(start, finish);
    }

    public static ArrayList<WebNode> findPath(WebNode finish) {
        return findPath(getClosest(webNode -> true), finish);
    }

    public static Web getWeb() {
        return web.isLoaded() ? web : (web = loader.load());
    }

    public AbstractWebLoader getLoader() {
        return loader;
    }

    public static WebNode getClosest(Predicate<WebNode> nodeFilter, Coordinate coordinate) {
        WebNode v = null;
        for (WebNode vtx : getWeb().getNodes())
            if (vtx != null && nodeFilter.test(vtx)) {
                if (v == null || vtx.distance(coordinate) < v.distance(coordinate)) {
                    v = vtx;
                }
            }
        return v;
    }

    public static WebNode getClosest(Predicate<WebNode> nodeFilter) {
        return getClosest(nodeFilter, PlayerInfo.myPosition());
    }

    public static WebNode getClosestNode(ArrayList<WebNode> set, Coordinate p) {
        WebNode v = null;
        for (WebNode vtx : set)
            if (p != null && vtx != null && p.getPlane() == vtx.getPlane()) {
                if (v == null || vtx.distance(p) < v.distance(p)) {
                    v = vtx;
                }
            }
        return v;
    }
}
