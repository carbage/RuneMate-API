package darkapi.script.task.impl.banking;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.util.Filter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Valkyr on 12/09/15.
 */
public class DepositAllExceptTask extends BankingTask {
    private final ArrayList<String> except;

    public DepositAllExceptTask(String... except) {
        this.except = new ArrayList<>();
        Collections.addAll(this.except, except);
    }

    @Override
    public boolean execute() {
        if (openBank()) {
            if (Bank.depositAllExcept(new Filter<SpriteItem>() {
                @Override
                public boolean accepts(SpriteItem spriteItem) {
                    return except.contains(spriteItem.getDefinition().getName());
                }
            })) {
                return false;
            }
        }
        return Bank.close();
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        return true;
    }
}
