package testscript.task;

import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.ChainExecutor;
import darkapi.script.task.impl.interact.InteractEntityTask;
import darkapi.script.utils.Logger;
import darkapi.script.utils.PlayerInfo;

/**
 * Created by Aiden on 20/10/2015.
 */
public class ChopTask extends ChainableTask {
    @Override
    public boolean execute() {
        Logger.log("Attempting to chop trees...");
        return ChainExecutor.exec(new InteractEntityTask(s -> s.equals("Tree"), "Chop down"));
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return !PlayerInfo.isPlayerAnimating();
    }
}
