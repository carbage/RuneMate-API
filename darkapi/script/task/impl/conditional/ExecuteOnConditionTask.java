package darkapi.script.task.impl.conditional;

import darkapi.script.task.ChainableTask;
import darkapi.script.task.util.Condition;

/**
 * Created by Valkyr on 12/09/15.
 */
public class ExecuteOnConditionTask extends ChainableTask {

    private final Condition condition;
    private final ChainableTask toExecute;

    public ExecuteOnConditionTask(Condition condition, ChainableTask toExecute) {
        this.condition = condition;
        this.toExecute = toExecute;
    }

    @Override
    public boolean execute() {
        return toExecute.canExecute() && toExecute.process();
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return condition.validate();
    }
}
