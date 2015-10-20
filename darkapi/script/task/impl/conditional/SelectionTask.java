package darkapi.script.task.impl.conditional;

import darkapi.script.task.ChainableTask;
import darkapi.script.task.util.Condition;

/**
 * Created by Valkyr on 12/09/15.
 */
public class SelectionTask extends ChainableTask {
    private final Condition condition;
    private final ChainableTask first;
    private final ChainableTask second;

    public SelectionTask(Condition condition, ChainableTask first, ChainableTask second) {
        this.condition = condition;
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean execute() {
        return condition.validate() ? first.execute() : second.execute();
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return true;
    }
}
