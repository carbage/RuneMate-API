package darkapi.script.task.impl.conditional;

import com.runemate.game.api.script.Execution;
import darkapi.script.task.ChainableTask;
import darkapi.script.task.util.Condition;

/**
 * Created by Valkyr on 12/09/15.
 */
public class WaitForConditionTask extends ChainableTask {
    private final Condition condition;

    public WaitForConditionTask(Condition condition) {
        this.condition = condition;
    }

    @Override
    public boolean execute() {
        Execution.delayUntil(condition::validate, 3000);
        return true;
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return true;
    }
}
