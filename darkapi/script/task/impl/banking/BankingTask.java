package darkapi.script.task.impl.banking;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.queries.results.SpriteItemQueryResults;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.sun.scenario.Settings;
import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.ChainExecutor;
import darkapi.script.task.impl.walking.WebWalkTask;
import darkapi.script.utils.Banking;
import darkapi.script.utils.Logger;
import darkapi.script.utils.PlayerInfo;
import darkapi.webwalker.WebWalker;
import darkapi.webwalker.web.WebNode;

/**
 * Created by Valkyr on 13/09/15.
 */
public abstract class BankingTask extends ChainableTask {

    private SpriteItemQueryResults cachedBank;

    public boolean isBankOpen() {
        return Bank.isOpen();
    }

    public boolean openBank() {
        if (!isBankOpen()) {
            Logger.log("Attemting to open bank.");
            if (!Bank.open()) {
                Logger.log("Unable to open bank, walking to selected location.");
                chain(new WebWalkTask(Banking.getBankLocation()));
            }
        } else cachedBank = Bank.getItems();
        return isBankOpen();
    }
}
