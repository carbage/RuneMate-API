package darkapi.script;

import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.AbstractScript;
import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.ChainExecutor;
import darkapi.script.utils.PlayerInfo;

/**
 * Created by Valkyr on 12/10/2015.
 */
public abstract class DarkScript extends AbstractScript {

    private ChainExecutor taskExecutor = new ChainExecutor();
    private ChainableTask mainTask;

    @Override
    public void onStart(String... args) {
        mainTask = buildTask();
    }

    @Override
    public void run() {
        // Process Events (Lobby Handler etc)
        if (getRSEventProcessor().validate()) {
            getRSEventProcessor().execute();
        }

        // Update player
        if(PlayerInfo.myPlayer() != null) {
            PlayerInfo.setPlayer(Players.getLocal());
        }

        taskExecutor.execute(mainTask);
    }

    protected abstract ChainableTask buildTask();
}
