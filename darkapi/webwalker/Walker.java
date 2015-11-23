package darkapi.webwalker;

import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import darkapi.script.task.impl.ChainExecutor;
import darkapi.script.task.impl.interact.InteractGameObjectTask;
import darkapi.script.task.impl.walking.LocalWalkTask;
import darkapi.script.utils.Calculations;
import darkapi.script.utils.Logger;
import darkapi.script.utils.PlayerInfo;
import darkapi.webwalker.web.WebNode;

import java.util.ArrayList;

public class Walker {

    public boolean walkPath(ArrayList<WebNode> path) {
        return walkPath(path, 2);
    }

    public static boolean walkPath(ArrayList<WebNode> path, int distance) {
        Coordinate myPos = Players.getLocal().getPosition();
        WebNode last = path.get(path.size() - 1);
        WebNode lastValid = lastValidNode(path);
        // Found suitable node?
        if (lastValid != null) {
            // Is player not moving or close to dest?
            if (PlayerInfo.isPlayerIdle() || (!lastValid.equals(last) && Calculations.distance(lastValid.construct()) <= distance)) {
                Logger.log("Found suitable node [" + lastValid.construct() + "]");
                // Next node reachable?
                // Process obstacle
                Logger.log("Checking for obstacles...");
                WebNode obstacleNode = processNextObstacle(path, lastValid);
                if (obstacleNode != null) {
                    Logger.log("Processed obstacle: [" + obstacleNode + "]");
                } else if (Calculations.canReach(lastValid.construct()) && myPos.getPlane() == lastValid.getPlane()) {
                    // Walk
                    Logger.log("Next node is reachable, walking via LocalWalker");
                    return ChainExecutor.exec(new LocalWalkTask(lastValid.construct()));
                }
                Logger.log("Found no walkable nodes!");
                return false;
            } else {
                Logger.log("Player is already moving!");
            }
        } else {
            Logger.log("No reachable nodes found in path!");
            return false;
        }
        Execution.delayUntil(() -> !PlayerInfo.isPlayerIdle());
        return lastValid.equals(last) || Calculations.distance(last.construct()) <= distance;
    }


    private static WebNode lastValidNode(ArrayList<WebNode> path) {
        //WebNode last = path.getWeb(path.size() - 1);
        WebNode next = null;
        for (WebNode node : path) {
            if (node.construct().isValid())
                if (next == null || path.indexOf(node) > path.indexOf(next)) {
                    next = node;
                }
        }
        return next;
    }

    private static WebNode nextReachableNode(ArrayList<WebNode> path) {
        //WebNode last = path.getWeb(path.size() - 1);
        WebNode next = null;
        for (WebNode node : path) {
            if (node.construct().isReachableFrom(PlayerInfo.myPosition()))
                if (next == null || path.indexOf(node) > path.indexOf(next)) {
                    next = node;
                }
        }
        return next;
    }

    private static WebNode processNextObstacle(ArrayList<WebNode> path, WebNode next) {
        WebNode obstacleNode = getNextObstacleIn(path);
        if (obstacleNode != null) {
            Logger.log("Obstacle found!");
            String obstacleAction = getAction(next, obstacleNode);
            if (obstacleAction != null)
                ChainExecutor.exec(new InteractGameObjectTask(obstacleNode.getName(), obstacleAction, obstacleNode.construct()));
            else Logger.log("No valid action found for " + obstacleNode + "!");
        }
        return null;
    }

    private static String getAction(WebNode next, WebNode obstacle) {
        String action = null;
        if (next.getPlane() > PlayerInfo.myPosition().getPlane()) {
            action = "Climb-up";
        } else if (next.getPlane() < PlayerInfo.myPosition().getPlane()) {
            action = "Climb-down";
        } else for (String s : obstacle.getActions())
            return s;
        if (action != null && !action.equals("Close") && !action.equals("Attack")) {
            return action;
        } else {
            return null;
        }
    }

    public static WebNode getNextObstacleIn(ArrayList<WebNode> path) {
        WebNode nextReachable = nextReachableNode(path);
        for (WebNode node : path)
            if (WebWalker.getWeb().getObstacles().contains(node))
                if (nextReachable == null || path.indexOf(node) >= path.indexOf(nextReachable))
                    return node;
        return null;
    }


}