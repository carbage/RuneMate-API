package darkapi.webwalker.web;

import com.runemate.game.api.hybrid.location.Coordinate;
import darkapi.script.utils.Calculations;

import java.util.ArrayList;

public class WebNode {

    private int id;
    private String name;
    private ArrayList<String> actions = new ArrayList<>();
    private short x, y, z;
    private ArrayList<WebNode> edges = new ArrayList<>();

    private int fScore = Integer.MAX_VALUE;
    private WebNode previous = null;
    private ArrayList<Integer> ids;

    public WebNode() {

    }

    public WebNode(WebNode node) {
        this.x = node.getX();
        this.y = node.getY();
        this.z = node.getPlane();
        this.id = node.getId();
        this.name = node.getName();
        this.actions = node.getActions();
        this.edges = node.getEdges();
        this.ids = node.getIds();
        this.fScore = node.getFScore();
        this.previous = node.getPrevious();
    }

    public WebNode(int id, Coordinate p, String name, String... interactOptions) {
        this.id = id;
        this.x = (short) p.getX();
        this.y = (short) p.getY();
        this.z = (short) p.getPlane();
        this.name = name;
        if (interactOptions != null)
            for (String s : interactOptions)
                this.actions.add(s);
        this.ids = new ArrayList<>();
    }

    public WebNode(Coordinate Coordinate, String name, String[] actions) {
        this(0, Coordinate, name, actions);
    }

    public short getX() {
        return x;
    }

    public short getY() {
        return y;
    }

    public short getPlane() {
        return z;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public ArrayList<String> getActions() {
        return actions;
    }

    public void addEdge(WebNode n) {
        if (n != null && !edges.contains(n)) {
            edges.add(n);
        }
    }

    public short distance(WebNode v3d) {
        return distance(v3d.construct());
    }

    public short distance(Coordinate p) {
        int x = this.x > 6000 ? (short) (this.x - 6400) : this.x;
        int y = this.y > 6000 ? (short) (this.y - 6400) : this.y;
        int z = this.z;
        return (short) Calculations.distance(new Coordinate(x, y, z), new Coordinate(p.getX(), p.getY(), p.getPlane()));
    }

    public Coordinate construct() {
        return new Coordinate(x, y, z);
    }


    public void setFScore(int fScore) {
        this.fScore = fScore;
    }

    public int getFScore() {
        return this.fScore;
    }

    public void setPrevious(WebNode v) {
        previous = v;
    }

    public WebNode getPrevious() {
        return previous;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof WebNode) {
            WebNode node = (WebNode) o;
            return getX() == node.getX()
                    && getY() == node.getY()
                    && getPlane() == node.getPlane();
        } else if (o instanceof Coordinate) {
            Coordinate p = (Coordinate) o;
            return getX() == p.getX()
                    && getY() == p.getY()
                    && getPlane() == p.getPlane();
        }
        return o != null && o.equals(this);
    }

    @Override
    public String toString() {
        return String.valueOf("id=" + id + " " + (name != null && name.length() > 0 ? "name=" + name + ", " : "") +
                "x=" + String.valueOf(getX()) + "y=" + String.valueOf(getY()) + ", z=" + String.valueOf(getPlane()));
    }

    public boolean hasAllActions(String... actions) {
        if (getActions() != null)
            for (String action : actions)
                if (!getActions().contains(action))
                    return false;
        return true;
    }

    public boolean hasAction(String... actions) {
        if (getActions() != null)
            for (String s : actions)
                for (String a : getActions())
                    if (s.equalsIgnoreCase(a))
                        return true;
        return false;
    }

    public ArrayList<WebNode> getEdges() {
        return edges;
    }

    public void addId(int i) {
        ids.add(i);
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }
}
