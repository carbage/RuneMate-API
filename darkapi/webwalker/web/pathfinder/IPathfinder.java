package darkapi.webwalker.web.pathfinder;


import darkapi.webwalker.web.WebNode;

import java.util.ArrayList;

public interface IPathfinder {

    ArrayList<WebNode> findPath(WebNode start, WebNode destination);
}
