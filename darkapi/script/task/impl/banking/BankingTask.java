package darkapi.script.task.impl.banking;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.queries.results.SpriteItemQueryResults;
import com.runemate.game.api.hybrid.region.Players;
import com.sun.scenario.Settings;
import darkapi.script.task.ChainableTask;
import darkapi.script.utils.Banking;
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
            //Logger.log("Attemting to open bank.");
            if (!Bank.open()) {
                //Logger.log("Unable to open bank, walking to selected location.");
                Coordinate position = !Banking.useCustomBank() ? Players.getLocal().getPosition() : Banking.getBankLocation();
                WebNode destination = WebWalker.getClosestNode(position, "Bank booth", "Bank chest");
                WebWalker.walk(destination);
            }
        } else cachedBank = Bank.getItems();
        return isBankOpen();
    }
}