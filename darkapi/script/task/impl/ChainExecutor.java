package darkapi.script.task.impl;

import darkapi.script.task.ChainableTask;

/**
 * Created by Valkyr on 13/09/15.
 */
public class ChainExecutor extends TaskExecutor {

    private static ChainExecutor instance;

    @Override
    public boolean execute(ChainableTask executable) {
        return executable.process();
    }

    public static ChainExecutor getInstance() {
        return instance == null ? (instance = new ChainExecutor()) : instance;
    }

    public static boolean exec(ChainableTask executable) {
        return getInstance().execute(executable);
    }
}
