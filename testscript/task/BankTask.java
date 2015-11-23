package testscript.task;

import com.runemate.game.api.hybrid.location.Coordinate;
import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.ChainExecutor;
import darkapi.script.task.impl.banking.DepositAllTask;
import darkapi.script.utils.Banking;
import darkapi.script.utils.PlayerInfo;

/**
 * Created by Valkyr on 20/10/2015.
 */
public class BankTask extends DepositAllTask {
    public BankTask() {
        super("Logs");
        Banking.setBankLocation(new Coordinate(3208, 3220, 2));
    }
    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return PlayerInfo.isPlayerIdle();
    }
}
