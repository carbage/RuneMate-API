package darkapi.script.task.impl.util;


import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.ChainExecutor;
import darkapi.script.utils.PlayerInfo;

/**
 * Created by Valkyr on 14/09/15.
 */
public class GroupTask extends ChainableTask {

    private final ChainableTask[] tasks;

    public GroupTask(ChainableTask... tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean execute() {
        for (ChainableTask task : tasks)
            if (ChainExecutor.exec(task))
                return true;
        return false;
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return true;
    }
}
