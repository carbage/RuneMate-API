package darkapi.script.task;

/**
 * Created by Valkyr on 12/09/15.
 */
public abstract class TaskExecutor implements Executor<ChainableTask> {
    public abstract boolean execute(ChainableTask executable);
}
