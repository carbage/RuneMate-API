package darkapi.webwalker;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.queries.GameObjectQueryBuilder;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import darkapi.script.utils.Calculations;
import darkapi.script.utils.Logger;
import darkapi.script.utils.PlayerInfo;
import darkapi.webwalker.web.WebNode;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Walker {

    public boolean walkPath(ArrayList<WebNode> path) {
        return walkPath(path, 2);
    }

    private boolean walkPath(ArrayList<WebNode> path, int distance) {
        Coordinate myPos = Players.getLocal().getPosition();
        WebNode last = path.get(path.size() - 1);
        WebNode next = nextCoordinate(path, 10);
        if (PlayerInfo.isPlayerIdle() || (!next.equals(last) && Calculations.distance(next.construct()) <= distance)) {
            if (next != null) {
                Logger.log("Found suitable node [" + next.construct() + "]");
                if (Calculations.canReach(next.construct()) && PlayerInfo.myPosition().getPlane() == next.getPlane()) {
                    Logger.log("Next node is reachable, walking via LocalWalker");
                    RegionPath.buildTo(next.construct()).step();
                } else {
                    Logger.log("Next node is unreachable, checking for obstacles.");
                    WebNode obstacleNode = noObstacleBlocking(next, path);
                    if (obstacleNode != null) {
                        Logger.log("Processed obstacle: [" + obstacleNode + "]");
                    } else {
                        Logger.log("No valid obstacle found!"); // Yay!
                    }
                }
                Logger.log("Found no walkable nodes!");
                return false;
            } else {
                Logger.log("Unable to determine next node!");
                return false;
            }
        }
        Execution.delayUntil(() -> !PlayerInfo.isPlayerIdle());
        return Calculations.distance(last.construct()) <= distance;
    }


    private WebNode nextCoordinate(ArrayList<WebNode> path, int skipDist) {
        //WebNode last = path.get(path.size() - 1);
        WebNode next = null;
        for (WebNode node : path) {
            if (Calculations.canReach(node.construct())) {
                if (next == null || path.indexOf(next) < path.indexOf(node)) {
                    next = node;
                }
            }
        }
        return next;
    }

    private WebNode noObstacleBlocking(WebNode next, ArrayList<WebNode> path) {
        GameObject obstacleEntity = null;
        Logger.log("Searching for obstacle nodes between [" + next.toString() + "]");
        WebNode obstacleNode = getNextObstacleIn(path, next);
        if (obstacleNode != null) {
            Logger.log("Searching for obstacle entity for [" + obstacleNode.toString() + "]");
            obstacleEntity = getObstacleEntity(path, obstacleNode, next);
        }
        if (obstacleNode == null) {
            Logger.log("No obstacle nodes found in path!");
            return null;
        }
        // Handle interaction
        if (obstacleEntity != null) {
            Logger.log("Found obstacle [" + obstacleEntity.getDefinition().getName() + "], searching for action");
            // Get right-click option
            for (String action : obstacleEntity.getDefinition().getActions()) {
                if (action != null && !action.equals("null")) {
                    Logger.log("Found action [" + action + "] for [" + obstacleEntity.getDefinition().getName() + "], parsing " +
                            "action");
                    if (processObstacle(action, obstacleEntity, next))
                        return obstacleNode;
                }
            }
        } else {
            Logger.log("No valid obstacle entities found!");
            return null;
        }
        return null;
    }

    private GameObject getObstacleEntity(ArrayList<WebNode> path, WebNode obstacleNode, WebNode next) {
        return new GameObjectQueryBuilder().filter(new Predicate<GameObject>() {
            @Override
            public boolean test(GameObject gameObject) {
                return false;
            }
        }).results().nearestTo(PlayerInfo.myPosition());
    }

    private boolean verify(ArrayList<WebNode> path, WebNode next, LocatableEntity
            currentLocatableEntity, LocatableEntity entity) {
        int currentLocatableEntityScore = obstacleScore(currentLocatableEntity, next);
        int entityScore = obstacleScore(entity, next);
        if (entityScore > 0 && (currentLocatableEntity == null || entityScore < currentLocatableEntityScore)) {
            WebNode currentLocatableEntityNode = currentLocatableEntity != null ?
                    WebWalker.getClosestNode(path, currentLocatableEntity.getPosition()) : null;
            WebNode entityNode = WebWalker.getClosestNode(path, entity.getPosition());
            return currentLocatableEntityNode == null || path.indexOf(currentLocatableEntityNode) < path.indexOf(entityNode);
        }
        return false;
    }

    private int obstacleScore(LocatableEntity entity, WebNode next) {
        if (entity == null || next == null) return -1;
        return next.distance(PlayerInfo.myPosition()) - next.distance(entity.getPosition());
    }

    private boolean processObstacle(String action, GameObject obstacleLocatableEntity, WebNode next) {
        if (action != null) {
            // Process stairs
            String name = obstacleLocatableEntity.getDefinition().getName();
            if (name.equals("Staircase") || name.equals("Stairs") || name.equals("Ladder") || name.equals("Manhole")) {
                if (next.getPlane() != PlayerInfo.myPosition().getPlane()) {
                    if (next.getPlane() > PlayerInfo.myPosition().getPlane()) {
                        action = "Climb-up";
                    } else if (next.getPlane() < PlayerInfo.myPosition().getPlane()) {
                        action = "Climb-down";
                    }
                }
            }
            // Avoid invalid obstacles
            if (action.equals("Close")) {
                Logger.log("Invalid obstacle [" + obstacleLocatableEntity.getPosition().toString() + "]");
                return false;
            }
            if (PlayerInfo.isPlayerIdle() && obstacleLocatableEntity.getPosition().isReachable()) {
                Logger.log("Interacting with obstacle obstructing [" + next.toString() + "]: [" + obstacleLocatableEntity.getPosition().toString() + "] using action \"" + action + "\"");
                if (!obstacleLocatableEntity.isVisible())
                    if(!Camera.turnTo(obstacleLocatableEntity))
                        RegionPath.buildTo(obstacleLocatableEntity).step();
                // Interact with obstacle
                if (obstacleLocatableEntity.interact(action)) {
                    //ABUtils.increaseFatigue();
                    Logger.log("Interaction with obstacle [" + obstacleLocatableEntity.getPosition().toString() + "] successful!");
                    return true;
                } else {
                    Logger.log("Interaction with obstacle [" + obstacleLocatableEntity.getPosition().toString() + "] failed!");
                }
            }
        }
        return false;
    }

    public WebNode getNextObstacleIn(ArrayList<WebNode> path, WebNode next) {
        /*for (int i = path.indexOf(next) - 1; i >= 0; i--) {
            WebNode node = path.get(i);
            Logger.log(i);
            if (node.getY() == PlayerInfo.myPosition().getY() && WebWalker.getWeb().getObstacles().contains(node)) {
                return node;
            }
        }*/
        WebNode obstacleNode = null;
        WebNode lastReachable = null;
        for (WebNode node : path)
            if (Calculations.canReach(node.construct())) lastReachable = node;
        if (lastReachable == null) {
            lastReachable = path.get(0);
        }
        if (WebWalker.getWeb().getObstacles().contains(lastReachable)) {
            obstacleNode = lastReachable;
        } else {
            for (int i = path.indexOf(lastReachable); i < path.size(); i++) {
                WebNode pathNode = path.get(i);
                if (WebWalker.getWeb().getObstacles().contains(pathNode)) {
                    obstacleNode = pathNode;
                    break;
                }
            }
        }
        return obstacleNode;

    }


}