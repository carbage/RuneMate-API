package darkapi.webwalker.web.loader.util;

import darkapi.webwalker.web.Web;
import darkapi.webwalker.web.loader.AbstractWebLoader;

public final class LoaderThread extends Thread {

    private final AbstractWebLoader loader;
    private final String[] set;
    private final Web web;
    private int index;
    private int length;
    private final int start;
    private final int end;

    public LoaderThread(AbstractWebLoader loader, String[] set, Web web, int start, int end) {
        this.loader = loader;
        this.web = web;
        this.set = set;
        this.start = start;
        this.end = end;
        this.length = start - end;
    }

    public final void run() {
        loader.log(index + " " + start + " " + end);
        for (index = start; index <= end; index++) {
            loader.parseLine(set[index]);
        }
    }

    public final int getIndex() {
        return index;
    }

    public final int getLength() {
        return length;
    }
}

