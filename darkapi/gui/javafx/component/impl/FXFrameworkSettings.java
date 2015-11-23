package darkapi.gui.javafx.component.impl;

import darkapi.data.bank.Bank;
import darkapi.gui.javafx.component.FXGUIComponent;
import darkapi.script.DarkScript;
import darkapi.script.utils.Banking;
import darkapi.script.utils.Settings;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

public class FXFrameworkSettings extends FXGUIComponent {

    public FXFrameworkSettings(DarkScript script) {
        super(script, "Framework Settings");

        // Banking
        final CheckBox bankCheckBox = new CheckBox("Banking");
        final ComboBox<Bank> bankComboBox = new ComboBox<>();

        bankCheckBox.setOnAction(event -> {
            boolean selected = bankCheckBox.isSelected();
            Banking.setUseBanking(selected);
            bankComboBox.setDisable(!selected);

        });

        bankComboBox.getItems().addAll(Bank.values());
        bankComboBox.setOnAction(event -> Banking.setBankLocation(bankComboBox.getSelectionModel().getSelectedItem().getLocation()));

        // Antiban
        final CheckBox antibanCheckBox = new CheckBox("Antiban");
        final CheckBox fatigueCheckBox = new CheckBox("Fatigue");

        antibanCheckBox.setOnAction(event -> Settings.setUseAntiban(antibanCheckBox.isSelected()));
        fatigueCheckBox.setOnAction(event -> Settings.setUseFatigue(fatigueCheckBox.isSelected()));

        addComponents("Banking", bankCheckBox, bankComboBox);
        addComponents("Antiban", antibanCheckBox, fatigueCheckBox);
    }


}
