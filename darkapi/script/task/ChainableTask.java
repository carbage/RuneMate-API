package darkapi.script.task;

import darkapi.script.utils.Logger;

/**
 * Created by Valkyr on 12/09/15.
 */
public abstract class ChainableTask implements Executable {
    private ChainableTask chained = null;

    public final ChainableTask chain(ChainableTask toChain) {
        this.chained = toChain;
        return chained;
    }

    public boolean process() {
        if (canExecute()) {
            Logger.log("Attempting to execute task: [" + getClass().getSimpleName() + "]");
            boolean executed = execute();
            if (chained != null) {
                Logger.log("Executing chained task: [" + chained.getClass().getSimpleName() + "]");
                chained.process();
            }
            if (executed) Logger.log("Successfully executed task: [" + getClass().getSimpleName() + "]");
            else Logger.log("Failed to execute task: [" + getClass().getSimpleName() + "]");
            return executed;
        } else {
            Logger.log("Unable to execute task: [" + getClass().getSimpleName() + "], preparing...");
            prepare();
        }
        return false;
    }

    public abstract boolean execute();

    public abstract void prepare();

    public abstract boolean canExecute();
}
