package darkapi.script.task.impl;

import darkapi.script.task.ChainableTask;
import darkapi.script.task.Executor;

/**
 * Created by Valkyr on 12/09/15.
 */
abstract class TaskExecutor implements Executor<ChainableTask> {
    public abstract boolean execute(ChainableTask executable);
}
