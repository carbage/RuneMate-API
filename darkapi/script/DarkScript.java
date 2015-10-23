package darkapi.script;

import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.AbstractScript;
import com.runemate.game.api.script.framework.LoopingScript;
import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.ChainExecutor;
import darkapi.script.utils.PlayerInfo;

/**
 * Created by Valkyr on 12/10/2015.
 */
public abstract class DarkScript extends LoopingScript {

    private ChainExecutor taskExecutor = new ChainExecutor();
    private ChainableTask mainTask;

    @Override
    public void onStart(String... args) {
        mainTask = buildTask();
    }

    @Override
    public void onLoop() {
        // Process Events (Lobby Handler etc)
        if (getRSEventProcessor().validate()) {
            getRSEventProcessor().execute();
        }

        // Update player
        if(PlayerInfo.myPlayer() == null && Players.getLocal() != null) {
            PlayerInfo.setPlayer(Players.getLocal());
        }

        taskExecutor.execute(mainTask);
    }

    protected abstract ChainableTask buildTask();
}
