package darkapi.script.utils;

import com.runemate.game.api.hybrid.location.navigation.web.Web;
import com.runemate.game.api.hybrid.location.navigation.web.default_webs.FileWeb;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Valkyr on 28/10/2015.
 */
public class WebTools {

    public static Web instance;

    public static Web getWeb() {
        return instance != null ? instance : (instance = loadWeb());
    }

    private static Web loadWeb() {
        return loadWeb("/darkapi/resources/web_DARK.nav");
    }

    public static Web loadWeb(String filename) {
        FileWeb fweb = null;
        InputStream is = WebTools.class.getResourceAsStream(filename);
        if (is != null) {
            try {
                byte[] bytes = IOUtils.readFully(is, -1, true);
                if (bytes != null) {
                    fweb = FileWeb.fromByteArray(bytes);
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        } else {
            Logger.log("Null inputstream");
        }
        if (fweb != null) {
            Logger.log("[NativeWeb] Loaded " + fweb.getVertices().size() + " vertices");
            return fweb;
        } else {
            Logger.log("Null fileweb");
        }
        return null;
    }
}
