package darkapi.script.utils;

import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.location.navigation.Path;
import darkapi.script.DarkScript;
import darkapi.webwalker.web.WebNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Valkyr on 29/10/2015.
 */
public class InfoTracker {

    private static Path path;
    private final DarkScript script;
    public static final Map<String, Returnable> returnables =  new HashMap<>();
    private static ArrayList<WebNode> webPath;

    public InfoTracker(final DarkScript script) {
        this.script = script;
        returnables.put("Run time:", new Returnable<String>() {
            @Override
            public String get() {
                return script.getRunTime();
            }
        });
    }

    public static Path getPath() {
        return path;
    }
    public static void setPath(Path path) {
        InfoTracker.path = path;
    }

    public static ArrayList<WebNode> getWebPath() {
        return webPath;
    }

    public static void setWebPath(ArrayList<WebNode> path) {
        webPath = path;
    }

    public void add(String s, Returnable<?> returnable) {
        if (returnables != null) {
            returnables.put(s, returnable);
        }
    }

    public static Map<String, Returnable> getReturnables() {
        return returnables;
    }
}
