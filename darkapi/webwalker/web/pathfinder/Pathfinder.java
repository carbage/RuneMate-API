package darkapi.webwalker.web.pathfinder;

import com.runemate.game.api.hybrid.location.Coordinate;
import darkapi.script.utils.PlayerInfo;
import darkapi.webwalker.web.Web;
import darkapi.webwalker.web.WebNode;

import java.util.ArrayList;

public abstract class Pathfinder implements IPathfinder {
    private Web web;

    public Pathfinder(Web web) {
        this.web = web;
    }

    protected ArrayList<WebNode> findPath(WebNode destination) {
        return findPath(getClosest(web.getNodes(), PlayerInfo.myPosition()));
    }

    protected static WebNode getClosest(ArrayList<WebNode> set, Coordinate p) {
        WebNode v = null;
        for (WebNode vtx : set)
            if (p != null && vtx != null && vtx.getPlane() == p.getPlane())
                if (v == null || vtx.distance(p) < v.distance(p)) {
                    v = vtx;
                }
        return v;
    }

    protected static ArrayList<WebNode> getAdjacent(WebNode node) {
        return node.getEdges();
    }

    protected static double getGScore(WebNode start, WebNode pos) {
        if (pos != null && start != null) {
            double dx = start.getX() - pos.getX();
            double dy = start.getY() - pos.getY();
            return Math.sqrt((dx * dx) + (dy * dy));
        }
        return Double.MAX_VALUE;
    }

    protected static int calcManhattanDistance(WebNode current, WebNode target) {
        int dx = Math.abs(target.getX() - current.getX());
        int dy = Math.abs(target.getY() - current.getY());
        return dx + dy;
    }

    protected Web getWeb() {
        return web;
    }

}
