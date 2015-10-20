package darkapi.script.task;

/**
 * Created by Valkyr on 12/09/15.
 */
public interface Executable {
    boolean execute();
    void prepare();
    boolean canExecute();
}
