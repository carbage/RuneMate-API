package darkapi.script.task.impl.walking;

import darkapi.script.task.ChainableTask;

/**
 * Created by Valkyr on 12/09/15.
 */
/*public class WebWalkTask extends ChainableTask {

    private final WebNode destination;

    public WebWalkTask(int dX, int dY, int dZ) {
        this(webNode -> webNode.equals(new Tile(dX, dY, dZ)));
    }

    public WebWalkTask( int dX, int dY) {
        this(webNode -> webNode.equals(new Tile(dX, dY, 0)));
    }

    public WebWalkTask(String... names) {
        this(webNode -> {
            for (String name : names) if (name.equalsIgnoreCase(webNode.getName())) return true;
            return false;
        });
    }

    public WebWalkTask(Predicate<WebNode> nodeFilter) {
        this(WebWalker.getClosest(nodeFilter));
    }

    public WebWalkTask(WebNode destination) {
        this.destination = destination;
    }

    @Override
    public boolean execute() {
        return getContext().getWebWalker().walk(destination);
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return destination != null;
    }
}*/
