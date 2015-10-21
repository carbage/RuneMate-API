package darkapi.script.task.impl.banking;


import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import darkapi.script.utils.PlayerInfo;

/**
 * Created by Valkyr on 12/09/15.
 */
public class DepositAllTask extends BankingTask {
    private final String[] items;

    public DepositAllTask(String... items) {
        this.items = items;
    }

    @Override
    public boolean execute() {
        if (openBank()) {
            for (String item : items)
                if (!Bank.deposit(item, 0))
                    return false;
        }
        return Bank.close();
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return PlayerInfo.isPlayerIdle();
    }
}
