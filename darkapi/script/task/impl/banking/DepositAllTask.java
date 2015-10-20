package darkapi.script.task.impl.banking;


import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import darkapi.script.utils.PlayerInfo;

/**
 * Created by Valkyr on 12/09/15.
 */
public class DepositAllTask extends BankingTask {
    private final String[] items;
    private final boolean depositBox;

    public DepositAllTask(boolean depositBox, String... items) {
        this.items = items;
        this.depositBox = depositBox;
    }

    @Override
    public boolean execute() {
        if (openBank()) {
            for (String item : items)
                if (!Bank.deposit(item, 0))
                    return false;
        }
        return true;
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return PlayerInfo.isPlayerIdle();
    }
}
