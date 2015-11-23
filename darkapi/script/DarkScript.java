package darkapi.script;

import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.Timer;
import com.runemate.game.api.script.framework.LoopingScript;
import darkapi.gui.javafx.FXScriptGUI;
import darkapi.gui.javafx.component.impl.FXScriptSettings;
import darkapi.script.task.ChainableTask;
import darkapi.script.task.impl.ChainExecutor;
import darkapi.script.utils.InfoTracker;
import darkapi.script.utils.Logger;
import darkapi.script.utils.PlayerInfo;
import darkapi.webwalker.WebWalker;
import darkapi.webwalker.web.Web;
import darkapi.webwalker.web.WebNode;
import javafx.application.Platform;

import java.awt.*;

/**
 * Created by Valkyr on 12/10/2015.
 */
public abstract class DarkScript extends LoopingScript implements PaintListener {

    private ChainableTask mainTask;
    private Timer timer = new Timer(0);

    private boolean isRunning;
    private FXScriptGUI gui;


    @Override
    public final void onStart(String... args) {
        mainTask = buildTask();
        timer.start();
        Platform.runLater(() -> {
            gui = new FXScriptGUI(DarkScript.this);
            gui.show();
        });
        getEventDispatcher().addListener(this);

    }

    @Override
    public final void onLoop() {
        if (isRunning) {
            // Process Events (Lobby Handler etc)
            if (getRSEventProcessor().validate()) {
                getRSEventProcessor().execute();
            }

            // Update player
            if (PlayerInfo.myPlayer() == null && Players.getLocal() != null) {
                PlayerInfo.setPlayer(Players.getLocal());
            }

            if (PlayerInfo.myPlayer() != null) {
                Logger.log("---------- [" + getClass().getSimpleName() + " -> Pulsing ChainExecutor] ----------");
                ChainExecutor.exec(mainTask);
            }
        }
    }

    @Override
    public final void onStop() {
        Platform.runLater(() -> gui.close());
    }

    @Override
    public final void onPaint(Graphics2D g) {
        if (InfoTracker.getWebPath() != null && !InfoTracker.getWebPath().isEmpty()) {
            for (WebNode loc : InfoTracker.getWebPath()) {
                if (loc != null) {
                    if (WebWalker.getWeb().getObstacles().contains(loc)) g.setColor(Color.RED);
                    else if (WebWalker.getWeb().getResources().contains(loc)) g.setColor(Color.GREEN);
                    else if (WebWalker.getWeb().getNpcs().contains(loc)) g.setColor(Color.YELLOW);
                    else g.setColor(Color.CYAN);
                    loc.construct().render(g);
                }
            }
        }
    }

    protected abstract ChainableTask buildTask();

    public final void toggleIsRunning() {
        isRunning = !isRunning;
    }

    public final boolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public abstract void initFXSettings(FXScriptSettings panel);

    public String getRunTime() {
        return timer.getElapsedTimeAsString();
    }
}
