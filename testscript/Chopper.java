package testscript;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import darkapi.gui.javafx.component.impl.FXScriptSettings;
import darkapi.script.DarkScript;
import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.conditional.SelectionTask;
import javafx.scene.control.Label;
import testscript.task.BankTask;
import testscript.task.ChopTask;

/**
 * Created by Valkyr on 20/10/2015.
 */
public class Chopper extends DarkScript {
    @Override
    protected ChainableTask buildTask() {
        return new SelectionTask(Inventory::isFull,
                new BankTask(),
                new ChopTask());
    }

    @Override
    public void initFXSettings(FXScriptSettings panel) {
        panel.addComponents("Test", new Label("Test chopper motherfucker"));
    }
}
