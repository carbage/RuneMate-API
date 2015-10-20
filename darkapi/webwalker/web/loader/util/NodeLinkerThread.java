package darkapi.webwalker.web.loader.util;

import darkapi.webwalker.web.Web;
import darkapi.webwalker.web.WebNode;

public final class NodeLinkerThread extends Thread {

    private final Web web;
    private final WebNode[] nodeSet;

    public NodeLinkerThread(WebNode[] nodeSet, Web web) {
        this.web = web;
        this.nodeSet = nodeSet;
    }

    public final void run() {
        for (WebNode node : nodeSet) {
            for (int id : node.getIds()) {
                WebNode edge = web.getNodeMap().get(id);
                if (edge != null) {
                    node.addEdge(edge);
                    edge.addEdge(node);
                }
            }
        }
    }
}

