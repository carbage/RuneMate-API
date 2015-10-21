package darkapi.script.task.impl.banking;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import darkapi.script.utils.PlayerInfo;

/**
 * Created by Valkyr on 12/09/15.
 */
public class WithdrawTask extends BankingTask {
    private final String itemName;
    private final int amount;

    public WithdrawTask(String itemName, int amount) {
        this.itemName = itemName;
        this.amount = amount;
    }

    @Override
    public boolean execute() {
        if (openBank()) {
            return Bank.withdraw(itemName, 0) && Bank.close();
        }
        return false;
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return PlayerInfo.isPlayerIdle();
    }
}
