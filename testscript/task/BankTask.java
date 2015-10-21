package testscript.task;

import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.ChainExecutor;
import darkapi.script.task.impl.banking.DepositAllTask;
import darkapi.script.utils.PlayerInfo;

/**
 * Created by Aiden on 20/10/2015.
 */
public class BankTask extends ChainableTask {
    @Override
    public boolean execute() {
        return ChainExecutor.exec(new DepositAllTask("Logs"));
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return PlayerInfo.isPlayerIdle();
    }
}
