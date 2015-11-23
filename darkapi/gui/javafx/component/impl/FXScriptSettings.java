package darkapi.gui.javafx.component.impl;

import darkapi.script.DarkScript;
 import darkapi.gui.javafx.component.FXGUIComponent;

public class FXScriptSettings extends FXGUIComponent {

    public FXScriptSettings(DarkScript script) {
        super(script, "Options");
        script.initFXSettings(this);
    }
}
