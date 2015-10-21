package testscript;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import darkapi.script.DarkScript;
import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.conditional.SelectionTask;
import testscript.task.BankTask;
import testscript.task.ChopTask;

/**
 * Created by Aiden on 20/10/2015.
 */
public class Chopper extends DarkScript {
    @Override
    protected ChainableTask buildTask() {
        return new SelectionTask(Inventory::isFull,
                new BankTask(),
                new ChopTask());
    }
}
