package darkapi.script.task;

/**
 * Created by Valkyr on 12/09/15.
 */
public interface Executor<T> {
    boolean execute(T executable);
}
