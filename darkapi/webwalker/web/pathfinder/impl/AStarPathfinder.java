package darkapi.webwalker.web.pathfinder.impl;

import darkapi.script.utils.Logger;
import darkapi.webwalker.web.Web;
import darkapi.webwalker.web.WebNode;
import darkapi.webwalker.web.pathfinder.Pathfinder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class AStarPathfinder extends Pathfinder {
    public AStarPathfinder(Web web) {
        super(web);
    }

    @Override
    public ArrayList<WebNode> findPath(WebNode start, WebNode destination) {
        if (start == null || destination == null || start.equals(destination))
            return null;
        ArrayDeque<WebNode> open = new ArrayDeque<>();
        ArrayDeque<WebNode> closed = new ArrayDeque<>();
        start.setFScore(calcManhattanDistance(start, destination));
        start.setPrevious(null);
        open.add(start);
        while (!open.isEmpty()) {
            WebNode u = open.getFirst();
            open.removeFirst();
            closed.add(u);
            if (u.equals(destination)) {
                ArrayList<WebNode> path = new ArrayList<>();
                while (u != null) {
                    path.add(u);
                    u = u.getPrevious();
                }
                Collections.reverse(path);
                return path;
            }
            for (WebNode neighbor : getAdjacent(u)) {
                int fScore = getFScore(start, neighbor, u);
                if (closed.contains(neighbor))
                    continue;
                if (!open.contains(neighbor) || fScore < getGScore(start, neighbor)) {
                    int neighbourFScore = getFScore(start, neighbor, destination);
                    neighbor.setFScore(neighbourFScore);
                    neighbor.setPrevious(u);
                    if (!open.contains(neighbor)) {
                        if (!open.isEmpty() && neighbourFScore < open.getFirst().getFScore()) open.addFirst(neighbor);
                        else open.addLast(neighbor);
                    }
                }
            }
        }
        Logger.log("Could not find path to [" + destination.toString() + "]!");
        return null;
    }

    private int getFScore(WebNode start, WebNode neighbor, WebNode u) {
        return (int) (getGScore(start, u) + calcManhattanDistance(neighbor, u));
    }
}
