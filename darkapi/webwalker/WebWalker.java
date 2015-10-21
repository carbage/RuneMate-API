package darkapi.webwalker;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import darkapi.script.utils.Calculations;
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
    private static final Web web = Web.getInstance();
    private static final AbstractWebLoader loader = new OptimizedWebLoader(web);
    private static final Pathfinder pathfinder = new AStarPathfinder(web);
    private static final Walker walker = new Walker();

    public WebWalker() {
        this(Web.getInstance());
    }

    public WebWalker(Web web) {
        if (!web.isLoaded()) loader.load();
    }

    public WebNode getClosest(String... names) {
        return getClosestNode(PlayerInfo.myPosition(), names);
    }

    public boolean interactWith(WebNode location, String option, LocatableEntity entity) {
        if (location != null && PlayerInfo.isPlayerIdle()) {
            if (Bank.isOpen()) {
                Logger.log("Closing bank.");
                Bank.close();
            }
            Logger.log("Searching for Interactable at " + location);
            if (entity != null && Calculations.canReach(entity)) {
                if (!PlayerInfo.isPlayerInteracting()) {
                    if (entity.interact(option)) {
                        //ABUtils.increaseFatigue();
                        Logger.log("Successfully interacted with [" + entity + "]");
                        //ABUtils.waitInteractionDelay();
                        return true;
                    } else {
                        Logger.log("Failed to interact with [" + entity + "]");
                        if (!entity.isVisible()) {
                            Logger.log("Interactable not visible, walking to " + entity.getPosition());
                            RegionPath.buildTo(entity).step();
                            if (!entity.isVisible()) {
                                Camera.turnTo(entity);
                            }
                        }
                    }
                } else {
                    /*if (ABUtils.getHoverNextInteractable() && ABUtils.BOOL_TRACKER.HOVER_NEXT.get()) {
                        Mouse.move(entity);
                    }*/
                    Logger.log("Not interacting; player is busy!");
                    return false;
                }
            } else {
                WebNode dest = entity != null ? getClosestNode(entity.getPosition()) : location;
                Logger.log("Could not locate Interactable, using WebWalker to reach [" + dest.toString() + "]");
                walk(dest);
            }
        } else {
            Logger.log("Invalid target/destination! target=[" + location + "] destination=[" + location + "]");
        }
        return false;
    }

    public WebNode getClosestWithOption(String... options) {
        return getClosestWithOption(getWeb().getNodes(), PlayerInfo.myPosition(), options);
    }

    public WebNode getClosestWithOption(ArrayList<WebNode> set, Coordinate p, String... options) {
        WebNode v = null;
        for (WebNode vtx : set)
            if (p != null && vtx != null) {
                if (v == null || vtx.distance(p) < v.distance(p)) {
                    if (vtx.hasAllActions(options)) {
                        v = vtx;
                    }
                }
            }
        return v;
    }

    public static WebNode getClosestNode(Coordinate p, String... names) {
        WebNode v = null;
        for (WebNode vtx : web.getNodes())
            if (p != null && vtx != null) {
                if (v == null || vtx.distance(p) < v.distance(p)) {
                    for (String name : names) {
                        if (vtx.getName().equals(name)) {
                            v = vtx;
                        }
                    }
                }
            }
        return v;
    }

    public static WebNode getClosestNode(Coordinate p) {
        return getClosestNode(web.getNodes(), p);
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

    public static ArrayList<WebNode> findPath(WebNode finish) {
        return pathfinder.findPath(getClosestNode(PlayerInfo.myPosition()), finish);
    }

    public ArrayList<WebNode> findPath(WebNode start, WebNode finish) {
        return pathfinder.findPath(start, finish);
    }

    public Walker getWalker() {
        return walker;
    }

    public static Web getWeb() {
        return web;
    }

    public int realDistance(WebNode node, WebNode destination) {
        ArrayList<WebNode> path = pathfinder.findPath(node, destination);
        return path != null ? path.size() : Integer.MAX_VALUE;
    }

    public AbstractWebLoader getLoader() {
        return loader;
    }

    public WebNode getClosest(Predicate<WebNode> nodeFilter) {
        WebNode v = null;
        for (WebNode vtx : getWeb().getNodes())
            if (vtx != null && nodeFilter.test(vtx)) {
                if (v == null || vtx.distance(PlayerInfo.myPosition()) < v.distance(PlayerInfo.myPosition())) {
                    v = vtx;
                }
            }
        return v;
    }
}
