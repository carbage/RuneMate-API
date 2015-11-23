package darkapi.webwalker.web.loader;

import com.runemate.game.api.hybrid.Environment;

public class LoaderSettings {
    public static final String WEB = "web_" + Environment.getGameType() + ".dat";
    public static final String SEPARATOR = ",";
    public static final String EDGE_SEPARATOR = ";";
    public static final String LINE_BREAK = "\n";
}
