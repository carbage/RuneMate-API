package darkapi.webwalker.web.loader;


import com.runemate.game.api.hybrid.Environment;
import darkapi.script.utils.Logger;
import darkapi.webwalker.web.Web;
import darkapi.webwalker.web.WebNode;
import darkapi.webwalker.web.loader.util.LoaderThread;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractWebLoader implements ILoader<Web, WebNode> {
    private final Web web;
    private final InputStream fileStream;

    protected final int processors = 1;

    protected AbstractWebLoader(Web web) {
        this.web = web;
        this.fileStream = this.getClass().getResourceAsStream("/darkapi/resources/" + LoaderSettings.WEB);
        //log("IF IT'S NOT LOADING, PM CZAR");
    }

    public abstract WebNode parseLine(String line);

    public abstract String parseObject(WebNode node);

    public abstract Web load();

    public final ArrayList<WebNode> getNodes() {
        parseLines(readNodes());
        return web.getNodes();
    }

    public final WebNode[] parseLines(String[] set) {
        try {
            int size = set.length - 1;
            log("Reading web on " + processors + " thread" + (processors > 1 ? "s" : "") + "...");
            ExecutorService es = Executors.newCachedThreadPool();
            int start = 0;
            int end = size;
            LoaderThread readerThread = new LoaderThread(this, set, web, start, end);
            es.execute(readerThread);
            es.shutdown();
            web.setLoaded(es.awaitTermination(10, TimeUnit.MINUTES));
            if (!web.isLoaded()) {
                log("Failed to load web! Please restart the script.");
                Environment.getScript().stop();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public final void store() {
        try {
            long startTime = System.currentTimeMillis();
            log("Storing web file...");
            int written = 0;
            File outFile = new File(LoaderSettings.WEB);
            FileOutputStream out = new FileOutputStream(outFile);
            for (String s : getStrings()) {
                written++;
                out.write(s.getBytes());
            }
            out.close();
            log("Successfully wrote " + written + "nodes");
            log("Wrote web file written to " + outFile.getPath() + " in " + (System.currentTimeMillis() - startTime) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
        log("Inputstreams cant melt steel beams");
    }

    public final String[] getStrings() {
        ArrayList<String> nodeStrings = new ArrayList<>();
        for (WebNode node : getWeb().getNodes()) {
            nodeStrings.add(parseObject(node));
        }
        return nodeStrings.toArray(new String[nodeStrings.size()]);
    }

    private String[] readNodes() {
        String[] rawSet;
        try {
            List<String> strings = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(getFileStream()));
            String line;
            while ((line = br.readLine()) != null) {
                strings.add(line);
            }

            rawSet = strings.toArray(new String[strings.size()]);
            return rawSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public final void log(String s) {
        String out = getClass().getSimpleName() + ": " + s;
        Logger.log(out);
    }

    public boolean isLoaded() {
        return web.isLoaded();
    }

    public final Web getWeb() {
        return web;
    }

    public final InputStream getFileStream() {
        return fileStream;
    }
}
